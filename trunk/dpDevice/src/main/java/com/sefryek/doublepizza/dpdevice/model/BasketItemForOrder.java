package com.sefryek.doublepizza.dpdevice.model;

import java.util.List;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 21 08 2014
 * Time: 14:06
 */
public class BasketItemForOrder {

    private  String  classType ;
    private  String  groupIdRefrence;
    private  String  productNoRefrence;
    private  String  quantity;
    List<BasketSingleItemForOrder> basketSingleItemList;

    public BasketItemForOrder() {
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getGroupIdRefrence() {
        return groupIdRefrence;
    }

    public void setGroupIdRefrence(String groupIdRefrence) {
        this.groupIdRefrence = groupIdRefrence;
    }

    public String getProductNoRefrence() {
        return productNoRefrence;
    }

    public void setProductNoRefrence(String productNoRefrence) {
        this.productNoRefrence = productNoRefrence;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<BasketSingleItemForOrder> getBasketSingleItemList() {
        return basketSingleItemList;
    }

    public void setBasketSingleItemList(List<BasketSingleItemForOrder> basketSingleItemList) {
        this.basketSingleItemList = basketSingleItemList;
    }
}
