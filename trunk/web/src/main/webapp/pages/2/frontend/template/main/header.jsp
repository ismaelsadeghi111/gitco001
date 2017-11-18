<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.common.util.SecurityUtils" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: Mostafa.Jamshid
  Date: 10/1/13
  Time: 12:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    Basket basket = (Basket)session.getAttribute(Constant.BASKET);
    BigDecimal totalPrice = null;
    BigDecimal defaultServiceCostBD = null;
    boolean isItemInBasket = false;
    String context = request.getContextPath();

    Integer basketItemsCount = 0;
    if (basket != null) {
        totalPrice = basket.calculateTotalPrice();
        defaultServiceCostBD = BigDecimal.valueOf(basket.getDefaultServiceCost().doubleValue());
        if (totalPrice == null ){
            totalPrice = BigDecimal.ZERO;
        }
        if (defaultServiceCostBD == null ){
            defaultServiceCostBD = BigDecimal.ZERO;
        }
        isItemInBasket = basket.getNumberOfItems() > 0;
    }
    if (isItemInBasket){
        totalPrice = basket.calculateTotalPrice();
        basketItemsCount = basket.getNumberOfItems();
    }
// ---- from customize-header.jsp

    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    String userName="";
    String userFamily="";
    if(user!=null){
        userName = StringUtil.extractIdFormEmailAddress(user.getFirstName());
        userFamily = StringUtil.extractIdFormEmailAddress(user.getLastName());
    }
    Boolean isDiscountUsed = (Boolean) request.getSession().getAttribute("isDiscountUsed");
    isDiscountUsed = isDiscountUsed == null ? false : isDiscountUsed;
    Double discount = (Double)request.getSession().getAttribute("discount");
    discount = discount == null ? new Double(00.00d) : discount;
%>

