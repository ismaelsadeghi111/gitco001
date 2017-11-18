package com.sefryek.doublepizza.model;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 3, 2012
 * Time: 7:32:04 PM
 */
public class OrderNumberHandler {

    private Integer orderNumber = 0;
    private static OrderNumberHandler instance = null;

    public static OrderNumberHandler getInstance() {

        if (instance == null)
            instance = new OrderNumberHandler();

        return instance;
    }

    private OrderNumberHandler() {
    }

    public void setOrderNumber(Integer lastOrderNumber) {

        if (lastOrderNumber < orderNumber)
            lastOrderNumber = orderNumber;

        orderNumber = lastOrderNumber;
    }

    public Integer getNextOrderNumber(){
        orderNumber++;
        return orderNumber;

    }
}
