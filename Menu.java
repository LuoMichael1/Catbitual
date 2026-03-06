// The main screen of the app
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

public class Menu extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener, ComponentListener{
    
    private Room room = new Room();
    private Cat cat = new Cat("Cat", Main.width/2, Main.height/2, this);
    private static ArrayList<Entities> entities = new ArrayList<Entities>();
    private PetStoreDB petStoreDB = null;
    public static double scale = Main.height/1080.0;

    // single shared ScoreMenu instance so other classes can request an update
    private static ScoreMenu theScoreMenu;

    private JButton[] sideButtons = new JButton[6];
    private ClipMenu[] clipMenus = new ClipMenu[6];
    private ClipMenu currentMenu = null;
    private int menuIndex = -1;
    private FocusScreenSetup prefocusScreen;
    private Entities currentEntity = null;

    // image icons for the sidebar buttons
    private ImageIcon pet = new ImageIcon("Assets/Icons/pet.png");
    private ImageIcon furniture = new ImageIcon("Assets/Icons/furniture.png");
    private ImageIcon book = new ImageIcon("Assets/Icons/book.png");
    private ImageIcon clipboard = new ImageIcon("Assets/Icons/clipboard.png");
    private ImageIcon fish = new ImageIcon("Assets/Icons/fish.png");
    private ImageIcon settings = new ImageIcon("Assets/Icons/cog.png");

    private int buttonSize = 90;
    
    private ScoreMenu scoreMenu;

    public static void refreshScore() {
        if (theScoreMenu != null) {
            theScoreMenu.update();
        }
    }

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

        sideButtons[0] = new JButton(new ImageIcon(pet.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));
        sideButtons[1] = new JButton(new ImageIcon(furniture.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));
        sideButtons[2] = new JButton(new ImageIcon(book.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));
        sideButtons[3] = new JButton(new ImageIcon(clipboard.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));
        sideButtons[4] = new JButton(new ImageIcon(fish.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));
        sideButtons[5] = new JButton(new ImageIcon(settings.getImage().getScaledInstance((int)(buttonSize*scale), (int)(buttonSize*scale), Image.SCALE_SMOOTH)));

        for (int i=0; i<sideButtons.length; i++) {
            // remove the gradient and border effect
            sideButtons[i].setContentAreaFilled(false);
            sideButtons[i].setBorderPainted(false);

            sideButtons[i].addActionListener(this);
            sideBar.add(sideButtons[i]);
        }
        
        // popup menus in the center
        JLayeredPane center = new JLayeredPane();
        this.add(center, BorderLayout.CENTER);

        new User(); // load user data (coins)

        clipMenus[0] = new ClipMenu("Cat Info");
        clipMenus[1] = new ClipMenu("Pet Store");
        clipMenus[2] = new HabitMenu("Habits");
        clipMenus[3] = new TaskMenu("To-Do List");
        clipMenus[4] = new ClipMenu("Score");
        clipMenus[5] = new ClipMenu("Settings");

        for (int i=0; i<clipMenus.length; i++) {
            center.add(clipMenus[i], Integer.valueOf(i));
        }

        clipMenus[1].add(new PetStore(this));


        scoreMenu = new ScoreMenu(cat);
        theScoreMenu = scoreMenu;
        clipMenus[4].add(scoreMenu);
       
       
        entities.add(cat);

