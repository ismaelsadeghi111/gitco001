<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    String flagChgPass = (String) request.getAttribute("flagChgPass");
    if (flagChgPass == null) {
        flagChgPass = "";
    }
%>
<link rel="stylesheet" href="<%=context%>/css/validationEngine.jquery.css" type="text/css"/>

<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine-en.js"
        charset="utf-8"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/validation/jquery.validationEngine-fr.js"
        charset="utf-8"></script>
<input type="text" style="display: none" value="<%=flagChgPass%>" id="flagChgPass"/>
<script type="text/javascript">
    var selected = 1;

</script>

<div class="cusfood"><bean:message key="myprofile.title"/></div>
<div class="LeftMiddleWrapper" style="" id="LeftMiddleWrapperDiv">

<c:set var="enState"
       value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState"
       value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>
<table class="font3 border" style="padding-bottom:15px;" width="100%" border="0" cellspacing="2" cellpadding="4"
       id="showUserInfo">

    <tr>
        <td colspan="4">
            <h3><bean:message key="lable.your.personal.information"/></h3>
        </td>
    </tr>
    <tr>
        <td width="10%"><bean:message key="lable.title"/>:</td>
        <td width="25%" class="gray"><c:if test="${sessionScope.user_transient.title eq 'MALE'}"><bean:message
                key="lable.mr"/></c:if> <c:if test="${sessionScope.user_transient.title eq 'FEMALE'}"><bean:message
                key="lable.ms"/></c:if></td>
        <td width="10%"><bean:message key="lable.registration.email"/>:</td>
        <td width="44%" class="gray">${sessionScope.user_transient.email}</td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.firstName"/>:</td>
        <td class="gray">${sessionScope.user_transient.firstName}</td>
        <td  width="15%"><bean:message key="lable.registration.lastName"/>:</td>
        <td class="gray">${sessionScope.user_transient.lastName}</td>
        <%--        <td>Facebook:</td>
                <td class="gray">${sessionScope.user_transient.facebbookUsername}</td>--%>
    </tr>
    <tr>

        <%--        <td>Twitter:</td>
                <td class="gray">${sessionScope.user_transient.twitterUsername}</td>--%>
    </tr>
    <tr>
        <td><bean:message key="label.birthday"/>:</td>
        <td class="gray">${sessionScope.user_transient.birthDateStr}&nbsp;&nbsp;&nbsp;<font color="red">${message}</font></td>
        <td><bean:message key="profile.Company"/>:</td>
        <td class="gray">${sessionScope.user_transient.company}</td>
    </tr>
    <tr>
        <td colspan="4"><bean:message key="profile.unsubscribe"/>:
            &nbsp;&nbsp;
            <input disabled="disabled" type="checkbox" id="viewBox"  ${subscribeValue}>
        </td>

    </tr>

    <tr>
        <td colspan="2"><a href="javascript: void(0);" class="btn-first" onclick="editUserInfo();"><bean:message
                key="lable.edit.personal.information"/></a></td>
        <td colspan="2">
            <a href="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=changePassword"
               class="btn-first"><bean:message key="change.password"/></a></td>
    </tr>
