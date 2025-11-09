import java.awt.*;
import javax.swing.ImageIcon;

public class Cat extends Entities{
    
    static ImageIcon catImage = new ImageIcon("Assets/Images/blackcat2.png");
    static final int sizeX = 1024;
    static final int sizeY = 1024;
    

    public Cat (int xPos, int yPos) {
        super(xPos, yPos);
        System.out.println(catImage.getImageLoadStatus());
        
    }
    public void drawCat(Graphics g) {
        g.drawImage(catImage.getImage(), super.getxPos()-200, super.getyPos(), super.getxPos()+200, super.getyPos()+400, 0, 0, sizeX, sizeX, null);
    }
}
