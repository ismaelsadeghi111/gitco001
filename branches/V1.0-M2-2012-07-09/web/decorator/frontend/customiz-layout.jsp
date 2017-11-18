<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 23, 2011
  Time: 1:37:08 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/pages/taglibs.jsp" %>
<%@ include file="/pages/tilesTagLib.jsp" %>
<%
    String context = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="edge" >
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
                                

    <title>DoublePizza | <tiles:getAsString name="name" ignore="true" /> </title>

    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery-1.4.4.min.js"> </script>
    <script  type="text/javascript" src="<%=context%>/js/jquery/jquery.easing.1.3.js"> </script>
    <script type="text/javascript" src="<%=context%>/js/jquery.nivo.slider.pack.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>
    <script type="text/javascript" src="<%=context%>/js/scriptchange.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery/jquery.vaccordion.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.ui.draggable.js"></script>
    <script type="text/javascript" src="<%=context%>/js/jquery.alerts.js"></script>
    <script type="text/javascript" src="<%=context%>/js/ui_message.js"></script>



    

    <script type="text/javascript">document.createElement('h7');</script>

    <link href="<%=context%>/css/global.css" rel="stylesheet" type="text/css"   />
    <link href="<%=context%>/css/global-mirror.css" rel="stylesheet" type="text/css"  />
    <link href="<%=context%>/css/stypri.css" rel="stylesheet" type="text/css"  />
    <link href="<%=context%>/css/jquery.alerts.css" rel="stylesheet" type="text/css"/>
    <link href="<%=context%>/css/ui_message.css" rel="stylesheet" type="text/css"/>

    <link rel="shortcut icon" href="<%= context %>/img/favicon.ico"/>

    <tiles:insert attribute="initial"/>

    <style type="text/css">
        #btn_ch {
            height:25px;
          /*  margin-bottom:5px;*/
        }
        
        #btn_ch span{
            text-align:left;
            margin-left:10px;
            line-height:25px;
        }
        
        #btn_ch input[type="button"] {
            border:0;
            display:none;
            cursor:pointer;
            background-color:transparent;
            background:none;
            float:left;
        }

        .btn_clear{
            background:url(img/clear.png) no-repeat center center !important;
            width:25px;
            height:25px;
            display:block;

        }
        .btn_full{
            background:url(img/full.png) no-repeat center center !important;
            width:25px;
            height:25px;
            display:block;
        }
        .btn_right{
            background:url(img/right.png) no-repeat center center !important;
            width:25px;
            height:25px;
            display:block;
        }
        .btn_left{
            background:url(img/left.png) no-repeat center center !important;
            width:25px;
            height:25px;
            display:block ;
        }
    </style>

</head>
<body id="index">
<div class="tinfo message">                
    <table>
        <td width="55">
            <img width="48" height="48" src="<%= context%>/img/messages/information.png">
        </td>
        <td>
            <h3 id="tinfoHeader" class="message-head"></h3>
            <p id="tinfoMessage" class="message-parag"></p>
        </td>
    </table>
</div>

<div class="terror message">
    <table>
        <td width="55">
            <img width="48" height="48" src="<%= context%>/img/messages/error.png">
        </td>
        <td>
            <h3 id="terrorHeader" class="message-head"></h3>
            <p id="terrorMessage" class="message-parag"></p>
        </td>        
    </table>
</div>

<div class="twarning message">
    <table>
        <td width="55">
            <img width="48" height="48" src="<%= context%>/img/messages/warning.png">
        </td>
        <td>
            <h3 id="twarningHeader" class="message-head"></h3>
            <p id="twarningMessage" class="message-parag"></p>
        </td>
    </table>
</div>

<div class="tsuccess message">
    <table>
        <td width="55">
            <img width="48" height="48" src="<%= context%>/img/messages/confirmation.png">
        </td>
        <td>
            <h3 id="tsuccessHeader" class="message-head"></h3>
            <p id="tsuccessMessage" class="message-parag"></p>
        </td>
    </table>
</div>
<div id="wrapper1">
    <div id="wrapper2">
        <div id="wrapper3">
            <div id="wrapper4">
                <!--<form id=""></form>-->
                <tiles:insert attribute="header"/>
                <tiles:insert attribute="header2"/>
            </div>
        </div>
        <div id="columns">
            <div id="center_column" class="center_column">
                <div id="center_column_inner" style="background:white;">
                    <tiles:insert attribute="body"/>
                    <tiles:insert attribute="footer"/>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="pseudo_footer">
</div>
</body>
</html>