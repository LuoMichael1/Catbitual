import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PetStore extends JScrollPane implements ActionListener{
    
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private Menu m;
    private ArrayList<String> imageFile = new ArrayList<String>(); 

    public PetStore(Menu m) {
        
        this.setBorder(null);
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(0,2, 10, 10));
        scrollPanel.setBackground(Color.WHITE);
        
        this.m = m;

        try {
            Scanner filesc = new Scanner(new File("Assets/Images/Furniture/Catalog.txt"));

            while (filesc.hasNextLine()) {
                imageFile.add(filesc.nextLine());
            }
            filesc.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }   


        for (int i=0; i<imageFile.size(); i++) {
            JPanel j = new JPanel();
            ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + imageFile.get(i));
            images.add(img);
            JButton b = new JButton(new ImageIcon(images.get(i).getImage().getScaledInstance((int)(Main.width/5.0*Main.scaleX), (int)(Main.width/5.0*Main.scaleX), Image.SCALE_SMOOTH)));
            b.addActionListener(this);
            j.setBackground(new Color(240, 240, 240));
            j.add(b);
            
            buttons.add(b);
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("furn ?");
        for (int i=0; i<buttons.size(); i++) {
            if (buttons.get(i) == e.getSource()) {
                // create an entity in the room
                Furniture furn = new Furniture(images.get(i));
                Menu.addEntity(furn);
                m.repaint();//(getGraphics());
                System.out.println("furn created");
            }
        }
    }
}
