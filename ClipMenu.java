// clipboard styled menus

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClipMenu extends JPanel{

    public ClipMenu(String title) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBounds(Main.width/2-450-100, 100, 900, 1000);
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
