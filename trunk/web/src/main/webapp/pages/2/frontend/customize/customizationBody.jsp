<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
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
    String itemName = (String) request.getAttribute("itemName");
    String selectedSingleItemPrice = (String) request.getAttribute("selectedSingleItemPrice");
    selectedSingleItemPrice = selectedSingleItemPrice == null ? (String) pageContext.getAttribute("selectedSingleItemPrice") : selectedSingleItemPrice;
    String priceNum = (String) request.getAttribute("price");
    String price = (priceNum == null) ? selectedSingleItemPrice : priceNum;
    String menuType = (String) pageContext.getAttribute("menuType");
    menuType = (menuType == null || menuType.isEmpty()) ? (String) request.getAttribute("menuType") : menuType;
    menuType = (menuType == null || menuType.isEmpty()) ? (String) request.getSession().getAttribute("menuType") : menuType;
    itemName = itemName == null ? " " : itemName;
    Category lastCategory = (Category) (request.getSession().getAttribute("lastCategory"));
    Object lastSelectedItem = null;
    if (request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) != null &&
            request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) instanceof CombinedMenuItem) {
        lastSelectedItem = (CombinedMenuItem) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM);
    } else if (request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) != null &&
            request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) instanceof MenuSingleItem) {
        lastSelectedItem = (MenuSingleItem) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM);
    }else if (request.getSession().getAttribute(Constant.LAST_CATEGORY) != null &&
            request.getSession().getAttribute(Constant.LAST_CATEGORY) instanceof Category) {
        lastSelectedItem = (Category) request.getSession().getAttribute(Constant.LAST_CATEGORY);
    }


    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    String totalItems = (String) (request.getParameter("totalItems") == null ? request.getSession().getAttribute("totalItems") : request.getParameter("totalItems"));
    String totalPrice = (String) (request.getParameter("totalPrice") == null ? request.getSession().getAttribute("totalPrice") : request.getParameter("totalPrice"));
/*    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires", 0);*/
    int listOrder =0;
    String listOrderStr=(String) request.getAttribute("listOrder");
    if(listOrderStr!=null){
        listOrder = Integer.parseInt(listOrderStr);
    }
    String groupId="";
    String typeLast="none";
    if(lastSelectedItem instanceof Category){
    typeLast="Category";
    }
    if(lastSelectedItem instanceof MenuSingleItem){
    typeLast="MenuSingleItem";
    }
%>

<%
    if(lastSelectedItem instanceof Category)
    {
        Object categoryitem=((Category) lastSelectedItem).getSubCategoryList().get(0).getObject();
        MenuSingleItem categoryitem1=(MenuSingleItem)categoryitem;
        groupId=categoryitem1.getGroupId();
    }

%>
<script>
    var typeLast='<%=typeLast%>';
    if(typeLast=='Category'){
        setTypeLast('Category');
    }
</script>


<%--<%=lastSelectedItem.getClass()%>--%>
<table border="0" width="100%"  style="padding: 15px;">
    <tr>
        <td width="40%">
            <h1 class="cusfood"> <bean:message key="label.cutomize.your.food"/></h1>
        </td>
        <td width="50%">
            <div class="Floatleft">
                <%--<span class="font5"><bean:message key="label.Order.From.Our"/>:</span>--%>
                <a style="padding: 12px 32px;" class="btn-first" href="javascript: void(0);"  onclick="setMenuType('menu'); getMenuList('menu');" style="margin:3px;" ><bean:message key="label.page.title.menu"/> </a>
                <a style="padding: 12px 24px;" class="btn-first" href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" style="margin:3px;"><bean:message key="label.page.title.specials"/> </a>
            </div>
        </td>


    </tr>
</table>
<%--<button onclick="alert('Sum:'+getCombinedItemsPrice());">Mio</button>--%>
<table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="10%">
            <img class="img-customiz" src="<%=context%><%= middlepath%>${requestScope.imageAddress}" width="150"
                 height="100" alt="Double pizza"/>
            <input type="hidden" name="imageAddress" value="${requestScope.imageAddress}"/></td>

        <td width="">
            <label class="foodtitle" style="text-decoration:underline;padding-left: 15px;">
                <c:choose>
                    <c:when test="<%=lastSelectedItem != null%>">
                        <%=(lastSelectedItem != null && lastSelectedItem instanceof CombinedMenuItem) ? ((CombinedMenuItem) lastSelectedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)) :
                                (lastSelectedItem != null && lastSelectedItem instanceof MenuSingleItem) ? ((MenuSingleItem) lastSelectedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)) :
                                        (lastSelectedItem != null && lastSelectedItem instanceof Category) ? ((Category) lastSelectedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)) : ""%></label>
                    <span class="red" style="text-decoration:none;font-size: 14px;font-family:'ItcKabelMedium',Arial;">(
                            <%=(lastSelectedItem != null && lastSelectedItem instanceof CombinedMenuItem) ? ((CombinedMenuItem) lastSelectedItem).getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY)) :
                                    (lastSelectedItem != null && lastSelectedItem instanceof MenuSingleItem) ? ((MenuSingleItem) lastSelectedItem).getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY)) :
                                            (lastSelectedItem != null && lastSelectedItem instanceof Category) ? ((Category) lastSelectedItem).getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY)) : ""%>)
                        </span>
                    </c:when>
                    <c:otherwise>
                        <%=lastCategory.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                    </c:otherwise>
                </c:choose>
