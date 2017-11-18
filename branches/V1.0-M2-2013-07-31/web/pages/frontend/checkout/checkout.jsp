<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketSingleItemHelper" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.SuggestionsHelper" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sefryek.common.util.CollectionsUtils" %>
<%@ page import="java.util.ArrayList" %>

<%--
  Created by IntelliJ IDEA.
  User: Mona Gholamian
  Date: Mar 27, 2012
  Time: 11:24:22 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer editButNum = 0;
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
    pageContext.setAttribute("basketItemsList", basket.getBasketItemList());
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH, Constant.DATA_RESOURCES_FOODS_TINY_PATH);
%>

<div id="columns2">
<!-- Center -->
<div id="center_column" class="center_column">

<div id="center_column_inner2">
<h5 id="cart_title">
    <bean:message key="label.shopping.cart.summry"/>
</h5>

<p style="margin-left:4%;"><bean:message key="message.itemNumbersInCart1"/>
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
        <c:forEach items="${pageScope.basketItemsList}" begin="0" var="basketItem" varStatus="itemIndex">
            <!--<tr id="product_6_0" class=" cart_item">-->
            <tr class=" cart_item" id="basketItemElem${basketItem.identifier}">
                <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketCombinedItem.class) %>'>
                    <bean:define id="basketCombinedItem" type="com.sefryek.doublepizza.model.BasketCombinedItem" name="basketItem" property="object"/>
                    <bean:define id="combinedMenuItem" type="com.sefryek.doublepizza.model.CombinedMenuItem" name="basketCombinedItem" property="combined"/>
                    <td class="cart_product">
                        <a id="image_link<%=editButNum%>" href="javascript: void(0);" class="product_link" onclick="gotoCustomizePage('combinedId', 'COMBINED', '${basketCombinedItem.productNoRef}','${basketCombinedItem.groupIdRef}', 'edit_but<%=editButNum%>');">
                            <img style="width: 100px; height:70px;" src="<%=context%><%=middlepath%><%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem")).getImageURl()%>" alt="<%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                        </a>

                    </td>
                    <td class="cart_description">
                        <h5><a id="edit_but<%=editButNum%>" href="javascript: void(0);" class="product_link" onclick="
                                gotoCustomizePage('combinedId', 'COMBINED', '${basketCombinedItem.productNoRef}', '${basketCombinedItem.groupIdRef}', 'edit_but<%=editButNum++%>');
                                ">
                            <%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem"))
                                    .getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                        </a>
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
                           title="Delete"  onclick="removeItemUpdateCheckout(${basketCombinedItem.identifier}, 'COMBINED');" >
                            <img src="<%=context%>/img/delete.gif" alt="Delete" class="icon" />
                        </a>
                        <input type="hidden" value="1" name="quantity_6_0_hidden" />
                    </td>
                </c:if>

                <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketSingleItem.class) %>'>
                    <bean:define id="basketSingleItem" type="com.sefryek.doublepizza.model.BasketSingleItem" name="basketItem" property="object"/>
                    <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>
                    <td class="cart_product">

                        <a id="image_link<%=editButNum%>" href="javascript: void(0);" class="product_link" onclick="gotoCustomizePage('singleId', 'SINGLEMENUITEM', '${basketSingleItem.id}', '${basketSingleItem.groupId}', 'edit_but<%=editButNum%>');">
                            <img style="width:100px; height:70px;" src="<%=context%><%=middlepath%><%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getImageURL()%>" alt="<%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                        </a>
                    </td>
                    <%
                        Category parentCategory = InMemoryData.getSingleParentCategory(menuSingleItem);
                        Category parentSubCategory = null;
                        if (parentCategory == null){
                            parentSubCategory = InMemoryData.getSingleParentSubCategory(menuSingleItem);
                            parentCategory = InMemoryData.getSubCategoryParentCategory(parentSubCategory);
                        }
                    %>
                    <td class="cart_description">
                        <h5>
                            <a id="edit_but<%=editButNum%>" href="javascript: void(0);" class="product_link" onclick="
                                   gotoCustomizePage('singleId', 'SINGLEMENUITEM', '${basketSingleItem.id}', '${basketSingleItem.groupId}', 'edit_but<%=editButNum++%>');
                                ">
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
                                    <%= exclusiveText%>
                                </c:if>
                            </a>
                        </h5>
                        <div class="single_description">
                            <c:if test="<%= fullText.length() > 0 %>">
                                <span class="checkout_topping_half"><bean:message key="label.full"/>: </span> <%= fullText%>
                            </c:if>
                            <c:if test="<%= leftText.length() > 0 %>">
                                <span class="checkout_topping_half"><bean:message key="label.left"/>: </span> <%= leftText%>
                            </c:if>
                            <c:if test="<%= rightText.length() > 0 %>">
                                <span class="checkout_topping_half"><bean:message key="label.right"/>: </span> <%= rightText%>
                            </c:if>
                        </div>
                    </td>
                    <td class="cart_price">
                            <span class="price">
                                <%=(CurrencyUtils.toMoney((new BasketSingleItemHelper()).getPrice((BasketSingleItem)pageContext.getAttribute("basketSingleItem"))))%>
                            </span>
                    </td>
                    <td class="cart_quantity">
                        <a href="javascript:void(0);" class="cart_quantity_delete" rel="nofollow"
                           title="Delete"   onclick="removeItemUpdateCheckout(${basketSingleItem.identifier}, 'SINGLEMENUITEM');">
                            <img src="<%=context%>/img/delete.gif" alt="Delete" class="icon"/>
                        </a>
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
                <input type="text" id="discount_name" name="discount_name" value="" maxlength="10" size="10" style="width:210px;"/>
                <button href="javascript: void(0);" class="exclusive" style="display:inline; position:relative;top:-4px; padding-left: 15px; padding-right: 15px;"><bean:message key="button.redeem"/></button>
            </p>
        </fieldset>
    </div>
