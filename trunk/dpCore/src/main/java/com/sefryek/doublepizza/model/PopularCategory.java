package com.sefryek.doublepizza.model;

import java.math.BigDecimal;

/**
 * User: elahe-PC
 * Date: 15/01/14
 * Time: 2:17 PM
 */
public class PopularCategory {

    private int orderCount;
    private int categId;
    private int groupId;
    private int categSeq;
    private String  descEn;
    private String  descFr;
    private String  webImage;
    private String  productNo;
    private BigDecimal price;

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getCategId() {
        return categId;
    }

    public void setCategId(int categId) {
        this.categId = categId;
    }

    public int getCategSeq() {
        return categSeq;
    }

    public void setCategSeq(int categSeq) {
        this.categSeq = categSeq;
    }

    public String getDescEn() {
        return descEn;
    }

    public void setDescEn(String descEn) {
        this.descEn = descEn;
    }

    public String getDescFr() {
        return descFr;
    }

    public void setDescFr(String descFr) {
        this.descFr = descFr;
    }

    public String getWebImage() {
        return webImage;
    }

    public void setWebImage(String webImage) {
        this.webImage = webImage;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
