package tasks.domain;

import java.util.Date;

public class TaskStub extends Task {

    public TaskStub(Task other) {
        super(other);
    }

    public TaskStub(){
        init();
    }

    private void init(){
        this.title = "task";
        this.start = new Date("2020/05/05");
        this.end = new Date("2020/08/05");
        this.interval = 1000;
        this.active = true;
    }
}