<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.common.MessageUtil" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt" %>
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
    Integer defaultoppCounter = 0;
    Integer xLiCounter = 0;
    Double price = (Double) request.getAttribute("price");
    String itemName = (String) request.getAttribute("itemName");
    List<ToppingCategoryAlt> selectedItemToppingCategory = (List<ToppingCategoryAlt>) request.getAttribute("selectedItemToppingCategory");
    List<ToppingCategoryAlt> selectedExlusiveToppingCategory = (List<ToppingCategoryAlt>) request.getAttribute("selectedExlusiveToppingCategory");
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    Category lastCategory = (Category)(request.getSession().getAttribute("lastCategory"));
    Integer maxTopping=5;

    Object obj=   lastCategory.getSubCategoryList().get(0).getObject();

//    instanceof MenuSingleItem
    if (obj instanceof CombinedMenuItem) {
        CombinedMenuItem combinedMenuItem = (CombinedMenuItem) lastCategory.getSubCategoryList().get(0).getObject();
        if (combinedMenuItem != null) {
            if (combinedMenuItem.getProductNo().equalsIgnoreCase("690")) {
                maxTopping = 20;
            }
        }
    }

%>

<%--<div id="nonExTopping">--%>
<c:if test="${!empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
    <div style="display: inline-block;">
    <div style="margin:0px 0px 20px">
            <%--<label class="cusfood"> <bean:message key="label.food.topping"/> </label>--%>
    </div>
    <%
        int subDivIndex = 0;
        int[] columns = new int[3];
        columns[0] = 0;
        columns[1] = 0;
        columns[2] = 0;
    %>

    <c:forEach items="${requestScope.selectedItemToppingCategory}" begin="0" var="topcat" varStatus="topCatIndex">
        <bean:define id="toppingCategorytemp" name="topcat"
                     type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
        <bean:define id="toppingCategoryIndex" name="topCatIndex" property="index" type="Integer"/>
        <c:if test="${!topCatIndex.last}">
            <%
                List list = toppingCategorytemp.getToppingSubCategoryList();
                if (list != null) {
                    int toppingCount = list.size();
                    columns[toppingCategoryIndex % 3] += toppingCount + 2;

                }
            %>

        </c:if>

    </c:forEach>
    <%
        int min = 100000;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] < min) {
                min = columns[i];
                subDivIndex = i;
            }
        }

        request.setAttribute("subDivIndex", new Integer(subDivIndex));
    %>
    <c:forEach var="i" begin="0" end="2" step="1" varStatus="divIndex">
        <bean:define id="divIndexVar" name="divIndex" property="index" type="Integer"/>
        <!--loading selected and default topping categories-->
        <c:if test="${!empty requestScope.selectedItemToppingCategory}">

            <c:forEach items="${requestScope.selectedItemToppingCategory}" begin="0" var="topcat"
                       varStatus="topCatIndex">
                <bean:define id="toppingCategory" name="topcat"
                             type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
                <c:if test="${topCatIndex.index ne topCatIndex.count}">
                    <c:if test="${(topCatIndex.index mod 3 eq divIndex.index and (!topCatIndex.last)) or (topCatIndex.last and requestScope.subDivIndex eq divIndex.index)}">
                        <div class="menu-block">
                            <div class="menu-title">
                                <div class="icon">
                                    <img alt="" id="cust_app_img"
                                         src="<%=context%>/img/titles/<%=(toppingCategory.getName(Locale.ENGLISH))%>.png"
                                         width="20px" height="20px"/>
                                </div>
                                    <span class="name"
                                          id="cust_app_span"><%=(toppingCategory.getName((Locale) session.getAttribute(Globals.LOCALE_KEY)))%></span>
                            </div>

                            <div class="menu-middle-box">
                                <ul id="toppingList">
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
                                        <li>
                                            <c:choose>
                                                <c:when test="${doesExist eq 'TRUE'}">
                                                    <c:if test="${topState eq 'full'}">
                                                        <script type="text/javascript" language="javascript">
                                                            setTopping('<%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>');
                                                        </script>

                                                        <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="checkbox"  onClick="
                                                                if((this.checked) && (getToppingCountselect()<=getMaxTopping()))
                                                                {setTopping('<%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>');

                                                                }
                                                                else
                                                                {if(getToppingCountselect()<getMaxTopping()){removeTopping('<%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>');}}
                                                                setSelectedToppingsCount('1');
                                                                if (allowSelectMoreToppings('toppId<%=liCounter%>'))
                                                                {var res = chan2('toppId<%=liCounter%>', ${requestScope.isPortion}); document.getElementById('top_hid_<%=liCounter%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);
                                                                getNewPrice();
                                                                onToppingsItemClicked(true);}
                                                                setToppingSelectedState(true);
                                                                "

                                                               id='toppId<%=liCounter%>' class="btn_full" checked/>
                                                        <label class="css-label" for="toppId<%=liCounter%>">
                                                            <%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                                        </label>
                                                        <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/full" id="top_hid_<%=liCounter%>"/>
                                                    </c:if>
                                                 </c:when>
                                                <c:otherwise>
                                                    <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="checkbox"  onClick="
                                                            if((this.checked) && (getToppingCountselect()<=getMaxTopping())) {setTopping('<%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>');}
                                                            else{if(getToppingCountselect()<getMaxTopping()){removeTopping('<%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>');}}
                                                            if (allowSelectMoreToppings('toppId<%=liCounter%>')) {var res = chan2('toppId<%=liCounter%>', ${requestScope.isPortion});
                                                            document.getElementById('top_hid_<%=liCounter%>').setAttribute('value', '${topcat.id}/${topSubCatObj.id}/' + res);
                                                            getNewPrice();
                                                            onToppingsItemClicked(true);}
                                                            setToppingSelectedState(true);
                                                            "
                                                           id='toppId<%=liCounter%>' class="btn_clear" />      <%-- undefault-topping--%>
                                                    <label class="css-label" for="toppId<%=liCounter%>">
                                                        <%=(topSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                                    </label>
                                                    <input countable="<%= InMemoryData.toppingIsCountable(toppingCategoryName) %>"  type="hidden" name="top_hid_vals" value="${topcat.id}/${topSubCatObj.id}/clear" id="top_hid_<%=liCounter%>"/>
                                                </c:otherwise>

                                            </c:choose>
                                        </li>
                                       <%liCounter++;%>
                                    </c:forEach>
                                </ul>
                                <ul id="dir3">

                                </ul>
                            </div>
                        </div>
                    </c:if>
                </c:if>

            </c:forEach>
        </c:if>
    </c:forEach>
</c:if>
<%--======== how cooked ="Exclusive Topping"--%>
<div id="howcooktopping">
    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory}">

        <table style="margin:20px 0 0 0; float:left;" width="100%" border="0" cellspacing="0" cellpadding="0">

            <%pageContext.setAttribute("loadOtherOptions", "0");%>
            <c:if test="${!empty requestScope.selectedExlusiveToppingCategory}">

                <tr id="tr1">
                    <c:forEach items="${requestScope.selectedExlusiveToppingCategory}" var="exTopCat"
                               varStatus="exTopCatStatus">
                        <bean:define id="imageURL" name="exTopCat"
                                     type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>
                        <%-- CookMode or Crust --%>
                        <td width="49%" class="how-cooked">
                            <%=(imageURL.getName((Locale) session.getAttribute(Globals.LOCALE_KEY)))%>  <%-- Chook mode or crust--%>
                        </td>
                        <c:if test="${!exTopCatStatus.last}">
                            <td width="2%">&nbsp;</td>
                        </c:if>
                    </c:forEach>

                </tr>
                <tr id="tr2">
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

                <tr id="tr3">

                        <%--===============--%>
                    <c:forEach items="${requestScope.selectedExlusiveToppingCategory}" var="exTopCat"
                               varStatus="exTopCatStatus">
                        <bean:define id="imageURL" name="exTopCat"
                                     type="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt"/>

                        <%--==============--%>
                        <td>
                            <table class="how-cooked-box" width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <c:forEach items="${exTopCat.toppingSubCategoryList}" var="extTopSubCat">

                                        <bean:define id="selecedStatus" value="FALSE"/>
                                        <bean:define id="extTopSubCatObj" name="extTopSubCat" property="object"
                                                     type="com.sefryek.doublepizza.model.Topping"/>

                                        <c:forEach items="${exTopCat.selectedToppingMap}" var="selectedMap">
                                            <c:if test="${selectedMap.key eq extTopSubCatObj.id}">
                                                <bean:define id="selecedStatus" value="TRUE"/>
                                            </c:if>
                                        </c:forEach>
                                        <td><label class="button">
                                            <c:choose>
                                                <c:when test="${selecedStatus eq 'TRUE'}">
                                                    <input type="radio" value="item 1" name="button${exTopCat.id}"
                                                           onclick="onToppingsItemClicked(true);setToppingSelectedState(true);document.getElementById('extHid_<%=xLiCounter%>').setAttribute('value', '${exTopCat.id}/${extTopSubCatObj.id}'); onToppingsItemClicked(this);"
                                                           checked/>
                                                    <span class="outer"><span class="inner"></span></span>
                                                    <c:set var="exSelectedItem" value="${extTopSubCatObj.id}"/>
                                                    <%=(extTopSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="radio" value="item 1" name="button${exTopCat.id}"
                                                           onclick="onToppingsItemClicked(true);setToppingSelectedState(true);document.getElementById('extHid_<%=xLiCounter%>').setAttribute('value', '${exTopCat.id}/${extTopSubCatObj.id}'); onToppingsItemClicked(this);"/>
                                                    <span class="outer"><span class="inner"></span></span>
                                                    <%=(extTopSubCatObj).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                                </c:otherwise>
                                            </c:choose>
                                        </label></td>
                                    </c:forEach>
                                    <input type="hidden" id="extHid_<%=xLiCounter++%>"
                                           value="${exTopCat.id}/${exSelectedItem}"/>
                                </tr>
                            </table>

                        </td>
                        <%--==============--%>
                        <c:if test="${!exTopCatStatus.last}">
                            <td width="2%">&nbsp;</td>
                        </c:if>
                    </c:forEach>

                </tr>
                <tr id="tr4">
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>

            </c:if>

        </table>

    </c:if>
</div>
<%--</div>--%>

<%--=========================Default and selectedTopping (nonExclusiveTopping)  --%>

<%--============================================== apply button--%>
<%
    Object lastSelectedItem = null;
    if (request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) != null &&
            request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) instanceof CombinedMenuItem) {
        lastSelectedItem = (CombinedMenuItem) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM);
    } else if (request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) != null &&
            request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM) instanceof MenuSingleItem) {
        lastSelectedItem = (MenuSingleItem) request.getSession().getAttribute(Constant.LAST_SELECTED_ITEM);
    }
