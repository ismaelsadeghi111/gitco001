package com.sefryek.doublepizza.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/23/14
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="catering_order")
public class CateringOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catering_order_id")
    private Long id;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "sub_total_price")
    private Float subTotalPrice;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "customer_note")
    private String customerNote;

//    @ForeignKey(name = "FK_CATERINGORDER_CATERINGORDERDETAIL")
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "cateringOrder")
    private List<CateringOrderDetail> cateringOrderDetails;


    @MapsId("id")
    @JoinColumn(name="catering_contact_info_id", referencedColumnName="catering_contact_info_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private CateringContactInfo cateringContactInfo;


    public CateringContactInfo getCateringContactInfo() {
        return cateringContactInfo;
    }

    public void setCateringContactInfo(CateringContactInfo cateringContactInfo) {
        this.cateringContactInfo = cateringContactInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public Float getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(Float subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public List<CateringOrderDetail> getCateringOrderDetails() {
        return cateringOrderDetails;
    }

    public void setCateringOrderDetails(List<CateringOrderDetail> cateringOrderDetails) {
        this.cateringOrderDetails = cateringOrderDetails;
    }



}
