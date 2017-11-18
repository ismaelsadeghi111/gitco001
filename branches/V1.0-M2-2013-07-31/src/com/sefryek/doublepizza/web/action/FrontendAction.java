package com.sefryek.doublepizza.web.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.Globals;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.core.*;
import com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.common.util.CollectionsUtils;

import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 23, 2011
 * Time: 1:47:46 PM
 */

public class FrontendAction extends DispatchAction {

    public enum ClassTypeEnum {
        CATEGORY, COMBINED, SINGLEMENUITEM
    }

    private Logger logger = Logger.getLogger(FrontendAction.class);

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        return goToMainPage(mapping, form, request, response);

    }

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
    }


    private ActionForward getCategoryItemsToForward(ActionForward forward, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryItemsToForward- > " + forward.toString());

        String catIdStr = request.getParameter("catId");
        String menuNameStr = request.getParameter("menuName");

        if ((catIdStr == null) || (catIdStr.equals(""))) {
            catIdStr = setDefaultCategoryId(menuNameStr);
            menuNameStr = request.getAttribute(Constant.MENU_NAME).toString();
        } else if ((catIdStr.equals("undefined"))) {
            catIdStr = setDefaultCategoryId(menuNameStr);

        }

        String menuName;
        if (menuNameStr.equals("Menu"))
            menuName = Constant.ORDER_MENU_NAME;
        else
            menuName = Constant.SPECIAL_MENU_NAME;

        Category category = InMemoryData.findCategoryById(menuName, Integer.valueOf(catIdStr));

        //extracting user selected category items(including innerCategory, combinedItems & SingleItems)
        List<Category> innerCategoryList = InMemoryData.getCategoryInnerCategoryList(category);
        List<MenuSingleItem> menuItemList = InMemoryData.getCategoryMenuSingleItemList(category);
        List<CombinedMenuItem> combinedItemList = InMemoryData.getCategoryCombinedMenuItemList(category);

        request.setAttribute("categoryName", category.getName((Locale)request.getSession().getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("innerCategoryList", innerCategoryList);
        request.setAttribute("menuSingleList", menuItemList);
        request.setAttribute("combinedItemList", combinedItemList);

        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        List<BasketItem> basketItemList = basket.getBasketItemList();

        List<BasketCombinedItem> basketCombinedItemList = new ArrayList<BasketCombinedItem>();
        List<BasketSingleItem> basketSingleItemList = new ArrayList<BasketSingleItem>();

        for (BasketItem basketItem : basketItemList) {
            if (basketItem.getClassType().equals(BasketCombinedItem.class))
                basketCombinedItemList.add((BasketCombinedItem) basketItem.getObject());

            if (basketItem.getClassType().equals(BasketSingleItem.class))
                basketSingleItemList.add((BasketSingleItem) basketItem.getObject());
        }

        List<MenuSingleItem> menuSingleItemList = new ArrayList<MenuSingleItem>();
        List<CombinedMenuItem> combinedMenuItemList = new ArrayList<CombinedMenuItem>();

        for (BasketCombinedItem basketCombinedItem : basketCombinedItemList) {
            combinedMenuItemList.add(InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem.getProductNoRef(),
                    basketCombinedItem.getGroupIdRef()));
        }

        for (BasketSingleItem basketSingleItem : basketSingleItemList) {
            menuSingleItemList.add(InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(),
                    basketSingleItem.getGroupId()));
        }

        request.setAttribute("combinedMenuItemList", combinedMenuItemList);
        request.setAttribute("menuSingleItemList", menuSingleItemList);

        return forward;
    }

    //forwarding user to the HomePage
    public ActionForward goToMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToMainPage - > forwardtoHomePage");

        List<Slider> sliderList = InMemoryData.getSliderList();
        request.setAttribute("sliderList", sliderList);

        HttpSession session = request.getSession();

        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);
        
        Object defualtLanguse = session.getAttribute("setdefaultlanguage");
        if (defualtLanguse == null) {
            session.setAttribute(Globals.LOCALE_KEY, new Locale ("fr", "FR"));
            session.setAttribute("setdefaultlanguage", "true");

        }

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Home);
        return mapping.findForward("forwardtoHomePage");
    }

    //redirect user to the registrationPage
    public ActionForward goToRegistrationPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToRegistrationPage - > forwardToRegistrationPage");

        return mapping.findForward("forwardToRegistrationPage");
    }

    //redirect user to the storeLocatorePage
    public ActionForward goToStoreLocator(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        logger.info(LogMessages.START_OF_METHOD + "goToStoreLocator - > forwardToStoreLocatorPage");

        List<String> storeCityList = InMemoryData.getCitiesCoveredByDoublePizza((Locale)session.getAttribute(Globals.LOCALE_KEY));
//        List<String> storeCityList = InMemoryData.getCitiesCoveredByDoublePizza((Locale)request.getAttribute(Globals.LOCALE_KEY));
        List<Store> StoreList = InMemoryData.getStoresInThisCity(storeCityList.get(Constant.DEFAULT_SELECTED_CITY_INDEX));

        request.setAttribute(Constant.STORE_LIST, StoreList);
        request.setAttribute(Constant.STORE_CITY, storeCityList);
        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Store_Locator);
        request.setAttribute(Constant.Selected_City_Item, "ALL" + Constant.ALTERNATIVE_CITY_NAME);

        return mapping.findForward("forwardToStoreLocatorPage");
    }

    //redirect user to the feedBackPage
    public ActionForward goToFeedBackPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToFeedBackPage - > forwardToFeedBackPage");

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_ContactUs);
        return mapping.findForward("forwardToFeedBackPage");
    }


    public ActionForward getStoreListOfSelectedCity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        String cityName = request.getParameter(Constant.CITY_ID_PARAM);
        List<Store> StoreList = InMemoryData.getStoresInThisCity(cityName);

        request.setAttribute(Constant.STORE_LIST, StoreList);

        return mapping.findForward("forwardToStoreContainerPage");
    }

    public ActionForward getStoreListOfSelectedCityOrderByStoreDistance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        String cityName = request.getParameter(Constant.CITY_ID_PARAM);
        String postalCode1 = request.getParameter("postalCode1");
        String postalCode2 = request.getParameter("postalCode2");
        String userPostalcode = postalCode1.toUpperCase() + " " + postalCode2.toUpperCase();
        List<Store> StoreList = InMemoryData.getStoresInThisCityOrderByStoreDistance(cityName, userPostalcode);
        request.setAttribute("postalCodeNotFound", StoreList == null);

        if (StoreList == null)
            StoreList = InMemoryData.getStoresInThisCity(cityName);
        request.setAttribute(Constant.STORE_LIST, StoreList);
        return mapping.findForward("forwardToStoreContainerPage");
    }

    public ActionForward getStoreListTopForPickup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        List<Store> StoreList1 = null;
        List<Store> StoreList2 = null;
        String cityName = Constant.ALTERNATIVE_ALL_CITIES;


        String postalCode1 = request.getParameter("postalCode1");
        String postalCode2 = request.getParameter("postalCode2");
        if (postalCode1 != null && postalCode2 != null){
            String userPostalcode = postalCode1.toUpperCase() + " " + postalCode2.toUpperCase();
            StoreList1 = InMemoryData.getStoresInThisCityOrderByStoreDistance(cityName, userPostalcode);
            if (StoreList1 != null)
                StoreList1 = CollectionsUtils.top(StoreList1, 3);
        }

        String city = request.getParameter("city");
        if (city != null && !city.equals("0")){
            Double latitude = Double.valueOf(request.getParameter("latitude"));
            Double longitude = Double.valueOf(request.getParameter("longitude"));
            StoreList2 = InMemoryData.getStoresInThisCityOrderByStoreDistance(cityName, latitude, longitude);
            if (StoreList2 != null)
                StoreList2 = CollectionsUtils.top(StoreList2, 3);                
        }

        List StoreList = null;

        if (StoreList1 != null & StoreList2 == null)
            StoreList = StoreList1;
        else if (StoreList1 == null & StoreList2 != null)
            StoreList = StoreList2;
        else if (StoreList1 != null & StoreList2 != null)
            StoreList = CollectionsUtils.union(StoreList1, StoreList2);

        request.setAttribute("postalCodeNotFound", StoreList == null);

        if (StoreList == null)
            StoreList = new ArrayList<Store>();
        else
            StoreList = CollectionsUtils.top(StoreList, 3);

        request.setAttribute(Constant.STORE_LIST_PICKUP, StoreList);
        return mapping.findForward("forwardToStoreListForPickup");
    }


    //redirect user to the aboutPage
    public ActionForward goToAboutPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToAboutPage - > forwardtoAboutPage");

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_AboutUs);
        return mapping.findForward("forwardtoAboutPage");
    }

    //redirect user to the aboutPage
    public ActionForward goToFunZonePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToFunZonePage - > forwardtoAboutPage");

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_AboutUs);
        return mapping.findForward("forwardtoFunZonePage");
    }

    //redirect  user to the FranchisingPage
    public ActionForward goToFranchisingPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToFranchisingPage - > forwardtoFranchisingPage");

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Franchising);
        return mapping.findForward("forwardtoFranchisingPage");
    }

    public ActionForward goToCateringPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToFranchisingPage - > forwardtoFranchisingPage");

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Franchising);
        return mapping.findForward("forwardtoCateringPage");
    }

    //redirect  user to the TermConditionsPage
    public ActionForward gotoTermsConditionsPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "gotoTermsConditionsPage - > forwardtoTermsConditionsPage");

        return mapping.findForward("forwardtoTermsConditionsPage");
    }

    //redirect user to the loginPage
    public ActionForward goToLoginPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToLoginPage - > forwardtoLoginPage");
        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Home);
        InMemoryData.setLatestUserUrl(request.getParameter(Constant.LATEST_USER_URL));
        if (request.getParameter("dontShowSuggestionsAgin") != null)
                request.getSession().setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, true);
        return mapping.findForward("forwardToLoginPage");
    }

    //forwarding user to the CustomizePage(Putting items to the session and Calculating the price of basket)
    public ActionForward goToCustomizePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToCustomizePage - > forwardToCustomizePage");

        String itemId;
        String groupId;

        HttpSession session = request.getSession();

        session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, null);

        MenuSingleItem menuSingleItem;
        CombinedMenuItem combinedMenuItem;
        Category innerCategory;

        String itemTypeStr = request.getParameter("type");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, itemType);
        switch (itemType) {
            case CATEGORY: {
                itemId = request.getParameter("categoryId");
                innerCategory = InMemoryData.getInnerCategoryById(itemId);

                session.setAttribute(Constant.LAST_SUB_CATEGORY, innerCategory);
                break;
            }
            case COMBINED: {
                itemId = request.getParameter("combinedId");
                groupId = request.getParameter("groupId");
//                combinedMenuItem = InMemoryData.findCombinedInnerCategory(itemId, groupId, category);
                combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(itemId, groupId);

                session.setAttribute(Constant.LAST_COMBINED_ITEM, combinedMenuItem);
                break;

            }
            case SINGLEMENUITEM: {
                itemId = request.getParameter("singleId");
                groupId = request.getParameter("groupId");
                menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(itemId, groupId);

                session.setAttribute(Constant.LAST_SINGLE_ITEM, menuSingleItem);

                BasketSingleItem basketSingleItem = new BasketSingleItem();
                basketSingleItem.setGroupId(menuSingleItem.getGroupId());
                basketSingleItem.setId(menuSingleItem.getId());

                List<ToppingCategory> allToppingCategoryList = menuSingleItem.getToppingCategoryList();
                if (allToppingCategoryList == null)
                    allToppingCategoryList = new ArrayList<ToppingCategory>();
                List<ToppingCategory> exclusiveToppingCategoryList = new ArrayList<ToppingCategory>();
                List<ToppingCategory> noneExlusiveToppingCategoryList = new ArrayList<ToppingCategory>();

                for (ToppingCategory topCatItem : allToppingCategoryList) {
                    if (topCatItem.getExclusive())
                        exclusiveToppingCategoryList.add(topCatItem);
                    else
                        noneExlusiveToppingCategoryList.add(topCatItem);
                }

                if (exclusiveToppingCategoryList.size() != 0)
                    request.setAttribute("exlusiveToppingCategory", exclusiveToppingCategoryList);

                request.setAttribute("itemToppingCategory", noneExlusiveToppingCategoryList);
                break;
            }
        }

        setSelectedItemToRequest(request);
        return getCategoryItemsToForward(mapping.findForward("forwardToCustomizePage"), form, request, response);
    }

    public ActionForward resetMenuObjectsOnSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Basket basket = createBasket(request);

        String itemId;
        String groupId;
        String catId = request.getParameter("catId");

        String menuName = request.getParameter("menuName");
        Integer identifier = Integer.valueOf(request.getParameter("identifier"));
        Category category = InMemoryData.findCategoryById(menuName, Integer.valueOf(catId));
        Integer customizeMode = Integer.valueOf(request.getParameter("customizeMode"));

        session.setAttribute(Constant.LAST_CATEGORY, category);
        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, customizeMode);

        MenuSingleItem menuSingleItem;
        CombinedMenuItem combinedMenuItem;
        Category innerCategory;

        String itemTypeStr = request.getParameter("type");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, itemType);
        switch (itemType) {
            case CATEGORY: {
                itemId = request.getParameter("subCatId");
                innerCategory = InMemoryData.getInnerCategoryById(itemId);
                session.setAttribute(Constant.LAST_SUB_CATEGORY, innerCategory);
                break;
            }
            case COMBINED: {
                itemId = request.getParameter("productNo");
                groupId = request.getParameter("groupId");
                combinedMenuItem = InMemoryData.findCombinedInnerCategory(itemId, groupId, category);
                BasketItem basketItem = basket.findBasketItemById(identifier, BasketCombinedItem.class);

                session.setAttribute(Constant.LAST_COMBINED_ITEM, combinedMenuItem);
                session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, basketItem.getObject());
                break;
            }
            case SINGLEMENUITEM: {
                itemId = request.getParameter("singleId");
                groupId = request.getParameter("groupId");
                menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(itemId, groupId);

                session.setAttribute(Constant.LAST_SINGLE_ITEM, menuSingleItem);
                break;
            }
        }
        setSelectedItemToRequest(request);
        return null;
    }


    //this method returns all subItems of a category(including innerCategories, CombinedMenuItems or SingleMenuItems)
    public ActionForward getCategoryAllItemList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryAllItemList- > getMenuAllItemList5");

        String catIdStr = request.getParameter("catId");
        String menuNameStr = request.getParameter("menuName");
        String menuName;
        if (menuNameStr.equals("Menu"))
            menuName = Constant.ORDER_MENU_NAME;
        else
            menuName = Constant.SPECIAL_MENU_NAME;
        Category category = InMemoryData.findCategoryById(menuName, Integer.valueOf(catIdStr));

        HttpSession session = request.getSession();

        //putting the user last_selected category to the session(if an attribute
        //with the name Constant.LAST_CATEGORY exist it's value will be replaced)
        session.setAttribute(Constant.LAST_CATEGORY, category);

        //creating new basket and putting it to user session
        createBasket(request);

        return getCategoryItemsToForward(mapping.findForward("getMenuAllItemList"), form, request, response);
    }

    //this method returns the menuList (first slide show on the HomePage)

    public ActionForward getMenuTypeItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSpecialList");
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);

        String menuType = request.getParameter("menuType");
        Menu menuObj = new Menu(null);
        if (menuType.equals("menu")) {
            menuObj = InMemoryData.getMenuByName(Constant.ORDER_MENU_NAME);
        } else if (menuType.equals("special")) {
            menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
        }

        String menuName = (String)session.getAttribute(Constant.MENU_NAME);
        if (menuName == null) {
            menuName = menuType;
            session.setAttribute(Constant.MENU_NAME, menuName);
        }


        List<Category> specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());

        request.setAttribute("menu_special", specialList);
        request.setAttribute("menuType", menuType);
        request.setAttribute("defaultSelectedCategoryId", getDefaultCategory(menuType).getId());
        request.setAttribute("defaultSelectedCategoryIndex", Constant.DEFAULT_SELECTED_CATEGORY_INDEX);

