package com.sefryek.doublepizza.dto;

import java.util.List;

/**
 * Created by Administrator on 6/21/2014.
 */
public class ToppingDataList {

    private String categoryId;
    private String categoryName;
    private String sequence;
    private String parentId;
    private String parentGroupId;
    private List<String> defaultToppingList;
    private List<ToppingSubCategoryPrice> toppingSubCategoryList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ToppingDataList() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public List<String> getDefaultToppingList() {
        return defaultToppingList;
    }

    public void setDefaultToppingList(List<String> defaultToppingList) {
        this.defaultToppingList = defaultToppingList;
    }

    public List<ToppingSubCategoryPrice> getToppingSubCategoryList() {
        return toppingSubCategoryList;
    }

    public void setToppingSubCategoryList(List<ToppingSubCategoryPrice> toppingSubCategoryList) {
        this.toppingSubCategoryList = toppingSubCategoryList;
    }
}
