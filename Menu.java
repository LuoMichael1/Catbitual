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


public class Menu extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener{
    
    private JLabel message;
    private int messagePadding = 15;
    private Room room = new Room("Room", 0, 0);
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2);
    private Timer timer;

    //private Color bgColor = Color.WHITE;//new Color(254, 227, 232);
    private Color color1 = new Color(15, 15, 15);
    private Color color2 = new Color(243, 169, 169);

    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JPanel tasklist;
    private JPanel catinfo;

    public Menu() {
        
        //setBackground(bgColor);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        /*  message
        message = new JLabel("Good Morning, Human");
        message.setFont(FontMaker.loadFont("Assets/Fonts/RobotoMono-Bold.ttf", (float)(Main.height*0.09)));
        message.setForeground(color2);
        
        // center message
        message.setBorder(BorderFactory.createEmptyBorder(messagePadding,messagePadding,messagePadding,messagePadding));
        this.add(message, BorderLayout.NORTH);

        // update message
        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textEffects("good morning human");
                repaint();
            }}
        );
        timer.start();
        */

        // sidebar buttons
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        b1 = new JButton(new ImageIcon("Assets/Icons/pet.png"));
        b2 = new JButton(new ImageIcon("Assets/Icons/furniture.png"));
        b3 = new JButton(new ImageIcon("Assets/Icons/book.png"));
        b4 = new JButton(new ImageIcon("Assets/Icons/clipboard.png"));
        b5 = new JButton(new ImageIcon("Assets/Icons/fish.png"));

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);

        this.add(sideBar, BorderLayout.WEST);
        sideBar.add(b1);
        sideBar.add(b2);
        sideBar.add(b3);
        sideBar.add(b4);
        sideBar.add(b5);
        
        // popup menus in the center
        JLayeredPane center = new JLayeredPane();
        this.add(center, BorderLayout.CENTER);

        tasklist = new JPanel();
        tasklist.setLayout(new BoxLayout(tasklist, BoxLayout.Y_AXIS));
        tasklist.setBounds(Main.width/2-450-100, 100, 900, 1000);
        JLabel taskTitle = new JLabel("To-Do List");
        taskTitle.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.11)));
        tasklist.add(taskTitle);
        tasklist.setEnabled(false);
        tasklist.setVisible(false);
        center.add(tasklist, Integer.valueOf(1));

        //add all the tasks to the to-do list
        for (int i=0; i<15; i++) {
            tasklist.add(new JButton("Task: " + i));
        }


        catinfo = new JPanel();
        catinfo.setLayout(new BoxLayout(catinfo, BoxLayout.Y_AXIS));
        catinfo.setBounds(Main.width/2-450-100, 100, 900, 1000);
        JLabel catTitle = new JLabel("How is your Cat?");
        catTitle.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.11)));
        catinfo.add(catTitle);
        catinfo.add(new JLabel("Name: " + cat.getName()));
        catinfo.add(new JLabel("Food: " + cat.getFood()));
        catinfo.add(new JLabel("Water: " + cat.getWater()));
        catinfo.add(new JLabel("Happiness: " + cat.getHappiness()));
        catinfo.setEnabled(false);
        catinfo.setVisible(false);
        center.add(catinfo, Integer.valueOf(2));

        revalidate();
        repaint();
    }
    
    /*
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
    */

    
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
            cat.grabbed();
            repaint();
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (cat.getGrabbed()) {
            cat.dropped();
            repaint();
        }
        
    }
    public void mouseDragged(MouseEvent e) {
        if (cat.getGrabbed()) {
            cat.setPosition(e.getX()+100, e.getY()-100);
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
        // idea, check if the mouse moves over the cat, which then can be used to trigger a petting animation?????????
    }
    public void actionPerformed(ActionEvent e) {

        // there has to be a way to turn each popup menu into an object so that this mess of if statements can be turned into a loop
        if (e.getSource() == b1) {
            System.out.println("1");
        }
        else if (e.getSource() == b2) {
            System.out.println("2");
        }
        else if (e.getSource() == b3) {
            System.out.println("3");
        }
        else if (e.getSource() == b4) {
            System.out.println("4");

            if (tasklist.isVisible()) {
                tasklist.setEnabled(false);
                tasklist.setVisible(false);
            }
            else {
                tasklist.setEnabled(true);
                tasklist.setVisible(true);
            }
            repaint();
        }
        else if (e.getSource() == b5) {
            System.out.println("5");
            if (catinfo.isVisible()) {
                catinfo.setEnabled(false);
                catinfo.setVisible(false);
            }
            else {
                catinfo.setEnabled(true);
                catinfo.setVisible(true);
            }
            repaint();
        }
        else {
            System.out.println("Error?");
        }
    }

    
}
