import java.time.LocalDateTime;

public class TaskNode {
    private int id = 0;
    private String name[];
    private int priority = 0;
    private boolean isSubtask = false;
    private boolean complete = false;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TaskNode next = null;
    public TaskNode previous = null;

    public TaskNode(int id, String[] name, int priority, boolean isSubtask, boolean isComplete, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.isSubtask = isSubtask;
        this.complete = isComplete;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getID() {
        return id;
    }
    public String[] getName() {
        return name;
    }
    public int getPriority() {
        return priority;
    }
    public boolean getIsSubTask() {
        return isSubtask;
    }
    public boolean getIsComplete() {
        return complete;
    }
    public LocalDateTime getStarTime() {
        return startDate;
    } 
    public LocalDateTime getEndTime() {
        return endDate;
    }

    public void setName(String name[]) {
        this.name = name;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setSubtask(boolean isSubtask) {
        this.isSubtask = isSubtask;
    }
    

   

}
