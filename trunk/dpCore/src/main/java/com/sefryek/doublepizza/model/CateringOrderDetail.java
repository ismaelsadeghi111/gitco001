package com.sefryek.doublepizza.model;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/23/14
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="catering_detail_order")
public class CateringOrderDetail implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catering_detail_order_id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;


    @ManyToOne(targetEntity = CateringOrder.class,optional = false)
    @JoinColumn(name = "catering_order_id")
    private CateringOrder cateringOrder;

    @MapsId("id")
    @JoinColumn(name="catering_id", referencedColumnName="catering_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Catering catering;



    public Catering getCatering() {
        return catering;
    }

    public void setCatering(Catering catering) {
        this.catering = catering;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CateringOrder getCateringOrder() {
        return cateringOrder;
    }

    public void setCateringOrder(CateringOrder cateringOrder) {
        this.cateringOrder = cateringOrder;
    }
}
