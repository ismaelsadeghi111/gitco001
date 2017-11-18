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

<logic:messagesPresent message="false">
    <div class="error">
        <ol>
            <bean:message key="label.registration.errors"/> :
            <html:messages id="aMsg" message="false">
                <logic:present name="aMsg">
                    <li><bean:write name="aMsg" filter="false"/></li>
                </logic:present>
            </html:messages>
        </ol>
    </div>
</logic:messagesPresent>
<logic:messagesPresent message="true">
    <html:messages id="message" message="true">
        <bean:write name="message"/><br/>
    </html:messages>
</logic:messagesPresent>

<p style="padding: 15px;" class="cusfood" ><bean:message key="label.page.title.forgot.password" />?</p>

<div class="LeftMiddleWrapper">

    <div style="float: left;width: 56%;clear: left;">

        <form action="<%=context%>/login.do" method="post" id="login_form">
            <input type="hidden" value="login" id="operation" name="operation">
            <input id="lang" name="lang" type="hidden" value="${enState}">
            <table class="border Fullbasket-msg font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr id="forgetPassMsgTr">
                    <td>
                        <label id="forgotResultMsg">
                            <bean:message key="message.registration.set.email.send.forget.password"/>
                        </label>
                  </td>
                <tr>
            </table>
          <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <td>
                      &nbsp;
                  </td>
              </tr>
                <tr >
                    <td style="width: 10%;">
                              <bean:message key="lable.registration.email"/>&nbsp;&nbsp;&nbsp;
                    </td>
                    <td style="width: 50%;">
                        <input size="30" id="emailForgotPassword" name="emailForgotPassword" class="default-textbox" />
                    </td>
                    <td style="width: 40%">
                        <sup class="required">*</sup>
                        <label id="requiredField">&nbsp;</label>
                    </td>

                </tr>
              <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
              </tr>
              <tr>
                  <td colspan="2" style="width: 20%;text-align: right;vertical-align: middle;">

                      <a href="javascript: void(0);" class="btn-first floatright"
                         onclick="sendForgetPassword()">
                          <bean:message key="label.send.forgot.password"/>
                      </a>
                  </td>
                  <td>

                  </td>
              </tr>
              <tr>
                    <td colspan="3">&nbsp;</td>
              </tr>
            </table>
        </form>
    </div>
     <div style="float: right;width: 42%;">
        <div style="padding-top: 120px;margin-left:-6px;">
            <c:if test="${enState}">
                <img src="<%=context%>/images/tasty-pizza-en.png"/>
            </c:if>
            <c:if test="${frState}">
                <img src="<%=context%>/images/tasty-pizza-fr.png"/>
            </c:if>

        </div>
    </div>
</div>


<script type="text/javascript">

    function gotoRegistrationPage() {

        var myForm = document.createElement('form');
        document.body.appendChild(myForm);

        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'goToRegistrationPage');

        var login = document.createElement('input');
        login.setAttribute('type', 'hidden');
        login.setAttribute('name', '<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>');
        login.setAttribute('value', '<%=Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE))%>');

        myForm.appendChild(operation);
        myForm.appendChild(login);

        myForm.method = 'POST';
        myForm.action = '<%=context%>/frontend.do';

        myForm.submit();
    }



    function sendForgetPassword(){
        if($.trim($('#emailForgotPassword').val()).length > 0 ){
            document.getElementById('login_form').action = '<%=context%>/login.do?operation=sendForgetPassword';
            document.getElementById('requiredField').innerHTML = "";
            $('#login_form').submit();
        }else{
          document.getElementById('requiredField').innerHTML = "<font style='font-size:15px; color: #777777'><bean:message key="message.registration.change.required"/></font>";
        }


    }


    function gotoLoginForm(){

        //$('#operation').val('login');
        document.getElementById('login_form').action = '<%=context%>/login.do?operation=login';
        //alert(document.getElementById('login_form').action);
        document.getElementById('login_form').submit();
    }

    $(function() {
        //--Saeid AmanZadeh - forgot password
        var forgotSendStatus = "<%=request.getAttribute("forgotSendStatus")%>";
        if(forgotSendStatus == "sent"){
            if(${enState}){
                document.getElementById("forgotResultMsg").innerHTML = "We have sent reset password link, please check your Email.";
            }else{
                document.getElementById("forgotResultMsg").innerHTML = "Nous avons envoyé lien de réinitialisation de mot, s'il vous plaît vérifier votre e-mail.";
            }
        }
        else if(forgotSendStatus == "invalidEmail"){
            if(${enState}){
                document.getElementById("forgotResultMsg").innerHTML = "Sorry, we were not able to find an account associated with that email!";
            }else{
                document.getElementById("forgotResultMsg").innerHTML = "Désolé, nous n'avons pas été en mesure de trouver un compte associé à cette e-mail !";
            }
        }


        var el=document.getElementById('login_form');
        $(document).keypress(function(event) {
            if (event.which == 13) {
                el.submit();
            }
        });
    });

</script>