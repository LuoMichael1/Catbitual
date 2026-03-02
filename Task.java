import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDateTime;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Task extends RoundedPanel implements MouseListener, MouseMotionListener{

    private int id;
    private String[] title;
    private String description; 
    private LocalDateTime deadLine; 
    private int priority;
    private double timeSpent; 
    private TaskMenu taskmenu;
    private JPanel j;

    private Color BACK_GROUND_COLOR = new Color(240, 240, 240);

    //private JLabel titleLabel;
    private JTextField titleLabel;
    private int maxCharacterLength = 20;  // if this is too long it wont look good on thin screens

    public Task(String[] title, String description, LocalDateTime deadLine, int priority) {
        super(40, false);
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = priority;

        initializeTask();
        repaint();
    }
    public Task(String[] title, LocalDateTime deadLine, int priority, TaskMenu taskmenu, int id, JPanel j) {
        super(40, false);
        this.title = title;
        this.deadLine = deadLine;
        this.priority = priority;
        this.taskmenu = taskmenu; // dependency injection
        this.id = id;
        this.j = j;
        initializeTask();
        repaint();

        this.addMouseListener(this);
    }

    public Task(String[] title, String description, LocalDateTime deadLine) {
        super(40, false);
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = 0;

        initializeTask();
        repaint();
    }

    public void initializeTask() {
        this.setFocusable(true);
        this.setVisible(true);
        this.setBackground(BACK_GROUND_COLOR);
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridy = 0;
        c.gridx = 3;
        JPanel j = new JPanel();
        j.setOpaque(false);
        //j.setBackground(Color.red);
        this.add(j, c);

        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 1;
        j = new JPanel();
        //j.setBackground(Color.red);
        j.setOpaque(false);
        this.add(j, c);

        JCheckBox cb = new JCheckBox();
        c.gridx = 1;
        c.weightx = 0.0;
        c.ipadx = 10;
        //c.ipady = 100;
        this.add(cb, c);
        c.ipadx = 0;
        c.ipady = 0;

        titleLabel = new JTextField(title[0], maxCharacterLength);
        titleLabel.setFont(FontMaker.p);
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        titleLabel.setBackground(BACK_GROUND_COLOR);
        c.gridwidth = 2;
        c.gridx = 2;
        c.weightx = 0.5;
        this.add(titleLabel, c);


        c.gridwidth = 1;
        c.gridx = 4;
        j = new JPanel();
        //j.setBackground(Color.red);
        j.setOpaque(false);
        this.add(j, c);


        JLabel priorityLabel = new JLabel("" + priority);
        priorityLabel.setFont(FontMaker.p);
        c.gridx = 5;
        c.weightx = 0.0;
        this.add(priorityLabel, c);

        for (int i=1; i<title.length; i++) {
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

            titleLabel = new JTextField(title[i], maxCharacterLength);
            titleLabel.setFont(FontMaker.p);
            titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            titleLabel.setBackground(BACK_GROUND_COLOR);
            c.gridx = 3;
            c.gridy = i+1;
            //c.weightx = 0.5;
            this.add(titleLabel, c);

            c.gridx = 4;
            c.gridy = 0;
            this.add(new JPanel(), c);
        }
        c.gridy = title.length+1;
        c.gridx = 3; // place in center to avoid it bleeding into the rounded edges (could also set the panel transparent)
        this.add(new JPanel(), c);
    }



    public LocalDateTime getDeadLine() {
        return deadLine;
    }
    public String getDescription() {
        return description;
    }
    public double getTimeSpent() {
        return timeSpent;
    }
    public String[] getTitle() {
        return title;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }
    public void lowerPriority() {
        priority--;
    }
    public void increasePriority() {
        priority++;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
        taskmenu.remove(id, j);
        System.out.println("Hiiii");

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

}
