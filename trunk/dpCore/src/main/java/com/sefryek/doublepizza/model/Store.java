package com.sefryek.doublepizza.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 1, 2012
 * Time: 4:55:27 PM
 */
public class Store {
    private String storeId;
    private String name;
    private String city;
    private String address1;
    private String address2;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private boolean webStatus;
    private List<WorkingHour> workingHours = new ArrayList<WorkingHour>();

    public Store(String storeId, String name, String city, String address1, String address2, String postalCode,
                 String imageUrl, Boolean webStatus, List<WorkingHour> workingHours) {
        this.storeId = storeId;
        this.name = name;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.imageUrl = imageUrl;
        this.webStatus = webStatus;
        this.workingHours = workingHours;
    }

    public Store(String name) {
        this.name = name;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<WorkingHour> getWorkingHours() {
        return workingHours;
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

    public void setWorkingHours(List<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(boolean webStatus) {
        this.webStatus = webStatus;
    }
}
