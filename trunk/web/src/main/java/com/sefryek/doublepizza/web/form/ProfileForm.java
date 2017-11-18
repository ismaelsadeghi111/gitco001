package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * User: E_Ghasemi
 * Date: 1/27/14
 * Time: 4:22 PM
 */
public class ProfileForm extends ValidatorForm {

    private Long id;
    private Integer userId;
    private String addressScreenEN;
    private String addressScreenFR;
    private String postalcode1;
    private String postalcode2;
    private String streetNo;
    private String street;
    private String suiteAPT;
    private String city;
    private String building;
    private String doorCode;
    private String phone1;
    private String phone2;
    private String phone3;
    private String ext;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String facebbookUsername;
    private String lastName;
    private String twitterUsername;
    private String company;
    private String birthDate;
    private String submitMode;
    private String title;
    private String subscribe;


    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddressScreenEN() {
        return addressScreenEN;
    }

    public void setAddressScreenEN(String addressScreenEN) {
        this.addressScreenEN = addressScreenEN;
    }

    public String getAddressScreenFR() {
        return addressScreenFR;
    }

    public void setAddressScreenFR(String addressScreenFR) {
        this.addressScreenFR = addressScreenFR;
    }


    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuiteAPT() {
        return suiteAPT;
    }

    public void setSuiteAPT(String suiteAPT) {
        this.suiteAPT = suiteAPT;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDoorCode() {
        return doorCode;
    }

    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getFacebbookUsername() {
        return facebbookUsername;
    }

    public void setFacebbookUsername(String facebbookUsername) {
        this.facebbookUsername = facebbookUsername;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSubmitMode() {
        return submitMode;
    }

    public void setSubmitMode(String submitMode) {
        this.submitMode = submitMode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostalcode1() {
        return postalcode1;
    }

    public void setPostalcode1(String postalcode1) {
        this.postalcode1 = postalcode1;
    }

    public String getPostalcode2() {
        return postalcode2;
    }

    public void setPostalcode2(String postalcode2) {
        this.postalcode2 = postalcode2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }
}
