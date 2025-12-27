import java.awt.*;
import javax.swing.ImageIcon;

public class Cat extends Entities{
    
    static ImageIcon catImage = new ImageIcon("Assets/Images/blackcat.png");
    static final int sizeX = 1024;
    static final int sizeY = 1024;
    private String breed;
    private double food;
    private double water;
    private double happiness;

    public Cat (String name, int xPos, int yPos, String breed, double water, double happiness) {
        super(name, xPos, yPos, sizeX, sizeY);
        System.out.println(catImage.getImageLoadStatus());
        this.breed = breed;
        this.water = water;
        this.happiness = happiness;
    }
    public Cat (String name, int xPos, int yPos) {
        super(name, xPos, yPos, sizeX, sizeY);
        System.out.println(catImage.getImageLoadStatus());
        breed = "Black";
        water = 100;
        happiness = 100;
    }
    public void grabbed() {
        catImage = new ImageIcon("Assets/Images/blackcat-grabbed.png");
    }
    public void notGrabbed() {
        catImage = new ImageIcon("Assets/Images/blackcat.png");
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
}
