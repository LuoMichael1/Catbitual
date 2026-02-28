import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreMenu extends JPanel{
    
    public ScoreMenu(Cat cat) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel("Name: " + cat.getName()));
        this.add(new JLabel("Food: " + cat.getFood()));
        this.add(new JLabel("Water: " + cat.getWater()));
        this.add(new JLabel("Happiness: " + cat.getHappiness()));
        this.add(new JLabel("TimeSpent: " + cat.getHappiness()));

        this.setBorder(BorderFactory.createMatteBorder(7,(int)(30*Main.scaleX),(int)(100*Main.scaleX),(int)(30*Main.scaleX),Color.white));
    }

}
