<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.BasketCombinedItem" %>
<%@ page import="com.sefryek.doublepizza.model.BasketSingleItem" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
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
    request.setAttribute("listOrder", listOrder);
    List combinedTypesCaption = (List)request.getAttribute("combinedTypesCaption");
    BasketCombinedItem basketCombinedItem = (BasketCombinedItem) session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
    List<BasketSingleItem> basketSingleItems = basketCombinedItem != null ? basketCombinedItem.getBasketSingleItemList() : null;
    Integer customizingMod = (Integer) session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM);
    Integer typeCount = 0;
    if (combinedTypesCaption != null)
        typeCount = combinedTypesCaption.size();
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="main_menu" >
    <%--<div class="for_1_pizza_left">--%>
    <bean:define id="cus_mod" value="<%=((Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)).toString()%>" />
    <a id="buttonAddCombinedToBasket" href="javascript:void(0);" onclick="addCombinedItemInSessionToBasket(<%= typeCount %>);"/>
    <ul id="packageList" >
        <bean:define id="singleBasketItemList" value="${sessionScope.basketCombinedSessionObj}" />
        <%
            basketCombinedItem = (BasketCombinedItem)session.getAttribute(Constant.BASKET_COMBINED_IN_SESSION);
            Basket basket = (Basket)session.getAttribute(Constant.BASKET);
            boolean customizeIt = basket.findBasketItemById(basketCombinedItem.getIdentifier(), BasketCombinedItem.class) != null;
            List<BasketSingleItem> basketSingleItemList = basketCombinedItem.getBasketSingleItemList();
            Integer basketCombinedSingleListCounter = 0;
        %>
        <c:set var="singleBasketItemList"  value="<%=basketCombinedItem.getBasketSingleItemList()%>" scope="page"/>

        <logic:iterate id="combinedTypeItem" name="combinedTypesCaption" offset="index">
            <c:set var="firstSingle" value="<%=basketCombinedItem.getBasketSingleItemList().get(basketCombinedSingleListCounter++)%>" />
            <script type="text/javascript">
                <c:if test="<%=counter.equals(0)%>">
                setBasketSingleItemIdentifier(<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter).getIdentifier()%>);
                </c:if>
            </script>


            <%if(listOrder==0){%><li class="left_box active" onclick="setAsActiveItem(this); combinedTypeItemOnClick(<%= listOrder %>);" ><%}
            else
            {
            %>
            <li class="left_box" onclick="setAsActiveItem(this); combinedTypeItemOnClick(<%= listOrder %>);" >
            <% }%>
                <div>
                    <a id="combinedType_<%= listOrder %>" href="javascript: void(0);" onclick="

                            var altName= document.getElementById('defaultSingle<%=listOrder%>');
                            setAlternative(altName.innerHTML);
                            $('#loadingimg').show();
                            if (isAnyTopSelected) {
                        <%--setAsActiveItem('combinedType_<%= listOrder %>');--%>
                            setToppingSelectedState(false);
                            document.getElementById('apply_but').click();
                            getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                            selectCombinedTypeItem('combinedType<%= listOrder %>', <%= listOrder %>);
                            setLastCaptionElId('defaultSingle<%=listOrder%>');
                            setBasketSingleItemIdentifier('<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter).getIdentifier()%>');
                            setCurrentCaption('combinedType<%= listOrder %>');
//                            getToppingsOrView();
                            getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');

                            return true;

                            } else {
                        <%--setAsActiveItem('combinedType_<%= listOrder %>');--%>
                            getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                            selectCombinedTypeItem('combinedType<%= listOrder %>', <%= listOrder %>);
                            setLastCaptionElId('defaultSingle<%=listOrder%>');
                            setBasketSingleItemIdentifier('<%=((List<BasketSingleItem>)pageContext.getAttribute("singleBasketItemList")).get(counter++).getIdentifier()%>');
                            setCurrentCaption('combinedType<%= listOrder %>');
//                            getToppingsOrView();
                            getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');
                            }
                            var basketDetails= document.getElementById('basketDetails');
//                            alert(basketDetails.childNodes.length);
                            ">
                        <strong class="title"  id="combinedItem<%= listOrder %>">${combinedTypeItem}</strong>
                        <%--<strong class="title">${combinedTypeItem.g}</strong>--%>

                    </a>


                            <c:if test="<%= !customizeIt %>">
                                <label id="defaultSingle<%=listOrder%>"  class="des">
                                    <%=((BasketSingleItem)pageContext.getAttribute("firstSingle")).getSingle().getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                                </label>
                                <label id="defaultSinglePrice<%=listOrder%>"  class="red" style="font-size: 16px">
                                    <%=((BasketSingleItem)pageContext.getAttribute("firstSingle")).getSingle().getFormattedPrice()%>
                                </label>
                                <%--<h5 id="customizeLink"><bean:message key='label.customizeIt'/></h5>--%>
                                <input type="hidden" id="defaultSingleId<%=listOrder%>" value="${firstSingle.id}"/>
                                <input type="hidden" id="defaultSingleGroupId<%=listOrder%>" value="${firstSingle.groupId}"/>
                            </c:if>
                            <c:if test="<%= customizeIt %>">
                                <%
                                    BasketSingleItem basketSingleItem = basketSingleItemList.get(listOrder);
                                    MenuSingleItem menuSingleItem = basketSingleItem.getSingle();
                                %>
                                <label id="defaultSingle<%=listOrder%>"  class="des">
                                    <%= menuSingleItem.getName((Locale)session.getAttribute(Globals.LOCALE_KEY)) %>
                                </label>
                                <label id="defaultSinglePrice<%=listOrder%>"  class="red" style="font-size: 16px">
                                    <%=((BasketSingleItem)pageContext.getAttribute("firstSingle")).getSingle().getFormattedPrice()%>
                                </label>
                                <%--<h5 id="customizeLink"><bean:message key='label.customizeIt'/></h5>--%>
                                <input type="hidden" id="defaultSingleId<%=listOrder%>" value="<%= menuSingleItem.getId() %>"/>
                                <input type="hidden" id="defaultSingleGroupId<%=listOrder%>"value="<%= menuSingleItem.getGroupId() %>"/>
                            </c:if>

                    <script type="text/javascript" language="JavaScript">
                        if ("<%= listOrder %>" == "0")
                            getSingleMenuItemsForType(${combinedProductNo},${combinedGroupId},<%= listOrder %>);
                    </script>
                    <% listOrder++;
                        request.setAttribute("listOrder", listOrder);
                    %>

                        <%--</div> &lt;%&ndash;end of div class="left_box"&ndash;%&gt;--%>
                </div>    <%--end of "left_box"--%>
            </li>
        </logic:iterate>
        <script type="text/javascript">
            initialCaptionArray(<%=listOrder%>);
            setlastSelectedCombinedIndex('<%=listOrder%>');
        </script>
    </ul>
    <%--</div>--%>




</div>
<script type="text/javascript">
    $(document).ready(function(){
        var priceLabels = $('[id^="down_price"]');
        $.each(priceLabels, function () {
            this.style.display='block';
        });
        var price=getCombinedItemsPrice();
        price.str("$","");
        setLastSelectedPrice('$'+price);
    });
</script>