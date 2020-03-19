package tasks.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 The Description :
    A small class used for methods related to Date of the tasks from service
 */
public class DateService {
    static final int SECONDS_IN_MINUTE = 60;
    static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;

    private final TasksService service;

    public DateService(TasksService service){
        this.service=service;
    }

    public static LocalDate getLocalDateValueFromDate(Date date){//for setting to DatePicker - requires LocalDate
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Date getDateValueFromLocalDate(LocalDate localDate){//for getting from DatePicker
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public Date getDateMergedWithTime(String time, Date noTimeDate) {//to retrieve Date object from both DatePicker and time field
        String[] units = time.split(":");
        if(units.length != 2){
            throw new IllegalArgumentException("Invalid interval format hh:MM");
        }
        try {
            int hour = Integer.parseInt(units[0]);
            int minute = Integer.parseInt(units[1]);
            if (hour > HOURS_IN_A_DAY || minute > MINUTES_IN_HOUR)
                throw new IllegalArgumentException("Time unit exceeds bounds");
            Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(noTimeDate);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            return calendar.getTime();
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid time input");
        }
    }

    public String getTimeOfTheDayFromDate(Date date){//to set in detached time field
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return service.formTimeUnit(hours) + ":" + service.formTimeUnit(minutes);
    }
}