</table>
<form action="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=saveUserInfo" id="userInfo"
      method="post">

    <table class="font3 border" style="padding-bottom:15px; display: none" width="100%" border="0" cellspacing="2"
           cellpadding="4" id="editUserInfo">
        <tr>
            <td colspan="4">
                <h3><bean:message key="lable.your.personal.information"/></h3>
            </td>
        </tr>
        <tr>
            <td width="11%"><bean:message key="lable.title"/>:</td>
            <td width="20%" class="gray">
                <input type="radio" value="MALE" name="title"
                       <c:if test="${sessionScope.user_transient.title eq 'MALE'}">checked</c:if>
                        ><bean:message key="lable.mr"/>
                &nbsp;&nbsp;&nbsp;

                <input type="radio" value="FEMALE" name="title"
                       <c:if test="${sessionScope.user_transient.title eq 'FEMALE'}">checked</c:if>
                        ><bean:message key="lable.ms"/>
            </td>
            <td width="10%"><bean:message key="lable.registration.email"/>:</td>
            <td width="44%" class="gray"><input type="text" name="email"
                                                value="${sessionScope.user_transient.email}">
                <sup class="required">*</sup>
            </td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.firstName"/>:</td>
            <td class="gray"><input type="text" name="firstName" value="${sessionScope.user_transient.firstName}">
                <sup class="required">*</sup>
            </td>
            <td  width="15%"><bean:message key="lable.registration.lastName"/>:</td>
            <td class="gray"><input type="text" name="lastName" value="${sessionScope.user_transient.lastName}">
                <sup class="required">*</sup>
            </td>
            <%--            <td>Facebook:</td>
                        <td class="gray"><input type="text" name="facebbookUsername"
                                                value="${sessionScope.user_transient.facebbookUsername}"/></td>--%>
        </tr>
        <tr>

            <%--            <td>Twitter:</td>
                        <td class="gray"><input type="text" name="twitterUsername"
                                                value="${sessionScope.user_transient.twitterUsername}"></td>--%>
        </tr>
        <tr>
            <td><bean:message key="label.birthday"/>:</td>
            <td class="gray"><input type="text" id="birthDate" name="birthDate"
                                    value="${sessionScope.user_transient.birthDateStr}"/></td>
            <td><bean:message key="profile.Company"/>:</td>
            <td class="gray"><input type="text" name="company" value="${sessionScope.user_transient.company}"/></td>
        </tr>
        <tr>
           <td colspan="4">
               <bean:message key="profile.unsubscribe"/>:
               &nbsp;&nbsp;
               <input type="hidden" id="subscribe" name="subscribe" value="${subscribeValue}" />
               <input type="checkbox" name="subscribeBox" id="subscribeBox" ${subscribeValue} onchange="changeSubscribe()"/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2"><a href="javascript: void(0);" class="btn-first" onclick="validateUserInfo(this);"
                               class="black_btn FloatLeft"><bean:message key="lable.save.personal.information"/></a>
            </td>
            <td colspan="2">
                <a href="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=changePassword"
                   class="btn-first"><bean:message key="change.password"/></a></td>
        </tr>
    </table>
</form>
<%int i = 1;%>


