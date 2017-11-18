package com.sefryek.doublepizza.dpdevice.model;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 14 09 2014
 * Time: 16:35
 */
public class ReqCouponObj {
    private String udid;
    private String version;
    private String coupon;

    public ReqCouponObj() {
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

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
