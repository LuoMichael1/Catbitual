import java.awt.*;
import javax.swing.ImageIcon;

public class Cat extends Entities{
    
    static ImageIcon basicCatImage = new ImageIcon("Assets/Images/blackcat.png");
    static ImageIcon grabbedCatImage = new ImageIcon("Assets/Images/blackcat-grabbed2.png");
    private ImageIcon catImage = basicCatImage;
    
    static final int sizeX = 1024;
    static final int sizeY = 1024;
    private String breed;
    private double food;
    private double water;
    private double happiness;

    // states for the cat
    private boolean grabbed = false;

    public Cat (String name, int xPos, int yPos, String breed, double water, double happiness) {
        super(name, xPos, yPos, sizeX, sizeY);
        this.breed = breed;
        this.water = water;
        this.happiness = happiness;
    }
    public Cat (String name, int xPos, int yPos) {
        super(name, xPos, yPos, sizeX, sizeY);
        breed = "Black";
        water = 100;
        happiness = 100;
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


    public void drawCat(Graphics g) {
        g.drawImage(catImage.getImage(), super.getX()-200, super.getY(), super.getX()+200, super.getY()+400, 0, 0, sizeX, sizeX, null);
    }
    
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
}
