// A Habit and task tracker with cats!!

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void print(String text) {
        System.out.println(text);
    }
    // Constants
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 30;

    static CardLayout cardLayout;
    static JPanel p = new JPanel(new CardLayout());

    public static void main(String[] args) {
        
        JFrame f = new JFrame("Catbitual :3");
        //ImageIcon test = new ImageIcon("images/favicon.png");
        //f.setIconImage(test.getImage());

        Menu menu = new Menu();

        f.add(p, BorderLayout.CENTER);
        addCard(menu, "Menu");
        

        f.setVisible(true);
        f.setSize(WIDTH, HEIGHT);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        cardLayout = (CardLayout) p.getLayout();
        showCard("Menu");
    }

    static public void nextCard() {
        cardLayout.next(p);}

    static public void showCard(String name) {
        cardLayout.show(p, name);}

    static public void addCard(JPanel jPanel, String name) {
        p.add(jPanel, name);}

    static public void removeCard(JPanel jPanel) {
        p.remove(jPanel);}

}