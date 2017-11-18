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

<% if(request.getParameter("error") != null) { %>

<%--<logic:messagesPresent message="false">--%>
    <div class="error">
        <ol>
            <bean:message key="message.login.invalid.user.password"/>
            <html:messages id="eMsg" message="false">
                <logic:present name="eMsg">
                    <li><bean:write name="eMsg" filter="false"/></li>
                </logic:present>
            </html:messages>
        </ol>
    </div>
<%--</logic:messagesPresent>--%>

<%  } %>

<p style="padding: 15px;" class="cusfood"><bean:message key="label.page.title.login"/></p>

<div class="LeftMiddleWrapper">

    <div style="float: left;width: 56%;clear: left;">

        <form action="<%=context%>/j_security_check" method="post" id="login_form">
            <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><bean:message key="message.registration.already.registered"/>? <bean:message
                            key="message.registration.want.to.register"/></td>
            </table>
            <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="88%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="lable.registration.email"/></td>
                    <td><input style="width: 100%;" id="email" name="j_username" class="default-textbox" type="text"/></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="lable.registration.password"/></td>
                    <td><input style="width: 100%;" id="password" name="j_password" class="default-textbox"
                               type="password"/></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table border="0" style="width: 100%">
                            <tr >
                                <td  style="width: 40%; vertical-align: middle;text-align: left;">
                                    <a href="javascript: void(0);" class="font" style="color:#ad040a;text-decoration: none;font-size: 15"
                                       onclick="openForgotenPassFrm();">
                                        <bean:message key="lable.registration.forgot.your.password"/>
                                    </a>

                                    <input class="hidden" name="<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>"
                                           value="<%=Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE))%>"
                                           type="hidden">
                                    <input name="back" value="my-account.php" type="hidden">
                                    <input type="hidden" name="registerOrLogin" value="login"/>
                                </td>
                                <td>
                                    <a href="javascript: void(0);" class="btn-first floatright"
                                       onclick="document.getElementById('login_form').submit()"><bean:message
                                            key="label.page.title.login"/></a>
                                    <a href="javascript: void(0);" class="btn-second floatright"
                                       onclick="gotoRegistrationPage();"><bean:message key="lable.registration.create.your.accunt"/></a>
                                 </td>
                            </tr>
                        </table>
                    </td>
                </tr>

            </table>



        </form>

        <div style="padding-top:60px;">
            <table class=" Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="40%">
                        <hr color="#c2bfbf">
                    </td>
                    <td width="3%" style="text-align:center;"><span style="font-size:20px;"><bean:message
                            key="label.filter.or"/> </span></td>
                    <td width="40%">
                        <hr color="#c2bfbf">
                    </td>
                </tr>
            </table>
        </div>
        <table width="105%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <p class="font5" style="width: 500px;line-height: 50px;">
                        <bean:message key="message.registration.do.not.skip"/>
                    </p>
                </td>
                <%--<td width="30%"><a href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu" class="black_btn2 FloatLeft"><bean:message key="button.continueShopping"/></a></td>--%>
            </tr>
            <tr>
                <%
                    if (basket != null && basket.getBasketItemList().size() > 0) {
                %>
                <td style="
                        font-size: 20px;
                        font-family:'ItcKabelMedium',Arial;
                    ">
                    <bean:message key="message.registration.skip.registeration"/>
                </td>
                <td>
                    <div style="position: relative;right: 205px;">
                        <a class="btn-first floatright" style="width: 193px;text-align: center"
                           href="<%=context%>/checkout.do?operation=goToDeliveryAddress"><bean:message
                                key="label.checkoutasGuest"/></a>
                    </div>
                </td>
                <td>


                    <%
                        }
                    %>
                </td>
            </tr>
        </table>
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
</div>
<script type="text/javascript">
    $(function() {
        var el=document.getElementById('login_form');
        $(document).keypress(function(event) {
            if (event.which == 13) {
                el.submit();
            }
        });
    });
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

    function openForgotenPassFrm(){
        document.getElementById('login_form').action = '<%=context%>/login.do?operation=forgetPasswordPage';
        $('#login_form').submit();
    }

</script>