<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/1/13
  Time: 1:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">--%>

<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="/pages/taglibs.jsp" %>
<%@ include file="/pages/tilesTagLib.jsp" %>

<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
    <title>DoublePizza | <tiles:getAsString name="name" ignore="true" /> </title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta http-equiv="X-UA-Compatible" content="edge" >
    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery-1.4.4.min.js"> </script>
    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery-migrate-1.2.1.min.js"> </script>
    <script  type="text/javascript" src="<%=context%>/js/jquery.nivo.slider.pack.js"></script>
    <script  type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>
    <script  type="text/javascript" src="<%=context%>/js/scriptchange.js"></script>
    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery.mousewheel.js"></script>
    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery.vaccordion.js"></script>
    <script  type="text/javascript" src="<%=context%>/js/jquery.jcarousel.js"></script>

    <script src="<%=context%>/js/jquery/jquery-1.10.2.min.js"></script>
    <link rel="stylesheet" id="style-css"  href="<%=context%>/css/diapo.css" type="text/css" media="all">

    <script type="text/javascript" src="<%=context%>/js/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=context%>/js/ui_message.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.validate.min.js"></script>
    <!--[if !IE]><!-->
    <script type="text/javascript" src="<%=context%>/js/jquery.mobile-1.0rc2.customized.min.js"></script>
    <!--<![endif]-->
    <script type="text/javascript" src="<%=context%>/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.hoverIntent.minified.js"></script>
    <script type="text/javascript" src="<%=context%>/js/diapo.js"></script>

    <link rel="shortcut icon" href="<%= context %>/img/favicon.ico"/>
    <link href="<%=context%>/css/ui_message.css" rel="stylesheet" type="text/css"/>

    <link type="text/css" rel="stylesheet" href="<%=context%>/css/style.css" />
    <link type="text/css" rel="stylesheet" href="<%=context%>/css/extraStyle.css" />

    <link type="text/css" rel="stylesheet" href="<%=context%>/css/dropdown.css"/>
    <script type="text/javascript" src="<%=context%>/js/jquery.datepick.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datepick.css">

    <script src="<%=context%>/js/dropdown.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=context%>/js/ui_message.js"></script>

    <%--Popup dialog ========================--%>
    <link rel="stylesheet" href="<%=context%>/css/jquery-ui.css">
    <%--<script src="<%=context%>/js/jquery/jquery-1.10.2.js"></script>--%>
    <script src="<%=context%>/js/jquery/jquery-ui.js"></script>

    <%--<script type="text/javascript" src="<%=context%>/js/jquery/jquery-1.10.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=context%>/js/jquery/jquery-ui.min.js"></script>--%>
    <link rel="stylesheet" media="all" type="text/css" href="<%=context%>/css/smoothness/jquery-ui.css" />
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery-ui-timepicker-addon.js"></script>
    <link rel="stylesheet" media="all" type="text/css" href="<%=context%>/css/jquery-ui-timepicker-addon.css" />
    <%--<link rel="stylesheet" href="<%=context%>/css/jquery-ui.css">--%>
    <%--<script src="<%=context%>/js/jquery/jquery-1.10.2.js"></script>--%>

    <script src="<%=context%>/js/jquery/jquery-ui.js"></script>

    <link rel="stylesheet" media="all" type="text/css" href="<%=context%>/css/hint.css" />




    <tiles:insert attribute="initial"/>
    <tiles:insert attribute="mapInitial"/>



</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<div id="container">
    <tiles:insert attribute="header"/>
    <div id="MiddleBlock" class="nav-line">
        <tiles:insert attribute="body"/>
        <tiles:insert attribute="sidebar"/>
    </div>
    <tiles:insert attribute="footer"/>
    <tiles:insert attribute="secondfooter"/>
</div>
</body>
</html>