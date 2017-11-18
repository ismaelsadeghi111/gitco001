package com.sefryek.doublepizza.dpdevice.model;

import java.util.*;

/**
 * Created by Mostafa Jamshid
 * Project: doublepizza
 * Date: 21 08 2014
 * Time: 14:35
 */
public class BasketSingleItemForOrder {

    private String id; // refer to modifierId
    private String groupId; // refer to modGroNo;
    private Integer quantity;
    private List<HashMap<String,String>> selectedToppings;
    public BasketSingleItemForOrder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<HashMap<String, String>> getSelectedToppings() {
        return selectedToppings;
    }

    public void setSelectedToppings(List<HashMap<String, String>> selectedToppings) {
        this.selectedToppings = selectedToppings;
    }
}
