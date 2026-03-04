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
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createMatteBorder((int)(30*Main.scaleX), (int)(25*Main.scaleX), (int)(25*Main.scaleX), (int)(25*Main.scaleX), new Color(0,0,0,0)));

        YearMonth current = YearMonth.now();

        for (int i = 0; i < 12; i++) {
            mainPanel.add(createMonthPanel(current.minusMonths(i)));
            mainPanel.add(Box.createVerticalStrut(20));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.WHITE);


        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
        this.revalidate();
    }

    private static JPanel createMonthPanel(YearMonth yearMonth) {

        JPanel monthPanel = new RoundedPanel();
        monthPanel.setLayout(new BorderLayout());
        monthPanel.setBackground(LIGHT_GRAY);
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
        monthPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // add the text showing which day of the week each columm represents
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        int col = 0;
        int row = 0;

        for (String day : days) {
            gbc.gridx = col++;
            gbc.gridy = row;

            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(FontMaker.p);
            label.setForeground(Color.GRAY);

            grid.add(label, gbc);
        }

        // add blank squares before the first day of a month
        int firstDay = yearMonth.atDay(1).getDayOfWeek().getValue(); // 1 = Monday
        int daysInMonth = yearMonth.lengthOfMonth();

        row = 1;
        col = 0;

        for (int i = 1; i < firstDay; i++) {
            gbc.gridx = col++;
            gbc.gridy = row;

            grid.add(new JLabel(""), gbc);
        }

        // add the days of the month
        LocalDate today = LocalDate.now();

        for (int day = 1; day <= daysInMonth; day++) {

            if (col == 7) {   // move to next row
                col = 0;
                row++;
            }

            gbc.gridx = col++;
            gbc.gridy = row;

            CalendarCell calendarCell = new CalendarCell(day);

            if (isToday(yearMonth, day, today)) {
                calendarCell.setToday(true);
            }

            calendarCell.setImage(new ImageIcon("Assets/Images/animatedcattrimed.png"));

            grid.add(calendarCell, gbc);
        }

        monthPanel.add(grid, BorderLayout.CENTER);
        return monthPanel;
    }

    
    private static boolean isToday(YearMonth ym, int day, LocalDate today) {
        return (ym.getYear() == today.getYear() && ym.getMonth() == today.getMonth() && day == today.getDayOfMonth());
    }
}
