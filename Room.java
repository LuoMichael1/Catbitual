
import java.awt.*;

import javax.swing.ImageIcon;

public class Room extends Entities {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/isoroom.png");

    public Room (int xPos, int yPos, int scale) {
        super(xPos, yPos);

        
    }
    
    // draws the room in the center of the screen
    public void drawRoom(Graphics g) {
        g.drawImage(roomImage.getImage(), (Main.width-Main.height)/2, 0, (Main.width+Main.height)/2, Main.height, 0, 0, 2500, 2500, null);
    }

}