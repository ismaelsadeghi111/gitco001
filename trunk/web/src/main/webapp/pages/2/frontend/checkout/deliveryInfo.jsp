<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.web.form.DeliveryAddressForm" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="javax.xml.bind.annotation.XmlElementDecl" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.ListIterator" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 12/29/13
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    String context = request.getContextPath();
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    List<ContactInfo> userContacts = (List<ContactInfo>) request.getAttribute("userContacts");
    DeliveryAddressForm deliveryAddressForm = (DeliveryAddressForm) request.getAttribute("deliveryAddressForm");
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket != null ? basket.calculateTotalPrice(): null;
%>
<script src="<%=context%>/js/jquery/jquery.js"></script>
<script src="<%=context%>/js/jquery/jquery.datetimepicker.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datetimepicker.css"/>

<SCRIPT LANGUAGE="javascript">

    function seperateCharNum(streetNo, postalcode_1st, postalcode_2nd){
        var digits = streetNo.replace(/\D/g, "");
        var letters = streetNo.replace(/[^a-z]/gi, "");

        document.getElementById('hideSuiteAPT').value = letters;
        document.getElementById('hideStreetNo').value = digits;
        if(digits != null && digits != ""){
            //alert(digits);
            //alert(letters);
            autoCompleteStreet(digits, postalcode_1st, postalcode_2nd);
        }
    }

    function setFieldBeforSubmit(){
        if($("#hideStreetNo").val().trim() != "")
            $('#streetNo').val($("#hideStreetNo").val().trim());
        if($("#hideSuiteAPT").val().trim() != "")
            $('#suiteApt').val($("#hideSuiteAPT").val().trim() + " " + $("#suiteApt").val().trim());
       // document.getElementById('deliveryAddressForm').submit()
        $('#deliveryAddressForm').submit();
    }

</script>

<p  class="cusfood" style="padding: 15px;"><bean:message key="label.page.title.delivery.information"/></p>
<div>
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
</div>
<div class="LeftMiddleWrapper">

<form action="<%=context%>/deliveryAddress.do?operation=createUserForDeliveryAddress" method="post" id="deliveryAddressForm" name="deliveryAddressForm">
<fieldset class="deliveryFieldSet">
    <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><bean:message key="lable.your.personal.information"/> </td>
    </table>
    <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="8%">&nbsp;</td>
            <td width="92%">&nbsp;</td>
        </tr>
        <tr>
            <td class="font"><bean:message key="lable.registration.firstName"/>:</td>
            <td>
               <input class="default-textbox" onkeyup="$('#firstname').val(this.value);" id="firstName" name="firstName" type="text" maxlength="50"
                  <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.firstName}"
                  </logic:present>
               >

                <sup class="required">*</sup>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="font"><bean:message key="lable.registration.lastName"/>:</td>
            <td>
               <input class="default-textbox" onkeyup="$('#lastname').val(this.value);" id="lastName" name="lastName" type="text" maxlength="50"
                  <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.lastName}"
                  </logic:present>
               >

                <sup class="required">*</sup>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="font"><bean:message key="lable.registration.email"/>:</td>
            <td>

                <input id="email" name="email" class="default-textbox" type="text" maxlength="50" <c:if test="<%= user != null %>">readonly="true" </c:if>
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.email}"
                </logic:present>
                <logic:present parameter="email">
                       value="${param['email']}"
                </logic:present>
                        >
                <sup class="required">*</sup>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.title"/>:</td>
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="17%"><label class="button info-title">
                            <input name="title" id="title-male" value="<%=User.Title.MALE.toString()%>" type="radio"
                                   <c:if test="<%= deliveryAddressForm == null %>">checked</c:if>
                                   <c:if test="<%= deliveryAddressForm != null && deliveryAddressForm.getTitle() != null && deliveryAddressForm.getTitle().equalsIgnoreCase(User.Title.MALE.toString()) %>">checked</c:if>
                                   <logic:present name="deliveryAddressForm">
                                          value="${deliveryAddressForm.title}"
                                    </logic:present>
                                    >

                            <span class="outer"><span class="inner"></span></span><bean:message key="lable.mr"/></label></td>
                        <td width="83%"><label class="button info-title">

                            <input name="title" id="title-female" value="<%=User.Title.FEMALE.toString()%>" type="radio"
                                   <c:if test="<%= deliveryAddressForm != null &&  deliveryAddressForm.getTitle() != null && deliveryAddressForm.getTitle().equalsIgnoreCase(User.Title.FEMALE.toString()) %>">checked</c:if>
                                   <logic:present name="deliveryAddressForm" >
                                          value="${deliveryAddressForm.title}"
                                    </logic:present>
                                    >
                            <span class="outer"><span class="inner"></span></span><bean:message key="lable.ms"/></label></td>
                    </tr>
                </table>
            </td>

        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
                <input name="reception" id="reception" class="css-checkbox" type="checkbox" checked/>
                <label for="reception" name="demo_lbl_1" class="css-label" style="font:15px/17px Calibri;"><bean:message
                        key="label.registration.reception"/></label>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
