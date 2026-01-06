import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon image = new ImageIcon("Assets/Images/blackcat.png");
    
    static final int sizeX = 256; // the width of the image file
    static final int sizeY = 256; // the height of the image file
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


    public void draw(Graphics g) {
        g.drawImage(image.getImage(), super.getX()-drawsizeX/2, super.getY(), super.getX()+drawsizeX/2, super.getY()+drawsizeY, 0, 0, sizeX, sizeX, null);
    }
    
    public boolean getGrabbed() {
        return grabbed;
    }

    public boolean withinBounds(int x, int y) {
        //Main.print("" + x + " : " + y);
        return (x>(xPos-drawsizeX/2) && x<xPos+drawsizeX/2 && y>(yPos-drawsizeY/2) && y<yPos+drawsizeY/2);  // the +/- 200 represent the offset of the cat image so that the image appears centred
    }
}
