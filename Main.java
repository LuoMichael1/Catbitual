// A Habit and task tracker with cats!!
import javax.swing.*;
import java.awt.*;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {

    // Constants
    public static final int FPS = 24;  // frames per second
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel p = new JPanel(cardLayout);

    public static int width = 1920;   // initial width of the display in pixels
    public static int height = 1080;   // initial height of the display in pixels

    // used to scale assets based on screen size
    // updated by the initializeScreenSize function
    // a value of one corresponds to 1920 x 1080p
    public static double scaleX = 1;
    public static double scaleY = 1;

    public static void main(String[] args) {
        
        // set look and feel
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        initializeScreenSize();

        // create the JFrame
        JFrame f = new JFrame("Catbitual");
        f.setUndecorated(false);
        f.setResizable(true);
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);         // makes the window full screen
        f.add(p, BorderLayout.CENTER);

        // Setting the app icon
        ImageIcon favicon = new ImageIcon("Assets/Images/favicon.png");
        f.setIconImage(favicon.getImage());

        // Creating JPanels
        Menu menu = new Menu();
        FocusScreenReward rewardScreen = new FocusScreenReward();

        addCard(menu, "Menu"); 
        addCard(rewardScreen, "rewardScreen");

        cardLayout = (CardLayout) p.getLayout();
        showCard("Menu");

        f.setVisible(true);   // menu will not show otherwise
    }

    public static void initializeScreenSize() {
        // gets the screen size of the current monitor in pixels
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();

        // set the scaling of elements based on the screen size assuming default is 1920 x 1080
        scaleX = width/1920.0;
        scaleY = height/1080.0;
    }

    // Custom CardLayout methods, makes things a little easier
    static public void nextCard() {
        cardLayout.next(p);}

    static public void showCard(String name) {
        cardLayout.show(p, name);}

    static public void addCard(JPanel jPanel, String name) {
        p.add(jPanel, name);}

    static public void removeCard(JPanel jPanel) {
        p.remove(jPanel);}
}