<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page import="com.sefryek.doublepizza.web.form.DeliveryAddressForm" %>--%>

<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if (basket != null) {
        int totalOfItems = basket.getNumberOfItems();
        BigDecimal totalPrice = basket.calculateTotalPrice();
    }
//    DeliveryAddressForm deliveryAddressForm = (DeliveryAddressForm) request.getAttribute("deliveryAddressForm");
    Locale lang=  (Locale) session.getAttribute(Globals.LOCALE_KEY);

%>

<link rel="stylesheet" href="<%=context%>/css/validationEngine.jquery.css" type="text/css"/>
<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine-en.js"
        charset="utf-8"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine-fr.js"
        charset="utf-8"></script>


<%--<script type="text/javascript" src="<%=context%>/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" media="all" type="text/css" href="<%=context%>/css/smoothness/jquery-ui.css" />

<script type="text/javascript" src="<%=context%>/js/jquery.datepick.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datepick.css">--%>

<SCRIPT LANGUAGE="javascript">

    function goNextElemnt(sender, next) {
        var len = sender.value.length;
        var maxLen = sender.maxLength;

        if (len >= maxLen)
            $('#' + next).focus();
    }

    function isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    }

    function seperateCharNum(streetNo, postalcode_1st, postalcode_2nd){
        var digits = streetNo.replace(/\D/g, "");
        var letters = streetNo.replace(/[^a-z]/gi, "");
        //alert(digits);
        //alert(letters);
        document.getElementById('hideSuiteAPT').value = letters;
        document.getElementById('hideStreetNo').value = digits;
        if(digits != null && digits != "")
            autoCompleteStreet(digits, postalcode_1st, postalcode_2nd);
    }

    function setFieldBeforSubmit(){
        if($("#hideStreetNo").val().trim() != "")
            $('#streetNo').val($("#hideStreetNo").val().trim());
        if($("#hideSuiteAPT").val().trim() != "")
            $('#suiteApt').val($("#hideSuiteAPT").val().trim() + " " + $("#suiteApt").val().trim());
        $('#userForm').submit();
    }

</SCRIPT>
<%--<div id="MiddleBlock" class="nav-line">--%>
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
<div class="cusfood"><bean:message key="label.page.title.register"/></div>

