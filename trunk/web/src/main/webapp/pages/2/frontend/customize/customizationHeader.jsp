<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%
    String context = request.getContextPath();
    String itemName = (String) request.getAttribute("itemName");
    itemName = itemName == null ? " " : itemName;
%>






<div id="MiddleBlock" class="nav-line">
                <h4>
                    <c:if test="${!empty requestScope.defaultExlusiveToppingCategory or !empty requestScope.selectedExlusiveToppingCategory or
                                  !empty requestScope.defaultItemToppingCategory or !empty requestScope.selectedItemToppingCategory}">

                        <h1><bean:message key="label.cutomize.your.food" /> <small style="font:18px Calibri">&raquo; <c:choose><c:when test="${not empty requestScope.categoryName}">${requestScope.categoryName}</c:when><c:otherwise><%=itemName%></c:otherwise></c:choose></small></h1>
                    </c:if>
                </h4>


<%--

                <div id="categoryItems" class="MiddleWrapper" style="padding:35px 35px;">
                    &lt;%&ndash;ajax call from categoryItemsInHeader.jsp&ndash;%&gt;
                </div>
--%>

                <div class="Clear"></div>
                <br />
                <div class="MiddleWrapper">

                    <table class="border Fullbasket-tilte" width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <%--<td width="60%">First Pizza, Second Pizza</td>--%>
                            <td width="60%"></td>

                             <td width="16%"><a href="#" class="orange-btn">Default Details</a></td>
                            <td width="14%"><a href="#" class="orange-btn">Add to Cart</a></td>
                        </tr>
                    </table>
                    <div id="combinedTypesMenu2" class="for_1_pizza_left">
                            <%-- filled from combinedTypesMenuInHeader2.jsp --%>
                    </div>

                    <div class="for_1_pizza_right">
                        <div id="center_column_inner_mir" style="float:left; padding-bottom:30px; width:100%;" class="border">
                                <%-- filled from singleMenuItemInHeader2.jsp --%>
                        </div>
                        <div id="howcookfood">

                        </div>
                    </div>
                    <div class="Clear"></div>
                    </div>
                    <br />