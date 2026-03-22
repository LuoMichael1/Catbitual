// displays important stats about the cat and user, allows user to feed cat
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

public class ScoreMenu extends JPanel implements ActionListener{
    
    private JLabel foodLabel = new JLabel();
    private JLabel waterLabel = new JLabel();
    private JLabel fishLabel = new JLabel();

    private JButton dryFoodButton = new JButton("Buy Dry Food (10 fish)");
    private JButton wetFoodButton = new JButton("Buy Wet Food (20 fish)");
    private JButton waterButton = new JButton("refill water (5 fish)");

    private Cat cat;
    private HungerBar hb;
    private WaterBar wb;

    public ScoreMenu(Cat cat) {
        this.cat = cat;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Style.bg1());

        fishLabel = new JLabel("Fish: " + User.coins);
        foodLabel = new JLabel("Food: " + cat.getFood());
        waterLabel = new JLabel("Water: " + cat.getWater());

        // water needs to be refilled almost daily -- reduced water from 100 by 1 every 20mins
        // food is less critical -- reduced by 1 every 35mins 

        
        fishLabel.setFont(FontMaker.p);
        foodLabel.setFont(FontMaker.p);
        waterLabel.setFont(FontMaker.p);
        
        this.add(fishLabel);
        this.add(foodLabel);
        hb = new HungerBar();
        hb.setHunger((int)cat.getFood());
        this.add(hb);

        this.add(waterLabel);
        wb = new WaterBar();
        wb.setWater((int)cat.getWater());
        this.add(wb);

        this.setBorder(BorderFactory.createMatteBorder(7,(int)(30*Main.scaleX),(int)(100*Main.scaleX),(int)(30*Main.scaleX),Style.bg1()));
        repaint();

        dryFoodButton.addActionListener(this);
        dryFoodButton.setFont(FontMaker.p);
        dryFoodButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        dryFoodButton.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));

        wetFoodButton.addActionListener(this);
        wetFoodButton.setFont(FontMaker.p);
        wetFoodButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        wetFoodButton.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));

        waterButton.addActionListener(this);
        waterButton.setFont(FontMaker.p);
        waterButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        waterButton.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));

        this.add(dryFoodButton);
        this.add(wetFoodButton);
        this.add(waterButton);


        
    }


    public void update() {
        // refresh coin display
        fishLabel.setText("Fish: " + User.coins);
        foodLabel.setText("Food: " + cat.getFood());
        waterLabel.setText("Water: " + cat.getWater());
        
        hb.setHunger((int)cat.getFood());
        wb.setWater((int)cat.getWater());
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        if (e.getSource() == dryFoodButton) {
            if (User.spendCoins(10)) {
                cat.setFood(cat.getFood()+30);
                update();
            }
        }
        else if (e.getSource() == wetFoodButton) {
            if (User.spendCoins(20)) {
                cat.setFood(cat.getFood()+75);
                update();
            }
        }
        else if (e.getSource() == waterButton) {
            if (User.spendCoins(5)) {
                cat.setWater(100);
                update();
            }
        }
        
    }

}
