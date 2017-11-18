package com.sefryek.doublepizza.dto;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 18 08 2014
 * Time: 14:19
 */
public class OrderInfo {
    private String docNumber;
    private  Boolean isNewUser;


    public OrderInfo() {

    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
