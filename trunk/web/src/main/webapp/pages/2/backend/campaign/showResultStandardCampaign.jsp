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

<form action="<c:out value="${pageContext.request.contextPath}"/>/campaign.do"
      name="campaignForm" method="post" id="campaignForm"  >

    <input type="hidden" name="operation" id="operation" value="sendTestEmail" />
    <input type="hidden" name="standardCampaignId" id="standardCampaignId" value="<%=request.getAttribute("standardCampaignId")%>" />

    <table width="60%" border="0" cellspacing="0" cellpadding="0" id="stable2">
        <tr class="admin-table-headline" style="background:#999999;">
            <td colspan="4" aligne="left" >
                <label id="sendMassageLb"  style="color: #ffff99"></font>-</label><br>
                This Campaign includes <font color="#ffd700"><label id="recSize" name="recSize" ></label> </font> users<br>
                Note : You can either select email from the list or write in the box by yourself.

            </td>
        </tr>
                <tr class="admin-table-headline" style="background:#999999;">
                    <td style="text-align:center; vertical-align: middle; width: 10%;" >
                       Emails
                    </td>
                    <td style="text-align: left; vertical-align: middle; width: 90%;">
                        <textarea aligne="left" rows="2" cols="6" style="width: 100%" type="text" id="mailList" name="mailList">saeid.amanzadeh@sefr-yek.com, sefryek_test@yahoo.com, sefryek.test@gmail.com, haniye.jalaliyar@sefr-yek.com, mostafa.jamshid@sefr-yek.com, fatemeh@sefr-yek.com, sefryek.test@hotmail.com</textarea>
                    </td>
                    <td  colspan="2" style="text-align: left;width: 10%;">
                        <table border="0">
                            <tr>
                                <td>
                                    <img title="Reset the Form" style="cursor: pointer;" src="img/icon/cancel.png" onclick="clearForm()" />
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <!-- <img title="Send Emails" style="cursor: pointer;" src="img/icon/orang-send.png" onclick="document.getElementById('campaignForm').submit()" />-->


                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr height="30px" style="background-image: url('img/icon/title.gif'); height: 30px;">
                    <td colspan="4" style="width: 100%; height: 30px;">
                        <table  style="height:30px; width: 100%;">
                            <tr height="30px" class="admin-table-headline">
                                <td width="10%" style="text-align: left">Id</td>
                                <td width="40%" >User Name</td>
                                <td width="50%">Email</td>
                                <td>&nbsp;</td>
                             </tr>
                         </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <div style="width: 100%; height: 420px; border: 1; overflow:auto; " >
                            <table style="width: 100%; height: 100%" border="0">
                            <% int int1 = 1; %>
                            <c:forEach items="${resultStandardCampaignList}" var="resultStandardCampaignList">
                                <tr>
                                    <td aligne="center" width="20%"><%= int1++ %></td>
                                    <td aligne="center" width="40%">
                                        <c:if test="${resultStandardCampaignList.name != null}">
                                              ${resultStandardCampaignList.name}
                                        </c:if>

                                    </td>

                                    <td aligne="center" width="40%">
                                        <c:if test="${resultStandardCampaignList.emailAddress != null}">
                                                <img src="img/icon/add.png" style="cursor: pointer;" onclick="setMailList('${resultStandardCampaignList.emailAddress}')"/>&nbsp;
                                                <label style="cursor: pointer;" onclick="setMailList('${resultStandardCampaignList.emailAddress}')">
                                                    ${resultStandardCampaignList.emailAddress}
                                              </label>
                                        </c:if>&nbsp;
                                    </td>

                                </tr>
                            </c:forEach>
                                </table>
                               </div>
                        </td>

                    </tr>
                <script type="application/javascript">
                    document.getElementById("recSize").innerHTML = "<%= --int1 %>";
                    var sendStatus = "<%=request.getAttribute("sendStatus")%>";
                    if(sendStatus == "sent"){
                        document.getElementById("sendMassageLb").innerHTML = "Your Emails have been sent!";
                    }

                </script>


            </table>
    </form>
</body>
</html>