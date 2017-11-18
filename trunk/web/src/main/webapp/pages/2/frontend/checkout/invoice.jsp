<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="java.util.ListIterator" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2/17/14
  Time: 7:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    //    Integer editButNum = 0;
    BigDecimal totalPrice = null;
    Double defaultServiceCostD = null;
    BigDecimal defaultServiceCostBD = null;
    String context = request.getContextPath();
    User user = (User) request.getSession().getAttribute(Constant.USER_TRANSIENT);
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if (basket != null) {
//        int totalOfItems = basket.getNumberOfItems();
        totalPrice = basket.calculateTotalPrice();
        defaultServiceCostD = basket.getDefaultServiceCost();
        defaultServiceCostBD = BigDecimal.valueOf(defaultServiceCostD.doubleValue());
        pageContext.setAttribute("basketItemsList", basket.getBasketItemList());
    }
//    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH, Constant.DATA_RESOURCES_FOODS_TINY_PATH);
//    Order order = (Order) request.getSession().getAttribute(Constant.ORDER);
//    String totalPriceOnSession = (String) request.getSession().getAttribute("totalPrice");
//    ========== Recommend
//    List<Suggestion> suggestionList = new ArrayList<Suggestion>();
//    Boolean reviewSuggestions = (Boolean)session.getAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION);
//    if (basket != null && reviewSuggestions == null || !reviewSuggestions){
//        suggestionList = CollectionsUtils.top(SuggestionsHelper.getSuggestionsForBasket(basket), 5);
//    }

    Double earnedDpDollarsAmount = (Double) session.getAttribute("earnedDpDollarsAmount");

    Boolean isDiscountUsed = (Boolean) request.getSession().getAttribute("isDiscountUsed");
    isDiscountUsed = isDiscountUsed == null ? false : isDiscountUsed;
    Double discount = (Double) request.getSession().getAttribute("discount");
    Double couponAmount = (Double) request.getSession().getAttribute("couponAmount");
    Double userDollarBalance = new Double(00.00d);
    if (user != null) {
        userDollarBalance = (user.getDpDollarBalance() - discount) < 0 ? new Double(00.00d) : (user.getDpDollarBalance() - discount);
    }
    Double totalPriceD = new Double(totalPrice.doubleValue());
    Double totalPriceAfterDPDollarAndCoupon = (totalPriceD - (discount + (couponAmount * totalPriceD / 100)));
    Double totalPriceAfterCoupon = (totalPriceD - (couponAmount * totalPriceD / 100));
    BigDecimal totalPriceAfterDPDollarAndCouponBD = BigDecimal.valueOf(totalPriceAfterDPDollarAndCoupon.doubleValue());
    BigDecimal totalPriceAfterCouponBD = BigDecimal.valueOf(totalPriceAfterCoupon.doubleValue());
%>
<%--<div class="subtotal">--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="47%"><bean:message key="label.fullBasket.Subtotal"/>:</td>
        <td width="15%"><%=totalPrice == null ? "$00.00" : CurrencyUtils.toMoney(totalPrice)%>
        </td>
        <td width="38%" rowspan="5" class="total"><bean:message key="label.rightmenu.header.total.price"/>
            <div class="total-price">
                <c:choose>
                    <c:when test="<%=isDiscountUsed%>">
                        <% Double total = new TaxHandler().getTotalPriceWithTax(totalPriceAfterDPDollarAndCouponBD.add(defaultServiceCostBD)).doubleValue(); %>
                        <%= CurrencyUtils.toMoney(total)%>
                    </c:when>
                    <c:otherwise>
                        <% Double total = new TaxHandler().getTotalPriceWithTax(totalPriceAfterCouponBD.add(defaultServiceCostBD)).doubleValue(); %>
                        <%= CurrencyUtils.toMoney(total)%>
                    </c:otherwise>
                </c:choose>
            </div>
        </td>

    </tr>
    <tr>
        <td>Coupon:</td>
        <td width="15%"><%=totalPrice == null ? "$00.00" : CurrencyUtils.toMoney(couponAmount * totalPrice.doubleValue() / 100)%>
        </td>
    </tr>

    <tr>
        <td width="47%"><bean:message key="label.fullBasket.ServiceCharge"/>:</td>
        <td width="15%"><%=totalPrice == null ? "$00.00" : CurrencyUtils.toMoney(defaultServiceCostD)%>
    </tr>


    <c:choose>
        <c:when test="<%=user != null && user.getDpDollarBalance() > 0%>">
            <tr>
                <td>
                    <input id="isDiscountUsed" name="isDiscountUsed" type="checkbox" checked
                           onclick="callDiscountOnTotalPrice()">
                    <bean:message key="invoice.mydp$"/>
                </td>
                <td>
                    <div style="position: relative;left: -5px;">-$<%=CurrencyUtils.roundTwoDecimals(discount)%>
                    </div>
                </td>
            </tr>
        </c:when>
        <c:otherwise>
            <tr>
                <td>
                    <input type="checkbox" readonly disabled>
                    <bean:message key="invoice.mydp$"/>
                </td>
                <td>
                    <div style="position: relative;left: -5px;">-$00.00</div>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>
    <%
        ListIterator taxIterator = InMemoryData.getTaxList().listIterator();
        for (Tax taxRule : InMemoryData.getTaxList()) {
    %>
    <tr>
        <td>
            <%=taxRule.getWebName()%>
        </td>
        <td>
            <%=CurrencyUtils.toMoney((new TaxHandler()).getStepTax(BigDecimal.valueOf((totalPrice.doubleValue() + defaultServiceCostD.doubleValue()) - discount - (couponAmount * (totalPrice.doubleValue()) / 100)), taxIterator.nextIndex()))%>
        </td>
    </tr>
    <%
            taxIterator.next();
        }
    %>
</table>
<div style="margin:18px 0px 5px 0px">
    <c:choose>
        <c:when test="<%=user != null%>">
                <span style="font-style:italic; color:#8e8d8d; font-size: 13;">
                    <span><bean:message key="fullbasket.balance1"
                                        arg0="<%=CurrencyUtils.roundTwoDecimals(userDollarBalance)%>"/></span>&nbsp;<bean:message
                        key="fullbasket.balance2" arg0="<%=CurrencyUtils.roundTwoDecimals(earnedDpDollarsAmount)%>"/>&nbsp;<bean:message
                        key="fullbasket.balance3"/> </span>
            </span>
        </c:when>
        <c:otherwise>
                <span style="font-style:italic; color:#8e8d8d; font-size: 13;">
                    <span> <bean:message key="fullbasket.balance4"
                                         arg0="<%=CurrencyUtils.roundTwoDecimals(earnedDpDollarsAmount)%>"/>&nbsp;<bean:message
                            key="fullbasket.balance5"/> </span>
                   <span>
                        <a href="javascript: void(0);"
                           onclick="login('<%=context%>checkout.do?operation=goToCheckoutPage');"
                           style="color:#0039a6; text-decoration: HighlightText"><bean:message
                                key="label.page.title.login"/>
                        </a>
                        &nbsp;|&nbsp;
                        <a href="<%=context%>/frontend.do?operation=goToRegistrationPage"
                           style="color:#0039a6; text-decoration: HighlightText"><bean:message
                                key="label.page.title.register"/>
                        </a>
                </span>
              </span>
        </c:otherwise>
    </c:choose>
</div>
<%--</div>--%>