<div class="LeftMiddleWrapper">
<%--======================================================================== The form register--%>
<div id="columns2">
<form action="<%=context%>/register.do?operation=register" method="post" id="userForm" name="userForm" class="std">
<div id="center_column" class="center_column">
    <div id="center_column_inner2">

        <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><bean:message key="lable.your.personal.information"/></td>
            </tr>
        </table>
        <fieldset class="deliveryFieldSet delivery_type">

            <table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
                <tr>
                    <%--First Name--%>
                    <td width="11%">
                        <label for="firstName"><bean:message key="lable.registration.firstName"/></label>
                    </td>
                    <td class="required text">
                        <input class="default-textbox" onkeyup="$('#firstname').val(this.value);" id="firstName"
                               name="firstName" type="text" maxlength="50"
                        <logic:present name="userForm">
                               value="${userForm.firstName}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>
                    </td>
                    <%--Last Name--%>
                    <td width="9%">
                        <label for="lastName"><bean:message key="lable.registration.lastName"/></label>
                    </td>
                    <td class="required text" width="25%">

                        <input class="default-textbox" onkeyup="$('#lastname').val(this.value);" id="lastName"
                               name="lastName" type="text" maxlength="50"
                        <logic:present name="userForm">
                               value="${userForm.lastName}"
                        </logic:present>
                                >

                        <sup class="required">*</sup>

                    </td>
                    <td width="20%">&nbsp;</td>
                </tr>
                <tr>
                    <td width="9%">
                        <bean:message key="label.birthday.page.registration"/>:
                    </td>
                    <td width="25%">
                        <input class="default-textbox" type="text" id="birthDate" name="birthDate"
                        <logic:present name="userForm">
                               value="${userForm.birthDate}"
                        </logic:present>
                                >
                    </td>

                    <td width="9%">

                    </td>
                    <td width="25%">

                    </td>
                    <td width="20%">&nbsp;</td>
                </tr>
                <tr>
                    <td><bean:message key="lable.title"/>:</td>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">

                            <%--==========================================================--%>
                            <tr>
                                <td width="27%">
                                    <label class="button info-title">
                                        <input name="title" id="title" value="<%=User.Title.MALE.toString()%>"
                                               type="radio" checked>
                                        <span class="outer"><span class="inner"></span></span><bean:message
                                            key="lable.mr"/>
                                    </label>
                                </td>

                                <td width="50%">
                                    <label class="button info-title">
                                        <input name="title" id="title" value="<%=User.Title.FEMALE.toString()%>"
                                               type="radio">
                                        <span class="outer"><span class="inner"></span></span><bean:message
                                            key="lable.ms"/>
                                    </label>
                                </td>
                            </tr>
                            <%--===========================================================--%>

                        </table>
                    </td>

                </tr>
                <tr>
                    <%--email --%>


                    <td width="9%">
                        <label for="email"><bean:message key="lable.registration.email"/></label>
                    </td>
                    <td class="required text" width="33%">
                        <input class="default-textbox" id="email" name="email" type="text" maxlength="50"
                        <logic:present name="userForm">
                               value="${userForm.email}"
                        </logic:present>
                        <logic:present parameter="email">
                               value="${param['email']}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>
                    </td>
                    <td width="1%">&nbsp;</td>
                    <td width="25%">&nbsp;</td>
                    <td width="20%">&nbsp;</td>

                </tr>
                <tr>
                    <%--Password --%>
                    <td width="9%">
                        <label for="password"><bean:message key="lable.registration.password"/>
                    </td>
                    <td class="required password" width="25%">
                        <input class="default-textbox" name="password" id="password" type="password" maxlength="100"

                        <logic:present name="userForm">
                               value="${userForm.password}"
                        </logic:present>

                                >
                        <sup class="required">*</sup> <br>
                        <span class="form_info"><bean:message key="lable.registration.attention"/></span>
                    </td>
                    <%--Verify Password--%>
                    <td width="12%">
                        <label for="verifyPassword"><bean:message key="lable.registration.verify.password"/></label>
                    </td>
                    <td class="required password" width="25%">
                        <input class="default-textbox" name="verifyPassword" id="verifyPassword" type="password"
                               maxlength="100"
                        <logic:present name="userForm">
                               value="${userForm.verifyPassword}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>
                    </td>
                    <td width="20%">&nbsp;</td>
                </tr>

            </table>
            <br class="clear">
            <table>
                <tr>
                    <td width="70%" >
                        <input type="checkbox" class="btn_clear" name="subscribed" id="subscribed" onclick="if((this.checked)){this.value='True'}else{this.value='False'}"

                        <logic:present name="userForm">
                               value="${userForm.subscribed}"
                        </logic:present>
                                >
                        <label for="subscribed" class="css-label">
                            <bean:message key="label.campaign"/>
                        </label>

                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
</div>

<br class="clear">
<table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><bean:message key="lable.your.address"/></td>
    </tr>
</table>