%>


<c:if test="${!empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
    <c:if test="${!empty requestScope.price}">

        <table id="cut-footer-detail" class="borderTop Fullbasket-tilte font2" width="100%" border="0" cellspacing="5" cellpadding="0" style="display: none">
            <tr>

                <td width="55%">
                    <label class="foodtitle">
                        <c:choose>
                            <c:when test="<%=lastSelectedItem != null%>">
                                <%=(lastSelectedItem != null && lastSelectedItem instanceof CombinedMenuItem) ? ((CombinedMenuItem) lastSelectedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)) :
                                        (lastSelectedItem != null && lastSelectedItem instanceof MenuSingleItem) ? ((MenuSingleItem) lastSelectedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)) : ""%>
                            </c:when>
                            <c:otherwise>
                                <%=lastCategory.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                            </c:otherwise>
                        </c:choose>
                        &raquo;
                    </label>
                    <label id="down_price" class="price"><bean:message key="label.price"/> $<c:choose><c:when
                            test="${not empty requestScope.price}">${requestScope.price}</c:when><c:otherwise><%=price%>
                    </c:otherwise></c:choose></label>

                </td>

                <td width="1%">
                        <%-- <c:choose>
                         <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">--%>
                    <c:choose>
                    <c:when test="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM) != (Constant.VALUE_NOT_CUSTOMIZING_BASKET_ITEM_MODE)%>">
                    <c:choose>
                    <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory
                                                or !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">
                <td width="1%"></td>
                <td width="1%">
                <td width="15%">
                    <a id="apply_but" class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setToppingSelected('true');setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                </td>
                <td width="1%">
                <td width="15%">
                    <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle);"><bean:message key="button.reset"/></a>
                </td>
                <td width="1%"></td>
                </c:if>
                </c:when>
                <c:otherwise>
                    <td width="1%"></td>
                    <td width="1%">
                    <td width="15%">
                        <a id="apply_but" class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.apply"/>" onclick="applyToppingValue();setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false); "><bean:message key="button.apply"/></a>
                    </td>
                    <td width="1%">
                    <td width="15%">
                        <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                    </td>
                    <td width="1%"></td>
                </c:otherwise>
                </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="<%=(((session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE))).equals(FrontendAction.ClassTypeEnum.COMBINED))%>">
                            <td width="30%">
                                <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.addToCart"/>" onclick="addAlert();document.getElementById('buttonAddCombinedToBasket').onclick();"><bean:message key="button.addToCart"/></a>
                            </td>
                            <td width="1%">
                            <td width="15%">
                                <a class="btn-first FloatRight" id="apply_but" href="javascript: void(0);" title="<bean:message key="button.apply"/>" onclick="applyToppingsOnSession(null); setToppingSelected('true');setCurrentCaptionStatusTrue(); getBasketItems(); setToppingSelectedState(false);"><bean:message key="button.apply"/></a>
                            </td>
                            <td width="1%">
                            <td width="15%">
                                <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.reset"/>" onclick="resetBasketItemToppings(lastSelectedBasketSingle);"><bean:message key="button.reset"/></a>
                            </td>
                            <td width="1%"></td>
                        </c:when>
                        <c:otherwise>
                            <td width="30%">
                                <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.addToCart"/>" onclick="addAlert();setToppingSelectedState(false);applyToppingValue();"><bean:message key="button.addToCart"/></a>
                            </td>
                            <td width="1%">
                            <td width="1%"></td>
                            <td width="1%">
                            <td width="15%">
                                <a class="btn-first FloatRight" href="javascript: void(0);" title="<bean:message key="button.reset"/>" onclick="getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM'); setToppingSelectedState(false);"><bean:message key="button.reset"/></a>
                            </td>
                            <td width="1%"></td>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
                </c:choose>
            </tr>
        </table>
    </c:if>
</c:if>




<%--end of 2-first middlerWare--%>
<script>
    function getToppingCountselect(){
        toppingCountselect = $("#toppingList li").find(':checkbox:checked[countable="true"]');
        return toppingCountselect.length;
    }
    function getMaxTopping(){
        maxTopping=<%=maxTopping%>
        return maxTopping
    }

</script>