//
//        String menuName;
//        Category category;
//        if (menuType.equals("Menu")){
//            menuName = Constant.ORDER_MENU_NAME;
//            category = InMemoryData.findCategoryById(menuName, Integer.valueOf("11"));
//        }
//        else {
//            menuName = Constant.SPECIAL_MENU_NAME;
//            category = InMemoryData.findCategoryById(menuName, Integer.valueOf("2"));
//        }

//        HttpSession session = request.getSession();
//
//        //putting the user last_selected category to the session(if an attribute
//        //with the name Constant.LAST_CATEGORY exist it's value will be replaced)
//        session.setAttribute(Constant.LAST_CATEGORY, specialList.get(3));
//
//        //creating new basket and putting it to user session
//        createBasket(request);

        //   return getCategoryItemsToForward(mapping.findForward("getMenuAllItemList"), form, request, response);

//
//
//
//        resetToDefaultSelection(mapping,form,request,response);
//
        return mapping.findForward("getMenuList");
    }


    private Category getCategoryFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Category) session.getAttribute(Constant.LAST_CATEGORY);
    }

    private void setCategoryToSession(HttpServletRequest request, Category category) {
        HttpSession session = request.getSession();
        session.setAttribute(Constant.LAST_CATEGORY, category);
    }

    //this method returns all subItems of a category(including innerCategories or items)
    public ActionForward getCategoryItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryItems - > getCategoryItems");

