// The gui part of the to-do list
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TaskMenu extends ClipMenu implements ActionListener{
    private JButton addButton = new JButton("+");
    private int SCROLLSPEED = (int)(8*Main.scaleY);
    private TaskList taskList = new TaskList();
    private JPanel scrollPanel = new JPanel();
    private JPanel scrollPaneloutside = new JPanel();

    public TaskMenu(String title) {
        super(title);
        //super.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        addButton.addActionListener(this);
        super.add(addButton);

        //super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add all the tasks to the to-do list;
        
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
        
        // adds the individual tasks to the menu
        TaskNode currentNode = taskList.getRoot();
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
                
        // Make scrolling faster
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLLSPEED); 

        JPanel footerContainer = new JPanel();
        footerContainer.setPreferredSize(new Dimension((int)(ClipMenu.menuWidth*0.9),100));
        footerContainer.setOpaque(false);
        footerContainer.setBorder(BorderFactory.createLineBorder(Color.white,10));  // used to create some margin around the footer
        scrollPanel.add(footerContainer);

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
