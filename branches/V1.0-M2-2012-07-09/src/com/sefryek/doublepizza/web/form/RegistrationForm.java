package com.sefryek.doublepizza.web.form;

import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;

import com.sefryek.common.util.serialize.StringUtil;
import com.sefryek.doublepizza.core.Constant;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 20, 2012
 * Time: 5:27:49 PM
 */
public class RegistrationForm extends ValidatorForm {

    private String title;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String verifyPassword;
    private String city;
    private String company;
    private String cellPhone1;
    private String cellPhone2;
    private String cellPhone3;
    private String ext;
    private String streetNo;
    private String street;
    private String suiteApt;
    private String building;
    private String doorCode;
    private String postalCode1;
    private String postalCode2;
    private String facebookUsername;
    private String twitterUsername;

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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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

    public String getDoorCode() {
        return doorCode;
    }

    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request) {


        ActionErrors errors = super.validate(actionMapping, request);

        if (postalCode1 == null || postalCode1.length() == 0 || postalCode2 == null || postalCode2.length() == 0)
            errors.add("postalCode", new ActionMessage("errors.required.postalcode"));

        else {
            String totalPostalCode = postalCode1.toUpperCase() + " " + postalCode2.toUpperCase();

            if (!validateCanadaPostalCode(totalPostalCode)) {

                errors.add("postalCode", new ActionMessage("errors.isNotValid.postalCode"));

            }
        }

        return errors;
    }


    private boolean validateCanadaPostalCode(String postalCode) {

        boolean isValid = false;
        String part1;
        String part2;

        if (postalCode == null)
            isValid = false;

        else if (postalCode.length() == Constant.MAX_POSTAL_CODE_LENGTH + 1) {

            int spaceIndex = postalCode.indexOf(" ");
            if (spaceIndex == -1)
                isValid = false;

            else {
                part1 = postalCode.substring(0, spaceIndex);
                part2 = postalCode.substring(spaceIndex + 1, postalCode.length());

                isValid = validatePart1(part1) && validatePart2(part2);
            }

        } else if (postalCode.length() == Constant.MAX_POSTAL_CODE_LENGTH) {
            part1 = postalCode.substring(0, 3);
            part2 = postalCode.substring(3, Constant.MAX_POSTAL_CODE_LENGTH);

            isValid = validatePart1(part1) && validatePart2(part2);

        } else {
            isValid = false;

        }

        return isValid;
    }

    private boolean validatePart1(String part1) {

        return part1.length() == 3 && (StringUtil.isCapitalLetter(part1.charAt(0))
                && StringUtil.isDigit(part1.charAt(1)) && StringUtil.isCapitalLetter(part1.charAt(2)));
    }

    private boolean validatePart2(String part2) {

        return part2.length() == 3 && (StringUtil.isDigit(part2.charAt(0))
                && StringUtil.isCapitalLetter(part2.charAt(1)) && StringUtil.isDigit(part2.charAt(2)));
    }


}
