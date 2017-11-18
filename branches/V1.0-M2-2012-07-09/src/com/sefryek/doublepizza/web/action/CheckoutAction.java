package com.sefryek.doublepizza.web.action;

import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import com.sefryek.doublepizza.core.LogMessages;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.SuggestionsHelper;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.IOrderService;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.ISubscriberService;
import com.sefryek.doublepizza.service.IPostalCodeService;
import com.sefryek.doublepizza.web.form.DeliveryAddressForm;
import com.sefryek.doublepizza.web.form.PaymentInfoForm;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Mar 27, 2012
 * Time: 1:13:46 PM
 */
public class CheckoutAction extends DispatchAction {

    private Logger logger = Logger.getLogger(CheckoutAction.class);

    private IUserService userService;
    private IOrderService orderService;
    private ISubscriberService subscriberService;
    private IPostalCodeService postalCodeService;
    WebApplicationContext context;

    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        orderService = (IOrderService) context.getBean(IOrderService.BEAN_NAME);
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        subscriberService = (ISubscriberService) context.getBean(ISubscriberService.BEAN_NAME);
        postalCodeService = (IPostalCodeService) context.getBean(IPostalCodeService.BEAN_NAME);
    }

    public ActionForward goToCheckoutPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToCheckoutPage - > checkoutLayout");
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        if (basket == null) {
            basket = new Basket();
            session.setAttribute(Constant.BASKET, basket);
        }

        Order order = new Order();
        order.setBasketItems(basket.getBasketItemList());
        order.setTotalPrice(basket.calculateTotalPrice());
        session.setAttribute(Constant.ORDER, order);
        
        return mapping.findForward("checkoutLayout");
    }

    public ActionForward setOrderCouponCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "setOrderCouponCode - > setOrderCouponCode");
        Order order = (Order) request.getSession().getAttribute(Constant.ORDER);
        String couponCode = request.getParameter("couponCode");
        order.setCouponCode(couponCode);

        return null;
    }


    public ActionForward goToDeliveryAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToDeliveryAddress - > deliveryAddressLayout");

        List<PostalCode> postalCodeList = postalCodeService.getCitiesListWithPosition();

        request.setAttribute("postalCodeList", postalCodeList);
        return mapping.findForward("deliveryAddress");
    }


    public ActionForward checkoutBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {

        PaymentInfoForm paymentForm = (PaymentInfoForm) form;
        String paymentType = paymentForm.getPaymentType();

        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(Constant.ORDER);
        if (order .getOrderDate() == null){
            order.setOrderDate(new Date());
        }
        order.setPaymentType(paymentType);
        User user = (User) session.getAttribute(Constant.CHECK_OUT_USER_IN_SESSION);
        Subscriber.Gender gender = user.getTitle() == User.Title.MALE ? Subscriber.Gender.MALE : Subscriber.Gender.FEMALE;
        order.setUser(user);
        Order.DeliveryType deliveryType = order.getDeliveryType();
        String store = order.getStore();
        try{
            orderService.save(order);
        }
        catch (Exception e){
            logger.error("error on save order \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            return mapping.findForward("redirectToError");
            
        }
        session.setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, new Boolean(false));
        if (subscriberService.findByEmail(user.getEmail()) == null)
            subscriberService.subscribe(new Subscriber(null, null, "web", null, null, user.getEmail(), user.getLastName(),
                    gender, user.getReception()));

        session.setAttribute(Constant.BASKET, new Basket());
        session.setAttribute(Constant.ORDER, new Order());
        request.getSession().removeAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT);

        session.setAttribute(Constant.LAST_ORDER_DELIVERY_TYPE, deliveryType);        
        session.setAttribute(Constant.LAST_ORDER_STORE, store);        
        return mapping.findForward("redirectToFinish");
    }


    public ActionForward createUserForDeliveryAddress(ActionMapping mapping, ActionForm form,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception {

        DeliveryAddressForm deliveryForm = (DeliveryAddressForm) form;                                          

        String title = deliveryForm.getTitle();
        String lastName = deliveryForm.getLastName();
        String email = deliveryForm.getEmail();
        Boolean reception = deliveryForm.getReception();
        String city = deliveryForm.getCity();
        String postalCode = deliveryForm.getPostalCode1() + " " + deliveryForm.getPostalCode2();
        String building = deliveryForm.getBuilding();
        String streetNo = deliveryForm.getStreetNo();
        String streetName = deliveryForm.getStreet();
        String suiteApt = deliveryForm.getSuiteApt();
        String doorCode = deliveryForm.getDoorCode();
        String company = deliveryForm.getCompany();
        String cellPhone1 = deliveryForm.getCellPhone1();
        String cellPhone2 = deliveryForm.getCellPhone2();
        String cellPhone3 = deliveryForm.getCellPhone3();
        String ext = deliveryForm.getExt();
        String deliveryType = deliveryForm.getDeliveryType();
        String store = deliveryForm.getStore();
        Boolean deliverLater = deliveryForm.getDeliverLater();
        Date deliverTime = null;
        if (deliverLater)
        {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mm");
            deliverTime = formatter.parse(deliveryForm.getDeliverTime());
        }
        ActionErrors errors = deliveryForm.validate(mapping, request);
        if (!errors.isEmpty()) {
            
            List<PostalCode> postalCodeList = postalCodeService.getCitiesListWithPosition();
            request.setAttribute("postalCodeList", postalCodeList);
            
            saveErrors(request, errors);
            return mapping.findForward("deliveryAddress");
        }


        User user = new User(User.Title.valueOf(title), email, null, lastName, null, company, city,
                postalCode, streetNo, streetName, suiteApt, building, doorCode, null, null,
                cellPhone1 + cellPhone2 + cellPhone3, ext);

        user.setRegistered(false);
        user.setReception(reception);
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);

        Order order = (Order) session.getAttribute(Constant.ORDER);
        session.setAttribute(Constant.ORDER, order);
        order.setDeliveryType(Order.DeliveryType.valueOf(deliveryType));
        if (deliverLater){
            order.setOrderDate(deliverTime);
        }
        
        String storeNo = "";
        
        if (order.getDeliveryType() == Order.DeliveryType.DELIVERY)
            storeNo = userService.findStoreForUser(user);
        else
            storeNo = store;
        
        order.setStore(storeNo);

        if (storeNo == null || storeNo.equals(Constant.NO_DELIVERY_CODE)){
            response.addCookie(new Cookie("homeAlert", "fromFindStore"));
            return mapping.findForward("redirectToStoreLocator");
        }

        session.setAttribute(Constant.NEAR_STORE_IN_SESSION, storeNo);
        session.setAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT, true);
        return mapping.findForward("redirectToSuccess");

    }

    public ActionForward getStreetName(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception{
        String postalCode = request.getParameter("postalCode");
        String streetNo = request.getParameter("streetNo");
        PrintWriter out = response.getWriter();
        List<String> cityAndStreet = null;
        String cityName = null;
        String streetName = null;
        String cityAndStreetStr = null;


        Pattern p = Pattern.compile("[\\d]+");
        Matcher m = p.matcher(streetNo);
        if (m.matches()) {
            cityAndStreet = userService.findStreetNameByPostalCodeAndStreetNo(postalCode, streetNo);
            response.setContentType("text/html");

            if (cityAndStreet != null && cityAndStreet.size() > 1) {
                streetName = cityAndStreet.get(0);
                cityName = cityAndStreet.get(1);
            }
        }

        cityAndStreetStr = streetName + "*" + cityName;

        out.write(cityAndStreetStr);

        out.close();

        return null;

    }

    public ActionForward forwardToFinish(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception{
        return mapping.findForward("finish");
    }

    public ActionForward forwardToSuccess(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response) {
        Boolean isTrustedForCheckout = (Boolean)request.getSession().getAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT);
        if (isTrustedForCheckout != null && isTrustedForCheckout)
            return mapping.findForward("success");
        else
            return mapping.findForward("forwardtoHomePage");
    }

    public ActionForward forwardToFailure(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response) {        
        return mapping.findForward("errorOnCheckout");
    }



    public ActionForward forwardToStoreLocator(ActionMapping mapping, ActionForm form, 
                                               HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward("forwardToStoreLocatorPage");

    }

    private Map<String, Integer> suggestionsSingleParamToMap(String paramStr){
        Map<String, Integer> result = new HashMap<String, Integer>();
        String[] params = paramStr.split(",");
        for (String param : params){
            String[] item = param.split(":");
            result.put(item[0], Integer.valueOf(item[1])); 
        }
        return result;
    }
        

    public ActionForward addSuggestionsToBasket(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) {
        String paramStr = request.getParameter("selectedSuggestions");
        Map<String, Integer> suggestionsMap = suggestionsSingleParamToMap(paramStr);
        HttpSession session = request.getSession();
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        SuggestionsHelper.addConfirmedSuggestionsToBasket(suggestionsMap, basket);
        session.setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, new Boolean(true));

        return mapping.findForward("goTocheckoutIt");
    }

}