<c:forEach items="${contacts}" var="profile">
<form action="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=saveContactInfo"
      id="contactInfo_<%=i%>"
      method="post">
    <table style="display:none;" width="100%" border="0" id="contactInfoForm_<%=i%>"
           cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="4" height="36" class=""><h3><bean:message key="label.your.address"/>&nbsp;#<%=i%>
            </h3></td>
        </tr>
        <tr>
            <td width="11%">&nbsp;</td>
            <td width="35%">&nbsp;</td>
            <td width="10%">&nbsp;</td>
            <td width="44%">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="label.user.address.name"/>:</td>
            <td class="gray">
                <input type="text" name="addressScreenEN_<%=i%>" value="${profile.addressScreenEN}"
                       id="addressScreenEN_<%=i%>"/>

                 <span>
                  <small style="font-style:italic; color:#8e8d8d; padding-left: 10px;">
                      <span><bean:message key="label.user.address.name.home.office"/>
                 </span>
                  </small>
                </span>
            </td>

            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.postalCode"/>:</td>
            <td class="gray">
                <input type="text" name="postalcode1_<%=i%>" id="postalCode1_<%=i%>" size="3" maxlength="3"
                       style=" width:40px;"
                       value="${fn:substring(profile.postalcode,0, 3)}"
                       onkeyup="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, this.value, document.getElementById('postalCode2_<%=i%>').value); goNextElemnt(this, 'postalCode2');"
                       onchange="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, this.value, document.getElementById('postalCode2_<%=i%>').value);"/>
                <span style="vertical-align:middle; font-size:14px; padding-bottom:10px;">-</span>
                <input type="text" name="postalcode2_<%=i%>" id="postalCode2_<%=i%>" size="3" maxlength="3"
                       style=" width: 40px;" value="${fn:substring(profile.postalcode,4,7)}"
                       onkeyup="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, this.value);"
                       onchange="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, this.value);"/>
                <sup class="required">*</sup>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.streetNo"/>:</td>
            <td class="gray"><input type="text" name="streetNo_<%=i%>" id="streetNo_<%=i%>" value="${profile.streetNo}"
                                    maxlength="8"
                                    onkeyup="seperateCharNumDynamic(<%=i%>, this.value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"
                                    onchange="seperateCharNumDynamic(<%=i%>, this.value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"
                    />
                <input type="hidden" name="hideStreetNo_<%=i%>" id="hideStreetNo_<%=i%>"  />
                <sup class="required">*</sup>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.street"/>:</td>
            <td class="gray"><input type="text" name="street_<%=i%>" value="${profile.street}" id="street_<%=i%>"
                                    maxlength="50"
                                    onclick="resetInput();"
                                    onkeyup="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"
                                    onchange="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"/>

                <sup class="required">*</sup>

            </td>

            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.city"/>:</td>
            <td class="gray">
                <input type="text" name="city_<%=i%>" id="city_<%=i%>" value="${profile.city}" maxlength="15"
                       onkeyup="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"
                       onchange="autoCompleteStreetDaynamic(<%=i%>, document.getElementById('streetNo_<%=i%>').value, document.getElementById('postalCode1_<%=i%>').value, document.getElementById('postalCode2_<%=i%>').value);"/>

                <sup class="required">*</sup>
            </td>
            <td><bean:message key="lable.registration.suite.apt"/>:</td>
            <td class="gray">

                <input type="hidden" name="hideSuiteAPT_<%=i%>" id="hideSuiteAPT_<%=i%>" />
                <input type="text" name="suiteAPT_<%=i%>" id="suiteAPT_<%=i%>" value="${profile.suiteAPT}"  maxlength="15" />
            </td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.building"/>:</td>
            <td class="gray">
                <input type="text" name="building_<%=i%>" id="building_<%=i%>" value="${profile.building}" maxlength="15" />
            </td>
            <td><bean:message key="lable.registration.doorCode"/>:</td>
            <td class="gray"><input type="text" maxlength="15" name="doorCode_<%=i%>" id="doorCode_<%=i%>"
                                    value="${profile.doorCode}"
                    /></td>
        </tr>

        <tr>
            <td><bean:message key="lable.registration.phone"/>:</td>
            <td class="gray">

                <input value="${fn:substring(profile.phone,0, 3)}" id="phone1_<%=i%>" name="phone1_<%=i%>" type="text"
                       size="3"
                       maxlength="3"
                       onkeyup="goNextElemnt(this, 'phone2');"/>
                <a style="vertical-align:middle;"> -</a>


                <input value="${fn:substring(profile.phone,3, 6)}" id="phone2_<%=i%>" name="phone2_<%=i%>" type="text"
                       size="3"
                       maxlength="3"
                       onkeyup="goNextElemnt(this, 'phone3');"/>
                <a style="vertical-align:middle;"> -</a>
                <input value="${fn:substring(profile.phone,6, 12)}" id="phone3_<%=i%>" name="phone3_<%=i%>" type="text"
                       size="4" maxlength="4"/>
                <sup class="required">*</sup>

            <td><bean:message key="lable.registration.home.ext"/>:</td>
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <input type="hidden" name="submitMode_<%=i%>" id="submitMode_<%=i%>" value="edit"/>
                        <input type="hidden" name="" profileId_<%=i%>" id="profileId_<%=i%>" value="${profile.id}"/>
                        <td width="19%" class="gray">
                            <input type="text" maxlength="15" name="ext_<%=i%>" value="${profile.ext}" id="ext_<%=i%>"/>
                        </td>
                        <td width="81%">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td width="5%">&nbsp;</td>
            <td width="81">
                <a href="javascript: void(0);" class="btn-first" style="right: 84px"
                   onclick="validateEditAddressInfo(this, <%=i%>);"><bean:message key="changes.Save"/></a>
            </td>
            </td>
        </tr>
    </table>
</form>

