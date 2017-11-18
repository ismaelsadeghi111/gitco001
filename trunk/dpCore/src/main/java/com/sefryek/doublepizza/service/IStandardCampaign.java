package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.dto.web.backend.campaign.CampaignEmailTemplate;
import com.sefryek.doublepizza.model.StandardCampaign;
import com.sefryek.doublepizza.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/13/13
 * Time: 2:04 PM
 */
public interface IStandardCampaign {
    public static String BEAN_NAME = "standardCampaignService";

    Integer doSaveStandardCampaign(StandardCampaign standardCampaign);
    int getCountStandardCampaign() throws DAOException;
    List<StandardCampaign> getStandardCampaign(int offset, int count);
    List<StandardCampaign> getStandardCampaignByNow();
    List<StandardCampaign> getSendingStandardCampaignByNow();
    List<StandardCampaign> getStandardCampaignById(int id);
    List<CampaignEmailTemplate> getUserEmailByStandardCampaign(StandardCampaign standardCampaign, String lang);
    List<User> getUsersForStandardCampaign(StandardCampaign standardCampaign);
    void doCompleteStandardCampaign(StandardCampaign standardCampaign);
    void saveStandardCampaignForUser(String campaignDate, Integer postalCampaign, List<User> users) throws DAOException;
    Map<String, String> getMenuItemCampaign();
    Integer saveChangedStandardCampaign(StandardCampaign standardCampaign);
    Integer saveChangedSubscribeUserCampaign(String userId, String reason);
}
