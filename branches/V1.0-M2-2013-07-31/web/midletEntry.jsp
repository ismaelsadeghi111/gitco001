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
<h1 align="center">

</h1>


<c:import url="/myInfo.xml" var="doc" context="/dp"/>
<x:parse varDom='${doc}' />
<%--<x:parse var='<%=pageContext.getAttribute("doc")%>' />--%>

<x:out select="$doc//error-page/exception-type" />
    



</body>
</html>
