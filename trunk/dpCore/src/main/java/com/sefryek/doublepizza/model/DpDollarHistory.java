package com.sefryek.doublepizza.model;

import net.sf.cglib.core.GeneratorStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hossein SadeghiFard on 2/1/14.
 */
// @javax.persistence.Table(name = "dpDollar_history")
@Entity
@Table(name = "dpdollar_history")
public class DpDollarHistory {
    public static enum Status{EARNED, SPENT}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dpdollar_history_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "order_id")
    private String orderId;         //-- It can be a forign key to the Web_Hear_His

    @Column(name = "amount")
    private Double amount;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "percentage")
    private Double percentage;

    @ManyToOne(targetEntity = User.class,optional = false)
    @ForeignKey(name = "FK_dpdollar_history_web_user")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "creation_date"/*,insertable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"*/)
    /*@Generated(GenerationTime.INSERT)*/
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
