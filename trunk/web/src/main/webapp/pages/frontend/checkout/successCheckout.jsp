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

<%--<div id="MiddleBlock" class="nav-line">--%>
    <div class="LeftMiddleWrapper">

    <div id="center_column" class="center_column">
        <div id="center_column_inner2">
            <h5><bean:message key="message.successfull.ckeckout"/> </h5>

            <img src="<%=context%>/img/success.png" align="center" style="float:left;"><br>
            <div class="box-success" id="success-message">
                <c:if test="<%= deliveryType == Order.DeliveryType.DELIVERY %>">
                    <bean:message key="message.info.successfull.checkout"/>
                </c:if>
                <c:if test="<%= deliveryType == Order.DeliveryType.PICKUP && store != null %>">
                    <bean:message key="message.info.successfull.checkout.pickup" arg0="<%= store.getName() %>"/>
                </c:if>
            </div>

            <ul class="footer_links">
                <li><a href="<%=context%>/frontend.do" title="Home"><img src="<%=context%>/img/icon/home.png" alt="Home" class="icon"></a><a href="<%=context%>/frontend.do" title="Home"><bean:message key="label.page.menu.home" /></a></li>

            </ul>

        </div>
    </div>

   </div>
<%--</div>--%>
