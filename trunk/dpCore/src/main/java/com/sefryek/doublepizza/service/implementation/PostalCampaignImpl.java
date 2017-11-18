package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CampaignDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.model.PostalCampaign;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.scheduler.EmailSenderScheduler;
import com.sefryek.doublepizza.service.IPostalCampaign;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: sepideh
 * Date: 1/7/14
 * Time: 10:41 AM
 */
public class PostalCampaignImpl implements IPostalCampaign {
    private CampaignDAO campaignDAO;
    private EmailSenderScheduler scheduler ;
    private CampaignMailRunnable mailRunner;

    public CampaignMailRunnable getMailRunner() {
        return mailRunner;
    }

    public void setMailRunner(CampaignMailRunnable mailRunner) {
        this.mailRunner = mailRunner;
    }

    public EmailSenderScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(EmailSenderScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public CampaignDAO getCampaignDAO() {
        return campaignDAO;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    public void completePostalCampaign(PostalCampaign postalCampaign) {
        try {
            Integer campaignId = campaignDAO.savePostalCampaign(postalCampaign) ;
            List<User> users = campaignDAO.getUsersForPostalCampaign(postalCampaign.getPostalCode());
            campaignDAO.savePostalCampaignForUsers(postalCampaign.getCampaign_date() ,campaignId , users);

            List<CampaignEmailTemplate> campaigns = campaignDAO.getOutboundEmailsByDate(postalCampaign.getCampaign_date());
            mailRunner.setCampaignEmailTemplates(campaigns);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.SECOND , 20);

            scheduler.schedule(mailRunner ,/* postalCampaign.getCampaign_date()*/ c.getTime());

        } catch (/*DAO*/Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doSavePostalCampaign(PostalCampaign postalCampaign) {
        try {
            campaignDAO.savePostalCampaign(postalCampaign);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCountPostalCampaign() throws DAOException {
        return campaignDAO.getCountPostalCampaign();
    }

    @Override
    public List<PostalCampaign> getPostalCampaign(int offset, int count) {
        try {
            return campaignDAO.getPostalCampaign(offset , count) ;
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