<div id="header2">
    <div id="bar">
        <div class="container">
            <ul id="social">
                <li class="instagram"><a target="_blank" href="http://www.instagram.com/doublepizzaca"></a></li>
                <li class="facebook"><a target="_blank" href="http://www.facebook.com/doublepizzaca" ></a></li>
                <li class="youtube"><a   target="_blank" href="http://www.youtube.com/user/DoublePizzaPepe"></a></li>
            </ul>
            <span ><img style="margin-left: 30px;" src="<%=context%>/images/phone2.png" alt="Phone" /></span>
            <span id="lang">
                        <div>
                            <form id="lang_form" action="<%=context%>/locale.do" method="post">
                            </form>

                            <c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
                            <c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>
                            <ul>
                                <li style="float: left;"
                                        <c:choose>
                                            <c:when test='${enState}'>
                                                class="unselected_lang"
                                            </c:when>
                                            <c:otherwise>
                                                class="selected_lang"
                                            </c:otherwise>
                                        </c:choose>
                                        >
                                    <a id="eng_link" class="headerLinks"
                                            <c:choose>
                                                <c:when test='${enState}'>
                                                    href="javascript: void(0);"
                                                </c:when>
                                                <c:otherwise>
                                                    onclick="changeLang('changeLangToEnglish');"
                                                    href="javascript: void(0);"
                                                    title="England (English)"><bean:message key="label.next.language"/>
                                                </c:otherwise>
                                            </c:choose>
                                    </a>


                                </li>
                                <li style="float: left;"
                                        <c:choose>
                                            <c:when test='${frState}'>
                                                class="unselected_lang"
                                            </c:when>
                                            <c:otherwise>
                                                onclick="changeLang('changeLangToFrench');"
                                                href="javascript: void(0);"
                                                title="Fran\u00e7ais (French)"><bean:message key="label.next.language"/>
                                            </c:otherwise>
                                        </c:choose>
                                        </a>

                                </li>

                            </ul>
                        </div>
            </span>
            <ul id="user">
                <logic:present name="<%=Constant.USER_TRANSIENT%>">

                 <span>
                    <bean:message key="lable.page.basket.welcome"/>
                    <%=userName + ' ' + userFamily%>
                </span>|<%if(SecurityUtils.isAdmin()){%>
                    <a class="white" href="<%=context%>/reload.do" target="_blank"><bean:message key="label.cpanel"/></a>&nbsp;|<%}%>
                    <a href="javascript: logout();"><bean:message key="label.page.title.logout"/></a>

                </logic:present>
                <logic:notPresent name="<%=Constant.USER_TRANSIENT%>">
                    <a href="javascript: gotoLoginPage('false','false');"><bean:message key="label.page.title.login"/></a> |
                    <a href="<%=context%>/frontend.do?operation=goToRegistrationPage"><bean:message
                            key="label.page.title.register"/></a>
                </logic:notPresent>
            </ul>
        </div>
    </div>

    <div id="midbar">
        <div class="container">

            <a  href="<%=context%>/frontend.do" title="Double Pizza.ca"><img src="<%=context%>/css/images/primarylogo.png"/></a>
            <div class="cart-box">
                <label class="cart">
                    <c:choose>
                        <c:when test="${!empty basket}">
                            (<span id="basketItemsCount" class="basket-items-count" style="font-size: 14px;"><%=((Basket) session.getAttribute(Constant.BASKET)).getNumberOfItems()%></span>) <bean:message key="label.page.basket.header.items"/>
                        </c:when>
                        <c:otherwise>
                            <span style="font-size: 14px;">(0)</span><bean:message key="label.page.basket.header.items"/>
                        </c:otherwise>
                    </c:choose>
                </label>

                <c:set var="priceState" value="<%=(totalPrice != null && !totalPrice.equals(new BigDecimal(0)))%>"/>

                </label>
                <br>
                <c:set var="priceState" value="<%=(totalPrice != null && !totalPrice.equals(new BigDecimal(0)) )%>"/>
                <c:choose>
                    <c:when test="${priceState}">

                        <bean:message key="label.rightmenu.header.total.price"/>&nbsp;
                    <span id="basketTotalPrice" >


                         <c:choose>
                             <c:when test="<%=isDiscountUsed%>">
                                 <%= CurrencyUtils.toMoney((new TaxHandler().getTotalPriceWithTax(totalPrice.add(defaultServiceCostBD).subtract(BigDecimal.valueOf(discount)))).doubleValue())%>
                             </c:when>
                             <c:otherwise>
                                 <%= CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice.add(defaultServiceCostBD)))%>
                             </c:otherwise>
                         </c:choose>
                </span>

                        <a href="javascript: void (0);" onclick="if(<%=user != null && user.getDpDollarBalance() > 0%>){if(isUserUncheckedDpDollar != false)setIsDiscountUsed(true);} else {setIsDiscountUsed(false);}goToCheckoutPage();" class="checkout"><bean:message key="button.checkout"/></a>

                    </c:when>
                    <c:otherwise>
                        <span> <bean:message key="label.rightmenu.header.total.price"/>&nbsp;$00.00</span>
                    </c:otherwise>
                </c:choose>

            </div>

            <div id="orderbox"><a  onclick="setMenuType('menu');" href="<%=context%>/frontend.do?operation=getMenuTypeItems&amp;menuType=menu" id="order_now"><bean:message key="label.order.now"/></a></div>
        </div>
    </div>
    <div id="menubar">
        <div class="container">
            <ul id="menu">
              <li><a  href="<%=context%>/frontend.do" title="Double Pizza.ca"> <bean:message key="label.page.menu.home"/></a></li>
                <li><a id="quickmenu"  href="<%=context%>/quickmenu.do?operation=goToQuickMenu" onclick="setMenuType('menu'); getQuickMenu('menu');" title="<bean:message key="label.page.quickMenu"/>"><bean:message key="label.page.quickMenu"/></a></li>
                <li><a  href="<%=context%>/catering.do?operation=goToCaternig" title="<bean:message key="label.page.menu.catering"/>" onclick="getCateringItems();" ><bean:message key="label.page.menu.catering"/></a></li>
                <li><a id="menu_item_storeLocator" href="<%=context%>/frontend.do?operation=goToStoreLocator" title="<bean:message key="label.page.menu.store.locator"/>">  <bean:message key="label.page.menu.store.locator"/> </a>  </li>
                <li><a id="menu_item_franchising" href="<%=context%>/frontend.do?operation=goToFranchisingPage" title="<bean:message key="label.page.menu.franchising"/>" ><bean:message key="label.page.menu.franchising"/></a></li>
                <li>
                <ul class="mainmenu2" >
                    <li class="menudrop"> <a href="<%=context%>/frontend.do?operation=goToAboutPage" title="" class=""><bean:message key="label.page.menu.about.us"/></a>
                        <div class="submenu2">
                            <ul>
                                <li  style="list-style: none;  border-left:none; "><a title="<bean:message key="label.page.menu.about.DP"/>" href="<%=context%>/frontend.do?operation=goToAboutPage"><bean:message key="label.page.menu.about.DP"/></a></li>
                                <li  style="list-style: none;  border-left:none; "><a title="<bean:message key="label.page.footer.link.contact"/>"  href="<%=context%>/frontend.do?operation=goToFeedBackPage" ><bean:message key="label.page.footer.link.contact"/> </a></li>
                                <li  style="list-style: none;  border-left:none; "><a title="<bean:message key="label.page.footer.link.contact"/>"  href="<%=context%>/frontend.do?operation=goToDPDOLLARS" ><bean:message key="label.page.menu.doublePizzaDOLLARS"/></a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
                </li>
                    <%
                        if (user != null) {
                    %>
                <li style="border-right: 1px solid #fbac81;box-shadow: -1px 0 0 0 #ce5447;">
                    <ul class="mainmenu3">
                        <li class="menudrop1"><a style="display: block;" class="MenuBarItemSubmenu " href="javascript: void(0);"
                                                 onclick="gotoLoginPage('false','false');"><bean:message key="label.myDP"/></a>
                            <div class="submenu3">
                                <ul id="dpmenu">
                                    <li style="list-style: none;  border-left:none;  box-shadow:none;"><a  style="list-style: none;  border-left:none; " href="<c:out value="${pageContext.request.contextPath}"/>/profile.do">
                                        <bean:message key="myprofile.title"/></a></li>
                                    <li style="list-style: none;  border-left:none;  box-shadow:none;"><a href="javascript: void(0);" onclick="goToDpDollarsHistory();"><bean:message key="DPDollars.myDPDollars"/></a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>

                </li>
                 <%
                        }
                        else if (user == null) {
                 %>
                <li style="border-right: 1px solid #fbac81;box-shadow: 0 0 0 1px  #ce5447;"><a  style="display: block;" class="MenuBarItemSubmenu "   onclick="gotoLoginPage('false','false');"  href="javascript: void(0);" ><bean:message key="label.myDP"/></a></li>
                <%
                    }
                %>
                </ul>
        </div>
    </div>

