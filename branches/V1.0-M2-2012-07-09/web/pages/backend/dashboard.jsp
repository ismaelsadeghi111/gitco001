<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean-el" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Apr 18, 2012
  Time: 2:16:51 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <title>DoublePizza Admin</title>
    <link rel="stylesheet" href="<%=context%>/css/960.css" type="text/css" media="screen" charset="utf-8" />
    <link rel="stylesheet" href="<%=context%>/css/template.css" type="text/css" media="screen" charset="utf-8" />
    <link rel="stylesheet" href="<%=context%>/css/colour.css" type="text/css" media="screen" charset="utf-8" />

    <link href="<%=context%>/css/global.css" rel="stylesheet" type="text/css"   />
    <link href="<%=context%>/css/global-mirror.css" rel="stylesheet" type="text/css"  />
    <link href="<%=context%>/css/stypri.css" rel="stylesheet" type="text/css"  />
    <link href="<%=context%>/css/jquery.alerts.css" rel="stylesheet" type="text/css"/>    

    <script src="<%=context%>/js/jquery/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="<%=context%>/js/jquery-ui-1.7.2.custom.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
            $("#content .grid_5, #content .grid_6").sortable({
                placeholder: 'ui-state-highlight',
                forcePlaceholderSize: true,
                connectWith: '#content .grid_6, #content .grid_5',
                handle: 'h2',
                revert: true
            });
            $("#content .grid_5, #content .grid_6").disableSelection();
        });

        function loadData() {
            document.getElementById("lunch_but").disabled = true;
            $('#result_state').css('background','#ffffff');
            var img = document.createElement('img');
            document.body.appendChild(img);
            img.setAttribute('src', '<%=context%>/img/ico-loading-white.gif');

            document.getElementById('result_state').innerHTML = '';
            document.getElementById('result_state').appendChild(img);

            $.ajax({
                method: 'POST',
                url: '<%=context%>/admin.do',
                success: function(res) {
                    document.getElementById("result_state").innerHTML = res;
                    $('#result_state').css('background','#33ff00');
                    document.getElementById("lunch_but").disabled = false;
                },
                error:function (xhr, ajaxOptions, thrownError){
                    document.getElementById("result_state").innerHTML = 'Error in Server, Please Try Again.';
                    $('#result_state').css('background','#ff0000');
                    document.getElementById("lunch_but").disabled = false;

                }
            });
        }


    </script>
    <!--[if IE]><![endif]><![endif]-->
</head>
<body>


<div id="wrapper1">
    <div id="wrapper2">
        <div id="wrapper3">
            <div id="wrapper4">
                <div id="pageHeader">
                    <h1 id="head">Double Pizza Admin Panel</h1>
                    <ul id="navigation">
                        <li style="float:right; margin-top:20px; font-size:10px;">Welcome Admin&nbsp;</li>
                    </ul>
                </div>
            </div>
        </div>

        <div id="columns" class="center_column">
            <div id="center_column_inner_admin">
                <div id="content" class="container_16 clearfix">
                    <div class="grid_5">
                        <div ></div>
                    </div>
                    <div class="grid_5" style="margin-left:40%;">
                        <div class="box">
                            <h4 ><bean:message key="label.header.admin"/></h4>
                            <button id="lunch_but" onclick="loadData();">
                                <bean:message key="label.load.data"/>
                            </button>
                            <div id="result_state">
                                <bean:message key="label.message.load.data"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="foot">
                    <div class="container_16 clearfix">
                        <div class="grid_16">
                            <a href="<%=context%>/frontend.do">DoublePizza</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>