//        Category category = getCategoryFromSession(request);
        Category category = InMemoryData.findCategoryById(Constant.MENU_NAME, Integer.valueOf(request.getParameter("catId")));

        if (category == null) {
            String catIdStr = request.getParameter("catId");
            category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));
            if (category != null)
                setCategoryToSession(request, category);
        }

        //extracting user selected category items(including innerCategory, combinedItems & SingleItems)
        List<Category> innerCategoryList = InMemoryData.getCategoryInnerCategoryList(category);
        List<MenuSingleItem> menuItemList = InMemoryData.getCategoryMenuSingleItemList(category);
        List<CombinedMenuItem> combinedItemList = InMemoryData.getCategoryCombinedMenuItemList(category);

        FrontendAction.ClassTypeEnum itemType = (FrontendAction.ClassTypeEnum) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);
        String selectedCategoryId = null;
        String selectedCombinedGroupId = null;
        String selectedCombinedProductNo = null;
        switch (itemType) {
            case CATEGORY: {
                selectedCategoryId = ((Category) request.getSession().getAttribute(Constant.LAST_SUB_CATEGORY)).getId();
                break;
            }
            case COMBINED: {
                selectedCombinedGroupId = ((CombinedMenuItem) request.getSession().getAttribute(Constant.LAST_COMBINED_ITEM)).getGroupId();
                selectedCombinedProductNo = ((CombinedMenuItem) request.getSession().getAttribute(Constant.LAST_COMBINED_ITEM)).getProductNo();
                break;
            }
        }

        request.setAttribute("selectedCategoryId", selectedCategoryId);
        request.setAttribute("selectedCombinedGroupId", selectedCombinedGroupId);
        request.setAttribute("selectedCombinedProductNo", selectedCombinedProductNo);

        request.setAttribute("categoryName", category.getName((Locale)request.getSession().getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("innerCategoryList", innerCategoryList);
        request.setAttribute("menuSingleList", menuItemList);
        request.setAttribute("combinedItemList", combinedItemList);
        setSelectedItemToRequest(request);

        return mapping.findForward("getCategoryItems");
    }


    //returns list of singleMenuItems for a subcategory item
    public ActionForward getSubCategoryItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSubCategoryItems - > getSubCategoryItems");
        HttpSession session = request.getSession();

        String catIdStr = request.getParameter("catId");
        Category category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));

        List<MenuSingleItem> menuItemList = InMemoryData.getCategoryMenuSingleItemList(category);
        request.setAttribute("menuSingleList", menuItemList);
        request.setAttribute("categoryName", category.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));

        request.getSession().setAttribute(Constant.LAST_SUB_CATEGORY, category);
        request.getSession().setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.CATEGORY);

        setSelectedItemToRequest(request);
        return mapping.findForward("getSubCategoryItems");
    }

    public ActionForward getSingleMenuItemsForType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSubCategoryItemsForType - > getSubCategoryItemsForType");
        HttpSession session = request.getSession();


        String catIdStr = request.getParameter("catId");
        Category category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));

        String productNoStr = request.getParameter("productNo");
        String groupIdStr = request.getParameter("groupId");
        int itemIndex = Integer.valueOf(request.getParameter("itemIndex"));

        CombinedMenuItem combined = InMemoryData.findCombinedInnerCategory(productNoStr,
                groupIdStr, category);

        List<MenuSingleItem> menuSingleList = new ArrayList<MenuSingleItem>();
        menuSingleList.add(combined.getMenuSingleItemList().get(itemIndex));

        List<MenuSingleItem> alternatives = combined.getAlternatives().get(itemIndex);
        for (MenuSingleItem item : alternatives) {
            menuSingleList.add(item);
        }

        request.setAttribute("menuSingleList", menuSingleList);
        request.setAttribute("categoryName", combined.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));
        if (menuSingleList.size() > 0) {
            request.setAttribute("selectedSingleItemForType", menuSingleList.get(0).getId());
            request.setAttribute("selectedSingleItemPrice", menuSingleList.get(0).getFormattedPrice());
        }

        return mapping.findForward("getSubCategoryItems");
    }


    //returns list of singleMenuItems for a combined item
    public ActionForward getCombinedItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCombinedItems - > getCombinedItems");
        HttpSession session = request.getSession();

        String catIdStr = request.getParameter("catId");

        Category category = InMemoryData.findCategoryById(null, Integer.valueOf(catIdStr));
        String productNoStr = request.getParameter("productNo");
        String groupIdStr = request.getParameter("groupId");

        CombinedMenuItem combined = InMemoryData.findCombinedInnerCategory(productNoStr, groupIdStr, category);

        if (combined != null) {
            session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.COMBINED);
            session.setAttribute(Constant.LAST_COMBINED_ITEM, combined);
        } else {
            combined = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
        }


        request.setAttribute("combinedTypesCaption", combined.getCaptions((Locale)session.getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("combinedProductNo", combined.getProductNo());
        request.setAttribute("combinedGroupId", combined.getGroupId());
        request.setAttribute("menuSingleItemList", combined.getMenuSingleItemList());

        return mapping.findForward("getCombinedItems");
    }

    public ActionForward setLastSelectedCombinedOnSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception {


        String catIdStr = request.getParameter("catId");

        HttpSession session = request.getSession();
        Category category = InMemoryData.findCategoryById(null, Integer.valueOf(catIdStr));

        String productNoStr = request.getParameter("productNo");
        String groupIdStr = request.getParameter("groupId");

        logger.info("setLastSelectedCombinedOnSession >> " + productNoStr + "\t" + groupIdStr);

        CombinedMenuItem combined = InMemoryData.findCombinedInnerCategory(productNoStr, groupIdStr, category);
        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.COMBINED);
        session.setAttribute(Constant.LAST_COMBINED_ITEM, combined);

        return null;
    }


    //returns list of singleMenuItems for a subcategory item
    public ActionForward getSingleMenuItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSingleMeniItems - > forwardtoTypeList");
        HttpSession session = request.getSession();

        String singleId = request.getParameter("singleMenuId");
        String singleMenuGroupId = request.getParameter("singleMenuGroupId");

        MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(singleId, singleMenuGroupId);
        session.setAttribute(Constant.LAST_SINGLE_ITEM, menuSingleItem);
        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.SINGLEMENUITEM);

        return null;
    }


    public ActionForward showSessionData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        String responseOutput;
        StringBuffer lastStr = new StringBuffer();
        StringBuffer cmbnAsRes = new StringBuffer();
        StringBuffer snglAsRes = new StringBuffer();
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        if (basket != null) {
            Category lastCat = (Category) session.getAttribute(Constant.LAST_CATEGORY);
            Category lastSubcat = (Category) session.getAttribute(Constant.LAST_SUB_CATEGORY);
            CombinedMenuItem lastCombined = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
            MenuSingleItem lastSingle = (MenuSingleItem) session.getAttribute(Constant.LAST_SINGLE_ITEM);
            if (lastCat != null)
                lastStr.append("lastCat: ").append(lastCat.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append(", ");
            if (lastSubcat != null)
                lastStr.append("lastSubcat: ").append(lastSubcat.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append(", ");
            if (lastCombined != null)
                lastStr.append("lastCombined: ").append(lastCombined.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append(", ");
            if (lastSingle != null)
                lastStr.append("lastSingle: ").append(lastSingle.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append(",");


            List<BasketItem> basketItemList = basket.getBasketItemList();
            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getClassType().equals(BasketCombinedItem.class)) {
                    BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                    CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(basketCombinedItem
                            .getProductNoRef(), basketCombinedItem.getGroupIdRef());
                    List<MenuSingleItem> singleItemList = combinedMenuItem.getMenuSingleItemList();
                    cmbnAsRes.append(combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append("#");
                    for (MenuSingleItem currSingle : singleItemList) {
                        cmbnAsRes.append(currSingle.getId()).append(",").append(currSingle.getGroupId()).append(": ")
                                .append(currSingle.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append(";\n");
                    }
                }
                if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                    BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();
                    MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(basketSingleItem.getId(), basketSingleItem.getGroupId());
                    snglAsRes.append(menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY))).append("#").append(menuSingleItem.getId()).append(",")
                            .append(menuSingleItem.getGroupId()).append(": ").append(menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));
                }
            }
            lastStr.append("\n/////////////////////////////////////\n");
            String str = "\n/////////////////////////////////////\n";
            responseOutput = lastStr + cmbnAsRes.toString() + str + snglAsRes.toString();
        } else {
            responseOutput = "Session is empty";
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write(responseOutput);
        out.flush();
        return null;
    }


    public ActionForward putCombinedToSessionAsBasketCombined(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                              HttpServletResponse response) {
//        setLastSelectedCombinedToBasketInSession(request);

        HttpSession session = request.getSession();

        String combinedprodNum = request.getParameter("combinedprodNum");
        String combinedGroupId = request.getParameter("combinedGroupId");
        String basketItemId = request.getParameter("basketItemId");
        BasketCombinedItem basketCombinedItem = null;
        if (!basketItemId.equals("null")){
            Basket basket = (Basket )session.getAttribute(Constant.BASKET);
            BasketItem basketItem = basket.findBasketItemById(Integer.valueOf(basketItemId));
            assert basketItem != null;
            basketCombinedItem = (BasketCombinedItem)basketItem.getObject();

            InMemoryData.basketCombinedItemRestoreToDefault(basketCombinedItem);

            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

            for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(aBasketSingleItem.getId(), aBasketSingleItem.getGroupId());

                Map<Integer, String> defaultTopsAsMap = new HashMap<Integer, String>();
                List<ToppingCategoryAlt> topCatAtlList = new ArrayList<ToppingCategoryAlt>();

                topCatAtlList.addAll(ToppingAction.copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null));
                for (ToppingCategoryAlt aToppingCategoryAlt : topCatAtlList){
                    defaultTopsAsMap.putAll(aToppingCategoryAlt.getSelectedToppingMap());
                }

                aBasketSingleItem.setSelectedToppingMap(defaultTopsAsMap);
            }
        }else{

        
            CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(combinedprodNum, combinedGroupId);

            if (combinedMenuItem == null) {
                combinedMenuItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
            }

            logger.info("putCombinedToSessionAsBasketCombined >> " + combinedMenuItem.getProductNo() + "\t"
                    + combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));

            basketCombinedItem = convertCombinedMenuToBasketCombined(combinedMenuItem);

            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

            for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(aBasketSingleItem.getId(), aBasketSingleItem.getGroupId());

                Map<Integer, String> defaultTopsAsMap = new HashMap<Integer, String>();
                List<ToppingCategoryAlt> topCatAtlList = new ArrayList<ToppingCategoryAlt>();

                topCatAtlList.addAll(ToppingAction.copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null));
                for (ToppingCategoryAlt aToppingCategoryAlt : topCatAtlList){
                    defaultTopsAsMap.putAll(aToppingCategoryAlt.getSelectedToppingMap());
                }

                aBasketSingleItem.setSelectedToppingMap(defaultTopsAsMap);
            }
        }
