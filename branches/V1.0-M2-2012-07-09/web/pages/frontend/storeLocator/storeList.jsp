<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.model.Store" %>
<%@ page import="com.sefryek.doublepizza.model.WorkingHour" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="cl" %>

<logic:iterate id="storeLocator" name="<%=Constant.STORE_LIST%>" >
    <bean:define id="storeItem" type="com.sefryek.doublepizza.model.Store" name="storeLocator"/>
    <%
        String contextForIncludedPage = request.getContextPath();
        List<WorkingHour> workingHoursGroups = InMemoryData.getWorkingHoursGroups(storeItem.getStoreId(),
                (Locale)(request.getSession().getAttribute(Globals.LOCALE_KEY)));
        request.setAttribute("workingHoursGroups", workingHoursGroups);
        String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_STORES_PATH);
    %>

    <li class="ajax_block_product ">
        <div class="left_block">
            <a class="product_img_link"><img src="<%=contextForIncludedPage%><%=middlePath%>${storeItem.imageUrl}" width="199" height="180" /></a>
        </div>

        <div class="right_block">

            <span class="price">${storeItem.name}</span><br clear="both">
            <span class="address">${storeItem.address1}</span><br clear="both">
            <span class="address">${storeItem.address2}</span><br clear="both">

            <logic:iterate id="workingHourGroups" name="workingHoursGroups" >

                <bean:define id="workingHourGroup" type="com.sefryek.doublepizza.model.WorkingHour" name="workingHourGroups"/>
                <br clear="both"><span class="discount">${workingHourGroup.openingHour} - ${workingHourGroup.closingHour}</span>
                <span class="daysOfWeek">${workingHourGroup.dayofWeekGroup}</span>

            </logic:iterate>

            <a class="button" onclick="showMap('${storeItem.storeId}','${storeItem.name}');" title="View"><bean:message key="store.loacator.button.title"/></a>

        </div>


    </li>

</logic:iterate>
<%
    Boolean postalCodeNotFound = (Boolean)request.getAttribute(Constant.POSTAL_CODE_NOT_FOUND);
    if (postalCodeNotFound == null)
        postalCodeNotFound = false;
%>
<cl:if test="<%= postalCodeNotFound %>">
    <script type="text/javascript">
        showValidationError('<bean:message key="label.no.item.found"/>');
    </script>
</cl:if>