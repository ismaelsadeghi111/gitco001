<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%
    String context = request.getContextPath();
%>

<html>
<head>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
    <title><bean:message key="Franchising.title"/></title>

</head>
    <body>
    <!-- Google Tag Manager (noscript) -->
    <noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                      height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
    <!-- End Google Tag Manager (noscript) -->

    <table border="0" width="100%"  style="padding: 15px;">
        <tr>
            <td width="33%">
                <span class="cusfood"  style="padding: 15px;"><bean:message key="Franchising.title"/></span> <span style="font-size: 14px;margin-left: -10px;"><bean:message key="Franchising.title2"/></span>
            </td>
            <td width="50%">
                <div class="Floatleft">
                    <%--<span class="font5"><bean:message key="label.Order.From.Our"/>:</span>--%>
                    <a style="padding: 12px 32px;" class="btn-first" href="javascript: void(0);"  onclick="setMenuType('menu'); getMenuList('menu');" style="margin:3px;"><bean:message key="label.page.title.menu"/> </a>
                    <a style="padding: 12px 24px;" class="btn-first" href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" style="margin:3px;"><bean:message key="label.page.title.specials"/> </a>
                </div>
            </td>
        </tr>
    </table>
          <div class="LeftMiddleWrapper">
                        <div >
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="30%" height="40" class="font5" style="padding-left:20px;padding-top:20px; padding-right:20px;color:#00002E;" >
                                        <img  style="padding-bottom:20px;float:left;" alt="franchising" src="<%=context%>/images/franchisingnew.png">
                                    </td>
                                    <td width="70%" height="40" class="font5" style="padding-left:20px;padding-top:20px; padding-right:20px;color:#00002E;" >
                                    <span style="text-align:justify;">  <bean:message key="Franchising.body1"/></span>
                                    </td>
                                </tr>
                            </table>
                            <br class="clear">

                        </div>

                    </div>



    </body>
</html>