//        BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
        session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, basketCombinedItem);

        //some testing code
        try {
            PrintWriter out = response.getWriter();
            out.write("Your Changes have been applied successfully.\nYou can find them in your cart if you add the item to it.");
            out.flush();

        } catch (IOException e) {
        }
        //end of testing code

        return null;

    }

    private void setLastSelectedCombinedToBasketInSession(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String combinedprodNum = request.getParameter("combinedprodNum");
        String combinedGroupId = request.getParameter("combinedGroupId");

        CombinedMenuItem combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(combinedprodNum, combinedGroupId);

        if (combinedMenuItem == null) {
            combinedMenuItem = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
        }

        logger.info("putCombinedToSessionAsBasketCombined >> " + combinedMenuItem.getProductNo() + "\t"
                + combinedMenuItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));

        BasketCombinedItem basketCombinedItem = convertCombinedMenuToBasketCombined(combinedMenuItem);

        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

        for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
            MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(aBasketSingleItem.getId(), aBasketSingleItem.getGroupId());

            Map<Integer, String> defaultTopsAsMap = new HashMap<Integer, String>();
            List<ToppingCategoryAlt> topCatAtlList = new ArrayList<ToppingCategoryAlt>();

            topCatAtlList.addAll(ToppingAction.copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null));
            for (ToppingCategoryAlt aToppingCategoryAlt : topCatAtlList){
                defaultTopsAsMap.putAll(aToppingCategoryAlt.getSelectedToppingMap());
            }

            aBasketSingleItem.setSelectedToppingMap(defaultTopsAsMap);
        }

