package com.sefryek.doublepizza.model;

import java.util.Date;

/**
 * User: sepideh
 * Date: 12/31/13
 * Time: 9:44 PM
 */
public class PostalCampaign  {
    private int postalCampaign_id;
    private String campaign_title_en;
    private String campaign_title_fr;
    private int status;
    private Date campaign_date;
    private String menu_id;
    private String postalCode;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPostalCampaign_id() {
        return postalCampaign_id;
    }

    public void setPostalCampaign_id(int postalCampaign_id) {
        this.postalCampaign_id = postalCampaign_id;
    }

    public String getCampaign_title_en() {
        return campaign_title_en;
    }

    public void setCampaign_title_en(String campaign_title_en) {
        this.campaign_title_en = campaign_title_en;
    }

    public String getCampaign_title_fr() {
        return campaign_title_fr;
    }

    public void setCampaign_title_fr(String campaign_title_fr) {
        this.campaign_title_fr = campaign_title_fr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCampaign_date() {
        return campaign_date;
    }

    public void setCampaign_date(Date campaign_date) {
        this.campaign_date = campaign_date;
    }


    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
