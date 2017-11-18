<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
%>
<SCRIPT LANGUAGE="javascript">

    function goNextElemnt(sender, next){
        var len = sender.value.length;
        var maxLen = sender.maxLength;

        if (len >= maxLen)
            $('#' + next).focus();
    }

</SCRIPT>

<div id="columns2">
<!-- Center -->
<div id="center_column" class="center_column">
<div id="center_column_inner2">

<h5><bean:message key="label.page.title.register"/></h5>

<%--show errors--%>
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

<form action="<%=context%>/register.do?operation=register" method="post" id="userForm" name="userForm" class="std">

<fieldset class="account_creation">
<h3><bean:message key="lable.your.personal.information"/></h3>

<p class="radio required">
    <span><bean:message key="lable.title"/></span>

    <input name="title" id="title" value="<%=User.Title.MALE.toString()%>" type="radio" checked>
    <label class="top"><bean:message key="lable.mr"/></label>
    <input name="title" id="title" value="<%=User.Title.FEMALE.toString()%>" type="radio">
    <label class="top"><bean:message key="lable.ms"/></label>
</p>

<p class="required text">
    <label for="firstName"><bean:message key="lable.registration.firstName"/></label>

    <input onkeyup="$('#firstname').val(this.value);" id="firstName" name="firstName" type="text" maxlength="50"

    <logic:present name="userForm">
           value="${userForm.firstName}"
    </logic:present>
            >


    <sup>*</sup>
</p>

<p class="required text">
    <label for="lastName"><bean:message key="lable.registration.lastName"/></label>
    <input onkeyup="$('#lastname').val(this.value);" id="lastName" name="lastName" type="text" maxlength="50"

    <logic:present name="userForm">
           value="${userForm.lastName}"
    </logic:present>
            >
    <sup>*</sup>

</p>

<p class="required text">
    <label for="email"><bean:message key="lable.registration.email"/></label>
    <input id="email" name="email" type="text" maxlength="50"

    <logic:present name="userForm">
           value="${userForm.email}"
    </logic:present>
    <logic:present parameter="email">
           value="${param['email']}"
    </logic:present>
            >
    <sup>*</sup>
</p>

<p class="required password">
    <label for="password"><bean:message key="lable.registration.password"/></label>

    <input name="password" id="password" type="password" maxlength="10"

    <logic:present name="userForm">
           value="${userForm.password}"
    </logic:present>

            >
    <sup>*</sup>
    <span class="form_info"><bean:message key="lable.registration.attention"/></span>
</p>

<p class="required password">
    <label for="verifyPassword"><bean:message key="lable.registration.verify.password"/></label>

    <input name="verifyPassword" id="verifyPassword" type="password" maxlength="10"

    <logic:present name="userForm">
           value="${userForm.verifyPassword}"
    </logic:present>

            >
    <sup>*</sup>
</p>

<p class="text">
    <label for="facebookUsername"><bean:message key="lable.registration.facebook.userName"/></label>
    <input name="facebookUsername" id="facebookUsername" type="text" maxlength="50"

    <logic:present name="userForm">
           value="${userForm.facebookUsername}"
    </logic:present>

            >

</p>


<p class="text">
    <label for="twitterUsername"><bean:message key="lable.registration.twitter.userName"/></label>
    <input name="twitterUsername" id="twitterUsername" type="text" maxlength="50"

    <logic:present name="userForm">
           value="${userForm.twitterUsername}"
    </logic:present>

            >
</p>

