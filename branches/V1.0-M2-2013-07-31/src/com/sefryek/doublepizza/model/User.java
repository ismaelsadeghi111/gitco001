package com.sefryek.doublepizza.model;


import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.sefryek.doublepizza.core.Constant;

@Entity
@Table(name = Constant.USER_TBL_NAME)
public class User {

    public enum UserType {
        INDIVIDUAL, CORPORATE
    }

    public enum PreferredLanguage {
        EN, FR
    }

    public enum Title {
        FEMALE, MALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;

    @Column(name = Constant.USER_TITLE)
    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(name = Constant.USER_EMAIL, unique = true)
    private String email;

    @Column(name = Constant.USER_FIRST_NAME)
    private String firstName;

    @Column(name = Constant.USER_LAST_NAME)
    private String lastName;

    @Column(name = Constant.USER_PASSWORD)
    private String password;

    @Column(name = Constant.USER_COMPANY)
    private String company;

    @Column(name = Constant.USER_CITY)
    private String city;

    @Column(name = Constant.USER_POSTALCODE)
    private String postalCode;

    @Column(name = Constant.USER_STREET_NO)
    private String streetNo;

    @Column(name = Constant.USER_STREET_NAME)
    private String streetName;

    @Column(name = Constant.USER_SUITE_APT)
    private String suiteApt;

    @Column(name = Constant.USER_BUILDING)
    private String building;

    @Column(name = Constant.USER_DOOR_CODE)
    private String doorCode;

    @Column(name = Constant.USER_FACEBOOK_USERNAME)
    private String facebbookUsername;

    @Column(name = Constant.USER_TWITTER_USERNAME)
    private String twitterUsername;

    @Column(name = Constant.USER_PHONE)
    private String cellPhone;

    @Column(name = Constant.USER_EXT)
    private String ext;

    @Transient
    private boolean isRegistered;

    @Transient
    private Boolean reception;



    public User(Title title, String email, String firstName, String lastName, String password, String company,
                String city, String postalCode, String streetNo, String streetName,String suiteApt, String building, String doorCode,
                String facebbookUsername, String twitterUsername, String cellPhone, String ext) {
        
        this.title = title;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.company = company;
        this.city = city;
        this.postalCode = postalCode;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.suiteApt = suiteApt;
        this.building = building;
        this.doorCode = doorCode;
        this.facebbookUsername = facebbookUsername;
        this.twitterUsername = twitterUsername;
        this.cellPhone = cellPhone;
        this.ext = ext;
    }


    public User() {
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getSuiteApt() {
        return suiteApt;
    }

    public void setSuiteApt(String suiteApt) {
        this.suiteApt = suiteApt;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFacebbookUsername() {
        return facebbookUsername;
    }

    public void setFacebbookUsername(String facebbookUsername) {
        this.facebbookUsername = facebbookUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDoorCode() {
        return doorCode;
    }

    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public Boolean getReception() {
        return reception;
    }

    public void setReception(Boolean reception) {
        this.reception = reception;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
