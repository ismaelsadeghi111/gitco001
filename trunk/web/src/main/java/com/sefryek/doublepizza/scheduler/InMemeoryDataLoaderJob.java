package com.sefryek.doublepizza.scheduler;

import com.sefryek.common.LogMessages;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

import java.util.Map;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.service.exception.DataLoadException;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 30, 2012
 * Time: 2:54:06 PM
 */
public class InMemeoryDataLoaderJob implements Job {
    private InMemoryData inMemoryData;
    private WebApplicationContext context;
    private Logger logger = Logger.getLogger(InMemeoryDataLoaderJob.class);

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {

        Map dataMap = jobContext.getJobDetail().getJobDataMap();
        InMemoryData memoryData = (InMemoryData) dataMap.get(Constant.IN_MEMORY_DATA_LOADR_TASK_NAME);
        inMemoryData = new InMemoryData();
        inMemoryData.initializeService(context);
        try {
            //loading data from DB to "menu"
            //Saeid AmanZadeh
            inMemoryData.runTimeEnReload = true;
            inMemoryData.runTimeFaReload = true;
            //---
            logger.info(LogMessages.START_OF_METHOD + "InMemeoryDataLoaderJob");
            memoryData.loadData();

        } catch (DataLoadException e) {
            logger.error("Excpetion in loadData(), Quartz call,", e);
        }
    }
}