</fieldset>
<table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><bean:message key="label.delivery.type"/> </td>
</table>
<table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="8%">&nbsp;</td>
        <td width="92%">&nbsp;</td>
    </tr>
    <tr>
        <td width="17%">
            <label class="button info-title" for="deliveryMode">
                <input type="radio" name="deliveryType" onchange="onDeliveryTypeChange(0);" id="deliveryMode"
                       onclick="changeDeliveryType(0);" value="<%= Order.DeliveryType.DELIVERY.toString()%>" checked/>
                <span class="outer"><span class="inner"></span></span><bean:message key="label.title.delivery.delivery"/>  </label></td>
        <td width="83%">
            <label class="button info-title" for="pickupMode">
                <input type="radio" name="deliveryType" onchange="onDeliveryTypeChange(1);" id="pickupMode"
                       onclick="changeDeliveryType(1);" value="<%= Order.DeliveryType.PICKUP.toString()%>"/>
                <span class="outer"><span class="inner"></span></span><bean:message key="label.delivery.pickup"/> </label></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
</table>
<%--======================================================================== The Order Goes to: Delivery--%>
<fieldset id="deliveryAddressInfo" class="deliveryFieldSet delivery_type">
<table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
<tr>
    <td width="13%">
<c:choose>
<c:when test="<%=user != null%>">
        <label for="addressName"><bean:message key="label.user.address.name"/></label>
</c:when>

</c:choose>
    </td>
    <td>
        <% int   contactInfoListIndex;
            if (  (String) request.getAttribute("contactInfoListIndex") == null) {
                  contactInfoListIndex = 0;
            }  else {
                {
                   contactInfoListIndex = Integer.parseInt((String)request.getAttribute("contactInfoListIndex"));
                }
            }
        %>
        <c:choose>
            <c:when test="<%=user != null%>">

                <select id="addressName" class="drop-down-select" name="contactInfoListIndex" onchange="getDeliveryUserContactInfo(this);">
                    <%
                        if(userContacts != null){
                            ListIterator contactInfoIterator = userContacts.listIterator();
                            for (ContactInfo contactInfo : userContacts){
                                int index = contactInfoIterator.nextIndex();
                                String addressName = " ";
                                if(contactInfo.getAddressScreenEN()!= null){
                                    addressName = contactInfo.getAddressScreenEN()+':';
                                }
                                StringBuilder address = new StringBuilder(addressName);

                                address.append(' ').append(contactInfo.getStreetNo()).append(' ').append(contactInfo.getStreet()).append(' ').append(contactInfo.getCity()).append(' ').append(contactInfo.getBuilding());
                    %>

                    <option value="<%=index%>" <% if (contactInfoListIndex==index)   { out.print("selected");}%>>
                        <%=address%>
                    </option>
                    <%
                                contactInfoIterator.next();
                            }
                        }
                    %>
                </select>
            </c:when>

        </c:choose>
    </td>
</tr>
</table>
<table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">

    <tr>
        <td  width="9%">
<%--            <%if(user==null){ %>
            <label for="addressName"><bean:message key="label.user.address.name"/></label>
            <%}%>--%>
        </td>
        <td>
