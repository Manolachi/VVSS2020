package tasks.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.domain.Task;
import tasks.repository.ArrayTaskList;
import tasks.repository.TaskList;
import tasks.repository.TasksFilter;
import java.util.Date;

/**
 The Description :
    Service class for tasks, implements methods related to couple of the functionality needed in the business logic
 */
public class TasksService {

    private final TaskList tasks;

    public TasksService(TaskList tasks){
        this.tasks = tasks;
    }

    public ObservableList<Task> getObservableList(){
        return FXCollections.observableArrayList(tasks.getAll());
    }

    public String getIntervalInHours(Task task){
        int seconds = task.getRepeatInterval();
        int minutes = seconds / DateService.SECONDS_IN_MINUTE;
        int hours = minutes / DateService.MINUTES_IN_HOUR;
        minutes = minutes % DateService.MINUTES_IN_HOUR;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }

    String formTimeUnit(int timeUnit){
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        if (timeUnit == 0) sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }

    public int parseFromStringToSeconds(String stringTime){
        String[] units = stringTime.split(":");
        if(units.length != 2){
            throw new IllegalArgumentException("Invalid interval format hh:MM");
        }
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        return (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksFilter tasksOps = new TasksFilter(getObservableList());
        return tasksOps.incoming(start,end);
    }
}
