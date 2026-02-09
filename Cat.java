import java.awt.*;
import javax.swing.ImageIcon;

public class Cat extends Entities{
    
    static ImageIcon basicCatImage = new ImageIcon("Assets/Images/blackcat.png");
    static ImageIcon grabbedCatImage = new ImageIcon("Assets/Images/blackcat-grabbed2.png");
    private ImageIcon catImage = basicCatImage;
    
    static final int imgSizeX = 512;
    static final int imgSizeY = 512;
    private String breed;
    private double food;
    private double water;
    private double happiness;
    private static int drawSizeX = (int)(200*Main.scaleY);
    private static int drawSizeY = (int)(200*Main.scaleY);
    // states for the cat
    private boolean grabbed = false;

    private int speed = 10; //

    public Cat (String name, int xPos, int yPos, String breed, double water, double happiness) {
        super(name, xPos, yPos, imgSizeX, imgSizeY, drawSizeX, drawSizeY);
        this.breed = breed;
        this.water = water;
        this.happiness = happiness;

        CatAI brain = new CatAI(this);
    }
    public Cat (String name, int xPos, int yPos) {
        super(name, xPos, yPos, imgSizeX, imgSizeY, drawSizeX, drawSizeY);
        breed = "Black";
        water = 100;
        happiness = 100;

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

}
