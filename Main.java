// A Habit and task tracker with cats!!


import javax.swing.*;
import java.awt.*;

public class Main {

    // Constants
    public static final int FPS = 24;
    static CardLayout cardLayout;
    static JPanel p = new JPanel(new CardLayout());

    static int width = 1080;   // initial width of the display in pixels
    static int height = 720;  // initial height of the display in pixels

    public static void main(String[] args) {
        
        // gets the screen size of the current monitor in pixels
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();


        JFrame f = new JFrame("Catbitual :3");
        f.setUndecorated(false);
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);         // makes the window full screen
        
        ImageIcon favicon = new ImageIcon("Assets/Images/favicon.png");
        f.setIconImage(favicon.getImage());

        Menu menu = new Menu();

        f.add(p, BorderLayout.CENTER);
        addCard(menu, "Menu");
        
        cardLayout = (CardLayout) p.getLayout();
        showCard("Menu");

        // menu will not show otherwise
        f.setVisible(true);

    }
    
    public static void print(String text) {
        System.out.println(text);}

    static public void nextCard() {
        cardLayout.next(p);}

    static public void showCard(String name) {
        cardLayout.show(p, name);}

    static public void addCard(JPanel jPanel, String name) {
        p.add(jPanel, name);}

    static public void removeCard(JPanel jPanel) {
        p.remove(jPanel);}

}