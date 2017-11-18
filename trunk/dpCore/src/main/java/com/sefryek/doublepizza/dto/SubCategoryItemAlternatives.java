package com.sefryek.doublepizza.dto;

import com.sefryek.doublepizza.model.MenuSingleItem;
import com.sefryek.doublepizza.dto.SubCategoryForDevice;
import java.util.List;

/**
 * Created by Nima on 3/5/14.
 */
public class SubCategoryItemAlternatives {

    private String menuType;
    private List<SubCategorySingleItem> alternativeList;

    public SubCategoryItemAlternatives() {
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List<SubCategorySingleItem> getAlternativeList() {
        return alternativeList;
    }

    public void setAlternativeList(List<SubCategorySingleItem> alternativeList) {
        this.alternativeList = alternativeList;
    }
}
