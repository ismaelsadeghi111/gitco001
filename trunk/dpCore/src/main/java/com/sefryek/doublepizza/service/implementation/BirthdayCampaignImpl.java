package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CampaignDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmail;
import com.sefryek.doublepizza.model.BirthdayCampaign;
import com.sefryek.doublepizza.service.IBirthdayCampaign;

import java.text.ParseException;
import java.util.List;

/**
 * User: sepideh
 * Date: 1/9/14
 * Time: 1:57 PM
 */
public class BirthdayCampaignImpl implements IBirthdayCampaign {
    private CampaignDAO campaignDAO;

    public CampaignDAO getCampaignDAO() {
        return campaignDAO;
    }

    public void setCampaignDAO(CampaignDAO campaignDAO) {
        this.campaignDAO = campaignDAO;
    }

    @Override
    public void doSaveBirthdayCampaign(BirthdayCampaign campaign) {
        try {
            campaignDAO.saveBirthdayCampaign(campaign);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public int getCountBirthdayCampaign() throws DAOException {
        return campaignDAO.getCountBirthdayCampaign();
    }

    @Override
    public List<BirthdayCampaign> getBirthdayCampaign(int offset, int count) {
        try {
            return campaignDAO.getBirthdayCampaign(offset , count);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public List<CampaignEmail> getMaskedEmailCampaign() {
        try {
            return campaignDAO.getEmailCampaign();
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public int maskeEmails() throws DAOException, ParseException {
        return campaignDAO.maskeEmails();
    }
}
