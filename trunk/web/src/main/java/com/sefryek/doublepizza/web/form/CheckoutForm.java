package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 27, 2012
 * Time: 2:00:14 PM
 */
public class CheckoutForm extends ValidatorForm {
    private Double redeemedDollar;

    public Double getRedeemedDollar() {
        return redeemedDollar;
    }

    public void setRedeemedDollar(Double redeemedDollar) {
        this.redeemedDollar = redeemedDollar;
    }
}
