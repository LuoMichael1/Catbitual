// im going to tentively make this a seperate class but it might
// make more sense as a method with in the existing Cat class

//actually 

import java.util.Timer;
import java.util.concurrent.*;

public class CatAI {

    private Cat cat;
    private ScheduledThreadPoolExecutor running = new ScheduledThreadPoolExecutor(2);
     // states
        //1 Walk       Walks to location
        //2 Grabbed    Cat has been grabbed by the user
        //3 Idle       Does not move
        //4 Sleep      Cat is sleeping
        //5 Sit        Cat sits in place
        //6 Clean      Cat sits and licks itself
        //7 Petted     Cat is being petted by the user

    public CatAI(Cat cat) {
        this.cat = cat;

        // updates the state of the cat every 10 seconds
        UpdateState task = new UpdateState(cat);
        running.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);      
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


    public void walk () {
        // pick a place on the screen to move towards
        int x = 500;
        int y = 500;


        // move the cat towards the objective
        // we currently do not normalize the diagnonal movement :3
        if (cat.getX()>500) {
            cat.setX(cat.getX()-cat.getSpeed());
        }
        else if (cat.getX()<500) {
            cat.setX(cat.getX()+cat.getSpeed());
        }

        if (cat.getY()>500) {
            cat.setY(cat.getY()-cat.getSpeed());
        }
        else if (cat.getY()<500) {
            cat.setY(cat.getY()+cat.getSpeed());
        }
    }
}