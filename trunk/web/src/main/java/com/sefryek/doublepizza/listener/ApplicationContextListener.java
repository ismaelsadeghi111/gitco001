package com.sefryek.doublepizza.listener;

/**
 * Created by IntelliJ IDEA.
 * Date: july 23, 2014
 * Time: 12:24:00 PM
 */

import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.scheduler.InMemeoryDataLoaderJob;
import com.sefryek.doublepizza.scheduler.SendCampaignMailJob;
import com.sefryek.doublepizza.service.IStandardCampaign;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class ApplicationContextListener implements ServletContextListener {
    private Scheduler scheduler;
    public Logger logger;
    private IStandardCampaign standardCampaignService;
    private IUserService userService;

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
//            logger.error("", e);
            throw new RuntimeException(enBundle.getString("message.error.in.loaddata"), e);
        }

        try {
            //configuring job details for quartz
            JobDetail inMemoryDataJob = new JobDetail();
            inMemoryDataJob.setName(Constant.IN_MEMORY_DATA_LOADR_JOB_NAME);
            inMemoryDataJob.setJobClass(InMemeoryDataLoaderJob.class);

            Map dataMap = inMemoryDataJob.getJobDataMap();
            dataMap.put(Constant.IN_MEMORY_DATA_LOADR_TASK_NAME, memoryData);

            //configure the scheduler time
            CronTrigger trigger = new CronTrigger();
            trigger.setName(Constant.IN_MEMORY_DATA_LOADR_CRON_NAME);
            trigger.setCronExpression(Constant.IN_MEMORY_DATA_LOADR_CRON_EXPRESSION);

           //------------------------------------------------------------------------
            //Saeid AmanZadeh Prepare job
                JobDetail sendCampaignMailJob = new JobDetail();
                sendCampaignMailJob.setName(Constant.SEND_CAMPAIGN_MAIL_JOB_NAME);
                sendCampaignMailJob.setJobClass(SendCampaignMailJob.class);

                userService  = (IUserService) context.getBean(IUserService.BEAN_NAME);
                standardCampaignService = (IStandardCampaign) context.getBean(IStandardCampaign.BEAN_NAME);

                SendMail sendMail = new SendMail();
                sendMail.setMailSender((JavaMailSender) context.getBean(Constant.MAIL_SENDER_BEAN_NAME));
                sendMail.setFreemarkerConfiguration((Configuration) context.getBean(Constant.FREE_MARKER_CONFIG_NAME));
                Map campaingDataMap = sendCampaignMailJob.getJobDataMap();
                campaingDataMap.put(Constant.SEND_CAMPAIGN_MAIL_TASK_NAME, sendMail);

                campaingDataMap.put(Constant.SEND_CAMPAIGN_SERVICE_NAME, standardCampaignService);
                campaingDataMap.put(Constant.SEND_CAMPAIGN_USER_SERVICE_NAME, userService);

                //configure the scheduler time
                CronTrigger campaignTrigger = new CronTrigger();
                campaignTrigger.setName(Constant.SEND_CAMPAIGN_MAIL_CRON_NAME);
                campaignTrigger.setCronExpression(Constant.SEND_CAMPAIGN_MAIL_CRON_EXPRESSION);
            //Saeid AmanZadeh
            //------------------------------------------------------------------------

            //Scheduleing prepared jobs and trigers
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            //scheduler.scheduleJob(sendCampaignMailJob, campaignTrigger);
            scheduler.scheduleJob(inMemoryDataJob, trigger);



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
