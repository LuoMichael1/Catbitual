import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PetStore extends JScrollPane implements ActionListener{
    
    private JButton[] buttons = new JButton[20];
    private ImageIcon[] images = new ImageIcon[20];
    private Menu m;

    public PetStore(Menu m) {
        
        this.setBorder(null);
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(0,2, 10, 10));
        scrollPanel.setBackground(Color.WHITE);
        
        this.m = m;


        for (int i=0; i<19; i++) {
            JPanel j = new JPanel();
            ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + i + ".png");
            images[i] = img;
            JButton b = new JButton(new ImageIcon(images[i].getImage().getScaledInstance((int)(Main.width/5.0*Main.scalex), (int)(Main.width/5.0*Main.scalex), Image.SCALE_SMOOTH)));
            b.addActionListener(this);
            j.setBackground(new Color(240, 240, 240));
            j.add(b);
            
            buttons[i] = b;
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("furn ?");
        for (int i=0; i<buttons.length; i++) {
            if (buttons[i] == e.getSource()) {
                // create an entity in the room
                Furniture furn = new Furniture(images[i]);
                Menu.addEntity(furn);
                m.repaint();//(getGraphics());
                System.out.println("furn created");
            }
        }
    }
}
