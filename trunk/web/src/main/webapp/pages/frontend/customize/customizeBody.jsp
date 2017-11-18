<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 25, 2012
  Time: 2:46:03 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
%>
<div id="columns_mir">
<br class="clear"/>
    <%--============================= start of Order panel--%>
<div id="right_column" class="column carousel_on">
    <div id="cart_block" class="block exclusive">


        <h4>
            <bean:message key="label.rightmenu.header.my.order" />
        </h4>

        <div class="block_content">

            <div class="full_basket">
                <img src="<%=context%>/img/food_icon/img-01.png" alt="" />
                <img src="<%=context%>/img/food_icon/img-05.png" alt="" />
                <img style="margin-top:15px;" src="<%=context%>/img/food_icon/img-02.png" alt="" />
                <img src="<%=context%>/img/food_icon/img-03.png" alt="" />
                <img src="<%=context%>/img/food_icon/img-04.png" alt="" />
                <img src="<%=context%>/img/food_icon/img-07.png" alt="" />
                <img src="<%=context%>/img/food_icon/img-08.png" alt="" />
            </div>
            <div id="basket_order">

                <ul>
                    <li id="shopping_cart">
                        <div>
                            <p>
                        <span class="cart_quantity_big">
                            <span id="items-no" class="basket-items-count">0</span> <bean:message key="label.basket.items.in.basket"/>
                        </span>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="cart_total_price">
                <label>
                    <bean:message key="label.rightmenu.header.total.price" />
                </label>
                <span id="basketTotalPrice">$0.00</span>

            </div>
            <a href="javascript: void(0);" onclick="goToOnlineOrder();" class="back-to-menu2">
                <span>&laquo; <bean:message key="back.to.menu"/></span>
            </a>
            <c:if test="${!empty basket}">
                    <c:if test="<%=(!((Basket)(request.getSession().getAttribute(Constant.BASKET))).isEmpty())%>">
                        <a class="back-to-menu2" id="your_account2" style="float:right;" href="<c:url value="/checkout.do" context="<%=context%>">
                            <c:param name="operation" value="goToCheckoutPage"/>
                            </c:url>">
                            <span><bean:message key="label.page.title.checkout"/> &raquo;</span>
                        </a>
                    </c:if>
                </c:if>
            <c:if test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED)) %>">
                <div class="add-combined" id="bottom-selected-block">
                <h2><bean:message key="message.bottom.want.to.add"/></h2>

                        <h2 id="bottom-selected-combinedItem">
                            <%=((CombinedMenuItem)session.getAttribute(Constant.LAST_COMBINED_ITEM)).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                        </h2>

                        <a class="add_to_cart" style="width:197px;" onclick="document.getElementById('buttonAddCombinedToBasket').onclick();"><bean:message key='button.items.add.to.basket'/></a>
            </div>
            </c:if>
            <br class="clear"  />
            <div class="set_acc">                
                <div id="basketList" class="va-wrapper">
                </div>
            </div>
        </div>
    </div>
</div>
    <%--============================= end of Order panel--%>

    <div id="featured-products_block_center">
    </div>
</div>
</div>
