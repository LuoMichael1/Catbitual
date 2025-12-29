// clipboard styled menus

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClipMenu extends JPanel{
    private int menuWidth = Main.width/2;
    private int temp_sidebar_width = 100;

    public ClipMenu(String title) {
        System.out.println("Width, Height: " + Main.width + " " + Main.height);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBounds((int)(Main.width*0.5)-menuWidth/2-temp_sidebar_width, 100, menuWidth, 1000);
        JLabel h1 = new JLabel(title);
        h1.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.11)));
        this.add(h1);
        this.setEnabled(false);
        this.setVisible(false);
    }

    public void toggleVisibility() {
        if (this.isVisible()) {
            this.setEnabled(false);
            this.setVisible(false);
        }
        else {
            this.setEnabled(true);
            this.setVisible(true);
        }
    }

}
