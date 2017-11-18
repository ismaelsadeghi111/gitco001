package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/11/13
 * Time: 3:07 PM
 */
public class PostalCampaignForm extends ValidatorForm {

    private String campaign_title_en;
    private String campaign_title_fr;
    private int status;
    private String campaignDate;
    private String menu_id;
    private String imageUrl;
    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getCampaignDate() {
        return campaignDate;
    }

    public void setCampaignDate(String campaignDate) {
        this.campaignDate = campaignDate;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
