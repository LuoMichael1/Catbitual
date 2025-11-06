import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel{
    
    private JLabel message;
    private int messagePadding = 15;

    public Menu() {
        
        setBackground(new Color(15, 15, 15));
        this.setLayout(new BorderLayout());
        this.setFocusable(true);        



        // message
        message = new JLabel("Good Morning, Human");
        message.setFont(FontMaker.loadFont("Assets/Fonts/Helvetica-Bold.ttf", 75));
        message.setForeground(Color.white);
        
        // center image
        message.setBorder(BorderFactory.createEmptyBorder(messagePadding,messagePadding,messagePadding,messagePadding));

        this.add(message, BorderLayout.CENTER);
        
        

        revalidate();
        repaint();
    }
    
    public void paintComponent(Graphics g) {

        // enables antialiasing on the text
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}