</div>

<div id="HOOK_SHOPPING_CART"></div>

<p class="cart_navigation">
    <a href="javascript: void(0);" class="exclusive" title="Next" onclick="if ($('#suggestionitems tr').length == 0) { gotoLoginPage('true', false);setOrderCouponCodeOnServer(); } else {showSuggestions();}">
        <bean:message key="button.next"/> &raquo;
    </a>

    <a href="javascript:void(0);" class="button_large" title="Continue shopping" onclick="continueShopping();">
        &laquo; <bean:message key="button.continueShopping"/>
    </a>
</p>

</div>
</div>
<!-- Right -->
<div id="right_column" class="column carousel_on">
    <div id="cart_block" class="block exclusive">
        <h4>
            <bean:message key="lable.cart"/>
            <span id="block_cart_expand" class="hidden">&nbsp;</span>
            <span id="block_cart_collapse">&nbsp;</span>
        </h4>

        <div class="block_content">
            <!-- block list of products -->
            <div id="cart_block_list" class="expanded">
                <div class="cart-prices">

                    <div class="cart-prices-block">
                        <span><bean:message key="label.item.no"/></span>
                        <span id="cart_block_shipping_cost"
                              class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                    </div>

                    <div class="cart-prices-block">
                        <span><bean:message key="lable.total.price"/> </span>
                        <span id="cart_block_total" class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%></span>
                    </div>
                </div>
                <p id="cart-price-precisions">
                    <bean:message key="message.price.with.tax"/>
                </p>

                <p id="cart-buttons">
                    <!--TODO: show suggestions here too-->
                    <a href="javascript: void(0);" class="button" title="Cart" onclick="gotoLoginPage('true', false);setOrderCouponCodeOnServer();"><bean:message key="button.checkout"/></a>

                </p>
            </div>
        </div>
    </div>
    <!-- /MODULE Block cart -->
