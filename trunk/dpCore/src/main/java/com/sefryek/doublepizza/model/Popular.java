package com.sefryek.doublepizza.model;

import java.util.Locale;

/**
 * User: E_Ghasemi
 * Date: 12/13/13
 * Time: 9:36 AM
 */

public class Popular {

    private Integer  popularItemsId;
    private Integer  quantity;
    private Integer  priority;
    private Integer  status;
    private Integer  categoryId;
    private Integer  groupId;
    private Integer  menuitemId;
    private String  foodName;
    private String  foodNameEn;
    private String  foodNameFr;
    private String  imageUrl;
    private double  price;
    private String  descriptionEn;
    private String  descriptionFr;
    private Class type;
    private Boolean special = false;
    private Boolean twoforone = false;
    private Boolean preset = false;



    public Popular() {
    }
    public Popular( Integer quantity, String foodName , String imageUrl, Integer menuitemId,Integer categoryId,Integer groupId,Integer priority) {
        this.popularItemsId = popularItemsId;
        this.quantity = quantity;
        this.status = status;
        this.menuitemId = menuitemId;
        this.foodName = foodName;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.groupId = groupId;
        this.priority=priority;

        // TODO : special , twoforone and preset must be filled.
//        this.special = special;
//        this.twoforone = twoforone;
//        this.preset = preset;

        if (special || twoforone || preset ) {
            this.type = CombinedMenuItem.class;
        } else {
            this.type = MenuSingleItem.class;
        }
    }

    public String getDescription(Locale loc){
        if (loc.equals(Locale.FRANCE)) {
            return this.descriptionFr;
        } else {
            return this.descriptionEn;
        }
    }

    public String getFoodNameEn() {
        return foodNameEn;
    }

    public void setFoodNameEn(String foodNameEn) {
        this.foodNameEn = foodNameEn;
    }

    public String getFoodNameFr() {
        return foodNameFr;
    }

    public void setFoodNameFr(String foodNameFr) {
        this.foodNameFr = foodNameFr;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getTwoforone() {
        return twoforone;
    }

    public void setTwoforone(Boolean twoforone) {
        this.twoforone = twoforone;
    }

    public Boolean getPreset() {
        return preset;
    }

    public void setPreset(Boolean preset) {
        this.preset = preset;
    }

    public Integer getPopularItemsId() {
        return popularItemsId;
    }

    public void setPopularItemsId(Integer popularItemsId) {
        this.popularItemsId = popularItemsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMenuitemId() {
        return menuitemId;
    }

    public void setMenuitemId(Integer menuitemId) {
        this.menuitemId = menuitemId;
    }



    public String getFoodName( Locale loc) {
        if (loc.equals(Locale.FRANCE)||loc.equals(Locale.FRENCH)) {
            return this.foodNameFr;
        } else {
            return this.foodNameEn;
        }
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }
}
