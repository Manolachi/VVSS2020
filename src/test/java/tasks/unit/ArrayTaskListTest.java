package tasks.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.domain.Task;
import tasks.repository.ArrayTaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ArrayTaskListTest {
    private ArrayTaskList arrayTaskList;

    @BeforeEach
    void setUp(){
        arrayTaskList = new ArrayTaskList();
    }

    @Test
    void arrayTaskList_add_valid_task() {
        Task taskMock = mock(Task.class);
        arrayTaskList.add(taskMock);

        assertEquals(1, arrayTaskList.size());
    }

    @Test
    void arrayTaskList_getAll() {
        Task taskMock_1 = mock(Task.class);
        Task taskMock_2 = mock(Task.class);
        Task taskMock_3 = mock(Task.class);
        Task taskMock_4 = mock(Task.class);
        Task taskMock_5 = mock(Task.class);

        arrayTaskList.add(taskMock_1);
        arrayTaskList.add(taskMock_2);

        assertEquals(2, arrayTaskList.getAll().size());

        arrayTaskList.add(taskMock_3);
        arrayTaskList.add(taskMock_4);
        arrayTaskList.add(taskMock_5);

        assertEquals(5, arrayTaskList.getAll().size());
    }
}
