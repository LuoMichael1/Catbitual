
import java.awt.*;

import javax.swing.ImageIcon;

public class Room extends Entities {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/Isoroom.png");
    static final int roomSizeX = 1920;
    static final int roomSizeY = 1080;
    private double scale = 1;

    public Room (String name, int xPos, int yPos, double scale) {
        super(name, xPos, yPos);
        this.scale = scale;
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
        g.drawImage(roomImage.getImage(), (int)(Main.width-roomSizeX*scale)/2, 0, (int)(Main.width+roomSizeX*scale)/2, (int)(roomSizeY*scale), 0, 0, roomSizeX, roomSizeY, null);
    }

    
}