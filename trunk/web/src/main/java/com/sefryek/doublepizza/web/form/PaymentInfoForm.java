package com.sefryek.doublepizza.web.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 29, 2012
 * Time: 6:49:22 PM
 */
public class PaymentInfoForm extends ValidatorForm {

    private String paymentType;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