</div>

<div class="Clear"></div>
<script type="text/javascript">
    var MenuBar = new Spry.Widget.MenuBar("MenuBar", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
</script>

<script type="text/javascript">
var lastMenuType = 'menu';
var isUserUncheckedDpDollar = <%=(Boolean) request.getSession().getAttribute("isUserUncheckedDpDollar")%>
var isDiscountUsed;
var customizedBasketItemId = <%=request.getAttribute("customizedBasketItemId")%>;
$.ajaxSetup ({
    // Disable caching of AJAX responses
    cache: false,
    beforeSend: function(request) {
        request.setRequestHeader("isAjax", "1");
    },
    complete: function(request) {
        if (request.status == "512")
            location.href = getHomeUrl();

    }
});

function setIsDiscountUsed(val) {
    isDiscountUsed = val;
}

function getIsDiscountUsed() {
    return isDiscountUsed;
}

function setCustomizedBasketItemId(id) {
    customizedBasketItemId = id;
}

function getCustomizedBasketItemId() {
    return customizedBasketItemId;
}

function setMenuType(type) {
    lastMenuType = type;
}

function getMenuType() {
    return lastMenuType;
}

function getMenuList(menuType) {
    setMenuType(menuType);
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'getMenuTypeItems');

    var paramName = document.createElement('input')
    paramName.setAttribute('type', 'hidden');
    paramName.setAttribute('name', 'menuType');
    paramName.setAttribute('value', lastMenuType);


    myForm.method = 'POST';
    myForm.action = '<%=context%>/frontend.do';

    myForm.appendChild(operation);
    myForm.appendChild(paramName);

    myForm.submit();
}