//        BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
        session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, basketCombinedItem);
    }


    public ActionForward putSingleItemToBasketCombinedInSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                                HttpServletResponse response) {
        HttpSession session = request.getSession();

        String singleId = request.getParameter("singleId");
        String singleGroupId = request.getParameter("singleGroupId");
        String toppings = request.getParameter("toppings");

        Map<Integer, String> selectedToppings = new HashMap<Integer, String>();
        if (toppings.length() > 3)
            selectedToppings = ToppingAction.getSelectedToppingsMap(toppings);

        BasketSingleItem basketSingleItem = new BasketSingleItem();
        basketSingleItem.setId(singleId);
        basketSingleItem.setGroupId(singleGroupId);
        basketSingleItem.setSelectedToppingMap(selectedToppings);

        BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
        basketCombinedItem.getBasketSingleItemList().add(basketSingleItem);
        return null;

    }


    public ActionForward applyToppingsOnBasketCombinedItemInSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                                    HttpServletResponse response) {
        HttpSession session = request.getSession();

        String toppings = request.getParameter("toppings");

        String singleBasketItemId = request.getParameter("basketSingleItemId");


        BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

        List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();

        BasketSingleItem selectedSingleBasketItem = null;

        for (BasketSingleItem aBasketSingleItem : basketSingleItemList) {
            if (aBasketSingleItem.getIdentifier().toString().equals(singleBasketItemId))
                selectedSingleBasketItem = aBasketSingleItem;
        }

        if (selectedSingleBasketItem == null)
            selectedSingleBasketItem = basketSingleItemList.get(0);


        Map<Integer, String> selectedToppings = ToppingAction.getSelectedToppingsMap(toppings);

        selectedSingleBasketItem.setSelectedToppingMap(selectedToppings);


        try {
            PrintWriter out = response.getWriter();
            out.write("Your Changes have been applied successfully.\nYou can find them in your cart if you add the item to it.");
            out.flush();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }


        return null;
    }


    public List<ToppingCategory> getDefaultToppings(ClassTypeEnum type, Object item) throws Exception {
        List<ToppingCategory> defaultToppingCategory = new ArrayList<ToppingCategory>();

        if (type.equals(ClassTypeEnum.SINGLEMENUITEM)) {
            List<ToppingCategory> allToppingCategory;
            MenuSingleItem menuSingleItem = (MenuSingleItem) item;
            allToppingCategory = menuSingleItem.getToppingCategoryList();


            for (ToppingCategory topCat : allToppingCategory) {
                List<Integer> defaultTopArray = topCat.getDefaultToppingList();
                ToppingCategory defaultTopCat = new ToppingCategory();
                defaultTopCat.setId(topCat.getId());
                defaultTopCat.setNameKey(topCat.getNameKey());

                List<ToppingSubCategory> toppingSubCatList = new ArrayList<ToppingSubCategory>();
                defaultTopCat.setToppingSubCategoryList(toppingSubCatList);

                for (Integer currId : defaultTopArray) {
                    Topping topping = InMemoryData.findToppingById(currId);
                    ToppingSubCategory subCategory = new ToppingSubCategory(topping.getClass(), topping);
                    toppingSubCatList.add(subCategory);
                }

                defaultToppingCategory.add(defaultTopCat);
            }
        } else if (type.equals(ClassTypeEnum.COMBINED)) {
            CombinedMenuItem combinedMenuItem = (CombinedMenuItem) item;
            List<MenuSingleItem> menuSingleItemList = combinedMenuItem.getMenuSingleItemList();
            MenuSingleItem menuSingleItem = menuSingleItemList.get(0);
            defaultToppingCategory = getDefaultToppings(FrontendAction.ClassTypeEnum.SINGLEMENUITEM, menuSingleItem);
        }


        return defaultToppingCategory;
    }


    private Basket createBasket(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        if (basket == null) {
            basket = new Basket();
            session.setAttribute(Constant.BASKET, basket);
        }
        return basket;
    }

    private void removeFromBasket(Basket basket, BasketItem basketItem) {
        List<BasketItem> basketItemList = basket.getBasketItemList();

        basketItemList.remove(basketItem);
    }


    public static void putToBasket(Basket basket, Class type, Object item) {
        List<BasketItem> basketItemList = basket.getBasketItemList();
        BasketItem basketItem = new BasketItem();

        if (type.equals(BasketSingleItem.class)) {
            BasketSingleItem basketSingleItem = (BasketSingleItem) item;

            basketItem.setClassType(BasketSingleItem.class);
            basketItem.setObject(basketSingleItem);

            basketItemList.add(basketItem);
        } else if (type.equals(BasketCombinedItem.class)) {
            BasketCombinedItem basketCombinedItem = (BasketCombinedItem) item;


            basketItem.setClassType(basketCombinedItem.getClass());
            basketItem.setObject(basketCombinedItem);

            basketItemList.add(basketItem);
        }
    }

    public ActionForward addItemToBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);

        String itemTypeStr = request.getParameter("basketItemType");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        switch (itemType) {
            case COMBINED: {
                String catId = request.getParameter("catId");
                Category category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catId));
                String productNo = request.getParameter("productNo");
                String groupId = request.getParameter("groupId");
                CombinedMenuItem combined = InMemoryData.findCombinedInnerCategory(productNo, groupId, category);
                BasketCombinedItem basketCombinedItem = convertCombinedMenuToBasketCombined(combined);

                putToBasket(basket, BasketCombinedItem.class, basketCombinedItem);
                break;
            }
            case SINGLEMENUITEM: {
                String menuSingleId = request.getParameter("menuSingleId");
                String groupId = request.getParameter("groupId");
                MenuSingleItem menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(menuSingleId, groupId);
                BasketSingleItem basketSingleItem = convertSingleToBasketSingle(menuSingleItem);
                putToBasket(basket, BasketSingleItem.class, basketSingleItem);
                break;
            }
        }

        return getBasketItems(mapping, form, request, response);

    }


    public ActionForward addCombinedItemFromSessionToBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);
        HttpSession session = request.getSession();
