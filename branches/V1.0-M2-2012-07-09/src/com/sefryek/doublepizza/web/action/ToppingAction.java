package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.Globals;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt;
import com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper;
import com.sefryek.doublepizza.InMemoryData;

import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Feb 25, 2012
 * Time: 6:01:38 PM
 */
public class ToppingAction extends DispatchAction {
    private static Logger logger;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        logger = Logger.getLogger(ToppingAction.class);
    }

    protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        return null;
    }


    public ActionForward getNewPriceByNewToppings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                  HttpServletResponse response) {
        Double price;

        String singleItemIdStr = request.getParameter("singleItemId");
        String singleItemGroupIdStr = request.getParameter("singleItemGroupIdStr");
        String selectedToppingsStr = request.getParameter("toppings");

        //for getting new price we just need the toppings ids(the state is not important[left, right, full]), so we get a list of ids
        Map<Integer, String> selectedToppingsIdStateMap = getSelectedToppingsMap(selectedToppingsStr);

        {
            BasketSingleItem basketSingleItem = new BasketSingleItem();
            basketSingleItem.setId(singleItemIdStr);
            basketSingleItem.setGroupId(singleItemGroupIdStr);
            basketSingleItem.setSelectedToppingMap(selectedToppingsIdStateMap);
            price = basketSingleItem.getPrice().doubleValue();
        }

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.write(price.toString());
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;

    }

    //requestToppings
    public ActionForward getSingleItemToppings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                               HttpServletResponse response) {
        Double price = null;
        String itemName = null;
        String imageAddress = "";
        HttpSession session = request.getSession();

        String singleAltId = request.getParameter("id");
        String singleAltGroupId = request.getParameter("groupId");

        MenuSingleItem menuSingle = InMemoryData.findMenuSingleItemByIdAndGroupId(singleAltId, singleAltGroupId);


        String basketSingleItemIdentifier = request.getParameter("singleBasketItemId");

        String itemTypeStr = request.getParameter("type");

        FrontendAction.ClassTypeEnum itemType = FrontendAction.ClassTypeEnum.valueOf(itemTypeStr);

//        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, itemType);

        //default of a default( (combined || category) || singleBasket)
        if (menuSingle == null) {

            //defaults of a defaults (combined || category)
            //user just clicked on a combined or category item, so default toppings of a default item should be loaded
            if (basketSingleItemIdentifier == null) {
                List<ToppingCategory> allTopCatList = null;
                List<ToppingCategory> exclusiveTopCatList = new ArrayList<ToppingCategory>();
                List<ToppingCategory> noneExclusiveTopCatList = new ArrayList<ToppingCategory>();

                switch (itemType) {
                    case CATEGORY: {

                        Category subCategory = (Category) session.getAttribute(Constant.LAST_SUB_CATEGORY);
                        imageAddress = subCategory.getImageURL();
                        List<SubCategory> subCatList = subCategory.getSubCategoryList();
                        SubCategory subCatItem = subCatList.get(0);
                        if (subCatItem.getType().equals(CombinedMenuItem.class)) {
                            CombinedMenuItem combinedItem = (CombinedMenuItem) subCatItem.getObject();
                            List<MenuSingleItem> singleItemList = combinedItem.getMenuSingleItemList();
                            MenuSingleItem singleItem = singleItemList.get(0);
                            allTopCatList = singleItem.getToppingCategoryList();
                            price = menuSingle.getPrice().doubleValue();
                            itemName = menuSingle.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                        } else if (subCatItem.getType().equals(MenuSingleItem.class)) {
                            MenuSingleItem singleItem = (MenuSingleItem) subCatItem.getObject();
                            allTopCatList = singleItem.getToppingCategoryList();
                            price = singleItem.getPrice().doubleValue();
                            itemName = singleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                        } else {
                            allTopCatList = new ArrayList<ToppingCategory>();
                        }
                        break;
                    }
                    case COMBINED: {
                        CombinedMenuItem combinedItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
                        imageAddress = combinedItem.getImageURl();
                        MenuSingleItem firstSingleItemInCombined = combinedItem.getMenuSingleItemList().get(0);
                        allTopCatList = firstSingleItemInCombined.getToppingCategoryList();
                        price = firstSingleItemInCombined.getPrice().doubleValue();
                        itemName = firstSingleItemInCombined.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                        if (allTopCatList == null)
                            allTopCatList = new ArrayList<ToppingCategory>();
                        break;
                    }
                    case SINGLEMENUITEM: {
                        MenuSingleItem menuSingleItem = (MenuSingleItem) session.getAttribute(Constant.LAST_SINGLE_ITEM);
                        imageAddress = menuSingleItem.getImageURL();
                        allTopCatList = menuSingleItem.getToppingCategoryList();
                        price = menuSingleItem.getPrice().doubleValue();
                        itemName = menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                        if (allTopCatList == null)
                            allTopCatList = new ArrayList<ToppingCategory>();
                        break;
                    }
                }

                for (ToppingCategory currToCat : allTopCatList) {
                    if (currToCat.getExclusive())
                        exclusiveTopCatList.add(currToCat);
                    else noneExclusiveTopCatList.add(currToCat);

                }

                request.setAttribute("defaultItemToppingCategory", noneExclusiveTopCatList);
                request.setAttribute("defaultExlusiveToppingCategory", exclusiveTopCatList);

            }

            //user wants to review the toppings of a item in basket(review the previously selected toppings of a item in basket)
            //selected basket (singleBasket)
            else {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

                BasketSingleItem basketSingleItem = null;
                for (BasketSingleItem currentBssketSIngleItem : basketCombinedItem.getBasketSingleItemList()) {
                    if (currentBssketSIngleItem.getIdentifier().toString().equals(basketSingleItemIdentifier)) {
                        basketSingleItem = currentBssketSIngleItem;
                        break;
                    }
                }

                if (basketSingleItem == null)
                    basketSingleItem = basketCombinedItem.getBasketSingleItemList().get(0);

                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(), basketSingleItem.getGroupId());

                List<ToppingCategoryAlt> allTopCatAltList;
                List<ToppingCategoryAlt> exclusiveTopCatList = new ArrayList<ToppingCategoryAlt>();
                List<ToppingCategoryAlt> noneExclusiveTopCatAltList = new ArrayList<ToppingCategoryAlt>();


                allTopCatAltList = copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), basketSingleItem.getSelectedToppingMap());

                for (ToppingCategoryAlt topCatAltItem : allTopCatAltList) {
                    if (topCatAltItem.getExclusive())
                        exclusiveTopCatList.add(topCatAltItem);
                    else
                        noneExclusiveTopCatAltList.add(topCatAltItem);

                }


                imageAddress = menuSingleItem.getImageURL();
                price = basketSingleItem.getPrice().doubleValue();
                itemName = menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                request.setAttribute("selectedExlusiveToppingCategory", exclusiveTopCatList);
                request.setAttribute("selectedItemToppingCategory", noneExclusiveTopCatAltList);


            }
        } else {
            //user wants to review the defaults toppings of a item => loading default toppings of a clicked single
            //defaults of a selected item (singleItem)
            session.setAttribute(Constant.LAST_SINGLE_ITEM, menuSingle);
//            session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, FrontendAction.ClassTypeEnum.SINGLEMENUITEM);

//            BasketCombinedItem basketCombinedInSession = (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
//            BasketSingleItem basketSingleInCombined = null;
//            for (BasketSingleItem aBasketSingleItem : basketCombinedInSession.getBasketSingleItemList()){
//                if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleItemIdentifier))
//                    basketSingleInCombined = aBasketSingleItem;
//            }
//
//            if (basketSingleInCombined == null)
//                basketSingleInCombined = basketCombinedInSession.getBasketSingleItemList().get(0);
//
//            basketSingleInCombined.setId(singleAltId);
//            basketSingleInCombined.setGroupId(singleAltGroupId);


            List<ToppingCategory> allTopCatList;
            List<ToppingCategory> exclusiveTopCatList = new ArrayList<ToppingCategory>();
            List<ToppingCategory> noneExclusiveTopCatList = new ArrayList<ToppingCategory>();

            BasketCombinedItem basketInSesion = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
            if (basketInSesion != null) {
                List<BasketSingleItem> basketSingleList = basketInSesion.getBasketSingleItemList();
                for (BasketSingleItem aBasketSingleItem : basketSingleList) {
                    if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleItemIdentifier)) {
                        aBasketSingleItem.setId(menuSingle.getId());
                        aBasketSingleItem.setGroupId(menuSingle.getGroupId());
                    }
                }
            }


            price = menuSingle.getPrice().doubleValue();
            itemName = menuSingle.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
            imageAddress = menuSingle.getImageURL();

            allTopCatList = menuSingle.getToppingCategoryList();
            if (allTopCatList != null) {
                for (ToppingCategory topCatItem : allTopCatList) {
                    if (topCatItem.getExclusive())
                        exclusiveTopCatList.add(topCatItem);
                    else
                        noneExclusiveTopCatList.add(topCatItem);

                }

                request.setAttribute("defaultExlusiveToppingCategory", exclusiveTopCatList);
                request.setAttribute("defaultItemToppingCategory", noneExclusiveTopCatList);

            }
        }

        request.setAttribute("price", price);
        request.setAttribute("itemName", itemName);
        request.setAttribute("imageAddress", imageAddress);
        return mapping.findForward("gotoCustomizeIt");
    }


    public ActionForward applyTopings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) {

        HttpSession session = request.getSession();

        String toppings = request.getParameter("toppings");
        Map<Integer, String> selectedToppings = null;

        if (toppings.length() > 3) {
            selectedToppings = getSelectedToppingsMap(toppings);
        }

        String singleBasketItemSessionIdentifier = request.getParameter("basketSingleIdentifier");
        MenuSingleItem lastMenuSingleItem = (MenuSingleItem) session.getAttribute(Constant.LAST_SINGLE_ITEM);

        if (singleBasketItemSessionIdentifier == null || singleBasketItemSessionIdentifier.equals("") ||
                singleBasketItemSessionIdentifier.equals("null")) {

            Basket basket = (Basket) session.getAttribute(Constant.BASKET);

            String combinedItemProdNum = request.getParameter("combinedItemProdNum");
            String combinedItemGroupId = request.getParameter("combinedItemGroupId");

            String singleItemId = request.getParameter("singleItemId");
            String singleItemGroupId = request.getParameter("singleItemGroupId");

            CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(combinedItemProdNum, combinedItemGroupId);
            MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(singleItemId, singleItemGroupId);
            if (combinedMenuItem == null) {
                Category category = (Category) session.getAttribute(Constant.LAST_CATEGORY);
                List<SubCategory> subCategoryList = category.getSubCategoryList();
                if (subCategoryList.get(0).getType().equals(CombinedMenuItem.class)) {
                    CombinedMenuItem currCombinedMenuItem = (CombinedMenuItem) subCategoryList.get(0).getObject();

                    if (menuSingleItem == null)
                        menuSingleItem = currCombinedMenuItem.getMenuSingleItemList().get(0);

                    BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
                    basketCombinedItem.setProductNoRef(currCombinedMenuItem.getProductNo());
                    basketCombinedItem.setGroupIdRef(currCombinedMenuItem.getGroupId());

                    List<MenuSingleItem> menuSingleItemList = currCombinedMenuItem.getMenuSingleItemList();

                    boolean isToppingsSet = false;
                    for (MenuSingleItem currSingleItem : menuSingleItemList) {
                        BasketSingleItem basketSingleItem = new BasketSingleItem();
                        basketSingleItem.setId(currSingleItem.getId());
                        basketSingleItem.setGroupId(currSingleItem.getGroupId());


                        if ((currSingleItem.getId().equals(menuSingleItem.getId())) &&
                                (currSingleItem.getGroupId().equals(menuSingleItem.getGroupId())) && !isToppingsSet) {
                            basketSingleItem.setSelectedToppingMap(selectedToppings);
                            isToppingsSet = true;
                        } else
                            basketSingleItem.setSelectedToppingMap(new HashMap<Integer, String>());

                        basketCombinedItem.getBasketSingleItemList().add(basketSingleItem);
                    }
                    FrontendAction.putToBasket(basket, BasketCombinedItem.class, basketCombinedItem);
                }
                else {
                    if (menuSingleItem == null)
                        menuSingleItem = (MenuSingleItem) subCategoryList.get(0).getObject();

                    BasketSingleItem basketSingleItem = new BasketSingleItem();
                    basketSingleItem.setGroupId(menuSingleItem.getGroupId());
                    basketSingleItem.setId(menuSingleItem.getId());
                    basketSingleItem.setSelectedToppingMap(selectedToppings);

                    FrontendAction.putToBasket(basket, BasketSingleItem.class, basketSingleItem);
                }
            }
            else {

                BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
                basketCombinedItem.setProductNoRef(combinedMenuItem.getProductNo());
                basketCombinedItem.setGroupIdRef(combinedMenuItem.getGroupId());

                List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();

                if (menuSingleItem == null)
                    menuSingleItemList.get(0);

                for (MenuSingleItem currSingleItem : menuSingleItemList) {
                    BasketSingleItem basketSingleItem = new BasketSingleItem();
                    basketSingleItem.setId(currSingleItem.getId());
                    basketSingleItem.setGroupId(currSingleItem.getGroupId());

                    if ((currSingleItem.getId().equals(menuSingleItem.getId())) && (currSingleItem.getGroupId().equals(menuSingleItem.getGroupId())))
                        basketSingleItem.setSelectedToppingMap(selectedToppings);
                    else
                        basketSingleItem.setSelectedToppingMap(new HashMap<Integer, String>());
                    basketCombinedItem.getBasketSingleItemList().add(basketSingleItem);
                }

                FrontendAction.putToBasket(basket, BasketCombinedItem.class, basketCombinedItem);
            }
        }
        else {
            String singlebasketInSesionId = request.getParameter("singlebasketInSesionId");
            String singleItemId = request.getParameter("singleItemId");
            String singleItemGroupId = request.getParameter("singleItemGroupId");

            BasketItem basketItem = new BasketItem();
            Basket basket = (Basket) session.getAttribute(Constant.BASKET);
            for (BasketItem aBasketItem : basket.getBasketItemList()) {
                if (aBasketItem.getIdentifier().toString().equals(singlebasketInSesionId))
                    basketItem = aBasketItem;
            }

            if (basketItem.getClassType().equals(BasketSingleItem.class)) {

                BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();


                basketSingleItem.setId(singleItemId);
                basketSingleItem.setGroupId(singleItemGroupId);
                basketSingleItem.setSelectedToppingMap(selectedToppings);
            }
        }


        //some testing code
        try {
            PrintWriter out = response.getWriter();

            ResourceBundle currentBundle = ResourceBundle.getBundle("MessageResources", (Locale)session.getAttribute(Globals.LOCALE_KEY));
            out.write(currentBundle.getString("message.item.had.been.added.successfully"));
            out.flush();

        } catch (IOException e) {
        }
        //end of testing code

        return null;
    }


    public ActionForward addSingleAndApplyToppings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) {
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);

        String singleItemId = request.getParameter("singleItemId");
        String singleItemGroupId = request.getParameter("singleItemGroupId");
        String toppings = request.getParameter("toppings");

        Map<Integer, String> selectedToppings = getSelectedToppingsMap(toppings);

        MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(singleItemId, singleItemGroupId);

        BasketSingleItem basketSingleItem = new BasketSingleItem();
        basketSingleItem.setId(menuSingleItem.getId());
        basketSingleItem.setGroupId(basketSingleItem.getGroupId());
        basketSingleItem.setSelectedToppingMap(selectedToppings);

        FrontendAction.putToBasket(basket, BasketSingleItem.class, basketSingleItem);

        //these are some testing code (should be removed after implementation)
        StringBuffer stringBuffer = new StringBuffer();
        for (BasketItem basketItem : basket.getBasketItemList()) {
            if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                BasketSingleItem curBasketSingleItem = (BasketSingleItem) basketItem.getObject();
                MenuSingleItem singleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(curBasketSingleItem.getId(), curBasketSingleItem.getGroupId());
                stringBuffer.append(singleItem.getDescription(Locale.ENGLISH)).append("#");
            } else {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(), basketCombinedItem.getGroupIdRef());
                stringBuffer.append(combinedMenuItem.getDescription(Locale.ENGLISH)).append("#");

            }
        }

        try {
            PrintWriter out = response.getWriter();
            out.write(stringBuffer.toString());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //end of testing code

        return null;
    }

    public ActionForward getCombinedOrCatPic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) {

        HttpSession session = request.getSession();
        String itemName = "";

        FrontendAction.ClassTypeEnum itemType = (FrontendAction.ClassTypeEnum) session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);


        String imageAddress = "";


        switch (itemType) {
            case CATEGORY: {
                Category category = (Category) session.getAttribute(Constant.LAST_SUB_CATEGORY);
                imageAddress = category.getImageURL();
                itemName = category.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                break;
            }
            case COMBINED: {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
                CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(), basketCombinedItem.getGroupIdRef());
                imageAddress = combinedMenuItem.getImageURl();
                itemName = combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                break;
            }
        }

        request.setAttribute("imageAddress", imageAddress);
        request.setAttribute("itemName", itemName);
        return mapping.findForward("gotoCustomizeIt");
    }

    public ActionForward getCombinedPicBack(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) {
        HttpSession session = request.getSession();

        String imageAddress = "";
        String itemName = "";

        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);

        imageAddress = combinedMenuItem.getImageURl();
        itemName = combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

        request.setAttribute("imageAddress", imageAddress);
        request.setAttribute("itemName", itemName);
        return mapping.findForward("gotoCustomizeIt");
    }


    public ActionForward getPreSelectedToppings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) {
        String basketSingleIdentifier = request.getParameter("basketSingleIdentifier");
        Double price = null;
        String itemName = "";
        String imageAddress;


        HttpSession session = request.getSession();
        BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
        BasketSingleItem basketSingleItem = null;

        for (BasketSingleItem aBasketSingle : basketSingleItemList) {
            if (aBasketSingle.getIdentifier().toString().equals(basketSingleIdentifier))
                basketSingleItem = aBasketSingle;
        }

        List<ToppingCategoryAlt> allTopCatAltList;
        List<ToppingCategoryAlt> exclusiveTopCatList = new ArrayList<ToppingCategoryAlt>();
        List<ToppingCategoryAlt> noneExclusiveTopCatAltList = new ArrayList<ToppingCategoryAlt>();

        if (basketSingleItem != null) {
            MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(), basketSingleItem.getGroupId());

            allTopCatAltList = copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), basketSingleItem.getSelectedToppingMap());

            for (ToppingCategoryAlt topCatAltItem : allTopCatAltList) {
                if (topCatAltItem.getExclusive())
                    exclusiveTopCatList.add(topCatAltItem);
                else
                    noneExclusiveTopCatAltList.add(topCatAltItem);
            }

            imageAddress = menuSingleItem.getImageURL();

            itemName = menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

        } else {
            CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(), basketCombinedItem.getGroupIdRef());

            imageAddress = combinedMenuItem.getImageURl();
            price = null;
            itemName = combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
        }


        if ((basketSingleItem != null) && (basketSingleItem.getSelectedToppingMap().size() > 0)) {
            request.setAttribute("selectedExlusiveToppingCategory", exclusiveTopCatList);
            request.setAttribute("selectedItemToppingCategory", noneExclusiveTopCatAltList);
            price = basketSingleItem.getPrice().doubleValue();
        }

        request.setAttribute("imageAddress", imageAddress);
        request.setAttribute("price", price);
        request.setAttribute("itemName", itemName);

        return mapping.findForward("gotoCustomizeIt");
    }

    public ActionForward resetBasketItemToppings(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                 HttpServletResponse response) {
        HttpSession session = request.getSession();
        BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

        String basketSingleIdentifier = request.getParameter("basketSingleIdentifier");
        BasketSingleItem basketSingleItem = null;

        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
        for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
            if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleIdentifier)) {
                basketSingleItem = aBasketSingleItem;
                break;
            }
        }

        if (basketSingleItem == null) {
            basketSingleItem = basketCombinedItem.getBasketSingleItemList().get(0);
        }

        MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
        Map<Integer, String> selectedToppingMap = basketSingleItem.getSelectedToppingMap();
        if (selectedToppingMap != null) {
            Set<Integer> toppingsId = selectedToppingMap.keySet();

            Set<Integer> newToppingsID = new HashSet<Integer>();
            for (Integer toppingId : toppingsId) {
                newToppingsID.add(new Integer(toppingId));
            }

            for (Integer toppingId : newToppingsID) {
                if (!InMemoryData.isDefaultTopping(menuSingleItem, toppingId)) {
                    Integer newToppingId = findToppingById(toppingsId, toppingId);
                    toppingsId.remove(newToppingId);
                }

            }

        }


