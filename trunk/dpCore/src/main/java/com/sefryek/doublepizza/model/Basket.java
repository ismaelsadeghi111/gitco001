package com.sefryek.doublepizza.model;


import com.sefryek.doublepizza.core.Constant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Basket {
    private List<BasketItem> basketItemList;
    private Double defaultServiceCost = Constant.DEFAULT_SERVICE_COST;

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
            ListIterator  basketItemListIterator = basketItemList.listIterator();
            while (basketItemListIterator.hasNext()){
                totalPrice = totalPrice.add(((BasketItem)basketItemListIterator.next()).getPrice());
            }
        }

        return totalPrice;
    }

    public Double getDefaultServiceCost() {
        return defaultServiceCost;
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
