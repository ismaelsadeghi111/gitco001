package com.sefryek.doublepizza.model;

import javax.persistence.*;

/**
 * User: E_Ghasemi
 * Date: 1/27/14
 * Time: 5:15 PM
 */
@Entity
@javax.persistence.Table(name = "contact_info")
public class ContactInfo {
    public static enum AddressName{HOME, OFFICE}

    public ContactInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_info_id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "addressScreen_en",length = 1000)
    private String addressScreenEN;


    @Column(name = "streetNumber",length = 8)
    private String streetNo;

    @Column(length = 50)
    private String postalcode;

    @Column(name= "streetName",length = 50)
    private String street;

    @Column(length = 15)
    private String suiteAPT;

    @Column(length = 50)
    private String city;

    @Column(length = 15)
    private String building;

    @Column(length = 15)
    private String doorCode;

    @Column(length = 15,scale = 10)
    private String phone;

    @Column(length = 15)
    private String ext;

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

    public String getAddressScreenEN() {
        return addressScreenEN;
    }

    public void setAddressScreenEN(String addressScreenEN) {
        this.addressScreenEN = addressScreenEN;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
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

    public void setDoorCode(String dooCode) {
        this.doorCode = dooCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo that = (ContactInfo) o;

        if (addressScreenEN != null ? !addressScreenEN.equals(that.addressScreenEN) : that.addressScreenEN != null)
            return false;
        if (building != null ? !building.equals(that.building) : that.building != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (doorCode != null ? !doorCode.equals(that.doorCode) : that.doorCode != null) return false;
        if (ext != null ? !ext.equals(that.ext) : that.ext != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (postalcode != null ? !postalcode.equals(that.postalcode) : that.postalcode != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (streetNo != null ? !streetNo.equals(that.streetNo) : that.streetNo != null) return false;
        if (suiteAPT != null ? !suiteAPT.equals(that.suiteAPT) : that.suiteAPT != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (addressScreenEN != null ? addressScreenEN.hashCode() : 0);
        result = 31 * result + (streetNo != null ? streetNo.hashCode() : 0);
        result = 31 * result + (postalcode != null ? postalcode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (suiteAPT != null ? suiteAPT.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (doorCode != null ? doorCode.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (ext != null ? ext.hashCode() : 0);
        return result;
    }
}
