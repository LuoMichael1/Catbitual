import java.awt.*;
import javax.swing.ImageIcon;

// originally the room was supposed to be isometric and I anticipated the need to be able to move and scale it and the items inside it; thus necessitating the creation of this now mostly useless class
public class Room {
    
    static ImageIcon roomImage = new ImageIcon("Assets/Images/roombg.png");
    static final int SPRITE_WIDTH = 640;
    static final int SPRITE_HEIGHT = 360;
    static final int floorHeight = (int)(440*Main.scaleY);   // the distance from the top of the screen to the start of the floor in pixels

    // draws the room in the center of the screen
    public void drawRoom(Graphics g) {
        g.drawImage(roomImage.getImage(), 0, 0, Main.width, Main.height, 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT, null);
    }

    
}