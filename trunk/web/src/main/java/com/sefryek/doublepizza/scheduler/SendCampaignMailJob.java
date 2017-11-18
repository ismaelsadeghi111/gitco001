package com.sefryek.doublepizza.scheduler;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dto.web.backend.campaign.SendMail;
import com.sefryek.doublepizza.service.IStandardCampaign;
import com.sefryek.doublepizza.service.IUserService;
import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Saeid AmanZadeh
 * Date: July 23, 204
 * Time: 1:54:06 PM
 */
public class SendCampaignMailJob implements Job {
    private Logger logger = Logger.getLogger(SendCampaignMailJob.class);

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        logger.info("Debug: Start SendCampaignMailJob");
        Map dataMap = jobContext.getJobDetail().getJobDataMap();
        SendMail sendMail = (SendMail) dataMap.get(Constant.SEND_CAMPAIGN_MAIL_TASK_NAME);

        IStandardCampaign standardCampaignService = (IStandardCampaign) dataMap.get(Constant.SEND_CAMPAIGN_SERVICE_NAME);
        IUserService userService = (IUserService)  dataMap.get(Constant.SEND_CAMPAIGN_USER_SERVICE_NAME);
        sendMail.doCheckCampaignAndSend(standardCampaignService, userService);

        logger.info("Debug: End of SendCampaignMailJob");
    }


}
