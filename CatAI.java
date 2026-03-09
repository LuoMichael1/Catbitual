// controls the movement and animations of the cat, runs on a separate thread to not interfere with the UI
import java.util.concurrent.*;


public class CatAI {
    private Cat cat;
    private ScheduledThreadPoolExecutor running = new ScheduledThreadPoolExecutor(2);

    public CatAI(Cat cat) {
        this.cat = cat;

        // updates the the cat ~9 times per seconds 
        UpdateState task = new UpdateState(cat);
        running.scheduleAtFixedRate(task, 0, 1000/9, TimeUnit.MILLISECONDS);      
    }
}



/*states
1 Wandering  Walks to location stops, and then chooses another location
3 Grabbed    Cat has been grabbed by the user
4 falling    Cat has been released by the user
5 Idle       Does not move
6 Sleeping   Cat is sleeping
7 Sitting    Cat sits in place
8 Petted     Cat is being petted by the user
*/

enum State {
    WANDERING,
    GRABBED,
    FALLING,
    IDLE,
    SLEEPING,
    SITTING,
    PETTED;
}


class UpdateState implements Runnable {
    
    private Cat cat;
    private int lastPosX = 0;   //cat.xPos; // this is only for the grabbing animations so we can tell if the mouse moved
    private int swayBuffer = 0; // if the cat was being dragged, gives a buffer of one tick to keep it dragged - prevents switching between still and moving while being dragged
    private long lastDecayTick; // track time between ticks to decrease hunger/water over time

    public UpdateState(Cat cat) {
        this.cat = cat;
        this.lastDecayTick = System.currentTimeMillis();
    }

    public void run() {
        // ** decay logic **
        long now = System.currentTimeMillis();
        if (now - lastDecayTick >= User.FOOD_WATER_DECAY_MS) {
            int steps = (int)((now - lastDecayTick) / User.FOOD_WATER_DECAY_MS);
            lastDecayTick += (long)steps * User.FOOD_WATER_DECAY_MS;
            // subtract steps units from both
            cat.setFood(cat.getFood() - steps);
            cat.setWater(cat.getWater() - steps);
            Menu.refreshCoins(); // update score menu display
        }

        State currentState = State.WANDERING;
        
        if (cat.getGrabbed()) {
            currentState = State.GRABBED;
        }
        else if (cat.getPetted()) {
            currentState = State.PETTED;
        }
        else if (!cat.getPetted()) {
            petAnimationSteps = 0; // reset value
        }

        //tempState = (int)(Math.floor(Math.random()*7));
        switch(currentState) {
            case WANDERING: wander();
                break;
            case GRABBED: animateGrab();
                break;
            case FALLING: // unused, there is no good way to determine where the cat should land
                break;
            case IDLE:    // this is essentially useless because of wandering, but could be used in the future
                break;
            case SLEEPING:// no cat beds currently, could be added in the future
                break;
            case SITTING: sit();  // works but unused
                break;
            case PETTED: pet();
                break;
            default: System.out.println("Warning: Cat state was not found");
                break;
        }
        cat.repaint();
    }


    private int count = 0;
    private int x = 0;
    private int y = 0;
    public void wander() {
        
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

    private int petAnimationSteps = 0;  // special counter for petting animation since single frames need to last longer 

    public void pet() {
        petAnimationSteps++;
        if (petAnimationSteps < 6)
            cat.setStep(23);
        else if(petAnimationSteps < 50)
            cat.setStep(20);
        else
            cat.setStep(29);



        if (System.currentTimeMillis() - Menu.lastMoveTime > 100) {
            cat.setPetted(false);
        }
    }

    public void sit() {
        if (cat.getStep() != 15) {
            cat.setStep(15);
        }
        else if (cat.getStep() != 16) {
            cat.setStep(16);
        }
    }
}