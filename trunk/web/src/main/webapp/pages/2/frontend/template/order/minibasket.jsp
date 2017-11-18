<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/5/13
  Time: 6:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    String totalItems = "";
    String totalPrice = "";
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    String menuType = (String) request.getParameter("menuType");
    Basket basket = (Basket)request.getSession().getAttribute(Constant.BASKET);
      totalItems = (basket == null) ? (String) request.getSession().getAttribute("totalItems") : String.valueOf(basket.getNumberOfItems());
      totalPrice = (basket == null) ? (String) request.getSession().getAttribute("totalPrice") : String.valueOf(basket.calculateTotalPrice());
%>
<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>

<div id="footer-cart-block">
    <div id="basketList" class="footer_popup" style="display: none;"></div>
    <div class="footer-cart">
        <a href="javascript: void(0);" class="cart2">
            <strong><bean:message key="minibasket.label.viewCard"/> </strong>
             <%--<span id="item_num" class="basket-items-count">--%>
            <div class="Clear"></div>
            <span id="basketItemsCountDiv">
            <span  id="basketItemsCount" class="basket-items-count">
              <%--<%=((Basket) session.getAttribute(Constant.BASKET)).getNumberOfItems()%>--%>
              <%=(totalItems == null || totalItems.isEmpty()) ? "0" : totalItems%>
            </span></span><span>&nbsp;<bean:message key="label.page.basket.items"/></span>
        </a>
        <div class="total-price" >
            <bean:message key="label.rightmenu.header.total.price"/> <span id="totalPriceID"><%=totalPrice == null ? "$00.00" : CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(((Basket) request.getSession().getAttribute(Constant.BASKET)).calculateTotalPrice()))%></span>
            <%--Total Price: <span id="basketTotalPrice"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(((Basket) request.getSession().getAttribute(Constant.BASKET)).calculateTotalPrice()))%></span>--%>
        </div>

      <div id="navmenu">
            <ul id="navigationmenu" >
                <li>
                    <ul class="maingoto">
                        <li class="gotomenu"><a href="">
                            <c:if test="${enState}">
                                <img src="<%=context%>/images/ordermenu-en2.png" alt="<bean:message key="minibasket.label.orderMore"/>" />
                            </c:if>
                            <c:if test="${frState}">
                                <img src="<%=context%>/images/ordermenu-fr21.png" alt="<bean:message key="minibasket.label.orderMore"/>" />
                            </c:if>
                        </a>
                            <div class="gotosubmenu">
                                <ul>
                                    <%--<li class="border"><a href="javascript: void(0);" style="margin:3px;">Articles Populaire&lt;%&ndash;<bean:message key="label.page.title.populars"/>&ndash;%&gt;</a></li>--%>
                                    <li class="border"><a href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" style="margin:3px;"><bean:message key="label.page.title.specials"/> </a></li>
                                    <li class="border"><a href="javascript: void(0);" onclick="setMenuType('menu'); getMenuList('menu');" style="margin:3px;"><bean:message key="label.page.title.menu"/> </a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>

    <%--<a href="#" class="btn2 special"><img src="<%=context%>/images/special-btn.png" alt="Go To Special" /></a>--%>
        <% if (totalItems != null && !totalItems.equals("0")) { %>
        <%--<c:if test="<%=totalItems != null && !totalItems.equals(String.valueOf(0))%>">--%>
            <%--<a href="javascript: void(0);" onclick="goToCheckoutPage();" class="btn2">Checkout</a>--%>
            <%--<a href="<%=context%>/checkout.do?operation=goToCheckoutPage" class="footer-btn">Checkout</a>--%>
        <a href="javascript: void (0);" onclick="if(<%=user != null && user.getDpDollarBalance() > 0%>){if(isUserUncheckedDpDollar != false)setIsDiscountUsed(true);} else {setIsDiscountUsed(false);}goToCheckoutPage();" class="footer-btn"><bean:message key="button.checkout"/> </a>

    <%--</c:if>--%>
        <%
            }
        %>

      <%--
        <a href="#" class="btn2" onclick="setMenuType('popular');" >Popular</a>
        <%
            if (menuType == null || menuType.contentEquals("menu")){
        %>
        &lt;%&ndash;<a href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=special" onclick="setMenuType('special');" class="btn2">Special</a>&ndash;%&gt;
        <a href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" class="btn2">Special</a>

        <%
            }
        %>
        <%
            if (menuType != null && menuType.contentEquals("special")){
        %>
        &lt;%&ndash;<a href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu" onclick="setMenuType('special');" class="btn2">Menu</a>&ndash;%&gt;
        <a href="javascript: void(0);" onclick="setMenuType('menu'); getMenuList('menu');" class="btn2">Menu</a>

        <%
            }
        %>

        --%>
    </div>
