
import java.awt.*;

import javax.swing.ImageIcon;

public class Room extends Entities {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/roombg2.png");
    static final int roomSizeX = 1920;
    static final int roomSizeY = 1080;

    static final int floorHeight = 310;   // the distance from the top of the screen to the start of the floor in pixels

    public Room (String name, int xPos, int yPos) {
        super(name, xPos, yPos, roomSizeX, roomSizeY);
        //this.scale = Main.height/((double)roomSizeY);
        //System.out.println("scale: " + scale);
        System.out.println("Room: " + roomImage.getImageLoadStatus());
    }
    
    public void addEntity(Entities entity) {

    }
    public void removeEntity(){
        
    }
    public void removeEntity(int pos){
        
    }
    public void setX(int x){
        
    }
    public void setY(int y){
        
    }

    // draws the room in the center of the screen
    public void drawRoom(Graphics g) {
        g.drawImage(roomImage.getImage(), 0, 0, Main.width, Main.height, 0, 0, roomSizeX, roomSizeY, null);
    }

    
}