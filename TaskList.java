public class TaskList{
    TaskNode root;

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
