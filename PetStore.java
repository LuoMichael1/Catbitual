// menu which allows user to purchase food and furniture for their cat!

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;

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
        this.setBorder(null);
       
        
        // adds margin between each displayed item
        scrollPanel.setLayout(new GridLayout(0,2, (int)(15*Main.scaleX), (int)(15*Main.scaleX)));
        scrollPanel.setBackground(Color.WHITE);
        
        // creates a small margin around the scrollable part of the menu
        scrollPanel.setBorder(BorderFactory.createMatteBorder(0,(int)(30*Main.scaleX),30,(int)(30*Main.scaleX),Color.white));

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

        // display items
        for (int i=0; i<imageFile.size(); i++) {
            JPanel j = new RoundedPanel();
            j.setLayout(new OverlayLayout(j));
            j.setBackground(new Color(240, 240, 240));
            
            ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + imageFile.get(i));
            
            images.add(img);

            // resize image so that they fit in the menu, while not being stretched
            // to achieve this, we scale based on the length of the longest side
            if (images.get(i).getIconWidth() > images.get(i).getIconHeight()) {
                previewImageScale = Main.width/6.5/images.get(i).getIconWidth();
            }
            else {
                previewImageScale = Main.width/6.5/images.get(i).getIconHeight();
            }
            previewImageWidth = images.get(i).getIconWidth()*previewImageScale;
            previewImageHeight = images.get(i).getIconHeight()*previewImageScale;

            // makes clicking on a display functional
            JPanel bp = new RoundedPanel();
            bp.setLayout(new BorderLayout());
            JButton b = new JButton(new ImageIcon(images.get(i).getImage().getScaledInstance((int)previewImageWidth, (int)previewImageHeight, Image.SCALE_SMOOTH)));
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.addActionListener(this);
            bp.add(b);
           
            // display prices
            RoundedPanel p = new RoundedPanel();
            p.setAlignmentX(1.0f);  // left
            p.setAlignmentY(0.0f);  // bottom
            p.setBackground(new Color(0,0,0,0));
            //p.setBackground(Color.YELLOW);
            p.setLayout(new GridBagLayout());

            RoundedPanel p2 = new RoundedPanel();
            p2.setMaximumSize(new Dimension(200, 50));
            //p2.setBorder(BorderFactory.createMatteBorder((int)(30*Main.scaleX),(int)(30*Main.scaleX),(int)(100*Main.scaleX),(int)(30*Main.scaleX), Color.blue));//new Color(0,0,0,0)));
            p2.setBackground(Color.WHITE);
            //p.add(p2);
            
            JLabel jl = new JLabel("100 clams");
            jl.setFont(FontMaker.p);
            p2.add(jl);

            // GridBagConstraints to anchor bottom-left
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.anchor = GridBagConstraints.LAST_LINE_START; // left alignment
            g.fill = GridBagConstraints.NONE;   // allow resizing horizontally
            g.weightx = 1; 
            g.weighty = 1;                         
            g.ipadx = 30;
            g.insets = new Insets(15, 15, 15, 15); // nudges the price to the bottom left corner
            p.add(p2, g);
            
            j.add(p);  // add overlay FIRST
            j.add(bp);  // add image SECOND
            
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

