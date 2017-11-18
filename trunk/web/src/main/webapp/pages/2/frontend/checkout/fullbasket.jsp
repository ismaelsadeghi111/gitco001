<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.SuggestionsHelper" %>
<%@ page import="com.sefryek.common.util.CollectionsUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Integer editButNum = 0;
    BigDecimal totalPrice = null;
    String context = request.getContextPath();
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if(basket != null){
    int totalOfItems = basket.getNumberOfItems();
     totalPrice = basket.calculateTotalPrice();
    pageContext.setAttribute("basketItemsList", basket.getBasketItemList());
    }
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH, Constant.DATA_RESOURCES_FOODS_TINY_PATH);
    Order order = (Order) request.getSession().getAttribute(Constant.ORDER);
    String totalPriceOnSession = (String) request.getSession().getAttribute("totalPrice");
//    ========== Recommend
    List<Suggestion> suggestionList = new ArrayList<Suggestion>();
    Boolean reviewSuggestions = (Boolean)session.getAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION);
    if (basket != null && reviewSuggestions == null || !reviewSuggestions){
        suggestionList = CollectionsUtils.top(SuggestionsHelper.getSuggestionsForBasket(basket), 5);
    }

    Float todayDPDollarsPercentage = (Float) request.getSession().getAttribute("todayDPDollarsPercentage");
    Double totalPriceDoubleFormat = CurrencyUtils.doubleRoundingFormat((new TaxHandler().getTotalPriceWithTax(totalPrice)));

    Double earnedDpDollarsAmount = (Double) session.getAttribute("earnedDpDollarsAmount");
    earnedDpDollarsAmount = earnedDpDollarsAmount == null ? new Double(00.00d) : earnedDpDollarsAmount;
    Boolean isDiscountUsed = (Boolean) request.getSession().getAttribute("isDiscountUsed");
    isDiscountUsed = isDiscountUsed == null ? false : isDiscountUsed;
    Double discount = (Double)request.getSession().getAttribute("discount");
    discount = discount == null ? new Double(00.00d) : discount;
    Double userDollarBalance = new Double(00.00d);
    if(user != null){
         userDollarBalance = (user.getDpDollarBalance() - discount) <0 ? new Double(00.00d) : (user.getDpDollarBalance() - discount);
    }

%>
 <div  id="suggestions" style="display: none" class="dpDollar-PopUp-body">
        <div style="   padding:20px;" class="dpDollar-PopUp-title">
            <span   style="color: #FFFFE0" class="foodtitle"><h3><bean:message key="suggestions.form.title"/></h3></span>
        </div>
 <div  style="margin-top: -16px;">
  <table id="suggestionTable"  style="width: 100%;">
            <thead>
                <tr style="height:40px; ">
                   <th class="cart_product first_item" style="width:25%;"><bean:message key="label.product"/></th>
                   <th class="cart_description item" style="width:35%;"><bean:message key="label.description"/></th>
                   <th class="cart_unit item" style="width:15%;"><bean:message key="label.price"/></th>
                   <th class="cart_total last_item" style="width:20%;"><bean:message key="lable.quantity"/></th>
                </tr>
            </thead>
            <tbody id="suggestionitems">
                <%
                    ListIterator suggIterator = suggestionList.listIterator();
                    for(Suggestion suggestion : suggestionList){
                        if( suggestion.getMenuSingleItem() != null) {
                %>
                <div style="margin-bottom: 15px;">
                <tr style="; border-radius: 5px; box-shadow: 0 0 5px #A0A0A0; width: inherit">

                    <td >
                        <a href="javascript: void(0);" class="product_link">
                            <img width="100px" height="70px" src="<%=context%><%=middlepath%><%=suggestion.getMenuSingleItem().getImageURL()%>" alt="<%=suggestion.getMenuSingleItem().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>


                        </a>
                    </td>
                    <td>
                        <h5>
                            <a href="javascript: void(0);" class="product_link">
                                <%=suggestion.getMenuSingleItem().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                <c:if test="<%=suggestion.getMenuSingleItem().getIsRedeemable()%>">
                                    <div class="clear"></div>
                                    <br />
                                    <small style="font-style:italic; color:#8e8d8d;">
                                        <span><bean:message key="spend.dp$"/> </span>
                                    </small>
                                </c:if>
                            </a>

                        </h5>
                    </td>
                    <td>
                       <span>
                                        <%=(CurrencyUtils.toMoney(suggestion.getMenuSingleItem().getPrice()))%>
                        </span>
                    </td>
                    <td>
                        <div id="cart_quantity_button" class="recommend-counter">
                            <a href="javascript: void (0);" class="add" title="Subtract" onclick="changeQuantity(this, -1);" id="cart_quantity_down" rel="nofollow" style="opacity: 1;"><img alt="Subtract"  src="<%=context%>/images/sub.png"></a>
                            <input type="text" name="quantity_input" value="0" size="2" class="popup-text-box">
                            <a href="javascript: void (0);" class="add" title="Add" onclick="changeQuantity(this, 1);" id="cart_quantity_up" ><img alt="Add" src="<%=context%>/images/add.png"></a>
                        </div>
                        <input type="hidden" value="<%=suggestion.getModifierId()%>" name="quantity"/>
                    </td>


                </tr>
                </div>
                <%
                     suggIterator.next();
                  }
                }
                %>
            </tbody>
         </table>
        <br/>
        <div class="Clear"></div>
        <table style="padding:10px;" class="border" width="100%" border="0" cellspacing="0" cellpadding="0">

