//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : CombinedMenuItem.java
//  @ Date : 1/21/2012
//  @ Author : Sepehr
//
//
package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.core.helpers.CurrencyUtils;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.InMemoryData;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.io.Serializable;
import java.math.BigDecimal;

public class CombinedMenuItem implements Serializable {
    static final long serialVersionUID = 7550476485681805409L;
    private String productNo; //MenuItem (small pizza) id;
    private String groupId; //MenuItem groupId;
    private String nameKey;
    private String descriptionKey;
    private int sequence;
    private List<MenuSingleItem> menuSingleItemList;
    private List<List<MenuSingleItem>> alternatives;
    private List<String> captionkeis;
    private Boolean presetFlag;
    private String imageURl = Constant.NO_IMAGE_NAME;
    private Boolean isPrintable;

    public CombinedMenuItem(String productNo, String groupId, String nameKey, String descriptionKey, Integer sequence,
    boolean isPrintable, String imageURL) {
        this.productNo = productNo;
        this.groupId = groupId;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.sequence = sequence;
        this.isPrintable = isPrintable;
        this.imageURl = imageURL;

    }


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public List<List<MenuSingleItem>> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<List<MenuSingleItem>> alternatives) {
        this.alternatives = alternatives;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getPresetFlag() {
        return presetFlag;
    }

    public void setPresetFlag(Boolean presetFlag) {
        this.presetFlag = presetFlag;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getName(Locale loc) {
        return InMemoryData.getData(nameKey, loc);

    }

    public String getDescription(Locale loc) {
        return InMemoryData.getData(descriptionKey, loc);

    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<MenuSingleItem> getMenuSingleItemList() {
        return menuSingleItemList;
    }

    public void setMenuSingleItemList(List<MenuSingleItem> menuSingleItemList) {
        this.menuSingleItemList = menuSingleItemList;
    }

    public Boolean getPrintable() {
        return isPrintable;

    }

    public List<String> getCaptions(Locale loc) {

        List<String> captions = new ArrayList<String>(captionkeis.size());
        for (String captionKey : captionkeis)
            captions.add(InMemoryData.getData(captionKey, loc));

        return captions;
    }

    public List<String> getDistinctCaptions(Locale loc) {
        List<String> captions = getCaptions(loc);

        boolean found = false;
        List<String> distictCaptions = new ArrayList<String>();
        for (String caption : captions) {
            for (String distictCaption : distictCaptions) {
                found = caption.equals(distictCaption);
            }

            if (!found)
                distictCaptions.add(caption);
        }
        
        return distictCaptions;
    }


    public void setCaptionKies(List<String> captionKies) {
        this.captionkeis = captionKies;

    }

    public String getImageURl() {
        return imageURl;

    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;

    }

    //get max price in SingleMenuItems
    public BigDecimal getPrice() {
        BigDecimal maxPrice = new BigDecimal(0);
        for (MenuSingleItem item : menuSingleItemList) {
            BigDecimal itemPrice = item.getPrice();
            if (itemPrice.compareTo(maxPrice) == 1)
                maxPrice = itemPrice;
        }
        return maxPrice;
    }

    public String getFormattedPrice() {
        return CurrencyUtils.toMoney(this.getPrice());
    }
}
