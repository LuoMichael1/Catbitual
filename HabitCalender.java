import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class HabitCalender extends JPanel{
    private static final Color LIGHT_GRAY = new Color(240, 240, 240);

    public HabitCalender() {
        this.setLayout(new BorderLayout());
        //SwingUtilities.invokeLater(() -> { });   remember to use this in plaes

        JPanel mainPanel = new RoundedPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createMatteBorder((int)(30*Main.scaleX), (int)(30*Main.scaleX), (int)(30*Main.scaleX), (int)(30*Main.scaleX), LIGHT_GRAY));

        YearMonth current = YearMonth.now();

        for (int i = 0; i < 12; i++) {
            mainPanel.add(createMonthPanel(current.minusMonths(i)));
            mainPanel.add(Box.createVerticalStrut(20));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
        this.revalidate();
    }

    private static JPanel createMonthPanel(YearMonth yearMonth) {

        JPanel monthPanel = new RoundedPanel();
        monthPanel.setLayout(new BorderLayout());
        monthPanel.setBackground(Color.WHITE);
        monthPanel.setBorder(new EmptyBorder(15, 20, 20, 20));
        //monthPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        // add the name of the month at the top of each month panel
        JLabel title = new JLabel(yearMonth.getMonth() + " " + yearMonth.getYear(), SwingConstants.CENTER);
        title.setFont(FontMaker.h3);
        title.setBorder(new EmptyBorder(0, 0, 15, 0));
        JPanel titlePanel = new JPanel();  // jpanel is used to left align the title (otherwise it would be centered)
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS)); 
        //titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titlePanel.add(title);
        titlePanel.setBackground(Color.WHITE);
        monthPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 7, 5, 5)); // 0 means only the need amount of rows are created, 7 because seven days per week
        grid.setBackground(Color.WHITE);

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(FontMaker.p);
            label.setForeground(Color.GRAY);
            grid.add(label);
        }

        int firstDay = yearMonth.atDay(1).getDayOfWeek().getValue(); // 1 = Monday
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int i = 1; i < firstDay; i++) {
            grid.add(new JLabel(""));
        }

        LocalDate today = LocalDate.now();

        for (int day = 1; day <= daysInMonth; day++) {

            final int currentDay = day;

            JButton dayButton = new JButton(String.valueOf(currentDay));
            dayButton.setFocusPainted(false);
            dayButton.setBorderPainted(false);
            dayButton.setBackground(Color.WHITE);
            //dayButton.setForeground(TEXT_COLOR);
            dayButton.setFont(FontMaker.p);

            // Hover effect
            dayButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    dayButton.setBackground(LIGHT_GRAY);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (!isToday(yearMonth, currentDay, today)) {
                        dayButton.setBackground(Color.WHITE);
                    }
                }
            });

            // Highlight today
            if (isToday(yearMonth, currentDay, today)) {
                dayButton.setBackground(LIGHT_GRAY);
            }

        grid.add(dayButton);
        }

        monthPanel.add(grid, BorderLayout.CENTER);
        return monthPanel;
    }

    private static boolean isToday(YearMonth ym, int day, LocalDate today) {
        return (ym.getYear() == today.getYear() && ym.getMonth() == today.getMonth() && day == today.getDayOfMonth());
    }
}
