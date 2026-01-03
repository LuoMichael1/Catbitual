// 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Task extends RoundedPanel{

    private String[] title;
    private String description; 
    private int deadLine; 
    private int priority;
    private double timeSpent; 

    public Task(String[] title, String description, int deadLine, int priority) {
        super(40, false);
        this.setFocusable(true);        
        this.setVisible(true);
        this.setBackground(new Color(240, 240, 240));
        this.setLayout(new GridBagLayout());
        //this.setBorder(BorderFactory.createLineBorder(Color.WHITE,7));

        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = priority;
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        

        JCheckBox cb =new JCheckBox();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        this.add(cb, c);

        JLabel titleLabel = new JLabel(title[0]);
        titleLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        this.add(titleLabel, c);
        

        JLabel deadLineLabel = new JLabel(""+deadLine);
        deadLineLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 0;
        this.add(new JPanel(), c);


        JLabel priorityLabel = new JLabel("" + priority);
        priorityLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        c.gridx = 4;
        c.weightx = 0.0;
        this.add(priorityLabel, c);

        for (int i=1; i<title.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            c.weightx = 0.0;
            c.ipadx = 20;
            this.add(new JPanel(), c);

            JCheckBox ckb = new JCheckBox();
            c.ipadx = 0;
            c.gridx = 1;
            c.gridy = i;
            c.weightx = 0.0;
            this.add(ckb, c);

            JLabel t = new JLabel(title[i]);
            t.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
            c.gridx = 2;
            c.gridy = i;
            //c.weightx = 0.5;
            this.add(t, c);

            c.gridx = 3;
            c.gridy = 0;
            this.add(new JPanel(), c);

            JLabel p = new JLabel("" + priority);
            p.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
            c.gridx = 4;
            c.gridy = i;
            c.weightx = 0.0;
            this.add(p, c);
        }

        repaint();
    }
    public Task(String[] title, String description, int deadLine) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = 0;
    }


    public int getDeadLine() {
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

}
