// The gui part of the to-do list
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;

public class TaskMenu extends ClipMenu implements ActionListener{
    private JButton addButton = new JButton("+");
    //private JButton editButton = new JButton("Edit");
    private int SCROLLSPEED = (int)(8*Main.scaleY);
    private TaskList taskList = new TaskList();
    private JPanel scrollPanel = new JPanel();
    private JPanel scrollPaneloutside = new JPanel();
    private GridBagConstraints c = new GridBagConstraints();

    public TaskMenu(String title) {
        super(title);
        //super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JPanel header = new JPanel(new GridBagLayout());
        JLabel h1 = new JLabel(title);
        h1.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 15));
        header.setBackground(Color.WHITE);
        h1.setFont(FontMaker.h1);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 10;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.LINE_START;
        header.add(h1,c);

        addButton.addActionListener(this);
        addButton.setMargin(new Insets(5, 15, 5, 15)); // makes the button itself a little bigger
        addButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT); // make corners round
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        c.anchor = GridBagConstraints.CENTER;
        header.add(addButton,c);

        // add spacer so their a gap between the button and the right end of the panel
        JPanel spacer = new JPanel();
        spacer.setBackground(Color.WHITE);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        header.add(spacer,c);

        super.replaceheader(header);


        
        //super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add all the tasks to the to-do list;
        
        scrollPaneloutside.setLayout(new GridBagLayout());

        c.ipadx = 10;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        scrollPaneloutside.add(scrollPanel,c);
        
        scrollPanel.setBackground(Color.WHITE);
        //scrollPanel.setBorder(BorderFactory.createLineBorder(Color.red,7));
        scrollPanel.setBorder(BorderFactory.createMatteBorder(0,(int)(30*Main.scaleX),(int)(100*Main.scaleX),(int)(30*Main.scaleX),Color.white));
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        
        // adds the individual tasks to the menu
        TaskNode currentNode = taskList.getRoot();
        while(currentNode != null) {
            // create a jpanel to put the task in so that padding can be added
            JPanel j = new JPanel();
            j.setBorder(BorderFactory.createMatteBorder(4,0,0,0,Color.white));
            j.setOpaque(false);
            j.setLayout(new GridLayout(1,1));
            j.add(new Task(currentNode.getName(), currentNode.getEndTime(), currentNode.getPriority()));
            scrollPanel.add(j);

            currentNode = currentNode.next;
        }

        JScrollPane scrollPane = new JScrollPane(scrollPaneloutside);
        scrollPane.setBorder(null);
                
        // Make scrolling faster
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED); 

        this.add(scrollPane);
    }

    
    public void actionPerformed(ActionEvent e) {
        System.out.println("button pressed" + e);
        if (e.getSource() == addButton) {
            String[] test = new String[1];
            test[0] = "test1";
            taskList.add(new TaskNode(taskList.getCounterID(),test,1,false,false,LocalDateTime.now(), LocalDateTime.now()));
            taskList.incrementCounterID();


            JPanel j = new JPanel();
            j.setBorder(BorderFactory.createLineBorder(Color.white,2));
            j.setOpaque(false);
            j.setLayout(new GridLayout(1,1));
            j.add(new Task(taskList.getRoot().getName(), taskList.getRoot().getEndTime(), taskList.getRoot().getPriority()));
            scrollPanel.add(j,0);
            scrollPanel.revalidate();
            System.out.println("Added Task");

            taskList.savetoFile("Userdata/saved_tasklist.txt");
        }
    }


    
}
