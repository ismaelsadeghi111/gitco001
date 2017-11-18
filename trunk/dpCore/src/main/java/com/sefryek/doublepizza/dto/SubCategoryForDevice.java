package com.sefryek.doublepizza.dto;

import com.sefryek.doublepizza.model.MenuSingleItem;
import com.sefryek.doublepizza.model.SubCategory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2/12/14.
 */
public class SubCategoryForDevice implements Serializable {

    //public static enum IdType { ProductNo, ModifierID  }

    private String id;
    private String menuId;
    private String type;

    //single
    private String idType; // refer to modifierId
    private String groupId; // refer to modGroNo;
    private String nameKeyEN;
    private String nameKeyFR;


    private String descriptionKeyEN;
    private String descriptionKeyFR;
    private String imageURL;
    private String price;
    private int freeToppingNo;// ModLink freeItemNo;
    private BigDecimal freeToppingPrice;// ModLink freeItemPrice;
    private boolean isTwoForOne;
    private boolean isPortion;// is half
    private boolean isPizza;
//    private List<ToppingCategory> toppingCategoryList;
    private int sequence;
    private String size;
    private Boolean isRedeemable = false;

    //combined
    private String productNo; //MenuItem (small pizza) id;
    private List<MenuSingleItem> menuSingleItemList;
    private List<List<MenuSingleItem>> alternatives;
    private List<String> captionkeis;
    private Boolean preset;
    private Boolean isPrintable;
    private List<String> captionkeisEN;
    private List<String> captionkeisFR;
    private int maxToppingSize;
    //Category
    private List<SubCategory> subCategoryList;


    public SubCategoryForDevice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public String getDescriptionKeyEN() {
        return descriptionKeyEN;
    }

    public void setDescriptionKeyEN(String descriptionKeyEN) {
        this.descriptionKeyEN = descriptionKeyEN;
    }

    public String getDescriptionKeyFR() {
        return descriptionKeyFR;
    }

    public void setDescriptionKeyFR(String descriptionKeyFR) {
        this.descriptionKeyFR = descriptionKeyFR;
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

    public int getFreeToppingNo() {
        return freeToppingNo;
    }

    public void setFreeToppingNo(int freeToppingNo) {
        this.freeToppingNo = freeToppingNo;
    }

    public BigDecimal getFreeToppingPrice() {
        return freeToppingPrice;
    }

    public void setFreeToppingPrice(BigDecimal freeToppingPrice) {
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

//    public List<ToppingCategory> getToppingCategoryList() {
//        return toppingCategoryList;
//    }
//
//    public void setToppingCategoryList(List<ToppingCategory> toppingCategoryList) {
//        this.toppingCategoryList = toppingCategoryList;
//    }

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

    public List<MenuSingleItem> getMenuSingleItemList() {
        return menuSingleItemList;
    }

    public void setMenuSingleItemList(List<MenuSingleItem> menuSingleItemList) {
        this.menuSingleItemList = menuSingleItemList;
    }

    public List<List<MenuSingleItem>> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<List<MenuSingleItem>> alternatives) {
        this.alternatives = alternatives;
    }

    public List<String> getCaptionkeis() {
        return captionkeis;
    }

    public void setCaptionkeis(List<String> captionkeis) {
        this.captionkeis = captionkeis;
    }

    public Boolean getPreset() {
        return preset;
    }

    public void setPreset(Boolean preset) {
        this.preset = preset;
    }

    public Boolean getIsPrintable() {
        return isPrintable;
    }

    public void setIsPrintable(Boolean isPrintable) {
        this.isPrintable = isPrintable;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public List<String> getCaptionkeisEN() {
        return captionkeisEN;
    }

    public void setCaptionkeisEN(List<String> captionkeisEN) {
        this.captionkeisEN = captionkeisEN;
    }

    public List<String> getCaptionkeisFR() {
        return captionkeisFR;
    }

    public void setCaptionkeisFR(List<String> captionkeisFR) {
        this.captionkeisFR = captionkeisFR;
    }

    public int getMaxToppingSize() {
        return maxToppingSize;
    }

    public void setMaxToppingSize(int maxToppingSize) {
        this.maxToppingSize = maxToppingSize;
    }
}
