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
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener{
    
    //private JLabel message;
    //private int messagePadding = 15;
    private Room room = new Room("Room", 0, 0);
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2);
    //private Timer timer;

    //private Color bgColor = Color.WHITE;//new Color(254, 227, 232);
    //private Color color1 = new Color(15, 15, 15);
    //private Color color2 = new Color(243, 169, 169);

    private JButton[] sideButtons = new JButton[5];
    private ClipMenu[] clipMenus = new ClipMenu[5];
    private ClipMenu currentMenu = null;
    private int menuIndex = -1;
    private PreFocusScreen prefocusScreen;

    public Menu() {
        
        //setBackground(bgColor);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //set up the focus screen
        prefocusScreen = new PreFocusScreen();
        Main.addCard(prefocusScreen, "prefocus");
       
        // sidebar buttons
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        this.add(sideBar, BorderLayout.WEST);
        sideBar.setOpaque(false);

        sideButtons[0] = new JButton(new ImageIcon("Assets/Icons/pet.png"));
        sideButtons[1] = new JButton(new ImageIcon("Assets/Icons/furniture.png"));
        sideButtons[2] = new JButton(new ImageIcon("Assets/Icons/book.png"));
        sideButtons[3] = new JButton(new ImageIcon("Assets/Icons/clipboard.png"));
        sideButtons[4] = new JButton(new ImageIcon("Assets/Icons/fish.png"));

        for (int i=0; i<sideButtons.length; i++) {
            sideButtons[i].addActionListener(this);

            // remove the gradient and border effect
            sideButtons[i].setContentAreaFilled(false);
            sideButtons[i].setBorderPainted(false);

            sideBar.add(sideButtons[i]);
        }
        
        // popup menus in the center
        JLayeredPane center = new JLayeredPane();
        this.add(center, BorderLayout.CENTER);

        clipMenus[0] = new ClipMenu("Cat Info");
        clipMenus[1] = new ClipMenu("Pet Store");
        clipMenus[2] = new ClipMenu("Habits");
        clipMenus[3] = new ClipMenu("To-Do List");
        clipMenus[4] = new ClipMenu("Score");

        for (int i=0; i<clipMenus.length; i++) {
            center.add(clipMenus[i], Integer.valueOf(i));
        }


        //add all the tasks to the to-do list
        Scanner filesc;
        ArrayList<String> taskdata = new ArrayList<String>();
        
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        
        //scrollPane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        try {
            filesc = new Scanner(new File("Userdata/tasklist.txt"));
            
            while (filesc.hasNextLine()) {
                taskdata.add(filesc.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Could not read task file " + e);
        }
        System.out.print(taskdata.toString());
        
        for (int i=0; i<taskdata.size(); i++) {
            scrollPanel.add(new Task(taskdata.get(i), "Testing", 10, i));
        }
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        //scrollPane.add(scrollPanel);
        clipMenus[3].add(scrollPane);

        clipMenus[4].add(new JLabel("Name: " + cat.getName()));
        clipMenus[4].add(new JLabel("Food: " + cat.getFood()));
        clipMenus[4].add(new JLabel("Water: " + cat.getWater()));
        clipMenus[4].add(new JLabel("Happiness: " + cat.getHappiness()));

        revalidate();
        repaint();
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

        // gets which button was pressed
        for (int i=0; i<sideButtons.length; i++) {
            if (e.getSource() == sideButtons[i]) {
                System.out.println(i);
                menuIndex = i;
            }
        }
    
        // changes the menu based on the button that was pressed
        // currently we don't check if the action is from a button, but we should
        
        // check if there is currently a menu open
        if (menuIndex == 0) {
            Main.showCard("prefocus");
        }
        else if (currentMenu == null) {
            currentMenu = clipMenus[menuIndex];
            currentMenu.toggleVisibility();
            repaint();
        }
        // check if the menu opened was the same as the currently open menu, in this case it closes it
        else if (currentMenu == clipMenus[menuIndex]) {
            currentMenu.toggleVisibility();
            currentMenu = null;
            repaint();
        }
        // otherwise, open the menu and display it
        else {
            currentMenu.toggleVisibility();
            currentMenu = clipMenus[menuIndex];
            currentMenu.toggleVisibility();
            repaint();
        }
        
    }    
}
