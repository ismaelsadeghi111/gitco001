package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.model.BasketCombinedItem;
import com.sefryek.doublepizza.model.BasketSingleItem;
import com.sefryek.doublepizza.model.CombinedMenuItem;
import com.sefryek.doublepizza.model.MenuSingleItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Feb 15, 2012
 * Time: 4:45:37 PM
 */
//"BasketCombinedItemHelper" is a wraper for calculate price of BasketCombinedItem object
public class BasketCombinedItemHelper {
    //returns price of BasketSingleItem with self helper class.
    private BigDecimal getBasketSingleItemPrice(BasketSingleItem item, List<MenuSingleItem> menuSingleItemList) {
        BasketSingleItemHelper helper = new BasketSingleItemHelper();
        return helper.getPrice(item);
    }

    private BasketSingleItem getExpensiveItem(BasketCombinedItem basketCombinedItem) {
        BigDecimal maxPrice = new BigDecimal(0);
        List<BasketSingleItem> singleItems = basketCombinedItem.getBasketSingleItemList();
        BasketSingleItem expensiveItem = null;
        CombinedMenuItem combinedMenuItem = basketCombinedItem.getCombined();
        int index = 0;
        //get max price
        for (BasketSingleItem item : singleItems) {
            List<List<MenuSingleItem>> alternatives = combinedMenuItem.getAlternatives();
            List<MenuSingleItem> menuSingleItemList = null;
            if (alternatives != null)
                menuSingleItemList = alternatives.get(index);

            BigDecimal itemPrice = getBasketSingleItemPrice(item, menuSingleItemList);
            /**
             * if an item isPortion it means that it's a pizza
             */
            if (itemPrice.compareTo(maxPrice) == 1 && item.getSingle().isPizza()) {
                maxPrice = itemPrice;
                expensiveItem = item;
            }
            index++;
        }
        return expensiveItem;
    }

    private BigDecimal getExpensiveItemPrice(BasketCombinedItem basketCombinedItem) {
        BasketSingleItem expensiveItem = getExpensiveItem(basketCombinedItem);
        return expensiveItem.getPrice();
    }

    private BigDecimal getSumOfBasketSingleItems(BasketCombinedItem basketCombinedItem, boolean withoutPizzasItems){
        BigDecimal price = new BigDecimal(0);
        List <BasketSingleItem> basketSingleItems = basketCombinedItem.getBasketSingleItemList();
        if (basketSingleItems != null) {
            for (BasketSingleItem basketSingleItem : basketSingleItems) {

                if (withoutPizzasItems){
                    MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
                    if (menuSingleItem.isPizza())
                        continue;
                }

                price = price.add(basketSingleItem.getPrice());
            }
        }

        return price;
    }

    private int getPizzaItemCount(BasketCombinedItem basketCombinedItem){
        int count = 0;
        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
        for(BasketSingleItem basketSingleItem : basketSingleItemList){
            MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
            if (menuSingleItem.isPizza())
                count++;
        }
        return count;
    }    

    public BigDecimal getPrice(BasketCombinedItem basketCombinedItem){
        BigDecimal price = new BigDecimal(0);
        List <BasketSingleItem> basketSingleItems = basketCombinedItem.getBasketSingleItemList();
        if (basketSingleItems != null) {
            int pizzaItemCount = getPizzaItemCount(basketCombinedItem);
            if (pizzaItemCount == 2){
                price = getExpensiveItemPrice(basketCombinedItem);
                BigDecimal priceWithoutPizzas = getSumOfBasketSingleItems(basketCombinedItem, true);
                price = price.add(priceWithoutPizzas);
            }
            else
                price = getSumOfBasketSingleItems(basketCombinedItem, false);
        }
        return price;
    }

    public BigDecimal getInvoicePrice(BasketCombinedItem basketCombinedItem, BasketSingleItem basketSingleItem) {

        /**
         * expensiveItem is among the pizzas. and price of other pizzas has to be considered as zero
         */
        int pizzaItemCount = getPizzaItemCount(basketCombinedItem);
        if (pizzaItemCount != 2)
            return basketSingleItem.getPrice();

        MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
        if (!menuSingleItem.isPizza())
            return basketSingleItem.getPrice();

        BasketSingleItem expensiveItem = getExpensiveItem(basketCombinedItem);
        if (basketSingleItem.getIdentifier().equals(expensiveItem.getIdentifier()))
            return basketSingleItem.getPrice();

        else
            return new BigDecimal(0);

    }
}
