import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
    
public class FocusScreenSetup extends JPanel implements MouseListener, ActionListener{

    private FocusScreen fcs = new FocusScreen();
    private JButton b1;
    private JButton b2;

    public FocusScreenSetup() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setFocusable(true);        
        this.addMouseListener(this);
        this.setBackground(Style.translucent());
        this.setBorder(new EmptyBorder((int)(50*Main.scaleX), (int)(150*Main.scaleX), (int)(50*Main.scaleX), (int)(150*Main.scaleX)));


        JLabel text = new JLabel("How long are you focusing?");
        text.setFont(FontMaker.h1);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setBorder(new EmptyBorder(0,0,50,0));   // adds padding between title and component below
        this.add(text);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Style.transparent());

        b1 = new JButton("Start");
        b2 = new JButton("Free Focus");

        b1.setFont(FontMaker.h2);
        b2.setFont(FontMaker.h2);
        
        b1.setMargin(new Insets(0, 30, 0, 30));
        b2.setMargin(new Insets(0, 30, 0, 30));
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);

        //make buttons round
        b1.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        b2.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        buttonPanel.add(b1);
        buttonPanel.add(b2);

        this.add(buttonPanel);

        Main.addCard(fcs, "FocusScreen");
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


    public void actionPerformed(ActionEvent e) {

        // gets which button was pressed
        if (e.getSource() == b1) {
            fcs.option(1);
            fcs.setTime(5);
            fcs.startTimer();
            Main.showCard("FocusScreen");
        }
        else if (e.getSource() == b2) {
            fcs.option(2);
            fcs.setTime(0);
            fcs.startTimer();
            Main.showCard("FocusScreen");
        }
        
        
    }    
}
