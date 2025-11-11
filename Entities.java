//Entities

public abstract class Entities {
    
    private int xPos;
    private int yPos;
    private int scale = 1;
    private int xSize;
    private int ySize;
    private String name;

    public Entities(String name, int x, int y) {
        this.name = name;
        xPos = x;
        yPos = y;
    }
    public Entities(String name) {
        this.name = name;
        xPos = 0;
        yPos = 0;
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



}