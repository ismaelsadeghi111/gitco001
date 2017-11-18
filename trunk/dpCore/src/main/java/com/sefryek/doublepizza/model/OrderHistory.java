package com.sefryek.doublepizza.model;

import org.hibernate.annotations.Loader;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Hossein SadeghiFard on 1/26/14.
 */
@SqlResultSetMappings(value={
        @SqlResultSetMapping(name = "OrderHistory.ResultSetMapping", entities = @EntityResult(entityClass = OrderHistory.class, fields = {
                @FieldResult(name = "docnumber", column = "Docnumber"),
                @FieldResult(name = "takeout", column = "Takeout"),
                @FieldResult(name = "cust", column = "Cust"),
                @FieldResult(name = "ext", column = "Ext"),
                @FieldResult(name = "todaydate", column = "Todaydate"),
                @FieldResult(name = "store", column = "Store"),
                @FieldResult(name = "total", column = "Total"),
                @FieldResult(name = "discountDesc", column = "DiscountDesc"),
                @FieldResult(name = "discount", column = "Discount"),
                @FieldResult(name = "docnumberorg", column = "Docnumberorg"),
                @FieldResult(name = "doctype", column = "Doctype"),
                @FieldResult(name = "dayno", column = "Dayno"),
                @FieldResult(name = "storeout", column = "Storeout"),
                @FieldResult(name = "pdate", column = "Pdate"),
        }))
})

@NamedNativeQueries({
        @NamedNativeQuery(name = "OrderHistory.findAllOrderHistory",
                resultSetMapping = "OrderHistory.ResultSetMapping",
                query = "select * from Web_Header_His wh ORDER BY wh.Todaydate desc ",
                resultClass = OrderHistory.class),

        @NamedNativeQuery(name = "OrderHistory.findOrderHistoryByEmail",
                resultSetMapping = "OrderHistory.ResultSetMapping",
                query = "select * from Web_Header_His wh on dh.Docnumber = wh.Docnumber  left join web_user u on wh.Cust = u.Phone and wh.Ext = u.Ext where u.Email = :email ORDER BY wh.Todaydate desc ",
                resultClass = OrderHistory.class)
})

/*@Entity
@Table(name="Web_Header_His", catalog = "${jdbc.database}")*/
public class OrderHistory implements Serializable {
   public enum DeliveryType {DELIVERY, PICKUP}

   private String docnumber;

   private Boolean takeout;

   private String cust;

   private String ext;

   @Temporal(TemporalType.DATE)
   private Date todaydate;

   private String store;

   private Float total;

   private String discountDesc;

   private Float discount;

   private String docnumberorg;

   private String doctype;

   private String dayno;

   private String storeout;

   private String pdate;

    private DeliveryType deliveryType;

   private List<OrderDetailHistory> orderDetailHistories;

   private User user;

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetailHistory> getOrderDetailHistories() {
        return orderDetailHistories;
    }

    public void setOrderDetailHistories(List<OrderDetailHistory> orderDetailHistories) {
        this.orderDetailHistories = orderDetailHistories;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }

    public Boolean getTakeout() {
        return takeout;
    }

    public void setTakeout(Boolean takeout) {
        this.takeout = takeout;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Date getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(Date todaydate) {
        this.todaydate = todaydate;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public String getDocnumberorg() {
        return docnumberorg;
    }

    public void setDocnumberorg(String docnumberorg) {
        this.docnumberorg = docnumberorg;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDayno() {
        return dayno;
    }

    public void setDayno(String dayno) {
        this.dayno = dayno;
    }

    public String getStoreout() {
        return storeout;
    }

    public void setStoreout(String storeout) {
        this.storeout = storeout;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }
}
