// menu which allows user to purchase food and furniture for their cat!

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PetStore extends JScrollPane implements ActionListener{
    
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private ArrayList<String> imageFile = new ArrayList<String>(); 
    private double previewImageWidth = 0;
    private double previewImageHeight = 0;
    private double previewImageScale = 1;
    private int SCROLLSPEED = (int)(8*Main.scaleY);
    private Menu m;
    private JPanel scrollPanel = new JPanel();

    public PetStore(Menu m) {
        this.m = m;

        // creates a small margin around the scrollable part of the menu
        this.setBorder(BorderFactory.createMatteBorder(0,(int)(30*Main.scaleX),(int)(30*Main.scaleX),(int)(30*Main.scaleX),Color.white));
        
        scrollPanel.setLayout(new GridLayout(0,2, (int)(10*Main.scaleX), (int)(10*Main.scaleX)));
        scrollPanel.setBackground(Color.WHITE);
        
        // Make scrolling faster
        this.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);

        // get image files
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

        // display the items
        for (int i=0; i<imageFile.size(); i++) {
            JPanel j = new RoundedPanel();
            j.setLayout(new BorderLayout());
            ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + imageFile.get(i));
            
            images.add(img);

            // resize image so that they are half the size of the menu, while not being stretched
            if (images.get(i).getIconWidth() > images.get(i).getIconHeight()) {
                previewImageScale = Main.width/6.5/images.get(i).getIconWidth();
            }
            else {
                previewImageScale = Main.width/6.5/images.get(i).getIconHeight();
            }
            previewImageWidth = images.get(i).getIconWidth()*previewImageScale;
            previewImageHeight = images.get(i).getIconHeight()*previewImageScale;

            JButton b = new JButton(new ImageIcon(images.get(i).getImage().getScaledInstance((int)previewImageWidth, (int)previewImageHeight, Image.SCALE_SMOOTH)));
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.addActionListener(this);

            j.setBackground(new Color(240, 240, 240));
            j.add(b, BorderLayout.CENTER);
            

            buttons.add(b);
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        for (int i=0; i<buttons.size(); i++) {
            if (buttons.get(i) == e.getSource()) {
                // create an entity in the room
                Furniture furn = new Furniture(images.get(i));
                Menu.addEntity(furn);
                m.repaint();
            }
        }
    }
}

