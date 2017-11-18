package com.sefryek.doublepizza.dto.web.frontend.catering;

import com.sefryek.doublepizza.model.CateringOrder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 1/23/14
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CateringBasket {
    private List<CateringOrder> cateringOrders;

    public List<CateringOrder> getCateringOrders() {
        return cateringOrders;
    }

    public void setCateringOrders(List<CateringOrder> cateringOrders) {
        this.cateringOrders = cateringOrders;
    }
}
