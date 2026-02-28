// im going to tentively make this a seperate class but it might
// make more sense as a method with in the existing Cat class


/*states
1 Walk       Walks to location
2 Wander     Walks to location stops, and then chooses another location
3 Grabbed    Cat has been grabbed by the user
4 falling    Cat has been released by the user
5 Idle       Does not move
6 Sleep      Cat is sleeping
7 Sit        Cat sits in place
8 Clean      Cat sits and licks itself
9 Petted     Cat is being petted by the user
*/


import java.util.concurrent.*;

public class CatAI {

    private Cat cat;
    private ScheduledThreadPoolExecutor running = new ScheduledThreadPoolExecutor(2);

    public CatAI(Cat cat) {
        this.cat = cat;
        cat.setState(0);

        // updates the the cat ~9 times per seconds (9 FPS)
        UpdateState task = new UpdateState(cat);
        running.scheduleAtFixedRate(task, 0, 1000/9, TimeUnit.MILLISECONDS);      
    }
}

class UpdateState implements Runnable {
    
    private String state = "";
    private Cat cat;
    private int lastPosX = 0;//cat.xPos; // this is only for the grabbing animations so we can tell if the mouse moved
    private int swayBuffer = 0; // if the cat was being dragged, gives a buffer of one tick to keep it dragged - prevents switching between still and moving while being dragged

    public UpdateState(Cat cat) {
        this.cat = cat;
    }

    
    
    public void run() {
        int tempState = 0;
        
        if (cat.getGrabbed()) {
            tempState = 1;
        }

        //tempState = (int)(Math.floor(Math.random()*7));

        if (tempState == 0) {
            state = "Walk";
            cat.setState(0);
            walk();
        }
        else if (tempState == 1) {
            state = "Grabbed";
            cat.setState(1);
            animateGrab();
        }
        else if (tempState == 2) {
            state = "Idle";
        }
        else if (tempState == 3) {
            state = "Sleep";
        }
        else if (tempState == 4) {
            state = "Sit";
        }
        else if (tempState == 5) {
            state = "Clean";
        }
        else {
            state = "Petted";
        }
        //System.out.println(state);
        cat.repaint();
    }

    private int count = 0;
    int x = 0;
    int y = 0;
    public void walk () {
        
        // pick a place on the screen to move towards
        if (count == 0) {
            x = (int)(Math.random()*Main.width);
            y = (int)(Math.random()*(Main.height-Room.floorHeight))+Room.floorHeight;    
        }
        // choses a new location to move towards after this amount of time
        count++;
        if (count == 85) {
            count =0;
        }
        // updates the animation step
        cat.setStep(cat.getStep()+1);
        if (cat.getStep() >= 6) {
            cat.setStep(0);
        }

        // move the cat towards the objective
        // we currently do not normalize the diagnonal movement :3
        if (cat.getX()>x) {
            // move left
            cat.setDirection(false);
            cat.setX(cat.getX()-cat.getSpeed());

            // prevents vibrating
            if (cat.getX() < x) {
                cat.setX(x);
            }
        }
        else if (cat.getX()<x) {
            // move right
            cat.setDirection(true);
            cat.setX(cat.getX()+cat.getSpeed());

            // prevents vibrating
            if (cat.getX() > x) {
                cat.setX(x);
            }
        }

        if (cat.getY()>y) {
            cat.setY(cat.getY()-cat.getSpeed());
            // prevents vibrating
            if (cat.getY() < y) {
                cat.setY(y);
            }
        }
        else if (cat.getY()<y) {
            cat.setY(cat.getY()+cat.getSpeed());
            // prevents vibrating
            if (cat.getY() >y) {
                cat.setY(y);
            }
        }

        //System.out.println("testing");
        
    }


    public void animateGrab() {
        cat.setStep(cat.getStep()+1);
        if (swayBuffer > 0) {
            swayBuffer--;
        }
        // if cat is being held in place, the +7 acts as a buffer so small movements still count
        if (lastPosX >= cat.xPos-7 && lastPosX <= cat.xPos+7 && swayBuffer==0) {
            if (cat.getStep() >= 13 || cat.getStep() < 9) {
                cat.setStep(9);
            }
        } 

        else if (!cat.getDirection()) {
            // cat moved left
            if (lastPosX > cat.xPos) {
                if (cat.getStep() >= 28 || cat.getStep() < 25) {
                    cat.setStep(26);
                }   
            }
            //cat moved right
            else {
                if (cat.getStep() >= 34 || cat.getStep() < 30) {
                    cat.setStep(31);
                }
            }
            swayBuffer = 2;
        }
        else {
            //cat moved right
            if (lastPosX > cat.xPos) {
                if (cat.getStep() >= 34 || cat.getStep() < 30) {
                    cat.setStep(31);
                }
            }
            // cat moved left
            else {
                if (cat.getStep() >= 28 || cat.getStep() < 25) {
                    cat.setStep(26);
                }   
            }
            swayBuffer = 2;
        }
        if (lastPosX == cat.xPos && swayBuffer != 0) {
            swayBuffer--;
        }
        lastPosX = cat.xPos;  
    }
}