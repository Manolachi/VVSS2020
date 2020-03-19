package tasks.repository;

import tasks.domain.Task;
import java.io.Serializable;
import java.util.List;

/**
 The Description :
    Abstract class for the list of tasks ( main repository ), that implements iterable and serializable
    is used in all other repository classes as attribute
 */
public interface TaskList extends Iterable<Task>, Serializable  {
    void add(Task task);
    boolean remove(Task task);
    int size();
    Task getTask(int index);
    List<Task> getAll();
}