import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class TaskList{
    private TaskNode root;  // represents the top most task
    private Scanner filesc;
    private static int counterID = 0;
    private String saveFilePath = "Userdata/saved_tasklist.txt";
    
    public TaskList() {
        initializeFromFile(saveFilePath);
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
        //System.out.println("Task removed" + id);
    }
    
    public void moveUp(int id) {
        if (isEmpty()) {
            System.out.println("Warning: There are no tasks, moveUp should not be called");
            return;
        }
        
        // If root is the task, it's already at the top
        if (root.getID() == id) {
            return;
        }
        
        TaskNode currentNode = root;
        TaskNode pastNode = null;
        
        // Find the node before the one we want to move
        while (currentNode != null && currentNode.next != null) {
            if (currentNode.next.getID() == id) {
                // Found the node to move up
                TaskNode nodeToMove = currentNode.next;
                
                if (pastNode == null) {
                    // Moving to root position
                    currentNode.next = nodeToMove.next;
                    if (nodeToMove.next != null) {
                        nodeToMove.next.previous = currentNode;
                    }
                    nodeToMove.next = root;
                    nodeToMove.previous = null;
                    root.previous = nodeToMove;
                    root = nodeToMove;
                } else {
                    // Moving up in the middle of list
                    currentNode.next = nodeToMove.next;
                    if (nodeToMove.next != null) {
                        nodeToMove.next.previous = currentNode;
                    }
                    nodeToMove.next = pastNode.next;
                    nodeToMove.previous = pastNode;
                    pastNode.next.previous = nodeToMove;
                    pastNode.next = nodeToMove;
                }
                return;
            }
            pastNode = currentNode;
            currentNode = currentNode.next;
        }
    }
    public void moveDown(int id) {
        if (isEmpty()) {
            System.out.println("Warning: There are no tasks, moveDown should not be called");
            return;
        }
        
        TaskNode currentNode = root;
        TaskNode pastNode = null;
        
        while (currentNode != null) {
            if (currentNode.getID() == id) {
                // Found the node to move down
                if (currentNode.next == null) {
                    //System.out.println("Warning: moveDown, but it is already the last task");
                    return;
                }
                
                TaskNode nodeToMove = currentNode;
                TaskNode nextNode = currentNode.next;
                
                if (pastNode == null) {
                    // Moving root down
                    root = nextNode;
                    nextNode.previous = null;
                    nodeToMove.next = nextNode.next;
                    nodeToMove.previous = nextNode;
                    if (nextNode.next != null) {
                        nextNode.next.previous = nodeToMove;
                    }
                    nextNode.next = nodeToMove;
                } else {
                    // Moving node in middle
                    pastNode.next = nextNode;
                    nextNode.previous = pastNode;
                    nodeToMove.next = nextNode.next;
                    nodeToMove.previous = nextNode;
                    if (nextNode.next != null) {
                        nextNode.next.previous = nodeToMove;
                    }
                    nextNode.next = nodeToMove;
                }
                return;
            }
            pastNode = currentNode;
            currentNode = currentNode.next;
        }
    }

    public void sort() {
        
    }

    private void initializeFromFile(String filepath) {
        try {
            filesc = new Scanner(new File(filepath));
            
            while (filesc.hasNextInt()) {
                int n = filesc.nextInt();          // get number of subtasks + task
                filesc.nextLine();
                String[] name = new String[n];
                for (int i=0; i<n; i++) {
                    name[i] = filesc.nextLine();   // place all of the text into a single array
                }
                String data = filesc.nextLine();
                
                //System.out.println(Arrays.toString(name));
                //System.out.println(data);

                // parse the line of data
                String[] dataArray = data.split(",");
                int priority = Integer.parseInt(dataArray[0]);
                boolean isSubTask = dataArray[1].equals("t");
                boolean complete = dataArray[2].equals("t");
                addEnd(new TaskNode(counterID++, name, priority, isSubTask, complete, LocalDateTime.parse(dataArray[3]), LocalDateTime.parse(dataArray[4])));
            }
        } catch (Exception e) {
            System.out.println("Could not read task file " + e);
        }
    }

    // used this website to help with file writing: https://www.w3schools.com/java/java_files_write.asp
    public void savetoFile(String filepath) {
        // go through linked list and write each task into the file
        try {
            FileWriter fw = new FileWriter(filepath);

            if (isEmpty()) {
                fw.write("Nothing Here :3");
            }
            else {
                TaskNode currentNode = root;
                while (currentNode != null) {
                    fw.write("" + currentNode.getName().length + "\n");
                    for (int i=0; i<currentNode.getName().length; i++) {
                        fw.write("" + currentNode.getName()[i] + "\n");
                    }

                    char subtask = 'f';
                    char complete = 'f';
                    if (currentNode.getIsSubTask()) {
                        subtask = 't';
                    }
                    if (currentNode.getIsComplete()) {
                        complete = 't';
                    }
                    fw.write("" + currentNode.getPriority() + "," + subtask + "," + complete + "," + currentNode.getStarTime() + "," + currentNode.getEndTime() + "\n");
                    currentNode = currentNode.next;
                }
            }
            
            fw.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    
    public int getCounterID() {
        return counterID;
    }
    public void incrementCounterID() {
        counterID++;
    }
}
