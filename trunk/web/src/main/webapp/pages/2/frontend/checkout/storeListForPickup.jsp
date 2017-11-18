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
            <div class="store-box">
                <table border="0" >
                    <tr>
                        <td>
                            <table border="0" >
                                <tr>
                                    <td>
                                    <td width="20%" >
                                        <a class="product_img_link"><img class="store-img" src="<%=context%><%=middlePath%>${storeItem.imageUrl}" width="145" height="125" style="margin-bottom: -18px;" /></a>
                                    </td>
                                    <td width="80%" >
                                        <table border="0" >
                                            <tr>
                                                <td>
                                                    <label class="button">
                                                        <input type="radio" name="store" value="${storeItem.storeId}">
                                                                <span class="outer">
                                                                    <span class="inner"></span>
                                                                 </span>

                                                    </label>
                                                    <span class="store-title">${storeItem.name}</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="address">${storeItem.address1}</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <span class="address">${storeItem.address2}</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <button type="button" class="btn-map" onclick="showMap('${storeItem.storeId}','${storeItem.name}','${storeItem.latitude}','${storeItem.longitude}','${storeItem.address1}','${storeItem.address2}');" title="View"><bean:message key="store.loacator.button.title"/></button>
                                                </td>
                                            </tr>

                                        </table>
                                    </td>
                                    </td>
                                </tr>
                            </table>
                    <tr>
                        <td>
                            <table border="0" >
                                <logic:iterate id="workingHourGroups" name="workingHoursGroups" >
                                    <tr>
                                        <bean:define id="workingHourGroup" type="com.sefryek.doublepizza.model.WorkingHour" name="workingHourGroups"/>
                                        <td style="padding-left: 15px;">
                                            <span class="store-hours">${workingHourGroup.openingHour} - ${workingHourGroup.closingHour}</span>
                                        </td>
                                        <td style="padding-left: 20px;">
                                            <span class="daysOfWeek">${workingHourGroup.dayofWeekGroup}</span>
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </table>
                        </td>
                    </tr>
                    </td>
                    </tr>
                </table>
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