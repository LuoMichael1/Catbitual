import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;
    
public class FocusScreen extends JPanel implements MouseListener, ActionListener{

    // have a button to pause and exit
    // maybe music and dark/minimal mode
    // need to have cat in the center

    private Timer timer;
    private int anicount = 0; // counts each time the timer fires (10 times a second)
    private int timecount = 0; // counts the seconds
    private int increment = 0;
    private JButton b1 = new JButton("Exit");
    private int option = 1;
    private JLabel time = new JLabel();
    private int delay = 100; // tenth of a second --> done this way to allow animations to use the same timer
    private Sound bgmusic;

    // total seconds the timer has run used to award fish coins
    private int focusSecondsElapsed = 0;


    public FocusScreen() {        
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        
        this.addMouseListener(this);

        // displays the current time focusing in the center of the screen
        time.setFont(FontMaker.big);
        time.setText(convertSeconds(timecount));
        time.setBorder(new EmptyBorder((int)(40*Main.scaleX)+Main.height/2, (int)(30*Main.scaleX), (int)(30*Main.scaleX), (int)(30*Main.scaleX)));
        this.add(time);
        this.repaint();


        JPanel timePanel = new JPanel();
        timePanel.setBackground(Style.transparent());
        timePanel.add(time);
        this.add(timePanel, BorderLayout.CENTER);


        ActionListener count = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // only advance the clock when the timer fires ten times (one second total)
                if (e.getSource() == timer) {
                    anicount++;
                    if (anicount >= 10) {
                        timecount = timecount + increment;
                        focusSecondsElapsed++;

                        // award one coin for every full minute of focus
                        if (focusSecondsElapsed > 0 && focusSecondsElapsed % 60 == 0) {
                            User.addCoins(1);
                            Menu.refreshCoins();
                        }
                        anicount = 0;
                    }
                }

                time.setText(convertSeconds(timecount));
                repaint();

                
                if (timecount < 0 && option == 1) {
                    timer.stop();
                    Main.showCard("rewardScreen");
                    bgmusic.stop();

                    if (User.sfxEnabled) {
                        new Sound("Assets/Sounds/meow" + ((int)(Math.random()*2)+1) + ".wav", 0);
                    }
                    
                }
                // the count actionlistener is primarly for the timer, but it also handles events from the exit button
                else if (e.getSource() == b1) {
                    timer.stop();
                    Main.showCard("rewardScreen");
                    bgmusic.stop();
                    
                    if (User.sfxEnabled) {
                        new Sound("Assets/Sounds/meow" + ((int)(Math.random()*2)+1) + ".wav", 0);
                    }
                }
            }
        };
        
        b1.addActionListener(count);
        b1.setFont(FontMaker.p);
        b1.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
        b1.setMargin(new Insets((int)(10*Main.scaleX), (int)(50*Main.scaleX), (int)(10*Main.scaleX),  (int)(50*Main.scaleX)));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Style.transparent());
        buttonPanel.add(b1);
        buttonPanel.setBorder(new EmptyBorder((int)(30*Main.scaleX), (int)(30*Main.scaleX), (int)(30*Main.scaleX), (int)(30*Main.scaleX)));

        this.add(buttonPanel, BorderLayout.SOUTH);
        timer = new Timer(delay, count);

        
    }

    // option 1 is count down from a time
    // option 2 is count up from zero until stopped
    public void option(int option) {
        this.option = option;
        focusSecondsElapsed = 0;
        if (option == 1) {
            increment = -1;
            timecount = 5;
        }
        else if (option == 2) {
            increment = 1;
            timecount = 0;
        }


        // starts a random song from the 4 available
        bgmusic = new Sound("Assets/Music/music" + ((int)(Math.random()*4)+1) + ".wav", -1);
        if (!User.musicEnabled) {
            bgmusic.stop();
        }
    }
    public void setTime(int seconds) {
        timecount = seconds;
        time.setText(convertSeconds(timecount));
        this.repaint();
    }
    
    public void startTimer() {
        timer.start();
    }
    private String convertSeconds(int seconds) {
        int hour = seconds / 3600;
        int min = (seconds % 3600) / 60;
        int sec = seconds % 60;

        // only show hour places if there are hours to show
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        } 
        else {
            return String.format("%02d:%02d", min, sec);
        }
    }
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawState(g);
    }
    

    static final int imgSizeX = 256;
    static final int imgSizeY = 154;
    private static int drawSizeX = (int)(256*Main.scaleY*2.5);
    private static int drawSizeY = (int)(150*Main.scaleY*2.5);
    private int animationStep = 0;
    private int sx1 = 0; 
    private int sx2 = 0;
    private int xPos = (int)(Main.width/2)-drawSizeX/2;
    private int yPos = (int)(Main.height/2);

    public void drawState(Graphics g) {
        animationStep++;
        if (animationStep >=6 ) {
            animationStep = 0;
        }
        
        sx1 = imgSizeX+(imgSizeX*animationStep);
        sx2 = imgSizeX*animationStep;

        g.drawImage(Cat.SPRITE.getImage(), xPos, yPos-drawSizeY, xPos+drawSizeX, yPos, sx1, 0, sx2, imgSizeY, null);        
    }
    
    
    public void actionPerformed(ActionEvent e) {
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
}
