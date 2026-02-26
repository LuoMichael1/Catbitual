import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class TaskList{
    TaskNode root;
    Scanner filesc;
    private int counter = 0;
    
    public TaskList() {
        try {
            filesc = new Scanner(new File("Userdata/tasklist.txt"));
            
            while (filesc.hasNextLine()) {
                String name = filesc.nextLine();   // I put the name on a different line so accidental injection is a little easier to deal with
                String data = filesc.nextLine();
                
                // parse the data line
                String[] dataArray = data.split(",");
                int priority = Integer.parseInt(dataArray[0]);
                boolean isSubTask = dataArray[1].equals("t");
                boolean complete = dataArray[2].equals("t");
                enList(new TaskNode(counter++, name, priority, isSubTask, complete, LocalDateTime.parse(dataArray[3]), LocalDateTime.parse(dataArray[4])));
            }
        } catch (Exception e) {
            System.out.println("Could not read task file " + e);
        }
    }

    public boolean isEmpty() {
        return (root==null);
    }
    
    // add the new task to the start of the list (the top)
    public void enList (TaskNode newTask) {
        if (isEmpty()) {
            root = newTask;
        }
        else {
            TaskNode temporaryNode = root;
            root = newTask;
            root.next = temporaryNode;
        }
    }

    void remove (int id) {
        TaskNode currentNode = root;
        TaskNode pastNode;
        
        // Since remove will only be called when the user clicks on a task to delete, it is assumed the task exists
        if (isEmpty()) {
            System.out.println("Something went wrong, task could not be found");
        }
        // check if it is the first item
        if (root.getID() == id) {
            root = currentNode.next;
        }
        // go through the rest of the list
        else {
            while (currentNode.next != null) {
                pastNode = currentNode;
                currentNode = currentNode.next;
                
                if (currentNode.getID() == id) {
                    // check if this is the last item
                    if (currentNode.next == null) {
                        pastNode.next = null;
                    }
                    else {
                        pastNode.next = currentNode.next;
                    }
                }
            }
        }
    }
   
    public void sort() {
        
    }
}
