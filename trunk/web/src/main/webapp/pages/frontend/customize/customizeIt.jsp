<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.common.MessageUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Feb 21, 2012
  Time: 10:25:58 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    Integer liCounter = 0;                                                            
    Integer xLiCounter = 0;
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
%>

<h4>
    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or
     !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
        <bean:message key="label.cutomize.your.food" />
    </c:if>
</h4>

<div class="block_content">


<div id="cust_app">

<div id="customize-top">
<img src="<%=context%><%= middlePath%>${requestScope.imageAddress}" width="650" height="330" class="img_border" style="float:left;"/>

<%--===================================================================================================================================================== START OF "CUSTOMIZE-TOP-CONTENT--%>
<div id = "customize-top-content">
<c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory}">
    <%pageContext.setAttribute("loadOtherOptions", "0");%>
    <!--loading selcted exclusive topping categories-->
    <c:if test="${!empty requestScope.selectedExlusiveToppingCategory}">
        <c:forEach items="${requestScope.selectedExlusiveToppingCategory}" var="exTopCat">
            <bean:define id="imageURL" name="exTopCat" type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
            <div >
                <table id="head_choise" style="width:240px; background: url(<%=context%>/img/<%=StringUtil.removeSpaces(imageURL.getName(Locale.ENGLISH))%>.png) 0 0 no-repeat;">
                    <tbody>
                    <tr style="padding-bottom:5%;">
                        <c:forEach items="${exTopCat.toppingSubCategoryList}" var="extTopSubCat">
                            <bean:define id="selecedStatus" value="FALSE"/>
                            <bean:define id="extTopSubCatObj" name="extTopSubCat" property="object" type="com.sefryek.doublepizza.model.Topping"/>

                            <c:forEach items="${exTopCat.selectedToppingMap}" var="selectedMap">
                                <c:if test="${selectedMap.key eq extTopSubCatObj.id}">
                                    <bean:define id="selecedStatus" value="TRUE"/>
                                </c:if>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${selecedStatus eq 'TRUE'}">
                                    <td style="float:left;" align="left">
                                        <input type="radio" value="item 1" name="${exTopCat.id}" onclick="document.getElementById('extHid_<%=xLiCounter%>').setAttribute('value', '${exTopCat.id}/${extTopSubCatObj.id}'); onToppingsItemClicked(this);" checked/>
                                        <c:set var="exSelectedItem" value="${extTopSubCatObj.id}"/>
                                        <label><%=(extTopSubCatObj).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%></label>
                                    </td>
                                </c:when>

                                <c:otherwise>
                                    <td style="float:left;"align="left">
                                        <input type="radio" value="item 1" name="${exTopCat.id}"  onclick="document.getElementById('extHid_<%=xLiCounter%>').setAttribute('value', '${exTopCat.id}/${extTopSubCatObj.id}'); onToppingsItemClicked(this);"/>
                                        <%=(extTopSubCatObj).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                    </td>
                                </c:otherwise>

                            </c:choose>

                        </c:forEach>
                        <input type="hidden" id="extHid_<%=xLiCounter++%>"  value="${exTopCat.id}/${exSelectedItem}"/>
                        <td style="width:100%; margin-top:-5px; color: #FFFFFF; float: right; text-align:right; font-family: Trebuchet MS; font-size: 18px; font-weight: bold; margin-right: 15px; text-shadow: 1px 1px 1px #3D7200; text-transform: none; text-transform:capitalize;" id="head_choise_th">
                                <%=(imageURL.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)))%>   <%-- Chook mode or crust--%>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:forEach>

    </c:if>
