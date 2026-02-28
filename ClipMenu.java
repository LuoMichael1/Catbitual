// clipboard styled menus

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClipMenu extends RoundedPanel{
    public static int menuWidth = 0;
    private int temp_sidebar_width = 100;
    private int topOffSet = (int)(100*Main.scaleY);
    private JLabel h1;

    public ClipMenu(String title) {
        initializeSize();
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        
        h1 = new JLabel(title);
        h1.setFont(FontMaker.h1);
        h1.setBorder(BorderFactory.createMatteBorder(0,(int)(30*Main.scaleX),0,0, new Color(0,0,0,0)));
        this.add(h1, BorderLayout.NORTH);
        
        this.setEnabled(false);
        this.setVisible(false);
    }
    public void replaceheader(JPanel p) {
        this.add(p, BorderLayout.NORTH);
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

    public void initializeSize() {
        menuWidth = Main.width/2;
        this.setBounds((int)(Main.width*0.5)-menuWidth/2-temp_sidebar_width, topOffSet, menuWidth, Main.height-topOffSet);
    }

}
