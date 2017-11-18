<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sefryek.doublepizza.service.implementation.OrderServiceImpl" %>
<%@ page import="com.sefryek.doublepizza.service.IOrderService" %>
<%
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    String context = request.getContextPath();
    String totalPriceOnSession = (String) request.getSession().getAttribute("totalPrice");
    String temp=totalPriceOnSession.replace("$","");
    BigDecimal totalPrice=new BigDecimal(temp);
    Order.DeliveryType deliveryType = (Order.DeliveryType) request.getSession().getAttribute(Constant.LAST_ORDER_DELIVERY_TYPE);
    Store store = null;
    if (deliveryType == Order.DeliveryType.PICKUP) {
        String storeNo = (String) request.getSession().getAttribute(Constant.LAST_ORDER_STORE);
        store = InMemoryData.findStoreById(storeNo);
    }
    String orderHistoriesSize="0";
    if (user!=null) {
        orderHistoriesSize= request.getSession().getAttribute("orderHistoriesSize").toString();
    }

    Boolean isNew=true;
    if (Integer.parseInt(orderHistoriesSize) >= 1) {
        isNew = false;
    }
%>

<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>

<%--<div id="MiddleBlock" class="nav-line">--%>
<h5  class="cusfood" style="padding: 15px;"><bean:message key="message.successfull.ckeckout"/></h5>
<div class="LeftMiddleWrapper">
            <!-- Center -->
            <div id="center_column" class="center_column">
                <div id="center_column_inner2">

                    <img src="<%=context%>/images/payment-success.png"  style="float:left;"><br>
                    <div  id="success-message" style="font-size: 22px;margin-top: 20px">
                        <c:if test="<%= deliveryType == Order.DeliveryType.DELIVERY %>">
                            <c:if test="<%= isNew || (totalPrice.compareTo(BigDecimal.valueOf(50))==1)%>">
                                <bean:message key="message.info.successfull.checkout"/><bean:message key="message.confirmation.newUser"/>
                            </c:if>

                            <c:if test="<%= !isNew && (totalPrice.compareTo(BigDecimal.valueOf(50))==-1)%>">
                                <bean:message key="message.info.successfull.checkout"/><bean:message key="message.confirmation.oldUser"/>
                            </c:if>

                        </c:if>
                        <c:if test="<%= deliveryType == Order.DeliveryType.PICKUP && store != null %>">
                            <bean:message key="message.info.successfull.checkout.pickup" arg0="<%= store.getName() %>"/>
                        </c:if>
                    </div>
                     <br class="clear">
                    <ul class="footer_links">
                        <li>
                            <a href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu">
                            <c:if test="${enState}">
                                <img src="<%=context%>/images/ordermenu.png" alt="<bean:message key="minibasket.label.orderMore"/>" />
                            </c:if>
                            <c:if test="${frState}">
                                <img src="<%=context%>/images/ordermenu-fr.png" alt="<bean:message key="minibasket.label.orderMore"/>" />
                            </c:if>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
    </div>
<%--</div>--%>
