<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: hassan.abdi
  Date: Feb 7, 2012
  Time: 10:22:30 AM
--%>

<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String context = request.getContextPath();
    String selectedCategoryId = (String)request.getAttribute("selectedCategoryId");
    String selectedMenuItemName = (String) request.getAttribute("selectedMenuItem");
    String selectedCombinedGroupId = (String)request.getAttribute("selectedCombinedGroupId");
    String selectedCombinedProductNo = (String)request.getAttribute("selectedCombinedProductNo");
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    String catId = request.getParameter("catId");
    String groupId = request.getParameter("groupId");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>
<script type="text/javascript">
    var carousel_autoplay = 0;
    var carousel_items_visible = 3;
    var carousel_scroll = 1;
</script>
<script type="text/javascript" src="<%=context%>/js/homecarousel.js"></script>
<% int itemOrder = 0; %>
<%--<% if (!catId.isEmpty() && groupId.isEmpty()) { %>
    getSubCategoryItems(<%=catId%>);
<%
    }
%>--%>
<div id="homecarousel" style="z-index:0; height:198px;">
    <h4>${categoryName}</h4>
    <ul id="mycarousel">

        <c:set var="customizeState" value="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)%>"/>
        <logic:iterate id="innerCategoryItem" name="innerCategoryList">
            <li style="height:145px;">
                <a id="categoryItem_<%=itemOrder%>" href="javascript: void(0);" title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" class="product_image" onclick="
                    if (${customizeState eq "0"}){
                        isAllCaptionsClicked = false;
                        resetCombinedAndSingleMenu();
                        getSubCategoryItems(${innerCategoryItem.id});
                        selectCarouselItem('${"frontDivcategory"}${innerCategoryItem.id}');
                        setToppingSelected('false');
                    }
                    else {
                        zAlert('<bean:message key="message.can.not.change.item.type.first.remove.then.select.favorite"/>', 'Info', 0,
                         function(button) {
                            return true;
                         }
                        );
                    }


                    ">
                    <%--<img height="210" width="267" src="<%=context%><%=middlepath%>${innerCategoryItem.imageURL}" alt="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" />--%>
                </a>
                <div id="${"frontDivcategory"}${innerCategoryItem.id}" onclick="document.getElementById('categoryItem_<%=itemOrder%>').onclick();">
                    <input type="hidden" value="<%= itemOrder %>"/><span class="price"></span>
                    <h5>
                        <a href="javascript: void(0);" title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%=StringUtil.shortify(((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)), Constant.MAX_LENGTH_CATEGORY_NAME)%>
                        </a>
                    </h5>
                </div>

            </li>
            <% itemOrder++; %>
            <c:if test="<%= selectedCategoryId != null %>">
                <logic:equal name="innerCategoryItem" property="id" value="<%= selectedCategoryId %>">
                    <script type="text/javascript" language="JavaScript">
                        getSubCategoryItems(${innerCategoryItem.id});
                        <%--getSubCategoryItems(${innerCategoryItem.id});--%>
                    </script>
                </logic:equal>
            </c:if>
        </logic:iterate>

        <logic:iterate id="singleItem" name="menuSingleList">
            <li style="height:145px;">
                <a id="singleItem_<%=itemOrder%>" href="javascript: void(0);" title="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" class="product_image" onclick="
                    if (<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM).equals(0)%>) {
                        setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                        resetCombinedAndSingleMenu();
                        getSingleMenuItems('${singleItem.id}', '${singleItem.groupId}');
                        selectCarouselItem('${"frontDivsingle"}${singleItem.id}${singleItem.groupId}');
                        getBasketItems();
                    }else {
                        zConfirm('<bean:message key="message.can.not.change.item.type.first.remove.then.select.favorite"/>' , 'Confirm', 0,
                         function(button) {
                            if (button == 'Yes') {
                                alert('<bean:message key="message.can.not.change.item.type.first.remove.then.select.favorite" />');
                            }else {
                                return false;
                            }
                         }
                         );
                    }
                    ">
                    <%--<img height="210" width="267" src="<%=context%><%=middlepath%>${singleItem.imageURL}" alt="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" />--%>
                </a>
                <div id="${"frontDivsingle"}${singleItem.id}${singleItem.groupId}" onclick="document.getElementById('singleItem_<%=itemOrder%>').onclick();">
                    <input type="hidden" value="<%= itemOrder %>"/> <span class="price">${singleItem.formattedPrice}</span>
                    <h5>
                        <a href="#" title="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%=StringUtil.shortify(((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)), Constant.MAX_LENGTH_CATEGORY_NAME)%>
                        </a>
                    </h5>
                </div>
            </li>
            <%itemOrder++;%>
        </logic:iterate>


        <c:set var="userState" value="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)%>"/>
        <logic:iterate id="combinedItem" name="combinedItemList">
            <%
                String combinedSize = InMemoryData.getCombinedMenuItemSize((CombinedMenuItem)combinedItem);
                Integer sliceCount = InMemoryData.getSliceCount(combinedSize);
            %>
            <li style="height:145px;">
                <a id="combinedItem_<%=itemOrder%>" href="javascript: void(0);" title="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>, <%= combinedSize %>, <%= sliceCount %> <bean:message key='label.slices'/>"  class="product_image" onclick="
                    if (${customizeState eq "0"}){
                        isPageSameOfSession('<%=pageContext.getAttribute("userState") == null || pageContext.getAttribute("userState").equals(0)%>', <%=itemOrder%>, ${combinedItem.productNo}, ${combinedItem.groupId});
                    }
                    else {
                        zAlert('<bean:message key="message.can.not.change.item.type.first.remove.then.select.favorite"/>', 'Info', 0,
                         function(button) {
                            return true;
                         }
                        );
                    }
                        ">

                    <%--<img height="210" width="267" src="<%=context%><%=middlepath%>${combinedItem.imageURl}" alt="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>--%>
                </a>
                <div id="${"frontDivcombined"}${combinedItem.productNo}${combinedItem.groupId}" onclick="document.getElementById('combinedItem_<%=itemOrder%>').onclick();">
                    <input type="hidden" value="<%= itemOrder %>"/>
                    <% BigDecimal realPrice = InMemoryData.getCombinedRealPrice((CombinedMenuItem)pageContext.getAttribute("combinedItem")); %>
                    <span class="price"><bean:message key='label.combined.price.from'/> <%= CurrencyUtils.toMoney(realPrice) %></span>
                    <h5>
                        <a class="price" href="javascript: void(0);" title="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                        </a>
                    </h5>
                </div>
            </li>
            <% itemOrder++; %>
            <c:if test="${selectedCombinedProductNo != null}">
                <logic:equal name="combinedItem" property="productNo" value="<%= selectedCombinedProductNo %>">
                    <logic:equal name="combinedItem" property="groupId" value="<%= selectedCombinedGroupId %>">
                        <script type="text/javascript" language="JavaScript">
                            getCombinedItems(${combinedItem.productNo}, ${combinedItem.groupId});
                        </script>
                    </logic:equal>
                </logic:equal>
            </c:if>
        </logic:iterate>

    </ul>
</div>

<script type="text/javascript">
    getSubCategoryItems(<%=selectedCategoryId%>);
    selectCarouselItem("<%= "frontDiv" + request.getAttribute("selectedMenuItem") %>");
    $('.product_image').each(function(index){ var title = this.title; title = title.replace('<br>', '\n'); this.title = title; })
</script>