<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.model.BasketCombinedItem" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%--
  Created by IntelliJ IDEA.
  User: hassan.abdi
  Date: Feb 7, 2012
  Time: 12:08:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%
    String context = request.getContextPath();
    String selectedSingleItemForType = (String)request.getAttribute("selectedSingleItemForType");
    Integer customizeMode = (Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM);
    if (customizeMode == null)
        customizeMode = 0;
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);    
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=context%>/js/jquery.jcarouselm.pack.js"></script>
<script type="text/javascript">
    var carousel_mir_autoplay= 5;
    var carousel_mir_items_visible= 3;
    var carousel_mir_scroll= 1;
</script>
<script type="text/javascript" src="<%=context%>/js/homecarouselm.js"></script>


<div id="homecarousel_mir" style="margin-top:-14px;">
    <ul id="mycarousel_mir">
        <logic:iterate id="singleItem" name="menuSingleList" indexId="itemIndex">
            <li>
                <%String singleName = StringUtil.quotedString(((MenuSingleItem)pageContext.getAttribute("singleItem")).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))); %>
                <a id="singleInCmbined_${itemIndex}" href="javascript: void(0);" title="<%=((MenuSingleItem)singleItem).getDescription((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" class="product_image_mir"
                   onclick="if (<%=!session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM).equals(0) &&
                                    !((FrontendAction.ClassTypeEnum)session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE)).equals(FrontendAction.ClassTypeEnum.COMBINED)%> ){
                                zAlert('<bean:message key="message.can.not.change.item.type.first.remove.then.select.favorite"/>', 'Alert', 0,
                                 function(button) {
                                    return true;
                                 }
                                );
                            }
                            else {
                                if (captionArray[currentCaption.substr(12)]) {
                                    zConfirm('<bean:message key='message.confirm.looz.your.toppings1'/><b>' + document.getElementById(currentCaption).innerHTML + '</b><bean:message key='message.confirm.looz.your.toppings2'/>','Confirm', 500,
                                    <%--zConfirm('<bean:message key='message.confirm.looz.your.toppings' arg0="${singleItem.name}"/>','Confirm', 500,--%>
                                        function(button) {
                                                   if (button == 'Yes') {
                                                            setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                                                            <%--getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');--%>
                                                            putThisSingleInSession('${singleItem.id}', '${singleItem.groupId}');
                                                            <%--selectSingleItem('singleInCmbined_${itemIndex}');--%>
                                                            updateDefaultMenuSingleOnType('<%= singleName %>', '${singleItem.id}', '${singleItem.groupId}');
                                                            captionArray[currentCaption.substr(12)] = false;
                                                            getBasketItems();
                                                            <%--selectSingleItem('singleInCmbined_${itemIndex}');--%>
                                                            scrollToToppingsIfNotCombined();
                                                  }
                                                   else {
                                                            return false;
                                                   }
                                        }
                                    );
                                }
                                else {
                                        setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                                        putThisSingleInSession('${singleItem.id}', '${singleItem.groupId}');
                                        if (document.getElementById('main_menu') == null){
                                            getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');
                                            selectSingleItem('endDivsingle${singleItem.id}');                                            
                                        }
                                        updateDefaultMenuSingleOnType('<%= singleName %>', '${singleItem.id}', '${singleItem.groupId}');
                                        getBasketItems();
                                        scrollToToppingsIfNotCombined();
                                }
                        }
                ">

                    <img src="<%=context%><%=middlepath%>${singleItem.imageURL}" alt="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" />
                </a>
                <div id="endDivsingle${singleItem.id}" onclick="document.getElementById('singleInCmbined_${itemIndex}').onclick();">
                    <span class="price_mir">${singleItem.formattedPrice}</span>
                    <h5><a href="#" title=""><%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%></a></h5>
                </div>
            </li>
            <script type="text/javascript" language="JavaScript">
                if ("${itemIndex}" == "0" & '<%=customizeMode%>' != '3'){
                    setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                }
            </script>
        </logic:iterate>
    </ul>
    <h4>${categoryName}</h4>
    <script type="text/javascript">
        if ('<%=customizeMode%>' == '3')
            selectSingleItem("endDivsingle" + lastSelectedSIngleItemId);
        <c:if test="<%=!session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM).equals(0)%>">
        setCaptionArrayTrue();
        </c:if>

    </script>
</div>
