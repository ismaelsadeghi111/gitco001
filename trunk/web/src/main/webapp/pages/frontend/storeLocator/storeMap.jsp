<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Store" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Store storeItem = (Store)request.getAttribute(Constant.STORE);
%>
<script type="text/javascript"> 
    window.onload = loadMap(<%=storeItem.getLatitude()%>,<%=storeItem.getLongitude()%>);
</script>

<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<div id="map_canvas" style="width: 625px; height: 400px"></div>
<div id="detail" style="width: 625px; height: 80px">
    <br>
    <span class="store-title" style="font-weight:bold;" ><%=storeItem.getName()%></span><br clear="both"><br>
    <p><span style=""><%=storeItem.getAddress1()%></span><br clear="both"><p>
    <p><span style=""><%=storeItem.getAddress2()%></span><br clear="both"><p>
</div>
</body>
</html>