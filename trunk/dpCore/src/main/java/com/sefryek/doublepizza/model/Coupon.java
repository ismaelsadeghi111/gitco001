package com.sefryek.doublepizza.model;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Created by Mostafa.Jamshid on 1/21/15.
 */
@Entity
@Table (name = "WebCoupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CouponId")
    private Long id;

    @Column(name = "Couponname")
    private String couponName;


    @Column(name = "Type")
    private String couponType;

    @Column(name = "Amount")
    private  Integer amount;

    @Column(name = "Morethan")
    private Integer moreThan;

    @Column(name = "Catindex")
    private Integer catIndex;

    @Column(name = "DateFrom")
    private String dateFrom;

    @Column(name = "DateTo")
    private String dateTo;

    @Column(name = "Couponseq")
    private Integer couponSeq;

    public Coupon() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getMoreThan() {
        return moreThan;
    }

    public void setMoreThan(Integer moreThan) {
        this.moreThan = moreThan;
    }

    public Integer getCatIndex() {
        return catIndex;
    }

    public void setCatIndex(Integer catIndex) {
        this.catIndex = catIndex;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getCouponSeq() {
        return couponSeq;
    }

    public void setCouponSeq(Integer couponSeq) {
        this.couponSeq = couponSeq;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
