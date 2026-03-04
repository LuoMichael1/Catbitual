import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon sprite;     
    static final int imgSizeX = 256; // the width of the image file
    static final int imgSizeY = 256; // the height of the image file
    private static double imageScaler = Main.scaleY;
    private boolean grabbed = false;
    private int dbId = -1;
    private String filepath = null;

    public Furniture(ImageIcon sprite) {
        super("furniture", 300, 500, (int)(sprite.getIconWidth()*imageScaler), (int)(sprite.getIconHeight()*imageScaler), sprite);
        this.sprite = sprite;
    }

    public Furniture(ImageIcon sprite, String filepath, int dbId) {
        super("furniture", 300, 500, (int)(sprite.getIconWidth()*imageScaler), (int)(sprite.getIconHeight()*imageScaler), sprite);
        this.sprite = sprite;
        this.filepath = filepath;
        this.dbId = dbId;
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
        g.drawImage(sprite.getImage(), super.getX(), super.getY()-(int)(sprite.getIconHeight()*imageScaler), super.getX()+(int)(sprite.getIconWidth()*imageScaler), super.getY(), 0, 0, sprite.getIconWidth(), sprite.getIconHeight(), null);
    }
    public void drawState(Graphics g) {
        g.drawImage(sprite.getImage(), super.getX(), super.getY()-(int)(sprite.getIconHeight()*imageScaler), super.getX()+(int)(sprite.getIconWidth()*imageScaler), super.getY(), 0, 0, sprite.getIconWidth(), sprite.getIconHeight(), null);
    }

    public boolean getGrabbed() {
        return grabbed;
    }

    public int getDbId() { return dbId; }
    public void setDbId(int id) { this.dbId = id; }
    public String getFilepath() { return filepath; }
    public void setFilepath(String path) { this.filepath = path; }
}
