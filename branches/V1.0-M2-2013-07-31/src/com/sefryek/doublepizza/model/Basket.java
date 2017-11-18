package com.sefryek.doublepizza.model;


import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Basket {
    private List<BasketItem> basketItemList;

    public Basket() {
        this.basketItemList = new ArrayList<BasketItem>();
    }

    public List<BasketItem> getBasketItemList() {
        return basketItemList;
    }

    public void setBasketItemList(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }

    public int getNumberOfItems() {
        if (this.basketItemList == null || this.basketItemList.size() == 0)
            return 0;

        else return this.basketItemList.size();
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);

        if (basketItemList != null && basketItemList.size() > 0) {
            for (BasketItem basketItem : basketItemList) {
                totalPrice = totalPrice.add(basketItem.getPrice());

            }
        }

        return totalPrice;
    }

    public BasketItem findBasketItemById(Integer indetifier, Class type) {
        if (basketItemList != null) {
            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getClassType() == type) {

                    if (basketItem.getClassType() == BasketSingleItem.class) {
                        if (((BasketSingleItem) basketItem.getObject()).getIdentifier().equals(indetifier))
                            return basketItem;
                    } else { //if (basketItem.getClassType() == BasketCombinedItem.class)
                        if (((BasketCombinedItem) basketItem.getObject()).getIdentifier().equals(indetifier))
                            return basketItem;
                    }

                }
            }
        }
        return null;
    }

    public BasketItem findBasketItemById(Integer indetifier){
        if (basketItemList != null) {
            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getIdentifier().equals(indetifier))
                    return basketItem;
            }
        }
        return null;
    }

    public boolean isEmpty(){
        return (basketItemList == null || basketItemList.size() == 0);
        
    }
}