<table class="font3 border" style="padding-bottom:15px;" width="100%" border="0" cellspacing="0" id='${profile.id}'
       cellpadding="0">

    <tr>
        <td width="11%">&nbsp;</td>
        <td width="35%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="44%">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="4" height="36" class=""><h3><bean:message key="label.your.address"/>&nbsp;#<%=i%>
        </h3></td>
    </tr>
    <tr>
        <td><bean:message key="label.user.address.name"/>:</td>
        <td class="gray">
                ${profile.addressScreenEN}
        </td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.postalCode"/>:</td>
        <td class="gray">${profile.postalcode}</td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.streetNo"/>:</td>
        <td class="gray">${profile.streetNo}</td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.street"/>:</td>
        <td class="gray">${profile.street}</td>
        <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.city"/>:</td>
        <td class="gray">${profile.city}</td>
        <td><bean:message key="lable.registration.suite.apt"/>:</td>
        <td class="gray">${profile.suiteAPT}</td>
    </tr>

    <tr>
        <td><bean:message key="lable.registration.building"/>:</td>
        <td class="gray">${profile.building}</td>
        <td><bean:message key="lable.registration.doorCode"/>:</td>
        <td class="gray">${profile.doorCode} </td>
    </tr>
    <tr>
        <td><bean:message key="lable.registration.phone"/>:</td>
        <td class="gray">${profile.phone}</td>
        <td><bean:message key="lable.registration.home.ext"/>:</td>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="19%" class="gray">${profile.ext}</td>
                    <td width="81%"><a href="javascript: void(0);"><img src="images/edit.png" alt="Edit"
                                                                        onclick="editAddress(this, <%=i%>);"/></a></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>

        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
</table>
<%i++;%>
</c:forEach>

<!-- Add Address Form-->
<form action="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=saveContactInfo"
      id="contactInfo_0"
      method="post">
    <table style="display:none;" width="100%" border="0" id="addContactInfoForm"
           cellspacing="0" cellpadding="0">
        <tr>
            <td width="11%">&nbsp;</td>
            <td width="35%">&nbsp;</td>
            <td width="10%">&nbsp;</td>
            <td width="44%">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="label.user.address.name"/>:</td>
            <td class="gray">
                <input type="text" name="addressScreenEN" value=""
                       id="addressScreenEN"/>

                 <span>

                  <small style="font-style:italic; color:#8e8d8d; padding-left: 10px;">

                      <span><bean:message key="label.user.address.name.home.office"/>

                 </span>

                  </small>
                </span>
            </td>

            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.postalCode"/>:</td>
            <td class="gray">
                <input type="text" name="postalcode1" size="3" maxlength="3" style=" width:40px;" value=""
                       id="postalCode1"
                       onkeyup="autoCompleteStreet(document.getElementById('streetNo').value, this.value,
document.getElementById('postalCode2').value); goNextElemnt(this, 'postalCode2');"
                       onchange="autoCompleteStreet(document.getElementById('streetNo').value, this.value,
document.getElementById('postalCode2').value);"/>
                <span style="vertical-align:middle; font-size:14px; padding-bottom:10px;">-</span>
                <input type="text" name="postalcode2" size="3" maxlength="3"
                       style=" width: 40px;" value="" id="postalCode2"
                       onkeyup="autoCompleteStreet(document.getElementById('streetNo').value,
document.getElementById('postalCode1').value, this.value);"
                       onchange="autoCompleteStreet(document.getElementById('streetNo').value,
