<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
%>

<div id="columns2">
<!-- Center -->
<div id="center_column" class="center_column">
<div id="center_column_inner2">

<h5><bean:message key="label.page.title.checkout"/></h5>

<form action="<%=context%>/finalcheckout.do?operation=checkoutBasket" method="post" id="paymentInfoForm" name="paymentInfoForm" class="std">

<fieldset class="account_creation">
<h3><bean:message key="lable.payment.info"/></h3>

<p class="radio required">
    <span style="margin-bottom:2%;text-align:left;width: 100%;color: #948686;font-size: 17px;" ><bean:message key="lable.choose.your.payment.type"/></span>
    <br class="clear_float"  />

    <!--todo:add them to resource bunndles: mona-->
    <input name="paymentType" id="paymentType" value="Cash" type="radio" checked>
    <label class="top">Cash</label>
    <input name="paymentType" id="paymentType" value="Debit" type="radio">
    <label class="top">Debit</label>
    <input name="paymentType" id="paymentType" value="Visa" type="radio">
    <label class="top">Visa Card</label>
    <input name="paymentType" id="paymentType" value="MC" type="radio">
    <label class="top">Master Card</label>

</p>
    <p class="submit" style="float:left;width:50%; padding-left:0; ">
    <input style="display:inline-block; width: 200px;" name="goHome" id="goHome" value="&laquo; <bean:message key="button.continueShopping"/>"
           class="exclusive_large" type="button" onclick="continueShopping();">
    <input style="display:inline-block;" name="submitAccount" id="submitAccount"
           value="<bean:message key="button.checkout"/>" class="exclusive" type="submit">

</p>
</fieldset>
</form>
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

        <div class="block_content2">
            <div id="cart_block_list" class="expanded">

                <div class="cart-prices">

                    <div class="cart-prices-block">
                        <span><bean:message key="label.item.no"/></span>
                        <span id="cart_block_shipping_cost"
                              class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                    </div>

                    <div class="cart-prices-block">
                        <span><bean:message key="lable.total.price"/> </span>
                        <span id="cart_block_total"
                              class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%></span>
                    </div>
                </div>

                <p id="cart-price-precisions">
                    <bean:message key="message.price.with.tax"/>
                </p>

            </div>
        </div>
    </div>
    <!-- /MODULE Block cart -->
</div>
<br class="clear">
</div>
