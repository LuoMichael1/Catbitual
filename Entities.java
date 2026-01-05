//Entities

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class Entities {
    
    protected int xPos;
    protected int yPos;
    private int scale = 1;
    private int xSize;
    private int ySize;
    private String name;
    private ImageIcon image;

    public Entities(String name, int x, int y, int xSize, int ySize) {
        this.name = name;
        this.xSize = xSize;
        this.ySize = ySize;
        xPos = x;
        yPos = y;
    }
    public Entities(String name) {
        this.name = name;
        xPos = 0;
        yPos = 0;
        xSize = 100;
        ySize = 100;
    }
    public Entities(String name, int x, int y, int xSize, int ySize, ImageIcon img) {
        this.name = name;
        this.xSize = xSize;
        this.ySize = ySize;
        this.image = img;
        xPos = x;
        yPos = y;
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

    // method returns true if the point on the screen is within the displayed entitiy
    public boolean withinBounds(int x, int y) {
        //Main.print("" + x + " : " + y);
        return (x>(xPos-200) && x<xPos+200);
    }

    public void draw(Graphics g) {
        g.drawImage(image.getImage(), xPos, yPos, null);
    }


}