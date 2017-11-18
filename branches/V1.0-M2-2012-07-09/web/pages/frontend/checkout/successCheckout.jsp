<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.Order" %>
<%@ page import="com.sefryek.doublepizza.model.Store" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String context= request.getContextPath();
    Basket basket= (Basket)request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems=basket.getNumberOfItems();
    BigDecimal totalPrice= basket.calculateTotalPrice();
    Order.DeliveryType deliveryType = (Order.DeliveryType) request.getSession().getAttribute(Constant.LAST_ORDER_DELIVERY_TYPE);
    Store store = null;
    if (deliveryType == Order.DeliveryType.PICKUP){
        String storeNo = (String)request.getSession().getAttribute(Constant.LAST_ORDER_STORE);
        store = InMemoryData.findStoreById(storeNo);
    }
%>

<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="center_column">
        <div id="center_column_inner2">
            <h5><bean:message key="message.successfull.ckeckout"/> </h5>

            <img src="<%=context%>/img/success.png" align="center" style="float:left;"><br>
            <h4 id="success-message">
                <c:if test="<%= deliveryType == Order.DeliveryType.DELIVERY %>">
                    <bean:message key="message.info.successfull.checkout"/>
                </c:if>
                <c:if test="<%= deliveryType == Order.DeliveryType.PICKUP && store != null %>">
                    <bean:message key="message.info.successfull.checkout.pickup" arg0="<%= store.getName() %>"/>
                </c:if>
            </h4>

            <ul class="footer_links">
                <li><a href="<%=context%>/frontend.do" title="Home"><img src="<%=context%>/img/icon/home.png" alt="Home" class="icon"></a><a href="<%=context%>/frontend.do" title="Home"><bean:message key="label.page.menu.home" /></a></li>

            </ul>

        </div>
    </div>
    <!-- Right -->
    <div id="right_column" class="column carousel_on">
        <!-- MODULE Block cart -->
        <div id="cart_block" class="block exclusive">
            <h4>
                <bean:message key="lable.cart"/>
                <span id="block_cart_expand" class="hidden">&nbsp;</span>
                <span id="block_cart_collapse">&nbsp;</span>
            </h4>
            <div class="block_content2">
                <!-- block summary -->
                <%--<div id="cart_block_summary" class="collapsed">--%>

                <%--</div>--%>
                <!-- block list of products -->
                <div id="cart_block_list" class="expanded">

                    <div class="cart-prices">
                        <div class="cart-prices-block">
                            <span><bean:message key="label.item.no"/></span>
                            <span id="cart_block_shipping_cost" class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                        </div>
                        <div class="cart-prices-block">

                            <span><bean:message key="lable.total.price"/> </span>
                            <span id="cart_block_total" class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(totalPrice)%></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br class="clear">
    </div>
</div>