<%--
            <tr>
                <td height="50"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr style="font-size: 13">
                        <td>Your Double Pizza Dollar is $58.00. You can redeem

                        </td>
                        <td>
                            <input type="text" value="7" class="popup-text-box" style="width:35px;">

                        </td>
                        <td>
                            by ordering from our recomendations.
                        </td>


                    </tr>
                </table></td>
            </tr>
            <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="28%"><small style="font-style:italic; color:#8e8d8d;">Enter more then $3.00</small></td>
                        &lt;%&ndash;<td width="74%"><a class="small-red FloatRight" href="javascript: void(0);">Not Now</a></td>&ndash;%&gt;
                        <td width="26%"><a class="small-red FloatRight" href="javascript: void(0);">Redeem</a></td>
                    </tr>
                </table></td>
            </tr>
--%>
            <%--======================================--%>
            <tr>
            <c:choose>
                <c:when test="<%=user != null%>">
                <span style="font-style:italic; color:#8e8d8d; font-size: 13;">
                   <span><bean:message key="fullbasket.balance1" arg0="<%=CurrencyUtils.roundTwoDecimals(userDollarBalance)%>"/></span>&nbsp;<bean:message key="fullbasket.balance2" arg0="<%=CurrencyUtils.roundTwoDecimals(earnedDpDollarsAmount)%>"/>&nbsp;<bean:message key="fullbasket.balance3"/> </span>
                    </span>
                </c:when>
                <c:otherwise>
                <span style="font-style:italic; color:#8e8d8d; font-size: 13;">
                    <span><bean:message key="fullbasket.balance4" arg0="<%=CurrencyUtils.roundTwoDecimals(earnedDpDollarsAmount)%>"/>&nbsp;<bean:message key="fullbasket.balance5"/> </span>
                    <span>
                        <a href="javascript: void(0);" onclick="login('<%=context%>checkout.do?operation=goToCheckoutPage');"
                           style="color:#0039a6; text-decoration: HighlightText"><bean:message key="label.page.title.login"/>
                        </a>
                        &nbsp;|&nbsp;
                        <a href="<%=context%>/frontend.do?operation=goToRegistrationPage" style="color:#0039a6; text-decoration: HighlightText"><bean:message
                                key="label.page.title.register"/>
                        </a>
                    </span>
                </span>
                </c:otherwise>
            </c:choose>
            </tr>
            <%--======================================--%>

        </table>
        <div class="main-recommend-box">
            <a id="addSuggestionToCart" style="color: #FFFFF0;" class="btn-first FloatRight"  href="javascript: void(0);" onclick="addSuggestionsToBasket();"><bean:message key="button.addToCart"/></a>
            <a id="noThanx" class="btn-second FloatRight" href="javascript: void(0);" onclick="gotoLoginPageFromCheckout('true', true); setOrderCouponCodeOnServer();"><bean:message key="label.buttun.nothanx"/> </a>
        </div>
 </div>

 </div>

