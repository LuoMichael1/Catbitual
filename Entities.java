//Entities

public abstract class Entities {
    
    private int xPos;
    private int yPos;
    private int scale = 1;
    private int xSize;
    private int ySize;
    private String name;

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


    public void setX(int xPos) {
        this.xPos = xPos;
    }
    public void setY(int yPos) {
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
        Main.print("" + x + " : " + y);
        return (x>(xPos-200) && x<xPos+200);
    }



}