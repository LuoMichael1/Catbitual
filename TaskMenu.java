
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TaskMenu extends ClipMenu{

    public TaskMenu(String title) {
        super(title);
        
        //add all the tasks to the to-do list;
        TaskList tk = new TaskList();
        TaskNode currentNode = tk.getRoot();

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
        
        while(currentNode != null) {
            // create a jpanel to put the task in so that padding can be added
            JPanel j = new JPanel();
            j.setBorder(BorderFactory.createLineBorder(Color.white,2));
            j.setOpaque(false);
            j.setLayout(new GridLayout(1,1));
            j.add(new Task(currentNode.getName(), currentNode.getEndTime(), currentNode.getPriority()));
            scrollPanel.add(j);
            
            currentNode = currentNode.next;
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
