package com.sefryek.doublepizza.web.form;

import com.sefryek.doublepizza.model.OrderDetailHistory;
import com.sefryek.doublepizza.model.OrderHistory;
import com.sefryek.doublepizza.model.User;
import org.apache.struts.validator.ValidatorForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sepehr
 * Date: Jan 20, 2012
 * Time: 5:27:49 PM
 */

public class OrderForm extends ValidatorForm {

    private User user;
    private String orderDate;
    private String orderCaption;
    private String price;
    private String status;
    private List<OrderHistory> orderHistories;
    private List<OrderDetailHistory> orderHistoryDetails;


    public List<OrderDetailHistory> getOrderHistoryDetails() {
        return orderHistoryDetails;
    }

    public void setOrderHistoryDetails(List<OrderDetailHistory> orderHistoryDetails) {
        this.orderHistoryDetails = orderHistoryDetails;
    }

    public List<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public void setOrderHistories(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderCaption() {
        return orderCaption;
    }

    public void setOrderCaption(String orderCaption) {
        this.orderCaption = orderCaption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}