</div>
<br class="clear"/>
</div>
<%
    List<Suggestion> suggestionList = new ArrayList<Suggestion>();
    Boolean reviewSuggestions = (Boolean)session.getAttribute(Constant.IN_SESSION_REVIEW_SUGGESTION);
    if (reviewSuggestions == null || !reviewSuggestions){
        suggestionList = CollectionsUtils.top(SuggestionsHelper.getSuggestionsForBasket(basket), 5);
    }
%>
<div id="suggestions" style="margin-bottom:10px; display:none;">
    <p style="margin-left:0; font: 17px/27px 'Arial',Arial,Helvetica,sans-serif;color: #948686; text-align: justify; margin-bottom:20px; line-height:30px;"><bean:message key="message.suggestions.guide"/></p>
    <table id="suggestionTable" class="std" style="margin-bottom:10px;">
    <thead>
    <tr>
        <th class="cart_product first_item" style="width:25%;"><bean:message key="label.product"/></th>
        <th class="cart_description item" style="width:40%;"><bean:message key="label.description"/></th>
        <th class="cart_unit item" style="width:15%;"><bean:message key="label.price"/></th>
        <th class="cart_total last_item" style="width:15%;"><bean:message key="lable.quantity"/></th>
    </tr>
    </thead>
    <tbody id="suggestionitems">
        <c:forEach items="<%=suggestionList%>"  begin="0" var="suggestionItem" varStatus="itemIndex">
        <bean:define id="suggestion" name="suggestionItem" type="com.sefryek.doublepizza.model.Suggestion"/>
            <c:if test="<%= suggestion.getMenuSingleItem() != null %>">

                <!--<tr id="product_6_0" class=" cart_item">-->
                <tr class="suggestionItem" id="suggestionItemElem${suggestion.modifierId}">
                        <td class="cart_product">
                            <a href="javascript: void(0);" class="product_link">
                                <img width="100px" height="70px" src="<%=context%><%=middlepath%><%=suggestion.getMenuSingleItem().getImageURL()%>" alt="<%=suggestion.getMenuSingleItem().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                            </a>
                        </td>
                        <td class="cart_description">
                            <h5>
                                <a href="javascript: void(0);" class="product_link">
                                    <%=suggestion.getMenuSingleItem().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                </a>
                            </h5>
                        </td>
                        <td class="cart_price">
                            <span class="price">
                                <%=(CurrencyUtils.toMoney(suggestion.getMenuSingleItem().getPrice()))%>
                            </span>
                        </td>
                        <td class="cart_quantity">
                            <div id="cart_quantity_button">
                                <a title="Add" onclick="changeQuantity(this, 1)" id="cart_quantity_up" class="cart_quantity_up" rel="nofollow"><img width="14" height="9" alt="Add" src="<%=context%>/img/quantity_up.png"></a>
                                <input type="text" name="quantity_input" value="0" size="2" class="cart_quantity_input text">
                                <a title="Subtract" onclick="changeQuantity(this, -1)" id="cart_quantity_down" rel="nofollow" class="cart_quantity_down" style="opacity: 1;"><img width="14" height="9" alt="Subtract" src="<%=context%>/img/quantity_down.png"></a>
                            </div>
                            <input type="hidden" value="<%=suggestion.getModifierId()%>" name="quantity"/>
                        </td>
                </tr>
                

            </c:if>
        </c:forEach>
    </tbody>
</table>
    <div style="display:block; width:60%; max-width:80%; float:right;">
        <input style="display:inline-block; width:184px; float:left;" name="addSuggestionToCart" id="addSuggestionToCart" value="<bean:message key="button.items.add.to.basket"/>" class="exclusive" type="button" onclick="addSuggestions();">
        <input style="display:inline-block; width:184px; float:right;" name="noThanx" id="noThanx" value="<bean:message key="label.buttun.nothanx"/> &raquo;" class="exclusive" type="button" onclick="        gotoLoginPage('true', true);
        setOrderCouponCodeOnServer();">
    </div>

</div>