</c:if>
<%--*************--%>
<!--loading top price and add to cart, apply or reset buttons and ... -->
<c:if test="${!empty requestScope.price}">
    <div class="customize_header">
        <div id="bottom_item_price">
            <a style="display:inline-block;">
                <bean:message key="label.price"/>
            </a>
            <label style="display:inline-block; width:94px;" id="down_price">${requestScope.price} $</label>
        </div>
        <div id="top_app" style="width:246px;">
            <c:choose>
            <c:when test="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM) != (Constant.VALUE_NOT_CUSTOMIZING_BASKET_ITEM_MODE)%>">
                <c:choose>
                    <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">

                        <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                    or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                                        <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setToppingSelected('true');setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                                    </c:if>

                        <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
                                    and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">
                        </c:if>

                    </c:when>

                    <c:otherwise>
                        <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                    or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                                        <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingValue(); setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                                    </c:if>

                        <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
                                    and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">

                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:otherwise>
            <c:choose>
            <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                    or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                                        <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                                        <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory}">
                                        <br class="clear_float"/>
                                        <label><bean:message key="message.apply.toppings"/></label>
                                    </c:if>
                                    </c:if>

                <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
                                    and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">

                </c:if>

            </c:when>
            <c:otherwise>
                <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                    <a style="width: 106px;" href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                <a style="width: 130px;" href="javascript: void(0);"  title="<bean:message key="button.addToCart"/>" onclick="applyToppingValue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.addToCart"/></a>
                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                    or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                    <br class="clear_float"/>
                    <c:if test="${empty requestScope.selectedExlusiveToppingCategory}">
                    <label><bean:message key="message.add.single.to.cart"/><label>
                    </c:if>    

                    </c:if>
                    </c:if>

                <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
                                    and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory}">
                                        <a style="width: 130px;" href="javascript: void(0);"  title="<bean:message key="button.addToCart"/>" onclick="applyToppingValue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.addToCart"/></a>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
        </div>
    </div>
    <!--if there is a single item which doesn't have any topping show proper message-->
            <bean:define id="isSingle" value="<%=((Boolean)((FrontendAction.ClassTypeEnum)session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE)).equals(FrontendAction.ClassTypeEnum.SINGLEMENUITEM)).toString()%>"/>
            <c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
                                         and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory
                                         and isSingle eq 'true'}">
                <br class="clear">
                <div style="min-height:100px;" id="item-with-no-topping">
                    <bean:message key="message.item.with.no.topping"/>
                </div>
                <img src="<%=context%>/img/stamp.png" style="height:110px; vertical-align:bottom; width:240px;">
            </c:if>
    
</c:if>
<!--if there is a combined item or a category, so there is no topping for it and we should show a proper message-->
<bean:define id="isSingle" value="<%=((Boolean)((FrontendAction.ClassTypeEnum)session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE)).equals(FrontendAction.ClassTypeEnum.SINGLEMENUITEM)).toString()%>"/>
<c:if test="${empty requestScope.defaultExlusiveToppingCategory and empty requestScope.selectedExlusiveToppingCategory
            and empty requestScope.defaultItemToppingCategory and empty requestScope.selectedItemToppingCategory
            and isSingle eq 'false'}">
<%
    HttpSession httpSession = request.getSession();
    StringBuffer sb = new StringBuffer();
    String otherOptions = "";
    FrontendAction.ClassTypeEnum itemType = (FrontendAction.ClassTypeEnum) httpSession.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE);

    if (itemType.equals(FrontendAction.ClassTypeEnum.CATEGORY)) {

        sb = new StringBuffer();

        Category category = (Category) httpSession.getAttribute(Constant.LAST_SUB_CATEGORY);
        List<SubCategory> subCategories = category.getSubCategoryList();

        for (int i = 0; i < subCategories.size(); i++) {
            SubCategory subCategory = subCategories.get(i);
            String itemName;

            if (subCategory.getType().equals(MenuSingleItem.class)) {
                itemName = ((MenuSingleItem) subCategory.getObject()).getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

            } else if (subCategory.getType().equals(CombinedMenuItem.class)) {
                itemName = ((CombinedMenuItem) subCategory.getObject())
                        .getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

            } else if (subCategory.getType().equals(Category.class)) {
                itemName = ((Category) subCategory.getObject()).getName((Locale)session.getAttribute(Globals.LOCALE_KEY));

            } else {
                itemName = null;
            }

            if (itemName != null) {
                sb.append(itemName);
                if (i != subCategories.size() - 1 && i != subCategories.size() - 2)
                    sb.append(", ");

                else if (i == subCategories.size() - 2)

                    sb.append(" ").append(MessageUtil.get("message.and", (Locale)session.getAttribute(Globals.LOCALE_KEY))).append(" ");

            }
        }

        otherOptions = sb.toString();
    }