document.getElementById('postalCode1').value, this.value);"/>
                <sup class="required">*</sup>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.streetNo"/>:</td>
            <td class="gray"><input maxlength="8" type="text" name="streetNo" value="" id="streetNo"
                                    onkeyup="seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                                    onchange="seperateCharNum(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                    />
                <input type="hidden" name="hideStreetNo" id="hideStreetNo" />
                <sup class="required">*</sup>
            </td>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.street"/>:</td>
            <td class="gray"><input type="text" name="street" value="" id="street" maxlength="50"
                                    onclick="resetInput();"
                                    onkeyup="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                                    onchange="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"/>

                <sup class="required">*</sup>

            </td>

            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.city"/>:</td>
            <td class="gray">
                <input type="text" name="city" value="" id="city" maxlength="15"
                       onkeyup="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
                       onchange="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"/>
                <sup class="required">*</sup>
            </td>
            <td><bean:message key="lable.registration.suite.apt"/>:</td>
            <td class="gray">
                <input type="hidden" name="hideSuiteAPT" id="hideSuiteAPT" />
                <input type="text" name="suiteAPT" value="" maxlength="15"  id="suiteAPT"/>
            </td>
        </tr>
        <tr>
            <td><bean:message key="lable.registration.building"/>:</td>
            <td class="gray"><input type="text" maxlength="15" name="building" value=""
                                    id="building"/></td>
            <td><bean:message key="lable.registration.doorCode"/>:</td>
            <td class="gray"><input type="text" maxlength="15" name="doorCode" value=""
                                    id="doorCode"/></td>
        </tr>

        <tr>
            <td><bean:message key="lable.registration.phone"/>:</td>
            <td class="gray">
                <input name="phone1" value="" id="phone1" type="text" size="3"
                       maxlength="3"
                       onkeyup="goNextElemnt(this, 'phone2');"/>
                <a style="vertical-align:middle;"> -</a>
                <input name="phone2" value="" id="phone2" type="text" size="3"
                       maxlength="3"
                       onkeyup="goNextElemnt(this, 'phone3');"/>
                <a style="vertical-align:middle;"> -</a>
                <input style="vertical-align:middle;" name="phone3" value="" id="phone3"
                       type="text"
                       size="4" maxlength="4"/>
                <sup class="required">*</sup>
            <td><bean:message key="lable.registration.home.ext"/>:</td>
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <input type="hidden" name="submitMode" id="submitMode" value="add"/>
                        <input type="hidden" name="id" id="profileId" value=""/>
                        <td width="19%" class="gray"><input type="text" maxlength="15" name="ext"
                                                            value=""
                                                            id="ext"/></td>
                        <td width="81%">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
        <%--        <tr>
                    <td colspan="2" width="30%">
                        <br><a href="" class="black_btn FloatLeft"
                               onclick="validateAddressInfo(this);"><bean:message
key="changes.Save"/></a>
                    </td>
                    <td colspan="2">&nbsp;</td>
                </tr>--%>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td width="5%">&nbsp;</td>
            <td width="81">
                <a href="javascript: void(0);" class="btn-first" style="right: 84px"
                   onclick="validateAddressInfo(this, 0);"><bean:message key="changes.Save"/></a>
            </td>
            </td>
        </tr>
    </table>
</form>
<!-- End Address Form--->

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="11%">&nbsp;</td>
        <td width="35%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="44%">&nbsp;</td>
    </tr>
    <tr>
        <td colspan="4"><a href="javascript: void(0);" class="btn-first" onclick="addAddress(this);"
                           class="black_btn FloatLeft"><bean:message key="add.Another.Address"/> </a></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
</table>
</div>


<script type="text/javascript">

function seperateCharNumDynamic(index, streetNo, postalcode_1st, postalcode_2nd){

    var digits = streetNo.replace(/\D/g, "");
    var letters = streetNo.replace(/[^a-z]/gi, "");
    //alert(digits);
    //alert(letters);
    document.getElementById('hideSuiteAPT_' + index).value = letters;
    document.getElementById('hideStreetNo_' + index).value = digits;
    if(digits != null && digits != "")
        autoCompleteStreetDaynamic(index, digits, postalcode_1st, postalcode_2nd);
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

$(document).ready(function () {

});
function addAddress(element) {
    if (selected == 1) {
        $("#addContactInfoForm").show();
        $('#' + myId).show();
        $("#submitMode").val('add');
        $('#addressScreenEN').val('<bean:message key="user.address.name.default"/>');
        $('#postalCode1').val('');
        $('#postalCode2').val('');
        $('#city').val('');
        $('#streetNo').val('');
        $('#doorCode').val('');
        $('#street').val('');
        $('#building').val('');
        $('#phone1').val('');
        $('#phone2').val('');
        $('#phone3').val('');
        $('#ext').val('');
        $('#suiteAPT').val('');
        var el = document.getElementById("addContactInfoForm");
        el.scrollIntoView(true);
        selected = -1;
    } else {
        var message = '<bean:message key="message.registration.save.changes" />';
        new $.Zebra_Dialog(message, {
            'buttons':  false,
            'width':400,
            'type':'error',
            'modal': false,
            'position':['center', 'top + 20'] ,
            'auto_close': 4000});
    }
    /*        var t=document.getElementById('submitMode').value;
     alert('mode:'+t);*/


}

function changeSubscribe(){
    if($("#subscribeBox").attr("checked"))
        $("#subscribe").val("true");
    else
        $("#subscribe").val("false")
}

function cancelAddAddress(element, idForm) {
    selected = 1;
    $("#addContactInfoForm").hide();
    $('#' + myId).hide();
    $('#addressScreenEN').val('');
    $('#postalCode1').val('');
    $('#postalCode2').val('');
    $('#city').val('');
    $('#streetNo').val('');
    $('#doorCode').val('');
    $('#street').val('');
    $('#building').val('');
    $('#phone1').val('');
    $('#phone2').val('');
    $('#phone3').val('');
    $('#ext').val('');
    $('#suiteAPT').val('');
    var el = document.getElementById("addContactInfoForm");
    el.scrollIntoView(true);
}
var myId;
function editAddress(element, editAddressId) {
    if (selected == 1) {
        myId = $(element).closest('table').parent().closest('table').attr("id");
        var trs = $('#' + myId).find('tr');
        var index = 1;
        var list = new Array();
        $('#' + myId + ' .gray').each(function () {
            list[index] = $(this).html();
            index = index + 1;
        });

        /*$('#addressScreenEN').val(list[1].trim());
         $('#postalCode1').val(list[2].substr(0, 3).trim());
         $('#postalCode2').val(list[2].substr(4,3).trim());
         $('#streetNo').val(list[3].trim());
         $('#street').val(list[4].trim());
         $('#city').val(list[5].trim());
         $('#suiteAPT').val(list[6].trim());
         $('#building').val(list[7].trim());
         $('#doorCode').val(list[8].trim());
         $('#phone1').val(list[9].substr(0, 3).trim());
         $('#phone2').val(list[9].substr(3, 3).trim());
         $('#phone3').val(list[9].substr(6).trim());
         $('#ext').val(list[10].trim());
         $('#profileId').val(myId);*/

        //Saeid AmanZadeh Edited
        //alert(editAddressId);
        $('#' + myId).hide();
        $('#contactInfoForm_' + editAddressId).fadeIn(3000);
        //----

        var el = document.getElementById("showUserInfo");
        //el.scrollIntoView(true);
        //$("#submitMode").val('edit');
        /*
         var t=document.getElementById('submitMode').value;
         alert('mode:'+t);
         */
        selected = -1;
    } else {
        var message = '<bean:message key="message.registration.save.changes" />';
        tAlert(message, '', 'error', 4000);
    }

}

function editUserInfo() {
    if (selected == 1) {
        $("#editUserInfo").show();
        $("#showUserInfo").hide();
        selected = -1
    } else {
        var message = '<bean:message key="message.registration.save.changes" />';
        tAlert(message, '', 'error', 4000);
    }
}

function goNextElemnt(sender, next) {
    var len = sender.value.length;
    var maxLen = sender.maxLength;

    if (len >= maxLen)
        $('#' + next).focus();
}

function validateUserInfo(element) {
    $("#userInfo").validate({
        rules: {
            firstName: {
                required: true,
                minlength: 3
            },
            lastName: {
                required: true,
                minlength: 3
            },
            email: {
                required: true,
                email: true,
                minlength: 3
            },
            company: {
                minlength: 3
            },
            twitterUsername: {
                minlength: 3
            },
            facebbookUsername: {
                minlength: 3
            }
        }
    });
    if ($(element).closest('form').valid()) {
        $(element).closest('form').submit();
    }

}

function validateAddressInfo(element, idForm) {

    $("#contactInfo_" + idForm).validate({
        rules: {
            postalcode2: "required",
            city: "required",
            street: "required",
            streetNo: "required",
            phone1: {required: true, minlength: 3},
            phone2: {required: true, minlength: 3},
            phone3: {required: true, minlength: 3}
        }
    });
    if ($(element).closest('form').valid()) {

        if($("#hideStreetNo").val().trim() != "")
            $('#streetNo').val($("#hideStreetNo").val().trim());
        if($("#hideSuiteAPT").val().trim() != "")
            $('#suiteAPT').val($("#hideSuiteAPT").val().trim() + " " + $("#suiteAPT").val().trim());
        $(element).closest('form').submit();
    }
}

function validateEditAddressInfo(element, idForm) {
//        if($("#streetNo_" + idForm).val().trim() == "")
//            alert('s');

    $('#addressScreenEN').val($("#addressScreenEN_" + idForm).val().trim());
    $('#postalCode1').val($("#postalCode1_" + idForm).val().trim());
    $('#postalCode2').val($("#postalCode2_" + idForm).val().trim());

    if($("#hideStreetNo_" + idForm).val().trim() != "")
        $('#streetNo').val($("#hideStreetNo_" + idForm).val().trim());
    else
        $('#streetNo').val($("#streetNo_" + idForm).val().trim());

    $('#street').val($("#street_" + idForm).val().trim());
    $('#city').val($("#city_" + idForm).val().trim());
    $('#suiteAPT').val($("#hideSuiteAPT_" + idForm).val().trim() + " " + $("#suiteAPT_" + idForm).val().trim());
    $('#building').val($("#building_" + idForm).val().trim());
    $('#doorCode').val($("#doorCode_" + idForm).val().trim());
    $('#phone1').val($("#phone1_" + idForm).val().trim());
    $('#phone2').val($("#phone2_" + idForm).val().trim());
    $('#phone3').val($("#phone3_" + idForm).val().trim());
    $('#ext').val($("#ext_" + idForm).val().trim());
    $('#profileId').val($("#profileId_" + idForm).val().trim());
    $('#submitMode').val($("#submitMode_" + idForm).val().trim());

    $("#contactInfo_" + idForm).validate({
        rules: {
            postalcode2: "required",
            city: "required",
            street: "required",
            streetNo: "required",
            phone1: {required: true, minlength: 3},
            phone2: {required: true, minlength: 3},
            phone3: {required: true, minlength: 3}
        }
    });
    if ($("#contactInfo_" + idForm).valid()) {
        $('#contactInfo_0').submit();
    }
}

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
    var flagChgPass = document.getElementById("flagChgPass");
    if (flagChgPass.value == "True") {
        new $.Zebra_Dialog('<span style="font-size:20px;"><bean:message key="message.registration.change.password.success" /></span>'
                , {
                    'buttons':  false,
                    'type':'confirmation',
                    'modal': false,
                    'position':['center', 'top + 20'] ,
                    'auto_close': 4000});
    }
    flagChgPass.value = "";

});