function goToOrderHistory(userEmail) {

    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'getOrderHistory');

    var userEmail_param = document.createElement('input');
    userEmail_param.setAttribute('type', 'hidden');
    userEmail_param.setAttribute('name', 'userEmail');
    userEmail_param.setAttribute('value', userEmail);


    myForm.method='GET';
    myForm.action = '<%=context%>/order.do';

    myForm.appendChild(operation);
    myForm.appendChild(userEmail_param);

    myForm.submit();
}

function showSessionData() {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'showSessionData'},
        success: function (res) {
            alert(res);
        },
        failure: function () {
            alert('FAILURE');
        }
    });


}

function goToOnlineOrder() {
    var path = document.getElementById('menu_item_home').href;
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    var hiddenField = document.createElement("input");
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", "toOnlineOrder");
    hiddenField.setAttribute("value", "1");
    form.appendChild(hiddenField);
    document.body.appendChild(form);
    form.submit();
}

function setCookie(c_name, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var c_value = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
    document.cookie = c_name + "=" + c_value;
}

function getCookie(c_name) {
    var i, x, y, ARRcookies = document.cookie.split(";");
    for (i = 0; i < ARRcookies.length; i++) {
        x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
        y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
        x = x.replace(/^\s+|\s+$/g, "");
        if (x == c_name) {
            return unescape(y);
        }
    }
    return "";

}

function getHomeUrl() {
    return document.getElementById('order_now').href;
}

function changeLang(operationValue) {

    var locationVar = window.location.href;

    if(locationVar.indexOf('cateringContactInformation.do?operation=save')>0){

        if(document.getElementsByName('errorBlock').length==0){
            locationVar=locationVar.replace('cateringContactInformation.do?operation=save','catering.do');
        }
    }
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);
    myForm.method = 'POST';
    myForm.action = '<%=context%>/locale.do';

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'method');
    operation.setAttribute('value', operationValue);

    var location = document.createElement('input');
    location.setAttribute('type', 'hidden');
    location.setAttribute('name', 'location');
    location.setAttribute('value', locationVar);

    myForm.appendChild(operation);
    myForm.appendChild(location);

    myForm.submit();
}

function getQuickMenu(type) {
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);
    myForm.method = 'POST';
    myForm.action = '<%=context%>/quickmenu.do';

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToQuickMenu');

    var menuType = document.createElement('input');
    menuType.setAttribute('type', 'hidden');
    menuType.setAttribute('name', 'menuType');
    menuType.setAttribute('value', getMenuType());

    var foodType = document.createElement('input');
    foodType.setAttribute('type', 'hidden');
    foodType.setAttribute('name', 'foodType');
    foodType.setAttribute('value', type);

    myForm.appendChild(operation);
    myForm.appendChild(menuType);
    myForm.appendChild(foodType);
    myForm.submit();

}


function getCateringItems() {
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);
    myForm.method = 'POST';
    myForm.action = '<%=context%>/catering.do';

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToCaternig');

    myForm.appendChild(operation);

    myForm.submit();
}


function gotoLoginPage(isFromCheckout) {
    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToLoginPage');

    var latestUrl = document.createElement('input');
    latestUrl.setAttribute('type', 'hidden');
    latestUrl.setAttribute('name', '<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>');
    latestUrl.setAttribute('value', isFromCheckout);

    myForm.appendChild(operation);
    myForm.appendChild(latestUrl);

    myForm.submit();
}

