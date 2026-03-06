import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreMenu extends JPanel{
    
    private JLabel foodLabel = new JLabel();
    private JLabel waterLabel = new JLabel();
    private JLabel fishLabel = new JLabel();

    public ScoreMenu(Cat cat) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Style.bg1());

        
        foodLabel = new JLabel("Food: " + cat.getFood());
        waterLabel = new JLabel("Water: " + cat.getWater());
        fishLabel = new JLabel("Fish: " + User.coins);
        
        foodLabel.setFont(FontMaker.p);
        waterLabel.setFont(FontMaker.p);
        fishLabel.setFont(FontMaker.p);

        this.add(foodLabel);
        this.add(waterLabel);
        this.add(fishLabel);

        this.setBorder(BorderFactory.createMatteBorder(7,(int)(30*Main.scaleX),(int)(100*Main.scaleX),(int)(30*Main.scaleX),Style.bg1()));
        repaint();
    }


    public void update() {
        // refresh coin display
        fishLabel.setText("Fish: " + User.coins);
        repaint();
    }

}
