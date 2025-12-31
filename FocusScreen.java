import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
    
public class FocusScreen extends JPanel implements MouseListener{
    // need to show time, 
    // have a button to pause and exit
    // maybe music and dark/minimal mode
    // need to have cat in the center

    private Timer timer;
    private int timecount;
    public FocusScreen() {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        JLabel time = new JLabel();
        time.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.1)));
        timecount = 5;  // 1200 - 20mins in seconds
        time.setText(convertSeconds(timecount));
        this.add(time);
        this.repaint();
        int delay = 1000; // 1 second
        ActionListener count = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                timecount = timecount-1;
                time.setText(convertSeconds(timecount));
                repaint();

                if (timecount <= 0) {
                    timer.stop();
                    Main.showCard("rewardScreen");
                }
            }
        };
        timer = new Timer(delay, count);
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
}
