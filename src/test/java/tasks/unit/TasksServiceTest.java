package tasks.unit;

import org.junit.jupiter.api.BeforeEach;
import tasks.repository.ArrayTaskList;
import tasks.service.TasksService;

import static org.mockito.Mockito.mock;

public class TasksServiceTest {

    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;

    @BeforeEach
    void setUp() {
        arrayTaskList = mock(ArrayTaskList.class);
        tasksService = new TasksService(arrayTaskList);
    }


}
