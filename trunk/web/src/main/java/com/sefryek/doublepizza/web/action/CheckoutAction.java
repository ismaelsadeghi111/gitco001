package com.sefryek.doublepizza.web.action;

import com.sefryek.common.LogMessages;
import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper;
import com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper;
import com.sefryek.doublepizza.core.helpers.CurrencyUtils;
import com.sefryek.doublepizza.core.helpers.SuggestionsHelper;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.*;
import com.sefryek.doublepizza.web.form.DeliveryAddressForm;
import com.sefryek.doublepizza.web.form.PaymentInfoForm;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private IDollarService dollarService;
    private IContactInfoService contactInfoService;
    private IUserRoleService userRoleService;
    private ICouponService couponService;
    WebApplicationContext context;


    @Override
    public void setServlet(ActionServlet actionServlet) {
        super.setServlet(actionServlet);
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(actionServlet.getServletContext());
        orderService = (IOrderService) context.getBean(IOrderService.BEAN_NAME);
        userService = (IUserService) context.getBean(IUserService.BEAN_NAME);
        subscriberService = (ISubscriberService) context.getBean(ISubscriberService.BEAN_NAME);
        postalCodeService = (IPostalCodeService) context.getBean(IPostalCodeService.BEAN_NAME);
        dollarService = (IDollarService) context.getBean(IDollarService.BEAN_NAME);
        contactInfoService = (IContactInfoService) context.getBean(IContactInfoService.BEAN_NAME);
        userRoleService = (IUserRoleService) context.getBean(IUserRoleService.BEAN_NAME);
        couponService = (ICouponService) context.getBean(ICouponService.BEAN_NAME);
    }

    @Override
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        return goToCheckoutPage(mapping, form, request, response);
    }

    public ActionForward goToCheckoutPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToCheckoutPage - > checkoutLayout");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_TRANSIENT);
