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