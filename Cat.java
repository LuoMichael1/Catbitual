import java.awt.*;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Cat extends Entities{
    
    static ImageIcon basicCatImage = new ImageIcon("Assets/Images/animatedcattrimed.png");
    static ImageIcon grabbedCatImage = new ImageIcon("Assets/Images/animatedcattrimed.png");
    private ImageIcon catImage = basicCatImage;
    
    static final int imgSizeX = 256;
    static final int imgSizeY = 154;
    private int sx1 = 0;  // used for drawing the cat in different directions
    private int sx2 = 0;  // matches with the drawimage function
    private String breed;
    private double food;
    private double water;
    private double happiness;
    private static int drawSizeX = (int)(256*Main.scaleY*1.1);
    private static int drawSizeY = (int)(154*Main.scaleY*1.1);

    // states for the cat
    private int state = 0;
    private int step = 0;  // iterator for animations
    private boolean grabbed = false;
    private boolean direction = false;  // false --> left, true --> right  (probably a dumb way to do this :3)

    private Menu m;
    private int speed = 4; //


    public Cat (String name, int xPos, int yPos, String breed, double water, double happiness, Menu m) {
        super(name, xPos, yPos, imgSizeX, imgSizeY, drawSizeX, drawSizeY);
        this.breed = breed;
        this.water = water;
        this.happiness = happiness;
        this.m = m;
        CatAI brain = new CatAI(this);
    }
    public Cat (String name, int xPos, int yPos, Menu m) {
        super(name, xPos, yPos, imgSizeX, imgSizeY, drawSizeX, drawSizeY);
        breed = "Black";
        water = 100;
        happiness = 100;
        this.m = m;
        CatAI brain = new CatAI(this);
    }
    public void grabbed() {
        //Main.print("Cat Grabbed ----");
        catImage = grabbedCatImage;
        grabbed = true;
    }
    public void dropped() {
        //Main.print("Cat Dropped ----");
        catImage = basicCatImage;
        grabbed = false;

        // don't allow the cat to be dropped onto the wall, move the cat down to the floor
        if (super.yPos < Room.floorHeight) 
            super.yPos = Room.floorHeight;
    }


    public void draw(Graphics g) {
        g.drawImage(catImage.getImage(), super.getX(), super.getY()-drawSizeY, super.getX()+drawSizeX, super.getY(), 0, 0, imgSizeX, imgSizeY, null);
    }
    public void drawState(Graphics g) {
        // wandering state
        if (state == 0) {
            // updates the animation step (this should be in the catAI because any call to draw the cat will update this)
            step++;
            if (step >= 6) {
                step = 0;
            }
        }
        // draw the grab state
        else if (state == 1) {
            
        }
        
        // if statement handles pointing left or right
        if (direction){
            sx1 = imgSizeX+(imgSizeX*step);
            sx2 = imgSizeX*step;
        }
        else {
            sx1 = imgSizeX*step;
            sx2 = imgSizeX+(imgSizeX*step);
        }

        g.drawImage(catImage.getImage(), super.getX(), super.getY()-drawSizeY, super.getX()+drawSizeX, super.getY(), sx1, 0, sx2, imgSizeY, null);        
    }
    /* 
    public boolean withinBounds(int x, int y) {
        return (x>(xPos) && x<xPos+drawSizeX && y>(yPos-drawSizeY) && y<yPos);
    }
    */
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public double getFood() {
        return food;
    }
    public void setFood(double food) {
        this.food = food;
    }
    public double getWater() {
        return water;
    }
    public void setWater(double water) {
        this.water = water;
    }
    public double getHappiness() {
        return happiness;
    }
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }
    public boolean getGrabbed() {
        return grabbed;
    }
    public int getDrawSize() {
        return drawSizeX;
    }
    public int getSpeed() {
        return speed;
    }

    // this is not a single source of truth i think, because the cat AI also has the state
    public int getState() {
        return state;
    }
    public void setState(int n) {
        state = n;
    }
    public void setDirection(boolean d) {
        direction = d;
    }
    public boolean getDirection() {
        return direction;
    }
    public void setStep(int step) {
        this.step = step;
    }
    public int getStep() {
        return step;
    }
    public void repaint() {
        m.repaint();
    }
}