$("#contactInfo_1").validate({
    rules: {
        postalcode2_1: "required",
        city_1: "required",
        street_1: "required",
        streetNo_1: "required",
        phone1_1: {required: true, minlength: 3},
        phone2_1: {required: true, minlength: 3},
        phone3_1: {required: true, minlength: 3}
    }
});

$("#contactInfo_2").validate({
    rules: {
        postalcode2_2: "required",
        city_2: "required",
        street_2: "required",
        streetNo_2: "required",
        phone1_2: {required: true, minlength: 3},
        phone2_2: {required: true, minlength: 3},
        phone3_2: {required: true, minlength: 3}
    }
});

$("#contactInfo_3").validate({
    rules: {
        postalcode2_3: "required",
        city_3: "required",
        street_3: "required",
        streetNo_3: "required",
        phone1_3: {required: true, minlength: 3},
        phone2_3: {required: true, minlength: 3},
        phone3_3: {required: true, minlength: 3}
    }
});

$("#contactInfo_4").validate({
    rules: {
        postalcode2_4: "required",
        city_4: "required",
        street_4: "required",
        streetNo_4: "required",
        phone1_4: {required: true, minlength: 3},
        phone2_4: {required: true, minlength: 3},
        phone3_4: {required: true, minlength: 3}
    }
});

