package com.sefryek.doublepizza.dto;

/**
 * Created by Administrator on 6/21/2014.
 */
public class ToppingSubCategoryInfo {

    private String id;
    private String nameKeyEN;
    private String nameKeyFR;
    private Integer webSequence;

    public ToppingSubCategoryInfo() {
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

    public Integer getWebSequence() {
        return webSequence;
    }

    public void setWebSequence(Integer webSequence) {
        this.webSequence = webSequence;
    }
}
