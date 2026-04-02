import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

class CalendarCell extends RoundedPanel {

    private final JLabel dayLabel;
    private final JLabel imageLabel;
    private final LocalDate date;

    public CalendarCell(LocalDate date) {
        this.date = date;
        setLayout(new BorderLayout());
        setBackground(Style.bg1());
        int width = (Main.width/2 - (int)(50*Main.scaleX) - 50 - (int)(60*Main.scaleX) - 7)/7;
        setPreferredSize(new Dimension(width, width)); // make each cell a square
        // Layered pane for stacking
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        // Image label (centered)
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Day number label (top-left)
        dayLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayLabel.setFont(FontMaker.p2);
        dayLabel.setForeground(Style.text2());

        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(dayLabel, Integer.valueOf(1));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setToday(boolean today) {
        if (today) {
            setBackground(Style.highlight());
        }
    }

    public void setImage(ImageIcon icon) {
        imageLabel.setIcon(icon);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int w = getWidth();
        int h = getHeight();

        for (Component comp : getComponents()) {
            comp.setBounds(0, 0, w, h);
        }

        imageLabel.setBounds(0, 0, w, h);
        dayLabel.setBounds((int)(9*Main.scaleX), (int)(9*Main.scaleX), w - 10, 20);
    }

}