package com.sefryek.doublepizza.dto;

import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.model.SubCategory;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nima on 1/4/14.
 */
public class CategoryForDevice implements Comparable, Serializable {
    private String id; // webCategory:catId id;
    private String nameKeyEN;
    private String nameKeyFR;
    private String descriptionKey;
    private int sequence; // sequence
    private String imageURL;
    private List<SubCategory> subCategoryList;

    public CategoryForDevice(String id, String nameKeyEN, String nameKeyFR, String descriptionKey, int sequence, String imageURL) {
        this.id = id;
        this.nameKeyEN = nameKeyEN;
        this.nameKeyFR = nameKeyFR;
        this.descriptionKey = descriptionKey;
        this.sequence = sequence;
        this.imageURL = imageURL;
    }

    public CategoryForDevice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameKeyEN() {
        return nameKeyEN;
    }

    public void setNameKeyEN(String nameKeyEN) {
        this.nameKeyEN = nameKeyEN;
    }

    public String getNameKeyFR() {
        return nameKeyFR;
    }

    public void setNameKeyFR(String nameKeyFR) {
        this.nameKeyFR = nameKeyFR;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public int compareTo(Object object) {

        CategoryForDevice that = (CategoryForDevice) object;
        Integer thisSequence = this.getSequence();
        Integer thatSequence = that.getSequence();

        if (thisSequence > thatSequence) {
            return 1;
        } else if (thisSequence < thatSequence) {
            return -1;
        } else {
            return 0;
        }

    }
}
