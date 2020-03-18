package tasks.repository;

import tasks.domain.Task;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 The Description :
    Interface fot the list of tasks ( main repository ), that implements iterable and serializable
    is used in all other repository classes as attribute
 */

public abstract class TaskList implements Iterable<Task>, Serializable  {
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract int size();
    public abstract Task getTask(int index);
    public abstract List<Task> getAll();

    public abstract Iterator<Task> iterator();

    public TaskList incoming(Date from, Date to){
        TaskList incomingTasks;
        if (this instanceof ArrayTaskList){
            incomingTasks = new ArrayTaskList();
        }
        else {
            incomingTasks = new LinkedTaskList();
        }

        for(int i = 0; i < this.size(); i++){
            if(getTask(i).nextTimeAfter(from) != null && getTask(i).nextTimeAfter(from).before(to)){
                incomingTasks.add(getTask(i));
                System.out.println(getTask(i).getTitle());
            }
        }
        return incomingTasks;
    }



}