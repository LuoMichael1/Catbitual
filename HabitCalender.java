import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

// DB helper
import java.io.File;

public class HabitCalender extends JPanel{
    private JPanel mainPanel = new RoundedPanel();
    private JScrollPane scrollPane = new JScrollPane(mainPanel);
    private CalendarDB db;


    public HabitCalender() {
        this.setLayout(new BorderLayout());
        //SwingUtilities.invokeLater(() -> { });   remember to use this in plaes

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Style.bg1());
        mainPanel.setBorder(BorderFactory.createEmptyBorder((int)(30*Main.scaleX), (int)(25*Main.scaleX), (int)(25*Main.scaleX), (int)(25*Main.scaleX)));

        // ensure Userdata dir exists for DB
        File userDataDir = new File("Userdata");
        if (!userDataDir.exists()) userDataDir.mkdirs();

        try {
            db = new CalendarDB();
        } catch (SQLException e) {
            e.printStackTrace();
            db = null;
        }

        YearMonth current = YearMonth.now();

        for (int i = 0; i < 12; i++) {
            mainPanel.add(createMonthPanel(current.minusMonths(i)));
            mainPanel.add(Box.createVerticalStrut(20));
        }

        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Style.bg1());


        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
        this.revalidate();
    }

    private JPanel createMonthPanel(YearMonth yearMonth) {

        JPanel monthPanel = new RoundedPanel();
        monthPanel.setLayout(new BorderLayout());
        monthPanel.setBackground(Style.bg2());
        monthPanel.setBorder(new EmptyBorder(15, 20, 20, 20));

        // add the name of the month at the top of each month panel
        JLabel title = new JLabel(yearMonth.getMonth() + " " + yearMonth.getYear(), SwingConstants.CENTER);
        title.setFont(FontMaker.h3);
        title.setBorder(new EmptyBorder(0, 0, 15, 0));
        JPanel titlePanel = new JPanel();  // jpanel is used to left align the title (otherwise it would be centered)
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS)); 
        titlePanel.add(title);
        monthPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        int spacing = (int)(5*Main.scaleX);
        c.insets = new Insets(spacing, spacing, spacing, spacing);
        c.weightx = 1.0;
        c.weighty = 1.0;

        // add the text showing which day of the week each columm represents
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        int col = 0;
        int row = 0;

        for (String day : days) {
            c.gridx = col++;
            c.gridy = row;

            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(FontMaker.p);
            label.setForeground(Style.text2());

            grid.add(label, c);
        }

        // add blank squares before the first day of a month
        int firstDay = yearMonth.atDay(1).getDayOfWeek().getValue(); // 1 = Monday
        int daysInMonth = yearMonth.lengthOfMonth();

        row = 1;
        col = 0;

        for (int i = 1; i < firstDay; i++) {
            c.gridx = col++;
            c.gridy = row;

            grid.add(new JLabel(""), c);
        }

        // add the days of the month
        LocalDate today = LocalDate.now();

        for (int day = 1; day <= daysInMonth; day++) {

            if (col == 7) {   // move to next row
                col = 0;
                row++;
            }

            c.gridx = col++;
            c.gridy = row;

            LocalDate cellDate = yearMonth.atDay(day);
            CalendarCell calendarCell = new CalendarCell(cellDate);

            if (isToday(yearMonth, day, today)) {
                calendarCell.setToday(true);
            }

            // set image only if checked in; otherwise blank
            int width = (Main.width/2 - (int)(50*Main.scaleX) - 50 - (int)(60*Main.scaleX) - 7)/7;
            try {
                if (db != null && db.isCheckedIn(cellDate)) {
                    calendarCell.setImage(new ImageIcon(new ImageIcon("Assets/Images/paw_stamp.png").getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH)));
                } else {
                    calendarCell.setImage(null);
                }
            } catch (Exception e) {
                System.out.print("Could not read check-ins: " + e);
            }

            // allow clicking a cell to mark check-in for that date
            calendarCell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    LocalDate todayDate = LocalDate.now();
                    if (!cellDate.equals(todayDate)) {
                        JOptionPane.showMessageDialog(HabitCalender.this, "You can only check in for today.");
                        return;
                    }
                    try {
                        if (db != null) {

                            if (db.isCheckedIn(cellDate)) {
                                JOptionPane.showMessageDialog(HabitCalender.this, "You can only check once today.");
                            }
                            else {
                                db.markCheckIn(cellDate);
                                calendarCell.setImage(new ImageIcon(new ImageIcon("Assets/Images/paw_stamp.png").getImage().getScaledInstance(width, width, Image.SCALE_SMOOTH)));
                                // reward coins for checking in
                                User.addCoins(10);
                                Menu.refreshCoins();
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            grid.add(calendarCell, c);
        }

        monthPanel.add(grid, BorderLayout.CENTER);
        return monthPanel;
    }

    
    private static boolean isToday(YearMonth ym, int day, LocalDate today) {
        return (ym.getYear() == today.getYear() && ym.getMonth() == today.getMonth() && day == today.getDayOfMonth());
    }
}
