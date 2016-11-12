package utkrishtdhankar.projectneptune;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by utkrishtdhankar on 21/10/16.
 */
public class Task {
    // The name of this task, e.g "Do the laundry"
    private String name;

    // The contexts list for this task
    private ArrayList<TaskContext> contexts;

    // What is the status of the task, e.g. Next, Waiting etc.
    private TaskStatus status;

    /**
     * Constructs a new task with the given parameters
     * Sets the tag list to empty and the status to inbox
     * @param taskName Name for the task
     */
    Task(String taskName) {
        name = taskName;
        contexts = new ArrayList<TaskContext>();
        status = TaskStatus.Inbox;
    }

    /**
     * Returns the status of the task, e.g. Inbox, Next etc.
     * @return The status
     */
    TaskStatus getStatus() {
        return status;
    }

    /**
     * Changes the status of the task
     * @param newStatus the new value of the status of this task
     */
    void changeStatus(TaskStatus newStatus) {
        status = newStatus;
    }

    /**
     * Adds a new context to the task
     * @param newContext The context to be added
     */
    public void addContext(TaskContext newContext) {
        if (newContext != null && !contexts.contains(newContext)) {
            contexts.add(newContext);
        }
    }

    /**
     * Removes a tag from the task.
     * If the given tag exists, it will be removed.
     * @param contextToBeRemoved The tag that will be searched for and removed
     */
    public void removeTag(TaskContext contextToBeRemoved) {
        // remove all contexts which are the same as tagToBeRemoved
        contexts.removeAll(Collections.singleton(contextToBeRemoved));
    }

    /**
     * Returns all the contexts associated with this task
     * @return an array of all the contexts
     */
    public ArrayList<TaskContext> getAllContexts() {
        return contexts;
    }

    /**
     * Sets the name of the Task to newName
     * @param newName The new name of the task
     */
    public void modifyName(String newName) {
        name = newName;
    }

    /**
     * Getter for name
     * @return The name of the task
     */
    public String getName() {return name;}
}