<%--            <%if(user==null){ %>
            <input class="default-textbox" name="addressName" type="text"  size="12"   value="<bean:message key="user.address.name.default"/>">
                <span>
                    <small style="font-style:italic; color:#8e8d8d; padding-left: 10px;">
                        <span><bean:message key="label.user.address.name.home.office"/> </span>
                    </small>
                </span>
            <%}%>--%>
        </td>
    </tr>

    <tr>
        <%--Postal Code--%>
       <td  width="9%">
           <label for="postalCode1"><bean:message key="lable.registration.postalCode"/></label>
           <input name="contactInfoId" value="${deliveryAddressForm.contactInfoId}" hidden="hidden">
       </td>
        <td class="required postcode text" width="25%">
            <input  class="default-textbox" name="postalCode1" id="postalCode1" type="text" size="3" maxlength="3"
                   onkeyup= "autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value); goNextElemnt(this, 'postalCode2');"
                   onchange="autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value);"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.postalCode1}"
            </logic:present>
                    >
            <span style="vertical-align:middle; font-size:14px;padding-bottom: 10px;">-</span>
            <input class="default-textbox"  name="postalCode2" id="postalCode2" type="text" size="3" maxlength="3"
                   onkeyup= "autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
                   onchange="autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.postalCode2}"
            </logic:present>
                    >
            <sup class="required text">*</sup>
        </td >
            <td width="1%">&nbsp;</td>
            <td width="7%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
    </tr>
    <tr>
        <%--Street NO--%>
        <td  width="9%">
            <label for="streetNo"><bean:message key="lable.registration.streetNo"/></label>
        </td>
        <td class="required text"  width="25%">
        <input name="streetNo" id="streetNo" type="text" maxlength="8"
                   onkeyup= "seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                   onchange="seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
        <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.streetNo}"
        </logic:present>
                >
            <input type="hidden" name="hideStreetNo" id="hideStreetNo" />
            <sup class="required">*</sup></td>
        <td width="1%">&nbsp;</td>
        <td width="7%">&nbsp;</td>
        <td width="25%">&nbsp;</td>

    </tr>
    <tr>
        <td  id="p_street"  width="9%">
            <label for="street"><bean:message key="lable.registration.street"/></label>
        </td>
        <%--Street(this field should be filled automaticaly)--%>
        <td  class="required text" width="25%">
            <input class="default-textbox" id="street" name="street" type="text" maxlength="15"
                    <logic:present name="deliveryAddressForm">
                        value="${deliveryAddressForm.street}"
                    </logic:present>
                    />
            <sup class="required">*</sup>
        </td>
        <td width="1%">&nbsp;</td>
        <%--Suite/Apt--%>
        <td width="7%">
            <label for="suiteApt"><bean:message key="lable.registration.suite.apt"/></label>
        </td>
        <td width="25%">
        <input name="suiteApt" id="suiteApt" type="text" size="12"  class="default-textbox"
                    <logic:present name="deliveryAddressForm">
                        value="${deliveryAddressForm.suiteApt}"
                    </logic:present>
                    />
            <input type="hidden" name="hideSuiteAPT" id="hideSuiteAPT" />
        </td>
    </tr>
</table>


<%--<table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
    <tr>
        <td width="30%">
            &lt;%&ndash;<span style="color: #616161; font: bold 13px Arial; margin-left:12px;">
            <bean:message key="label.fill.your.address.info"/>
            </span>&ndash;%&gt;
        </td>
        <td width="30%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="20%">&nbsp;</td>
    </tr>
