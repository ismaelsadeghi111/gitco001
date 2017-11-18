<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%--
  Created by IntelliJ IDEA.
  User: hassan.abdi
  Date: Feb 7, 2012
  Time: 12:09:56 PM
--%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String context = request.getContextPath();
    int listOrder = 0;
    Integer counter = 0;
    List combinedTypesCaption = (List)request.getAttribute("combinedTypesCaption");
    Integer typeCount = 0;
    if (combinedTypesCaption != null)
        typeCount = combinedTypesCaption.size();    
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="main_menu">

    <%--Choose Now--%>
    <h6><bean:message key='label.page.category.choose.now'/></h6>
    <%--<br class="clear_float"/>--%>
    <bean:define id="cus_mod" value="<%=((Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)).toString()%>" />
        <div style="display:inline-block;">
            <c:choose>
                <c:when test="${cus_mod eq 0}">
                    <a id="buttonAddCombinedToBasket" class="add_to_cart" onclick="addCombinedItemInSessionToBasket(<%= typeCount %>);"><bean:message key='button.items.add.to.basket'/></a>
                    <a class="reset_cart" onclick="
                        selectSingleItem(null);
                        resetCaptionArray();
                        setCurrentCaption('combinedType0');
                        setLastCaptionElIdDefault();
                        resetCaption();
                    ">
                    <bean:message key="button.reset"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="reset_cart" style="width:110px;" onclick="
                    resetCaption();
                    ">
                    <bean:message key="button.reset"/>
                    </a>

                </c:otherwise>
            </c:choose>
            
        </div>

    <%--Caption List    --%>
    <ul>
        <bean:define id="singleBasketItemList" value="${sessionScope.basketCombinedSessionObj}" />
        <%
            BasketCombinedItem basketCombinedItem = (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
            Basket basket = (Basket)session.getAttribute(Constant.BASKET);
            boolean customizeIt = basket.findBasketItemById(basketCombinedItem.getIdentifier(), BasketCombinedItem.class) != null;
            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
            Integer basketCombinedSingleListCounter = 0;
        %>
        <c:set var="singleBasketItemList"  value="<%=basketCombinedItem.getBasketSingleItemList()%>" />

        <logic:iterate id="combinedTypeItem" name="combinedTypesCaption" offset="index">
            <c:set var="firstSingle" value="<%=basketCombinedItem.getBasketSingleItemList().get(basketCombinedSingleListCounter++)%>" />
            <script type="text/javascript">
                <c:if test="<%=counter.equals(0)%>">
                setBasketSingleItemIdentifier(<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter).getIdentifier()%>);
                </c:if>
            </script>


            <li onclick="combinedTypeItemOnClick(<%= listOrder %>);">
                <a id="combinedType<%= listOrder %>" href="javascript: void(0);" onclick="
                    if (isAnyTopSelected) {
                        zConfirm('<bean:message key="message.u.will.looz.ur.selected.tops.apply.them.be4.leave"/>', 'Confirm', 0,
                            function(button) {
                                if (button == 'Yes') {
                                    setToppingSelectedState(false);
                                    document.getElementById('apply_but').click();
                                    getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                                    selectCombinedTypeItem('combinedType<%= listOrder %>', <%= listOrder %>);
                                    setLastCaptionElId('defaultSingle<%=listOrder%>');
                                    setBasketSingleItemIdentifier('<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter).getIdentifier()%>');
                                    setCurrentCaption('combinedType<%= listOrder %>');
                                    getToppingsOrView();

                                    return true;
                                } else {
                                    setToppingSelectedState(false);
                                    getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                                    selectCombinedTypeItem('combinedType<%= listOrder %>', <%= listOrder %>);
                                    setLastCaptionElId('defaultSingle<%=listOrder%>');
                                    setBasketSingleItemIdentifier('<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter).getIdentifier()%>');
                                    setCurrentCaption('combinedType<%= listOrder %>');
                                    getToppingsOrView();

                                    return true;
                                }
                        });

                    } else {
                        getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                        selectCombinedTypeItem('combinedType<%= listOrder %>', <%= listOrder %>);
                        setLastCaptionElId('defaultSingle<%=listOrder%>');
                        setBasketSingleItemIdentifier('<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter++).getIdentifier()%>');
                        setCurrentCaption('combinedType<%= listOrder %>');
                        getToppingsOrView();
                    }
                    ">
                        ${combinedTypeItem}
                </a>
                <c:if test="<%= !customizeIt %>">
                    <label id="defaultSingle<%=listOrder%>">
                        <%=((BasketSingleItem)pageContext.getAttribute("firstSingle")).getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                    </label>
                    <h5 id="customizeLink"><bean:message key='label.customizeIt'/></h5>
                    <input type="hidden" id="defaultSingleId<%=listOrder%>" value="${firstSingle.id}"/>
                    <input type="hidden" id="defaultSingleGroupId<%=listOrder%>" value="${firstSingle.groupId}"/>
                </c:if>
                <c:if test="<%= customizeIt %>">
                    <%
                        BasketSingleItem basketSingleItem = basketSingleItemList.get(listOrder);
                        MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
                    %>
                    <label id="defaultSingle<%=listOrder%>"><%= menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)) %></label>
                    <h5 id="customizeLink"><bean:message key='label.customizeIt'/></h5>
                    <input type="hidden" id="defaultSingleId<%=listOrder%>" value="<%= menuSingleItem.getId() %>"/>
                    <input type="hidden" id="defaultSingleGroupId<%=listOrder%>"value="<%= menuSingleItem.getGroupId() %>"/>
                </c:if>

                <script type="text/javascript" language="JavaScript">
                    if ("<%= listOrder %>" == "0")
                        getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                </script>
                <% listOrder++; %>
            </li>
        </logic:iterate>
        <script type="text/javascript">
            initialCaptionArray(<%=listOrder%>);
        </script>
    </ul>
</div>
<script type="text/javascript">
    var el = document.getElementById("imgClearSelectedItems");
    if (el != null)
        el.title = '<bean:message key='button.delete.selected.items'/>';

</script>