import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

class CalendarCell extends RoundedPanel {

    //private final int day;
    private boolean isToday = false;
    private final JLabel dayLabel;
    private final JLabel imageLabel;

    private static final Color LIGHT_GRAY = new Color(240, 240, 240);

    public CalendarCell(int day) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        // the width of the cell is = (width of clip menu) - (gray margin around calenders) - (white margin of month calender) - (internal gaps between cells) - (width of scroll bar) then all divided by 7 for the seven days of the week
        int width = (Main.width/2 - (int)(50*Main.scaleX) - 50 - 60 - 7)/7;
        setPreferredSize(new Dimension(width, width)); // make each cell a square
        // Layered pane for stacking
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        // Image label (centered)
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Day number label (top-left)
        dayLabel = new JLabel(String.valueOf(day));
        dayLabel.setFont(FontMaker.p2);
        dayLabel.setForeground(Color.GRAY);

        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(dayLabel, Integer.valueOf(1));

        // Hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                //if (!isToday) setBorder(BorderFactory.createLineBorder(Color.gray, 3));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                //if (!isToday) setBorder(null);
            }
        });
    }

    public void setToday(boolean today) {
        this.isToday = today;
        if (today) setBackground(new Color(255, 234, 218));
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
        dayLabel.setBounds(5, 5, w - 10, 20);
    }
    
}