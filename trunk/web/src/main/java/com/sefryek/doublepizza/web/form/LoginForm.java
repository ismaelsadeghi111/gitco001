package com.sefryek.doublepizza.web.form;

import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 20, 2012
 * Time: 5:27:49 PM
 */
public class LoginForm extends ValidatorForm {

    private String email;
    private String password;
    private String emailForgotPassword;
    private String lang;
    private String userId;
    private String newPassword;
    private String verifyNewPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getEmailForgotPassword() {
        return emailForgotPassword;
    }

    public void setEmailForgotPassword(String emailForgotPassword) {
        this.emailForgotPassword = emailForgotPassword;
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


}