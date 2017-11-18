package com.sefryek.doublepizza.web.action;

import com.sefryek.common.LogMessages;
import com.sefryek.common.MessageUtil;
import com.sefryek.common.util.CollectionsUtils;
import com.sefryek.common.util.DateUtil;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt;
import com.sefryek.doublepizza.dpdevice.model.BasketItemForOrder;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.IContactInfoService;
import com.sefryek.doublepizza.service.IDollarService;
import com.sefryek.doublepizza.service.IPopularService;
import com.sefryek.doublepizza.service.ISliderService;
import com.sefryek.doublepizza.web.form.DeliveryAddressForm;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.actions.DispatchAction;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Jan 23, 2011
 * Time: 1:47:46 PM
 */

public class FrontendAction extends DispatchAction {
    private IDollarService dollarService;
    private IPopularService popularService;
    private ISliderService sliderService;
    private IContactInfoService contactInfoService;

    public enum ClassTypeEnum {
        CATEGORY, COMBINED, SINGLEMENUITEM
    }

    private Logger logger = Logger.getLogger(FrontendAction.class);

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        int dayNo = DateUtil.getDayOfWeekNumber(new Date());
        Float todayDPDollarsPercentage = dollarService.getDollarPercentageByNumberOfDay(dayNo);
        DpDollarSchedule dpDollarSchedule = dollarService.findDPDollarSchedualByDate(new Date());
        todayDPDollarsPercentage = (dpDollarSchedule != null && dpDollarSchedule.getPercentage() > 0) ? dpDollarSchedule.getPercentage() : todayDPDollarsPercentage;
        session.setAttribute("todayDPDollarsPercentage", todayDPDollarsPercentage);

        List<Map<String,Float>> dpDollarsWeeklyList = dollarService.getDpDollarsWeekly(locale);

        session.setAttribute("dpDollarsWeeklyList", dpDollarsWeeklyList);

