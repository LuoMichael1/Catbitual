import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Furniture extends Entities{
    private ImageIcon sprite;     
    private static double imageScaler = Main.scaleY; // can add a coefficent to scaleY to resize furniture independently of the screen size
    private boolean grabbed = false;
    private int dbId = -1;
    private String filepath = null;
    private String type = ""; // furniture type from DB like carpet or walldeco

    public Furniture(ImageIcon sprite, String filepath, int dbId, String type) {
        super("furniture", 300, 500, (int)(sprite.getIconWidth()*imageScaler), (int)(sprite.getIconHeight()*imageScaler), sprite);
        this.sprite = sprite;
        this.filepath = filepath;
        this.dbId = dbId;
        this.type = (type == null) ? "" : type.toLowerCase();
    }

    public void grabbed() {
        grabbed = true;
    }
    public void dropped() {
        grabbed = false;

        // don't allow the furniture to be dropped onto the wall for most furniture —
        // but allow wall decorations to be placed on the wall
        if (!type.equals("walldeco")) {
            if (super.yPos < Room.floorHeight)
                super.yPos = Room.floorHeight;
        }
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

    public int getDbId() { 
        return dbId;
    }
    public void setDbId(int id) {
        this.dbId = id;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String path) {
        this.filepath = path;
    }
    public String getType() {
        return type;
    }
    public void setType(String t) {
        this.type = t.toLowerCase();
    }
}