        // load owned furniture from DB
        try {
            petStoreDB = new PetStoreDB();
            ArrayList<PetStoreDB.FurnitureRecord> owned = petStoreDB.getOwnedItems();
            for (PetStoreDB.FurnitureRecord rec : owned) {
                try {
                    ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + rec.filepath);
                    Furniture f = new Furniture(img, rec.filepath, rec.id, rec.type);
                    if (rec.x >= 0 && rec.y >= 0) 
                        f.setPosition(rec.x, rec.y);
                    else 
                        f.setPosition(Main.width/2, Room.floorHeight);
                    entities.add(f);
                } catch (Exception ex) {
                    System.out.println("Could not load furniture image: " + rec.filepath + " -> " + ex);
                }
            }
        } catch (Exception e) {
            System.out.println("Furniture DB load error: " + e);
        }

        revalidate();
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        room.drawRoom(g);

        // Draw carpets and wall decorations first so they appear below other objects
        for (int i=0; i<entities.size(); i++) {
            Entities ent = entities.get(i);
            if (ent instanceof Furniture) {
                Furniture f = (Furniture) ent;
                String t = f.getType();
                if ("carpet".equals(t) || "walldeco".equals(t)) {
                    ent.drawState(g);
                }
            }
        }

        // Draw all other entities (including cat and regular furniture)
        for (int i=0; i<entities.size(); i++) {
            Entities ent = entities.get(i);
            if (ent instanceof Furniture) {
                Furniture f = (Furniture) ent;
                String t = f.getType();
                if ("carpet".equals(t) || "walldeco".equals(t)) continue;
            }
            ent.drawState(g);
        }

        // enables antialiasing on the text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    // Mouse Events
    public void mousePressed(MouseEvent e) {
        // count backward so that entities are drawn layered correctly
        for (int i=entities.size()-1; i>=0; i--) {
            if (entities.get(i).withinBounds(e.getX(), e.getY()) && !entities.get(i).getGrabbed() && currentEntity==null) {
                entities.get(i).grabbed();
                currentEntity = entities.get(i);
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        for (int i=0; i<entities.size(); i++) {
            if (entities.get(i).getGrabbed()) {
                entities.get(i).dropped();
                repaint();
                // make location of furniture persist using database
                if (entities.get(i) instanceof Furniture) {
                    Furniture f = (Furniture) entities.get(i);
                    int id = f.getDbId();
                    if (id != -1 && petStoreDB != null) {
                        try {
                            petStoreDB.updateLocation(id, f.getX(), f.getY());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        currentEntity = null;
    }
    private Entities temp;

    public void mouseDragged(MouseEvent e) {
        for (int i=0; i<entities.size(); i++) {
            if (entities.get(i).getGrabbed() && currentEntity==entities.get(i)) {
                entities.get(i).setPosition(e.getX()-entities.get(i).getDrawSize()/2, e.getY()+entities.get(i).getDrawSize()/2);
                repaint();
            }
            // the first item in the list should be the highest up (greatest y value)
            // the last item should be on top
            // do some bubble sort shanangins here
            // there is some sort of runtime error currently
            if (i>=1) {
                if (entities.get(i).getY() < entities.get(i-1).getY()) {
                    temp = entities.get(i);

                    entities.set(i, entities.get(i-1));
                    entities.set(i-1, temp); 
                }
            }
            else if (entities.size() > 1) {
                if (entities.get(i).getY() > entities.get(i+1).getY()) {
                    temp = entities.get(i);

                    entities.set(i, entities.get(i+1));
                    entities.set(i+1, temp); 
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
        // if the mouse clicks outside of a menu while it is open, we should close the menu
        //System.out.println(e);
        if (currentMenu != null) {
            currentMenu.toggleVisibility();
            currentMenu = null;
            repaint();
        }
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }


    

    public static long lastMoveTime = System.currentTimeMillis();
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

    public void actionPerformed(ActionEvent e) {

        // gets which button was pressed
        for (int i=0; i<sideButtons.length; i++) {
            if (e.getSource() == sideButtons[i]) {
                //System.out.println(i);
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

    public void componentResized(ComponentEvent e) {
        Main.width = e.getComponent().getWidth();
        Main.height = e.getComponent().getHeight();
        System.out.println(Main.width);

        for (int i=0; i<clipMenus.length; i++) {
            clipMenus[i].initializeSize();
        }

        repaint();
    }

    public void componentMoved(ComponentEvent e) {
    }
    public void componentShown(ComponentEvent e) {
    }
    public void componentHidden(ComponentEvent e) {
    }    

    public static void addEntity(Entities e) {
        entities.add(e);
    }

    // uses the id from the database
    public static Entities findEntity(int dbId) {
        for (Entities e : entities) {
            if (e instanceof Furniture) {  // because the entity array could have other types such as cat  - used https://www.baeldung.com/java-instanceof
                Furniture f = (Furniture) e;
                if (f.getDbId() == dbId) {
                    return f;
                }
            }
        }
        return null;
    }

    public static void removeEntity(Entities e) {
        entities.remove(e);
    }
}
