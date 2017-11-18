<%@ page import="com.sefryek.common.config.ApplicationConfig" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 28, 2012
  Time: 12:09:51 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context= request.getContextPath();
%>
<div id="footer">

    <ul id="footer_links">
        <li class="first_item">
            <a href="<%=context%>/frontend.do?operation=goToAboutPage" title="">
                <bean:message key="label.page.footer.link.aboutUs"/>
            </a>
        </li>
        <li class="item">
            <a href="<%=context%>/frontend.do?operation=goToFranchisingPage" title="">
                <bean:message key="label.page.footer.link.franchise"/>
            </a>
        </li>
        <li class="item">
            <a href="<%=context%>/frontend.do?operation=goToFunZonePage" title="">
                <bean:message key="label.page.footer.link.fun.zone"/>
            </a>
        </li>
        <li class="item"><a href="<%=context%>/frontend.do?operation=goToFeedBackPage" title="">
            <bean:message key="label.page.footer.link.contact"/>
        </a></li>
        <li class="item">
            <a href="<%=context%>/frontend.do?operation=goToStoreLocator" title="">
                <bean:message key="label.page.footer.link.storeLocator"/>
            </a>
        </li>
        <li class="item">
            <a href="<%=context%>/frontend.do?operation=gotoTermsConditionsPage" title="">
                <bean:message key="label.page.footer.link.termsAndConditions"/>
            </a>
        </li>
        <li class="last_item" style="line-height:21px;">
            &copy;<bean:message key="label.page.footer.all.right"/><br>
            <bean:message key="label.page.footer.poweredby"/> <a href="http://www.zoitek.com" target="_blank" style="text-decoration: underline;"><bean:message key="label.page.footer.zoitek"/></a>. <%= ApplicationConfig.versionInfo %></li>            

        </li>        
        <li class="item"><span style="color: #FFFFFF;"> <bean:message key="label.from"/>   </span><img src="<%=context%>/img/browser/firefox.png" style="vertical-align: middle;"/><span style="color: #FFFFFF;">8.0</span></li>
        <li class="item"><img src="<%=context%>/img/browser/opera.png" style="vertical-align: middle;"/><span style="color: #FFFFFF;">11.64</span></li>
        <li class="item"><img src="<%=context%>/img/browser/ie.png" style="vertical-align: middle;"/><span style="color: #FFFFFF;">8.0</span></li>
        <li class="item"><img src="<%=context%>/img/browser/chrome.png" style="vertical-align: middle;"/><span style="color: #FFFFFF;">8.0</span></li>
        <li class="item"><img src="<%=context%>/img/browser/safari.png" style="vertical-align: middle;"/><span style="color: #FFFFFF;">5.1</span></li>
    </ul>
</div>
