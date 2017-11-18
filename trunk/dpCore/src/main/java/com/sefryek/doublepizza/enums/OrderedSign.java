package com.sefryek.doublepizza.enums;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/9/13
 * Time: 11:35 AM
 */
public enum OrderedSign {
    EQUAL("="),
    SMALLER("<"),
    GREATER(">");

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private OrderedSign(String sign) {
        this.sign = sign;
    }
}