<%--========================================= Order Summary =======--%>


<table     width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <span class="cusfood" ><bean:message key="fullbasket.title"/></span>
        </td>
        <td>
            <a href="javascript: void(0);" onclick="setMenuType('menu'); removeAllIBasketItems();" class="btn-second FloatRight"><bean:message key="fullbasket.deleteAll"/></a>
        </td>
    </tr>
</table>
<div class="MiddleWrapper">
<%
    if ( basket != null && basket.getBasketItemList() != null && basket.getBasketItemList().size() != 0) {
%>

<table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="16%"><bean:message key="label.product"/></td>
        <td width="73%"><bean:message key="label.description"/></td>
        <td width="11%"><bean:message key="label.price"/></td>
    </tr>
</table>
<%
    List<BasketItem> basketItemList = basket.getBasketItemList();
    for (BasketItem basketItem : basketItemList) {
        pageContext.setAttribute("basketItem", basketItem);
%>
    <%--====================== COMBINED ITEMS--%>
<%if (basketItem.getClassType().equals(BasketCombinedItem.class)){ %>
    <bean:define id="basketCombinedItem" type="com.sefryek.doublepizza.model.BasketCombinedItem" name="basketItem" property="object" scope="page"/>
    <bean:define id="combinedMenuItem" type="com.sefryek.doublepizza.model.CombinedMenuItem" name="basketCombinedItem" property="combined" scope="page"/>
    <div class="Fullbasket border">
        <div class="Image-box">
                <img style="width: 150px; height:110px;"
                     src="<%=context%><%=middlepath%><%=(((BasketCombinedItem)basketItem.getObject()).getCombined()).getImageURl()%>"
                     alt="<%=(((BasketCombinedItem)basketItem.getObject()).getCombined()).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
        </div>
        <div class="des">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="border">
                    <td height="60" colspan="2">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="82%" class="padding-left">
                                    <b>
                                            <%=(((BasketCombinedItem) basketItem.getObject()).getCombined()).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                    </b>
                                </td>
                                <td width="13%">
                                </td>
                                <td width="5%">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="62%">
                                 <%--                   <a id="edit_but<%=editButNum%>" href="javascript: void(0);" class="product_link"
                                                       onclick="isInCustomizingMode = true;
                                                               &lt;%&ndash;gotoCustomizePage('combinedId', 'COMBINED', '<%=((BasketCombinedItem)basketItem.getObject()).getProductNoRef()%>', '<%=((BasketCombinedItem)basketItem.getObject()).getGroupIdRef()%>', 'edit_but<%=editButNum++%>');&ndash;%&gt;
                                                               gotoMiniBasketCustomizePage('combinedId', 'COMBINED', '<%=((BasketCombinedItem)basketItem.getObject()).getProductNoRef()%>', '<%=((BasketCombinedItem)basketItem.getObject()).getGroupIdRef()%>', '<%=((BasketCombinedItem)basketItem.getObject()).getIdentifier()%>', 'edit_but<%=editButNum++%>');
                                                               ">
                                                    <img src="<%=context%>/images/edit.png" alt="Edit"/>
                                                </a>--%>
                                            </td>
                                            <td width="38%">
                                                <a href="javascript:void(0);" class="cart_quantity_delete"
                                                   rel="nofollow"
                                                   title="Delete"
                                                   onclick="removeFullBasketItem(<%=((BasketCombinedItem)basketItem.getObject()).getIdentifier()%>, 'COMBINED');"
                                                        >
                                                    <img src="<%=context%>/images/close.png" alt="Close"/>
                                                </a>
                                                <input type="hidden" value="1" name="quantity_6_0_hidden"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%
                    for (BasketSingleItem basketSingleItem : ((BasketCombinedItem) basketItem.getObject()).getBasketSingleItemList()) {
                %>
                <tr class="yellow">
                    <td width="87%" height="30" class="padding-left">
                        <%=basketSingleItem.getSingle().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                    </td>
                    <td width="13%">
                        <%=(CurrencyUtils.toMoney((new BasketCombinedItemHelper()).getInvoicePrice((BasketCombinedItem) basketItem.getObject(), basketSingleItem)))%>
                    </td>
                </tr>

                <tr>
                    <%
                        String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString(basketSingleItem, null, true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        exclusiveText = exclusiveText.replace("<context>", context);
                        String fullText = InMemoryData.getBasketSingleItemAllToppingsToString(basketSingleItem, "full", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        String leftText = InMemoryData.getBasketSingleItemAllToppingsToString(basketSingleItem, "left", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        String rightText = InMemoryData.getBasketSingleItemAllToppingsToString(basketSingleItem, "right", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                    %>
                    <td height="30" class="padding-left">
                        <% if (fullText.length() > 0) { %>
                        <span class="red">Topping: </span>
                                <span class="gray">
                                      <%= fullText%>
                                  </span>
                        <% } %>
                        <%----------------%>
                        <br>
                        <% if (exclusiveText.length() > 0) { %>
                        <span class="red">Crust and Cooking Mode: </span>
                                  <span class="gray">
                                      <%= exclusiveText %>
                                  </span>
                        <% } %>
                        <%-----------------%>

                        <% if (leftText.length() > 0) { %>
                        <span class="red">Left: </span>
                                <span class="gray">
                                      <%= leftText%>
                                  </span>
                        <% } %>
                        <%----------------%>
                        <% if (rightText.length() > 0) { %>
                        <span class="red">Right: </span>
                                <span class="gray">
                                      <%= rightText%>
                                  </span>
                        <% } %>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <% } %>
                <tr class="dark-orange">
                    <td height="30" width="87%" class="padding-left"><bean:message key="label.rightmenu.header.total.price"/></td>
                    <td width="13%">
                        <%=CurrencyUtils.toMoney(((new BasketCombinedItemHelper()).getPrice((BasketCombinedItem) basketItem.getObject())))%>
                    </td>
                </tr>
            </table>
        </div>
    </div>
<% } %>
    <%--==============  SINGLE ITEMS--%>
<% if (basketItem.getClassType().equals(BasketSingleItem.class)){ %>
<%--<c:if test='<%= basketItem.getClassType().equals(BasketSingleItem.class) %>'>--%>
    <bean:define id="basketSingleItem" type="com.sefryek.doublepizza.model.BasketSingleItem" name="basketItem" property="object" scope="page"/>
    <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single" scope="page"/>

    <div class="Fullbasket border">
        <div class="Image-box">


                <img style="width:100px; height:70px;"
                     src="<%=context%><%=middlepath%><%=((BasketSingleItem)basketItem.getObject()).getSingle().getImageURL()%>"
                     alt="<%=((BasketSingleItem)basketItem.getObject()).getSingle().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>"/>

        </div>
        <div class="des">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="border">
                    <td height="60" colspan="2">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="82%" class="padding-left">
                                    <b>
                                            <%=((BasketSingleItem)basketItem.getObject()).getSingle().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                    </b>
                                    <c:if test="<%=((BasketSingleItem)basketItem.getObject()).getSingle().getIsRedeemable()%>">
                                        <small style="font-style:italic; color:#8e8d8d;">
                                            <bean:message key="fullbasket.spend"/>
                                        </small>
                                    </c:if>
                                </td>
                                <td width="13%">

                                </td>
                                <td width="5%">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="62%">
                  <%--                                  <a id="edit_but<%=editButNum%>" href="javascript: void(0);" class="product_link"
                                                       onclick="gotoMiniBasketCustomizePage('singleId', 'SINGLEMENUITEM', '<%=((BasketSingleItem) basketItem.getObject()).getId()%>', '<%=((BasketSingleItem) basketItem.getObject()).getGroupId()%>','<%=((BasketSingleItem) basketItem.getObject()).getIdentifier()%>', 'edit_but<%=editButNum%>');
                                                               isInCustomizingMode = true;">
                                                    <img src="<%=context%>/images/edit.png" alt="Edit"/>
                                                </a>--%>
                                            </td>
                                            <td width="38%">
                                                <a href="javascript:void(0);" class="cart_quantity_delete"
                                                   rel="nofollow"
                                                   title="Delete"
                                                   onclick="removeFullBasketItem(<%=((BasketSingleItem) basketItem.getObject()).getIdentifier()%>, 'SINGLEMENUITEM');">
                                                    <img src="<%=context%>/images/close.png" alt="Close"/>
                                                </a>
                                                <input type="hidden" value="1" name="quantity_6_0_hidden"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <%
                        String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem) basketItem.getObject(), null, true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        exclusiveText = exclusiveText.replace("<context>", context);
                        String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem) basketItem.getObject(), "full", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem) basketItem.getObject(), "left", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                        String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem) basketItem.getObject(), "right", true, (Locale) session.getAttribute(Globals.LOCALE_KEY));
                    %>
                    <td height="30" class="padding-left">
                        <% if (fullText.length() > 0) { %>
                        <span class="red">Topping: </span>
                                <span class="gray">
                                      <%= fullText%>
                                  </span>
                        <% } %>
                        <%----------------%>
                        <br>
                        <% if (exclusiveText.length() > 0) { %>
                        <span class="red">Crust and Cooking Mode: </span>
                                  <span class="gray">
                                      <%= exclusiveText %>
                                  </span>
                        <% } %>
                        <%-----------------%>

                        <% if (leftText.length() > 0) { %>
                        <span class="red">Left: </span>
                                <span class="gray">
                                      <%= leftText%>
                                  </span>
                        <% } %>
                        <%----------------%>
                        <% if (rightText.length() > 0) { %>
                        <span class="red">Right: </span>
                                <span class="gray">
                                      <%= rightText%>
                                  </span>
                        <% } %>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <tr class="dark-orange">
                    <td height="30" width="87%" class="padding-left"><bean:message key="label.rightmenu.header.total.price"/></td>
                    <td width="13%">
                        <%=(CurrencyUtils.toMoney((new BasketSingleItemHelper()).getPrice((BasketSingleItem) basketItem.getObject())))%>
                    </td>
                </tr>
            </table>
        </div>
    </div>
<% } %>
<div class="Clear"></div>
<% } %>
<%
} else {
%>
<div id="empty"> 
<img src="<%=context%>/img/messages/info-icon-50px.png">
    <span class="info-message"><bean:message key="fullbasket.empty"/></span>
</div>
<%
    }