//        basketSingleItem.setSelectedToppingMap(new HashMap<Integer, String>());

        return null;
    }

    private Integer findToppingById(Set<Integer> toppingsId, Integer toppingId) {
        for (Integer topping : toppingsId) {
            if (topping.equals(toppingId))
                return topping;
        }
        return null;
    }


    public ActionForward getItemToppingsView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) {
        HttpSession session = request.getSession();
        String itemTypeStr = request.getParameter("itemType");

        FrontendAction.ClassTypeEnum itemType;

        if (itemTypeStr != null && !itemTypeStr.trim().equals("") && !itemTypeStr.trim().equals("null")) {
            itemType = FrontendAction.ClassTypeEnum.valueOf(itemTypeStr);
        } else {
            itemType = (FrontendAction.ClassTypeEnum) session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);
        }


        String imageAddress = "";
        Double price = null;
        String itemName = "";


        switch (itemType) {
            case SINGLEMENUITEM: {

                List<ToppingCategoryAlt> exclusiveTopCatList = new ArrayList<ToppingCategoryAlt>();
                List<ToppingCategoryAlt> noneExclusiveTopCatAltList = new ArrayList<ToppingCategoryAlt>();

                boolean isDeafultToppingsRequested = true;
                boolean isItemInSession = false;
                boolean isItemInBasket = false;

                String id = request.getParameter("prodNum_id");
                String groupId = request.getParameter("groupId");
//                String oldId = id;
//                String oldGroupId = groupId;
                String basketSingleIdentifier = request.getParameter("basketSingleIdentifier");
                String customizedBasketItemId = request.getParameter("customizedBasketItemId");



                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(id, groupId);

                if (menuSingleItem == null)
                    menuSingleItem = (MenuSingleItem) session.getAttribute(Constant.LAST_SINGLE_ITEM);


                imageAddress = menuSingleItem.getImageURL();
                itemName = menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                price = menuSingleItem.getPrice().doubleValue();
                Map<Integer, String> selectedTopCatMap = new HashMap<Integer, String>();
                List<ToppingCategoryAlt> allTopCatAltList = new ArrayList<ToppingCategoryAlt>();

                //we're in customizing mode, so we should search in the basket
                if (session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM) != Constant.VALUE_NOT_CUSTOMIZING_BASKET_ITEM_MODE) {
                    Basket basket = (Basket) session.getAttribute(Constant.BASKET);
                    List<BasketItem> basketItemList = basket.getBasketItemList();

                    for (BasketItem aBasketItem : basketItemList) {
                        if (aBasketItem.getIdentifier().toString().equals(customizedBasketItemId)) {
                            if (aBasketItem.getClassType().equals(BasketSingleItem.class)) {
                                BasketSingleItem basketSingle = (BasketSingleItem) aBasketItem.getObject();
                                selectedTopCatMap = basketSingle.getSelectedToppingMap();
                                isItemInBasket = true;
                                imageAddress = basketSingle.getSingle().getImageURL();
                                itemName = basketSingle.getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                                BasketSingleItemHelper basketSingleItemHelper = new BasketSingleItemHelper();
                                price = basketSingleItemHelper.getPrice(basketSingle).doubleValue();
                                allTopCatAltList = convertMapToToppingCategoryAltList(menuSingleItem, selectedTopCatMap);
                            }

                            else if (aBasketItem.getClassType().equals(BasketCombinedItem.class)) {
                                BasketCombinedItem basketCombinedItem = (BasketCombinedItem)aBasketItem.getObject();
                                List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

                                for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
                                    if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleIdentifier)) {
                                        selectedTopCatMap = aBasketSingleItem.getSelectedToppingMap();
                                        isItemInBasket = true;
                                        imageAddress = aBasketSingleItem.getSingle().getImageURL();
                                        itemName = aBasketSingleItem.getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                                        BasketSingleItemHelper basketSingleItemHelper = new BasketSingleItemHelper();
                                        price = basketSingleItemHelper.getPrice(aBasketSingleItem).doubleValue();
                                        allTopCatAltList = convertMapToToppingCategoryAltList(menuSingleItem, selectedTopCatMap);
                                    }
                                }
                            }
                        }
                    }

                } else {
                    /**
                     * see if the selected single is in the last seleced combined in session or not
                     */
                    BasketSingleItem basketSingleItem = null;
                    if (basketSingleIdentifier != null && !basketSingleIdentifier.trim().equals("") && !basketSingleIdentifier.trim().equals("null")) {
                        BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
                        for (BasketSingleItem aBasketSingleItem : basketCombinedItem.getBasketSingleItemList()) {
                            if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleIdentifier)) {
                                basketSingleItem = aBasketSingleItem;
                                selectedTopCatMap = aBasketSingleItem.getSelectedToppingMap();
                                isDeafultToppingsRequested = false;
                                isItemInSession = true;
                                imageAddress = aBasketSingleItem.getSingle().getImageURL();
                                itemName = aBasketSingleItem.getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY));
                                BasketSingleItemHelper basketSingleItemHelper = new BasketSingleItemHelper();
                                price = basketSingleItemHelper.getPrice(aBasketSingleItem).doubleValue();
                                allTopCatAltList = convertMapToToppingCategoryAltList(menuSingleItem, selectedTopCatMap);

                                // image and item name is same as the original menuSingleItem
                            }
                        }
                    }

                    if (basketSingleItem != null && basketSingleItem.getId().equals(id) && basketSingleItem.getGroupId().equals(groupId)) {

                        if (!id.equals(basketSingleItem.getId()))
                            System.out.println("conflict in id");
                        id = basketSingleItem.getId();

                        if (!groupId.equals(basketSingleItem.getGroupId()))
                            System.out.println("conflict in group id");
                        groupId = basketSingleItem.getGroupId();

                    } else {
                        allTopCatAltList = copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null);

                    }
                }


                for (ToppingCategoryAlt topCatAltItem : allTopCatAltList) {
                    if (topCatAltItem.getExclusive())
                        exclusiveTopCatList.add(topCatAltItem);
                    else
                        noneExclusiveTopCatAltList.add(topCatAltItem);
                }

                request.setAttribute("isPortion", menuSingleItem.isPortion());
                request.setAttribute("selectedExlusiveToppingCategory", exclusiveTopCatList);
                request.setAttribute("selectedItemToppingCategory", noneExclusiveTopCatAltList);

                break;
            }
            case COMBINED: {
                String prodNum = request.getParameter("prodNum_id");
                String groupId = request.getParameter("groupId");

                CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(prodNum, groupId);

//                BasketCombinedItem basketCombinedItem;


                if (combinedMenuItem == null)
                    combinedMenuItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);

