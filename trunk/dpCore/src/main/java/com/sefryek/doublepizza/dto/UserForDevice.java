package com.sefryek.doublepizza.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Nima on 4/27/2014.
 */
public class UserForDevice {

    private int userId;
    private String firstName;
    private String lastName;
    private String birthday;



    public enum Title {
        FEMALE, MALE
    }
    @Enumerated(EnumType.STRING)
    private Title title;
    private String company;
    private String email;
    private String subscribe;

    private  String dpDollar;


    public UserForDevice() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getDpDollar() {
        return dpDollar;
    }

    public void setDpDollar(String dpDollar) {
        this.dpDollar = dpDollar;
    }
}