//        BasketCombinedItem selectedBasketCombinedItem =
//                (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
//        BasketCombinedItem newBasketCombinedItem = new BasketCombinedItem();
        BasketCombinedItem newBasketCombinedItem =
                (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);

//        newBasketCombinedItem.setBasketSingleItemList(selectedBasketCombinedItem.getBasketSingleItemList());
//        newBasketCombinedItem.setGroupIdRef(selectedBasketCombinedItem.getGroupIdRef());
//        newBasketCombinedItem.setProductNoRef(selectedBasketCombinedItem.getProductNoRef());
//        newBasketCombinedItem.setIdentifier(selectedBasketCombinedItem.getIdentifier());
//        newBasketCombinedItem.setQuantity(selectedBasketCombinedItem.getQuantity());


        putToBasket(basket, BasketCombinedItem.class, newBasketCombinedItem);

        setLastSelectedCombinedToBasketInSession(request);
//        session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, null);
//        List<BasketSingleItem> basketSingleItems =  newBasketCombinedItem.getBasketSingleItemList();
//        for(BasketSingleItem basketSingleItem : basketSingleItems){
//            basketSingleItem.setSelectedToppingMap(new HashMap<Integer, String>());
//        }
        return getBasketItems(mapping, form, request, response);

    }

    public ActionForward getBasketItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        Basket basket = createBasket(request);
        List<BasketItem> basketItemsList = basket.getBasketItemList();

        request.setAttribute("basketItemsList", basketItemsList);
        request.setAttribute("basketTotalPrice", basket.calculateTotalPrice());

        return mapping.findForward("getBasketItems");

    }

    public ActionForward addBasketCombinedItemToBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                       HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);
        BasketCombinedItem basketCombinedItem = new BasketCombinedItem();

        String productNo = request.getParameter("productNo");
        String groupId = request.getParameter("groupId");
        String menuSingleItems = request.getParameter("menuSingleItems");

        basketCombinedItem.setProductNoRef(productNo);
        basketCombinedItem.setGroupIdRef(groupId);

        String[] menuSingleItemsArr = menuSingleItems.split("#");
        for (String menuSingleItem : menuSingleItemsArr) {
            String[] menuSingleItemArr = menuSingleItem.split("-");
            MenuSingleItem menuSingleItemObj = InMemoryData.findMenuSingleItemByIdAndGroupId(menuSingleItemArr[0], menuSingleItemArr[1]);
            BasketSingleItem basketSingleItem = convertSingleToBasketSingle(menuSingleItemObj);
            List<BasketSingleItem> BasketSingleItemList = basketCombinedItem.getBasketSingleItemList();
            BasketSingleItemList.add(basketSingleItem);
        }

        putToBasket(basket, BasketCombinedItem.class, basketCombinedItem);
        return getBasketItems(mapping, form, request, response);
    }

    public ActionForward removeItemFromBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);

        Integer basketItemId = Integer.valueOf(request.getParameter("basketItemId"));
        String itemTypeStr = request.getParameter("basketItemType");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        Class type = null;
        switch (itemType) {
            case COMBINED: {
                type = BasketCombinedItem.class;
                break;
            }
            case SINGLEMENUITEM: {
                type = BasketSingleItem.class;
                break;
            }
        }

        BasketItem basketItem = basket.findBasketItemById(basketItemId, type);
        if (basketItem != null)
            removeFromBasket(basket, basketItem);

        return getBasketItems(mapping, form, request, response);
    }


    public ActionForward removeItemUpdateCheckout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                  HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);

        Integer basketItemId = Integer.valueOf(request.getParameter("basketItemId"));
        String itemTypeStr = request.getParameter("basketItemType");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        Class type = null;
        switch (itemType) {
            case COMBINED: {
                type = BasketCombinedItem.class;
                break;
            }
            case SINGLEMENUITEM: {
                type = BasketSingleItem.class;
                break;
            }
        }

        BasketItem basketItem = basket.findBasketItemById(basketItemId, type);
        if (basketItem != null)
            removeFromBasket(basket, basketItem);

        return mapping.findForward("goTocheckoutIt");

    }



    private void setSelectedItemToRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        ClassTypeEnum itemType = (ClassTypeEnum) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);
        switch (itemType) {
            case CATEGORY: {
                Category category = (Category) session.getAttribute(Constant.LAST_SUB_CATEGORY);
                request.setAttribute("selectedMenuItem", "category" + category.getId());
                break;
            }

            case COMBINED: {
                CombinedMenuItem combined = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
                request.setAttribute("selectedMenuItem", "combined" + combined.getProductNo() + combined.getGroupId());
                break;
            }

            case SINGLEMENUITEM: {
                MenuSingleItem single = (MenuSingleItem) session.getAttribute(Constant.LAST_SINGLE_ITEM);
                request.setAttribute("selectedMenuItem", "single" + single.getId() + single.getGroupId());
                break;
            }
        }
    }

    //"CategoryComperator" class is used for sorting a List of Category Objects according to their "Sequesnce" property
    class CategoryComperator implements Comparator {
        public int compare(Object o1, Object o2) {
            Category c1 = (Category) o1;
            return c1.compareTo(o2);
        }
    }

    private BasketCombinedItem convertCombinedMenuToBasketCombined(CombinedMenuItem combinedMenuItem) {
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

    private BasketSingleItem convertSingleToBasketSingle(MenuSingleItem menuSingleItem) {
        BasketSingleItem basketSingleItem = new BasketSingleItem();
        basketSingleItem.setGroupId(menuSingleItem.getGroupId());
        basketSingleItem.setId(menuSingleItem.getId());
        return basketSingleItem;
    }

    public ActionForward resetToDefaultSelection(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        String menuType = request.getParameter(Constant.MENU_NAME);
        String menuName = null;
        Menu menuObj = null;

        if (menuType.equals(Constant.ORDER_MENU_NAME)) {
            menuName = Constant.ORDER_MENU_NAME;
        } else if (menuType.equals(Constant.SPECIAL_MENU_NAME)) {
            menuName = Constant.SPECIAL_MENU_NAME;
        }

        menuObj = InMemoryData.getMenuByName(menuName);

        assert menuObj != null;
        List<Category> specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());

        request.setAttribute(Constant.CATEGORYID, Integer.valueOf(specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX).getId()));
        request.setAttribute(Constant.MENU_NAME, menuName);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Constant.LAST_CATEGORY, getDefaultCategory(menuName));

        //creating new basket and putting it to user session
        createBasket(request);

        return getCategoryItemsToForward(mapping.findForward("getMenuAllItemList"), form, request, response);

    }

    private String setDefaultCategoryId(String menuName) throws Exception {

        Menu menuObj = null;

        if (menuName.equals("Menu")) {
            menuName = Constant.ORDER_MENU_NAME;
        } else if (menuName.equals(Constant.SPECIAL_MENU_NAME)) {
            menuName = Constant.SPECIAL_MENU_NAME;
        }

        menuObj = InMemoryData.getMenuByName(menuName);

        assert menuObj != null;
        List<Category> specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());

        return specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX).getId().toString();

    }

    private Category getDefaultCategory(String menuName) throws Exception {

        Menu menuObj = null;

        if (menuName.equals("Menu")) {
            menuName = Constant.ORDER_MENU_NAME;
        } else if (menuName.equals(Constant.SPECIAL_MENU_NAME)) {
            menuName = Constant.SPECIAL_MENU_NAME;
        }

        menuObj = InMemoryData.getMenuByName(menuName);

        assert menuObj != null;
        List<Category> specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());


        return specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX);

    }


}
