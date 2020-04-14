package tasks.domain;

import org.apache.log4j.Logger;
import tasks.utils.Utils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 The Description :
    Main class containing all the attributes and methods of the object Task
    -implements constructors, getters setters, date&time formatters, and couple of overwritten methods
 */
public class Task implements Serializable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    private static final Logger log = Logger.getLogger(Task.class.getName());
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private SimpleDateFormat getDateFormat(){
        return simpleDateFormat;
    }

    public Task(String title, Date time){
        if (time.getTime() < 0) {
            log.error("time below bound");
            throw new IllegalArgumentException("Time cannot be negative");
        }
        this.title = title;
        this.time = time;   //same ?
        this.start = time;
        this.end = time;
    }

    public Task(Task other){
        this.title = other.getTitle();
        this.start = other.getStartTime();
        this.end = other.getEndTime();
        this.time = other.getTime();
        this.active = other.isActive();
        this.interval = other.getRepeatInterval();
    }
    
    public Task(String title, Date start, Date end, int interval){
        if (start.getTime() < 0 || end.getTime() < 0) {
            log.error("time below bound");
            throw new IllegalArgumentException("Time cannot be negative");
        }
        if (interval < 1) {
            log.error("interval < than 1");
            throw new IllegalArgumentException("interval should be >= 1");
        }
        this.title = title;
        this.time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive(){
        return this.active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public Date getTime() {
        return time;
    }

    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
    }

    public int getRepeatInterval(){
        return interval > 0 ? interval : 0;
    }

    public boolean isRepeated(){
        return this.interval != 0;
    }

    public Date nextTimeAfter(Date current){
        if (current.after(end) || current.equals(end))
            return null;

        if (isRepeated() && isActive()){
            Date timeBefore = getDate(current);
            if (timeBefore != null) return timeBefore;
        }
        if (!isRepeated() && current.before(time) && isActive()){
            return time;
        }

        return null;
    }

    private Date getDate(Date current) {
        Date timeBefore  = start;
        Date timeAfter = start;
        if (current.before(start)){
            return start;
        }

        if ((current.after(start) || current.equals(start)) && (current.before(end) || current.equals(end))){
            for (long i = start.getTime(); i <= end.getTime(); i += interval*1000){
                if (current.equals(timeAfter))
                    return new Date(timeAfter.getTime());

                if (current.after(timeBefore) && current.before(timeAfter))
                    return timeBefore;

                timeBefore = timeAfter;
                timeAfter = new Date(timeAfter.getTime()+ interval*1000);
            }
        }
        return null;
    }

    public String getFormattedDateStart(){
        return getDateFormat().format(start);
    }

    public String getFormattedDateEnd(){
        return getDateFormat().format(end);
    }

    public String getFormattedRepeated(){
        if (isRepeated()){
            String formattedInterval = Utils.getFormattedInterval(interval);
            return "Every " + formattedInterval;
        }
        else {
            return "No";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!time.equals(task.time)) return false;
        if (!start.equals(task.start)) return false;
        if (!end.equals(task.end)) return false;
        if (interval != task.interval) return false;
        if (active != task.active) return false;
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }
}