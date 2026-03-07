import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
        text.setBorder(new EmptyBorder(0,0,(int)(10*Main.scaleX),0));   // adds padding between title and component below
        this.add(text);
        

        // Time setter
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        timePanel.setBackground(Style.transparent());
        timePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timePanel.setBorder(new EmptyBorder(0,0,50,0));   // adds padding between time inputs and buttons

        hoursField = new JTextField("0", 2);
        minsField = new JTextField("20", 2);
        secsField = new JTextField("0", 2);

        hoursField.setFont(FontMaker.big);
        minsField.setFont(FontMaker.big);
        secsField.setFont(FontMaker.big);

        hoursField.setBorder(new EmptyBorder((int)(15*Main.scaleX), (int)(5*Main.scaleX), (int)(15*Main.scaleX), (int)(5*Main.scaleX)));
        minsField.setBorder(new EmptyBorder((int)(15*Main.scaleX), (int)(5*Main.scaleX), (int)(15*Main.scaleX), (int)(5*Main.scaleX)));
        secsField.setBorder(new EmptyBorder((int)(15*Main.scaleX), (int)(5*Main.scaleX), (int)(15*Main.scaleX), (int)(5*Main.scaleX)));

        // remove background or else you can see the corners
        hoursField.setBackground(Style.transparent());
        minsField.setBackground(Style.transparent());
        secsField.setBackground(Style.transparent());
        
        RoundedPanel hoursFieldPanel = new RoundedPanel();
        RoundedPanel minsFieldPanel = new RoundedPanel();
        RoundedPanel secsFieldPanel = new RoundedPanel();

        hoursFieldPanel.setBackground(Style.bg1());
        minsFieldPanel.setBackground(Style.bg1());
        secsFieldPanel.setBackground(Style.bg1());

        hoursFieldPanel.add(hoursField);
        minsFieldPanel.add(minsField);
        secsFieldPanel.add(secsField);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5); // spacing between components

        gbc.gridx = 0; 
        gbc.gridy = 0;
        timePanel.add(hoursFieldPanel, gbc);

        gbc.gridx = 1;
        JLabel colon1 = new JLabel(":");
        colon1.setFont(FontMaker.big);
        timePanel.add(colon1, gbc);

        gbc.gridx = 2;
        timePanel.add(minsFieldPanel, gbc);

        gbc.gridx = 3;
        JLabel colon2 = new JLabel(":");
        colon2.setFont(FontMaker.big);
        timePanel.add(colon2, gbc);

        gbc.gridx = 4;
        timePanel.add(secsFieldPanel, gbc);

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