</table>--%>
<table class="font" width="100%" border="0" cellspacing="2" cellpadding="5">
    <tr>
        <%--City--%>
        <td  width="9%">
         <label for="city"><bean:message key="lable.registration.city"/></label>
        </td>
        <td class="required text" width="25%">
            <input class="default-textbox" name="city" id="city" type="text" maxlength="50"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.city}"
            </logic:present>
                    >
            <sup class="required">*</sup>
        </td>
            <td width="1%"></td>
            <%--DoorCode--%>
            <td class="text" width="7%">
            <label for="doorCode"><bean:message key="lable.registration.doorCode"/></label>
            </td>
            <td class="text" width="25%">
            <input name="doorCode" id="doorCode" type="text"  size="12" class="default-textbox"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.doorCode}"
            </logic:present>
                    >
        </td>

    </tr>
    <tr>
        <td  width="9%">
            <label for="building"><bean:message key="lable.registration.building"/></label>
        </td>
        <td  class="required text" width="25%">
            <input class="default-textbox" name="building" id="building" type="text"  size="12"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.building}"
            </logic:present>
                    >
        </td>
        <td width="1%">&nbsp;</td>
        <td width="7%">&nbsp;</td>
        <td width="25%">&nbsp;</td>
    </tr>
    <tr>
        <%--Home Phone--%>
        <td width="9%">
            <label style="padding-top:6px;" for="cellPhone1"><bean:message key="lable.registration.home.phone"/></label>
        </td>
        <td class="required cellphone text" width="25%">
            <input class="default-textbox" style="vertical-align:middle;" name="cellPhone1" id="cellPhone1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone2');"

            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.cellPhone1}"
            </logic:present>
                    >
            <%--<a style="vertical-align:middle;"> -</a>--%>
            <span style="vertical-align:middle;">-</span>
            <input class="default-textbox" style="vertical-align:middle;" name="cellPhone2" id="cellPhone2" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone3');"

            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.cellPhone2}"
            </logic:present>
                    >
            <%--<a style="vertical-align:middle;"> -</a>--%>
            <span style="vertical-align:middle;">-</span>
            <input class="default-textbox" style="vertical-align:middle;" name="cellPhone3" id="cellPhone3" type="text" size="4"
                   maxlength="4"

            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.cellPhone3}"
            </logic:present>
                    >
            <sup class="required">*</sup>
        </td>
            <td width="1%">&nbsp;</td>
            <td width="7%">
            <label style="width:12%;text-align:left;padding-top:6px;" for="ext"><bean:message key="lable.registration.home.ext"/></label>
        </td>
            <td width="25%">
            <input class="default-textbox" name="ext" id="home-ext" type="text" size="12" maxlength="15"
            <logic:present name="deliveryAddressForm">
                   value="${deliveryAddressForm.ext}"
            </logic:present>
                    >
        </td>
    </tr>
    <tr>
        <td width="9%">&nbsp;</td>
        <td width="25%">&nbsp;</td>
        <td width="1%">&nbsp;</td>
        <td width="7%">&nbsp;</td>
        <td width="25%">&nbsp;</td>
    </tr>
</table>
</fieldset>

<%--======================================================================== The Order Goes to: Pick-Up --%>

