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
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if(basket != null){
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
    }
%>
<%--<div id="MiddleBlock" class="nav-line">--%>
<h5  class="cusfood" style="padding: 15px;">
    <bean:message key="message.error.ckeckout"/></h5>
    <div class="LeftMiddleWrapper">
            <!-- Center -->
            <div id="center_column" class="center_column">
                <div id="center_column_inner2">


                    <img src="<%=context%>/images/payment-failed.png"  style="float:left;"><br>
                    <div class="box-failed" id="success-message">
                        <bean:message key="message.info.failed.checkout"/>
                    </div>
                    <br class="clear">

                </div>
            </div>
        <%--</div>--%>
    </div>
<%--</div>--%>
