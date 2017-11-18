<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.model.Store" %>
<%@ page import="com.sefryek.doublepizza.model.WorkingHour" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="cl" %>

<%
    String context = request.getContextPath();
%>

<ul class="clear" id="product_list_pickup">

<logic:iterate id="storeLocator" name="<%=Constant.STORE_LIST_PICKUP%>" indexId="counter">
    <bean:define id="storeItem" type="com.sefryek.doublepizza.model.Store" name="storeLocator"/>
    <%
        List<WorkingHour> workingHoursGroups = InMemoryData.getWorkingHoursGroups(storeItem.getStoreId(),
                (Locale)(request.getSession().getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("workingHoursGroups", workingHoursGroups);
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_STORES_PATH);
    %>

    <li class="ajax_block_product">
        <div class="left_block">            
            <a class="product_img_link"><img src="<%=context%><%=middlePath%>${storeItem.imageUrl}" width="145" height="125" /></a>
        </div>


        <div class="right_block border-bottom">
            <input type="checkbox" name="store" class="checkbox-select-image" style="position:absolute; top:10px; left:15px; margin-left:492px;" <cl:if test="<%=counter.equals(0) %>">checked</cl:if> value="${storeItem.storeId}"
                onclick="uncheckAllOther(this);"/><span style="position:absolute; top:6px; left:17px; margin-left:507px;"><bean:message key="label.checkbox.pickup.from"/></span>
            <span class="price">${storeItem.name}</span><br clear="both">
            <span class="address">${storeItem.address1}</span><br clear="both">
            <span class="address">${storeItem.address2}</span><br clear="both">

            <logic:iterate id="workingHourGroups" name="workingHoursGroups">

                <bean:define id="workingHourGroup" type="com.sefryek.doublepizza.model.WorkingHour" name="workingHourGroups"/>
                <br clear="both"><span class="discount">${workingHourGroup.openingHour} - ${workingHourGroup.closingHour}</span>
                <span class="daysOfWeek">${workingHourGroup.dayofWeekGroup}</span>

            </logic:iterate>

            <button type="button" class="button-smaller" onclick="showMap('${storeItem.storeId}','${storeItem.name}');" title="View"><bean:message key="store.loacator.button.title"/></button>
        </div>

    </li>
    <%--<hr width="70%" style="background-color: #eeeeee; color:#eeeeee;">--%>
</logic:iterate>
</ul>
<%
    List storesList = (List)request.getAttribute(Constant.STORE_LIST_PICKUP);
    boolean isEmpty = storesList != null && storesList.size() <= 0;
%>
<cl:if test="<%= isEmpty %>">
    <span style="color:#DA0F00;"><bean:message key="label.no.item.found"/></span>
</cl:if>