import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon image;// = new ImageIcon("Assets/Images/blackcat.png");
    
    static final int imgSizeX = 256; // the width of the image file
    static final int imgSizeY = 256; // the height of the image file
    private static int drawSizeX = (int)(600*Main.scaleY);
    private static int drawSizeY = (int)(600*Main.scaleY);
    private static double imageScaler = 1;
    private boolean grabbed = false;


    public Furniture(String name, int x, int y, int xSize, int ySize) {
        super(name, x, y, xSize, ySize, drawSizeX, drawSizeX);
    }
    public Furniture() {
        super("test", 100, 100, imgSizeX, imgSizeY, drawSizeX, drawSizeX);
    }
    public Furniture(ImageIcon image) {
        super("test", 300, 500, image.getIconWidth(), image.getIconHeight(), (int)(image.getIconWidth()*imageScaler), (int)(image.getIconHeight()*imageScaler), image);
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
        g.drawImage(image.getImage(), super.getX(), super.getY()-(int)(image.getIconHeight()*imageScaler), super.getX()+(int)(image.getIconWidth()*imageScaler), super.getY(), 0, 0, image.getIconWidth(), image.getIconHeight(), null);
    }
    public void drawState(Graphics g) {
        g.drawImage(image.getImage(), super.getX(), super.getY()-(int)(image.getIconHeight()*imageScaler), super.getX()+(int)(image.getIconWidth()*imageScaler), super.getY(), 0, 0, image.getIconWidth(), image.getIconHeight(), null);
    }

    public boolean getGrabbed() {
        return grabbed;
    }
}
