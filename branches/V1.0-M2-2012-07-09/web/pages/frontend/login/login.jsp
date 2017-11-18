<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String context= request.getContextPath();
    Basket basket= (Basket)request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems=basket.getNumberOfItems();
    BigDecimal totalPrice= basket.calculateTotalPrice();
%>

<script type="text/javascript">

    function gotoRegistrationPage() {

        var myForm = document.createElement('form');
        document.body.appendChild(myForm);

        var operation = document.createElement('input');
        operation.setAttribute('type' ,'hidden');
        operation.setAttribute('name' ,'operation');
        operation.setAttribute('value' ,'goToRegistrationPage');

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
</script>


<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="center_column">
        <div id="center_column_inner2">
            <h5><bean:message key="label.page.title.login" /></h5>
            <%--show errors--%>
            <logic:messagesPresent message="false">
                <div class="error">
                    <ol>
                        <bean:message key="label.registration.errors"/> :
                        <html:messages id="aMsg" message="false">
                            <logic:present name="aMsg">
                                <li><bean:write name="aMsg" filter="false" /></li>
                            </logic:present>
                        </html:messages>
                    </ol>
                </div>
            </logic:messagesPresent>


            <form action="<%=context%>/login.do?operation=login" method="post" id="login_form" class="std">
                <fieldset>
                    <h3><bean:message key="message.registration.already.registered"/>? <bean:message key="message.registration.want.to.register"/></h3>
                    <p class="text">
                        <label for="email"><bean:message key="lable.registration.email"/></label>
                        <input id="email" name="email" class="account_input" type="text">
                    </p>
                    <p class="text">
                        <label for="password"><bean:message key="lable.registration.password"/> </label>
                        <input id="password" name="password" value="" class="account_input" type="password">
                    </p>
                    <p class="submit" style="padding: 20px 0 0 76px;">
                        <input class="hidden" name="<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>" value="<%=Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE))%>" type="hidden">
                        <input class="hidden" name="back" value="my-account.php" type="hidden">
                        <input type="hidden" name="registerOrLogin" value="login" />           
                        <input id="SubmitLogin" name="SubmitLogin" class="button" style="width: 127px; display:inline-block;" value="<bean:message key="label.page.title.login"/>" type="submit">
                        <input style="display:inline-block; width:184px;" name="goHome" id="goHome" value="<bean:message key="lable.registration.create.your.accunt"/>" class="exclusive" type="button" onclick="gotoRegistrationPage();">
                    </p>

                    <%--:TODO unComment this line after its action implemented completely: next faze--%>
                    <%--<p class="lost_password"><a href="http://livedemo00.template-help.com/prestashop_34919/password.php"><bean:message key="message.registration.forgot.your.password"/>?</a></p>--%>
                </fieldset>
            </form>


            <c:if test="<%=Boolean.valueOf(request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE))%>">
                <form action="<%=context%>/checkMail.do?operation=goToRegistrationPage" method="post" id="create-account_form" class="std">
                    <fieldset>
                        <h3><bean:message key="message.registration.skip.step"/><span style="text-transform:none; color:red;"> <bean:message key="message.registration.not.recommended"/></span></h3>
                        <div id="checkout_instruction"><bean:message key="message.registration.do.not.skip"/></div>

                        <p class="cart_navigation" style="width:94%; margin-left:3%; margin-right:5%" >
                            <a href="<%=context%>/checkout.do?operation=goToDeliveryAddress" class="exclusive" title="<bean:message key="message.registration.click.here"/>">
                                <bean:message key="message.registration.click.here"/> &raquo;
                            </a>
                            <span class="skip"><bean:message key="message.registration.skip.registeration"/></span>
                            <a href="javascript:void(0);" class="button_large" title="Continue shopping" onclick="continueShopping();">
                                &laquo; <bean:message key="button.continueShopping"/>
                            </a>
                        </p>
                    </fieldset>
                </form>
            </c:if>

            <%--</div>--%>
        </div>
    </div>
    <!-- Right -->
    <div id="right_column" class="column carousel_on">
        <!-- MODULE Block cart -->
        <div id="cart_block" class="block exclusive">
            <h4>
                <bean:message key="lable.cart"/>
                <span id="block_cart_expand" class="hidden">&nbsp;</span>
                <span id="block_cart_collapse">&nbsp;</span>
            </h4>
            <div class="block_content2">

                <%--</div>--%>
                <!-- block list of products -->
                <div id="cart_block_list" class="expanded">
                    <div class="cart-prices">
                        <div class="cart-prices-block">
                            <span><bean:message key="label.item.no"/></span>
                            <span id="cart_block_shipping_cost" class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                        </div>
                        <div class="cart-prices-block">
                            <span><bean:message key="lable.total.price"/> </span>
                            <span id="cart_block_total" class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%></span>
                        </div>
                    </div>
                    <p id="cart-price-precisions">
                        <bean:message key="message.price.with.tax"/>
                    </p>
                </div>
            </div>
        </div>
        <!-- /MODULE Block cart -->
    </div>
    <div id="right_column_continue" >
        <ul>
            <li>
                <a class="two_chef" title="<bean:message key="label.page.footer.link.franchise"/>" href="<%=request.getContextPath()%>/frontend.do?operation=goToFranchisingPage">
                </a>
            </li>
        </ul>

    </div>
    <br class="clear">
</div>
