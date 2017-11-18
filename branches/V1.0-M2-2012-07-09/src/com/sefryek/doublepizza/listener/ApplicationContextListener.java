package com.sefryek.doublepizza.listener;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 30, 2012
 * Time: 12:24:00 PM
 */

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.quartz.JobDetail;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.apache.log4j.Logger;

import javax.servlet.*;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.scheduler.InMemeoryDataLoaderJob;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.service.exception.DataLoadException;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Locale;
import java.text.ParseException;


public class ApplicationContextListener implements ServletContextListener {
    private Scheduler scheduler;
    public Logger logger;

    public void contextInitialized(ServletContextEvent event) {

        ResourceBundle enBundle = ResourceBundle.getBundle("MessageResources", Locale.ENGLISH);

        logger = Logger.getLogger(ApplicationContextListener.class);
        //loading data from DB(or from flat file(Serialized Object)) for the first time then scheduling it for reloading once a day
        InMemoryData memoryData = new InMemoryData();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());

        //initializing service beans for InMemoryData class
        memoryData.initializeService(context);

        try {
            //loading data from DB to "menu"
            memoryData.loadData();

        } catch (DataLoadException e) {
            logger.error("", e);
            throw new RuntimeException(enBundle.getString("message.error.in.loaddata"), e);
        }

        try {
            //configuring job details for quartz
            JobDetail job = new JobDetail();
            job.setName(Constant.IN_MEMORY_DATA_LOADR_JOB_NAME);
            job.setJobClass(InMemeoryDataLoaderJob.class);

            Map dataMap = job.getJobDataMap();
            dataMap.put(Constant.IN_MEMORY_DATA_LOADR_TASK_NAME, memoryData);

            //configure the scheduler time
            CronTrigger trigger = new CronTrigger();
            trigger.setName(Constant.IN_MEMORY_DATA_LOADR_CRON_NAME);
            trigger.setCronExpression(Constant.IN_MEMORY_DATA_LOADR_CRON_EXPRESSION);

            //scheduleing the job
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);


        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void contextDestroyed(ServletContextEvent event) {
        try {
            if (scheduler != null)
                scheduler.shutdown(true);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
