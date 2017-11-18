package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 20, 2012
 * Time: 5:27:49 PM
 */
public class UserForm extends ValidatorForm {

    private String title;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String verifyPassword;
    private String address1;
    private String address2;
    private String street;
    private String streetNo;
    private String city;
    private String company;
    private String cellPhone1;
    private String cellPhone2;
    private String cellPhone3;
    private String postalCode1;
    private String postalCode2;
    private String facebookUsername;
    private String twitterUsername;
    private String suiteApt;
    private String addressName;
    private String doorCode;
    private String building;
    private String ext;
    private String subscribed;

    public String getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCellPhone1() {
        return cellPhone1;
    }

    public void setCellPhone1(String cellPhone1) {
        this.cellPhone1 = cellPhone1;
    }

    public String getCellPhone2() {
        return cellPhone2;
    }

    public void setCellPhone2(String cellPhone2) {
        this.cellPhone2 = cellPhone2;
    }

    public String getCellPhone3() {
        return cellPhone3;
    }

    public void setCellPhone3(String cellPhone3) {
        this.cellPhone3 = cellPhone3;
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

    public String getSuiteApt() {
        return suiteApt;
    }

    public void setSuiteApt(String suiteApt) {
        this.suiteApt = suiteApt;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFacebookUsername() {
        return facebookUsername;
    }

    public void setFacebookUsername(String facebookUsername) {
        this.facebookUsername = facebookUsername;
    }

    public String getPostalCode1() {
        return postalCode1;
    }

    public void setPostalCode1(String postalCode1) {
        this.postalCode1 = postalCode1;
    }

    public String getPostalCode2() {
        return postalCode2;
    }

    public void setPostalCode2(String postalCode2) {
        this.postalCode2 = postalCode2;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    //    @Override
//    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request) {
//
//        ActionErrors errors = new ActionErrors();
//        if (!getPassword().equals(getVerifyPassword())) {
////            errors.add("lable.registration.email", new ActionMessage("errors.required"));
//            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.registration.pass.dose.not.match"));
//        }
//        return errors;
//    }

}
