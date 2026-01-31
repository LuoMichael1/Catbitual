import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon image = new ImageIcon("Assets/Images/blackcat.png");
    
    static final int imgSizeX = 256; // the width of the image file
    static final int imgSizeY = 256; // the height of the image file
    private static int drawSizeX = (int)(400*Main.scaleY);
    private static int drawSizeY = (int)(400*Main.scaleY);
    private boolean grabbed = false;


    public Furniture(String name, int x, int y, int xSize, int ySize) {
        super(name, x, y, xSize, ySize, drawSizeX, drawSizeX);
    }
    public Furniture() {
        super("test", 100, 100, imgSizeX, imgSizeY, drawSizeX, drawSizeX);
    }
    public Furniture(ImageIcon image) {
        super("test", 100, 100, imgSizeX, imgSizeY, drawSizeX, drawSizeY, image);
        this.image = image;
    }
    

    public void grabbed() {
        grabbed = true;
    }
    public void dropped() {
        grabbed = false;

        // don't allow the furniture to be dropped onto the wall, move it down to the floor
        if (super.yPos < Room.floorHeight) 
            super.yPos = Room.floorHeight;
    }


    public void draw(Graphics g) {
        g.drawImage(image.getImage(), super.getX(), super.getY()-drawSizeY, super.getX()+drawSizeX, super.getY(), 0, 0, imgSizeX, imgSizeY, null);
    }
    
    public boolean getGrabbed() {
        return grabbed;
    }
}
