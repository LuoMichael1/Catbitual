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
            
            while (filesc.hasNextInt()) {
                int n = filesc.nextInt();          // get number of subtasks + task
                filesc.nextLine();
                String[] name = new String[n];
                for (int i=0; i<n; i++) {
                    name[i] = filesc.nextLine();   // place all of the text into a single array
                }
                String data = filesc.nextLine();
                
                System.out.println(Arrays.toString(name));
                System.out.println(data);

                // parse the data line
                String[] dataArray = data.split(",");
                int priority = Integer.parseInt(dataArray[0]);
                boolean isSubTask = dataArray[1].equals("t");
                boolean complete = dataArray[2].equals("t");
                addEnd(new TaskNode(counter++, name, priority, isSubTask, complete, LocalDateTime.parse(dataArray[3]), LocalDateTime.parse(dataArray[4])));
            }
        } catch (Exception e) {
            System.out.println("Could not read task file " + e);
        }
    }

    public boolean isEmpty() {
        return (root==null);
    }

    public TaskNode getRoot() {
        return root;
    }
    
    // add the new task to the start of the list (the top)
    public void add (TaskNode newTask) {
        if (isEmpty()) {
            root = newTask;
        }
        else {
            TaskNode temporaryNode = root;
            root = newTask;
            root.next = temporaryNode;
        }
    }
    // add the new task to the end of the list (the bottom)
    public void addEnd (TaskNode newTask) {
        if (isEmpty()) {
            root = newTask;
        }
        else {
            TaskNode currentNode = root;

            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newTask;
            newTask.previous = currentNode;
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
                    break;
                }
            }
        }
    }
   
    public void sort() {
        
    }
}