<%--======================================================================== The Order Goes to: Delivery--%>
<fieldset id="deliveryAddressInfo" class="deliveryFieldSet delivery_type">
    <table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
        <tr>
            <td width="10%">
                <label for="addressName"><bean:message key="label.user.address.name"/></label>
            </td>
            <td>
                <input class="default-textbox" name="addressName" id="addressName" type="text" size="12" value="<bean:message key="user.address.name.default"/>">
            </td>
            <td width="30%">
                <div style="position: relative;left: -122px">
                    <small style="font-style:italic; color:#8e8d8d;">
                        <span><bean:message key="label.user.address.name.home.office"/></span>
                    </small>
                </div>
            </td>
        </tr>
        <tr>
            <%--Postal Code--%>
            <td width="9%">
                <label for="postalCode1"><bean:message key="lable.registration.postalCode"/></label>
            </td>
            <td class="required postcode text" width="25%">
                <input class="default-textbox" name="postalCode1" id="postalCode1" type="text" size="3" maxlength="3"
                       onkeyup="autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value); goNextElemnt(this, 'postalCode2');"
                       onchange="autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value);"
                <logic:present name="userForm">
                       value="${userForm.postalCode1}"
                </logic:present>
                        >
                <span style="vertical-align:middle; font-size:14px;padding-bottom: 10px;">-</span>
                <input class="default-textbox" name="postalCode2" id="postalCode2" type="text" size="3" maxlength="3"
                       onkeyup="autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
                       onchange="autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
                <logic:present name="userForm">
                       value="${userForm.postalCode2}"
                </logic:present>
                        >
                <sup class="required">*</sup>
            </td>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
        </tr>
        <tr>
            <%--Street NO--%>
            <td width="9%">
                <label for="streetNo"><bean:message key="lable.registration.streetNo"/></label>
            </td>
            <td class="required text" width="25%">
                <input class="default-textbox" name="streetNo" id="streetNo" type="text" maxlength="8"
                       onkeyup="seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                       onchange="seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                <logic:present name="userForm">
                       value="${userForm.streetNo}"
                </logic:present>
                        >
                <input type="hidden" name="hideStreetNo" id="hideStreetNo" />
                <sup class="required">*</sup></td>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="20%">&nbsp;</td>

        </tr>
        <tr>
            <td id="p_street" width="11%">
                <label for="street"><bean:message key="lable.registration.street"/></label>
            </td>
            <%--Street(this field should be filled automaticaly)--%>
            <td class="required text" width="32%">
                <input class="default-textbox" id="street" name="street" type="text" maxlength="50"
                        <logic:present name="userForm">
                            value="${userForm.street}"
                        </logic:present>
                        />
                <sup class="required">*</sup>
            </td>
            <%--Suite/Apt--%>
            <td width="26%">
                <div style="position: relative;left: -6px"><label for="suiteApt"><bean:message
                        key="lable.registration.suite.apt"/></label></div>
            </td>
            <td>
                <div style="position: relative; left: -173px">
                    <input class="default-textbox" name="suiteApt" id="suiteApt" type="text" size="12"
                           class="default-textbox"
                            <logic:present name="userForm">
                                value="${userForm.suiteApt}"
                            </logic:present>
                            />
                    <input type="hidden" name="hideSuiteAPT" id="hideSuiteAPT" />
                </div>
            </td>

        </tr>
    </table>
    <table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
        <tr>
            <%--City--%>
            <td width="11%">
                <label for="city"><bean:message key="lable.registration.city"/></label>
            </td>
            <td class="required text" width="31%">
                <input class="default-textbox" name="city" id="city" type="text" maxlength="50"
                <logic:present name="userForm">
                       value="${userForm.city}"
                </logic:present>
                        >
                <sup class="required">*</sup>
            </td>
            <%--DoorCode--%>
            <td class="text" width="10%">
                <label for="doorCode"><bean:message key="lable.registration.doorCode"/></label>
            </td>
            <td class="text" width="25%">
                <input name="doorCode" id="doorCode" type="text" size="12" class="default-textbox"
                <logic:present name="userForm">
                       value="${userForm.doorCode}"
                </logic:present>
                        >
            </td>
            <td width="20%"></td>
        </tr>
        <tr>
            <td width="9%">
                <label for="building"><bean:message key="lable.registration.building"/></label>
            </td>
            <td class="required text" width="25%">
                <input class="default-textbox" name="building" id="building" type="text" size="12"
                <logic:present name="userForm">
                       value="${userForm.building}"
                </logic:present>
                        >

            </td>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
        </tr>
        <tr>
            <%--Home Phone--%>
            <td width="9%">
                <label style="padding-top:6px;" for="cellPhone1"><bean:message
                        key="lable.registration.home.phone"/></label>
            </td>
            <td class="required cellphone text" width="25%">
                <input class="default-textbox" style="vertical-align:middle;" name="cellPhone1" id="cellPhone1"
                       type="text" size="3" maxlength="3" onkeypress="return isNumber(event)" onkeyup="goNextElemnt(this, 'cellPhone2');"

                <logic:present name="userForm">
                       value="${userForm.cellPhone1}"
                </logic:present>
                        >
                <%--<a style="vertical-align:middle;"> -</a>--%>
                <span style="vertical-align:middle;">-</span>
                <input class="default-textbox" style="vertical-align:middle;" name="cellPhone2" id="cellPhone2"
                       type="text" size="3" maxlength="3" onkeypress="return isNumber(event)" onkeyup="goNextElemnt(this, 'cellPhone3');"

                <logic:present name="userForm">
                       value="${userForm.cellPhone2}"
                </logic:present>
                        >
                <%--<a style="vertical-align:middle;"> -</a>--%>
                <span style="vertical-align:middle;">-</span>
                <input class="default-textbox" style="vertical-align:middle;" onkeypress="return isNumber(event)" name="cellPhone3" id="cellPhone3"
                       type="text" size="4"
                       maxlength="4"

                <logic:present name="userForm">
                       value="${userForm.cellPhone3}"
                </logic:present>
                        >
                <sup class="required">*</sup>
            </td>
            <td width="9%">
                <label style="width:12%;text-align:left;padding-top:6px;" for="ext"><bean:message
                        key="lable.registration.home.ext"/></label>
            </td>
            <td width="25%">
                <input class="default-textbox" name="ext" id="ext" type="text" size="12" maxlength="15"
                <logic:present name="userForm">
                       value="${userForm.ext}"
                </logic:present>
                        >
            </td>
            <td width="20%">&nbsp;</td>
        </tr>
        <tr>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
        </tr>
    </table>
