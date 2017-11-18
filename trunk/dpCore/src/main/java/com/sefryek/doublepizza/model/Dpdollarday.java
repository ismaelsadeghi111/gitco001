package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.Constant;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 11/20/13
 * Time: 3:33 PM
 */
public class Dpdollarday {

    public Dpdollarday(Float sunday, Float monday, Float tuesday, Float wednesday, Float thursday, Float friday, Float saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    private Integer id;

    private Float sunday;

    private Float monday;

    private Float tuesday;

    private Float wednesday;

    private Float thursday;

    private Float friday;

    private Float saturday;

    private byte status;

    private Date creation_date;

    public Dpdollarday(Integer id, Float sunday, Float monday, Float tuesday, Float wednesday, Float thursday,
                       Float friday, Float saturday, byte status, Date creation_date) {
        this.id = id;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.status = status;
        this.creation_date = creation_date;
    }

    public Dpdollarday() {
    }

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
}
