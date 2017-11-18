package com.sefryek.doublepizza.enums;

/**
 * Created with IntelliJ IDEA.
 * User: S_Abedin
 * Date: 12/13/13
 * Time: 1:52 PM
 */
public enum CampaignType {
    CustomerOrder,
    CustomerNotOrder,
    customizedOrdered,
    allUsers;

    public static CampaignType  getType(String s){
       for(CampaignType c : CampaignType.values()) {
           if(c.name().equals(s)) {
               return c;
           }
       }
        return CustomerOrder;
    }
}
