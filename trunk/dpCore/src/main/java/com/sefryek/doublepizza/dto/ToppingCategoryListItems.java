package com.sefryek.doublepizza.dto;

import java.util.List;

/**
 * Created by Administrator on 3/16/14.
 */
public class ToppingCategoryListItems {

    private String menuType;
    private List<ToppingCategoryInfo> toppingCategory;
    private List<ToppingSubCategoryInfo> toppingSubCategory;
    private List<ToppingDataList> toppingDataList;
//    private List<ToppingCategoryDevice> ToppingCategoryList;

    public ToppingCategoryListItems() {
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<ToppingCategoryInfo> getToppingCategory() {
        return toppingCategory;
    }

    public void setToppingCategory(List<ToppingCategoryInfo> toppingCategory) {
        this.toppingCategory = toppingCategory;
    }

    public List<ToppingSubCategoryInfo> getToppingSubCategory() {
        return toppingSubCategory;
    }

    public void setToppingSubCategory(List<ToppingSubCategoryInfo> toppingSubCategory) {
        this.toppingSubCategory = toppingSubCategory;
    }

    public List<ToppingDataList> getToppingDataList() {
        return toppingDataList;
    }

    public void setToppingDataList(List<ToppingDataList> toppingDataList) {
        this.toppingDataList = toppingDataList;
    }
}