        return goToMainPage(mapping, form, request, response);

    }

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
        popularService = (IPopularService) context.getBean(IPopularService.BEAN_NAME);
        contactInfoService = (IContactInfoService) context.getBean(IContactInfoService.BEAN_NAME);
        sliderService=(ISliderService) context.getBean(ISliderService.BEAN_NAME);

    }


    private ActionForward getCategoryItemsToForward(ActionForward forward, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCategoryItemsToForward- > " + forward.toString());

        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
        String menuNameStr = request.getParameter("menuName");
        String menuType = request.getParameter("menuType");

        if ((catIdStr == null) || (catIdStr.equals(""))) {
            catIdStr = setDefaultCategoryId(menuType);
            menuNameStr = request.getAttribute(Constant.MENU_NAME).toString();
        } else if ((catIdStr.equals("undefined"))) {
            catIdStr = setDefaultCategoryId(menuType);
        }

        String menuName;
        if (menuNameStr != null && menuNameStr.equals("Menu"))
            menuName = Constant.ORDER_MENU_NAME;
        else
            menuName = Constant.SPECIAL_MENU_NAME;

        Category category = InMemoryData.findCategoryById(menuType, Integer.valueOf(catIdStr));

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
        List<BasketItem> basketItemList = null;
        if (basket != null && basket.getBasketItemList() != null) {
            basketItemList = basket.getBasketItemList();
        }

        List<BasketCombinedItem> basketCombinedItemList = new ArrayList<BasketCombinedItem>();
        List<BasketSingleItem> basketSingleItemList = new ArrayList<BasketSingleItem>();

        if (basketItemList != null && basketItemList.size() > 0) {
            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getClassType().equals(BasketCombinedItem.class))
                    basketCombinedItemList.add((BasketCombinedItem) basketItem.getObject());

                if (basketItem.getClassType().equals(BasketSingleItem.class))
                    basketSingleItemList.add((BasketSingleItem) basketItem.getObject());
            }
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
        request.setAttribute("catId",catIdStr);

        return forward;
    }

    //forwarding user to the HomePage
    public ActionForward goToMainPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToMainPage - > forwardtoHomePage");

        List<Slide> sliderList = sliderService.findTopSlides();

        HttpSession session = request.getSession();

        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);
        session.setAttribute("sliderList", sliderList);
        Object defualtLanguse = session.getAttribute("setdefaultlanguage");
        if (defualtLanguse == null) {
            session.setAttribute(Globals.LOCALE_KEY, new Locale ("fr", "FR"));
            session.setAttribute("setdefaultlanguage", "true");

        }

        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Home);

        List<Popular> popularItems = popularService.getActivePopulars();
        session.setAttribute("popularItems",popularItems);


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
    //redirect user to the DoublePizzaDOLLARS
    public ActionForward goToDPDOLLARS(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToDPDOLLARS - > forwardToDPDOLLARS");
        return mapping.findForward("forwardToDPDOLLARS");
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
    public ActionForward goToPopUpPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToPopUpPage - > forwardtoPopUpPage");

        return mapping.findForward("forwardtoPopUpPage");
    }

    //redirect user to the Funzone
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
        Boolean isFromCheck = Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE));
        User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
        Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
        request.setAttribute(Constant.Selected_Menu_Item_Att_Name, Constant.Menu_Item_Home);
        InMemoryData.setLatestUserUrl(request.getParameter(Constant.LATEST_USER_URL));
        if (request.getParameter("dontShowSuggestionsAgin") != null){
//                request.getSession().setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, true);
        }
        if (isFromCheck && user != null){
            return mapping.findForward("deliveryAddress");
        }

        // Saeid AmanZadeh
        //----------------- set todayDPDollarsPercentage on session --------------
        HttpSession session = request.getSession();
        //Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        int dayNo = DateUtil.getDayOfWeekNumber(new Date());
        Float todayDPDollarsPercentage = dollarService.getDollarPercentageByNumberOfDay(dayNo);
        DpDollarSchedule dpDollarSchedule = dollarService.findDPDollarSchedualByDate(new Date());
        todayDPDollarsPercentage = (dpDollarSchedule != null && dpDollarSchedule.getPercentage() > 0) ? dpDollarSchedule.getPercentage() : todayDPDollarsPercentage;
        session.setAttribute("todayDPDollarsPercentage", todayDPDollarsPercentage);

        List<Map<String,Float>> dpDollarsWeeklyList = dollarService.getDpDollarsWeekly(locale);

        session.setAttribute("dpDollarsWeeklyList", dpDollarsWeeklyList);
        //

        return mapping.findForward("forwardToLoginPage");

    }

    private DeliveryAddressForm userToDeliveryAddressForm(User user, ContactInfo contactInfo, Locale locale){

        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        deliveryAddressForm.setTitle(user.getTitle().toString());
        deliveryAddressForm.setEmail(user.getEmail());
        deliveryAddressForm.setLastName(user.getLastName());
        deliveryAddressForm.setCity(contactInfo.getCity());
        deliveryAddressForm.setCompany(user.getCompany());
//        String phone = user.getCellPhone();
/*        if (phone != null){
            deliveryAddressForm.setCellPhone1(phone.substring(0, 3));
            deliveryAddressForm.setCellPhone2(phone.substring(3, 6));
            deliveryAddressForm.setCellPhone3(phone.substring(6, 10));
        }*/
        if (contactInfo != null && !"".equalsIgnoreCase((deliveryAddressForm.getContactInfoId())))
        {
            contactInfo.setId(Long.valueOf(deliveryAddressForm.getContactInfoId()));
        }
        deliveryAddressForm.setExt(contactInfo.getExt());
        deliveryAddressForm.setStreetNo(contactInfo.getStreetNo());
        deliveryAddressForm.setSuiteApt(contactInfo.getSuiteAPT());
        deliveryAddressForm.setBuilding(contactInfo.getBuilding());
        deliveryAddressForm.setDoorCode(contactInfo.getDoorCode());
        String postalCode = contactInfo.getPostalcode();
        deliveryAddressForm.setPostalCode1(postalCode.substring(0, 3));
        deliveryAddressForm.setPostalCode2(postalCode.substring(4, 7));
        deliveryAddressForm.setAddressName(contactInfo.getAddressScreenEN());
        deliveryAddressForm.setStreet(contactInfo.getStreet());
        return deliveryAddressForm;
    }

    //forwarding user to the CustomizePage(Putting items to the session and Calculating the price of basket)
    public ActionForward goToCustomizePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToCustomizePage - > forwardToCustomizePage");
        //We have to create a new basket in session before show customize page
            if(request.getParameter("clUrl") != null && request.getParameter("clUrl").equals("1")){
                    logger.info(LogMessages.START_OF_METHOD + "getSpecialList");
                    HttpSession session = request.getSession();
                    session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);

                    String menuType = request.getParameter("menuType");
                    session.setAttribute(Constant.MENU_TYPE,menuType);
                    Menu menuObj = new Menu(null);
                    if (menuType.equals("menu")) {
                        menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
                    } else if (menuType.equals("special")) {
                        menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
                    }

                    String menuName = (String)session.getAttribute(Constant.MENU_NAME);

                    if (menuName != null) {
                        session.setAttribute(Constant.MENU_NAME, menuName);
                    }
                    session.setAttribute("menuType", menuType);

                    if (menuObj.getCategoryList() != null){
                        List<Category> specialList = menuObj.getCategoryList();
                        Collections.sort(specialList, new CategoryComperator());
                        request.setAttribute("menu_special", specialList);
                    }
                    if (getDefaultCategory(menuType) != null && getDefaultCategory(menuType).getId() != null){
                        request.setAttribute("defaultSelectedCategoryId", getDefaultCategory(menuType).getId());
                    }

                    request.setAttribute("menuType", menuType);
                    request.setAttribute("defaultSelectedCategoryIndex", Constant.DEFAULT_SELECTED_CATEGORY_INDEX);
            }
