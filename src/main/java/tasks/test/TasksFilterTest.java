package tasks.test;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.domain.Task;
import tasks.repository.LinkedTaskList;
import tasks.repository.TaskList;
import tasks.repository.TasksFilter;

import java.util.*;

class TasksFilterTest {
    private TaskList taskList;
    private Task taskF1, taskF2, taskF3, taskF4, taskF6, taskF7, taskF8;

    @BeforeEach
    void setUp() {
        taskList = new LinkedTaskList();
        taskF1 = new Task("F1", new Date(120, Calendar.APRIL,14,8,0));
        taskF2 = new Task("F2", new Date(120, Calendar.APRIL,14,12,0));
        taskF3 = new Task("F3", new Date(120, Calendar.APRIL,14,18,0));
        taskF4 = new Task("F4", new Date(120, Calendar.APRIL,15,8,0));
        taskF6 = new Task("F6", new Date(120, Calendar.APRIL,15,18,0));
        taskF7 = new Task("F7", new Date(120, Calendar.APRIL,16,8,0));
        taskF8 = new Task("F8", new Date(120, Calendar.APRIL,18,8,0));

        taskF1.setActive(true);
        taskF2.setActive(true);
        taskF3.setActive(true);
        taskF4.setActive(true);
        taskF6.setActive(true);
        taskF7.setActive(true);
        taskF8.setActive(true);
    }

    @AfterEach
    void tearDown() {
        taskList = null;
    }

    @Test
    void GivenIntervalContainingAllTasks_WhenFiltering_AllTasksAreReturned() {
        // Arrange
        taskList.add(taskF1);
        taskList.add(taskF2);
        Date start = new Date(120, Calendar.APRIL,14,0,0);
        Date end = new Date(120, Calendar.APRIL,15,0,0);
        List<Task> filteredTasks = new ArrayList<>();

        // Act
        TasksFilter tasksFilter = new TasksFilter(FXCollections.observableArrayList(taskList.getAll()));
        tasksFilter.incoming(start, end).forEach(filteredTasks::add);

        //Assert
        Assertions.assertEquals(taskList.getAll(), filteredTasks,"Error: the tasks F1 and F2 were not found in the filtered list");
    }

    @Test
    void GivenIntervalEndingOnTaskDate_WhenFiltering_TheGivenTasksIsReturned() {
        // Arrange
        taskList.add(taskF1);
        Date start = new Date(120, Calendar.APRIL,14,7,0);
        Date end = new Date(120, Calendar.APRIL,14,8,0);
        List<Task> filteredTasks = new ArrayList<>();

        // Act
        TasksFilter tasksFilter = new TasksFilter(FXCollections.observableArrayList(taskList.getAll()));
        tasksFilter.incoming(start, end).forEach(filteredTasks::add);

        //Assert
        Assertions.assertEquals(taskList.getAll(), filteredTasks,"Error: the tasks F1 has not been found");
    }

    @Test
    void GivenEmptyTaskList_WhenFiltering_EmptyListIsReturned() {
        // Arrange
        Date start = new Date(120, Calendar.APRIL,14,0,0);
        Date end = new Date(120, Calendar.APRIL,19,0,0);
        List<Task> filteredTasks = new ArrayList<>();

        // Act
        TasksFilter tasksFilter = new TasksFilter(FXCollections.observableArrayList(taskList.getAll()));
        tasksFilter.incoming(start, end).forEach(filteredTasks::add);

        //Assert
        Assertions.assertEquals(0, filteredTasks.size(),"Error: the returned list is not empty");
    }

    @Test
    void GivenTasksAfterInterval_WhenFiltering_EmptyListIsReturned() {
        // Arrange
        taskList.add(taskF1);
        Date start = new Date(120, Calendar.APRIL,14,0,0);
        Date end = new Date(120, Calendar.APRIL,14,6,0);
        List<Task> filteredTasks = new ArrayList<>();

        // Act
        TasksFilter tasksFilter = new TasksFilter(FXCollections.observableArrayList(taskList.getAll()));
        tasksFilter.incoming(start, end).forEach(filteredTasks::add);

        //Assert
        Assertions.assertEquals(0, filteredTasks.size(), "Error: the returned list is not empty");
    }

    @Test
    void GivenTasksBeforeInterval_WhenFiltering_EmptyListIsReturned() {
        // Arrange
        taskList.add(taskF8);
        Date start = new Date(120, Calendar.APRIL,20,0,0);
        Date end = new Date(120, Calendar.APRIL,20,1,0);
        List<Task> filteredTasks = new ArrayList<>();

        // Act
        TasksFilter tasksFilter = new TasksFilter(FXCollections.observableArrayList(taskList.getAll()));
        tasksFilter.incoming(start, end).forEach(filteredTasks::add);

        //Assert
        Assertions.assertEquals(0, filteredTasks.size(), "Error: the returned list is not empty");
    }

}