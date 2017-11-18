<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 1/8/14
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    Float todayDPDollarsPercentage = (Float) request.getSession().getAttribute("todayDPDollarsPercentage");
    List<Map<String,Float>> dpDollarsWeeklyList = (List<Map<String,Float>>) request.getSession().getAttribute("dpDollarsWeeklyList");
    todayDPDollarsPercentage = todayDPDollarsPercentage == null ? new Float(0) : todayDPDollarsPercentage;
%>
<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>
<div id="MiddleBlockRight">
    <div class="sidebar">
        <img src="<%=context%>/images/sidebar-top.jpg"  style="border-radius: 10px;margin-left:-10px;">
        <a href="#">
            <div class="slider-ad">

                <span style="font-size: 18px;"><bean:message key="slideShow.rightAds.text1"/> </span>

                <c:if test="${enState}">
                    <b style="margin-bottom: 0px; margin-left: 18px;color:#fff;"> <bean:message key="slideShow.rightAds.text2"/></b>
                </c:if>
                <c:if test="${frState}">
                    <b style="	font: bold 37px afta_serifregular;margin-bottom: -2px;color:#fff;" > <bean:message key="slideShow.rightAds.text2"/></b>
                </c:if>
                <c:if test="${enState}">
                            <span class="font2" style="margin-left:8; text-align: left;width: auto;">
                </c:if>
                <c:if test="${frState}">
                            <span class="font2" style="margin: 0; padding-left:16px;text-align: left;width: auto;">
                </c:if>
                <bean:message key="slideShow.rightAds.text3"/><br/>
                <bean:message key="slideShow.rightAds.text4"/> <i style="color:yellow;margin-left: 50px;"><%=todayDPDollarsPercentage%>%</i><br/>
                <bean:message key="slideShow.rightAds.text5"/>
                </span>
                <div style="margin-top:10px; color:#fcefa1; float:left; width:100%;font-size: 18px;font-family:'ItcKabelMedium',Arial;
                 ;">
                    <div style="width: inherit;">
                        <ul>
                            <%
                                if (dpDollarsWeeklyList != null && dpDollarsWeeklyList.size() > 0) {
                                    for (Map<String, Float> dpDollarsWeekly : dpDollarsWeeklyList) {
                                        for (Map.Entry entry : dpDollarsWeekly.entrySet()) {
                            %>
                            <li>
                                <label style="float: left;"><%=StringUtils.capitalize(entry.getKey().toString())%></label>
                                <label style="float: right;"><%=entry.getValue()%>%</label>
                            </li>
                            <br />
                            <%
                                        }
                                    }
                                }
                            %>

                        </ul>
                    </div>
                </div>
                <br/>
            </div>
        </a>
        <%--<img src="<%=context%>/images/sidebar-bottom.jpg"  style="margin-top:25px;border-radius: 10px;margin-left:-10px;">--%>
    </div>
</div>

<div class="Clear"></div>
