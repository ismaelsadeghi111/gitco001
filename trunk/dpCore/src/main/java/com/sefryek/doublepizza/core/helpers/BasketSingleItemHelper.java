package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.rules.BlankPizzaReplacingRule;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Feb 15, 2012
 * Time: 2:36:18 PM
 */

//"BasketSingleItemHelper" is a wraper for calculate price of BasketSingleItem object
public class BasketSingleItemHelper{
    private BasketSingleItem basketSingleItem;
    private List<Topping> selectedToppings = new ArrayList<Topping>();
    private List<Topping> defaultToppings = new ArrayList<Topping>();
    private Map<Topping, String> allSelectedToppingsWithPart = new HashMap<Topping, String>();

    private void setDefaultToppings(MenuSingleItem menuSingleItem){               
        List<ToppingCategory> categories = menuSingleItem.getToppingCategoryList();
        if (categories != null)
            for(ToppingCategory item : categories){
                for(Integer id : item.getDefaultToppingList()){
                    Topping topping = findToppingByIdInCategory(id, item);
                    if (topping != null)
                        defaultToppings.add(topping);
                }
            }
    }

    private  Topping findToppingByIdInCategory(Integer id, ToppingCategory toppingCategory){
        for(ToppingSubCategory item : toppingCategory.getToppingSubCategoryList()){
            if (item.getClassType() == Topping.class &
                    ((Topping)item.getObject()).getId().equals(id)){
                return (Topping)item.getObject();
            }
        }
        return null;
    }

    private BigDecimal getDefaultToppingsPrice(MenuSingleItem menuSingleItem){
        BigDecimal defaultToppingsPrice = new BigDecimal(0);
        for (Topping topping : defaultToppings){
            defaultToppingsPrice = defaultToppingsPrice.add(topping.getPrice());
        }
        return defaultToppingsPrice;
    }

    private void findAndAddToppingsToList(MenuSingleItem menuSingleItem){
        selectedToppings.clear();
        List<ToppingCategory> categories = menuSingleItem.getToppingCategoryList();
        if (categories != null){
            Map<Integer, String> basketselectedToppings = basketSingleItem.getSelectedToppingMap();
            if (basketselectedToppings != null)
                for(Integer id :  basketselectedToppings.keySet()){
                    String value = basketselectedToppings.get(id);
                    for(ToppingCategory item : categories){
                        Topping topping = findToppingByIdInCategory(id, item);
                        if (topping != null){
                            selectedToppings.add(topping);
                            allSelectedToppingsWithPart.put(topping, value);
                        }
                    }
                }
        }
    }

    private BigDecimal removeDefaultToppingsAndGetSum(MenuSingleItem menuSingleItem){
        BigDecimal result = new BigDecimal(0);
        for(Topping topping : defaultToppings){
            if (selectedToppings.remove(topping))
                result = result.add(topping.getPrice());
        }
        return result;
    }

    private BigDecimal sumOfSelectedLeftToppingPrice(){
        BigDecimal result = new BigDecimal(0);
        for(Topping topping : selectedToppings){
            String part = allSelectedToppingsWithPart.get(topping);
            if (part != null && (part.equalsIgnoreCase(Constant.TOPPING_PART_FULL) || part.equalsIgnoreCase(Constant.TOPPING_PART_LEFT))){
                result = result.add(topping.getPrice());
            }
        }
        return result;
    }

    private BigDecimal sumOfSelectedRightToppingPrice(){
        BigDecimal result = new BigDecimal(0);
        for(Topping topping : selectedToppings){
            String part = allSelectedToppingsWithPart.get(topping);
            if (part != null && (part.equalsIgnoreCase(Constant.TOPPING_PART_FULL) || part.equalsIgnoreCase(Constant.TOPPING_PART_RIGHT))){
                result = result.add(topping.getPrice());
            }
        }
        return result;
    }

    private void removeItemsAsDefaultToppingsPriceDif(BigDecimal dif){
        int i = 0;
        while (selectedToppings.size() > i & dif.compareTo(BigDecimal.ZERO) > 0){
            Topping topping = selectedToppings.get(i);
            if (topping.getPrice().compareTo(dif) != 1){
                dif = dif.subtract(topping.getPrice());
                selectedToppings.remove(topping);
            }
            else
                i++;
        }
    }

    public BigDecimal getPrice(BasketSingleItem basketSingleItemParam){
        this.basketSingleItem = new BlankPizzaReplacingRule(basketSingleItemParam).checkRule();
        MenuSingleItem menuItem = this.basketSingleItem.getSingle();
        setDefaultToppings(menuItem); 
        BigDecimal defaultToppingsPrice = getDefaultToppingsPrice(menuItem);

        findAndAddToppingsToList(menuItem);

        BigDecimal removedDefaultToppingsSum = removeDefaultToppingsAndGetSum(menuItem);

        //means if defaultToppingsPrice > removedDefaultToppingsSum
        if (defaultToppingsPrice.compareTo(removedDefaultToppingsSum) == 1){
            defaultToppingsPrice = defaultToppingsPrice.subtract(removedDefaultToppingsSum);
            Collections.sort(selectedToppings, new ToppingPriceComperator());
            removeItemsAsDefaultToppingsPriceDif(defaultToppingsPrice);
        }

        BigDecimal freeToppingPrice = menuItem.getFreeToppingPrice().multiply(new BigDecimal(menuItem.getFreeToppingNo()));

        BigDecimal selectedLeftToppingPrice = sumOfSelectedLeftToppingPrice();
        BigDecimal selectedRightToppingPrice = sumOfSelectedRightToppingPrice();
        BigDecimal selectedToppingPrice = selectedLeftToppingPrice.compareTo(selectedRightToppingPrice) == 1 ?
                selectedLeftToppingPrice : selectedRightToppingPrice;

        selectedToppingPrice = selectedToppingPrice.subtract(freeToppingPrice);
        BigDecimal result = null;
        if (selectedToppingPrice.compareTo(new BigDecimal(0)) != 1 )
            result = menuItem.getPrice();
        else
            result = selectedToppingPrice.add(menuItem.getPrice());
        return result;
    }
}

//"ToppingPriceComperator" class is used for sorting a List of Toppings Objects according to their DESC "price" property
    class ToppingPriceComperator implements Comparator {
        public int compare(Object o1, Object o2) {
            Topping tp = (Topping) o1;
            return -tp.compareTo(o2);
        }
    }