</fieldset>

<fieldset class="deliveryFieldSet">
    <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><bean:message key="label.your.company"/></td>
    </table>
    <table class="font" width="100%" border="0" cellspacing="2" cellpadding="0">
        <tr>
            <td width="9%">&nbsp;</td>
            <td width="73%">&nbsp;</td>
        </tr>
        <tr>
            <td class="font" widh="30%"><bean:message key="label.your.company"/></td>
            <td><input class="default-textbox"  name="company" id="company" type="text"
                <logic:present name="userForm">
                    value="${userForm.company}"
                </logic:present> >
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>

            </td>
        </tr>

    </table>

</fieldset>
<input type="hidden" name="<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>"
       value="<%=request.getParameter(Constant.LOGIN_OR_REGIISTER_SOURCE)%>"/>
<input type="hidden" name="registerOrLogin" value="register"/>
<input name="email_create" value="1" type="hidden">
<input name="is_new_customer" value="1" type="hidden">
<input name="lang" value="<%=lang.toString()%>" type="hidden">


<a href="javascript: void(0);" class="btn-first floatright"
   onclick="setFieldBeforSubmit();"><bean:message key="label.page.title.register"/> </a>
<%--<a  class="btn-second FloatRight" href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu"><bean:message key="button.returnToOrder"/> </a>--%>

</form>
</div>
</div>
<%--</div>--%>
<script type="text/javascript">
    $(function () {
        $("#birthDate").datepicker({
            dateFormat: 'yy-mm-dd',
            changeMonth: true,
            changeYear: true,
            yearRange: '-100y:c+nn',
            maxDate: '-1d'
        });
    });

    $(document).ready(function () {
        var birthDate = $('#birthDate');
        birthDate.datepicker();

    });
</script>