%>
<div style="display:block; float:right">
    <div id="selectedCombinedItem">
            <c:if test="<%=itemType.equals(FrontendAction.ClassTypeEnum.CATEGORY)%>">
                <bean:message key="message.choose.possible.option1"/> <span style="line-height: 25px; color:red;"><%=otherOptions%></span> <bean:message key="message.choose.possible.option2"/>
            </c:if>
    </div>
    <img src="<%=context%>/img/stamp.png" style="height:110px; vertical-align:bottom; width:240px;">
</div>
</div>
<%--========================================================================================================================================================== END OF "CUSTOMIZE-TOP-CONTENT--%>

</c:if>
</div>
</div>


<c:if test="${!empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
    <div style="width:103%;">
    <%
        int subDivIndex = 0;
        int[] columns = new int[3];
        columns[0] = 0;
        columns[1] = 0;
        columns[2] = 0;
    %>

        <c:forEach items="${requestScope.selectedItemToppingCategory}" begin="0" var="topcat" varStatus="topCatIndex">
        <bean:define id="toppingCategorytemp" name="topcat" type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
        <bean:define id="toppingCategoryIndex" name="topCatIndex" property="index" type="Integer"/>
            <c:if test="${!topCatIndex.last}">
                <%
            List list = toppingCategorytemp.getToppingSubCategoryList();
            if (list != null){
                int toppingCount = list.size();
                columns[toppingCategoryIndex % 3] += toppingCount + 2;

            }
        %>

            </c:if>

    </c:forEach>
        <%
            int min = 100000;
                for(int i = 0; i < columns.length; i++){
                    if  (columns[i] < min){
                        min = columns[i];
                        subDivIndex = i;
                    }
                }

            request.setAttribute("subDivIndex", new Integer(subDivIndex));
        %>
    <c:forEach var="i" begin="0" end="2" step="1" varStatus ="divIndex">
    <bean:define id="divIndexVar" name="divIndex" property="index" type="Integer"/>


    <div class="dir1" style="display:inline-block; width:32%;">
        <!--loading selected and default topping categories-->
        <c:if test="${!empty requestScope.selectedItemToppingCategory}">

            <c:forEach items="${requestScope.selectedItemToppingCategory}" begin="0" var="topcat" varStatus="topCatIndex">
                <bean:define id="toppingCategory" name="topcat" type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
                <c:if test="${topCatIndex.index ne topCatIndex.count}">
                    <c:if test="${(topCatIndex.index mod 3 eq divIndex.index and (!topCatIndex.last)) or (topCatIndex.last and requestScope.subDivIndex eq divIndex.index)}">

                    <div style="display:inline-block;">
                    <ul class="dir0">
                        <img alt="" id="cust_app_img" src="<%=context%>/img/titles/<%=(toppingCategory.getName(Locale.ENGLISH))%>.png" />
                        <span id="cust_app_span"><%=(toppingCategory.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)))%></span>
                    </ul>
                    <ul class="dir2">
                        <c:forEach items="${topcat.toppingSubCategoryList}" var="topSubCat" varStatus="subTopItemIndex">
                            <bean:define id="topSubCatObj" name="topSubCat" property="object" type="com.sefryek.doublepizza.model.Topping"/>
                            <bean:define id="topState" value="NULL"/>
                            <bean:define id="doesExist" value="FALSE"/>
                            <c:forEach items="${topcat.selectedToppingMap}" var="selectedTop">
                                <c:if test="${selectedTop.key eq topSubCatObj.id}">
                                    <bean:define id="doesExist" value="TRUE"/>
                                    <bean:define id="topState" value="${selectedTop.value}"/>
                                </c:if>
                            </c:forEach>
                            <%
                                String toppingCategoryName = toppingCategory.getName(Locale.ENGLISH);
                            %>
                            <c:choose>
                                <c:when test="${doesExist eq 'TRUE'}">
                                    <li>
                                        <div id="btn_ch">
                                            <c:if test="${topState eq 'full'}">
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('clear', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+3%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_full" style="display:block" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('left',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+2%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_clear" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('right', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+1%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_left"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('full',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+0%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_right" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/full" id="top_hid_<%=liCounter%>"/>

                                            </c:if>
                                            <c:if test="${topState eq 'right'}">
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('left', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+3%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'    class="btn_right" style="display:block" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('full',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+2%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_left"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('clear', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+1%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'   class="btn_full"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('right',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+0%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>' class="btn_clear" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/right" id="top_hid_<%=liCounter%>"/>

                                            </c:if>
                                            <c:if test="${topState eq 'left'}">
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('right', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+3%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'   class="btn_left" style="display:block" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('full',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+2%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>' class="btn_right" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('clear', 'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+1%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_full"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('left',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+0%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice();  onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>' class="btn_clear" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/left" id="top_hid_<%=liCounter%>"/>

                                            </c:if>
                                                <span>
                                                    <%=(topSubCatObj).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                                </span>
                                        </div>
                                    </li>

                                </c:when>
                                <c:otherwise>
                                    <div>
                                        <li>
                                            <div id="btn_ch" >
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('full', 'ab_<%=liCounter%>', ${requestScope.isPortion});   document.getElementById('top_hid_<%=liCounter+3%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_clear" style="display:block" onclick="setToppingSelectedState(true);"/>
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('left',  'ab_<%=liCounter%>', ${requestScope.isPortion});  document.getElementById('top_hid_<%=liCounter+2%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);  getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"   id='<%=liCounter%>'  class="btn_full"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('right', 'ab_<%=liCounter%>', ${requestScope.isPortion});  document.getElementById('top_hid_<%=liCounter+1%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"    id='<%=liCounter%>'  class="btn_left"  />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="button"  onClick="if (allowSelectMoreToppings('<%=++liCounter%>')) {var res = chan('clear',  'ab_<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter+0%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res); getNewPrice(); onToppingsItemClicked(this);} setToppingSelectedState(true);"    id='<%=liCounter%>'  class="btn_right" />
                                                <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/clear" id="top_hid_<%=liCounter%>"/>

                                                    <span>
                                                        <%=(topSubCatObj).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                                    </span>
                                            </div>
                                        </li>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                    <ul class="dir3">

                    </ul>
                </div>
                </c:if>
                </c:if>

            </c:forEach>
        </c:if>
    </div>
        </c:forEach>
    </div>

</c:if>

<!--loading bottom price and add to cart, apply or reset buttons-->
<c:if test="${!empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
        <c:if test="${!empty requestScope.price}">
        <br class="clear_float"/>
        <div class="customize_header" style="margin-top:15px; display:inline-block;">
            <div id="bottom_item_price">
                <a style="display:inline-block;">
                    <bean:message key="label.price"/>
                </a>
                <label style="display:inline-block;width:94px;" id="down_price">${requestScope.price} $</label>
            </div>

            <div id="top_app">
                <c:choose>
                        <c:when test="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM) != (Constant.VALUE_NOT_CUSTOMIZING_BASKET_ITEM_MODE)%>">
                            <c:choose>
                                <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                    or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle);"><bean:message key="button.reset"/></a>
                                        <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setToppingSelected('true');setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                                    </c:if>
                                </c:when>

                                <c:otherwise>
                                    <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                                    <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingValue();setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false); "><bean:message key="button.apply"/></a>

                                </c:otherwise>
                            </c:choose>
                        </c:when>

                        <c:otherwise>
                            <c:choose>
                                <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle);"><bean:message key="button.reset"/></a>
                                        <a id="apply_but" href="javascript: void(0);"  title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setToppingSelected('true');setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                                </c:when>
                                <c:otherwise>
                                        <a href="javascript: void(0);"  title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                                        <a href="javascript: void(0);"  title="<bean:message key="button.addToCart"/>" onclick="applyToppingValue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.addToCart"/></a>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
            </div>
        </div>
    </c:if>
</c:if>
</div>

