import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
    
public class FocusScreen extends JPanel implements MouseListener, ActionListener{
    // need to show time, 
    // have a button to pause and exit
    // maybe music and dark/minimal mode
    // need to have cat in the center

    private Timer timer;
    private int timecount;
    private int increment = 0;
    private JButton b1 = new JButton("End");
    private int option = 1;
    private JLabel time = new JLabel();

    public FocusScreen() {        
        
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        int delay = 1000; // 1 second
        
        time.setFont(FontMaker.h1);
        time.setText(convertSeconds(timecount));
        this.add(time);
        this.repaint();

        ActionListener count = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timecount = timecount + increment;
                time.setText(convertSeconds(timecount));
                repaint();

                if (timecount < 0 && option == 1) {
                    timer.stop();
                    Main.showCard("rewardScreen");
                }
                else if (e.getSource()==b1) {
                    timer.stop();
                    Main.showCard("rewardScreen");
                }
            }
        };
        
        b1.addActionListener(count);
        this.add(b1, BorderLayout.SOUTH);

        timer = new Timer(delay, count);
    }

    // option 1 is count down from a time
    // option 2 is count up from zero until stopped
    public void option(int option) {
        if (option == 1) {
            increment = -1;
            timecount = 5;
        }
        else if (option == 2) {
            increment = 1;
            timecount = 0;
        }
    }
    public void setTime(int seconds) {
        timecount = seconds;
        time.setText(convertSeconds(timecount));
        this.repaint();
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void startTimer() {
        timer.start();
    }
    private String convertSeconds(int seconds) {
        int min = seconds/60;
        int sec = seconds%60;
        return String.format("%d:%02d", min, sec);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  

        Cat.basicCatImage.paintIcon(null, g, 500, 100);

        // enables antialiasing on the text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    public void actionPerformed(ActionEvent e) {
    }
}
