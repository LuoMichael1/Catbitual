//Entities

public abstract class Entities {
    
    private int xPos;
    private int yPos;

    public Entities(int x, int y) {
        xPos = x;
        yPos = y;
    }
    public Entities() {
        xPos = 0;
        yPos = 0;
    }



    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void moveTo(int xPos, int yPos) {
        
    }



}