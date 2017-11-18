<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml_rt" %>
<%
    String context = request.getContextPath();
%>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Mar 12, 2012
  Time: 10:29:44 AM
--%>                                                    
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HEAD>

</HEAD>
<html>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<h1 align="center">

</h1>


<c:import url="/myInfo.xml" var="doc" context="/dp"/>
<x:parse varDom='${doc}' />
<%--<x:parse var='<%=pageContext.getAttribute("doc")%>' />--%>

<x:out select="$doc//error-page/exception-type" />
    



</body>
</html>
