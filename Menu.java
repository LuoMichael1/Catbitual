// The main screen of the app from which the functionality of the app is accessed

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Menu extends JPanel implements MouseListener, KeyListener, ComponentListener{
    
    private JLabel message;
    private int messagePadding = 15;
    private Room room = new Room("Room", Main.width/2, Main.height/2);
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2);
    private Timer timer;

    public Menu() {
    
        setBackground(new Color(243, 169, 169));
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        JPanel j = new JPanel();
        j.setBackground(new Color(15, 15, 15));
        this.add(j, BorderLayout.EAST);

        // message
        message = new JLabel("Good Morning, Human");
        message.setFont(FontMaker.loadFont("Assets/Fonts/RobotoMono-Bold.ttf", (float)(Main.height*0.09)));
        message.setForeground(new Color(254, 227, 232));
        
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





    public void componentResized(ComponentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'componentResized'");
    }

    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'componentMoved'");
    }

    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'componentShown'");
    }

    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'componentHidden'");
    }

    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    public void mouseClicked(MouseEvent e) {
        //Main.print("testClicked1: " + e);
        if (e.getX() >= cat.getX()) {
            //Main.print("testClicked2: " + e);
            //cat.setX(e.getX());
            //repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
        Main.print("Outside: " + e);
        if (cat.withinBounds(e.getX(), e.getY())) {
            Main.print("Inside: " + e);
            cat.setX(e.getX());
            cat.setY(e.getY());
            repaint();
        }
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
        Main.print("hiii mouse entered");
    }

    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
    
}
