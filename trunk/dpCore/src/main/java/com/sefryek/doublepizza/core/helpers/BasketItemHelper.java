package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.model.BasketItem;
import com.sefryek.doublepizza.model.BasketSingleItem;
import com.sefryek.doublepizza.model.MenuSingleItem;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 7, 2012
 * Time: 6:19:04 PM
 */
public class BasketItemHelper {
    public static BasketItem createBasketItem(MenuSingleItem menuSingleItem){
        BasketItem basketItem = new BasketItem();
        basketItem.setClassType(BasketSingleItem.class);

        BasketSingleItem basketSingleItem = createBasketSingleItem(menuSingleItem);
        basketItem.setObject(basketSingleItem);
        return basketItem;
    }

    public static BasketSingleItem createBasketSingleItem(MenuSingleItem menuSingleItem){
        BasketSingleItem basketSingleItem = new BasketSingleItem();
        basketSingleItem.setGroupId(menuSingleItem.getGroupId());
        basketSingleItem.setId(menuSingleItem.getId());
        return basketSingleItem;
    }
}
