package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.model.ToppingSubCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Feb 26, 2012
 * Time: 1:46:18 PM
 */
public class ToppingCategoryAlt {
    private Integer id;
    private String nameKey;
    private List<ToppingSubCategory> toppingSubCategoryList;
    private Integer [][] toppingConflictArray;
    private Map<Integer, String> selectedToppingMap;
    private Boolean exclusive;

    public ToppingCategoryAlt(Integer id, String nameKey, List<ToppingSubCategory> toppingSubCategoryList, Boolean exclusive) {
        this.id = id;
        this.nameKey = nameKey;
        this.toppingSubCategoryList = toppingSubCategoryList;
        this.exclusive = exclusive;
        this.selectedToppingMap = new HashMap<Integer, String>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName(Locale loc) {

        return InMemoryData.getData(nameKey, loc);
    }

    public List<ToppingSubCategory> getToppingSubCategoryList() {
        return toppingSubCategoryList;
    }

    public void setToppingSubCategoryList(List<ToppingSubCategory> toppingSubCategoryList) {
        this.toppingSubCategoryList = toppingSubCategoryList;
    }

    public Integer[][] getToppingConflictArray() {
        return toppingConflictArray;
    }

    public void setToppingConflictArray(Integer[][] toppingConflictArray) {
        this.toppingConflictArray = toppingConflictArray;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Map<Integer, String> getSelectedToppingMap() {
        return selectedToppingMap;
    }

    public void setSelectedToppingMap(Map<Integer, String> selectedToppingMap) {
        this.selectedToppingMap = selectedToppingMap;
    }
}
