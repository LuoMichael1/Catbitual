import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Menu extends JPanel{
    
    private JLabel message;
    
    public Menu() {
        setBackground(new Color(10, 10, 10));

        this.setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();

        layeredPane.setPreferredSize(new Dimension(1280, 720));

        this.setFocusable(true);
        
        
        message = new JLabel("Hello :D");
        message.setFont(loadFont("test", 12));
        message.setBounds(Main.WIDTH / 2 - (100), Main.HEIGHT - 70, 200, 10);
        
        // message
        this.add(message);
    }
    
    public static Font loadFont(String path, float size) {
        Font font = null;
        try {
            font = Font.createFont(0, new File(path)).deriveFont(size);
        } catch (Exception e) {
            System.out.println("Couldn't get font");
        }
        return font;
    }
}
