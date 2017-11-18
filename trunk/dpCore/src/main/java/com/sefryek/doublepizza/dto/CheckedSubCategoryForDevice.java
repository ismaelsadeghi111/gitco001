package com.sefryek.doublepizza.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2/12/14.
 */
public class CheckedSubCategoryForDevice implements Serializable {

    private String menuType;
    private List<SubCategoryMiddleForDevice> subCategoryList;

    public CheckedSubCategoryForDevice() {
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<SubCategoryMiddleForDevice> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryMiddleForDevice> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
