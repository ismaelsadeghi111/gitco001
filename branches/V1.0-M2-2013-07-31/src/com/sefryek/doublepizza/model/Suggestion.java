package com.sefryek.doublepizza.model;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 6, 2012
 * Time: 9:34:17 AM
 */

public class Suggestion  {

    private String productNo;
    private String modifierId;
    private BigDecimal price;
    private MenuSingleItem menuSingleItem;

    public Suggestion(String productNo, String modifierId, BigDecimal price) {
        this.productNo = productNo;
        this.modifierId = modifierId;
        this.price = price;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuSingleItem getMenuSingleItem() {
        return menuSingleItem;
    }

    public void setMenuSingleItem(MenuSingleItem menuSingleItem) {
        this.menuSingleItem = menuSingleItem;
    }
}