%>
</div>
        <%--====================== Coupoun --%>
<div class="coupoun"><span class="title"><bean:message key="label.copoun"/></span>
    <input class="textbox" id="coupon-code" type="text" value=""/>
    <a class="btn-second" href="javascript: void(0);" onclick="setOrderCouponCodeOnServer();"><bean:message key="button.redeem"/></a></div>
<div class="subtotal"></div>
  <div class="clear"></div>
<div style="margin-top: 10px;">
    <% if ( basket != null && basket.getBasketItemList() != null && basket.getBasketItemList().size() != 0) {%>
    <a class="btn-first FloatRight" href="javascript: void(0);" onclick="if (<%=suggestionList != null && suggestionList.size()>0%>) {showSuggestions();} else { gotoLoginPageFromCheckout('true', false);setOrderCouponCodeOnServer();}"><bean:message key="button.checkout.continue"/></a>
    <%}%>
    <a class="btn-second FloatRight" href="javascript:void(0);" onclick="setMenuType('menu'); getMenuList('menu');"><bean:message key="button.continueShopping"/></a>
</div>

<script>

    function setOrderCouponCodeOnServer() {
        var el = document.getElementById('coupon-code');
        if (el != null) {
            var couponCode = el.value;

            $.ajax({
                method: 'POST',
                url: '<%=context%>/checkout.do',
                data: {operation: 'setOrderCouponCode', couponCode : couponCode},
                success: function(res) {
                    $('.subtotal').html(res);
                },
                failure: function() {
                    alert('FAILURE');
                }
            });
        }
    }
</script>