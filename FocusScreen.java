import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class FocusScreen extends JPanel implements MouseListener{
    // need to show time, 
    // have a button to pause and exit
    // maybe music and dark/minimal mode
    // need to have cat in the center

    public FocusScreen() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}