<fieldset id="pickupInfo" class="deliveryFieldSet delivery_type" style="display: none;">
    <br class="clear">
    <div  class="font4"><bean:message key="label.fill.your.pickup.info"/></div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="9%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td width="1%">&nbsp;</td>
            <td width="7%">&nbsp;</td>
            <td width="25%">&nbsp;</td>
        </tr>
        <tr>
            <td class="font" width="9%"><bean:message key="lable.registration.home.phone"/></td>
            <td width="25%">
                <input style="vertical-align:middle;" name="cellPhone1" id="cellPhone_1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone_2');"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone1}"
                </logic:present>
                        >
                <span style="vertical-align:middle;">-</span>
                <input style="vertical-align:middle;" name="cellPhone2" id="cellPhone_2" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone_3');"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone2}"
                </logic:present>
                        >
                <span style="vertical-align:middle;">-</span>
                <input style="vertical-align:middle;" name="cellPhone3" id="cellPhone_3" type="text" size="4"
                       maxlength="4"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone3}"
                </logic:present>
                        >
                <sup class="required">*</sup>
            </td>
            <td width="1%">&nbsp;</td>
            <td class="font"  width="7%"><bean:message key="lable.registration.home.ext"/></td>
            <td width="25%">
                <input class="default-textbox"  name="phone-ext" id="ext" type="text" size="12" maxlength="15"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.ext}"
                </logic:present>
                        >
                <sup class="required"></sup></td>
        </tr>
    </table>
    <br class="clear">

            <div class="font4" ><bean:message key="label.find.store.for.pickup"/> </div>
    <br class="clear">
    <table class="font" style="padding-bottom:25px;" width="100%" border="0" cellspacing="0" cellpadding="0">

        <tr>
            <td width="8%"><bean:message key="lable.registration.postalCode"/>:</td>
            <td width="5%">
                <input class="default-textbox location_textbox" style="text-transform:uppercase;" name="postalCode1" id="postalCode_1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'postalCode_2');"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.postalCode1}"
                </logic:present>
                        >

            </td>
            <td width="1%"><b>-</b></td>
            <td width="5%">
                <input class="default-textbox location_textbox" style="text-transform:uppercase;" name="postalCode2" id="postalCode_2" type="text" size="3" maxlength="3"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.postalCode2}"
                </logic:present>
                        >

            </td>
            <td width="20%">
                <select id="storeListByCity" class="location_select">
                    <option value="0" latitude="0" longitude="0"><bean:message key="label.item.nothing"/></option>
                    <c:forEach var="postalCode" items="${postalCodeList}">
                        <option value="${postalCode.city}" latitude="${postalCode.latitude}" longitude="${postalCode.longitude}">${postalCode.city}</option>
                    </c:forEach>
                </select>
            <td width="2%">&nbsp;</td>
            <td width="32%">
                <a href="javascript: void(0);" class="btn-first" onclick="getStoreListTopForPickup();"><bean:message key="label.button.find"/></a>
            </td>
        </tr>
    </table>

    <ul id="stores" class="stores-columns">

    </ul>
</fieldset>
<%--======================================================================== Delivery Time --%>

<fieldset class="deliveryFieldSet">
    <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><bean:message key="label.delivery.time"/> </td>
        </tr>
    </table>
    <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="19%">&nbsp;</td>
            <td width="81%">&nbsp;</td>
        </tr>

        <tr>
            <td width="25%">
                <label class="button info-title"  id="NowDeliverNow" for="deliverNow">
                    <input type="radio"  name="deliverLater" id="deliverNow" onclick="changeDeliveryTime(0);" value="0"
                           <c:if test="${!deliveryAddressForm.deliverLater}">checked</c:if>
                            <logic:present name="deliveryAddressForm">
                                value="${deliveryAddressForm.deliverLater}"
                            </logic:present>
                            />
                    <span class="outer"><span class="inner"></span></span>  <bean:message key="label.delivery.now"/>
                </label>
            </td>
            <td width="75%">
                <label class="button info-title" id="NowDeliverLater" for="deliverLater">
                    <input type="radio" name="deliverLater" id="deliverLater" onclick="changeDeliveryTime(1);" value="1"
                           <c:if test="${deliveryAddressForm.deliverLater}">checked</c:if>
                            <logic:present name="deliveryAddressForm">
                                value="${deliveryAddressForm.deliverLater}"
                            </logic:present>
                            />
                    <span class="outer"><span class="inner"></span></span><bean:message key="label.deliver.choose.time"/> </label>
            </td>
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>

        <tr>
            <td colspan="2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr class="required text" id="deliverLaterInfo" style="display: none;">
                        <td width="11%">
                            <label style="font-size:14px;" for="datetimepicker"><bean:message key="label.delivery.time"/></label>
                        </td>
                        <td width="79%">
                            <input class="default-textbox celander" type="text" name="deliverTime" id="datetimepicker" readonly="true" style="width:180px;"
                            <logic:present name="deliveryAddressForm">
                                   value="${deliveryAddressForm.deliverTime}"
                            </logic:present>
                                    >
                            <sup class="required">*</sup>
                        </td>

                </tr>
                </table>
            </td>
        </tr>
  </table>
</fieldset>

