package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmail;
import com.sefryek.doublepizza.model.BirthdayCampaign;

import java.text.ParseException;
import java.util.List;

/**
 * User: sepideh
 * Date: 1/7/14
 * Time: 10:38 AM
 */
public interface IBirthdayCampaign {
    public static String BEAN_NAME = "birthdayCampaignService";
    public void doSaveBirthdayCampaign(BirthdayCampaign campaign);
    int getCountBirthdayCampaign() throws DAOException;
    public List<BirthdayCampaign> getBirthdayCampaign(int offset, int count);
    public List<CampaignEmail> getMaskedEmailCampaign();
    public int maskeEmails() throws DAOException, ParseException;

}
