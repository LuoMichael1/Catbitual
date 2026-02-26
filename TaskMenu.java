
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TaskMenu extends ClipMenu{

    public TaskMenu(String title) {
        super(title);
        
        //add all the tasks to the to-do list
        Scanner filesc;
        ArrayList<String[]> taskdata = new ArrayList<String[]>();
        
        JPanel scrollPanel = new JPanel();
        JPanel scrollPaneloutside = new JPanel();
        scrollPaneloutside.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        scrollPaneloutside.add(scrollPanel,c);
        
        c.weightx = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.VERTICAL;
        JPanel spacer = new JPanel();
        spacer.setBackground(Color.WHITE);
        scrollPaneloutside.add(spacer, c);

        c.gridx = 2;
        spacer = new JPanel();
        spacer.setBackground(Color.WHITE);
        scrollPaneloutside.add(spacer, c);

        
        scrollPanel.setBackground(Color.WHITE);
        scrollPanel.setBorder(BorderFactory.createLineBorder(Color.white,7));
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));

        //scrollPane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        try {
            filesc = new Scanner(new File("Userdata/tasklist_Old.txt"));
            //int counter = 0;
            while (filesc.hasNextLine()) {
                //counter++;
                //System.out.println("Count: " + counter);
                int temp = filesc.nextInt();
                filesc.nextLine();
                String[] tasks = new String[temp];

                for (int i=0; i<temp; i++) {
                    tasks[i] = (filesc.nextLine());
                }
                taskdata.add(tasks);
            }
        } catch (Exception e) {
            System.out.println("Could not read task file " + e);
        }
        //System.out.print(taskdata.toString());
        
        for (int i=0; i<taskdata.size(); i++) {
            // create a jpanel to put the task in so that padding can be added
            JPanel j = new JPanel();
            j.setBorder(BorderFactory.createLineBorder(Color.white,2));
            j.setOpaque(false);
            j.setLayout(new GridLayout(1,1));
            j.add(new Task(taskdata.get(i), "Testing", 10, i));
            scrollPanel.add(j);
        }
        JScrollPane scrollPane = new JScrollPane(scrollPaneloutside);
        scrollPane.setBorder(null);

        JPanel footerContainer = new JPanel();
        RoundedPanel footer = new RoundedPanel();
        footer.setPreferredSize(new Dimension((int)(ClipMenu.menuWidth*0.8),200));
        footerContainer.add(footer);
        footerContainer.setOpaque(false);
        footerContainer.setBorder(BorderFactory.createLineBorder(Color.white,10));
        scrollPanel.add(footerContainer);

        this.add(scrollPane);
    }
    
}
