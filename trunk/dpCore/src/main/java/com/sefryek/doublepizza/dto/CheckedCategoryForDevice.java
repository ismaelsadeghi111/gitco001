package com.sefryek.doublepizza.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nima on 1/4/14.
 */
public class CheckedCategoryForDevice implements Serializable {

    private String menuType;
    private List<CategoryForDevice> categoryList;

    public CheckedCategoryForDevice() {
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<CategoryForDevice> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryForDevice> categoryList) {
        this.categoryList = categoryList;
    }

}