//                basketCombinedItem = convertCombinedMenuToBasketCombined(combinedMenuItem);


//                basketCombinedItem.setGroupIdRef(combinedMenuItem.getGroupId());
//                basketCombinedItem.setProductNoRef(combinedMenuItem.getProductNo());


                imageAddress = combinedMenuItem.getImageURl();
                itemName = combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                break;
            }
            case CATEGORY: {
                Category category = (Category) session.getAttribute(Constant.LAST_CATEGORY);


                imageAddress = category.getImageURL();
                itemName = category.getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

                break;
            }
        }

        request.setAttribute("imageAddress", imageAddress);
        request.setAttribute("itemName", itemName);
        request.setAttribute("price", price);

        return mapping.findForward("gotoCustomizeIt");
    }


    public ActionForward setThisSingleInSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) {

        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        String groupId = request.getParameter("groupId");
        String basketSingleIdentifier = request.getParameter("identifier");

        if ((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE)).equals(FrontendAction.ClassTypeEnum.COMBINED)) {
            BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
            BasketSingleItem basketSingleItem = null;

            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
            for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
                if (aBasketSingleItem.getIdentifier().toString().equals(basketSingleIdentifier)) {
                    basketSingleItem = aBasketSingleItem;
                    break;
                }
            }

            if (basketSingleItem == null) {
                basketSingleItem = basketCombinedItem.getBasketSingleItemList().get(0);
            }

            basketSingleItem.setId(id);
            basketSingleItem.setGroupId(groupId);

            MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(), basketSingleItem.getGroupId());

            List<ToppingCategoryAlt> toppingCategoryAltList = copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null);

            Map<Integer, String> defaultTopsAsMap = new HashMap<Integer, String>();

            for (ToppingCategoryAlt aToppingCategoryAlt : toppingCategoryAltList) {
                defaultTopsAsMap.putAll(aToppingCategoryAlt.getSelectedToppingMap());
            }

            basketSingleItem.setSelectedToppingMap(defaultTopsAsMap);
        }

        try {
            PrintWriter out = response.getWriter();
            out.print("BasketSingleItem in session sucessfully changed");
            out.flush();
        } catch (IOException e) {
        }
        return null;
    }


    public ActionForward isSessionTheSame(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) {
        HttpSession session = request.getSession();
        Locale curLocale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MenuSingleItem firstSingleInCombined = null;

        CombinedMenuItem defaultCombinedMenuItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
        List<MenuSingleItem> menuSingleItemList = defaultCombinedMenuItem.getMenuSingleItemList();
        firstSingleInCombined = menuSingleItemList.get(0);
        StringBuffer serverSingleNameSpace = new StringBuffer();
        for (MenuSingleItem single : menuSingleItemList) {
            serverSingleNameSpace.append(single.getName(curLocale)).append("#");
        }
        String serverSingleName =  serverSingleNameSpace.toString().replaceAll("\\s+", "");

        StringBuffer clientSingleName;
        String  clientSingleNameSpace = ((String)request.getParameter("uniqueStr")).trim();
        clientSingleName = new StringBuffer(clientSingleNameSpace.replaceAll("\\s+", ""));


        String singleName = menuSingleItemList.get(0).getName(curLocale);
        String serverUniqueStr1 = singleName + "0/31/clear#0/32/clear#0/41/clear#0/44/clear#0/216/clear#0/22/clear#5/20/clear#5/21/clear#5/23/clear#5/15/clear#5/16/clear#5/17/clear#1/24/full#1/25/clear#1/26/clear#1/27/clear#1/28/clear#1/29/clear#1/30/clear#1/42/clear#1/43/clear#2/45/clear#2/33/full#2/34/full#2/35/clear#2/36/clear#2/37/clear#2/38/clear#2/39/clear#2/40/clear#*3/213#4/336#";
        String serverUniqueStr2 = singleName + "0/31/clear#0/32/clear#0/41/clear#0/44/clear#0/216/clear#0/22/clear#5/20/clear#5/21/clear#5/23/clear#5/15/clear#5/16/clear#5/17/clear#1/24/full#1/25/clear#1/26/clear#1/27/clear#1/28/clear#1/29/clear#1/30/clear#1/42/clear#1/43/clear#2/45/clear#2/33/full#2/34/clear#2/35/clear#2/36/clear#2/37/clear#2/38/full#2/39/clear#2/40/clear#*3/213#4/336#";

        String name = request.getParameter("singleName").trim();
        String toppingStr = request.getParameter("toppings");
        String clientUniqueStr = name + toppingStr;

        boolean justFirstCondition = true;
        if (name.equals(singleName)) {
            justFirstCondition = false;
        }


        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        if (justFirstCondition) {
            if (clientSingleName.toString().equals(serverSingleName))
                out.write("1");
            else
                out.write("0");
        }else {
            if ( (clientSingleName.toString().equals(serverSingleName)) && (clientUniqueStr.equals(serverUniqueStr1) || clientUniqueStr.equals(serverUniqueStr2) ))
                out.write("1");
            else
                out.write("0");
        }

        return null;
    }


    public ActionForward resetBasket(ActionMapping maping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) {

        HttpSession session = request.getSession();
        String combinedGroupIdStr = request.getParameter("combinedGroupId");
        String combinedProdNumStr = request.getParameter("combinedProdNum");

        Basket basket = (Basket )session.getAttribute(Constant.BASKET);
        for (BasketItem basketItem : basket.getBasketItemList()) {
            if (basketItem.getClassType().equals(BasketCombinedItem.class)) {

                BasketCombinedItem basketCombinedMenuItem = (BasketCombinedItem)basketItem.getObject();
                if (basketCombinedMenuItem.getGroupIdRef().equals(combinedGroupIdStr)  && basketCombinedMenuItem.getProductNoRef().equals(combinedProdNumStr
                )) {
                    CombinedMenuItem defaultCombinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedMenuItem.getProductNoRef(), basketCombinedMenuItem.getGroupIdRef());

                    List<BasketSingleItem> basketSingleItemList = basketCombinedMenuItem.getBasketSingleItemList();
                    List<MenuSingleItem> defaultMenuSingleItemList = defaultCombinedMenuItem.getMenuSingleItemList();


                    int index = 0;
                    for (BasketSingleItem basketSingleItem : basketSingleItemList) {

                        basketSingleItem.setId(defaultMenuSingleItemList.get(index).getId());
                        basketSingleItem.setGroupId(defaultMenuSingleItemList.get(index).getGroupId());


                        Map<Integer, String> defaultTops = new HashMap<Integer, String>();
                        for (ToppingCategory topCat : defaultMenuSingleItemList.get(index).getToppingCategoryList()) {
                            for (Integer topId : topCat.getDefaultToppingList())
                                defaultTops.put(topId, "full");
                        }
                        basketSingleItem.setSelectedToppingMap(defaultTops);
                        index++;
                    }                   
                }
            }
        }


        try {
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.write("OK");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }




    //util methods


    public static BasketCombinedItem convertCombinedMenuToBasketCombined(CombinedMenuItem combinedMenuItem) {
        BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
        basketCombinedItem.setGroupIdRef(combinedMenuItem.getGroupId());
        basketCombinedItem.setProductNoRef(combinedMenuItem.getProductNo());


        List<BasketSingleItem> basketSingleItemList = new ArrayList<BasketSingleItem>();

        for (MenuSingleItem currSingleMenuItem : combinedMenuItem.getMenuSingleItemList()) {
            BasketSingleItem basketSingleItem = convertSingleToBasketSingle(currSingleMenuItem);
            basketSingleItemList.add(basketSingleItem);
        }
        basketCombinedItem.setBasketSingleItemList(basketSingleItemList);
        return basketCombinedItem;
    }

    public static BasketSingleItem convertSingleToBasketSingle(MenuSingleItem menuSingleItem) {
        BasketSingleItem basketSingleItem = new BasketSingleItem();
        basketSingleItem.setGroupId(menuSingleItem.getGroupId());
        basketSingleItem.setId(menuSingleItem.getId());
        return basketSingleItem;
    }


    public static BasketSingleItem findBasketSingleItemInBasketByIdentifier(Integer identifier, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        List<BasketItem> basketItemList = basket.getBasketItemList();
        for (BasketItem basketItem : basketItemList) {
            if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();
                if (basketSingleItem.getIdentifier().equals(identifier))
                    return basketSingleItem;
            } else {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
                for (BasketSingleItem basketSingleItem : basketSingleItemList) {
                    if (basketSingleItem.getIdentifier().equals(identifier))
                        return basketSingleItem;
                }
            }
        }

        return null;
    }

    public static List<ToppingCategoryAlt> copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(List<ToppingCategory> defaultTopCatList,
                                                                                                      Map<Integer, String> selectedAndDefaultToppingsList) {
        if (selectedAndDefaultToppingsList == null)
            selectedAndDefaultToppingsList = new HashMap<Integer, String>();

        if (defaultTopCatList == null)
            defaultTopCatList = new ArrayList<ToppingCategory>();

        List<ToppingCategoryAlt> copyOfTopCatList = new ArrayList<ToppingCategoryAlt>();

        for (ToppingCategory topCatItem : defaultTopCatList) {
            ToppingCategoryAlt topCatAltList = new ToppingCategoryAlt(topCatItem.getId(), topCatItem.getNameKey(), topCatItem.getToppingSubCategoryList(), topCatItem.getExclusive());

            //putting selected and default toppings
            Map<Integer, String> selectedAndDefaultToppingCopy = new HashMap<Integer, String>();
            for (Map.Entry<Integer, String> currentToppingMapItem : selectedAndDefaultToppingsList.entrySet()) {
                if (doesToppingCategoryContainsThisTopping(topCatItem, currentToppingMapItem.getKey())) {
                    selectedAndDefaultToppingCopy.put(currentToppingMapItem.getKey(), currentToppingMapItem.getValue());
                }
            }

            for (Integer topItemInteger : topCatItem.getDefaultToppingList()) {
                selectedAndDefaultToppingCopy.put(topItemInteger, "full");
            }

//            topCatAltList.getSelectedToppingMap().putAll(defaultToppingCopy);
            topCatAltList.getSelectedToppingMap().putAll(selectedAndDefaultToppingCopy);

//            topCatAltList.setSelectedToppingMap(defaultToppingCopy);

            copyOfTopCatList.add(topCatAltList);
        }

        return copyOfTopCatList;
    }


    public static List<ToppingCategoryAlt> convertMapToToppingCategoryAltList(MenuSingleItem menuSingleItem, Map<Integer, String> selectedToppingsList) {
        List<ToppingCategory> toppingCategoryList = menuSingleItem.getToppingCategoryList();

        if (toppingCategoryList == null) {
            toppingCategoryList = new ArrayList<ToppingCategory>();
        }

        List<ToppingCategoryAlt> topCatAltList = new ArrayList<ToppingCategoryAlt>();

        for (ToppingCategory topCatItem : toppingCategoryList) {
            ToppingCategoryAlt topCatAltItem = new ToppingCategoryAlt(topCatItem.getId(), topCatItem.getNameKey(), topCatItem.getToppingSubCategoryList(), topCatItem.getExclusive());

            for (Integer selectedMapItemId : selectedToppingsList.keySet()) {
                if (doesToppingCategoryContainsThisTopping(topCatItem, selectedMapItemId)) {
                    topCatAltItem.getSelectedToppingMap().put(selectedMapItemId, selectedToppingsList.get(selectedMapItemId));
                }
            }

            topCatAltList.add(topCatAltItem);

        }
        return topCatAltList;
    }

    public static boolean doesToppingCategoryContainsThisTopping(ToppingCategory topCat, Integer topId) {
        List<ToppingSubCategory> topSubCatList = topCat.getToppingSubCategoryList();
        for (ToppingSubCategory subTopCat : topSubCatList) {
            if (subTopCat.getClassType().equals(Topping.class)) {
                Topping topping = (Topping) subTopCat.getObject();
                if (topping.getId().equals(topId))
                    return true;
            }
        }
        return false;
    }

    public static Map<Integer, String> getSelectedToppingsMap(String toppings) {
        Map<Integer, String> toppingIdStateMap = new HashMap<Integer, String>();
        if (toppings == null)
            return toppingIdStateMap;

        String[] toppingsArray = toppings.split("\\*");

        if (toppingsArray.length < 1)
            return toppingIdStateMap;

        String[] nXTops = toppingsArray[0].split("#");


        for (String toppingItem : nXTops) {
            String[] toppingsIdsStr = toppingItem.split("/");
            if (toppingsIdsStr.length >= 3)
                if (!toppingsIdsStr[2].equals("clear"))
                    toppingIdStateMap.put(Integer.valueOf(toppingsIdsStr[1]), toppingsIdsStr[2]);
        }

        if (toppingsArray.length > 1) {
            String[] xTops = toppingsArray[1].split("#");
            for (String toppingItem : xTops) {
                String[] toppingsIdsStr = toppingItem.split("/");
                toppingIdStateMap.put(Integer.valueOf(toppingsIdsStr[1]), null);
            }
        }

        return toppingIdStateMap;
    }


}
