import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
    
public class PreFocusScreen extends JPanel implements MouseListener, ActionListener{

    private FocusScreen fcs;
    private JButton b1;
    private JButton b2;

    public PreFocusScreen() {
        //this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);
    
        JLabel text = new JLabel("How long are you focusing?");
        text.setFont(FontMaker.h1);
        this.add(text);
        
        b1 = new JButton("5 sec");
        b2 = new JButton("Free Focus");
        
        //make buttons round
        b1.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        b2.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        this.add(b1);
        this.add(b2);
    }

    public void mouseClicked(MouseEvent e) {
        Main.showCard("FocusScreen");
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


    public void actionPerformed(ActionEvent e) {

        // gets which button was pressed
        if (e.getSource() == b1) {
            fcs = new FocusScreen(1);
            Main.addCard(fcs, "FocusScreen");
            fcs.startTimer();
            Main.showCard("FocusScreen");
        }
        else if (e.getSource() == b2) {
            fcs = new FocusScreen(2);
            Main.addCard(fcs, "FocusScreen");
            fcs.startTimer();
            Main.showCard("FocusScreen");
        }
        
    }    
}
