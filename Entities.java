//Entities
import java.awt.Graphics;
import javax.swing.ImageIcon;

public abstract class Entities {
    
    // let the position refer to the bottom left corner of the entity
    protected int xPos;
    protected int yPos;
    private int scale = 1;
    private int imgSizeX;    //Width of the image file
    private int imgSizeY;    //Height of the image file
    private int drawSizeX;   //Width of displayed entity
    private int drawSizeY;   //Height of displayed entity
    private String name;
    private ImageIcon image;
    private boolean grabbed = false;

    public Entities(String name, int x, int y, int imgSizeX, int imgSizeY, int drawSizeX, int drawSizeY) {
        this.name = name;
        this.imgSizeX = imgSizeX;
        this.imgSizeY = imgSizeY;
        this.drawSizeX = drawSizeX;
        this.drawSizeY = drawSizeY;
        xPos = x;
        yPos = y;
    }

    public Entities(String name, int x, int y, int xSize, int ySize, int drawSizeX, int drawSizeY, ImageIcon img) {
        this.name = name;
        this.imgSizeX = xSize;
        this.imgSizeY = ySize;
        this.drawSizeX = drawSizeX;
        this.drawSizeY = drawSizeY;
        this.image = img;
        xPos = x;
        yPos = y;
    }

    public void grabbed() {
        grabbed = true;
    }
    public void dropped() {
        grabbed = false;
    }
    public boolean getGrabbed() {
        return grabbed;
    }
    public void setX(int xPos) {
        this.xPos = xPos;
    }
    public void setY(int yPos) {
        this.yPos = yPos;
    }
    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public int getX() {
        return xPos;
    }
    public int getY() {
        return yPos;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void moveTo(int xPos, int yPos) {
        
    }
    public void adjustScale() {
        
    }

    // returns true if the point on the screen is within the displayed entity
    public boolean withinBounds(int x, int y) {
        return (x>(xPos) && x<xPos+drawSizeX && y>(yPos-drawSizeY) && y<yPos);  
    }

    public void draw(Graphics g) {
        g.drawImage(image.getImage(), xPos, yPos, null);
    }

    public int getDrawSize() {
        return drawSizeX;
    }

}