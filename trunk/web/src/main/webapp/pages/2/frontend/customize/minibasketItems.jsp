<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.BasketCombinedItemHelper" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%
        Integer editButNum = 0;
        BasketSingleItem expensiveItem = null;
        BigDecimal basketTotalPrice = (BigDecimal)request.getAttribute("basketTotalPrice");
        Basket basket = (Basket)request.getSession().getAttribute(Constant.BASKET);
        String context = request.getContextPath();
        List<BasketItem> basketItemsList= (List<BasketItem>) request.getAttribute("basketItemsList");
        String totalItems = basketItemsList != null ? String.valueOf( basketItemsList.size()) : "0";
        String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
        request.getSession().setAttribute("totalItems", totalItems);
        request.getSession().setAttribute("totalPrice", CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(basketTotalPrice)));
    %>
        <%--<script type="text/javascript">
            updateBasketItemCounts('<%=((Basket) session.getAttribute(Constant.BASKET)).getNumberOfItems()%>');
            updateBasketTotalPrice('<%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(((Basket) request.getSession().getAttribute(Constant.BASKET)).calculateTotalPrice()))%>');
        </script>
--%>
           <%--===================================================================================================================================================--%>
<%--<c:if test='<%=((List)request.getAttribute("basketItemsList")).size() != 0%>'>--%>
  <div class="DivWithScroll">
    <div class="light-arrwo"></div>

    <div class="title">
    <label>
        <span class="title" style="width: 87%" id="btCount"> <bean:message key="minibasket.label.cardProducts"/>(<%=totalItems%>) </span><span id="btPrice"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(basketTotalPrice))%></span>
        <input type="hidden" name="totalItems" value="<%=totalItems%>">
        <input type="hidden" name="totalPrice" value="<%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(basketTotalPrice))%>">
    </label>
    </div>
    <c:forEach items="${requestScope.basketItemsList}" begin="0" var="basketItem" varStatus="cuurIndex1">
        <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketCombinedItem.class) %>'>
            <bean:define id="basketCombinedItem" type="com.sefryek.doublepizza.model.BasketCombinedItem" name="basketItem" property="object"/>
            <bean:define id="combinedMenuItem" type="com.sefryek.doublepizza.model.CombinedMenuItem" name="basketCombinedItem" property="combined"/>
            <% Category parentCategory = InMemoryData.getCombinedParentCategory(combinedMenuItem); %>
            <%-- ===================== --%>

            <div class="lightBox-inner">
                <div class="ImageBox"><img width="85px" height="85px" style="float: left" src="<%=context%><%= middlepath%><%=((CombinedMenuItem)pageContext.getAttribute("combinedMenuItem")).getImageURl()%>"
                        alt="Double Pizza"/>
                </div>
                <div class="light-des">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td  width="89%" colspan="0" align="left" valign="top" height="25" style="color: #a40308; font: font-family:'ItcKabelMedium',Arial;font-size: 18px;

                            ">
                                 <b><%=StringUtil.shortify(((CombinedMenuItem) pageContext.getAttribute("combinedMenuItem")).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)), Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE)%></b>
                            </td>
                            <td width="4%" align="left" valign="top">
                                <a href="javascript: void(0);"
                                   onclick="removeItemFromBasket(${basketCombinedItem.identifier}, 'COMBINED');reloadMiniBasket('<%=totalItems%>');"><img src="<%=context%>/images/close.png" alt="Close"/></a>
                                <%--<a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit"
                                   onclick="setBasketSingleItemIdentifier('---1');
                                        setIdAndGroupId(null, null);
                                        editBasketCombined(${basketCombinedItem.identifier},'<%= parentCategory.getId()%>', '${basketCombinedItem.productNoRef}', '${basketCombinedItem.groupIdRef}') ;
                                        setCustomizedBasketItemId(${basketItem.identifier});
                                        isInCustomizingMode = true;
                                        ">--%>
<%--                                <a id="edit_but_<%=editButNum%>"
                                   href="javascript: void(0);"
                                   class="product_link"
                                   onclick="setBasketSingleItemIdentifier('${basketCombinedItem.identifier}');
                                            setCustomizedBasketItemId('${basketItem.identifier}');
                                           gotoMiniBasketCustomizePage('combinedId', 'COMBINED', '<%=parentCategory.getId()%>','${basketCombinedItem.productNoRef}', '${basketCombinedItem.groupIdRef}', '${basketCombinedItem.identifier}', 'edit_but<%=editButNum++%>');