$("#contactInfo_5").validate({
    rules: {
        postalcode2_5: "required",
        city_5: "required",
        street_5: "required",
        streetNo_5: "required",
        phone1_5: {required: true, minlength: 3},
        phone2_5: {required: true, minlength: 3},
        phone3_5: {required: true, minlength: 3}
    }
});

$("#contactInfo_6").validate({
    rules: {
        postalcode2_6: "required",
        city_6: "required",
        street_6: "required",
        streetNo_6: "required",
        phone1_6: {required: true, minlength: 3},
        phone2_6: {required: true, minlength: 3},
        phone3_6: {required: true, minlength: 3}
    }
});

$("#contactInfo_7").validate({
    rules: {
        postalcode2_7: "required",
        city_7: "required",
        street_7: "required",
        streetNo_7: "required",
        phone1_7: {required: true, minlength: 3},
        phone2_7: {required: true, minlength: 3},
        phone3_7: {required: true, minlength: 3}
    }
});

$("#contactInfo_8").validate({
    rules: {
        postalcode2_8: "required",
        city_8: "required",
        street_8: "required",
        streetNo_8: "required",
        phone1_8: {required: true, minlength: 3},
        phone2_8: {required: true, minlength: 3},
        phone3_8: {required: true, minlength: 3}
    }
});

