package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.domain.TaskStub;
import tasks.repository.ArrayTaskList;
import tasks.service.TasksService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrateArrayTaskListTest {
    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;

    @BeforeEach
    void setUp(){
        arrayTaskList = new ArrayTaskList();
        arrayTaskList.add(new TaskStub());
        tasksService = new TasksService(arrayTaskList);
    }

    @Test
    void integrateArrayTaskList_getObservableList(){
        assertEquals(1, tasksService.getObservableList().size());
    }

    @Test
    void integrateArrayTaskList_getIntervalInHours(){
        assertEquals("00:16", tasksService.getIntervalInHours(tasksService.getObservableList().get(0)));
    }
}
