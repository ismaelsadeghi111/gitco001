<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 1/29/14
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
%>
<div class="Histoty-popup-box">
    <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="33%">Product</td>
            <td width="51%">Description</td>
            <td width="13%">Price</td>
        </tr>
    </table>
    <logic:iterate id="orderHisDetails" name="orderForm" property="orderHistoryDetails" indexId="i">
       <bean:define id="orderDetailHis" type="com.sefryek.doublepizza.model.OrderDetailHistory" name="orderHisDetails"/>
    <div id="order-his-detals" class="Fullbasket border">
        <div class="Image-box"><img width="150px" height="125px" src="<%=context%><%=middlepath%>${orderDetailHis.imageUrl}" alt="Double Pizza" /></div>
        <div class="des history-des">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr class="border">
                    <td height="60" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="82%" class="padding-left"><b><%=orderDetailHis.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></b></td>
                            <td width="13%">&nbsp;</td>
                            <td width="5%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="62%">&nbsp;</td>
                                    <td width="38%">&nbsp;</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
                <tr class="yellow">
                    <td width="81%" height="30" class="padding-left">${orderDetailHis.itemName} </td>
                    <td width="19%">${orderDetailHis.itemPrice}</td>
                </tr>
                <tr class="orange">
                    <td height="30" class="padding-left"><bean:message key="label.rightmenu.header.total.price"/></td>
                    <td>${orderDetailHis.itemPrice}</td>
                </tr>
                <tr>
                    <td height="30" class="padding-left">
                    <c:if test="${not empty orderDetailHis.descriptionEn}">
                        <span class="red">Full</span>:<span class="gray"> <%=orderDetailHis.getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%></span>
                    </c:if>
                    </td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
    </logic:iterate>
    <div class="Clear"></div>
    <a href="#" onclick="viewPopup('orderHisPopup','close');" class="Histoty-close"><img src="<%=context%>/images/popup-close.png"  alt="Close" /></a>
</div>
<div class="Success-bg"></div>


