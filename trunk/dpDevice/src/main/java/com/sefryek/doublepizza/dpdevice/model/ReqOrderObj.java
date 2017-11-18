package com.sefryek.doublepizza.dpdevice.model;

import java.util.List;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 18 08 2014
 * Time: 11:21
 */
public class ReqOrderObj {
    private String udid;
    private String version;
    private String addressId;
    private List<BasketItemForOrder> basketItem;
    private String deliveryType;
    private String orderPrice;
    private String paymentType;
    private String storeId;
    private String userId;
    private String deliveryNote;
    private String deliveryTime;
    private String earnedDPD;
    private String spentDPD;
    private String pickupPhone;
    private String pickupExt;
    private String discount;
    private String discountDescription;




    public ReqOrderObj() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<BasketItemForOrder> getBasketItem() {
        return basketItem;
    }

    public void setBasketItem(List<BasketItemForOrder> basketItem) {
        this.basketItem = basketItem;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getEarnedDPD() {
        return earnedDPD;
    }

    public void setEarnedDPD(String earnedDPD) {
        this.earnedDPD = earnedDPD;
    }

    public String getSpentDPD() {
        return spentDPD;
    }

    public void setSpentDPD(String spentDPD) {
        this.spentDPD = spentDPD;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }

    public String getPickupExt() {
        return pickupExt;
    }

    public void setPickupExt(String pickupExt) {
        this.pickupExt = pickupExt;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }
}
