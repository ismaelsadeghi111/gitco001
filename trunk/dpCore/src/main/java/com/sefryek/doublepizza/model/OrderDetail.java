package com.sefryek.doublepizza.model;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import java.math.BigDecimal;

/**
 * Created by Hossein Sadeghi Fard on 1/22/14.
 */

public class OrderDetail {
    private String docNumber;
    private Integer quantity;
    private String itemKind;
    private String itemNumber;
    private String itemName;
    private String itemName2;
    private BigDecimal itemPrice;
    private Boolean twoforOne;
    private BigDecimal freeMods;
    private BigDecimal freeModPrice;
    private String productNo;
    private String modifierId;
    private String modGroupNo;
    private String baseItem;
    private Integer level2;
    private String imageUrl;
    private Order order;

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemKind() {
        return itemKind;
    }

    public void setItemKind(String itemKind) {
        this.itemKind = itemKind;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName2() {
        return itemName2;
    }

    public void setItemName2(String itemName2) {
        this.itemName2 = itemName2;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getTwoforOne() {
        return twoforOne;
    }

    public void setTwoforOne(Boolean twoforOne) {
        this.twoforOne = twoforOne;
    }

    public BigDecimal getFreeMods() {
        return freeMods;
    }

    public void setFreeMods(BigDecimal freeMods) {
        this.freeMods = freeMods;
    }

    public BigDecimal getFreeModPrice() {
        return freeModPrice;
    }

    public void setFreeModPrice(BigDecimal freeModPrice) {
        this.freeModPrice = freeModPrice;
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

    public String getModGroupNo() {
        return modGroupNo;
    }

    public void setModGroupNo(String modGroupNo) {
        this.modGroupNo = modGroupNo;
    }

    public String getBaseItem() {
        return baseItem;
    }

    public void setBaseItem(String baseItem) {
        this.baseItem = baseItem;
    }

    public Integer getLevel2() {
        return level2;
    }

    public void setLevel2(Integer level2) {
        this.level2 = level2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
