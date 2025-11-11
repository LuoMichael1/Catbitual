// 

public class Task {

    private String title;
    private String description; 
    private int deadLine; 
    private int priority;
    private double timeSpent; 

    public Task(String title, String description, int deadLine, int priority) {
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.priority = priority;
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
