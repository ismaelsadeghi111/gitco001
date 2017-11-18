package com.sefryek.doublepizza.model;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: May 13, 2012
 * Time: 10:51:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostalCode {
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;

    public PostalCode(String postalCode, String city, Double latitude, Double longitude) {
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
