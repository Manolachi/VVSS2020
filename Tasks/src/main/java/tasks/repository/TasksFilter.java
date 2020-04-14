package tasks.repository;

import javafx.collections.ObservableList;
import tasks.domain.Task;
import java.util.*;

//import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 The Description :
    Class that implements methods used in the filtering of tasks
 */
public class TasksFilter {

    private final ArrayList<Task> tasks;

    public TasksFilter(ObservableList<Task> tasksList){
        tasks = new ArrayList<>();
        tasks.addAll(tasksList);
    }

    public Iterable<Task> incoming(Date start, Date end){

        ArrayList<Task> incomingTasks = new ArrayList<>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            if(nextTime==null)System.out.println("null");
            else System.out.println(nextTime.toString());
            if (nextTime != null ){
                if(nextTime.before(end))
                    incomingTasks.add(t);
                if(nextTime.equals(end))
                    incomingTasks.add(t);
            }
        }
        return incomingTasks;
    }
}