import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
    
public class FocusScreenSetup extends JPanel implements MouseListener, ActionListener{

    private FocusScreen fcs = new FocusScreen();
    private JButton b1;
    private JButton b2;
    private JTextField hoursField;
    private JTextField minsField;
    private JTextField secsField;

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
        

        // Time setter
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
        timePanel.setBackground(Style.transparent());
        timePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timePanel.setBorder(new EmptyBorder(0,0,50,0));   // adds padding between time inputs and buttons

        hoursField = new JTextField("0", 3);
        minsField = new JTextField("20", 3);
        secsField = new JTextField("0", 3);

        hoursField.setFont(FontMaker.big);
        minsField.setFont(FontMaker.big);
        secsField.setFont(FontMaker.big);

        timePanel.add(hoursField);
        timePanel.add(Box.createRigidArea(new Dimension(20,1)));
        timePanel.add(new JLabel(":"));
        timePanel.add(Box.createRigidArea(new Dimension(5,1)));
        timePanel.add(minsField);
        timePanel.add(Box.createRigidArea(new Dimension(20,1)));
        timePanel.add(new JLabel(":"));
        timePanel.add(Box.createRigidArea(new Dimension(5,1)));
        timePanel.add(secsField);

        this.add(timePanel);





        // start focus buttons

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Style.transparent());
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        b1 = new JButton("Start");
        b2 = new JButton("Free Focus");

        b1.setFont(FontMaker.p);
        b2.setFont(FontMaker.p);
        
        // button internal padding
        b1.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));
        b2.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));
        

        //make buttons round
        b1.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        b2.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        
       
        b1.addActionListener(this);
        b2.addActionListener(this);
        buttonPanel.add(b1);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,1)));  // button inbetween padding
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
            int totalSeconds = getValidatedTime();
            if (totalSeconds > 0) {
                fcs.option(1);
                fcs.setTime(totalSeconds);
                fcs.startTimer();
                Main.showCard("FocusScreen");
            }
        }
        else if (e.getSource() == b2) {
            fcs.option(2);
            fcs.setTime(0);
            fcs.startTimer();
            Main.showCard("FocusScreen");
        }
        
        
    }

    // gets time and ensures whats entered is valid
    private int getValidatedTime() {
        try {
            int hours = Integer.parseInt(hoursField.getText().trim());
            int mins = Integer.parseInt(minsField.getText().trim());
            int secs = Integer.parseInt(secsField.getText().trim());
            if (hours < 0 || mins < 0 || secs < 0 || mins > 60 || secs > 60) {
                return 0; // invalid
            }
            return hours * 3600 + mins * 60 + secs;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
