// menu which allows user to purchase food and furniture for their cat!

import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.OverlayLayout;

public class PetStore extends JScrollPane implements ActionListener{
    
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private ArrayList<String> imageFile = new ArrayList<String>(); 
    private ArrayList<JLabel> priceLabels = new ArrayList<JLabel>();
    private double previewImageWidth = 0;
    private double previewImageHeight = 0;
    private double previewImageScale = 1;
    private int SCROLLSPEED = (int)(10*Main.scaleY);
    private Menu m;
    private JPanel scrollPanel = new JPanel();

    public PetStore(Menu m) {
        this.m = m;
        this.setBorder(null);
       
        
        // adds margin between each displayed item
        scrollPanel.setLayout(new GridLayout(0,2, (int)(15*Main.scaleX), (int)(15*Main.scaleX)));
        scrollPanel.setBackground(Style.bg1());
        
        // creates a small margin around the scrollable part of the menu
        scrollPanel.setBorder(BorderFactory.createMatteBorder(0,(int)(30*Main.scaleX),30,(int)(30*Main.scaleX),Style.bg1()));

        // Make scrolling faster
        this.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED);

        // ensure DB catalog and load items
        PetStoreDB fdb = null;
        ArrayList<PetStoreDB.FurnitureRecord> items = new ArrayList<>();
        try {
            fdb = new PetStoreDB();
            fdb.ensureCatalogFromFile(new File("Assets/Images/Furniture/Catalog.txt"));
            items = fdb.getAllItems();
        } catch (Exception e) {
            System.out.println("Furniture DB error: " + e);
        } finally {
            if (fdb != null) fdb.close();
        }

        // display items
        for (int i=0; i<items.size(); i++) {
            PetStoreDB.FurnitureRecord rec = items.get(i);

            JPanel j = new RoundedPanel();
            j.setLayout(new OverlayLayout(j));
            j.setBackground(Style.bg2());
            
            ImageIcon img = new ImageIcon("Assets/Images/Furniture/" + rec.filepath);
            images.add(img);

            // resize image so that they fit in the menu, while not being stretched
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
            p.setBackground(Style.transparent());
            p.setLayout(new GridBagLayout());

            RoundedPanel p2 = new RoundedPanel();
            p2.setMaximumSize(new Dimension(200, 50));
            p2.setBackground(Style.bg1());
            
            JLabel jl;
            if (rec.owned) {
                jl = new JLabel("Owned");
                jl.setForeground(Style.success()); // change font color to green when owned
            }
            else {
                jl = new JLabel(rec.price + " clams");
            }
            jl.setFont(FontMaker.p);
            p2.add(jl);
            priceLabels.add(jl);

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
            imageFile.add(rec.filepath);
            scrollPanel.add(j);
        }
        this.setViewportView(scrollPanel);
        revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        PetStoreDB fdb = null;
        try {
            fdb = new PetStoreDB();
            java.util.List<PetStoreDB.FurnitureRecord> items = fdb.getAllItems();
            for (int i=0; i<buttons.size(); i++) {
                if (buttons.get(i) == e.getSource()) {
                    PetStoreDB.FurnitureRecord rec = items.get(i);
                    ImageIcon img = images.get(i);
                    if (!rec.owned) {
                        // mark owned and add to room
                        fdb.markOwned(rec.id);
                        Furniture furn = new Furniture(img, rec.filepath, rec.id);
                        // default position
                        furn.setPosition(Main.width/2, Room.floorHeight);
                        Menu.addEntity(furn);
                        // update label
                        priceLabels.get(i).setText("Owned");
                        m.repaint();
                    } else {
                        // already owned: don't do anything
                        // actually change this so that it removes the entity from the room
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fdb != null) fdb.close();
        }
    }
}
