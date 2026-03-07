import javax.swing.*;
import java.awt.*;

public class HungerBar extends JPanel {

    private int hunger = 80;   // current hunger
    private int maxHunger = 100;

    public void setHunger(int value) {
        hunger = Math.max(0, Math.min(maxHunger, value));
        repaint(); // redraw panel
    }

    public int getHunger() {
        return hunger;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // background (empty bar)
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        // hunger percentage
        double percent = hunger / (double) maxHunger;
        int fillWidth = (int)(width * percent);

        // hunger color
        if (percent > 0.6)
            g.setColor(new Color(0, 200, 0));  // green
        else if (percent > 0.3)
            g.setColor(Color.ORANGE);                  // medium
        else
            g.setColor(Color.RED);                     // low

        // filled portion
        g.fillRect(0, 0, fillWidth, height);

        // border
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        // text
        g.setColor(Color.WHITE);
        g.drawString("Hunger: " + hunger, 10, height - 8);
    }
}