</div>
<!-- Footer Card Div End Here -->
<script type="text/javascript">
    $(function() {
        $(".gotomenu").hover(
                function () {
                    $(".gotosubmenu").show();
                },
                function () {
                    $(".gotosubmenu").removeClass("active");
                    $(".gotosubmenu").hide();
                }
        );
    });



    function gotoMiniBasketCustomizePage(paramName, type, catId, id, groupId, identifier, itemElem) {
        isInCustomizingMode = true;
        if (type == 'COMBINED') {
            customizing = 1;
        } else if (type == 'SINGLEMENUITEM') {
            customizing = 2;
        } else if (type == 'CATEGORY') {
            customizing = 3;
        }
        var myForm = document.createElement('form');
        myForm.setAttribute('method', 'POST');
        myForm.setAttribute('action', '<%=context%>/frontend.do');
        document.body.appendChild(myForm);

        var hid_paramName = document.createElement('input');
        hid_paramName.setAttribute('name', paramName);
        hid_paramName.setAttribute('value', id);

        var hid_operation = document.createElement('input');
        hid_operation.setAttribute('type', 'hidden');
        hid_operation.setAttribute('name', 'operation');
        hid_operation.setAttribute('value', 'goToCustomizePage');

        var hid_identifier = document.createElement('input');
        hid_identifier.type = 'hidden';
        hid_identifier.setAttribute('type', 'hidden');
        hid_identifier.setAttribute('name', 'identifier');
        hid_identifier.setAttribute('value', identifier);

        var hid_type = document.createElement('input');
        hid_type.setAttribute('type', 'hidden');
        hid_type.setAttribute('name', 'type');
        hid_type.setAttribute('value', type);

        var hid_groupId = document.createElement('input');
        hid_groupId.setAttribute('type', 'hidden');
        hid_groupId.setAttribute('name', 'groupId');
        hid_groupId.setAttribute('value', groupId);

        var hid_catId = document.createElement('input');
        hid_catId.setAttribute('type', 'hidden');
        hid_catId.setAttribute('name', 'catId');
        hid_catId.setAttribute('value', catId);

        var hid_menuName = document.createElement('input');
        hid_menuName.setAttribute('type', 'hidden');
        hid_menuName.setAttribute('name', 'menuName');
        hid_menuName.setAttribute('value', '<%=session.getAttribute(Constant.MENU_NAME)%>');

        var hide_itemElem = document.createElement('input');
        hide_itemElem.setAttribute('type', 'hidden');
        hide_itemElem.setAttribute('name', 'itemToClick');
        hide_itemElem.setAttribute('value', itemElem);

        var hid_customizing = document.createElement('input');
        hid_customizing.setAttribute('type', 'hidden');
        hid_customizing.setAttribute('name', 'customizing');
        hid_customizing.setAttribute('value', customizing);

        var hid_customizedBasketItemId = document.createElement('input');
        hid_customizedBasketItemId.setAttribute('type', 'hidden');
        hid_customizedBasketItemId.setAttribute('name', 'customizedBasketItemId');
        hid_customizedBasketItemId.setAttribute('value', customizedBasketItemId);

        myForm.appendChild(hid_operation);
        myForm.appendChild(hid_catId);
        myForm.appendChild(hid_paramName);
        myForm.appendChild(hid_type);
        myForm.appendChild(hid_groupId);
        myForm.appendChild(hid_menuName);
        myForm.appendChild(hid_identifier);
        myForm.appendChild(hide_itemElem);
        myForm.appendChild(hid_customizing);
        myForm.appendChild(hid_customizedBasketItemId);

        myForm.submit();
    }

    function removeItemFromBasket(basketItemId, basketItemType) {
        $.ajax({
            type: 'POST',
            url: '<%=context%>/frontend.do',
            data: {operation: 'removeItemFromBasket', basketItemId: basketItemId, basketItemType: basketItemType},
            success: function (res) {
                if (res != "") {
//                $('#footer-cart-block').append('<div id="basketList" class="footer_popup" style="display: none;"></div>').before('<div class="footer-cart">');
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').html(res);
                    } else {
                        $('#basketList').addClass("footer_popup");
                        $('#basketList').html(res);
                    }

                    setCookie("homeAlert", "fromDelete", 1);
                    /* var path = getHomeUrl();
                     location.href = path;*/
                    var tp=document.getElementById('totalPriceID');
                    var tpHeader=document.getElementById('basketTotalPrice');
                    tp.innerHTML=document.getElementById('btPrice').innerHTML;
                    tpHeader.innerHTML=document.getElementById('btPrice').innerHTML;



                }  else if(res == null || res == "" || res == 'undefined'){
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').removeClass("footer_popup");
                    }
                    $('#basketList').html('<label style="">Your Basket is empty</label>');
                }
            },
            failure: function () {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
        <%--updateBasketItemCounts('<%=((Basket) session.getAttribute(Constant.BASKET)).getNumberOfItems()%>');--%>
        <%--updateBasketTotalPrice('<%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(((Basket) request.getSession().getAttribute(Constant.BASKET)).calculateTotalPrice()))%>');--%>


    }
    function onCustomizing() {
        $('#bottom-selected-block').remove();
        $('#add-to-card').remove();
    }
</script>
<script type="text/javascript">

    function reloadMiniBasket(totalItems){
        if(totalItems==1) totalItems='0';
        else
        totalItems=(totalItems.valueOf(totalItems)-1);
        var t=document.getElementById("basketItemsCountDiv");
        t.innerHTML=totalItems;
        var bItemCountHeader=document.getElementById("basketItemsCount");
        bItemCountHeader.innerHTML=totalItems;
        }


</script>