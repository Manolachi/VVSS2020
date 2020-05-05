package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.domain.Task;
import tasks.domain.TaskStub;
import tasks.repository.ArrayTaskList;
import tasks.service.TasksService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrateTaskTest {
    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;

    @BeforeEach
    void setUp(){
        arrayTaskList = new ArrayTaskList();
        Task task = new Task("task", new Date("2020/05/05"), new Date("2020/08/05"), 1000);
        task.setActive(true);
        arrayTaskList.add(new TaskStub());
        tasksService = new TasksService(arrayTaskList);
    }

    @Test
    void integrateTask_getObservableList(){
        assertEquals(1, arrayTaskList.getAll().size());
    }

    @Test
    void integrateTask_getIntervalInHours(){
        assertEquals("00:16", tasksService.getIntervalInHours(tasksService.getObservableList().get(0)));
    }
}
