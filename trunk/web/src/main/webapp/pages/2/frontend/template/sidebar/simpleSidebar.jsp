<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%--
  Created by IntelliJ IDEA.
  User: Mostafa Jamshid
  Date: 1/5/14
  Time: 2:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
%>
<div id="MiddleBlockRight">
    <div class="sidebar">
        <img src="<%=context%>/images/sidebar-top.jpg"  style="border-radius: 10px;margin-left:-10px;">
        <br class="clear">
        <br class="clear">
        <br class="clear">
        <div  style="height: 300px;" class="slider-ad">
	       <span style="margin-top:15px;text-align: center;color:#fdf3b4;">
	       <bean:message key="label.simpleSidebar1"/>
           </span>
            <br class="clear">
             <div><br>
                 <br class="clear">
             </div>
            <span style="text-align: center;color:#fdf3b4;">
               <bean:message key="label.simpleSidebar2"/>
            </span>
            <br class="clear">
            <div><br></div>
            <span style="margin-bottom:15px;text-align:center;color:#fff;">
                <bean:message key="label.simpleSidebar3"/>
            </span>
            <br class="clear">
            <div><br></div>
                        <span style="text-align: center;color:#fdf3b4;">
               <bean:message key="label.simpleSidebar4"/>
            </span>
            <br class="clear">
            <div><br></div>
        </div>
        <br class="clear">
        <br class="clear">
        <br class="clear">
        <%--<img src="<%=context%>/images/sidebar-bottom.jpg"  style="margin-top:-2px;border-radius: 10px;margin-left:-10px;">--%>
    </div>
</div>
<div class="Clear"></div>