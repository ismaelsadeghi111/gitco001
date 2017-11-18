package com.sefryek.doublepizza.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;

import java.util.Map;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.service.exception.DataLoadException;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 30, 2012
 * Time: 2:54:06 PM
 */
public class InMemeoryDataLoaderJob implements Job {
    private Logger logger = Logger.getLogger(InMemeoryDataLoaderJob.class);

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {

        Map dataMap = jobContext.getJobDetail().getJobDataMap();
        InMemoryData memoryData = (InMemoryData) dataMap.get(Constant.IN_MEMORY_DATA_LOADR_TASK_NAME);

        try {
            memoryData.loadData();

        } catch (DataLoadException e) {
            logger.error("Excpetion in loadData(), Quartz call,", e);
        }
    }
}