<fieldset class="deliveryFieldSet">
    <table class="border Fullbasket-tilte font2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><bean:message key="label.your.company"/></td>
    </table>
    <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="12%">&nbsp;</td>
            <td width="88%">&nbsp;</td>
        </tr>
        <tr>
            <td class="font"><bean:message key="label.your.company"/>:</td>
            <td>
                <input class="default-textbox" type="text"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.company}"
                </logic:present>
                    >
               </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>

        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>

            <td width="50%">&nbsp;</td>

            <td width="50%">
                <%--<a href="#" class="black_btn2 FloatLeft">Next</a>--%>
                    <a   href="javascript: void(0);" class="btn-first FloatRight" onclick="setFieldBeforSubmit()"><bean:message key="button.checkout.continue"/></a>
                    <a  href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu"
                        onclick="setMenuType('menu');" class="btn-second floatright" width="200px;"><bean:message key="button.continueShopping"/></a>

            </td>

        </tr>
    </table>
</fieldset>
</form>
</div>

<div id="map" style="display:none">
</div>
<%--</div>--%>
<script>
    var now = new Date();
    var dateval= now.getFullYear() + '/' +(now.getMonth() + 1) + '/' + now.getDate() + ' ' + now.getHours() + ':' + now.getMinutes();
    $('#datetimepicker').datetimepicker();
    $('#datetimepicker').datetimepicker({value:dateval,step:10,minDate:dateval});
</script>

<script language="javascript">
function uncheckAllOther(sender) {
    $('#product_list_pickup input[type=checkbox]').each(
            function (index) {
                if (sender != this)
                    this.checked = false;
                else
                    this.checked = true;
            });
}
function changeDeliveryType(type) {
    $('.delivery_type').each(
            function (index) {
                this.style.display = "none";
            }
    );

    $('.delivery_type input').each(function (index) {
        this.disabled = "disabled"
    });

    if (type == 0) {
        $('#deliveryAddressInfo')[0].style.display = "block";
        $('#deliveryAddressInfo input').each(function (index) {
            this.disabled = null;
        });

    } else if (type == 1) {
        $('#pickupInfo')[0].style.display = "block";
        $('#pickupInfo input').each(function (index) {
            this.disabled = null;
        });
    }
}

var numb = '0123456789';
var lwr = 'abcdefghijklmnopqrstuvwxyz';
var upr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

function isValid(parm, val) {
    if (parm == "") return true;
    for (i = 0; i < parm.length; i++) {
        if (val.indexOf(parm.charAt(i), 0) == -1) return false;
    }
    return true;
}

function isDigit(parm) {
    return isValid(parm, numb);
}

function isCapital(parm) {
    return isValid(parm.toUpperCase(), upr)
}

function postalCodeIsValid(part1, part2) {
    if (part1.length < 3 || part2.length < 3)
        return false;

    return (isCapital(part1.charAt(0)) && isDigit(part1.charAt(1)) && isCapital(part1.charAt(2))) &&
            (isDigit(part2.charAt(0)) && isCapital(part2.charAt(1)) && isDigit(part2.charAt(2)));
}