//        ================================   from getCategoryAllItemList
        HttpSession session = request.getSession();
        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
        String menuType = request.getParameter(Constant.MENU_TYPE);
        String customizingMod = request.getParameter("customizing");
//        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM,Integer.valueOf(customizingMod));
        Category category = InMemoryData.findCategoryById(menuType, Integer.valueOf(catIdStr));


        //putting the user last_selected category to the session(if an attribute
        //with the name Constant.LAST_CATEGORY exist it's value will be replaced)
        session.setAttribute(Constant.LAST_CATEGORY, category);
        session.setAttribute(Constant.MENU_NAME, menuType);
        session.setAttribute(Constant.MENU_TYPE, menuType);
        session.setAttribute(Constant.CATEGORYID, catIdStr);
        session.setAttribute("menuType", menuType);

        //creating new basket and putting it to user session
        createBasket(request);

//       ================== from getCategoryItemsToForward
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        List<BasketItem> basketItemList = null;
        if (basket != null && basket.getBasketItemList() != null) {
            basketItemList = basket.getBasketItemList();
        }

        List<BasketCombinedItem> basketCombinedItemList = new ArrayList<BasketCombinedItem>();
        List<BasketSingleItem> basketSingleItemList = new ArrayList<BasketSingleItem>();

        if (basketItemList != null && basketItemList.size() > 0) {
            for (BasketItem basketItem : basketItemList) {
                if (basketItem.getClassType().equals(BasketCombinedItem.class))
                    basketCombinedItemList.add((BasketCombinedItem) basketItem.getObject());

                if (basketItem.getClassType().equals(BasketSingleItem.class))
                    basketSingleItemList.add((BasketSingleItem) basketItem.getObject());
            }
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
        request.setAttribute("catId",catIdStr);
//       =============================================

        String itemId;
        String groupId;

//        HttpSession session = request.getSession();

        session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, null);

        MenuSingleItem menuSingleItem;
        CombinedMenuItem combinedMenuItem;
        Category innerCategory;

        String itemTypeStr = request.getParameter("type");
        ClassTypeEnum itemType = ClassTypeEnum.valueOf(itemTypeStr);
        session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, itemType);
      if(customizingMod.equals("0")){
        switch (itemType) {
            case CATEGORY: {
                itemId = request.getParameter("categoryId");
                innerCategory = InMemoryData.getInnerCategoryById(itemId);

                session.setAttribute(Constant.LAST_SUB_CATEGORY, innerCategory);
                request.setAttribute("imageAddress",innerCategory.getImageURL());
                session.setAttribute(Constant.LAST_SELECTED_ITEM, null);
                break;
            }
            case COMBINED: {
                itemId = request.getParameter("combinedId");
                groupId = request.getParameter("groupId");
//                combinedMenuItem = InMemoryData.findCombinedInnerCategory(itemId, groupId, category);
                combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(itemId, groupId);
                List<MenuSingleItem> menuSingleItems = combinedMenuItem.getMenuSingleItemList();

                session.setAttribute(Constant.LAST_COMBINED_ITEM, combinedMenuItem);
                session.setAttribute(Constant.LAST_SELECTED_ITEM, combinedMenuItem);
                BasketCombinedItem basketCombinedItem = new BasketCombinedItem();
                basketCombinedItem.setGroupIdRef(combinedMenuItem.getGroupId());

                request.setAttribute("price",combinedMenuItem.getFormattedPrice());
                request.setAttribute("imageAddress",combinedMenuItem.getImageURl());
                session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.COMBINED);
                break;

            }
            case SINGLEMENUITEM: {
                itemId = request.getParameter("singleId");
                groupId = request.getParameter("groupId");
                if(groupId.equals("null"))
                    groupId = "";
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

                if (exclusiveToppingCategoryList.size() != 0){
                    request.setAttribute("exlusiveToppingCategory", exclusiveToppingCategoryList);
                }
                if (noneExlusiveToppingCategoryList.size() != 0){
                request.setAttribute("itemToppingCategory", noneExlusiveToppingCategoryList);
                }
                request.setAttribute("price",menuSingleItem.getFormattedPrice());
                request.setAttribute("imageAddress",menuSingleItem.getImageURL());
                session.setAttribute(Constant.LAST_SELECTED_ITEM, menuSingleItem);
                session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.SINGLEMENUITEM);
                break;
            }
        }
    } else if (customizingMod.equals("1")){
          itemId = request.getParameter("combinedId");
          groupId = request.getParameter("groupId");
          String identifier = request.getParameter("identifier");
          BasketCombinedItem basketCombinedItem = findBasketCombinedItemInBasketByIdentifier(Integer.valueOf(identifier), request);
          session.setAttribute(Constant.BASKET_COMBINED_IN_SESSION, basketCombinedItem);
          if(basketCombinedItem != null){
              combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(itemId, groupId);
              List<MenuSingleItem> menuSingleItems = combinedMenuItem.getMenuSingleItemList();
              if(basketCombinedItem.getBasketSingleItemList() != null){
                  BasketSingleItem basketSingleItem = basketCombinedItem.getBasketSingleItemList().get(0);

                  session.setAttribute(Constant.LAST_BASKET_SINGLE_ITEM, basketSingleItem);
                  session.setAttribute(Constant.LAST_BASKET_SINGLE_IDENTIFIER, basketSingleItem.getIdentifier());
              }

              session.setAttribute(Constant.LAST_SINGLE_ITEM, combinedMenuItem.getMenuSingleItemList().get(0));
              session.setAttribute(Constant.LAST_COMBINED_ITEM, combinedMenuItem);
              session.setAttribute(Constant.LAST_SELECTED_ITEM, combinedMenuItem);
              session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.COMBINED);
              basketCombinedItem.setGroupIdRef(combinedMenuItem.getGroupId());

              request.setAttribute("price",combinedMenuItem.getFormattedPrice());
              request.setAttribute("imageAddress",combinedMenuItem.getImageURl());

          }
      } else if (customizingMod.equals("2")){
          String identifier = request.getParameter("identifier");
          itemId = request.getParameter("singleId");
          BasketSingleItem basketSingleItem = findBasketSingleItemInBasketByIdentifier(Integer.valueOf(identifier), request);
          if(basketSingleItem != null){
              session.setAttribute(Constant.LAST_BASKET_SINGLE_ITEM, basketSingleItem);
              session.setAttribute(Constant.LAST_BASKET_SINGLE_IDENTIFIER, basketSingleItem.getIdentifier());
              menuSingleItem = basketSingleItem.getSingle();
              session.setAttribute(Constant.LAST_SINGLE_ITEM, menuSingleItem);
              session.setAttribute(Constant.LAST_SELECTED_ITEM, menuSingleItem);
              session.setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.SINGLEMENUITEM);
              request.setAttribute("price",menuSingleItem.getFormattedPrice());
              request.setAttribute("imageAddress",menuSingleItem.getImageURL());
          }
      }
        request.setAttribute("itemType",itemType);
        request.setAttribute("menuType",menuType);
        setSelectedItemToRequest(request);
        return getCategoryItemsToForward(mapping.findForward("forwardToCustomizePage"), form, request, response);
    }


    public  BasketSingleItem findBasketSingleItemInBasketByIdentifier(Integer identifier, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        List<BasketItem> basketItemList = basket.getBasketItemList();
        for (BasketItem basketItem : basketItemList) {
            if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                BasketSingleItem basketSingleItem = (BasketSingleItem) basketItem.getObject();
                if (basketSingleItem.getIdentifier().equals(identifier))
                    return basketSingleItem;
            }
        }
        return null;
    }

    public  BasketCombinedItem findBasketCombinedItemInBasketByIdentifier(Integer identifier, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        List<BasketItem> basketItemList = basket.getBasketItemList();
        for (BasketItem basketItem : basketItemList) {
            if (basketItem.getClassType().equals(BasketCombinedItem.class)) {
                BasketCombinedItem basketCombinedItem = (BasketCombinedItem) basketItem.getObject();
                if (basketCombinedItem.getIdentifier().equals(identifier))
                    return basketCombinedItem;
            }
        }
        return null;
    }

    public ActionForward resetMenuObjectsOnSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Basket basket = createBasket(request);

        String itemId;
        String groupId;
        String catId = request.getParameter("catId");
        catId = (catId == null || catId.isEmpty() || catId.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catId;
        String menuName = request.getParameter("menuName");
        String menuType = request.getParameter("menuType");
        Integer identifier = Integer.valueOf(request.getParameter("identifier"));
        Category category = InMemoryData.findCategoryById(menuType, Integer.valueOf(catId));
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
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
        String menuNameStr = request.getParameter("menuName");
        String menuType = request.getParameter("menuType");
        String menuName;
        if (menuNameStr.equals("Menu"))
            menuName = Constant.ORDER_MENU_NAME;
        else
            menuName = Constant.SPECIAL_MENU_NAME;
        Category category = InMemoryData.findCategoryById(menuType, Integer.valueOf(catIdStr));

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
        /*logger.info(LogMessages.START_OF_METHOD + "getSpecialList");
        request.setAttribute("menu_special",null);
        List<Category> menuSpecialList = new ArrayList<Category>();
        menuSpecialList.clear();
        menuSpecialList = getMenuSpecialList(request);
        request.setAttribute("menu_special", menuSpecialList);

        return mapping.findForward("getMenuList");*/
        logger.info(LogMessages.START_OF_METHOD + "getSpecialList");
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CUSTOMIZING_BASKET_ITEM, 0);

        String menuType = request.getParameter("menuType");
        session.setAttribute(Constant.MENU_TYPE,menuType);
        Menu menuObj = new Menu(null);
        if (menuType.equals("menu")) {
            menuObj = InMemoryData.getItemByName(Constant.ORDER_MENU_NAME);
        } else if (menuType.equals("special")) {
            menuObj = InMemoryData.getItemByName(Constant.SPECIAL_MENU_NAME);
        }

        String menuName = (String)session.getAttribute(Constant.MENU_NAME);

        if (menuName != null) {
            session.setAttribute(Constant.MENU_NAME, menuName);
        }
        session.setAttribute("menuType", menuType);

        if (menuObj.getCategoryList() != null){
            List<Category> specialList = menuObj.getCategoryList();
            Collections.sort(specialList, new CategoryComperator());
            request.setAttribute("menu_special", specialList);
        }
        if (getDefaultCategory(menuType) != null && getDefaultCategory(menuType).getId() != null){
            request.setAttribute("defaultSelectedCategoryId", getDefaultCategory(menuType).getId());
        }

        request.setAttribute("menuType", menuType);
        request.setAttribute("defaultSelectedCategoryIndex", Constant.DEFAULT_SELECTED_CATEGORY_INDEX);

        // Saeid AmanZadeh
        //----------------- set todayDPDollarsPercentage on session --------------

        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        int dayNo = DateUtil.getDayOfWeekNumber(new Date());
        Float todayDPDollarsPercentage = dollarService.getDollarPercentageByNumberOfDay(dayNo);
        DpDollarSchedule dpDollarSchedule = dollarService.findDPDollarSchedualByDate(new Date());
        todayDPDollarsPercentage = (dpDollarSchedule != null && dpDollarSchedule.getPercentage() > 0) ? dpDollarSchedule.getPercentage() : todayDPDollarsPercentage;
        session.setAttribute("todayDPDollarsPercentage", todayDPDollarsPercentage);

        List<Map<String,Float>> dpDollarsWeeklyList = dollarService.getDpDollarsWeekly(locale);

        session.setAttribute("dpDollarsWeeklyList", dpDollarsWeeklyList);
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
        Category category = null;
        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
        if (catIdStr != null && ! catIdStr.isEmpty()){
         category = InMemoryData.findCategoryById(Constant.MENU_NAME, Integer.valueOf(catIdStr));
            if (category == null) {
                category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));
                if (category != null)
                    setCategoryToSession(request, category);
            }
        }

        if (category != null){

        //extracting user selected category items(including innerCategory, combinedItems & SingleItems)
        List<Category> innerCategoryList = InMemoryData.getCategoryInnerCategoryList(category);
        List<MenuSingleItem> menuItemList = InMemoryData.getCategoryMenuSingleItemList(category);
        List<CombinedMenuItem> combinedItemList = InMemoryData.getCategoryCombinedMenuItemList(category);

        ClassTypeEnum itemType = (ClassTypeEnum) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);
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
        }

        setSelectedItemToRequest(request);

        return mapping.findForward("getCategoryItems");
    }


    //returns list of singleMenuItems for a subcategory item
    public ActionForward getSubCategoryItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSubCategoryItems - > getSubCategoryItems");
        HttpSession session = request.getSession();

        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String)session.getAttribute(Constant.CATEGORYID) : catIdStr;
        String price = request.getParameter("price");
        Category category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));

        List<MenuSingleItem> menuItemList = InMemoryData.getCategoryMenuSingleItemList(category);
        request.setAttribute("menuSingleList", menuItemList);
        if (menuItemList.size() > 0) {
            request.setAttribute("selectedSingleItemForType", menuItemList.get(0).getId());
            request.setAttribute("selectedSingleItemPrice", menuItemList.get(0).getFormattedPrice());
        }
        request.setAttribute("categoryName", category.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("imageAddress", category.getImageURL());
        request.setAttribute("categoryName", category.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));

        request.getSession().setAttribute(Constant.LAST_SUB_CATEGORY, category);
