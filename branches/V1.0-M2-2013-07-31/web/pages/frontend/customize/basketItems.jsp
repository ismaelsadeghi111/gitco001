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

<%--
  Created by IntelliJ IDEA.
  User: hassan.abdi
  Date: Feb 26, 2012
  Time: 1:09:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <%
        Integer editButNum = 0;
        BasketSingleItem expensiveItem = null;
        BigDecimal basketTotalPrice = (BigDecimal)request.getAttribute("basketTotalPrice");
        Basket basket = (Basket)request.getSession().getAttribute(Constant.BASKET);
        String context = request.getContextPath();
    %>

        <script type="text/javascript">
            updateBasketItemCounts(<%= basket.getBasketItemList().size()%>);
            updateBasketTotalPrice("<%= CurrencyUtils.toMoney(basket.calculateTotalPrice()) %>");
        </script>

        <c:if test='<%=((List)request.getAttribute("basketItemsList")).size() != 0%>'>
            <c:forEach items="${requestScope.basketItemsList}" begin="0" var="basketItem" varStatus="cuurIndex1" >

                <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketCombinedItem.class) %>'>
                    <bean:define id="basketCombinedItem" type="com.sefryek.doublepizza.model.BasketCombinedItem" name="basketItem" property="object"/>
                    <bean:define id="combinedMenuItem" type="com.sefryek.doublepizza.model.CombinedMenuItem" name="basketCombinedItem" property="combined"/>
                    <% Category parentCategory = InMemoryData.getCombinedParentCategory(combinedMenuItem); %>
                    <div class="va-slice ">
                        <div class="h12">
                            (${cuurIndex1.count}) <%=StringUtil.shortify(((CombinedMenuItem)pageContext.getAttribute(
                                    "combinedMenuItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)), 
                                Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE)%>
                            <a href="javascript: void(0);" onclick="removeItemFromBasket(${basketCombinedItem.identifier}, 'COMBINED');"/>
                        </div>
                        <c:forEach items="${basketCombinedItem.basketSingleItemList}" begin="0" var="basketSingleItem" varStatus="cuurIndex2">
                            <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>
                            <span>
                                <table>
                                    <tr>
                                        <% String singleName = ((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)); %>
                                        <td class="title" title="<%= singleName %>"><%= StringUtil.shortify(singleName, Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE) %></td>
                                        <% BasketSingleItem basketSingleItemCur = (BasketSingleItem)pageContext.getAttribute("basketSingleItem"); %>
                                        <td class="price"><%= CurrencyUtils.toMoney((new BasketCombinedItemHelper().getInvoicePrice(
                                                (BasketCombinedItem)((BasketItem)pageContext
                                                        .getAttribute("basketItem")).getObject(), basketSingleItemCur))) %></td>
                                    </tr>
                                </table>
                            </span>
                            <!--<br class="clear_float"  />-->
                            <%
                                String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,null, false, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                exclusiveText = exclusiveText.replace("<context>", context);
                                String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            %>
                            <div>
                                <c:if test="<%= exclusiveText.length() > 0 %>">
                                    <label class="toppingText" ><%= exclusiveText %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= fullText.length() > 0 %>">
                                    <img class="img_full"/><label class="toppingText" title="<%= fullText%>"><%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= leftText.length() > 0 %>">
                                    <img class="img_left" /><label class="toppingText" title="<%= leftText%>"><%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= rightText.length() > 0 %>">
                                    <img class="img_right"/><label class="toppingText" title="<%= rightText%>"><%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                            </div>
                            <!--<br class="clear_float"  />-->
                        </c:forEach>
                         <a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit" onclick="
                            setBasketSingleItemIdentifier('---1');
                            setIdAndGroupId(null, null);
                            editBasketCombined(${basketCombinedItem.identifier},'<%= parentCategory.getId()%>', '${basketCombinedItem.productNoRef}', '${basketCombinedItem.groupIdRef}') ;
                            setCustomizedBasketItemId(${basketItem.identifier});
                            isInCustomizingMode = true;
                         ">
                             <bean:message key='button.customizeAndChange'/>
                         </a>
                    </div>
                </c:if>

                <c:if test='<%= ((BasketItem)pageContext.getAttribute("basketItem")).getClassType().equals(BasketSingleItem.class) %>'>
                    <bean:define id="basketSingleItem" type="com.sefryek.doublepizza.model.BasketSingleItem" name="basketItem" property="object"/>
                    <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>
                    <div class="va-slice ">
                        <div class="h12">
                            (${cuurIndex1.count}) Order
                            <a href="javascript: void(0);" onclick="removeItemFromBasket(${basketSingleItem.identifier}, 'SINGLEMENUITEM');"/>
                        </div>
                        <bean:define id="menuSingleItem" type="com.sefryek.doublepizza.model.MenuSingleItem" name="basketSingleItem" property="single"/>
                        <%
                            Category parentCategory = InMemoryData.getSingleParentCategory(menuSingleItem);
                            Category parentSubCategory = null;
                            if (parentCategory == null){
                                parentSubCategory = InMemoryData.getSingleParentSubCategory(menuSingleItem);
                                parentCategory = InMemoryData.getSubCategoryParentCategory(parentSubCategory);
                            }                         
                        %>

                        <span>
                                <table>
                                    <tr>
                                        <% String singleName = ((MenuSingleItem)pageContext.getAttribute("menuSingleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)); %>
                                        <td class="title" title="<%= singleName %>"><%= StringUtil.shortify(singleName, Constant.MAXIMUM_CHARACTERS_OF_BASKET_ITEM_TITLE) %></td>
                                        <td class="price"><%= CurrencyUtils.toMoney(((BasketSingleItem)pageContext.getAttribute("basketSingleItem")).getPrice()) %></td>
                                    </tr>
                                </table>
                        </span>
                        <!--<br class="clear_float"  />-->

                            <%
                                String exclusiveText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,null, false, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String fullText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"full", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String leftText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"left", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                                String rightText = InMemoryData.getBasketSingleItemAllToppingsToString((BasketSingleItem)pageContext.getAttribute("basketSingleItem") ,"right", true, (Locale)session.getAttribute(Globals.LOCALE_KEY));
                            %>
                            <div>
                                <c:if test="<%= exclusiveText.length() > 0 %>">
                                    <label class="toppingText" title="<%= exclusiveText%>"><%= StringUtil.shortify(exclusiveText, 38) %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= fullText.length() > 0 %>">
                                    <img class="img_full"/><label class="toppingText" title="<%= fullText%>"><%= StringUtil.shortify(fullText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= leftText.length() > 0 %>">
                                    <img class="img_left" /><label class="toppingText" title="<%= leftText%>"><%= StringUtil.shortify(leftText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                                <c:if test="<%= rightText.length() > 0 %>">
                                    <img class="img_right"/><label class="toppingText" title="<%= rightText%>"><%= StringUtil.shortify(rightText, Constant.MAXIMUM_CHARACTERS_OF_TOPPIMGS) %></label>
                                    <br/>
                                </c:if>
                            </div>
                        <c:choose>
                            <c:when test="<%= parentCategory != null & parentSubCategory == null %>">
                                <a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit" onclick="
                                    setBasketSingleItemIdentifier('---2');
                                    setIdAndGroupId(null, null);
                                    editBasketSingle(${basketSingleItem.identifier},'<%= parentCategory.getId()%>', '${basketSingleItem.id}', '${basketSingleItem.groupId}') ;
                                    setCustomizedBasketItemId(${basketItem.identifier});
                                ">
                                    <bean:message key='button.customizeAndChange'/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a id="edit_but<%=editButNum++%>" href="javascript: void(0);" class="button_inherit" onclick="
                                    setBasketSingleItemIdentifier('---');
                                    setIdAndGroupId(null, null);
                                    editBasketSingleInSubCategory(${basketSingleItem.identifier},'<%= parentCategory.getId()%>', '<%= parentSubCategory.getId()%>', '${basketSingleItem.id}', '${basketSingleItem.groupId}');
                                    setCustomizedBasketItemId(${basketItem.identifier});
                                ">
                                    <bean:message key='button.customizeAndChange'/>
                                </a>
                            </c:otherwise>
                        </c:choose>                        

                    </div>
                </c:if>

            </c:forEach>
        </c:if>
