package com.sefryek.doublepizza.model;

import com.sefryek.doublepizza.dao.DollarDAO;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by Hossein SadeghiFard on 1/22/14.
 */
@SqlResultSetMappings(value={
        @SqlResultSetMapping(name = "OrderDetailHistory.ResultSetMapping", entities = @EntityResult(entityClass = OrderDetailHistory.class, fields = {
                @FieldResult(name = "docNumber", column = "Docnumber"),
                @FieldResult(name = "taxable", column = "Taxable"),
                @FieldResult(name = "quantity", column = "Quantity"),
                @FieldResult(name = "itemKind", column = "ItemKind"),
                @FieldResult(name = "itemNumber", column = "ItemNumber"),
                @FieldResult(name = "itemName", column = "ItemName"),
                @FieldResult(name = "itemName2", column = "ItemName2"),
                @FieldResult(name = "itemPrice", column = "ItemPrice"),
                @FieldResult(name = "store", column = "Store"),
                @FieldResult(name = "twoforOne", column = "TwoforOne"),
                @FieldResult(name = "freeMods", column = "FreeMods"),
                @FieldResult(name = "freeModPrice", column = "FreeModPrice"),
                @FieldResult(name = "orgprice", column = "Orgprice"),
                @FieldResult(name = "presetmaster", column = "Presetmaster"),
                @FieldResult(name = "lev2", column = "Lev2"),
                @FieldResult(name = "iPrepare", column = "iPrepare"),
                @FieldResult(name = "itemnumberorg", column = "itemnumberorg"),
                @FieldResult(name = "seq", column = "seq"),
                @FieldResult(name = "productNo", column = "productNo"),
                @FieldResult(name = "modifierId", column = "modifierId"),
                @FieldResult(name = "modGroupNo", column = "modGroupNo"),
                @FieldResult(name = "baseItem", column = "baseItem"),
        }))
})

@NamedNativeQueries({
        @NamedNativeQuery(name = "OrderDetailHistory.findAllOrderDetailHistory",
                resultSetMapping = "OrderDetailHistory.ResultSetMapping",
                query = "select * from Web_Details_His wd ORDER BY wd.DocNumber desc",
                resultClass = OrderDetailHistory.class),

        @NamedNativeQuery(name = "OrderDetailHistory.findOrderDetailHistoryById",
                resultSetMapping = "OrderDetailHistory.ResultSetMapping",
                query = "select * from Web_Details_His wd where wd.Docnumber = :docnumber ORDER BY wd.DocNumber desc",
                resultClass = OrderDetailHistory.class)
})


public class OrderDetailHistory implements Serializable {
    private String docNumber;
    private Boolean taxable;
    private Integer quantity;
    private String itemKind;
    private String itemNumber;
    private String itemName;
    private String itemName2;
    private BigDecimal itemPrice;
    private String store;
    private Boolean twoforOne;
    private BigDecimal freeMods;
    private BigDecimal freeModPrice;
    private BigDecimal orgprice;
    private Boolean presetmaster;
    private Double lev2;
    private String iPrepare;
    private String itemnumberorg;
    private Integer seq;
    private String productNo;
    private String modifierId;
    private String modGroupNo;
    private String baseItem;
    private String imageUrl;               // Transient
    private Integer categoryId;            // can be a foreign key
    private String groupId;                // Transient
    private String titleEn;                // Transient
    private String titleFr;                // Transient
    private String descriptionEn;          // Transient
    private String descriptionFr;          // Transient
    private OrderHistory orderHistory;     // Transient


    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleFr() {
        return titleFr;
    }

    public void setTitleFr(String titleFr) {
        this.titleFr = titleFr;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public BigDecimal getOrgprice() {
        return orgprice;
    }

    public void setOrgprice(BigDecimal orgprice) {
        this.orgprice = orgprice;
    }

    public Boolean getPresetmaster() {
        return presetmaster;
    }

    public void setPresetmaster(Boolean presetmaster) {
        this.presetmaster = presetmaster;
    }

    public Double getLev2() {
        return lev2;
    }

    public void setLev2(Double lev2) {
        this.lev2 = lev2;
    }

    public String getiPrepare() {
        return iPrepare;
    }

    public void setiPrepare(String iPrepare) {
        this.iPrepare = iPrepare;
    }

    public String getItemnumberorg() {
        return itemnumberorg;
    }

    public void setItemnumberorg(String itemnumberorg) {
        this.itemnumberorg = itemnumberorg;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

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

    // Transient
    public String getName(Locale loc){
        if (loc.equals(Locale.FRENCH)) {
            return this.titleFr;

        } else if (loc.equals(Locale.ENGLISH)){
            return this.titleEn;

        }
        return this.titleEn;
    }

    // Transient
    public String getDescription(Locale loc){
        if (loc.equals(Locale.FRENCH)) {
            return this.descriptionFr;

        } else if (loc.equals(Locale.ENGLISH)){
            return this.descriptionEn;

        }
        return this.descriptionEn;
    }

}
