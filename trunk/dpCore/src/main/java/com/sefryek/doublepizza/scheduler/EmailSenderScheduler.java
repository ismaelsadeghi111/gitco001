package com.sefryek.doublepizza.scheduler;

import org.springframework.scheduling.TaskScheduler;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 2/3/14
 * Time: 7:03 PM
 */
public class EmailSenderScheduler extends Scheduler {
    private TaskScheduler taskScheduler;

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void setTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }


    @Override
    public void schedule(Runnable task , Date date) {
        taskScheduler.schedule(task , date);
    }
}
