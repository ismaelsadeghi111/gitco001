package com.sefryek.doublepizza.web.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/8/13
 * Time: 2:45 PM
 */
public class CampaignForm extends ValidatorForm {
    private String campaignId;
    private int orderInLastDays;
    private String orderSign;
    private int orderNumbers;
    private String food;
    private String imageFilePath;
    private String titleEn;
    private String titleFr;
    private String campaignDate;
    private int customOrderInLastDays;
    private int customNotOrderInLastDays;
    private String campaignType;
    private String imageFileName;
    private FormFile imageFile;
    private String imageFileNameEn;
    private FormFile imageFileEn;
    private String postalCode;
    private String pagingAction;
    private int standardCampaignId;
    private String status;
    private String note;
    private String subjectEn;
    private String subjectFr;
    private String userId;
    private String email;
    private String reason;
    private String checkedRreason;
    private String lang;
    private String itemHtmlEn;
    private String itemHtmlFr;

    public String getImageFileNameEn() {
        return imageFileNameEn;
    }

    public void setImageFileNameEn(String imageFileNameEn) {
        this.imageFileNameEn = imageFileNameEn;
    }

    public FormFile getImageFileEn() {
        return imageFileEn;
    }

    public void setImageFileEn(FormFile imageFileEn) {
        this.imageFileEn = imageFileEn;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCheckedRreason() {
        return checkedRreason;
    }

    public void setCheckedRreason(String checkedRreason) {
        this.checkedRreason = checkedRreason;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStandardCampaignId() {
        return standardCampaignId;
    }

    public void setStandardCampaignId(int standardCampaignId) {
        this.standardCampaignId = standardCampaignId;
    }

    public FormFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(FormFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getPagingAction() {
        return pagingAction;
    }

    public void setPagingAction(String pagingAction) {
        this.pagingAction = pagingAction;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public java.lang.String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(java.lang.String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(String campaignType) {
        this.campaignType = campaignType;
    }

    public int getOrderInLastDays() {
        return orderInLastDays;
    }

    public void setOrderInLastDays(int orderInLastDays) {
        this.orderInLastDays = orderInLastDays;
    }

    public String getOrderSign() {
        return orderSign;
    }

    public void setOrderSign(String orderSign) {
        this.orderSign = orderSign;
    }

    public int getOrderNumbers() {
        return orderNumbers;
    }

    public void setOrderNumbers(int orderNumbers) {
        this.orderNumbers = orderNumbers;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public String getCampaignDate() {
        return campaignDate;
    }

    public void setCampaignDate(String campaignDate) {
        this.campaignDate = campaignDate;
    }

    public int getCustomOrderInLastDays() {
        return customOrderInLastDays;
    }

    public void setCustomOrderInLastDays(int customOrderInLastDays) {
        this.customOrderInLastDays = customOrderInLastDays;
    }

    public int getCustomNotOrderInLastDays() {
        return customNotOrderInLastDays;
    }

    public void setCustomNotOrderInLastDays(int customNotOrderInLastDays) {
        this.customNotOrderInLastDays = customNotOrderInLastDays;
    }
}
