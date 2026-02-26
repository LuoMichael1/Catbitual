import java.time.LocalDateTime;

public class TaskNode {
    private int id = 0;
    private String name;
    private int priority = 0;
    private boolean isSubtask = false;
    private boolean complete = false;
    
    private LocalDateTime startDate;
    private LocalDateTime dueDate;

    public TaskNode next = null;
    public TaskNode previous = null;

    public TaskNode(int id, String name, int priority, boolean isSubtask, boolean isComplete, LocalDateTime startDate, LocalDateTime dataArray) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.isSubtask = isSubtask;
        this.startDate = startDate;
        this.dueDate = dataArray;
        this.complete = isComplete;
    }

    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPriority() {
        return priority;
    }
    public boolean isSubTask() {
        return isSubtask;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setSubtask(boolean isSubtask) {
        this.isSubtask = isSubtask;
    }

   

}
