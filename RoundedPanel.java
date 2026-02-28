// Used this source to help achieve the rounded corners: https://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency
import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 40;
    private boolean drawOutline = false;

    public RoundedPanel() {
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
        setOpaque(false);
    }
    public RoundedPanel(int radius, boolean drawOutline) {
        this.cornerRadius = radius;
        this.drawOutline = drawOutline;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        
        if (drawOutline) {
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        }

        g2.dispose();
    }
}
