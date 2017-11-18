<%@ page import="java.util.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>

    <script type="application/javascript">



   function setMailList(mailStr){
        //alert(mailStr);
        document.getElementById("mailList").value = document.getElementById("mailList").value + mailStr +  "," ;
    }

    function clearForm(){
        document.getElementById('mailList').value = "";
    }


</script>

    <style type="text/css">
        body {
            overflow:hidden;
        }
    </style>
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<div id="admin-right" class="admin-right2">
    <div class="admin-table">
        <table class="font3" width="100%" border="1" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="4" height="36" class=""><span class="admin-table-heading">Mask Emails Form</span></td>
                <td width="11%" colspan="3"><span class="admin-table-heading2"></span></td>
            </tr>
            <tr>
                <td class="Compaign-form" colspan="7">
                    <div>
                        <form action="<c:out value="${pageContext.request.contextPath}"/>/campaign.do"
                          name="form1" method="post" id="form1">
                            <input type="hidden" name="operation" id="operation" value="maskeEmails">
                            <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0" id="stable" >

                                <tr>
                                   <td colspan="2">

                                   </td>
                               </tr>
                                <tr >
                                    <td colspan="2" style="text-align: left;">Are you sure to masked all of emails and submit changes on the Test Database?</td>
                                </tr>
                                <tr style="height: 15px;">
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td style="text-align: left;width: 20%;">
                                        <a onclick="cancelForm();" class="black_btn2 FloatLeft"
                                           style=" padding:0 50px;">Cancel</a>
                                    </td>
                                    <td >
                                        &nbsp;&nbsp;
                                        <a onclick="goTomask()" class="black_btn2 FloatLeft"
                                           style=" padding:0 50px;">Go</a>
                                    </td>
                                </tr>
                                <tr id="wait" style="display: none; vertical-align: middle;">
                                    <td colspan="3" height="70" style="text-align: left;"><img src="images/loading.gif"/><span class="font3">Emails are masking to test format. It may take a few minuets.</span>
                                    </td>

                                </tr>
                                <tr>
                                    <td>
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr style="background:#62ac25;display: none; vertical-align: middle;" id="finished">
                                    <td colspan="2" width="94%" class="font4" style="color:#fff;">
                                        <img src="images/right.png" alt="Right"/> &nbsp;
                                        <label id="sendMassageLb"></label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                            </form>
                        </div>
                   </td>
                </tr>
            </table>
        </div>
    </div>
<script type="application/javascript">
    function cancelForm(){

    }

     function goTomask(){
        $('#finished').hide();
        $('#wait').show();
        document.getElementById('form1').submit();
    }

    $(function() {
        var resultStr = "<%=request.getAttribute("resultMsg")%>";
        if(resultStr == "submited"){
            $('#finished').show();
            document.getElementById("sendMassageLb").innerHTML = "User emails have been masked on the Database successfully!<br>&nbsp;";
          }else if(resultStr == "failed"){
              document.getElementById("sendMassageLb").innerHTML = "Operation has been failed!";
          }
    });
</script>

</body>
</html>