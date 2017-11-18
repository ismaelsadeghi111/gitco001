package com.sefryek.doublepizza.model;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 10, 2012
 * Time: 6:53:48 PM
 */
public class RegistrationInfo {

    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String company;
    private String address1;
    private String address2;
    private String city;
    private String postalCode;
    private String phoneNumber;
    private String facebookId;
    private String twitterId;
    private String birthDate;

    public RegistrationInfo(String title, String firstName, String lastName, String email, String password,
                            String confirmPassword, String company, String address1, String address2, String city,
                            String postalCode, String phoneNumber, String facebookId, String twitterId) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.company = company;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.facebookId = facebookId;
        this.twitterId = twitterId;
        this.birthDate =   birthDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String getBirthDate() { return birthDate;   }

    public void setBirthDate(String birthDate) {   this.birthDate = birthDate; }
}