<%--
            <a style="cursor: pointer;">
                <div style="margin-top: 18px;"><img style="padding-left: 10px;margin-left: 10px;"
                                                    src="<%=context%>/images/fb-2.png" alt="Facebook"/></div>
            </a>--%>
        </td>
        <td>
            <table border="0" style="float:right;">
                <tr>
                    <td >
                        <label id="down_price" class="price FloatRight" style="display: none"><bean:message key="label.price"/>
                            <c:choose>
                                <c:when test="${not empty requestScope.price}">
                                    ${requestScope.price}
                                </c:when>
                                <c:otherwise>
                                    <%=price%>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>

                        <c:choose>
                            <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                           or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">

                                    <a id="add-to-card" class="bt-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="addAlert();document.getElementById('combinedType_<%= listOrder %>').onclick();document.getElementById('apply_but').onclick();document.getElementById('buttonAddCombinedToBasket').onclick();"><bean:message
                                            key="button.addToCart"/></a>
                                </c:if>
                                <c:if test="${empty requestScope.defaultExlusiveToppingCategory or empty requestScope.selectedExlusiveToppingCategory
                                          or empty requestScope.defaultItemToppingCategory or empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="
                                       document.getElementById('combinedType_0').click();
                                       setTimeout(function(){document.getElementById('buttonAddCombinedToBasket').onclick()},500);
                                       addAlert();
                                               ">
                                        <bean:message   key="button.addToCart"/></a>

                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="document.getElementById('combinedType_<%= listOrder %>').onclick();document.getElementById('apply_but').onclick();addAlert();applyToppingValue(); getBasketItems(); setToppingSelectedState(false);"><bean:message
                                            key="button.addToCart"/></a>
                                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <br class="clear_float"/>
                                        <c:if test="${empty requestScope.selectedExlusiveToppingCategory}">
                                            <label><bean:message key="message.add.single.to.cart"/></label>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="
                                                addAlert();
                                                setToppingSelectedState(false);
                                                applyToppingValue();

                                               "><bean:message
                                            key="button.addToCart"/></a>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

