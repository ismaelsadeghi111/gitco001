//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : MenuSingleItem.java
//  @ Date : 1/21/2012
//  @ Author : Sepehr
//
//
package com.sefryek.doublepizza.model;


import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.helpers.CurrencyUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class MenuSingleItem implements Serializable, Cloneable {

    public static enum IdType { ProductNo, ModifierID  }

    private String id; // refer to modifierId

    private IdType idType; // refer to modifierId
    private String groupId; // refer to modGroNo;
    private String nameKey;
    private String descriptionKey;
    private String imageURL;
    private BigDecimal price;
    private int freeToppingNo;// ModLink freeItemNo;
    private BigDecimal freeToppingPrice;// ModLink freeItemPrice;
    private boolean isTwoForOne;
    private boolean isPortion;// is half
    private boolean isPizza;
    private List<ToppingCategory> toppingCategoryList;
    private int sequence;
    private String size;
    private Boolean isRedeemable = false;

    private String productNo;

     public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }


    public MenuSingleItem(String id,IdType idType, String groupId, String nameKey, String descriptionKey, String imageURL, BigDecimal price, int freeToppingsNo,
                          BigDecimal freeToppingPrice, boolean twoForOne, boolean isPortion,String size, int sequence, Boolean isRedeemable) {
        this.id = id;
        this.idType=idType;
        this.groupId = groupId;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.imageURL = imageURL;
        this.price = price;
        this.freeToppingNo = freeToppingsNo;
        this.freeToppingPrice = freeToppingPrice;
        this.isTwoForOne = twoForOne;
        this.isPizza = isPortion;
        this.isPortion = false;
        this.sequence = sequence;
        this.size = size;
        this.isRedeemable = isRedeemable == null ? false : isRedeemable;
        /**
         * in first faze we need to disable half/half topping selection in UI for pizzas and also we need to know if an item is pizza or not.
         * and pizza is the only item that isportion is true for it. so we use the isportion to find if item is pizza or not.
         * and set isportion to false for all items because we want to disable half half topping selection in UI.
         */


    }

    public Boolean getIsRedeemable() {
        return isRedeemable;
    }

    public void setIsRedeemable(Boolean isRedeemable) {
        this.isRedeemable = isRedeemable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

     public String getName(Locale loc) {
         return InMemoryData.getData(nameKey, loc);

    }

    public String getDescription(Locale loc) {
        return InMemoryData.getData(descriptionKey, loc);

    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isTwoForOne() {
        return isTwoForOne;
    }

    public void setTwoForOne(boolean twoForOne) {
        this.isTwoForOne = twoForOne;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFreeToppingPrice() {
        return freeToppingPrice;
    }

    public void setFreeToppingPrice(BigDecimal freeToppingPrice) {
        this.freeToppingPrice = freeToppingPrice;
    }

    public List<ToppingCategory> getToppingCategoryList() {
        return toppingCategoryList;
    }

    public void setToppingCategoryList(List<ToppingCategory> toppingCategoryList) {
        this.toppingCategoryList = toppingCategoryList;
    }

    public int getFreeToppingNo() {
        return freeToppingNo;
    }

    public void setFreeToppingNo(int freeToppingNo) {
        this.freeToppingNo = freeToppingNo;
    }

    public boolean isPortion() {
        return isPortion;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormattedPrice(){
        return CurrencyUtils.toMoney(this.getPrice());
    }

    public boolean isPizza() {
        return isPizza;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Override
    public Object clone(){
        try {
            MenuSingleItem menuSingleItem = (MenuSingleItem)super.clone();
            menuSingleItem.id = id;
            menuSingleItem.idType = idType;
            menuSingleItem.groupId = groupId;
            menuSingleItem.nameKey = nameKey;
            menuSingleItem.descriptionKey = descriptionKey;
            menuSingleItem.imageURL = imageURL;
            menuSingleItem.price = price;
            menuSingleItem.freeToppingNo = freeToppingNo;
            menuSingleItem.freeToppingPrice = freeToppingPrice;
            menuSingleItem.isTwoForOne = isTwoForOne;
            menuSingleItem.isPizza = isPortion;
            menuSingleItem.isPortion = isPortion;
            menuSingleItem.sequence = sequence;
            menuSingleItem.size = size;
            return menuSingleItem;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
