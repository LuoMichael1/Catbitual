
import java.awt.*;

import javax.swing.ImageIcon;

public class Room extends Entities {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/isoroom1.png");
    static final int roomSizeX = 1920;
    static final int roomSizeY = 1080;

    public Room (int xPos, int yPos, int scale) {
        super(xPos, yPos);

        
    }
    
    // draws the room in the center of the screen
    public void drawRoom(Graphics g) {
        g.drawImage(roomImage.getImage(), (Main.width-roomSizeX)/2, 0, (Main.width+roomSizeX)/2, Main.height, 0, 0, roomSizeX, roomSizeY, null);
    }

}