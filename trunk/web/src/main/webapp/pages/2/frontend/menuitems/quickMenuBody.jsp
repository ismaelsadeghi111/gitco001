<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: Mostafa Jamshid
  Date: 12/29/13
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String context = request.getContextPath();
String middlepath = InMemoryData.getFileServicePath(Constant.DATA_RESOURCES_QMENU_PATH);
%>
<html>
<head>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
    <title><bean:message key="label.page.quickMenu"/></title>

</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

    <div class="LeftMiddleWrapper">
        <div class="Quick-box">
            <div class="">
                <%--<h2 class="Quick-box-title">Foods</h2>--%>
                    <div class="tabs">
                        <div id="navigation">
                        <ul class="nav">
                            <li><a id="qmenu" href="javascript: void(0);" class="selectedam" onclick="menuclass(); setMenuType('menu'); getFoodByType('menu');"><bean:message key="label.page.title.quickMenu.menu"/></a></li>
                            <li><a id="special" href="javascript: void(0);" class="" onclick="specialclass(); setMenuType('special'); getFoodByType('special');"><bean:message key="label.page.title.quickMenu.specials"/></a></li>
                            <li><a id="sideorders" href="javascript: void(0);" class="" onclick="sideordersclass(); setMenuType('menu'); getFoodByType('Side Orders');"><bean:message key="lable.sideOrder"/></a></li>
                            <li><a id="drinks" href="javascript: void(0);" class="" onclick="drinksclass(); setMenuType('menu'); getFoodByType('Drinks');"><bean:message key="lable.drinks"/></a></li>

                        </ul>
                            <div class="spacer"></div>
                            </div>

                        <div style="clear: both;"></div>
                    </div>
                    <div id="food-by-type" class="quick-inner">
                        <%-- filled from quickMenu.jsp --%>
                    </div>

            </div>
        </div>

        <div class="quick-errows-box ">
        </div>
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="30%"><a  href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu" onclick="setMenuType('menu');"  class="red" style="text-decoration:underline;"><bean:message key="label.online.ordering"/></a></td>
                 <%--PDF menu removed from here, find it in svn version = 2113--%>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        </table>
        <div class="Clear"></div>

    </div>
</body>
</html>


<script>
     function menuclass(){
        $('#special').removeClass('selectedam').addClass(' ');
        $('#sideorders').removeClass('selectedam').addClass(' ');
        $('#drinks').removeClass('selectedam').addClass(' ');
        $('#qmenu').removeClass(' ').addClass('selectedam');
    }

     function specialclass(){
         $('#qmenu').removeClass('selectedam').addClass(' ');
         $('#sideorders').removeClass('selectedam').addClass(' ');
         $('#drinks').removeClass('selectedam').addClass(' ');
         $('#special').removeClass(' ').addClass('selectedam');
     }

     function sideordersclass(){
         $('#qmenu').removeClass('selectedam').addClass(' ');
         $('#special').removeClass('selectedam').addClass(' ');
         $('#drinks').removeClass('selectedam').addClass(' ');
         $('#sideorders').removeClass(' ').addClass('selectedam');
     }

     function drinksclass(){
         $('#qmenu').removeClass('selectedam').addClass(' ');
         $('#sideorders').removeClass('selectedam').addClass(' ');
         $('#special').removeClass('selectedam').addClass(' ');
         $('#drinks').removeClass(' ').addClass('selectedam');
     }
</script>