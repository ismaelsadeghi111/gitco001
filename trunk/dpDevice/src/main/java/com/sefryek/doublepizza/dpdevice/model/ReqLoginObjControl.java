package com.sefryek.doublepizza.dpdevice.model;

/**
 * Created by Administrator on 4/20/2014.
 */
public class ReqLoginObjControl {

    private String udid;
    private String version;
    private String email;
    private String password;



    public ReqLoginObjControl() {
    }

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
