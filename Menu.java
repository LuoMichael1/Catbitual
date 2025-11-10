import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel{
    
    private JLabel message;
    private int messagePadding = 15;
    private Room room = new Room(Main.width/2, Main.height/2, 1);
    private Cat cat = new Cat(Main.width/2, Main.height/2);
    private Timer timer;

    public Menu() {
        
        //setBackground(new Color(15, 15, 15));
        //setBackground(Color.YELLOW);
        setBackground(new Color(243, 169, 169));
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        

        //JPanel j = new JPanel();
        //j.setBackground(new Color(15, 15, 15));

        // message
        message = new JLabel("Good Morning, Human");
        message.setFont(FontMaker.loadFont("Assets/Fonts/RobotoMono-Bold.ttf", (float)(Main.height*0.09)));
        message.setForeground(new Color(254, 227, 232));
        
        // center image
        message.setBorder(BorderFactory.createEmptyBorder(messagePadding,messagePadding,messagePadding,messagePadding));
        this.add(message, BorderLayout.NORTH);


        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textEffects("good morning human");
                repaint();
            }}
        );
        timer.start();

        
        revalidate();
        repaint();
    }
    
    static final String validCharacters = "abcdefghijklmnopqrstuvwxyz!@#$%&?";
    
    private void textEffects (String text) {
        char[] newtext = text.toCharArray();
        for (int i=0; i<text.length(); i++) {
            if (Math.random()>0.7 && newtext[i] != ' ') {
                newtext[i] = validCharacters.charAt((int)(Math.random()*30));
            }
        }
        message.setText("" + String.valueOf(newtext));
    }
    



    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        room.drawRoom(g);
        cat.drawCat(g);

        // enables antialiasing on the text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    
}
