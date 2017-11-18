package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CampaignDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.model.StandardCampaign;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.scheduler.EmailSenderScheduler;
import com.sefryek.doublepizza.service.IStandardCampaign;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/13/13
 * Time: 2:04 PM
 */
public class StandardCampaignImpl implements IStandardCampaign {

    private CampaignDAO campaignDAO;
    private EmailSenderScheduler scheduler ;
    private CampaignMailRunnable mailRunner;

    public EmailSenderScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(EmailSenderScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public CampaignMailRunnable getMailRunner() {
        return mailRunner;
    }

    public void setMailRunner(CampaignMailRunnable mailRunner) {
        this.mailRunner = mailRunner;
    }

    public CampaignDAO getCampaignDAO() {
        return campaignDAO;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public Integer saveChangedSubscribeUserCampaign(String userId, String reason) {
        try {
            return campaignDAO.saveChangedSubscribeUserCampaign(userId, reason);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer doSaveStandardCampaign(StandardCampaign standardCampaign) {
        try {
            return campaignDAO.saveStandardCampaign(standardCampaign);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCountStandardCampaign() throws DAOException {
        return campaignDAO.getCountStandardCampaign();
    }


    @Override
    public List<CampaignEmailTemplate> getUserEmailByStandardCampaign(StandardCampaign standardCampaign, String lang) {
        try {
            return campaignDAO.getUserEmailByStandardCampaign(standardCampaign, lang);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StandardCampaign> getStandardCampaignById(int id) {
        try {
            return campaignDAO.getStandardCampaignById(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }


     @Override
    public List<StandardCampaign> getStandardCampaign(int offset, int count) {
        try {
            return campaignDAO.getStandardCampaign(offset , count);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StandardCampaign> getStandardCampaignByNow() {
        try {
            return campaignDAO.getStandardCampaignByNow();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StandardCampaign> getSendingStandardCampaignByNow() {
        try {
            return campaignDAO.getSendingStandardCampaignByNow();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsersForStandardCampaign(StandardCampaign standardCampaign) {
        return campaignDAO.getUsersForStandardCampaign(standardCampaign);
    }

    @Override
    public void doCompleteStandardCampaign(StandardCampaign standardCampaign) {
        Integer campaignId =  doSaveStandardCampaign(standardCampaign);
        List<User> users = getUsersForStandardCampaign(standardCampaign);
        try {
            saveStandardCampaignForUser(standardCampaign.getCampaign_date() , campaignId , users);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        List<CampaignEmailTemplate> emails = campaignDAO.getOutboundStandardEmailsByDate(standardCampaign.getCampaign_date());
        mailRunner.setCampaignEmailTemplates(emails);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND , 20);

        scheduler.schedule(mailRunner , c.getTime());

    }

    @Override
    public void saveStandardCampaignForUser(String campaignDate, Integer postalCampaign, List<User> users) throws DAOException {
        campaignDAO.saveStandardCampaignForUser(campaignDate , postalCampaign , users);
    }


    @Override
    public Map<String, String> getMenuItemCampaign(){
        return campaignDAO.getMenuItemCampaign();
    }

    @Override
    public Integer saveChangedStandardCampaign(StandardCampaign standardCampaign) {
        try {
            return campaignDAO.saveChangedStandardCampaign(standardCampaign);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
