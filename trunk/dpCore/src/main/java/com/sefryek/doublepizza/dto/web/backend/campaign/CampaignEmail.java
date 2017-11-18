package com.sefryek.doublepizza.dto.web.backend.campaign;

/**
 * User: Saeid.AmanZadeh
 * Date: 04/26/14
 * Time: 11:06 AM
 */


public class CampaignEmail  extends  DPEmailTemplate{

   private String emailAddress;
    private String userId;
    private String lang;


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CampaignEmail(String emailAddress, String userId) {

        this.emailAddress = emailAddress;
        this.userId = userId;
    }

    public CampaignEmail() {
    }
}