</fieldset>
<fieldset class="account_creation">
    <h3><bean:message key="label.your.address"/></h3>

    <%--Postal Code--%>
                <p class="required postcode text">

                    <label for="postalCode1"><bean:message key="lable.registration.postalCode"/></label>
                    <input style="text-transform: uppercase; width:26px;" name="postalCode1" id="postalCode1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'postalCode2');"
                            <logic:present name="userForm">
                           value="${userForm.postalCode1}"
                    </logic:present>
                            >
                    <span style="vertical-align:middle; font-size:14px; padding-bottom:10px;">-</span>
                    <input style="text-transform:uppercase; width: 26px;" name="postalCode2" id="postalCode2" type="text" size="3" maxlength="3"
                            <logic:present name="userForm">
                           value="${userForm.postalCode2}"
                    </logic:present>
                           >
                    <sup>*</sup>
                </p>

     <p class="required text">
        <label for="streetNo"><bean:message key="lable.registration.streetNo"/></label>
        <input name="streetNo" id="streetNo" type="text" maxlength="8"
                                            onkeyup="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                            onchange="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"

        <logic:present name="userForm">
               value="${userForm.streetNo}"
        </logic:present>

                >
        <sup>*</sup>
    </p>

    <%--Street(this field should be filled automaticaly)--%>
            <p id="p_street" class="required text">
                <label for="street"><bean:message key="lable.registration.street"/></label>
                <input id="street" name="street" type="text" maxlength="15"
                       onclick="resetInput();"

                <logic:present name="userForm">
                       value="${userForm.street}"
                </logic:present>                        

                        />
                <sup>*</sup>
            </p>



    <p class="text">
           <label for="suiteApt"><bean:message key="lable.registration.suite.apt"/></label>
           <input name="suiteApt" id="suiteApt" type="text" maxlength="15"

           <logic:present name="userForm">
                  value="${userForm.suiteApt}"
           </logic:present>

                   >
       </p>

    <p class="required text">
        <label for="city"><bean:message key="lable.registration.city"/></label>
        <input name="city" id="city" type="text" maxlength="50"

        <logic:present name="userForm">
               value="${userForm.city}"
        </logic:present>

                >
        <sup>*</sup>
    </p>



        <p class="text">
        <label for="doorCode"><bean:message key="lable.registration.doorCode"/></label>
        <input name="doorCode" id="doorCode" type="text" maxlength="15"

        <logic:present name="userForm">
               value="${userForm.doorCode}"
        </logic:present>

                >
    </p>


    <p class="text">
        <label for="building"><bean:message key="lable.registration.building"/></label>
        <input name="building" id="building" type="text" maxlength="15"

        <logic:present name="userForm">
               value="${userForm.building}"
        </logic:present>
                >
    </p>


    



    <div>
        <p style="display:inline-block;">
            <p2 class="required cellphone text">
                <label style="padding-top:6px;" for="cellPhone1"><bean:message
                        key="lable.registration.home.phone"/></label>
                <input name="cellPhone1" id="cellPhone1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone2');"

                <logic:present name="userForm">
                       value="${userForm.cellPhone1}"
                </logic:present>
                <a style="vertical-align:middle;"> -</a>
                <input name="cellPhone2" id="cellPhone2" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone3');"

                <logic:present name="userForm">
                       value="${userForm.cellPhone2}"
                </logic:present>
                <a style="vertical-align:middle;"> -</a>

            </p2>

            <p3 class="required cellphone text">
                <input style="vertical-align:middle;" name="cellPhone3" id="cellPhone3" type="text" size="4"
                       maxlength="4"

                <logic:present name="userForm">
                       value="${userForm.cellPhone3}"
                </logic:present>

                        >
                <sup>*</sup>
            </p3>
        </p>

        <p style="margin-left:-10px; max-width:40%; display:inline-block;">
            <p2 class="required cellphone text" style="width:100%;" >
            <label style="width:12%;text-align:left;padding-top:6px;" for="ext"><bean:message key="lable.registration.home.ext"/></label>
            <input style="width:40%;vertical-align:middle;" name="ext" id="ext" type="text" size="15" maxlength="15"
            <logic:present name="userForm">
                   value="${userForm.ext}"
            </logic:present>
                    >
        </p2>
        </p>
    </div>


</fieldset>
<fieldset class="account_creation">
    <h3><bean:message key="label.your.company"/></h3>

    <p class="text">
        <label for="company"><bean:message key="label.your.company"/></label>

        <input id="company" name="company" type="text"

        <logic:present name="userForm">
               value="${userForm.company}"
        </logic:present>

                >
    </p>
</fieldset>

<p class="required required_desc">
    <span><sup>*</sup><bean:message key="lable.required.field"/></span>
</p>

<p class="submit">

    <input type="hidden" name="<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>" value="<%=request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE)%>" /> 
    <input type="hidden" name="registerOrLogin" value="register" /> 
    <input name="email_create" value="1" type="hidden">
    <input name="is_new_customer" value="1" type="hidden">
    <input class="hidden" name="back" value="my-account.php" type="hidden">
    <input style="display:inline-block; width:130px;" name="submitAccount" id="submitAccount"
           value="<bean:message key="label.page.title.register"/>" class="exclusive" type="submit">
    <input style="display:inline-block; width:100px;" name="goHome" id="goHome" value="<bean:message key="button.home"/>"
           class="exclusive" type="button" onclick="window.location = '<%=context%>/frontend.do';">


</p>
</form>
</div>
</div>
<!-- Right -->
<div id="right_column" class="column carousel_on">
    <div id="cart_block" class="block exclusive">
        <h4>
            <bean:message key="lable.cart"/>
            <span id="block_cart_expand" class="hidden">&nbsp;</span>
            <span id="block_cart_collapse">&nbsp;</span>
        </h4>

        <div class="block_content2">
            <div id="cart_block_list" class="expanded">

                <div class="cart-prices">

                    <div class="cart-prices-block">
                        <span><bean:message key="label.item.no"/></span>
                        <span id="cart_block_shipping_cost"
                              class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                    </div>

                    <div class="cart-prices-block">
                        <span><bean:message key="lable.total.price"/> </span>
                        <span id="cart_block_total"
                              class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%></span>
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
