package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.device.ServicesConstant;
import com.sefryek.common.MessageUtil;

import javax.persistence.*;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 7, 2012
 * Time: 10:15:28 AM
 */

@Entity
@Table(name = Constant.SUBSCRIBER_TBL_NAME)
public class Subscriber {

    public enum Gender {
        FEMALE, MALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;


    @Column(name = Constant.SUBSCRIBER_UDID)
    private String udid;

    @Column(name = Constant.SUBSCRIBER_DEVICE_TOCKEN)
    private String deviceTocken;

    @Column(name = Constant.SUBSCRIBER_DEVICE_TYPE)
    private String deviceType;

    @Column(name = Constant.SUBSCRIBER_LATITUDE)
    private String latitude;

    @Column(name = Constant.SUBSCRIBER_LONGITUDE)
    private String longitude;


    @Column(name = Constant.USER_EMAIL, unique = true)
    private String email;


    @Column(name = Constant.SUBSCRIBER_NAME)
    private String name;

    @Column(name = Constant.SUBSCRIBER_GENDER)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = Constant.SUBSCRIBER_RECEPTION)
    private Boolean reception;

//    public Subscriber(Integer id, String version, String udid, String deviceTocken, String deviceType, String latitude, String longitude, String email, String name, Gender gender) {
//        this.id = id;
//        this.version = version;
//        this.udid = udid;
//        this.deviceTocken = deviceTocken;
//        this.deviceType = deviceType;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.email = email;
//        this.name = name;
//        this.gender = gender;
//    }


    public Subscriber(String udid, String deviceTocken, String deviceType, String latitude, String longitude, String email,
                      String name, Gender gender, Boolean reception) {
        this.udid = udid;
        this.deviceTocken = deviceTocken;
        this.deviceType = deviceType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.reception = reception;
    }

    public Subscriber(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getDeviceTocken() {
        return deviceTocken;
    }

    public void setDeviceTocken(String deviceTocken) {
        this.deviceTocken = deviceTocken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getReception() {
        return reception;
    }

    public void setReception(Boolean reception) {
        this.reception = reception;
    }

    public String validateData(HttpServletRequest request) {
        String message = null;
        if (email.length() > ServicesConstant.THRESHOLD_MAIL_MAX_LEN) {
            message = MessageUtil.get("message.error.email.threshold", request.getLocale());
        }

        if (name != null && name.length() > ServicesConstant.THRESHOLD_NAME_MAX_LEN) {
            if (message == null)
                message = "";
            else
                message += "\n";

            message += MessageUtil.get("message.error.name.threshold", request.getLocale());


        }
        return message;
    }
}
