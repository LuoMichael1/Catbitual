import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class WaterBar extends RoundedPanel {

    private double water = 100;   // current water
    private final int MAXWATER = 100;
    private final int RADIUS = 30;
    private final int HORIZONTAL_PADDING = 15;

    private int width;
    private int height = RADIUS;

    public WaterBar() {
        this.setPreferredSize(new Dimension(width, RADIUS));
        this.setBackground(Style.transparent());
    }

    public void setWater(int value) {
        water = Math.max(0, Math.min(MAXWATER, value));
        repaint(); // redraw panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // enables antialiasing on the rectangles
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        width = getWidth();

        // background (empty bar)
        g.setColor(Style.bg2());
        g.fillRoundRect(HORIZONTAL_PADDING, 0, width - 2*HORIZONTAL_PADDING, height, RADIUS, RADIUS);

        // calculate water percentage
        double percent = water / MAXWATER;
        int fillWidth = (int)((width-HORIZONTAL_PADDING*2) * percent);

        // water bar color
        g.setColor(Color.BLUE);

        // filled colored portion
        g.fillRoundRect(HORIZONTAL_PADDING, 0, fillWidth, height, RADIUS, RADIUS);



    }
}