//                                           onCustomizing();
                                           ">

                                <img src="<%=context%>/images/edit.png" alt="Edit"/>
                                </a>--%>
                            </td>
                        </tr>
                        <c:forEach items="${basketCombinedItem.basketSingleItemList}" begin="0" var="basketSingleItem" varStatus="cuurIndex2">
                           <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>

                    <%--=====================--%>
                        <tr>
                            <% String singleName = ((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)); %>
                            <td  width="30%" style="font: 14px/25px arial;" title="<%= singleName %>">
                               <%= StringUtil.shortify(singleName, Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE) %>
                            </td>
                            <% BasketSingleItem basketSingleItemCur = (BasketSingleItem)pageContext.getAttribute("basketSingleItem"); %>
                            <td width="26%" align="right" valign="top" style="font: 14px/25px arial">
                             <b> <%= CurrencyUtils.toMoney((new BasketCombinedItemHelper().getInvoicePrice((BasketCombinedItem)((BasketItem)pageContext.getAttribute("basketItem")).getObject(), basketSingleItemCur))) %></b>
                            </td>
                        </tr>
                         <%--=====================--%>

                            <%
                                String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,null, false, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                exclusiveText = exclusiveText.replace("<context>", context);
                                exclusiveText = exclusiveText.replace("<img src=\"/img/et-CRUST.png\" class=\"img_topping\">", " ");
                                exclusiveText = exclusiveText.replace("<img src=\"/img/et-COOKINGMODE.png\" class=\"img_topping\">", " ");
                                String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                if (!exclusiveText.isEmpty() || !fullText.isEmpty() || !leftText.isEmpty() || !rightText.isEmpty()){
                            %>
                        <tr>


                            <td width="60%" align="left" valign="top" height="30" style="font: 12px/25px arial; padding-left: 5px; color: gray">

                                        <c:if test="<%= exclusiveText.length() > 0 %>">
                                            <label class="toppingText" ><%= exclusiveText %></label>
                                        </c:if>
                                        <c:if test="<%= fullText.length() > 0 %>">
                                           <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                        </c:if>
                                        <c:if test="<%= leftText.length() > 0 %>">
                                           <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                        </c:if>
                                        <c:if test="<%= rightText.length() > 0 %>">
                                          <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                        </c:if>

                            </td>

                        </tr>
                            <% } %>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <%--=============================--%>
        </c:if>

        <%--===================================================================================== Single Menu Items --%>
        <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketSingleItem.class) %>'>
            <bean:define id="basketSingleItem" type="com.sefryek.doublepizza.model.BasketSingleItem" name="basketItem" property="object"/>
            <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>
            <%
                Category parentCategory = InMemoryData.getSingleParentCategory(menuSingleItem);
                Category parentSubCategory = null;
                if (parentCategory == null){
                    parentSubCategory = InMemoryData.getSingleParentSubCategory(menuSingleItem);
                    parentCategory = InMemoryData.getSubCategoryParentCategory(parentSubCategory);
                }
            %>
            <%-- ===================== --%>
            <% MenuSingleItem singleItem= ((MenuSingleItem)pageContext.getAttribute("menuSingleItem"));%>

            <div class="lightBox-inner">
                <div class="ImageBox">
                    <%--<img  width="85px" height="85px" src="<%=context%><%= middlepath%><%=((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getImageURl()%>" alt="Double Pizza"/>--%>
                    <img  width="85px" height="85px" src="<%=context%><%=middlepath%><%=singleItem.getImageURL()%>" alt="Double Pizza"/>
                </div>
                <div class="light-des">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <% String singleName = ((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)); %>
                            <%--=============================--%>
                            <td colspan="2" align="left" valign="top" height="25" style="color: #a40308; font: 14px/25px arial">
                                <b><%= StringUtil.shortify(singleName, Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE) %></b>

                            </td>

                            <td width="10%" align="left" valign="top">
                                <a href="javascript: void(0);" onclick="removeItemFromBasket(${basketSingleItem.identifier}, 'SINGLEMENUITEM');">
                                    <img src="<%=context%>/images/close.png" alt="Close"/>
                                </a>
                               <%-- <c:choose>
                                    <c:when test="<%= parentCategory != null & parentSubCategory == null %>">
                                        <a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit"
                                           onclick="setBasketSingleItemIdentifier('---2');
                                                setIdAndGroupId(null, null);
                                                editBasketSingle(${basketSingleItem.identifier},'<%= parentCategory.getId()%>', '${basketSingleItem.id}', '${basketSingleItem.groupId}') ;
                                                setCustomizedBasketItemId(${basketItem.identifier});
                                                ">
                                            <img src="<%=context%>/images/edit.png"  alt="Edit" />
                                        </a>

                                    </c:when>
                                    <c:otherwise>
                                        &lt;%&ndash;customizeMiniBasketItem('singleId', 'SINGLEMENUITEM',  null,'${basketSingleItem.id}', '${basketSingleItem.groupId}', 'edit_but<%=editButNum++%>');&ndash;%&gt;
                                        <a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit"
                                           onclick="setBasketSingleItemIdentifier('---');
                                                setIdAndGroupId(null, null);
                                                editBasketSingleInSubCategory(${basketSingleItem.identifier},'<%= parentCategory.getId()%>', '<%= parentSubCategory.getId()%>', '${basketSingleItem.id}', '${basketSingleItem.groupId}');
                                                setCustomizedBasketItemId(${basketItem.identifier});
                                                ">
                                            <img src="<%=context%>/images/edit.png"  alt="Edit" />
                                        </a>

                                    </c:otherwise>
                                </c:choose>--%>
