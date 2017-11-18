<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.MathContext" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Apr 4, 2012
  Time: 10:50:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


    <%
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
    String context = request.getContextPath();
    pageContext.setAttribute("basketItemsList", basket.getBasketItemList());
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH, Constant.DATA_RESOURCES_FOODS_TINY_PATH);
%>
<%--<bean:define id="totalOfItems"    value="<%=basket.getNumberOfItems()%>" />--%>
<%--<bean:define id="totalPrice"      value="<%=basket.calculateTotalPrice()%>" type="java.math.BigDecimal"/>--%>
<%--<bean:define id="context"         value="<%=request.getContextPath()%>" />--%>
<%--<bean:define id="basketItemsList" value="<%=basket.getBasketItemList()%>" />--%>

<div id="center_column_inner2">

<h5 id="cart_title">
    <bean:message key="label.shopping.cart.summry"/>
</h5>

<p style="margin-left:4%;">
    <bean:message key="message.itemNumbersInCart1"/>
    <span id="summary_products_quantity">
    <%=totalOfItems%> <bean:message key="message.itemNumbersInCart2"/>
    </span>
</p>

<div id="order-detail-content" class="table_block">
    <table id="cart_summary" class="std">
        <thead>
        <tr>
            <th class="cart_product first_item"><bean:message key="label.product"/></th>
            <th class="cart_description item"><bean:message key="label.description"/></th>
            <th class="cart_unit item"><bean:message key="label.price"/></th>
            <th class="cart_total last_item"/>
            <th class="cart_total last_item"/>
        </tr>
        </thead>
        <tbody id="cart_items">
        <c:if test='<%=basket.getBasketItemList().size() != 0%>'>
        <%--<c:if test="${totalOfItems}">--%>
            <c:forEach items="${pageScope.basketItemsList}" var="basketItem" >
                <!--<tr id="product_6_0" class=" cart_item">-->
                <tr class=" cart_item" id="basketItemElem${basketItem.identifier}">
                    <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketCombinedItem.class) %>'>
                        <bean:define id="basketCombinedItem" type="com.sefryek.doublepizza.model.BasketCombinedItem" name="basketItem" property="object"/>
                        <bean:define id="combinedMenuItem" type="com.sefryek.doublepizza.model.CombinedMenuItem" name="basketCombinedItem" property="combined"/>
                        <td class="cart_product">
                            <img    style="width: 100px; height:70px;"
                                    src="<%=context%><%=middlepath%><%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem")).getImageURl()%>"
                                 alt="<%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem")).getName(
                                     (Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                        </td>
                        <td class="cart_description">
                            <h5><a class="product_link">
                                <%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem"))
                                        .getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%></a>
                            </h5>

                            <div style="display: block; margin-top:-38px; margin-left:-5px;">
                                <c:forEach items='<%=((BasketCombinedItem)pageContext
                                     .getAttribute("basketCombinedItem")).getBasketSingleItemList()%>' begin="0" var="aSingle">
                                    <div style="margin-left:4%;border-style: solid; border-color:#cccccc; border-width:2px;border-bottom:none; border-left:none; border-right:none;">
                                        <a class="combined_description_title">
                                            <%=((BasketSingleItem)pageContext.getAttribute("aSingle")).
                                                    getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                            <span class="checkout_detail_price">
                                                <%=(CurrencyUtils.toMoney((new BasketCombinedItemHelper()).getInvoicePrice((BasketCombinedItem)pageContext.getAttribute("basketCombinedItem"), (BasketSingleItem)pageContext.getAttribute("aSingle"))))%>
                                            </span>
                                            <br/>
                                        </a>

                                        <a class="combined_description">
                                            <%
                                                String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("aSingle") ,null, true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                                exclusiveText = exclusiveText.replace("<context>", context);
                                                String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("aSingle") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                                String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("aSingle") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                                String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("aSingle") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                            %>
                                            <c:if test="<%= exclusiveText.length() > 0 %>">
                                                <%= exclusiveText %>
                                            </c:if>
                                            <c:if test="<%= fullText.length() > 0 %>">
                                                <span class="checkout_topping_half"><bean:message key="label.full"/>: </span><%= fullText%>
                                            </c:if>
                                            <c:if test="<%= leftText.length() > 0 %>">
                                                <span class="checkout_topping_half"><bean:message key="label.left"/>: </span> <%= leftText%>
                                            </c:if>
                                            <c:if test="<%= rightText.length() > 0 %>">
                                                <span class="checkout_topping_half"><bean:message key="label.right"/>: </span> <%= rightText%>
                                            </c:if>
                                        </a>

                                    </div>



                                </c:forEach>

                            </div>


                        </td>
                        <td class="cart_price">
                            <span class="price" >
                                <%=CurrencyUtils.toMoney(((new BasketCombinedItemHelper()).getPrice(
                                        (BasketCombinedItem)pageContext.getAttribute("basketCombinedItem"))))%>
                            </span>
                        </td>
                        <td class="cart_quantity">
                            <a href="javascript:void(0);" class="cart_quantity_delete" rel="nofollow"
                               title="Delete"  onclick="removeItemUpdateCheckout('${basketCombinedItem.identifier}', 'COMBINED');">
                                <img src="<%=context%>/img/delete.gif" alt="Delete" class="icon" />
                            </a>
                            <input type="hidden" value="1" name="quantity_6_0_hidden"/>
                        </td>
                    </c:if>

                    <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketSingleItem.class) %>'>
                        <bean:define id="basketSingleItem" type="com.sefryek.doublepizza.model.BasketSingleItem" name="basketItem" property="object"/>
                        <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>

                        <td class="cart_product">
                            <img    style="width: 100px; height:70px;"
                                    src="<%=context%><%=middlepath%><%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getImageURL()%>"
                                    alt="<%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                        </td>
                        <td class="cart_description">
                            <h5><a class="product_link">
                                <%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                <br/>
                                <%
                                    String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,null, true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                    exclusiveText = exclusiveText.replace("<context>", context);
                                    String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                    String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                    String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                %>

                                <c:if test="<%= exclusiveText.length() > 0 %>">
                                    <%= exclusiveText %>
                                </c:if>
                                <c:if test="<%= fullText.length() > 0 %>">
                                    <span class="checkout_topping_half"><bean:message key="label.full"/>: </span><%= fullText%>
                                </c:if>
                                <c:if test="<%= leftText.length() > 0 %>">
                                    <span class="checkout_topping_half"><bean:message key="label.left"/>: </span> <%= leftText%>
                                </c:if>
                                <c:if test="<%= rightText.length() > 0 %>">
                                    <span class="checkout_topping_half"><bean:message key="label.right"/>: </span> <%= rightText%>
                                </c:if>
                            </a></h5>
                        </td>
                        <td class="cart_price">
                            <span class="price">
                                <%=(CurrencyUtils.toMoney((new BasketSingleItemHelper()).getPrice((BasketSingleItem)pageContext.getAttribute("basketSingleItem"))))%>
                            </span>
                        </td>
                        <td class="cart_quantity">
                            <a href="javascript:void(0);" class="cart_quantity_delete" rel="nofollow"
                               title="Delete"   onclick="removeItemUpdateCheckout('${basketSingleItem.identifier}', 'SINGLEMENUITEM');">
                                <img src="<%=context%>/img/delete.gif"
                                     alt="Delete" class="icon"/></a>
                            <input type="hidden" value="1" name="quantity_6_0_hidden"/>
                        </td>
                    </c:if>
                </tr>


            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <div class="cart_price_section">
        <div id="checkout_price_info" >
            <label id="price_labels">
                <bean:message key="label.subtotal"/>
            </label>
            <span id="checkout_prices">
                <%=CurrencyUtils.toMoney(totalPrice)%>
            </span>
        </div>
        <br class="clear_float"  />
        <%pageContext.setAttribute("taxIndex", new Integer(0));%>
        <c:forEach items='<%=InMemoryData.getTaxList()%>' begin="0" var="taxRule">

            <div id="checkout_price_info" >
            <label id="price_labels" style="text-transform:capitalize;">
              <%=((Tax)pageContext.getAttribute("taxRule")).getWebName()%>:
            </label>
            <span id="checkout_prices">
                <%=CurrencyUtils.toMoney((new TaxHandler()).getStepTax(totalPrice, (Integer)pageContext.getAttribute("taxIndex")))%>
            </span>
        </div>
            <br class="clear_float"  />
            <%pageContext.setAttribute("taxIndex", ((Integer)pageContext.getAttribute("taxIndex"))+ 1);%>
        </c:forEach>
        <hr/>
        <div id="checkout_price_info" >
            <label id="price_labels">
                <bean:message key="label.total.price.with.tax"/>
            </label>
            <span id="checkout_prices">
                <%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%>
            </span>
        </div>
    </div>
    <br class="clear_float"  />

</div>
<div id="cart_voucher" class="table_block">
    <div style="border:2px solid #eee;
    padding:10px;" id="voucher">
        <fieldset>
            <h2 style="display:inline-block;"><bean:message key="label.copoun"/></h2>

            <p style="display:inline-block; vertical-align:middle;margin-top:8px; height:17px;" class="text">
                <label for="discount_name"><bean:message key="label.code"/></label>
                <input type="text" id="discount_name" name="discount_name" value="" maxlength="10" size="10"/>
            </p>
        </fieldset>
    </div>
</div>

<div id="HOOK_SHOPPING_CART"></div>

<p class="cart_navigation">
    <a href="javascript: void(0);" class="exclusive" title="Next" onclick="gotoLoginPage('true', false);setOrderCouponCodeOnServer();">
        <bean:message key="button.next"/> &raquo;
    </a>
    <a href="javascript:void(0);" class="button_large" title="Continue shopping" onclick="continueShopping();">
        &laquo; <bean:message key="button.continueShopping"/>
    </a>
</p>

</div>