//        Double discount = (Double) session.getAttribute("discount");
//        discount = discount == null ? new Double(00.00d) : discount;
        Double discount = new Double(00.00d);
        Double couponAmount = new Double(00.00d);
        session.setAttribute("couponAmount", couponAmount);
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        if (basket == null) {
            basket = new Basket();
            session.setAttribute(Constant.BASKET, basket);
        } else {
            Double redeemableItemsPrice = calculateDiscountFromRedeemableBasketItems(request, basket);

            if (user != null) {
                if(dollarService != null){
                    user.setDpDollarBalance(dollarService.findDpDollarBalanceByUserId(user.getId()).getBalance());
                }
                if (user.getDpDollarBalance() != null && user.getDpDollarBalance() > 0) {
                    Double minRedeemablePrice = redeemableItemsPrice < user.getDpDollarBalance() ? redeemableItemsPrice : user.getDpDollarBalance();
                    discount = CurrencyUtils.doubleRoundingFormat(minRedeemablePrice);
                    String isDiscountUsedStr = request.getParameter("isDiscountUsed");
                    Boolean isDiscountUsed = isDiscountUsedStr != null ? Boolean.valueOf(isDiscountUsedStr) : null;
                    session.setAttribute("isDiscountUsed", isDiscountUsed);
                    if (isDiscountUsed != null && isDiscountUsed) {
                        Double balance = (user.getDpDollarBalance() - discount) < 0 ? new Double(00.00d) : (user.getDpDollarBalance() - discount);
                        session.setAttribute("balance", balance);
                    }

                }
            } else {
//                discount = redeemableItemsPrice;
            }
            session.setAttribute("discount", discount);

        }

        Order order = new Order();
        order.setBasketItems(basket.getBasketItemList());
        BigDecimal totalPrice = new BigDecimal(0);
        if (user != null) {
            totalPrice = BigDecimal.valueOf(basket.calculateTotalPrice().doubleValue() - discount);
        } else {
            totalPrice = basket.calculateTotalPrice();
        }
        order.setTotalPrice(totalPrice);
        session.setAttribute(Constant.ORDER, order);

        Double earnedDpDollarsAmount = new Double(00.00d);
        Float todayDPDollarsPercentage = (Float) session.getAttribute("todayDPDollarsPercentage");

       /* Double totalPriceDoubleFormat = CurrencyUtils.doubleRoundingFormat((new TaxHandler().getTotalPriceWithTax(order.getTotalPrice())));
        if(todayDPDollarsPercentage != null && totalPriceDoubleFormat != null){
            earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * totalPriceDoubleFormat;
        }*/

        if (order != null && order.getTotalPrice() != null && todayDPDollarsPercentage != null) {
            earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * (new TaxHandler().getTotalPriceWithTax(order.getTotalPrice()).doubleValue());
        } else {
            earnedDpDollarsAmount = new Double(00.00d);
        }

        earnedDpDollarsAmount = CurrencyUtils.doubleRoundingFormat(earnedDpDollarsAmount);
        session.setAttribute("earnedDpDollarsAmount", earnedDpDollarsAmount);

        return mapping.findForward("checkoutLayout");
    }

    public ActionForward setOrderCouponCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        logger.info(LogMessages.START_OF_METHOD + "setOrderCouponCode - > setOrderCouponCode");
        Order order = (Order) request.getSession().getAttribute(Constant.ORDER);
        String couponCode = request.getParameter("couponCode");
        order.setCouponCode(couponCode);
        Double couponAmount = new Double(0);
        List<Coupon> coupons = couponService.findAll();
        for (Coupon dblCoupon : coupons) {
            if (request.getParameter("couponCode").equalsIgnoreCase(dblCoupon.getCouponName())) {
                couponAmount = Double.valueOf(dblCoupon.getAmount().toString());
                break;
            }
        }
        session.setAttribute("couponAmount", couponAmount);

        return mapping.findForward("getInvoice");
    }


    public ActionForward goToDeliveryAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "goToDeliveryAddress - > deliveryAddressLayout");
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
        List<ContactInfo> userContacts = new ArrayList<ContactInfo>();
        Map<Long, ContactInfo> map = null;
        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        if (user != null) {
            userContacts = contactInfoService.getAll(user.getId());

            if (userContacts != null && userContacts.size() > 0) {
                map = new HashMap();
                for (ContactInfo c : userContacts) {
                    map.put(c.getId(), c);
                }

            } else {
                ContactInfo contactInfo = getContactInfo(user);
                contactInfoService.save(contactInfo);
                userContacts = new ArrayList<ContactInfo>();
                userContacts.add(contactInfo);
            }
            deliveryAddressForm = userToDeliveryAddressForm(user, userContacts.get(0), locale);
            request.setAttribute("deliveryAddressForm", deliveryAddressForm);
        }
        request.setAttribute("userContacts", userContacts);
        request.setAttribute("contactMap", map);

        List<PostalCode> postalCodeList = postalCodeService.getCitiesListWithPosition();
        request.setAttribute("postalCodeList", postalCodeList);
        return mapping.findForward("deliveryAddress");
    }

    public ActionForward getDeliveryUserContactInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception {

        DeliveryAddressForm deliveryAddressForm = (DeliveryAddressForm) form;
        String contactInfoListIndexStr = request.getParameter("contactInfoListIndex");
        int contactInfoListIndex = contactInfoListIndexStr == null ? new Integer(0) : Integer.valueOf(contactInfoListIndexStr);
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
        List<ContactInfo> userContacts = null;
        Map<Long, ContactInfo> map = null;
        if (user != null) {
            userContacts = contactInfoService.getAll(user.getId());

            if (userContacts == null || userContacts.size() == 0) {
                ContactInfo contactInfo = getContactInfo(user);
                contactInfoService.save(contactInfo);
                userContacts = new ArrayList<ContactInfo>();
                userContacts.add(contactInfo);
            }
            deliveryAddressForm = userToDeliveryAddressForm(user, userContacts.get(contactInfoListIndex), locale);
            request.setAttribute("deliveryAddressForm", deliveryAddressForm);
        }
        request.setAttribute("userContacts", userContacts);
        request.setAttribute("contactInfoListIndex", contactInfoListIndexStr);

        return mapping.findForward("deliveryAddress");
    }

    @Transactional()
    public ActionForward checkoutBasket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        logger.info(LogMessages.START_OF_METHOD + "checkoutBasket - > redirectToFinish");
        PaymentInfoForm paymentForm = (PaymentInfoForm) form;
        String paymentType = paymentForm.getPaymentType();
        Order.DeliveryType deliveryType = null;
        String store = "";
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute(Constant.ORDER);
        ContactInfo contactInfo = (ContactInfo) session.getAttribute("contactInfo");

        if (order != null) {
            if (order.getOrderDate() == null) {
                order.setOrderDate(new Date());
            }
            order.setPaymentType(paymentType);
            User user = (User) session.getAttribute(Constant.CHECK_OUT_USER_IN_SESSION);
            Subscriber.Gender gender = user.getTitle() == User.Title.MALE ? Subscriber.Gender.MALE : Subscriber.Gender.FEMALE;
            user.setOfficialTitle(user.getTitle() == User.Title.MALE ? User.OfficialTitle.Mr : User.OfficialTitle.Ms);
            order.setUser(user);
            deliveryType = order.getDeliveryType();
            Double discount = (Double) request.getSession().getAttribute("discount");
            Double couponAmount = (Double) request.getSession().getAttribute("couponAmount");
            discount = discount == null ? new Double(00.00d) : discount;
            Double balance = new Double(00.00d);
            if (discount > 0) {
                order.setDiscount(discount);
            }
            if (couponAmount > 0) {
                order.setDiscount((couponAmount * Double.valueOf(order.getTotalPrice().toString())) / 100);
                order.setDiscountDesc("This order use " + couponAmount + " from coupon");

            }

            if (order.getCouponCode() != "") {
                order.setDiscountDesc("This order use " + order.getCouponCode() + " from coupon");

            }
            store = order.getStore();
            try {
                logger.debug("checkoutBasket method: start saving new order");
                orderService.save(order, contactInfo, null, null);
                logger.debug("checkoutBasket method: new ordered saved");
                if (user != null && user.getIsRegistered() && order != null) {
                    checkoutDiscount(discount, order.getDocNumber(), user);
                    Float todayDPDollarsPercentage = (Float) session.getAttribute("todayDPDollarsPercentage");
                    Double earnedDpDollarsAmount = (Double) session.getAttribute("earnedDpDollarsAmount");
                    checkoutEarned(todayDPDollarsPercentage, earnedDpDollarsAmount, order.getDocNumber(), user);
                    session.setAttribute(Constant.USER_TRANSIENT, user);
                }
            } catch (Exception e) {
                logger.debug("error on save order \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                        e.getStackTrace() + " \n \t cause: \n" + e.getCause());
                return mapping.findForward("redirectToError");

            } finally {
                session.setAttribute(Constant.BASKET, null);
                session.removeAttribute("discount");
                session.removeAttribute(Constant.ORDER);
//            session.removeAttribute("totalPrice");
                session.removeAttribute("isDiscountUsed");
                session.removeAttribute("earnedDpDollarsAmount");
                session.removeAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION);
            }
        } else if (order == null) {
            return mapping.findForward("redirectToError");
        }
        session.setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, new Boolean(false));
        //-- subscriber was for device service in the phase 1
     /*   if (subscriberService.findByEmail(user.getEmail()) == null)
            subscriberService.subscribe(new Subscriber(null, null, "web", null, null, user.getEmail(), user.getLastName(),
                    gender, user.getReception()));
*/
        session.setAttribute(Constant.BASKET, new Basket());
        session.setAttribute(Constant.ORDER, new Order());
        request.getSession().removeAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT);

        session.setAttribute(Constant.LAST_ORDER_DELIVERY_TYPE, deliveryType);
        session.setAttribute(Constant.LAST_ORDER_STORE, store);
        return mapping.findForward("redirectToFinish");
    }


    private void checkoutDiscount(Double discount, String orderId, User user) {
        logger.info(LogMessages.START_OF_METHOD + "checkoutDiscount");
        if (discount != null && !discount.equals(new Double(00.00d))) {
            DpDollarHistory dpDollarHistory = new DpDollarHistory();
            dpDollarHistory.setAmount(discount);
            dpDollarHistory.setStatus(DpDollarHistory.Status.SPENT.name());
            dpDollarHistory.setOrderId(orderId);
            dpDollarHistory.setCreationDate(new Date());
            Double balance = new Double(00.00d);
            logger.debug("checkoutDiscount method: start of reattachment user to session");
            dollarService.reattachToSession(user);
            logger.debug("checkoutDiscount method: reattachment user to session finished");
            logger.debug("checkoutDiscount method: start of calculateDpDollarsBalanceForUser");
            balance = dollarService.calculateDpDollarsBalanceForUser(user.getId());
            logger.debug("checkoutDiscount method: calculateDpDollarsBalanceForUser finished");

            balance = balance - discount;
            balance = CurrencyUtils.doubleRoundingFormat(balance);
            dpDollarHistory.setBalance(balance);

            dpDollarHistory.setUser(user);
            try {
                logger.debug("checkoutDiscount method: start of earned dpDollar saving");
                dollarService.saveHistory(dpDollarHistory);
                logger.debug("checkoutDiscount method: earned dpDollar saving finished");
            } catch (Exception e) {
                logger.debug("checkoutDiscount Method: error on saving Discount DpDollar  \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                        e.getStackTrace() + " \n \t cause: \n" + e.getCause());
                e.printStackTrace();
            }
            user.setDpDollarBalance(balance);
            logger.debug("checkoutDiscount method: start of reattachment user to session");
            dollarService.reattachToSession(user);
            logger.debug("checkoutDiscount method: reattachment user to session finished");
        }

    }

    private void checkoutEarned(Float todayDPDollarsPercentage, Double earnedDpDollarsAmount, String orderId, User user) {
        logger.info(LogMessages.START_OF_METHOD + "checkoutEarned");
        earnedDpDollarsAmount = earnedDpDollarsAmount == null ? new Double(0) : earnedDpDollarsAmount;
        DpDollarHistory dpDollarHistory = new DpDollarHistory();
        dpDollarHistory.setAmount(earnedDpDollarsAmount);
        dpDollarHistory.setStatus(DpDollarHistory.Status.EARNED.name());
        dpDollarHistory.setPercentage(CurrencyUtils.doubleRoundingFormat((todayDPDollarsPercentage)));
        dpDollarHistory.setCreationDate(new Date());
        Double balance = new Double(00.00d);
        logger.debug("checkoutEarned method: start of reattachment user to session");
        dollarService.reattachToSession(user);
        logger.debug("checkoutEarned method: reattachment user to session finished");
        logger.debug("checkoutEarned method: start of calculateDpDollarsBalanceForUser: " + user.getId() + " : ");
        balance = dollarService.calculateDpDollarsBalanceForUser(user.getId());
        logger.debug("checkoutEarned method: calculateDpDollarsBalanceForUser finished and balance is: " + balance);
        balance = balance + earnedDpDollarsAmount;
        balance = CurrencyUtils.doubleRoundingFormat(balance);
        logger.debug("checkoutEarned method: doubleRoundingFormat balance is: " + balance);
        dpDollarHistory.setUser(user);

        dpDollarHistory.setBalance(balance);
        dpDollarHistory.setOrderId(orderId);
        try {
            logger.debug("checkoutEarned method: start of earned dpDollar saving");
            dollarService.saveHistory(dpDollarHistory);
            logger.debug("checkoutEarned method: earned dpDollar saving finished");
        } catch (Exception e) {
            logger.debug("checkoutEarned Method: error on saving Earned DpDollar \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            e.printStackTrace();
        }
        user.setDpDollarBalance(balance);
        logger.debug("checkoutEarned method: start of reattachment user to session");
        dollarService.reattachToSession(user);
        logger.debug("checkoutEarned method: reattachment user to session finished");

    }

    public ActionForward createUserForDeliveryAddress(ActionMapping mapping, ActionForm form,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception {

        DeliveryAddressForm deliveryForm = (DeliveryAddressForm) form;


        String title = deliveryForm.getTitle();
        String firstName = deliveryForm.getFirstName();
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
        String addressName = deliveryForm.getAddressName();
        Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
        Date deliverTime = null;
        if (deliverLater) {
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            deliverTime = formatter.parse(deliveryForm.getDeliverTime());
        }
        ActionErrors errors = deliveryForm.validate(mapping, request);
        //todo: un-comment if (!errors.isEmpty()
        if (!errors.isEmpty()) {

            List<PostalCode> postalCodeList = postalCodeService.getCitiesListWithPosition();
            request.setAttribute("postalCodeList", postalCodeList);

            saveErrors(request, errors);
            return mapping.findForward("deliveryAddress");
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.USER_TRANSIENT);

        if (user != null) {
            userService.update(user);
            ContactInfo contactInfo = new ContactInfo();
            contactInfo = getContactInfo(deliveryForm, user.getId(), locale);
            session.setAttribute("contactInfo", contactInfo);
            if (deliveryForm.getDeliveryType().equals(OrderHistory.DeliveryType.DELIVERY)) {
                contactInfoService.update(contactInfo);
            }

        } else {
            String lang = "";
            UserRole userRole = userRoleService.findByName(IUserRoleService.UserRoleName.ROLE_USER);
            logger.debug("After finding user role");
            if (locale.getDisplayName().equals("English")) {
                lang = "En";
            } else {
                lang = "Fr";
            }
            user = new User(User.Title.valueOf(title), email, firstName, lastName, null, company, /*city,
                    postalCode, streetNo, streetName, suiteApt, building, doorCode,*/ null, null,
                    ext, lang, "False", userRole);
            logger.debug("go to save user");
            user.setIsRegistered(false);
            userService.save(user);
            logger.debug("the user has been saved");
            ContactInfo contactInfo = getContactInfo(deliveryForm, user.getId(), locale);
            contactInfoService.save(contactInfo);
            logger.debug("contact has been saved");

            session.setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);
            session.setAttribute("contactInfo", contactInfo);
        }


        user.setReception(reception);
        session.setAttribute(Constant.CHECK_OUT_USER_IN_SESSION, user);

        Order order = (Order) session.getAttribute(Constant.ORDER);
        session.setAttribute(Constant.ORDER, order);
        order.setDeliveryType(Order.DeliveryType.valueOf(deliveryType));
        if (deliverLater) {
            order.setOrderDate(deliverTime);
        }

        String storeNo = "";

        if (order.getDeliveryType() == Order.DeliveryType.DELIVERY) {
            storeNo = userService.findStoreForUser(postalCode, deliveryForm.getStreetNo());
        } else {
            storeNo = store;
        }

        if (storeNo == null || storeNo.equals("")) {
            storeNo = Constant.NO_DELIVERY_CODE;
            response.addCookie(new Cookie("homeAlert", "fromNoDeliveryCode"));
        }

        order.setStore(storeNo);

//        if (storeNo == null || storeNo.equals(Constant.NO_DELIVERY_CODE)){
//            response.addCookie(new Cookie("homeAlert", "fromFindStore"));
//            return mapping.findForward("redirectToStoreLocator");
//        }

        session.setAttribute(Constant.NEAR_STORE_IN_SESSION, storeNo);
        session.setAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT, true);
        return mapping.findForward("redirectToSuccess");

    }

    public ActionForward getStreetName(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
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

        cityName = cityName != null ? cityName : "";
        streetName = streetName != null ? streetName : "";
        cityAndStreetStr = streetName + "*" + cityName;

        out.write(cityAndStreetStr);

        out.close();

        return null;

    }

    public ActionForward forwardToFinish(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        orderService = (IOrderService) context.getBean(IOrderService.BEAN_NAME);
        List<OrderHistory> orderHistories = new ArrayList<OrderHistory>();
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        if (user != null) {
            orderHistories = orderService.getOrderHistoryByUserId(user);
            request.getSession().setAttribute("orderHistoriesSize", orderHistories.size());
        }
        return mapping.findForward("finish");
    }

    public ActionForward forwardToSuccess(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response) {
        Boolean isTrustedForCheckout = (Boolean) request.getSession().getAttribute(Constant.USER_IS_TRUSTED_FOR_CHECKOUT);
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

    private Map<String, Integer> suggestionsSingleParamToMap(String paramStr) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        String[] params = paramStr.split(",");
        for (String param : params) {
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
        Order order = (Order) session.getAttribute(Constant.ORDER);
        User user = (User) session.getAttribute(Constant.USER_TRANSIENT);
        Double discount = (Double) session.getAttribute("discount");
        discount = new Double(00.00d);
        Double couponAmount = new Double(00.00d);
        session.setAttribute("couponAmount", couponAmount);
        Double earnedDpDollarsAmount = new Double(00.00d);
        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        SuggestionsHelper.addConfirmedSuggestionsToBasket(suggestionsMap, basket);

        if (basket == null) {
            basket = new Basket();
            session.setAttribute(Constant.BASKET, basket);
        } else {
            Double redeemableItemsPrice = calculateDiscountFromRedeemableBasketItems(request, basket);

            if (user != null) {
                if (user.getDpDollarBalance() != null && user.getDpDollarBalance() > new Double(00.00d)) {
                    Double minRedeemablePrice = redeemableItemsPrice < user.getDpDollarBalance() ? redeemableItemsPrice : user.getDpDollarBalance();
                    discount = CurrencyUtils.doubleRoundingFormat(minRedeemablePrice);
                    String isDiscountUsedStr = request.getParameter("isDiscountUsed");
                    Boolean isDiscountUsed = isDiscountUsedStr != null ? Boolean.valueOf(isDiscountUsedStr) : null;
                    session.setAttribute("isDiscountUsed", isDiscountUsed);
                    if (isDiscountUsed != null && isDiscountUsed) {
                        Double balance = (user.getDpDollarBalance() - discount) < 0 ? new Double(00.00d) : (user.getDpDollarBalance() - discount);
                        session.setAttribute("balance", balance);
                    }

                }
            } else {
//                discount = redeemableItemsPrice;
            }
            session.setAttribute("discount", discount);
        }


        if (order != null) {
            order.setBasketItems(basket.getBasketItemList());
            BigDecimal totalPrice = new BigDecimal(0);
            if (user != null) {
                totalPrice = BigDecimal.valueOf(basket.calculateTotalPrice().doubleValue() - discount);
            } else {
                totalPrice = basket.calculateTotalPrice();
            }
            order.setTotalPrice(totalPrice);
            session.setAttribute(Constant.ORDER, order);


            Float todayDPDollarsPercentage = (Float) session.getAttribute("todayDPDollarsPercentage");

           /* Double totalPriceDoubleFormat = CurrencyUtils.doubleRoundingFormat((new TaxHandler().getTotalPriceWithTax(order.getTotalPrice())));
            if(todayDPDollarsPercentage != null && totalPriceDoubleFormat != null){
                earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * totalPriceDoubleFormat;
            }*/

            if (order != null && order.getTotalPrice() != null && todayDPDollarsPercentage != null) {
                earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * (new TaxHandler().getTotalPriceWithTax(order.getTotalPrice()).doubleValue());
            } else {
                earnedDpDollarsAmount = new Double(00.00d);
            }

            earnedDpDollarsAmount = CurrencyUtils.doubleRoundingFormat(earnedDpDollarsAmount);
            session.setAttribute("earnedDpDollarsAmount", earnedDpDollarsAmount);
        }


//        session.setAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION, new Boolean(true));

//        return mapping.findForward("goTocheckoutIt");
        return mapping.findForward("checkoutLayout");
    }

    public ActionForward redeem(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {
        Double discount = (Double) request.getSession().getAttribute("discount");
        discount = discount == null ? new Double(00.00d) : discount;

        String redeemedDollarStr = request.getParameter("redeemedDollar");
        discount += redeemedDollarStr != null ? Double.valueOf(redeemedDollarStr) : new Double(00.00d);
        request.getSession().setAttribute("discount", discount);
        Double couponAmount = new Double(00.00d);
        request.setAttribute("couponAmount", couponAmount);
        User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
        Double currentDollar = user.getDpDollarBalance() - discount;
        currentDollar = currentDollar < 0 ? new Double(00.00d) : currentDollar;
        user.setDpDollarBalance(CurrencyUtils.doubleRoundingFormat(currentDollar));
        request.getSession().setAttribute(Constant.USER_TRANSIENT, user);
        return null;
    }

    public Double calculateDiscountFromRedeemableBasketItems(HttpServletRequest request, Basket basket) {
        Double discount = new Double(00.00d);
        List<BasketItem> basketItemList = basket.getBasketItemList();
        for (BasketItem basketItem : basketItemList) {
            if (basketItem.getClassType().equals(BasketCombinedItem.class)) {
                if (((BasketCombinedItem) basketItem.getObject()).getCombined().getIsRedeemable()) {
                    discount += Double.valueOf((new BasketCombinedItemHelper()).getPrice((BasketCombinedItem) basketItem.getObject()).setScale(2, RoundingMode.HALF_UP).toString());
                }
            } else if (basketItem.getClassType().equals(BasketSingleItem.class)) {
                Boolean isRedeemable = ((BasketSingleItem) basketItem.getObject()).getSingle().getIsRedeemable();
                if ((isRedeemable != null) && (isRedeemable)) {
                    discount += Double.valueOf((new BasketSingleItemHelper()).getPrice((BasketSingleItem) basketItem.getObject()).setScale(2, RoundingMode.HALF_UP).toString());
                }
            }
        }

        return discount;
    }

    public ActionForward calDiscountOnTotalPrice(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String isDiscountUsedStr = null;
        Boolean isDiscountUsed = null;

        String isUserUncheckedDpDollarStr = request.getParameter("isUserUncheckedDpDollar");
        Boolean isUserUncheckedDpDollar = isUserUncheckedDpDollarStr != null ? Boolean.valueOf(isUserUncheckedDpDollarStr) : null;
        session.setAttribute("isUserUncheckedDpDollarStr", isUserUncheckedDpDollar);
        if (isUserUncheckedDpDollar != null) {
            isDiscountUsed = !isUserUncheckedDpDollar;
            session.setAttribute("isDiscountUsed", isDiscountUsed);
        } else {
            isDiscountUsed = (Boolean) session.getAttribute("isDiscountUsed");
        }
//        session.setAttribute("isDiscountUsed",isDiscountUsed);

        Basket basket = (Basket) session.getAttribute(Constant.BASKET);
        User user = (User) session.getAttribute(Constant.USER_TRANSIENT);
        Double discount = (Double) session.getAttribute("discount");
        Order order = (Order) session.getAttribute(Constant.ORDER);
        BigDecimal totalPrice = new BigDecimal(0);
        if (isDiscountUsed != null && isDiscountUsed) {
            discount = discount == null ? new Double(0) : discount;
            if (basket != null) {
               /*
                Double reminedUserDollar = user.getDpDollarBalance() - discount;
                reminedUserDollar = reminedUserDollar == null || reminedUserDollar < 0 ? new Double(00.00d) : reminedUserDollar;
                user.setDpDollarBalance(CurrencyUtils.doubleRoundingFormat(reminedUserDollar));
                */
//                ============================
                if (user != null) {
                    Double redeemableItemsPrice = calculateDiscountFromRedeemableBasketItems(request, basket);
                    if (user.getDpDollarBalance() != null && user.getDpDollarBalance() > new Double(00.00d)) {
                        Double minRedeemablePrice = redeemableItemsPrice < user.getDpDollarBalance() ? redeemableItemsPrice : user.getDpDollarBalance();
                        discount = CurrencyUtils.doubleRoundingFormat(minRedeemablePrice);

                        Double balance = (user.getDpDollarBalance() - discount) < 0 ? new Double(00.00d) : (user.getDpDollarBalance() - discount);
                        session.setAttribute("balance", balance);
                    }
                    totalPrice = BigDecimal.valueOf(basket.calculateTotalPrice().doubleValue() - discount);
                }
            }

        } else {
            if (basket != null) {
                totalPrice = basket.calculateTotalPrice();
                discount = new Double(00.00d);

            }

        }
        BigDecimal totalPriceWithServiceCost = new BigDecimal(0);
        if(totalPrice != null && totalPrice.compareTo(new BigDecimal(0.0))==1){
            totalPriceWithServiceCost = totalPrice.add(BigDecimal.valueOf(Constant.DEFAULT_SERVICE_COST));
        }
        session.setAttribute("discount", discount);
        order.setTotalPrice(totalPriceWithServiceCost);
        session.setAttribute(Constant.ORDER, order);
        session.setAttribute(Constant.USER_TRANSIENT, user);

        Double earnedDpDollarsAmount = new Double(00.00d);
        Float todayDPDollarsPercentage = (Float) session.getAttribute("todayDPDollarsPercentage");

        if (order != null && order.getTotalPrice() != null && todayDPDollarsPercentage != null) {
            earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * (new TaxHandler().getTotalPriceWithTax(totalPrice).doubleValue());
        } else {
            earnedDpDollarsAmount = new Double(00.00d);
        }
        /*Double totalPriceDoubleFormat = CurrencyUtils.doubleRoundingFormat((new TaxHandler().getTotalPriceWithTax(order.getTotalPrice())));
        if(todayDPDollarsPercentage != null && totalPriceDoubleFormat != null){
            earnedDpDollarsAmount = (todayDPDollarsPercentage / 100) * totalPriceDoubleFormat;
        }*/

        earnedDpDollarsAmount = CurrencyUtils.doubleRoundingFormat(earnedDpDollarsAmount);
        session.setAttribute("earnedDpDollarsAmount", earnedDpDollarsAmount);

        return mapping.findForward("getInvoice");
    }

    private ContactInfo getContactInfo(DeliveryAddressForm deliveryAddressForm, int userId, Locale locale) {
        ContactInfo contactInfo = new ContactInfo();
        if (contactInfo != null && deliveryAddressForm.getContactInfoId() != null && !"".equalsIgnoreCase((deliveryAddressForm.getContactInfoId()))) {
            contactInfo.setId(Long.valueOf(deliveryAddressForm.getContactInfoId()));
        }
        contactInfo.setUserId(userId);
        contactInfo.setAddressScreenEN(deliveryAddressForm.getAddressName());
        contactInfo.setBuilding(deliveryAddressForm.getBuilding());
        contactInfo.setCity(deliveryAddressForm.getCity());
        contactInfo.setDoorCode(deliveryAddressForm.getDoorCode());
        contactInfo.setExt(deliveryAddressForm.getExt());
        contactInfo.setPhone(deliveryAddressForm.getCellPhone1() + deliveryAddressForm.getCellPhone2() + deliveryAddressForm.getCellPhone3());
        contactInfo.setPostalcode(deliveryAddressForm.getPostalCode1() + " " + deliveryAddressForm.getPostalCode2());
        contactInfo.setStreet(deliveryAddressForm.getStreet());
        contactInfo.setStreetNo(deliveryAddressForm.getStreetNo());
        contactInfo.setSuiteAPT(deliveryAddressForm.getSuiteApt());
        contactInfo.setAddressScreenEN(deliveryAddressForm.getAddressName());

        return contactInfo;
    }

    private ContactInfo getContactInfo(User user) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setUserId(user.getId());
        contactInfo.setAddressScreenEN("");
//        contactInfo.setBuilding(user.getBuilding());
//        contactInfo.setCity(user.getCity());
//        contactInfo.setDoorCode(user.getDoorCode());
//        contactInfo.setExt(user.getExt());
//        contactInfo.setPhone(user.getCellPhone());
//        contactInfo.setPostalcode(user.getPostalCode());
//        contactInfo.setStreet(user.getStreetName());
//        contactInfo.setStreetNo(user.getStreetNo());
//        contactInfo.setSuiteAPT(user.getSuiteApt());

        return contactInfo;
    }


    private DeliveryAddressForm userToDeliveryAddressForm(User user, ContactInfo contactInfo, Locale locale) {

        DeliveryAddressForm deliveryAddressForm = new DeliveryAddressForm();
        deliveryAddressForm.setTitle(user.getTitle().toString());
        deliveryAddressForm.setEmail(user.getEmail());
        deliveryAddressForm.setFirstName(user.getFirstName());
        deliveryAddressForm.setLastName(user.getLastName());
        deliveryAddressForm.setCity(contactInfo.getCity());
        deliveryAddressForm.setCompany(user.getCompany());
        String phone = contactInfo.getPhone();
        StringBuilder userPhone = new StringBuilder(phone);
        userPhone.length();
        if (userPhone != null && userPhone.length() >= 10) {
            deliveryAddressForm.setCellPhone1(phone.substring(0, 3));
            deliveryAddressForm.setCellPhone2(phone.substring(3, 6));
            deliveryAddressForm.setCellPhone3(phone.substring(6, 10));
        }
        deliveryAddressForm.setContactInfoId(String.valueOf(contactInfo.getId()));
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


}
