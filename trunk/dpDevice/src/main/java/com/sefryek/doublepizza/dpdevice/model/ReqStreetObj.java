package com.sefryek.doublepizza.dpdevice.model;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 14 09 2014
 * Time: 09:44
 */
public class ReqStreetObj {

    private String udid;
    private String version;
    private  String postalCode;
    private  String streetNo;

    public ReqStreetObj(){}
    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }
}
