package com.sefryek.doublepizza.rules;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.helpers.BasketItemHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 25, 2012
 * Time: 4:01:18 PM
 */

public class BlankPizzaReplacingRule {
    private BasketSingleItem basketSingleItem;
    private MenuSingleItem menuSingleItem;

    public BlankPizzaReplacingRule(BasketSingleItem basketSingleItem) {
        this.basketSingleItem = basketSingleItem;
        this.menuSingleItem = basketSingleItem.getSingle();
    }

    private MenuSingleItem findBlankPizza(List<MenuSingleItem> alternatives){
        if (alternatives != null)
            for(MenuSingleItem menuSingleItem : alternatives){
//                List defaultToppings = InMemoryData.getMenuSingleItemDefaultToppingsWithoutExclusives(menuSingleItem);
                Integer numberOfToppings = InMemoryData.getSingleItemNumberOfToppings(menuSingleItem.getId(), "");
                if (numberOfToppings == 0)
                    return menuSingleItem;
            }
        return null;
    }

    private boolean toppingIsSelected(Map<Integer, String> selectedToppingMap, Integer id){
        if (selectedToppingMap == null)
            return false;
        Set<Integer> toppingsId = selectedToppingMap.keySet();
        for(Integer toppingId : toppingsId){
            if (toppingId.equals(id))
                return true;
        }
        return false;
    }

    private List<MenuSingleItem> findMenuSingleItemParentAlternatives(){
        List<CombinedMenuItem> combinedMenuItemList = InMemoryData.getAllCombinedMenuItemList();
        if (combinedMenuItemList != null){
            for(CombinedMenuItem combinedMenuItem : combinedMenuItemList){
                List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
                if (menuSingleItemList != null){
                    for(int i = 0; i < menuSingleItemList.size(); i++){
                        MenuSingleItem targetMenuSingleItem = menuSingleItemList.get(i);
                        if (targetMenuSingleItem.getId().equals(this.menuSingleItem.getId()) &&
                                targetMenuSingleItem.getGroupId().equals(this.menuSingleItem.getGroupId()))
                            return combinedMenuItem.getAlternatives().get(i);
                    }
                }

                List<List<MenuSingleItem>> alternativesList = combinedMenuItem.getAlternatives();
                if (alternativesList != null){
                    for(List<MenuSingleItem> alternatives : alternativesList){
                        for(MenuSingleItem targetMenuSingleItem : alternatives){
                            if (targetMenuSingleItem.getId().equals(this.menuSingleItem.getId()) &&
                                    targetMenuSingleItem.getGroupId().equals(this.menuSingleItem.getGroupId()))
                                return alternatives;                            
                        }
                    }
                }                
                
            }
        }
        return new ArrayList<MenuSingleItem>();
    }

    private boolean allDefaultToppingsIsSelected(){
        Map<Integer, String> selectedToppingMap = basketSingleItem.getSelectedToppingMap();
        List<Topping> defaultToppings = InMemoryData.getMenuSingleItemDefaultToppings(menuSingleItem);
        for(Topping topping : defaultToppings){
            if (!toppingIsSelected(selectedToppingMap, topping.getId()))
                return false;
        }
        return true;
    }

    private BasketSingleItem create(List<MenuSingleItem> alternatives){
        MenuSingleItem menuSingleItem = findBlankPizza(alternatives);

        BasketSingleItem result = BasketItemHelper.createBasketSingleItem(menuSingleItem);
        result.setSelectedToppingMap(basketSingleItem.getSelectedToppingMap());
        return result;
    }

    public BasketSingleItem checkRule(){
        if (menuSingleItem == null)
            return null;

        List<MenuSingleItem> alternatives = findMenuSingleItemParentAlternatives();
        //it means menuSingleItem is member of a combined
        if (alternatives.size() > 0){
            boolean needToReplaceBlankPizza = !allDefaultToppingsIsSelected();
            if (needToReplaceBlankPizza)
                return create(alternatives);
        }           
        return basketSingleItem;
    }
}