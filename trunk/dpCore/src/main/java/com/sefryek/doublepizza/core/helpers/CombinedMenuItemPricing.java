package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.CombinedMenuItem;
import com.sefryek.doublepizza.model.MenuSingleItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jul 10, 2012
 * Time: 1:22:39 PM
 */

public class CombinedMenuItemPricing {
    
    private CombinedMenuItem combinedMenuItem;
    public CombinedMenuItemPricing(CombinedMenuItem combinedMenuItem){
        this.combinedMenuItem = combinedMenuItem;
    }

    private MenuSingleItem findInexpensiveItem(List<MenuSingleItem> menuSingleItemList){
        BigDecimal minPrice = new BigDecimal(1000000000);
        MenuSingleItem result = null;

        for (MenuSingleItem menuSingleItem : menuSingleItemList){
            BigDecimal itemPrice =  menuSingleItem.getPrice();
            if (itemPrice.compareTo(minPrice) == -1){
                minPrice = itemPrice;
                result = menuSingleItem;
            }
        }

        return result;
    }

    private MenuSingleItem findExpensiveItem(List<MenuSingleItem> menuSingleItemList){
        BigDecimal maxPrice = new BigDecimal(0);
        MenuSingleItem result = null;

        for (MenuSingleItem menuSingleItem : menuSingleItemList){
            BigDecimal itemPrice =  menuSingleItem.getPrice();
            if (itemPrice.compareTo(maxPrice) == 1){
                maxPrice = itemPrice;
                result = menuSingleItem;
            }
        }

        return result;
    }

    private List<Integer> findPizzaItems(){
        List<Integer> result = new ArrayList<Integer>();
        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
        int index = 0;
        for(MenuSingleItem menuSingleItem : menuSingleItemList){
            if (menuSingleItem.isPizza())
                result.add(index);
            index++;
        }
        return result;
    }

    private BigDecimal getCombinedPriceHasTowPizza(List<Integer> pizzaItemIndexList){
        if (pizzaItemIndexList.size() != Constant.COMBINED_2_PIZZA)
            return null;

        List<MenuSingleItem> selectedInexpensiveMenuSingleItem = new ArrayList<MenuSingleItem>();

        for(Integer index : pizzaItemIndexList){
            List<MenuSingleItem> menuSingleItemList = extractAllMenuSingleItem(index);
            selectedInexpensiveMenuSingleItem.add(findInexpensiveItem(menuSingleItemList));
        }

        BigDecimal expensivePizzaPrice = findExpensiveItem(selectedInexpensiveMenuSingleItem).getPrice();
        BigDecimal result = sumOfItemsPrice(false);
        result = result.add(expensivePizzaPrice);
        return result;
    }

    private BigDecimal sumOfItemsPrice(boolean withPizza){
        BigDecimal result = new BigDecimal(0);

        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
        for (int i = 0; i <= menuSingleItemList.size() - 1; i++) {
            if (!withPizza && menuSingleItemList.get(i).isPizza())
                continue;
            
            List<MenuSingleItem> alternativeMenuItemList = extractAllMenuSingleItem(i);
            MenuSingleItem inexpensiveItem = findInexpensiveItem(alternativeMenuItemList);
            result = result.add(inexpensiveItem.getPrice());
        }
        return result;        
    }

    private List<MenuSingleItem> extractAllMenuSingleItem(int index){
        List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();

        MenuSingleItem defaultMenuSingleItem = menuSingleItemList.get(index);
        List<MenuSingleItem> alternativeMenuItemList = combinedMenuItem.getAlternatives().get(index);
        List<MenuSingleItem> allMenuSingleItemList = new ArrayList<MenuSingleItem>(alternativeMenuItemList);
        allMenuSingleItemList.add(defaultMenuSingleItem);
        return allMenuSingleItemList;
    }

    public BigDecimal getDisplayingPrice(){
        if (combinedMenuItem == null)
            return null;
        
        BigDecimal displayingPrice;
        List<Integer> pizzaItemIndexList = findPizzaItems();
        if (pizzaItemIndexList.size() == Constant.COMBINED_2_PIZZA)
            displayingPrice = getCombinedPriceHasTowPizza(pizzaItemIndexList);
        else
            displayingPrice = sumOfItemsPrice(true);

        return displayingPrice;

    }
    
}