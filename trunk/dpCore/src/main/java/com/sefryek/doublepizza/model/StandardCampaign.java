package com.sefryek.doublepizza.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/13/13
 * Time: 10:55 AM
 */
public class StandardCampaign {
    private int standardcampaign_id;
    private String campaign_title_en;
    private String campaign_title_fr;
    private String status;
    private String campaign_date;
    private String menu_id;
    private boolean ordered;
    private int orderNumbers;
    private String imageUrl;
    private String imageUrlEn;
    private String orderType;
    private int orderDays;
    private String orderSign;
    private String note;
    private String newStatus;
    private String subjectEn;
    private String subjectFr;
    private String sendingStat;
    private String itemHtmlEn;
    private String itemHtmlFr;


    public String getImageUrlEn() {
        return imageUrlEn;
    }

    public void setImageUrlEn(String imageUrlEn) {
        this.imageUrlEn = imageUrlEn;
    }

    public String getItemHtmlEn() {
        return itemHtmlEn;
    }

    public void setItemHtmlEn(String itemHtmlEn) {
        this.itemHtmlEn = itemHtmlEn;
    }

    public String getItemHtmlFr() {
        return itemHtmlFr;
    }

    public void setItemHtmlFr(String itemHtmlFr) {
        this.itemHtmlFr = itemHtmlFr;
    }

    public String getSendingStat() {
        return sendingStat;
    }

    public void setSendingStat(String sendingStat) {
        this.sendingStat = sendingStat;
    }

    public String getSubjectEn() {
        return subjectEn;
    }

    public void setSubjectEn(String subjectEn) {
        this.subjectEn = subjectEn;
    }

    public String getSubjectFr() {
        return subjectFr;
    }

    public void setSubjectFr(String subjectFr) {
        this.subjectFr = subjectFr;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderSign() {
        return orderSign;
    }

    public void setOrderSign(String orderSign) {
        this.orderSign = orderSign;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public StandardCampaign() {
    }

    public StandardCampaign(String campaign_title_en, String campaign_title_fr, String status, String campaign_date, String menuId , boolean ordered ,
                            int orderNumbers) {
        this.campaign_title_en = campaign_title_en;
        this.campaign_title_fr = campaign_title_fr;
        this.status = status;
        this.campaign_date = campaign_date;
        this.menu_id=menuId;
        this.ordered = ordered;
        this.orderNumbers = orderNumbers;
    }

    public int getStandardcampaign_id() {
        return standardcampaign_id;
    }

    public void setStandardcampaign_id(int standardcampaign_id) {
        this.standardcampaign_id = standardcampaign_id;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCampaign_date() {
        return campaign_date;
    }

    public void setCampaign_date(String campaign_date) {
        this.campaign_date = campaign_date;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public int getOrderNumbers() {
        return orderNumbers;
    }

    public void setOrderNumbers(int orderNumbers) {
        this.orderNumbers = orderNumbers;
    }

    public int getOrderDays() {
        return orderDays;
    }

    public void setOrderDays(int orderDays) {
        this.orderDays = orderDays;
    }
}
