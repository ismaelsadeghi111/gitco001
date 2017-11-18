package com.sefryek.doublepizza.core.helpers;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.common.util.serialize.StringUtil;

import java.util.*;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: Jun 7, 2012
 * Time: 11:30:04 AM
 */

public class SuggestionsHelper {
    
    private List<Suggestion> findSuggestionsByProductNo(String productNo){
        List<Suggestion> result = new ArrayList<Suggestion>();
        List<Suggestion> suggestionList = InMemoryData.getSuggestionList();
        for(Suggestion suggestion : suggestionList){
            if (suggestion.getProductNo() != null && productNo.equals(suggestion.getProductNo()))
                result.add(suggestion);
        }
        return result;
    }

    private List<Suggestion> getSuggestionsForBasketItem(BasketItem basketItem){
        List<Suggestion> result = new ArrayList<Suggestion>();

        if (basketItem.getClassType() == BasketSingleItem.class){
            BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();
            List<Suggestion> suggestionList = findSuggestionsByProductNo( StringUtil.putZeroToNumber(basketSingleItem.getId(),
                    Constant.MENUITEM_PRODUCTNO_DIGITS));
            result.addAll(suggestionList);
        }
        else {
            BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
            List<Suggestion> suggestionList = findSuggestionsByProductNo(StringUtil.putZeroToNumber(basketCombinedItem.getProductNoRef(),
                    Constant.MENUITEM_PRODUCTNO_DIGITS));
            result.addAll(suggestionList);            
        }
        return result;
    }

    private List<Suggestion> removeDuplicateSuggestions(List<Suggestion> suggestionList){
        Collections.sort(suggestionList, new SuggestionComperator());
        List<Suggestion> suggestionListNew = new ArrayList<Suggestion>(suggestionList);

        String preModifierId = "";
        BigDecimal prePrice = new BigDecimal(0);
        for (Suggestion suggestion : suggestionList){
            if (!preModifierId.equals(suggestion.getModifierId())){
                preModifierId = suggestion.getModifierId();
            }
            else{
                if (suggestion.getPrice().compareTo(prePrice) != -1)
                    suggestionListNew.remove(suggestion);
            }
            prePrice = suggestion.getPrice();
        }

        return suggestionListNew;
    }

    public static List<Suggestion> getSuggestionsForBasket(Basket basket){
        List<Suggestion> result = new ArrayList<Suggestion>();
        SuggestionsHelper self = new SuggestionsHelper();

        List<BasketItem> basketItemList = basket.getBasketItemList();
        for(BasketItem basketItem : basketItemList){
            List<Suggestion> suggestionList = self.getSuggestionsForBasketItem(basketItem);
            result.addAll(suggestionList);
        }

        return self.removeDuplicateSuggestions(result);
    }

    public static MenuSingleItem findMenuSingleItemFromSuggestionsById(String id){
        List<Suggestion> suggestionList = InMemoryData.getSuggestionList();
        for(Suggestion suggestion : suggestionList){
            MenuSingleItem menuSingleItem = suggestion.getMenuSingleItem();
            if (menuSingleItem != null && menuSingleItem.getId().equals(id)){
                return menuSingleItem;
            }
        }        

        return null;
    }


    public static void addConfirmedSuggestionsToBasket(Map<String, Integer> suggestions, Basket basket){
        List<BasketItem> basketItemList = basket.getBasketItemList();

        for (String id : suggestions.keySet()){
            MenuSingleItem menuSingleItem = findMenuSingleItemFromSuggestionsById(id);
            if (menuSingleItem != null){
                Integer quantity = suggestions.get(id);
                if (quantity != null && quantity > 0){
                    for(int i = 1; i <= quantity; i++){
                        BasketItem basketItem = BasketItemHelper.createBasketItem(menuSingleItem);
                        basketItemList.add(basketItem);
                    }
                }

            }
            
        }
        
    }
}

class SuggestionComperator implements Comparator {
    public int compare(Object o1, Object o2) {
        Suggestion suggestion1 = (Suggestion) o1;
        Suggestion suggestion2 = (Suggestion) o2;
        return suggestion1.getModifierId().compareTo(suggestion2.getModifierId());
    }
}