package com.sefryek.doublepizza.model;

import java.util.Date;

/**
 * User: sepideh
 * Date: 12/31/13
 * Time: 9:40 PM
 */
public class BirthdayCampaign  {
    private int birthdayCampaign_id;
    private String campaign_title_en;
    private String campaign_title_fr;
    private int status;
    private String campaign_note;
    private String menu_id;
    private int contactinfo_id;
    private String imageUrl;

    public BirthdayCampaign(String campaign_title_en, String campaign_title_fr, int status,
                            String campaign_note, String menu_id, int contactinfo_id) {
        this.campaign_title_en = campaign_title_en;
        this.campaign_title_fr = campaign_title_fr;
        this.status = status;
        this.campaign_note = campaign_note;
        this.menu_id = menu_id;
        this.contactinfo_id = contactinfo_id;
    }

    public BirthdayCampaign() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBirthdayCampaign_id() {
        return birthdayCampaign_id;
    }

    public void setBirthdayCampaign_id(int birthdayCampaign_id) {
        this.birthdayCampaign_id = birthdayCampaign_id;
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

    public String getCampaign_note() {
        return campaign_note;
    }

    public void setCampaign_note(String campaign_note) {
        this.campaign_note = campaign_note;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public int getContactinfo_id() {
        return contactinfo_id;
    }

    public void setContactinfo_id(int contactinfo_id) {
        this.contactinfo_id = contactinfo_id;
    }
}
