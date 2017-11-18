package com.sefryek.doublepizza.dto;

import java.util.List;

/**
 * Created by Administrator on 3/16/14.
 */
public class ToppingCategoryDevice {

    private ToppingCategoryInfo toppingCategoryInfo;
    private List<ToppingSubCategoryInfo> toppingSubCategoryInfo;
//    private List<ToppingDataList> toppingDataList;
    private ToppingDataList toppingDataList;

    public ToppingCategoryDevice() {
    }

    public ToppingCategoryInfo getToppingCategoryInfo() {
        return toppingCategoryInfo;
    }

    public void setToppingCategoryInfo(ToppingCategoryInfo toppingCategoryInfo) {
        this.toppingCategoryInfo = toppingCategoryInfo;
    }

    public List<ToppingSubCategoryInfo> getToppingSubCategoryInfo() {
        return toppingSubCategoryInfo;
    }

    public void setToppingSubCategoryInfo(List<ToppingSubCategoryInfo> toppingSubCategoryInfo) {
        this.toppingSubCategoryInfo = toppingSubCategoryInfo;
    }

    public ToppingDataList getToppingDataList() {
        return toppingDataList;
    }

    public void setToppingDataList(ToppingDataList toppingDataList) {
        this.toppingDataList = toppingDataList;
    }
}
