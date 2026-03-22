import java.awt.*;

public class HungerBar extends RoundedPanel {

    private double hunger = 100;   // current hunger
    private final int MAXHUNGER = 100;
    private final int RADIUS = 30;
    private final int HORIZONTAL_PADDING = 15;

    private int width;
    private int height = RADIUS;

    public HungerBar() {
        this.setPreferredSize(new Dimension(width, RADIUS));
        this.setBackground(Style.transparent());
    }

    public void setHunger(int value) {
        hunger = Math.max(0, Math.min(MAXHUNGER, value));
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        width = getWidth();

        // background (empty bar)
        g.setColor(Style.bg2());
        g.fillRoundRect(HORIZONTAL_PADDING, 0, width - 2*HORIZONTAL_PADDING, height, RADIUS, RADIUS);

        // calculate hunger percentage
        double percent = hunger / (double) MAXHUNGER;
        int fillWidth = (int)((width-HORIZONTAL_PADDING*2) * percent);

        // hunger bar color
        if (percent > 0.6)
            g.setColor(new Color(0, 200, 0));  // green
        else if (percent > 0.3)
            g.setColor(Color.ORANGE);          // medium
        else
            g.setColor(Color.RED);             // low

        // filled colored portion
        g.fillRoundRect(HORIZONTAL_PADDING, 0, fillWidth, height, RADIUS, RADIUS);


        
    }
}