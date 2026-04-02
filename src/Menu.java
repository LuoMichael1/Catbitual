// The main screen of the app
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener, ComponentListener{

    public static double scale = Main.height/1080.0;
    public static long lastMoveTime = System.currentTimeMillis();
    private static ScoreMenu scoreMenu;
    private Room room = new Room();
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2, this);


    private JButton[] sideButtons = new JButton[5];
    private ClipMenu[] clipMenus = new ClipMenu[5];
    private ClipMenu currentMenu = null;
    private int menuIndex = -1;
    private FocusScreenSetup prefocusScreen;


    // image icons for the sidebar buttons
    private ImageIcon pet = new ImageIcon(Menu.class.getResource("/Assets/Icons/pet.png"));
    private ImageIcon furniture = new ImageIcon(Menu.class.getResource("/Assets/Icons/furniture.png"));
    private ImageIcon book = new ImageIcon(Menu.class.getResource("/Assets/Icons/book.png"));
    private ImageIcon clipboard = new ImageIcon(Menu.class.getResource("/Assets/Icons/clipboard.png"));
    private ImageIcon fish = new ImageIcon(Menu.class.getResource("/Assets/Icons/fish.png"));
    private ImageIcon buttonIcons[] = {pet,furniture,book,clipboard,fish};

    private final int BUTTON_SIZE = 90;


    public Menu() {

        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);

        //set up the focus screen
        prefocusScreen = new FocusScreenSetup();
        Main.addCard(prefocusScreen, "prefocus");

        // sidebar buttons
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setOpaque(false);
        this.add(sideBar, BorderLayout.WEST);


        for (int i=0; i<sideButtons.length; i++) {
            sideButtons[i] = new JButton(new ImageIcon(buttonIcons[i].getImage().getScaledInstance((int)(BUTTON_SIZE*scale), (int)(BUTTON_SIZE*scale), Image.SCALE_SMOOTH)));

            // remove the gradient and border effect
            sideButtons[i].setContentAreaFilled(false);
            sideButtons[i].setBorderPainted(false);

            sideButtons[i].addActionListener(this);
            sideBar.add(sideButtons[i]);
        }

        // popup menus in the center
        JLayeredPane center = new JLayeredPane();
        this.add(center, BorderLayout.CENTER);

        // load user data
        new User();

        // transfer persisted cat values into the cat instance
        cat.setFood(User.catFood);
        cat.setWater(User.catWater);

        clipMenus[0] = new ClipMenu("focus");
        clipMenus[1] = new ClipMenu("Pet Store");
        clipMenus[2] = new HabitMenu("Habits");
        clipMenus[3] = new TaskMenu("To-Do List");
        clipMenus[4] = new ClipMenu("Catformation");

        for (int i=0; i<clipMenus.length; i++) {
            center.add(clipMenus[i], Integer.valueOf(i));
        }

        clipMenus[1].add(new PetStore(this, room));
        scoreMenu = new ScoreMenu(cat);
        clipMenus[4].add(scoreMenu);

        room.addEntity(cat);

        revalidate();
        repaint();
    }



    @Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        room.drawRoom(g);
    }


    // Mouse Events ------------------------------

    @Override
	public void mousePressed(MouseEvent e) {
        room.mousePressed(e);
        repaint();
    }
    @Override
	public void mouseReleased(MouseEvent e) {
        room.mouseReleased(e);
        repaint();
    }
    @Override
	public void mouseDragged(MouseEvent e) {
        room.mouseDragged(e);
        repaint();
    }
    @Override
	public void mouseClicked(MouseEvent e) {
        // if the mouse clicks outside of a menu while it is open, we should close the menu
        //System.out.println(e);
        if (currentMenu != null) {
            currentMenu.toggleVisibility();
            currentMenu = null;
            repaint();
        }
    }
    @Override
	public void mouseMoved(MouseEvent e) {
        // check if the mouse moves over the cat, which then can be used to trigger a petting animation
        // mouse hovering over the cat should not counting as petting, user must move mouse back and forth to pet it
        // 100ms margin, also a check in the catAI because mouseMoved is only triggered with movement, so a stationary mouse wont otherwise update the animation
        if (System.currentTimeMillis() - lastMoveTime < 100) {
            if (cat.withinBounds(e.getX(), e.getY()) && !cat.getGrabbed()) {
                cat.setPetted(true);
            }
            else{
                cat.setPetted(false);
            }
        }
        lastMoveTime = System.currentTimeMillis();
    }
    @Override
	public void mouseEntered(MouseEvent e) {
    }
    @Override
	public void mouseExited(MouseEvent e) {
    }



    @Override
	public void actionPerformed(ActionEvent e) {

        // gets which button was pressed
        for (int i=0; i<sideButtons.length; i++) {
            if (e.getSource() == sideButtons[i]) {
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

    @Override
	public void componentResized(ComponentEvent e) {
        Main.width = e.getComponent().getWidth();
        Main.height = e.getComponent().getHeight();
        System.out.println(Main.width);

        for (ClipMenu element : clipMenus) {
            element.initializeSize();
        }
        repaint();
    }



    public static void refreshCoins() {
        if (scoreMenu != null) {
            scoreMenu.update();
        }
    }

    @Override
	public void componentMoved(ComponentEvent e) {
    }
    @Override
	public void componentShown(ComponentEvent e) {
    }
    @Override
	public void componentHidden(ComponentEvent e) {
    }
    @Override
	public void keyTyped(KeyEvent e) {
    }
    @Override
	public void keyPressed(KeyEvent e) {
    }
    @Override
	public void keyReleased(KeyEvent e) {
    }
}

