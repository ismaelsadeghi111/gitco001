<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="s" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 23, 2011
  Time: 2:24:07 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Basket basket = (Basket)session.getAttribute(Constant.BASKET);
    BigDecimal totalPrice = null;
    if (basket != null) {
        totalPrice = basket.calculateTotalPrice();
    }
    String context = request.getContextPath();

%>

<script type="text/javascript">
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
    function showSessionData() {
        $.ajax({
            type    :'POST',
            url     :'<%=context%>/frontend.do',
            data    :{operation: 'showSessionData'},
            success:function(res) {
                alert(res);
            },
            failure: function() {
                alert('FAILURE');
            }
        });


    }

    function goToOnlineOrder(){
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

    function setCookie(c_name,value,exdays)
    {
        var exdate=new Date();
        exdate.setDate(exdate.getDate() + exdays);
        var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
        document.cookie=c_name + "=" + c_value;
    }

    function getCookie(c_name)
    {
        var i,x,y,ARRcookies=document.cookie.split(";");
        for (i=0;i<ARRcookies.length;i++)
        {
          x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
          y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
          x=x.replace(/^\s+|\s+$/g,"");
          if (x==c_name)
            {
            return unescape(y);
            }
          }
        return "";

    }

    function getHomeUrl(){
        return document.getElementById('menu_item_home').href;
    }

    function changeLang(operationValue) {

        var locationVar = window.location.href;

        var myForm = document.getElementById('lang_form');

        var operation = document.createElement('input');
        operation.setAttribute('type','hidden');
        operation.setAttribute('name','method');
        operation.setAttribute('value',operationValue);

        var location = document.createElement('input');
        location.setAttribute('type','hidden');
        location.setAttribute('name','location');
        location.setAttribute('value',locationVar);


        myForm.appendChild(operation);
        myForm.appendChild(location);

        myForm.submit();
    }


    $(document).ready(function() {
        var selectedMenuItem = document.getElementById("<%= request.getAttribute(Constant.ALTERNATIVE_ALL_CITIES) %>");
        if (selectedMenuItem != null)
            selectedMenuItem.setAttribute("class","selected");

    });

</script>
<%
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    String userName="";
    if(user!=null){
        userName = StringUtil.extractIdFormEmailAddress(user.getEmail());
    }
%>
<div id="header">
    <a id="header_logo" href="#" title="DoublePizza">
        <img class="logo" src="<%=request.getContextPath()%>/img/logo.png" alt="DoublePizza" />
    </a>
    <div id="header_right">
        <script type="text/javascript">

        </script>

        <div id="languages_block_top">


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
                                </c:otherwise>
                            </c:choose>
                       title="England (English)"><bean:message key="label.next.language"/>
                    </a>
                </li>
                <li style="float: left; margin-left:-47px;"
                        <c:choose>
                            <c:when test='${frState}'>
                                class="unselected_lang"
                            </c:when>
                            <c:otherwise>
                                class="selected_lang"
                            </c:otherwise>
                        </c:choose>
                        >
                    <a id="frn_link" class="headerLinks"
                            <c:choose>
                                <c:when test='${frState}'>
                                    href="javascript: void(0);"
                                </c:when>
                                <c:otherwise>
                                    onclick="changeLang('changeLangToFrench');"
                                    href="javascript: void(0);"
                                </c:otherwise>
                            </c:choose>
                       title="Franзais (French)"><bean:message key="label.next.language"/>
                    </a>
                </li>
            </ul>

        </div>

        <div id="header_user">
            <ul>
                <li id="your_account">

                </li>
                <li id="shopping_cart">
                    <div>
                        <p>
                            <span>
                                <bean:message key="label.page.basket.now.in.your.cart" />
                            </span>
                            <c:choose>
                                <c:when test="${!empty basket}">
                                    <span id="item_num" class="basket-items-count" style="padding: 0 0px 0 1px; font: bold 32px Arial;"><%=((Basket)session.getAttribute(Constant.BASKET)).getNumberOfItems()%></span>
                                    <span>
                                        <bean:message key="label.page.basket.items" />
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="basket-items-count" style="padding: 0 5px 0 5px; font: bold 32px Arial;">0</span>
                                    <span>
                                        <bean:message key="label.page.basket.items" />
                                    </span>
                                </c:otherwise>
                            </c:choose>

                        </p>

                        <c:if test="${!empty basket}">
                             <c:if test="<%=(!((Basket)(request.getSession().getAttribute(Constant.BASKET))).isEmpty())%>">
                            <a id="your_account" href="<c:url value="/checkout.do" context="<%=context%>">
                            <c:param name="operation" value="goToCheckoutPage"/>
                            </c:url>">
                                <bean:message key="label.page.basket.your.account"/>
                            </a>
                        </c:if>
                        </c:if>


                    </div>
                </li>
                <%--this should be price info --%>
                <li id="account_info">
                    <c:set var="priceState" value="<%=(totalPrice != null && !totalPrice.equals(new BigDecimal(0)) )%>"/>
                    <c:if test="${priceState}">
                    <bean:message key="label.rightmenu.header.total.price" />&nbsp;
                        <%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%>
                    </c:if>
                </li>
            </ul>
        </div>



        <c:if test="${enState}">
        <div id="search_block_top">
        </c:if>
        <c:if test="${frState}">
        <div id="search_block_top_fr">
        </c:if>
        </div>

        <c:if test="${enState}">
        <div id="order_online" onclick="goToOnlineOrder();">
        </c:if>
        <c:if test="${frState}">
        <div id="order_online_fr" onclick="goToOnlineOrder();">
        </c:if>
        </div>

        <ul id="header_links">

            <logic:present name="<%=Constant.USER_TRANSIENT%>">
            </logic:present>
            <logic:notPresent name="<%=Constant.USER_TRANSIENT%>">
                <li><a href="javascript: gotoLoginPage('false');"><bean:message key="label.page.title.login" /></a></li>
                <li><a href="<%=context%>/frontend.do?operation=goToRegistrationPage"><bean:message
                        key="label.page.title.register"/></a></li>
            </logic:notPresent>
            <logic:present name="<%=Constant.USER_TRANSIENT%>">
                <li><a href="javascript: logout();"><bean:message key="label.page.title.logout" /></a></li>
                <li style="color: #EBC000;font: 12px Arial;text-decoration: none;text-transform: capitalize; margin-left:14px; display:inline;">
                        <bean:message key="lable.page.basket.welcome" />
                        <%=userName%>
                    </li>
            </logic:present>

        </ul>

        <div id="tmcategories">
            <ul id="cat">
                <li id="first_li"><a id="menu_item_home" style="width:80px;" href="<%=context%>/frontend.do" title="" class=""><bean:message
                        key="label.page.menu.home"/></a></li>
                <li><a id="menu_item_menu_aboutUs" style="width:160px;" href="<%=context%>/frontend.do?operation=goToAboutPage" title=""
                       class=""><bean:message key="label.page.menu.about.us"/></a></li>
                <li> <a id="menu_item_menu_catering" style="width:168px;" href="<%=context%>/frontend.do?operation=goToCateringPage">
                    <bean:message key="label.page.menu.catering"/></a> </li>
                <li><a id="menu_item_franchising" style="width:110px;" href="<%=context%>/frontend.do?operation=goToFranchisingPage" title=""
                       class=""><bean:message key="label.page.menu.franchising"/></a></li>
                <li><a id="menu_item_menu_funZone" style="min-width:120px;" href="<%=context%>/frontend.do?operation=goToFunZonePage" title=""
                       class=""><bean:message key="label.page.menu.fun.zone"/></a></li>
                <li><a id="menu_item_contactUs" style="width:150px;"
                       href="<%=context%>/frontend.do?operation=goToFeedBackPage" title="" class=""><bean:message
                        key="label.page.menu.contactUs"/></a></li>
                <li><a id="menu_item_storeLocator" style="width:150px;" href="<%=context%>/frontend.do?operation=goToStoreLocator" title="" class=""><bean:message
                        key="label.page.menu.store.locator"/></a></li>
            </ul>
        </div>

    </div>

</div>






