import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon image = new ImageIcon("Assets/Images/blackcat.png");
    
    static final int sizeX = 1024;
    static final int sizeY = 1024;
    private double scale = Main.height/1080.0;
    private int drawsizeX = (int)(400*scale);
    private int drawsizeY = (int)(400*scale);
    private boolean grabbed = false;


    public Furniture(String name, int x, int y, int xSize, int ySize) {
        super(name, x, y, xSize, ySize);
    }
    public Furniture() {
        super("test", 100, 100, sizeX, sizeY);
    }
    public Furniture(ImageIcon image) {
        super("test", 100, 100, sizeX, sizeY, image);
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


    public void drawCat(Graphics g) {
        g.drawImage(image.getImage(), super.getX()-drawsizeX/2, super.getY(), super.getX()+drawsizeX/2, super.getY()+drawsizeY, 0, 0, sizeX, sizeX, null);
    }
    
    public boolean getGrabbed() {
        return grabbed;
    }
}
