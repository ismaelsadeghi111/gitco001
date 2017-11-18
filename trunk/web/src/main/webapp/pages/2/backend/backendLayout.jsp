<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 11/20/13
  Time: 1:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/pages/taglibs.jsp" %>
<%@ include file="/pages/tilesTagLib.jsp" %>

<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>

    <title>DoublePizza | <tiles:getAsString name="name" ignore="true"/></title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta http-equiv="X-UA-Compatible" content="edge">
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.nivo.slider.pack.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>
    <script type="text/javascript" src="<%=context%>/js/scriptchange.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery.vaccordion.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.validate.min.js"></script>

    <script type="text/javascript" src="<%=context%>/js/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=context%>/js/ui_message.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.datepick.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datepick.css">

    <link rel="shortcut icon" href="<%= context %>/img/favicon.ico"/>
    <link href="<%=context%>/css/ui_message.css" rel="stylesheet" type="text/css"/>

    <link type="text/css" rel="stylesheet" href="<%=context%>/css/style.css"/>
    <link type="text/css" rel="stylesheet" href="<%=context%>/css/extraStyle.css"/>
    <link type="text/css" rel="stylesheet" href="<%=context%>/css/dropdown.css"/>
    <script src="<%=context%>/js/dropdown.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=context%>/js/ui_message.js"></script>

    <tiles:insert attribute="initial"/>
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<div id="admin-container">
    <tiles:insert attribute="header"/>
    <div id="admin-middle">
        <tiles:insert attribute="leftmenu"/>
        <tiles:insert attribute="body"/>
    </div>
    <tiles:insert attribute="footer"/>
</div>
</body>
</html>