// The main screen of the app

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Menu extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
    
    private JLabel message;
    private int messagePadding = 15;
    private Room room = new Room("Room", 0, 0);
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2);
    private Timer timer;

    private Color bgColor = new Color(254, 227, 232);
    private Color color1 = new Color(15, 15, 15);
    private Color color2 = new Color(243, 169, 169);

    public Menu() {
        
        setBackground(bgColor);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        JPanel j = new JPanel();
        j.setBackground(new Color(15, 15, 15));
        this.add(j, BorderLayout.EAST);

        // message
        message = new JLabel("Good Morning, Human");
        message.setFont(FontMaker.loadFont("Assets/Fonts/RobotoMono-Bold.ttf", (float)(Main.height*0.09)));
        message.setForeground(color2);
        
        // center image
        message.setBorder(BorderFactory.createEmptyBorder(messagePadding,messagePadding,messagePadding,messagePadding));
        this.add(message, BorderLayout.NORTH);


        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textEffects("good morning human");
                repaint();
            }}
        );
        timer.start();

        revalidate();
        repaint();
    }
    
    static final String validCharacters = "abcdefghijklmnopqrstuvwxyz!@#$%&?";
    
    private void textEffects (String text) {
        char[] newtext = text.toCharArray();
        for (int i=0; i<text.length(); i++) {
            if (Math.random()>0.7 && newtext[i] != ' ') {
                newtext[i] = validCharacters.charAt((int)(Math.random()*30));
            }
        }
        message.setText("" + String.valueOf(newtext));
    }
    

    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        room.drawRoom(g);
        cat.drawCat(g);

        // enables antialiasing on the text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }




    // Mouse Events
    
    public void mousePressed(MouseEvent e) {
        if (cat.withinBounds(e.getX(), e.getY()) && !cat.getGrabbed()) {
            Main.print("Cat Grabbed ----");
            cat.grabbed();
            repaint();
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (cat.getGrabbed()) {
            Main.print("Cat Dropped ----");
            cat.dropped();
            repaint();
        }
        
    }
    public void mouseDragged(MouseEvent e) {
        if (cat.getGrabbed()) {
            cat.setX(e.getX()+100);
            cat.setY(e.getY()-100);
            
            repaint();
        }
    }

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
    }

    
}
