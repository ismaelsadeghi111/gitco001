package com.sefryek.doublepizza.dto;

/**
 * Created by Administrator on 6/22/2014.
 */
public class ToppingCategoryInfo {
    private String id;
    private String nameKeyEN;
    private String nameKeyFR;
    private Boolean exclusive;
//    private List<ToppingDataList> toppingDataList;


    public ToppingCategoryInfo() {
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

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }
}