function getStoreListTopForPickup() {
    var postalCode1 = $('#postalCode_1')[0].value;
    var postalCode2 = $('#postalCode_2')[0].value;

    var latitude = null;
    var longitude = null;
    var city = null;
    $("#storeListByCity option").each(function (index) {
        if (this.selected) {
            latitude = $(this).attr("latitude");
            longitude = $(this).attr("longitude");
            city = this.value;
            return;
        }
    });
    if ((postalCode1 == "" && postalCode2 == "") && (city == "0")) {
        $('#stores').css('-webkit-column-count','1');
        $('#stores').css('-moz-column-count','1');
        $('#stores').css('column-count','1');
        $('#stores').html("<span style='color:#DA0F00;'><bean:message key="message.find.postalcode.city" /></span>");
        return;
    }

    if ((postalCode1 != "" || postalCode2 != "") && !postalCodeIsValid(postalCode1, postalCode2)) {
        $('#stores').html("<span style='color:#DA0F00;'><bean:message key='errors.isNotValid.postalCode'/></span>");
        return;
    }

    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getStoreListTopForPickup', postalCode1: postalCode1, postalCode2: postalCode2, city: city,
            latitude: latitude, longitude: longitude},
        success: function (res) {
            if (res != "") {
                $('#stores').html(res);

                <c:if test="${!empty deliveryAddressForm.store}">
                $('.ajax_block_product').children('input').each(
                        function (index) {
                            if (this.value == '${deliveryAddressForm.store}')
//                                        this.checked = "checked";
                                uncheckAllOther(this);
                        });

                </c:if>
            }

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function changeDeliveryTime(type) {
    if (type == 0) {
        $('#deliverLaterInfo')[0].style.display = "none";
        $('#deliverLaterInfo input').each(function (index) {
            this.disabled = "disabled";
        });
    }
    else {
        $('#deliverLaterInfo')[0].style.display = "block";
        $('#deliverLaterInfo input').each(function (index) {
            this.disabled = null;
        });
    }
}

function goNextElemnt(sender, next) {
    var len = sender.value.length;
    var maxLen = sender.maxLength;

    if (len >= maxLen)
        $('#' + next).focus();
}

function onDeliveryTypeChange(type) {
    if (type == 0) {
        $('#labelDeliverNow')[0].innerHTML = '<bean:message key="label.delivery.now"/>';
        $('#labelDeliverLater')[0].innerHTML = '<bean:message key="label.delivery.later"/>';
    }
    else {
        $('#labelDeliverNow')[0].innerHTML = '<bean:message key="label.deliver.will.be.ready"/>';
        $('#labelDeliverLater')[0].innerHTML = '<bean:message key="label.deliver.choose.time"/>';
    }
}

$(document).ready(function () {
    var deliveryTime = $('#deliverTime');
//    deliveryTime.datetimepicker();
    <%--<c:if test="${empty deliveryAddressForm.deliverTime}">--%>
//    var now = new Date();
//    deliveryTime[0].value = (now.getMonth() + 1) + '/' + now.getDate() + '/' + now.getFullYear() + ' ' + now.getHours() + ':' + now.getMinutes();
    <%--</c:if>--%>


    <c:if test="${empty deliveryAddressForm.deliveryType or deliveryAddressForm.deliveryType eq 'DELIVERY' }">
    $('#deliveryMode')[0].click();
    </c:if>
    <c:if test="${deliveryAddressForm.deliveryType eq 'PICKUP' }">
    $('#pickupMode')[0].click();
    <c:if test="${!empty deliveryAddressForm.store}">
    getStoreListTopForPickup();
    </c:if>
    </c:if>

    <c:if test="${!deliveryAddressForm.deliverLater}">
    $('#deliverNow').click();
    </c:if>
    <c:if test="${deliveryAddressForm.deliverLater}">
    $('#deliverLater').click();
    </c:if>

});


function loadMap(lat,lng) {
    if (GBrowserIsCompatible()) {
        map = new GMap2(document.getElementById("map"));
        map.setCenter(new GLatLng(lat, lng), 15);
        map.setUIToDefault();
        var marker = new GMarker( new GLatLng(lat, lng));
        map.addOverlay(marker);

    }
}

$(function() {
    $("#map").dialog({
        autoOpen: false,
        draggable: false,
        resizable: false,
        modal: true,
        width: 650,
        height: 450,
        open: function(event, ui) {
            $(event.target).parent().css('position', 'fixed');
            $(event.target).parent().css('top', '200px');
            $(event.target).parent().css('left', '320px');
        }
    });
});

function showMap(sotreId,storeName,lat,lng,address1,address2){

    loadMap(lat,lng);
    var ajaxUrl  =  "<%=context%>/viewMap.do?operation=viewMap&<%=Constant.STORE_ID%>="+sotreId;
    $.ajax({
        type: "GET",
        url: ajaxUrl ,  // Send the login info to this page
        success: function(msg){
            $("#map").ajaxComplete(function(event, request, settings){
                $("#map").attr("title",'view Map');
            });
            $("#map").dialog('option', 'title', storeName + " ("+address1 + ", "+address2+")");
            $("#map").dialog('open');
        }
    });
}

</script>
<%---------------------------------------------------%>