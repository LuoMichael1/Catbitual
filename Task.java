import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Task extends RoundedPanel implements MouseListener, MouseMotionListener, ActionListener, FocusListener{

    private TaskMenu taskmenu;
    private TaskNode data;
    private JPanel j;

    private final Color BACK_GROUND_COLOR = new Color(240, 240, 240);

    //private JLabel titleLabel;
    //private JTextField titleLabel;
    private ArrayList<JTextField> titleLabel = new ArrayList<JTextField>();

    private int maxCharacterLength = 15;  // limits how short a task title can appear, lower limits allow for thinner screens

    public Task(TaskNode data, TaskMenu taskmenu, JPanel j) {
        super(40, false);
        this.data = data;
        this.taskmenu = taskmenu; // dependency injection?
        this.j = j;
        
        this.setFocusable(true);
        this.setVisible(true);
        this.setBackground(BACK_GROUND_COLOR);
        this.setLayout(new GridBagLayout());

        initializeGraphics();
        repaint();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void initializeGraphics() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 3;
        JPanel j = new JPanel();
        j.setOpaque(false);
        this.add(j, c);

        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 1;
        j = new JPanel();
        j.setOpaque(false);
        this.add(j, c);

        JCheckBox cb = new JCheckBox();
        c.gridx = 1;
        c.weightx = 0.0;
        c.ipadx = 10;
        this.add(cb, c);
        c.ipadx = 0;
        c.ipady = 0;

        titleLabel.add(new JTextField(data.getName()[0], maxCharacterLength));
        titleLabel.get(0).setFont(FontMaker.p);
        titleLabel.get(0).setBorder(javax.swing.BorderFactory.createEmptyBorder());
        titleLabel.get(0).setBackground(BACK_GROUND_COLOR);
        titleLabel.get(0).addActionListener(this);
        titleLabel.get(0).addFocusListener(this);
        c.gridwidth = 2;
        c.gridx = 2;
        c.weightx = 0.5;
        this.add(titleLabel.get(0), c);


        c.gridwidth = 1;
        c.gridx = 4;
        j = new JPanel();
        j.setOpaque(false);
        this.add(j, c);

        /*
        JLabel priorityLabel = new JLabel("" + data.getPriority());
        priorityLabel.setFont(FontMaker.p);
        c.gridx = 5;
        c.weightx = 0.0;
        this.add(priorityLabel, c);
        */

        // this handles having subtasks
        for (int i=1; i<data.getName().length; i++) {
            c.gridx = 1;
            c.gridy = i+1;
            c.weightx = 0.0;
            c.ipadx = 20;
            this.add(new JPanel(), c);

            JCheckBox ckb = new JCheckBox();
            c.ipadx = 0;
            c.gridx = 2;
            c.gridy = i+1;
            c.weightx = 0.0;
            c.ipadx = 10;
            this.add(ckb, c);

            titleLabel.add(new JTextField(data.getName()[i], maxCharacterLength));
            titleLabel.get(i).setFont(FontMaker.p);
            titleLabel.get(i).setBorder(javax.swing.BorderFactory.createEmptyBorder());
            titleLabel.get(i).setBackground(BACK_GROUND_COLOR);
            titleLabel.get(0).addFocusListener(this);
            c.gridx = 3;
            c.gridy = i+1;
            //c.weightx = 0.5;
            this.add(titleLabel.get(i), c);

            c.gridx = 4;
            c.gridy = 0;
            this.add(new JPanel(), c);
        }
        c.gridy = data.getName().length+1;
        c.gridx = 3; // place in center to avoid it bleeding into the rounded edges (could also set the panel transparent)
        this.add(new JPanel(), c);
    }


    public String[] getTitle() {
        return data.getName();
    }
    public void setPriority(int priority) {
        data.setPriority(priority);
    }
    public int getPriority() {
        return data.getPriority();
    }
    public void lowerPriority() {
        data.setPriority(data.getPriority()-1);
    }
    public void increasePriority() {
        data.setPriority(data.getPriority()+1);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}



    boolean dragged = false;
    boolean isVerticalDrag = false;
    int x1;
    int y1;
    int taskX = getX();
    int taskY = getY();
    int verticalDragThreshold = this.getHeight()+5;  // minimum pixels to move before triggering reorder
    int verticalAccumulator = 0;  // tracks accumulated vertical movement
    

    public void mousePressed(MouseEvent e) {
        //taskmenu.remove(data.getID(), j);
        //System.out.println("Hiiii");
        verticalDragThreshold = this.getHeight()+2;

        dragged = true;
        isVerticalDrag = false;
        x1 = e.getXOnScreen();
        y1 = e.getYOnScreen();
        taskX = getX();
        taskY = getY();
        verticalAccumulator = 0;
    }
    
    public void mouseReleased(MouseEvent e) {
        if (dragged) {
            dragged = false;

            // Reset position for visual feedback
            if (!isVerticalDrag) {
                setLocation(taskX, getY());
            }

            // Handle horizontal delete drag only if not doing vertical drag
            if (!isVerticalDrag && y1 > e.getYOnScreen()-40 && y1 < e.getYOnScreen()+40) {
                // Check if they dragged far enough left
                if (x1-e.getXOnScreen() > 100*Main.scaleY) {
                    taskmenu.remove(data.getID(), j);
                    System.out.println("removed by drag");
                }
            }
            
            isVerticalDrag = false;
            verticalAccumulator = 0;
        }
    }
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - x1;
        int deltaY = e.getYOnScreen() - y1;
        
        // Determine if this is a vertical or horizontal drag
        if (Math.abs(deltaY) > Math.abs(deltaX)) {
            // Vertical drag - reorder tasks
            isVerticalDrag = true;
            
            // Accumulate vertical movement
            verticalAccumulator += deltaY;
            y1 = e.getYOnScreen();  // Update baseline for next calculation
            
            // Check how many thresholds have been crossed and move accordingly
            if (verticalAccumulator < -verticalDragThreshold) {
                // Moving up - keep moving while accumulated movement justifies it
                if (verticalAccumulator < -verticalDragThreshold) {
                    taskmenu.moveTaskUp(data.getID(), j);
                    verticalAccumulator += verticalDragThreshold;
                }
            } else if (verticalAccumulator > verticalDragThreshold) {
                // Moving down - keep moving while accumulated movement justifies it
                if (verticalAccumulator > verticalDragThreshold) {
                    taskmenu.moveTaskDown(data.getID(), j);
                    verticalAccumulator -= verticalDragThreshold;
                }
            }
        } else {
            // Horizontal drag to delete task
            isVerticalDrag = false;
            if (deltaX < 0 && deltaX > -60)
                setLocation(taskX + deltaX, getY());
            else if (deltaX < 0)
                setLocation(taskX + deltaX*2, getY());  // makes movement faster near the end
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {}


    // this only triggers when you press enter in a textfield, which means that a lot of work accidentally is not saved, therefore we need to used focuslistener instead
    public void actionPerformed(ActionEvent e) {
        // save the new text to the save file
        for (int i=0; i<titleLabel.size(); i++) {
            if (e.getSource() == titleLabel.get(i)) {
                String[] temp = data.getName();
                temp[i] = titleLabel.get(i).getText();
                data.setName(temp);
                taskmenu.saveList();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {}

    public void focusLost(FocusEvent e) {
       // save the new text to the save file
        for (int i=0; i<titleLabel.size(); i++) {
            if (e.getSource() == titleLabel.get(i)) {
                String[] temp = data.getName();
                temp[i] = titleLabel.get(i).getText();
                data.setName(temp);
                taskmenu.saveList();
            }
        }
    }

}
