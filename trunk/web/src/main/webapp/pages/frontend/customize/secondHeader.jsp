<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%
    String context = request.getContextPath();
%>

<div id="columns" style="margin-top:-35px;">
    <a href="javascript: void(0);" class="back-to-menu" onclick="goToOnlineOrder();"><span>&laquo; <bean:message key="back.to.menu"/></span></a>      <%--  BACK TO MENU  --%>
    <div id="center_column" class="center_column">
        <div id="center_column_inner">
            <div id="categoryItems">
            <%--ajax call from categoryItemsInHeader.jsp--%>
            </div>
            <br class="clear"/>

            <div id="combinedTypesMenu" style="display:inline-block;">
            <%--ajax call from combinedTypesMenuInHeader.jsp--%>
            </div>
            <div id="columns_mir">
            <div id="center_column_mir" class="center_column_mir">
                <div id="center_column_inner_mir">
                    <%--ajax call from singleMenuItemInHeader.jsp--%>
                </div>
            </div>