//        request.getSession().setAttribute(Constant.LAST_SELECTED_ITEM_TYPE, ClassTypeEnum.CATEGORY);

        setSelectedItemToRequest(request);
        return mapping.findForward("getSubCategoryItems");
    }

    public List<MenuSingleItem> getAlternativeForType(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer customizeMode = (Integer) session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM); 
        Category category = null;
        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String)session.getAttribute(Constant.CATEGORYID) : catIdStr;
        if (catIdStr != null && !catIdStr.isEmpty()){
            category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(catIdStr));
        }

        String productNoStr = request.getParameter("productNo");
        String groupIdStr = request.getParameter("groupId");
        int itemIndex = Integer.valueOf(request.getParameter("itemIndex"));

        CombinedMenuItem combined = InMemoryData.findCombinedInnerCategory(productNoStr,groupIdStr, category);
        if (combined == null){
            combined = (CombinedMenuItem) session.getAttribute(Constant.LAST_COMBINED_ITEM);
        }
        List<MenuSingleItem> menuSingleList = new ArrayList<MenuSingleItem>();
        if (combined != null && combined.getMenuSingleItemList() != null){
            menuSingleList.add(combined.getMenuSingleItemList().get(itemIndex));
            List<MenuSingleItem> alternatives = combined.getAlternatives().get(itemIndex);
            for (MenuSingleItem item : alternatives) {
                menuSingleList.add(item);
            }

//            request.setAttribute("menuSingleList", menuSingleList);
            request.setAttribute("imageAddress", combined.getImageURl());
            request.setAttribute("categoryName", combined.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)));
            if (menuSingleList.size() > 0) {
                request.setAttribute("selectedSingleItemForType", menuSingleList.get(0).getId());
                request.setAttribute("selectedSingleItemPrice", menuSingleList.get(0).getFormattedPrice());
            }
        }
        return menuSingleList;
    }

    public ActionForward getSingleMenuItemsForType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getSubCategoryItemsForType - > getSubCategoryItemsForType");
        request.setAttribute("menuSingleList","");
        List<MenuSingleItem> altMenuSingleList = new ArrayList<MenuSingleItem>();
        altMenuSingleList.clear();
        altMenuSingleList = getAlternativeForType(request);
        request.setAttribute("menuSingleList", altMenuSingleList);
        return mapping.findForward("getSubCategoryItems");
    }


    //returns list of singleMenuItems for a combined item
    public ActionForward getCombinedItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "getCombinedItems - > getCombinedItems");
        HttpSession session = request.getSession();

        Category category = null;
        String catIdStr = request.getParameter("catId");
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
        if (catIdStr != null && !catIdStr.isEmpty()){
         category = InMemoryData.findCategoryById(null, Integer.valueOf(catIdStr));
        }

        String productNoStr = request.getParameter("productNo");
        String groupIdStr = request.getParameter("groupId");
        CombinedMenuItem combined = null;
        if (category != null){
         combined = InMemoryData.findCombinedInnerCategory(productNoStr, groupIdStr, category);
        }
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
        catIdStr = (catIdStr == null || catIdStr.isEmpty() || catIdStr.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catIdStr;
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
        Integer customizingMod = (Integer) session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM);
        String combinedprodNum = request.getParameter("combinedprodNum");
        String combinedGroupId = request.getParameter("combinedGroupId");
        String basketItemId = request.getParameter("basketItemId");
        BasketCombinedItem basketCombinedItem = null;
        if (!basketItemId.equals("null") && !basketItemId.isEmpty()){
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

//                topCatAtlList.addAll(ToppingAction.copyToppingCategoryListWithNewToppingsToToppingCategoryAlt(menuSingleItem.getToppingCategoryList(), null));
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
            String applyMsg = MessageUtil.get("msg.apply" ,(Locale) session.getAttribute(Globals.LOCALE_KEY));
//            Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
            PrintWriter out = response.getWriter();
            out.write(applyMsg);
//            out.write("Your Changes have been applied successfully.\nYou can find them in your cart if you add the item to it.");
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
//            String englishApplyMsg = MessageUtil.get("msg.apply", Locale.ENGLISH);
            String applyMsg = MessageUtil.get("msg.apply" ,(Locale) session.getAttribute(Globals.LOCALE_KEY));
//            Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
            PrintWriter out = response.getWriter();

            out.write(applyMsg);
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
            defaultToppingCategory = getDefaultToppings(ClassTypeEnum.SINGLEMENUITEM, menuSingleItem);
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

    public ActionForward removeAllIBasketItems(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        Basket basket = createBasket(request);
        basket.getBasketItemList().clear();
        request.getSession().setAttribute(Constant.BASKET, basket);

        return getMenuTypeItems(mapping,form,request,response);
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
                catId = (catId == null || catId.isEmpty() || catId.equalsIgnoreCase("null"))? (String) request.getSession().getAttribute(Constant.CATEGORYID) : catId;
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
    public ActionForward removeFullBasketItem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
        if (basketItem != null){
            removeFromBasket(basket, basketItem);
        }
        return mapping.findForward("checkoutLayout");
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
        request.setAttribute("itemType",itemType);
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

        String menuName = request.getParameter(Constant.MENU_NAME);
        String menuType = request.getParameter("menuType");
        Menu menuObj = null;

        if (menuType.equals(Constant.ORDER_MENU_NAME)) {
            menuName = Constant.ORDER_MENU_NAME;
        } else if (menuType.equals(Constant.SPECIAL_MENU_NAME)) {
            menuName = Constant.SPECIAL_MENU_NAME;
        }

        menuObj = InMemoryData.getMenuByName(menuType);

        assert menuObj != null;
        List<Category> specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());

        request.setAttribute(Constant.CATEGORYID, Integer.valueOf(specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX).getId()));
        request.setAttribute(Constant.MENU_NAME, menuName);
        request.setAttribute("menuType", menuType);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Constant.LAST_CATEGORY, getDefaultCategory(menuType));

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
        if (menuName != null && !menuName.isEmpty()) {
            menuObj = InMemoryData.getMenuByName(menuName);
        }


        List<Category> specialList = null;
