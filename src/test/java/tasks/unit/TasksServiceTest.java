package tasks.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.domain.Task;
import tasks.repository.ArrayTaskList;
import tasks.service.TasksService;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TasksServiceTest {

    private ArrayTaskList arrayTaskList;
    private TasksService tasksService;

    @BeforeEach
    void setUp() {
        arrayTaskList = mock(ArrayTaskList.class);
        tasksService = new TasksService(arrayTaskList);
    }

    @Test
    void tasksService_getObservableList() {
        Task taskMock_1 = mock(Task.class);
        Task taskMock_2 = mock(Task.class);
        Task taskMock_3 = mock(Task.class);
        Task taskMock_4 = mock(Task.class);
        Task taskMock_5 = mock(Task.class);

        Mockito.when(arrayTaskList.getAll()).thenReturn(Arrays.asList(taskMock_1, taskMock_2, taskMock_3, taskMock_4, taskMock_5));
        assertEquals( 5, tasksService.getObservableList().size());
    }

    @Test
    void tasksService_getIntervalInHours() {
        Task taskMock_1 = mock(Task.class);

        Mockito.when(arrayTaskList.getAll()).thenReturn(Collections.singletonList(taskMock_1));
        assertEquals("00:00", tasksService.getIntervalInHours(tasksService.getObservableList().get(0)));
    }
}
