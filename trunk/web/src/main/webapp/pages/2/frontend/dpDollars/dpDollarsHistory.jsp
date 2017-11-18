 <%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="com.sefryek.common.util.DateUtil" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2/10/14
  Time: 4:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    Double lastBalance= new Double(00.00d);
%>
<p  class="cusfood" style="padding: 15px;"><bean:message key="DPDollars.myDPDollars"/></p>

    <div class="LeftMiddleWrapper">
        <div style="position:relative;">
            <%--<a class="my-doubel-dollar-top" href="#"></a>--%>
            <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">

                <tr height="33" style="background:#2e2d2e; color:#fff;" class="font4">
                    <td width="16%" style="padding-left:5px;">Date</td>
                    <td width="15%"><bean:message key="doubble.PizzaDollar.earned"/>/<bean:message key="spent"/></td>
                    <td width="24%"><bean:message key="label.page.menu.doublePizzaDOLLARS"/></td>
                    <td width="15%">Balance</td>
                    <td width="15%"><bean:message key="percentage"/></td>
                    <td width="15%"><bean:message key="orderNumber"/></td>
                </tr>
        <logic:iterate id="dpDollarHistories" name="dpDollarsHistoryForm" property="dpDollarHistories" indexId="i">
            <bean:define id="dpDollarHis" type="com.sefryek.doublepizza.model.DpDollarHistory" name="dpDollarHistories"/>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="padding-left:5px;"><%=DateUtil.dateToStringYYY_MM_DD_with_Dash(dpDollarHis.getCreationDate())%></td>
                    <c:choose>
                        <c:when test="${dpDollarHis.status eq 'EARNED'}">
                            <td><bean:message key="doubble.PizzaDollar.earned"/></td>
                        </c:when>
                        <c:otherwise>
                            <td class="red"><bean:message key="spent"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td>${dpDollarHis.amount}</td>
                    <%
                    if (user!=null){
                        lastBalance = dpDollarHis.getBalance();
                    }%>
                    <td>
                        <fmt:formatNumber  pattern="#,##0.00"
                                           value="${dpDollarHis.balance}"/></td>
                    <td>${dpDollarHis.percentage}</td>
                    <td>${dpDollarHis.orderId}</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
        </logic:iterate>
                <tr class="border">
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <%user.setDpDollarBalance(lastBalance);%>
                    <td height="35">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td class="orange_color">$<%=user.getDpDollarBalance()%></td>
                    <td>&nbsp;</td>
                    <td>
                        <%--<a class="my-doubel-dollar-bottom" href="#"></a></td>--%>
                </tr>
            </table>
        </div>
    </div>


