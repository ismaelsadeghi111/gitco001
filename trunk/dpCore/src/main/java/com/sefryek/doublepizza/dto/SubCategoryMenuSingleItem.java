package com.sefryek.doublepizza.dto;

import java.util.List;

/**
 * Created by Administrator on 3/5/14.
 */
public class SubCategoryMenuSingleItem {

    private String menuType;
    private List<SubCategorySingleItem> menuSingleItemList;

    public SubCategoryMenuSingleItem() {
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<SubCategorySingleItem> getMenuSingleItemList() {
        return menuSingleItemList;
    }

    public void setMenuSingleItemList(List<SubCategorySingleItem> menuSingleItemList) {
        this.menuSingleItemList = menuSingleItemList;
    }
}