<%--                                <a id="edit_but_<%=editButNum%>"
                                   href="javascript: void(0);"
                                   class="product_link"
                                   onclick="setCustomizedBasketItemId(${basketItem.identifier});
                                           gotoMiniBasketCustomizePage('singleId', 'SINGLEMENUITEM', '<%= parentCategory.getId()%>', '${basketSingleItem.id}', '${basketSingleItem.groupId}','${basketSingleItem.identifier}', 'edit_but<%=editButNum%>');
//                                           onCustomizing();
                                           ">
                                    <img src="<%=context%>/images/edit.png"  alt="Edit" />
                                </a>--%>
                            </td>
                        </tr>
                        <%
                            String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,null, false, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            exclusiveText = exclusiveText.replace("<img src=\"/img/et-CRUST.png\" class=\"img_topping\">", " ");
                            exclusiveText = exclusiveText.replace("<img src=\"/img/et-COOKINGMODE.png\" class=\"img_topping\">", " ");
                            String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            if (!exclusiveText.isEmpty() || !fullText.isEmpty() || !leftText.isEmpty() || !rightText.isEmpty()){
                        %>

                        <tr>

                            <td width="60%" align="left" valign="top" height="25" style=" padding-left: 5px; font: 14px/25px arial; color: gray">
                                <c:if test="<%= exclusiveText.length() > 0 %>">
                                   <%= exclusiveText %>
                                </c:if>
                                <c:if test="<%= fullText.length() > 0 %>">
                                    <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                </c:if>
                                <c:if test="<%= leftText.length() > 0 %>">
                                    <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                </c:if>
                                <c:if test="<%= rightText.length() > 0 %>">
                                    <%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %>
                                </c:if>
                            </td>
                            <% BasketSingleItem basketSingleItemCur = (BasketSingleItem)pageContext.getAttribute("basketSingleItem"); %>
                            <% String singlePrice=singleItem.getPrice().toString(); %>
                            <td width="26%" align="right" valign="top">
                                <b><%= CurrencyUtils.toMoney(((BasketSingleItem)pageContext.getAttribute("basketSingleItem")).getPrice()) %></b>
                            </td>
                        </tr>
                        <% } else {%>
                        <tr>
                            <td width="60%"></td>
                            <td width="26%" align="right" valign="top">
                            <b><%= CurrencyUtils.toMoney(((BasketSingleItem)pageContext.getAttribute("basketSingleItem")).getPrice()) %></b>
                            </td>
                        </tr>
                        <% }%>
                    </table>
                </div>
            </div>
        </c:if>
    </c:forEach>
  </div>
<%--</c:if>--%>

