import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
    
public class FocusScreenReward extends JPanel implements MouseListener{

    public FocusScreenReward() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        JLabel text = new JLabel("Yippiee, time for a break", SwingConstants.CENTER);
        text.setFont(FontMaker.h1);
        this.add(text, BorderLayout.CENTER);
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
