package tasks.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import tasks.repository.LinkedTaskList;
import tasks.repository.TaskList;
import tasks.service.DateService;
import tasks.service.TasksService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@Nested
@DisplayName("Testing Date Merged with Time")
class DateServiceTest {

    public TaskList taskList;
    public TasksService service;
    public DateService dateService;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        taskList=new LinkedTaskList();
        service = new TasksService(taskList);
        dateService = new DateService(service);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
//
//    @org.junit.jupiter.api.Test
//    void getDateMergedWithTime() {
//        getDateMergedWithTime_valid_minute_ECP();
//
//    }


    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_valid_minute_ECP() {

        System.out.println("1");

    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_invalid_minute_ECP() {

        System.out.println("2");
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_valid_hour_ECP() {

        System.out.println("3");
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_invalid_hour_ECP() {
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_valid_hour_BVA() {
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_invalid_hour_BVA() {
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_invalid_date_BVA() {
    }

    @org.junit.jupiter.api.Test
    void getDateMergedWithTime_valid_date_BVA() {
    }



}