function gotoLoginPageFromCheckout(isFromCheckout, dontShowSuggestionsAgain) {
    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToLoginPage');

    if (dontShowSuggestionsAgain) {
        var dontShowSuggestionsAgainField = document.createElement('input');
        dontShowSuggestionsAgainField.setAttribute('type', 'hidden');
        dontShowSuggestionsAgainField.setAttribute('name', 'dontShowSuggestionsAgin');
        dontShowSuggestionsAgainField.setAttribute('value', 'true');
        myForm.appendChild(dontShowSuggestionsAgainField);
    }

    var latestUrl = document.createElement('input');
    latestUrl.setAttribute('type', 'hidden');
    latestUrl.setAttribute('name', '<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>');
    latestUrl.setAttribute('value', isFromCheckout);

    myForm.appendChild(operation);
    myForm.appendChild(latestUrl);

    myForm.submit();
}

function logout() {
    var currentUrl = window.location;
    window.location.href = '<%=context%>/logout.do?operation=logout&<%=Constant.LATEST_USER_URL%>=' + currentUrl;
}

function viewPopup(id, action) {
    if (action == 'open') {
        $('#' + id).css({'display': 'block'});
    } else if (action == 'close') {
        $('#' + id).css({'display': 'none'});
    }
}

function login(url) {
    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);
    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToLoginPage');
    var latestUrl = document.createElement('input');
    latestUrl.setAttribute('type', 'hidden');
    latestUrl.setAttribute('name', 'previousUrl');
    latestUrl.setAttribute('value', url);

    myForm.appendChild(operation);
    myForm.appendChild(latestUrl);

    myForm.submit();
}

function goToDpDollarsHistory() {
    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'get');
    myForm.setAttribute('action', '<%=context%>/mydollars.do');
    document.body.appendChild(myForm);
    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'getDpDollarsHistory');


    myForm.appendChild(operation);
    myForm.submit();
}

function goToCheckoutPage() {
    if (isUserUncheckedDpDollar == true) {
        isDiscountUsed = false;
    } else {
        isDiscountUsed = true;
    }
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToCheckoutPage');

    var isDiscountUsed_param = document.createElement('input');
    isDiscountUsed_param.setAttribute('type', 'hidden');
    isDiscountUsed_param.setAttribute('name', 'isDiscountUsed');
    isDiscountUsed_param.setAttribute('value', isDiscountUsed);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/checkout.do';

    myForm.appendChild(operation);
    myForm.appendChild(isDiscountUsed_param);
    myForm.submit();
}

function shareItemOnFaceBook(name, link, caption, description) {
    <%
       String sessionId = session.getId();
       String siteUrl = request.getRequestURL().toString().replace(request.getRequestURI(),"")+ request.getContextPath();

       String appId = "";
       String redirectUrl = siteUrl + "/facebookLoginAction.do";
       String returnValue = "https://www.facebook.com/dialog/oauth/";
       String stringQuery = "?client_id=" + appId + "&redirect_uri=" + redirectUrl
               + "&scope=email,user_birthday"
               + "&state=" + sessionId;

       String url = returnValue + stringQuery;
   %>
    <%--href="http://www.facebook.com/sharer.php?u=<%=url%><%=ctx%>/goToViewRecipe/${requestScope.recipe.id}"--%>
    FB.ui({
        method: 'feed',
        display: 'popup',
        name: name,
        link: link,
        caption: caption,
        description: description,
        message: ''

    }, function (response) {
        if (typeof reponse == 'object' && typeof response['post_id'] != 'undefined') {
            msg = 'Message send.';
        } else {
            msg = 'Failed to send message.';
        }
    });
}

</script>
<script type="text/javascript">
    $(function () {
        $(".menudrop").hover(
                function () {
                    $(".submenu2").show();
                },
                function () {
                    $(".submenu2").removeClass("active");
                    $(".submenu2").hide();
                }
        );
    });
    $(function () {
        $(".menudrop1").hover(
                function () {
                    $(".submenu3").show();
                },
                function () {
                    $(".submenu3").removeClass("active");
                    $(".submenu3").hide();
                }
        );
    });
</script>