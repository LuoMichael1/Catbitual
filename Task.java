// 

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Task extends JPanel{

    private String title;
    private String description; 
    private int deadLine; 
    private int priority;
    private double timeSpent; 

    public Task(String title, String description, int deadLine, int priority) {
        this.setFocusable(true);        
        this.setVisible(true);
        this.setBackground(new Color(230,230,230));
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE,7));

        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = priority;

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        this.add(titleLabel, BorderLayout.WEST);
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        this.add(descriptionLabel, BorderLayout.CENTER);
        JLabel deadLineLabel = new JLabel(""+deadLine);
        deadLineLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        this.add(deadLineLabel, BorderLayout.EAST);
        JLabel priorityLabel = new JLabel("" + priority);
        priorityLabel.setFont(FontMaker.loadFont("Assets/Fonts/PatrickHand-Regular.ttf", (float)(Main.height*0.03)));
        this.add(priorityLabel, BorderLayout.EAST);
    }
    public Task(String title, String description, int deadLine) {
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
    public String getTitle() {
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

}
