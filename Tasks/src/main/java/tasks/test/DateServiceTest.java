package tasks.test;

import org.junit.jupiter.api.*;
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
    @Order(1)
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


//    @org.junit.jupiter.api.Test
    @DisplayName("ECP_Minutes_Valid")
    @Tag("ECP")
    @Order(2)
    @Timeout(1000)
    @RepeatedTest(3)
    void getDateMergedWithTime_valid_minute_ECP() {

//        120 year, because date get year-1900 as parameter
        Date expectedDate = new Date(120,3,31,8,30);
        assertEquals(expectedDate, dateService.getDateMergedWithTime("8:30",new Date(120,3,31)));

    }

    @org.junit.jupiter.api.Test
    @DisplayName("ECP_Minutes_Non_Valid")
    @Tag("ECP")
    @Order(3)
    @Timeout(1000)
    void getDateMergedWithTime_invalid_minute_ECP() {
//        120 year, because date get year-1900 as parameter
        try{

            dateService.getDateMergedWithTime("8:-5",new Date(120,3,31));
            assert(false);
        }catch(IllegalArgumentException e){
            assert(true);
        }

    }

    @org.junit.jupiter.api.Test
    @DisplayName("ECP_Hours_Valid")
    @Tag("ECP")
    @Order(4)
    @Timeout(1000)
    void getDateMergedWithTime_valid_hour_ECP() {

//        120 year, because date get year-1900 as parameter
        Date expectedDate = new Date(120,3,31,10,15);
        assertEquals(expectedDate, dateService.getDateMergedWithTime("10:15",new Date(120,3,31)));

    }

    @org.junit.jupiter.api.Test
    @DisplayName("ECP_Hours_Non_Valid")
    @Tag("ECP")
    @Order(5)
    @Timeout(1000)
    void getDateMergedWithTime_invalid_hour_ECP() {
        //        120 year, because date get year-1900 as parameter
        try{

            dateService.getDateMergedWithTime("30:15",new Date(120,3,31));
            assert(false);
        }catch(IllegalArgumentException e){
            assert(true);
        }

    }

    @org.junit.jupiter.api.Test
    @DisplayName("BVA_Hours_Valid")
    @Tag("BVA")
    @Order(6)
    @Timeout(1000)
    void getDateMergedWithTime_valid_hour_BVA() {

//        120 year, because date get year-1900 as parameter
        Date expectedDate = new Date(120,3,31,23,59);
        assertEquals(expectedDate, dateService.getDateMergedWithTime("23:59",new Date(120,3,31)));

    }

    @org.junit.jupiter.api.Test
    @DisplayName("BVA_Hours_Non_Valid")
    @Tag("BVA")
    @Order(7)
    @Timeout(1000)
    void getDateMergedWithTime_invalid_hour_BVA() {
        //        120 year, because date get year-1900 as parameter
        try{

            dateService.getDateMergedWithTime("24:00",new Date(120,3,31));
            assert(false);
        }catch(IllegalArgumentException e){
            assert(true);
        }
    }

    @org.junit.jupiter.api.Test
    @DisplayName("BVA_Date_Non_Valid")
    @Tag("BVA")
    @Order(8)
    @Timeout(1000)
    void getDateMergedWithTime_invalid_date_BVA() {
        //        120 year, because date get year-1900 as parameter
        Date expectedDate = new Date(200,11,18,10,00);
        assertEquals(expectedDate, dateService.getDateMergedWithTime("10:00",new Date(200,11,18)));

    }

    @org.junit.jupiter.api.Test
    @DisplayName("BVA_Date_Valid")
    @Tag("BVA")
    @Order(9)
    @Timeout(1000)
    void getDateMergedWithTime_valid_date_BVA() {
        //        120 year, because date get year-1900 as parameter
        try{

            dateService.getDateMergedWithTime("10:00",new Date(201,11,10));
            assert(false);
        }catch(IllegalArgumentException e){
            assert(true);
        }
    }



}