package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: elahe-PC
 * Date: 22/11/13
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DpDollarForm extends ValidatorForm {

    private Float sunday;
    private Float monday;
    private Float tuesday;
    private Float wednesday;
    private Float thursday;
    private Float friday;
    private Float saturday;
    private byte status;
    private Date creation_date;
    private String startDate;
    private String endDate;
    private Float percentage;
    private Integer schaduleId;
    private Float minValue;
    private String pagingAction;
    private String submitMode;

    public Float getSunday() {
        return sunday;
    }

    public void setSunday(Float sunday) {
        this.sunday = sunday;
    }

    public Float getMonday() {
        return monday;
    }

    public void setMonday(Float monday) {
        this.monday = monday;
    }

    public Float getTuesday() {
        return tuesday;
    }

    public void setTuesday(Float tuesday) {
        this.tuesday = tuesday;
    }

    public Float getWednesday() {
        return wednesday;
    }

    public void setWednesday(Float wednesday) {
        this.wednesday = wednesday;
    }

    public Float getThursday() {
        return thursday;
    }

    public void setThursday(Float thursday) {
        this.thursday = thursday;
    }

    public Float getFriday() {
        return friday;
    }

    public void setFriday(Float friday) {
        this.friday = friday;
    }

    public Float getSaturday() {
        return saturday;
    }

    public void setSaturday(Float saturday) {
        this.saturday = saturday;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getSchaduleId() {
        return schaduleId;
    }

    public void setSchaduleId(Integer schaduleId) {
        this.schaduleId = schaduleId;
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public String getPagingAction() {
        return pagingAction;
    }

    public void setPagingAction(String pagingAction) {
        this.pagingAction = pagingAction;
    }

    public String getSubmitMode() {
        return submitMode;
    }

    public void setSubmitMode(String submitMode) {
        this.submitMode = submitMode;
    }
}

