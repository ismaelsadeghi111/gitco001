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
    String email = request.getParameter("email");
    String userId = request.getParameter("userId");
    String passMessage = request.getParameter("passMessage");

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


    <p style="padding: 15px;" class="cusfood"><bean:message key="label.page.title.reset.password" /></p>


<div class="LeftMiddleWrapper">

    <div style="float: left;width: 56%;clear: left;">

        <form action="<%=context%>/login.do" method="post" id="login_form">
           <input type="hidden" value="saveResetPassword" id="operation" name="operation">
            <input id="flagChgPass" name="flagChgPass" type="hidden" value="${flagChgPass}" >
            <input id="userId" name="userId" type="hidden" value="${userId}" >
            <input id="email" name="email" type="hidden" value="${email}" >
            <input id="flagSecurity" name="flagSecurity" type="hidden" value="${flagSecurity}" >

            <input id="passMessage" name="passMessage" type="hidden" value="${passMessage}" >


            <input id="lang" name="lang" type="hidden" value="${enState}">
            <table class="border Fullbasket-msg font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr id="">
                    <td style="text-align: left;">
                        <label id="captionLb">
                        &nbsp;
                        </label>
                     </td>
                <tr>

            </table>

          <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0" id="tblForm">
              <tr>
                  <td colspan="3" style="height: 30px">
                      <label id="requiredField">&nbsp;</label>
                      <br>
                  </td>
              </tr>

              <tr >
                  <td style="width: 40%;">
                      <bean:message key="lable.reset.password"/>&nbsp;&nbsp;&nbsp;
                  </td>
                  <td style="width: 40%;text-align: left;">
                      <input type="password" size="20" id="newPassword" name="newPassword" class="default-textbox" onkeyup="nospaces(this)"/>
                  </td>
                  <td style="width: 20%; text-align: left;">
                      <sup class="required">*</sup>

                  </td>

              </tr>
              <tr>
                  <td colspan="3" style="height: 20px">

                  </td>
              </tr>
              <tr >
                  <td  >
                      <bean:message key="lable.reset.verify.password"/>&nbsp;&nbsp;&nbsp;
                  </td>
                  <td style="text-align: left;">
                      <input type="password" size="20" id="verifyNewPassword" name="verifyNewPassword" class="default-textbox" onkeyup="nospaces(this)"/>
                  </td>
                  <td style="text-align: left;" >
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
                      <input class="btn-first" class="black_btn FloatLeft" type="submit" name="submitChgPass" id="submitChngPass" value="<bean:message key="lable.submit.title"/>"  />
                  </td>
                  <td></td>
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

    function nospaces(t){
        if(t.value.match(/\s/g)){
            t.value=t.value.replace(/\s/g,'');
        }
    }

    function submitResetPassword(){
        if($.trim($('#newPassword').val()).length > 6 ){
            if($.trim($('#newPassword').val()) == $.trim($('#verifyNewPassword').val())){
                document.getElementById('login_form').action = '<%=context%>/login.do?operation=submitResetPassword';
                document.getElementById('requiredField').innerHTML = "";
                $('#login_form').submit();
            }else{
                document.getElementById('requiredField').innerHTML = "<font style='font-size:15px; color: #777777'><bean:message key="message.reset.password.mach.alarm"/></font>";
            }
        }else{
          document.getElementById('requiredField').innerHTML = "<font style='font-size:15px; color: #777777'><bean:message key="message.reset.password.lenght.alarm"/></font>";
        }


    }



    $(document).ready(function () {
        if($.trim($('#flagChgPass').val()) == "True" ){
            document.getElementById("captionLb").innerHTML = "<bean:message key="message.registration.reset.password.successful"/>";
            document.getElementById("tblForm").style.visibility="hidden";

        }else{
            document.getElementById("captionLb").innerHTML = "<bean:message key="message.registration.set.email.enter.password"/>";
        }
        if($.trim($('#flagSecurity').val()) == "False" ){
            document.getElementById("captionLb").innerHTML = "<bean:message key="message.registration.reset.security.not.fix"/>";
            document.getElementById("tblForm").style.visibility="hidden";

        }


        $("#login_form").validate({
            rules:{
                newPassword:{required:true, minlength:5/*, notSpace:true*/},
                verifyNewPassword:{required:true, minlength:5/*, notSpace:true*/, equalTo:"#newPassword"}
            },
            messages:{
                newPassword:{
                    required:'<bean:message key="message.registration.change.required" />',
                    minlength:'<bean:message key="message.registration.change.minlength"/>'
                },
                confirmPassword:{
                    equalTo:'<bean:message key="message.registration.change.confirm" />',
                    required:'<bean:message key="message.registration.change.required" />',
                    minlength:'<bean:message key="message.registration.change.minlength"/>'
                }
            }
        });
    });

</script>