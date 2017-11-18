package com.sefryek.doublepizza.dpdevice.model;

/**
 * Created by Administrator on 4/21/2014.
 */
public class Birthday {

    private String dd;
    private String mm;
    private String yy;

    public Birthday() {
    }

    public Birthday(String dd, String mm, String yy) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }
}
