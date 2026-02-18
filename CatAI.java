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

    public UpdateState(Cat cat) {
        this.cat = cat;
    }

    
    
    public void run() {
        int tempState = 0;
        

        //tempState = (int)(Math.floor(Math.random()*7));

        if (tempState == 0) {
            state = "Walk";
            walk ();
        }
        else if (tempState == 1) {
            state = "Grabbed";
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
        System.out.println(state);
    }

    private int count = 0;
    int x = 0;
    int y = 0;
    public void walk () {
        // pick a place on the screen to move towards
        
        if (count%100 == 0) {
            x = (int)(Math.random()*Main.width);
            y = (int)(Math.random()*(Main.height-Room.floorHeight))+Room.floorHeight;    
        }
        count++;
        System.out.println(count);

        // move the cat towards the objective
        // we currently do not normalize the diagnonal movement :3
        if (cat.getX()>x) {
            cat.setX(cat.getX()-cat.getSpeed());

            // prevents vibrating
            if (cat.getX() < x) {
                cat.setX(x);
            }
        }
        else if (cat.getX()<x) {
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

        System.out.println("testing");
        cat.repaint();
    }
}