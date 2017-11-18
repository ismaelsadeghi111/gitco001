package com.sefryek.doublepizza.dto;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 14 09 2014
 * Time: 16:37
 */
public class CouponForDevice {
    private String type;
    private float value;

    public CouponForDevice() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
