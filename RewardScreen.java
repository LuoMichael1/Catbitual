import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
    
public class RewardScreen extends JPanel implements MouseListener{

    public RewardScreen() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        JLabel text = new JLabel("Yippie, time for a break");
        text.setFont(FontMaker.h1);
        this.add(text);
    }

    public void mouseClicked(MouseEvent e) {
        Main.showCard("Menu");
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  

        // enables antialiasing on the text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}
