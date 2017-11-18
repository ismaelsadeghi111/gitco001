package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.PostalCampaign;

import java.util.List;

/**
 * User: sepideh
 * Date: 1/7/14
 * Time: 10:37 AM
 */
public interface IPostalCampaign {
    public static String BEAN_NAME = "postalCampaignService";

    void doSavePostalCampaign(PostalCampaign postalCampaign);
    int getCountPostalCampaign() throws DAOException;
    public List<PostalCampaign> getPostalCampaign(int offset, int count);
    public void completePostalCampaign(PostalCampaign postalCampaign);

}
