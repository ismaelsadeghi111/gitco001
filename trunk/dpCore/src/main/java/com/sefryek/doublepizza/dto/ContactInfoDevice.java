package com.sefryek.doublepizza.dto;

/**
 * Created by Administrator on 5/24/2014.
 */
public class ContactInfoDevice {

    private Long id;
    private Integer userId;
    private String address;
    private String addressScreenEN;
    private String streetNo;
    private String postalcode;
    private String street;
    private String suiteAPT;
    private String city;
    private String building;
    private String doorCode;
    private String phone;
    private String ext;

    public ContactInfoDevice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressScreenEN() {
        return addressScreenEN;
    }

    public void setAddressScreenEN(String addressScreenEN) {
        this.addressScreenEN = addressScreenEN;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuiteAPT() {
        return suiteAPT;
    }

    public void setSuiteAPT(String suiteAPT) {
        this.suiteAPT = suiteAPT;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDoorCode() {
        return doorCode;
    }

    public void setDoorCode(String doorCode) {
        this.doorCode = doorCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