<% if(lastSelectedItem instanceof CombinedMenuItem){%>
<%--Start of customization page ======================================================================================================================================================================--%>
<div class="Clear"></div>
<div class="LeftMiddleWrapper4SubCat">
    <div id="combinedTypesMenu" class="for_1_pizza_left">
        <%-- filled from combinedTypesMenuInHeader2.jsp --%>
    </div>
</div>
<div class="RightMiddleWrapper">
    <fieldset class="baskeDetails">
        <legend><bean:message key="label.your.choice"/> </legend>
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width=20%"  style="text-align: center; vertical-align: middle;">
                 <span id="basketDetail" class="title2">
                     <%-- Inject by two function SetTopping --%>

                 </span>
                </td>
                <td  width="70%"  style="text-align: center; vertical-align: middle;">
                    <label><img id="loadingimg" src="<%=context%>/images/loader2.gif"/></label>

                    <span id="basketDetails" class="font4">

                        <%-- Inject by two function setAlternative  --%>
                    </span>

                </td>
                <td  width="15%"  style="text-align: center; vertical-align: middle;">
                    <a class="btn-second FloatRight" href="javascript: void(0);"
                       title="<bean:message key="button.reset"/>"
                       onclick="setAlternative(alternative); getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message
                            key="button.reset"/></a>

                </td>
            </tr>

        </table>
    </fieldset>
    <% if(((CombinedMenuItem) lastSelectedItem).getMenuSingleItemList().get(0).getToppingCategoryList().size()!=0){%>
    <div class="alterNative">
        <div style="font-size:18px;font-family:'ItcKabelMedium',Arial;  padding: 5px 5px;">
            <bean:message key="label.Choose.your.type"/>
        </div>
        <div  id="center_column_inner_mir">
            <%-- filled from singleMenuItemInHeader2.jsp --%>
        </div>
    </div>

    <div class="RightMiddleWrapper4Topping">
        <label style="font-size:18px;font-family:'ItcKabelMedium',Arial;">
            <bean:message key="label.food.topping"/>
        </label>
        <div  id="featured-products_block_center">

            <%-- filled from customizTopping.jsp --%>
        </div>
        <div id="howcookfood">

        </div>
    </div>
    <%}else {%>
    <div class="alterNative"  style="width: 80%;margin-bottom: 20px;margin-left: 8%;padding: 20px;">
        <div style="font-size:18px;font-family:'ItcKabelMedium',Arial;  padding: 5px 5px;">
            <bean:message key="label.Choose.your.type"/>
        </div>
        <div  id="center_column_inner_mir">
            <%-- filled from singleMenuItemInHeader2.jsp --%>
        </div>
    </div>
    <%}%>
</div>

<div class="Clear"></div>
<%--End of customization page ======================================================================================================================================================================--%>
<%}%>
<% if((lastSelectedItem instanceof MenuSingleItem) || (groupId.equalsIgnoreCase("0010"))){%>
<%--Start of customization page ======================================================================================================================================================================--%>
<div class="Clear"></div>

<div class="RightMiddleWrapper"  style="width: 100%;min-height: 300px;">
    <fieldset class="baskeDetails">
        <legend><bean:message key="label.your.choice"/></legend>
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20%"  style="text-align: center; vertical-align: middle;">
                 <span id="basketDetail" class="title2">
                     <%-- Inject by two function SetTopping --%>

                 </span>
                </td>
                <td  width="70%"  style="text-align: center; vertical-align: middle;">
                    <label><img id="loadingimg" src="<%=context%>/images/loader2.gif"/></label>

                    <span id="basketDetails" class="font4">

                        <%-- Inject by two function setAlternative  --%>
                    </span>

                </td>
                <td  width="15%"  style="text-align: center; vertical-align: middle;">
                    <a class="btn-second FloatRight" href="javascript: void(0);"
                       title="<bean:message key="button.reset"/>"
                       onclick="setAlternative(alternative); getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message
                            key="button.reset"/></a>

                </td>
            </tr>

        </table>
    </fieldset>

    <div class="alterNative"  style="width: 80%;margin-bottom: 20px;margin-left: 8%;padding: 20px;    min-height: 200px;">
        <div style="font-size:18px;font-family:'ItcKabelMedium',Arial;  padding: 5px 5px;">
            <bean:message key="label.Choose.your.type"/>
        </div>
        <div  id="center_column_inner_mir">
            <%-- filled from singleMenuItemInHeader2.jsp --%>
        </div>
    </div>

</div>

<div class="Clear"></div>
<%--End of customization page ======================================================================================================================================================================--%>
<%}%>
<% if((lastSelectedItem instanceof Category) && !(groupId.equalsIgnoreCase("0010"))){%>

<%--Start of customization page ======================================================================================================================================================================--%>
<div class="Clear"></div>

<div class="RightMiddleWrapper" style="width: 100%;height: auto;min-height:auto;">
    <fieldset class="baskeDetails"  style="width: 77%;margin-left: 10%;">
        <legend><bean:message key="label.your.choice"/></legend>
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="20%"  style="text-align: center; vertical-align: middle;">
                 <span id="basketDetail" class="title2">
                     <%-- Inject by two function SetTopping --%>

                 </span>
                </td>
                <td  width="70%"  style="text-align: center; vertical-align: middle;">
                    <label><img id="loadingimg" src="<%=context%>/images/loader2.gif"/></label>

                    <span id="basketDetails" class="font4">
                        <%-- Inject by two function setAlternative  --%>
                    </span>

                </td>
                <td  width="15%"  style="text-align: center; vertical-align: middle;">
                    <a class="btn-second FloatRight" href="javascript: void(0);"
                       title="<bean:message key="button.reset"/>"
                       onclick="setAlternative(alternative); getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message
                            key="button.reset"/></a>

                </td>
            </tr>

        </table>
    </fieldset>

    <div class="alterNative" style="margin-left: 10%;height: auto;min-height:auto;">
        <div style="font-size:18px;font-family:'ItcKabelMedium',Arial;  padding: 5px 5px;">
            <bean:message key="label.Choose.your.type"/>
        </div>
        <div  id="center_column_inner_mir">
            <%-- filled from singleMenuItemInHeader2.jsp --%>
        </div>
    </div>
    <div class="RightMiddleWrapper4Topping" style="height: auto;min-height:auto;">
        <label style="font-size:18px;font-family:'ItcKabelMedium',Arial;"> <bean:message key="label.food.topping"/> </label>
        <div  id="featured-products_block_center">
            <%-- filled from customizTopping.jsp --%>
        </div>
        <div id="howcookfood">

        </div>
    </div>
</div>

<div class="Clear"></div>
<%--End of customization page ======================================================================================================================================================================--%>
<%}%>


