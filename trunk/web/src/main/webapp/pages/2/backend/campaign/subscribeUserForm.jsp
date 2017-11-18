<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 12/26/13
  Time: 11:14 AM
  To change this template use File | Settings | File Templates.
--%>

<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if (basket != null) {
        int totalOfItems = basket.getNumberOfItems();
        BigDecimal totalPrice = basket.calculateTotalPrice();
    }
%>
<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>


<p style="padding: 15px;" class="cusfood" id="loginTitle">
    <bean:message key="label.page.title.unsubscribe"/>
</p>

<div class="LeftMiddleWrapper">

    <div style="float: left;width: 80%;clear: left;">

        <form action="<%=context%>/campaign.do" method="post" id="subscribeForm">
           <input type="hidden" id="operation" name="operation"  value="unsubUser">
            <input type="hidden" id="userId" name="userId" value="<%=request.getAttribute("userId")%>" >
            <input type="hidden" id="email" name="email" value="<%=request.getAttribute("emailAddress")%>" >

            <input id="lang" name="lang" type="hidden" value="${enState}">

           <table class="border Fullbasket-msg font" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr id="resultMsgTr" style="display: none;">
                   <td>
                       <label id="forgotResultMsg">

                       </label>
                   </td>
               <tr>
               <tr id="msgTr" >
                    <td>
                        <bean:message key="message.subscribe.user.msg"/>
                    </td>
                <tr>

            </table>

            <table id="loginBottonTr" class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="2" style="font-size: 10;">&nbsp;</td>
                </tr>
                <tr >
                    <td style="width: 20%; text-align: left">
                        <bean:message key="lable.registration.email"/>
                    </td>
                    <td style="width: 80;text-align: left;">
                       <font color="red">
                               <%=request.getAttribute("emailAddress")%>
                       </font>
                    </td>

                </tr>

                <tr >
                    <td colspan="2">
                        &nbsp;
                    </td>
                </tr>

                <tr >
                    <td style="text-align: left;">
                        <bean:message key="label.reason"/>
                    </td>
                    <td style="text-align: left;">
                         <input style="width: 100%;" id="reason" name="reason" class="default-textbox" />
                    </td>

                </tr>
                <tr id="reqMsgTr" style="display: none">
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        <label id="requiredField">&nbsp;</label>
                    </td>
                </tr>


                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>


                <tr  style="vertical-align: bottom;">

                    <td colspan="2"  >
                        <table STYLE="width:100%" border=0">
                            <tr>
                                <td STYLE="text-align: left">
                                    <input class="hidden" name="<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>"
                                           value="<%=Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE))%>"
                                           type="hidden">
                                    <input class="hidden" name="back" value="my-account.php" type="hidden">
                                    <input type="hidden" name="registerOrLogin" value="login"/>
                                    <a href="javascript: void(0);" class="btn-first floatright"  onclick="gotoUnsubscribe()">
                                        <bean:message key="button.remove"/>
                                    </a>
                                    <a href="javascript: void(0);" class="btn-second floatright"
                                       onclick="gotoRegistrationPage();">Cancel</a>
                                 </td>
                            </tr>
                        </table>

                    </td>

                </tr>
            </table>
        </form>
    </div>

</div>
</div>
<script type="text/javascript">


    function gotoUnsubscribe(){
       // document.getElementById('subscribeForm').action = '<%=context%>/login.do?operation=unsubUser';
        document.getElementById('requiredField').innerHTML = "";
        $('#subscribeForm').submit();
    }

    $(function() {
        //--Saeid AmanZadeh -
        var forgotSendStatus = "<%=request.getAttribute("unsubscribed")%>";
        if(forgotSendStatus == "submited"){
            if(${enState}){
                document.getElementById("forgotResultMsg").innerHTML = "Your email has been unsubscribed successfully";
            }else{
                document.getElementById("forgotResultMsg").innerHTML = "Votre email a été désabonné avec succès";
            }
            $('#loginBottonTr').hide();
            $('#resultMsgTr').show();
            $('#msgTr').hide();
        }
        else if(forgotSendStatus == "faild"){
            if(${enState}){
                document.getElementById("forgotResultMsg").innerHTML = "Sorry, we were not able to find an account associated with that email address!";
            }else{
                document.getElementById("forgotResultMsg").innerHTML = "Désolé, nous n'avons pas été en mesure de trouver un compte associé à cette adresse e-mail !";
            }
            $('#loginBottonTr').hide();
            $('#resultMsgTr').show();
            $('#msgTr').hide();
        }


    });

</script>