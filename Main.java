// A Habit and task tracker with cats!!

import javax.swing.*;
import java.awt.*;

public class Main {

    // Constants
    public static final int FPS = 30;
    static CardLayout cardLayout;
    static JPanel p = new JPanel(new CardLayout());

    static int width = 1;
    static int height = 1;

    public static void main(String[] args) {
        
        // gets the screen size of the current monitor in pixels
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        //print("" + width + " : " + height);

        JFrame f = new JFrame("Catbitual :3");
        f.setUndecorated(false);
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(Frame.MAXIMIZED_BOTH);
        

        //ImageIcon test = new ImageIcon("images/favicon.png");
        //f.setIconImage(test.getImage());

        Menu menu = new Menu();

        f.add(p, BorderLayout.CENTER);
        addCard(menu, "Menu");
        
        cardLayout = (CardLayout) p.getLayout();
        showCard("Menu");

        // menu will not show otherwise
        f.setVisible(true);

    }
    
    public static void print(String text) {
        System.out.println(text);
    }
    static public void nextCard() {
        cardLayout.next(p);}

    static public void showCard(String name) {
        cardLayout.show(p, name);
    }

    static public void addCard(JPanel jPanel, String name) {
        p.add(jPanel, name);}

    static public void removeCard(JPanel jPanel) {
        p.remove(jPanel);}

}