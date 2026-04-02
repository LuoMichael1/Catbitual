import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Cat extends Entities {

    public static final ImageIcon SPRITE = new ImageIcon(Cat.class.getResource("/Assets/Images/animatedcattrimed.png"));
    private static final int IMAGE_WIDTH = 256;
    private static final int IMAGE_HEIGHT = 154;
    private static int drawSizeX = (int)(IMAGE_WIDTH*Main.scaleY*1.1);
    private static int drawSizeY = (int)(IMAGE_HEIGHT*Main.scaleY*1.1);
    private int sx1 = 0;  // updated to flip the cat so that it faces different directions
    private int sx2 = 0;  // matches with the drawimage function
    private double food = 10;
    private double water = 100;
    private Menu m;       // needed for repainting when state changes

    // states for the cat
    private int step = 0;  // iterator for animations
    private boolean grabbed = false;
    private boolean petted = false;
    private boolean direction = false;  // false --> left, true --> right  (probably a dumb way to do this :3)
    private int speed = 4; //


    public Cat (String name, int xPos, int yPos, Menu m) {
        super(name, xPos, yPos, drawSizeX, drawSizeY);
        water = 100;
        this.m = m;
        new CatAI(this);
    }


    @Override
	public void draw(Graphics g) {
        g.drawImage(SPRITE.getImage(), super.getX(), super.getY()-drawSizeY, super.getX()+drawSizeX, super.getY(), 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
    }
    @Override
	public void drawState(Graphics g) {

        // if statement handles pointing left or right
        if (direction){
            sx1 = IMAGE_WIDTH+(IMAGE_WIDTH*step);
            sx2 = IMAGE_WIDTH*step;
        }
        else {
            sx1 = IMAGE_WIDTH*step;
            sx2 = IMAGE_WIDTH+(IMAGE_WIDTH*step);
        }

        // don't draw the cat if it ran out of water or food
        if (water >0 && food > 0) {
            g.drawImage(SPRITE.getImage(), super.getX(), super.getY()-drawSizeY, super.getX()+drawSizeX, super.getY(), sx1, 0, sx2, IMAGE_HEIGHT, null);
        }

    }

    @Override
	public void grabbed() {
        grabbed = true;
    }
    @Override
	public void dropped() {
        grabbed = false;

        // don't allow the cat to be dropped onto the wall, move the cat down to the floor
        if (super.yPos < Room.floorHeight) {
			super.yPos = Room.floorHeight;
		}
    }

    public void setPetted(boolean petted) {
        this.petted = petted;
    }

    public double getFood() {
        return food;
    }
    public void setFood(double food) {
        this.food = food;

        // cap food between 0 and 100
        if (this.food > 100) {
            this.food = 100;
        }
        if (this.food < 0) {
            this.food = 0;
        }

        // persist change
        User.setCatFood(this.food);
    }
    public double getWater() {
        return water;
    }
    public void setWater(double water) {
        this.water = water;
        if (this.water < 0) {
            this.water = 0;
        }
        if (this.water > 100) {
            this.water = 100;
        }

        User.setCatWater(this.water);
    }
    @Override
	public boolean getGrabbed() {
        return grabbed;
    }
    public boolean getPetted() {
        return petted;
    }
    public int getSpeed() {
        return speed;
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
    public void mouseEntered(MouseEvent e) {
        petted = true;
    }
    public void mouseExited(MouseEvent e) {
        petted = false;
    }
}