<table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>

        <td width="">
            <table  style="float:right;">
                <tr>
                    <td>
                        <label id="down_price" class="price FloatRight"><bean:message key="label.price"/> <c:choose><c:when
                                test="${not empty requestScope.price}">${requestScope.price}</c:when><c:otherwise><%=price%>
                        </c:otherwise></c:choose></label>
                    </td>
                </tr>
                <tr>
                    <td>


                        <c:choose>
                            <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                           or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">

                                    <a id="add-to-card" class="bt-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="addAlert();document.getElementById('combinedType_<%= listOrder %>').onclick();document.getElementById('apply_but').onclick();document.getElementById('buttonAddCombinedToBasket').onclick();"><bean:message
                                            key="button.addToCart"/></a>
                                </c:if>
                                <c:if test="${empty requestScope.defaultExlusiveToppingCategory or empty requestScope.selectedExlusiveToppingCategory
                                          or empty requestScope.defaultItemToppingCategory or empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="
                                       document.getElementById('combinedType_0').click();
                                       setTimeout(function(){document.getElementById('buttonAddCombinedToBasket').onclick()},500);
                                       addAlert();
                                               ">
                                        <bean:message   key="button.addToCart"/></a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="document.getElementById('combinedType_<%= listOrder %>').onclick();document.getElementById('apply_but').onclick();addAlert();applyToppingValue(); getBasketItems(); setToppingSelectedState(false);"><bean:message
                                            key="button.addToCart"/></a>
                                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <br class="clear_float"/>
                                        <c:if test="${empty requestScope.selectedExlusiveToppingCategory}">
                                            <label><bean:message key="message.add.single.to.cart"/></label>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">
                                    <a id="add-to-card" class="btn-first FloatRight" href="javascript: void(0);"
                                       title="<bean:message key="button.addToCart"/>"
                                       onclick="
                                                addAlert();
                                                setToppingSelectedState(false);
                                                applyToppingValue();
                                               "><bean:message
                                            key="button.addToCart"/></a>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>
<script>

    function showSuccess(){
        new $.Zebra_Dialog('<span style="font-size:20px;"><bean:message key="msg.addItem"/></span>'
               , {
            'buttons':  false,
            'type':'confirmation',
            'modal': false,
            'position':['center', 'top + 20'] ,
            'auto_close': 2000});
    }
    var topping=[];
    var alternative="";
    var flag=0;
    Array.prototype.toStringDefault = Array.prototype.toString;
    Array.prototype.toString = function (delim) {
        if ('undefined' === typeof delim) {
            return this.toStringDefault();
        }
        return this.join(delim);
    }


    function setTopping(item){
        var start = new Date().getTime();
        if (topping.indexOf(item) == -1)
        {
            topping.push(item);
        }
        var basketDetail= document.getElementById("basketDetail");
        var basketDetails= document.getElementById("basketDetails");
        var result = topping.toString(' + ');
        basketDetail.innerHTML=alternative;
        basketDetails.innerHTML=result;
        var end = new Date().getTime();
        var time = end - start;
        console.log('Time ejra setTopping:'+time);
    }
    function removeTopping(item){

        var index = topping.indexOf(item);
        topping.splice(index, 1);
        var basketDetail= document.getElementById("basketDetail");
        var basketDetails= document.getElementById("basketDetails");
        var result = topping.toString(' + ');
        basketDetail.innerHTML=alternative;
        basketDetails.innerHTML=result;
    }
    function setAlternative(item){
        topping=[];
        alternative=item;
        var basketDetail= document.getElementById("basketDetail");
        var basketDetails= document.getElementById("basketDetails");
        var result = topping.toString(' + ');
        basketDetail.innerHTML=alternative;
        basketDetails.innerHTML=result;
    }

    function addAlert() {
        showSuccess();
    }

    $(document).ready(function() {
      var typeLast='<%=typeLast%>';
      var price='<%=price%>';
        if(typeLast=='Category' || typeLast=='MenuSingleItem' ){
            var priceLabels = $('[id^="down_price"]');
            $.each(priceLabels, function () {
                this.style.display='block';
            });
            price.str("$","");
            setLastSelectedPrice('$'+price);
        }
    });
</script>