//        assert menuObj != null;
        if(menuObj != null){
        specialList = menuObj.getCategoryList();
        Collections.sort(specialList, new CategoryComperator());
            return specialList.get(Constant.DEFAULT_SELECTED_CATEGORY_INDEX);
        }
        return null;


    }

    public ActionForward testServices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("services","allServices");
        return mapping.findForward("goToViewTestService");
    }

    public ActionForward testServicesResponse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String serviceType = request.getParameter("st");
        String data = "";
        response.setContentType("text/html");
        response.setHeader("cache-control", "no-cache");
        PrintWriter printWriter = response.getWriter();

        try {
            String url = "";
            if(serviceType.equals("getMenuSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/dpDevice/service/getMenu";
            }
            if(serviceType.equals("getSpecialSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getSpecial";
            }
            if(serviceType.equals("getSubMenuSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getSubMenu";
            }
            if(serviceType.equals("getSubSpecialSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getSubSpecial";
            }
            if(serviceType.equals("getAlternativesSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getAlternativesMenu";
            }
            if(serviceType.equals("getAlternativeSpecSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getAlternativesSpecial";
            }
            if(serviceType.equals("getMenuSingleSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getMenuSingleItemMenu";
            }
            if(serviceType.equals("getMenuSingleSpecSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getMenuSingleItemSpecial";
            }
            if(serviceType.equals("getToppingMenuSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getMenuToppings";
            }
            if(serviceType.equals("getToppingSpecialSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getSpecialToppings";
            }
            if(serviceType.equals("loginService")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/LoginUser";
            }
            if(serviceType.equals("registerService")){
//                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/Register";
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/NewRegister";
            }
            if(serviceType.equals("deliveryService")){
//                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/Register";
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/DeliveryInfo";
            }
            if(serviceType.equals("getContactInfoService")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getContactInfo";
            }
            if(serviceType.equals("orderService")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/Order";
            }
            if(serviceType.equals("getStoreListSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getBranches";
            }
            if(serviceType.equals("getPopularListSrv")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getPopulars";
            }
            if(serviceType.equals("getTax")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getTax";
            }
            if(serviceType.equals("getDPDollarPercent")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getDPDollarPercent";
            }
            if(serviceType.equals("getDPDollarService")){
                url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()  + "/dpDevice/service/getDPDollar";
            }
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            String input = "";
            if(!serviceType.equals("loginService")) {
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\"}";
            }
            if (serviceType.equals("loginService")){
                String username = request.getParameter("uname");
                String password = request.getParameter("passwd");
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"email\":\""+username+"\", \"password\":\""+password+"\"}";
            }
            if(serviceType.equals("registerService")){
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                String birthday = request.getParameter("birthday");
                String title = request.getParameter("title");
                String company = request.getParameter("company");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                String addressName = request.getParameter("addressName");
                String postalCode = request.getParameter("postalCode");
                String streetNo = request.getParameter("streetNo");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String building = request.getParameter("building");
                String phone = request.getParameter("phone");
                String ext = request.getParameter("ext");
                String suiteApt = request.getParameter("suiteApt");
                String doorCode = request.getParameter("doorCode");
                String subscribed = request.getParameter("subscribed");

//                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"fistname\":\""+fname+"\", \"lastname\":\""+lname+"\", \"birthday\":\""+birthday+"\", \"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\", \"password\":\""+password+"\"}";
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"firstName\":\""+fname+"\", \"lastName\":\""+lname+"\", \"birthday\":\""+birthday+"\", \"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\", \"password\":\""+password+"\", \"addressName\":\""+addressName+"\", \"postalCode\":\""+postalCode+"\", \"streetNo\":\""+streetNo+"\", \"street\":\""+street+"\", \"city\":\""+city+"\", \"building\":\""+building+"\", \"phone\":\""+phone+"\", \"ext\":\""+ext+"\", \"suiteApt\":\""+suiteApt+"\", \"doorCode\":\""+doorCode+"\", \"subscribed\":\""+subscribed+"\"}";
            }
            if(serviceType.equals("deliveryService")){
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                String title = request.getParameter("title");
                String company = request.getParameter("company");
                String email = request.getParameter("email");
                String addressName = request.getParameter("addressName");
                String postalCode = request.getParameter("postalCode");
                String streetNo = request.getParameter("streetNo");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String building = request.getParameter("building");
                String phone = request.getParameter("phone");
                String ext = request.getParameter("ext");
                String suiteApt = request.getParameter("suiteApt");
                String doorCode = request.getParameter("doorCode");

//                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"fistname\":\""+fname+"\", \"lastname\":\""+lname+"\", \"birthday\":\""+birthday+"\", \"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\", \"password\":\""+password+"\"}";
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"firstName\":\""+fname+"\", \"lastName\":\""+lname+"\",\"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\",\"postalCode\":\""+postalCode+"\", \"streetNo\":\""+streetNo+"\", \"street\":\""+street+"\", \"city\":\""+city+"\", \"building\":\""+building+"\", \"phone\":\""+phone+"\", \"ext\":\""+ext+"\", \"suiteApt\":\""+suiteApt+"\", \"doorCode\":\""+doorCode+"\"}";
            }
            if(serviceType.equals("getContactInfoService")){

                String uid = request.getParameter("uid");
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                String title = request.getParameter("title");
                String company = request.getParameter("company");
                String email = request.getParameter("email");
                String addressName = request.getParameter("addressName");
                String postalCode = request.getParameter("postalCode");
                String streetNo = request.getParameter("streetNo");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String building = request.getParameter("building");
                String phone = request.getParameter("phone");
                String ext = request.getParameter("ext");
                String suiteApt = request.getParameter("suiteApt");
                String doorCode = request.getParameter("doorCode");

//                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"fistname\":\""+fname+"\", \"lastname\":\""+lname+"\", \"birthday\":\""+birthday+"\", \"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\", \"password\":\""+password+"\"}";
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"userId\":\""+uid+"\", \"addressName\":\""+addressName+"\",\"postalCode\":\""+postalCode+"\", \"streetNo\":\""+streetNo+"\", \"street\":\""+street+"\", \"city\":\""+city+"\", \"building\":\""+building+"\", \"phone\":\""+phone+"\", \"ext\":\""+ext+"\", \"suiteApt\":\""+suiteApt+"\", \"doorCode\":\""+doorCode+"\"}";
            }
            if(serviceType.equals("getDPDollarService")){

                String uid = request.getParameter("uid");
                String fname = "";
                String lname = "";
                String title = "";
                String company = "";
                String email = "";
                String addressName= "";
                String postalCode = "";
                String streetNo = "";
                String street = "";
                String city = "";
                String building = "";
                String phone = "";
                String ext = "";
                String suiteApt = "";
                String doorCode = "";

//                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"fistname\":\""+fname+"\", \"lastname\":\""+lname+"\", \"birthday\":\""+birthday+"\", \"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\", \"password\":\""+password+"\"}";
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"userId\":\""+uid+"\", \"addressName\":\""+addressName+"\",\"postalCode\":\""+postalCode+"\", \"streetNo\":\""+streetNo+"\", \"street\":\""+street+"\", \"city\":\""+city+"\", \"building\":\""+building+"\", \"phone\":\""+phone+"\", \"ext\":\""+ext+"\", \"suiteApt\":\""+suiteApt+"\", \"doorCode\":\""+doorCode+"\"}";
            }
            if(serviceType.equals("orderService")){

                String userId;
                String addressId;
                String deliveryType;
                String orderPrice;
                String paymentType;
                String storeId;
                String deliveryNote;
                userId=request.getParameter("order[userId]");
                addressId=request.getParameter("order[addressId]");
                deliveryType=request.getParameter("order[deliveryType]");
                paymentType=request.getParameter("order[paymentType]");
                orderPrice=request.getParameter("order[orderPrice]");
                storeId=request.getParameter("order[storeId]");



                   List<BasketItemForOrder> basketItems;
//                   basketItems=(BasketItemForOrder)request.getParameter("basketItems");
                   Object objBasketItems=request.getParameter("basketItems");
                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"userId\":\""+userId+"\", \"addressId\":\""+addressId+"\", \"deliveryType\":\""+deliveryType+"\", \"paymentType\":\""+paymentType+"\", \"orderPrice\":\""+orderPrice+"\", \"storeId\":\""+storeId+"\"}";
//                input = "{\"udid\":\"BFA21522-E1CC-4331-B8CD-CD7C26C059E1\",\"version\":\"1.0\", \"fistname\":\""+fname+"\", \"lastname\":\""+lname+"\",\"title\":\""+title+"\", \"company\":\""+company+"\", \"email\":\""+email+"\",\"postalCode\":\""+postalCode+"\", \"streetNo\":\""+streetNo+"\", \"street\":\""+street+"\", \"city\":\""+city+"\", \"building\":\""+building+"\", \"phone\":\""+phone+"\", \"ext\":\""+ext+"\", \"suiteApt\":\""+suiteApt+"\", \"doorCode\":\""+doorCode+"\"}";
            }
            ClientResponse resp = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

            if (resp.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + resp.getStatus());
            }
            String output = resp.getEntity(String.class);

            printWriter.println(output);
            printWriter.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("goToViewTestService1");
    }

    public ActionForward viewMap(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        Store selectedStore = InMemoryData.getStore(request.getParameter(Constant.STORE_ID));

        request.setAttribute(Constant.STORE,selectedStore);

        return mapping.findForward("viewMap");
    }

    private ContactInfo getContactInfo(User user) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setUserId(user.getId());
        contactInfo.setAddressScreenEN("");
//        contactInfo.setAddressScreenFR("");
//        contactInfo.setBuilding(user.getBuilding());
//        contactInfo.setCity(user.getCity());
//        contactInfo.setDoorCode(user.getDoorCode());
//        contactInfo.setExt(user.getExt());
//        contactInfo.setPhone(user.getCellPhone());
//        contactInfo.setPostalcode(user.getPostalCode());
//        contactInfo.setStreet(user.getStreetName());
//        contactInfo.setStreetNo(user.getStreetNo());
//        contactInfo.setSuiteAPT(user.getSuiteApt());  //todo address

        return contactInfo;
    }

}