$("#contactInfo_9").validate({
    rules: {
        postalcode2_9: "required",
        city_9: "required",
        street_9: "required",
        streetNo_9: "required",
        phone1_9: {required: true, minlength: 3},
        phone2_9: {required: true, minlength: 3},
        phone3_9: {required: true, minlength: 3}
    }
});

$("#contactInfo_10").validate({
    rules: {
        postalcode2_10: "required",
        city_10: "required",
        street_10: "required",
        streetNo_10: "required",
        phone1_10: {required: true, minlength: 3},
        phone2_10: {required: true, minlength: 3},
        phone3_10: {required: true, minlength: 3}
    }
});

$("#contactInfo_11").validate({
    rules: {
        postalcode2_11: "required",
        city_11: "required",
        street_11: "required",
        streetNo_11: "required",
        phone1_11: {required: true, minlength: 3},
        phone2_11: {required: true, minlength: 3},
        phone3_11: {required: true, minlength: 3}
    }
});

$("#contactInfo_12").validate({
    rules: {
        postalcode2_12: "required",
        city_12: "required",
        street_12: "required",
        streetNo_12: "required",
        phone1_12: {required: true, minlength: 3},
        phone2_12: {required: true, minlength: 3},
        phone3_12: {required: true, minlength: 3}
    }
});

$("#contactInfo_13").validate({
    rules: {
        postalcode2_13: "required",
        city_13: "required",
        street_13: "required",
        streetNo_13: "required",
        phone1_13: {required: true, minlength: 3},
        phone2_13: {required: true, minlength: 3},
        phone3_13: {required: true, minlength: 3}
    }
});

$("#contactInfo_14").validate({
    rules: {
        postalcode2_14: "required",
        city_14: "required",
        street_14: "required",
        streetNo_14: "required",
        phone1_14: {required: true, minlength: 3},
        phone2_14: {required: true, minlength: 3},
        phone3_14: {required: true, minlength: 3}
    }
});

$("#contactInfo_15").validate({
    rules: {
        postalcode2_15: "required",
        city_15: "required",
        street_15: "required",
        streetNo_15: "required",
        phone1_15: {required: true, minlength: 3},
        phone2_15: {required: true, minlength: 3},
        phone3_15: {required: true, minlength: 3}
    }
});

$("#contactInfo_16").validate({
    rules: {
        postalcode2_16: "required",
        city_16: "required",
        street_16: "required",
        streetNo_16: "required",
        phone1_16: {required: true, minlength: 3},
        phone2_16: {required: true, minlength: 3},
        phone3_16: {required: true, minlength: 3}
    }
});

</script>