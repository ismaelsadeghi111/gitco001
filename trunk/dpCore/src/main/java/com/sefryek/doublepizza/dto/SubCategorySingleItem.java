package com.sefryek.doublepizza.dto;

import com.sefryek.doublepizza.model.ToppingCategory;
import java.util.List;

/**
 * Created by Administrator on 3/5/14.
 */
public class SubCategorySingleItem {


    private String id; // refer to modifierId

    private String idType; // refer to modifierId
    private String groupId; // refer to modGroNo;
    private String nameKeyEN;
    private String nameKeyFR;
    private String descriptionKey;
    private String imageURL;
    private String price;
    private String freeToppingNo;// ModLink freeItemNo;
    private String freeToppingPrice;// ModLink freeItemPrice;
    private boolean isTwoForOne;
    private boolean isPortion;// is half
    private boolean isPizza;
    private List<ToppingCategory> toppingCategoryList;
    private List toppingCategoryIdList;
    private int sequence;
    private String size;
    private Boolean isRedeemable = false;

    private String productNo;
//    private List<String> captionsEN;
//    private List<String> captionsFR;

    private String captionsEN;
    private String captionsFR;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNameKeyEN() {
        return nameKeyEN;
    }

    public void setNameKeyEN(String nameKeyEN) {
        this.nameKeyEN = nameKeyEN;
    }

    public String getNameKeyFR() {
        return nameKeyFR;
    }

    public void setNameKeyFR(String nameKeyFR) {
        this.nameKeyFR = nameKeyFR;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreeToppingNo() {
        return freeToppingNo;
    }

    public void setFreeToppingNo(String freeToppingNo) {
        this.freeToppingNo = freeToppingNo;
    }

    public String getFreeToppingPrice() {
        return freeToppingPrice;
    }

    public void setFreeToppingPrice(String freeToppingPrice) {
        this.freeToppingPrice = freeToppingPrice;
    }

    public boolean isTwoForOne() {
        return isTwoForOne;
    }

    public void setTwoForOne(boolean isTwoForOne) {
        this.isTwoForOne = isTwoForOne;
    }

    public boolean isPortion() {
        return isPortion;
    }

    public void setPortion(boolean isPortion) {
        this.isPortion = isPortion;
    }

    public boolean isPizza() {
        return isPizza;
    }

    public void setPizza(boolean isPizza) {
        this.isPizza = isPizza;
    }

    public List<ToppingCategory> getToppingCategoryList() {
        return toppingCategoryList;
    }

    public void setToppingCategoryList(List<ToppingCategory> toppingCategoryList) {
        this.toppingCategoryList = toppingCategoryList;
    }

    public List getToppingCategoryIdList() {
        return toppingCategoryIdList;
    }

    public void setToppingCategoryIdList(List toppingCategoryIdList) {
        this.toppingCategoryIdList = toppingCategoryIdList;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getIsRedeemable() {
        return isRedeemable;
    }

    public void setIsRedeemable(Boolean isRedeemable) {
        this.isRedeemable = isRedeemable;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getCaptionsEN() {
        return captionsEN;
    }

    public void setCaptionsEN(String captionsEN) {
        this.captionsEN = captionsEN;
    }

    public String getCaptionsFR() {
        return captionsFR;
    }

    public void setCaptionsFR(String captionsFR) {
        this.captionsFR = captionsFR;
    }
}
