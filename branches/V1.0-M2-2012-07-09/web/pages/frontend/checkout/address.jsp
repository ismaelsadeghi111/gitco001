<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ page import="com.sefryek.doublepizza.model.Order" %>
<%@ page import="com.sefryek.doublepizza.web.form.DeliveryAddressForm" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
    DeliveryAddressForm deliveryAddressForm = (DeliveryAddressForm)request.getAttribute("deliveryAddressForm");
%>
<SCRIPT LANGUAGE="javascript">

    function uncheckAllOther(sender){
        $('#product_list_pickup input[type=checkbox]').each(
                function (index){
                    if (sender != this)
                        this.checked = false;
                    else
                        this.checked = true;
                });
    }
    function changeDeliveryType(type){
        $('.delivery_type').each(
                function(index){
                    this.style.display = "none";
                }
            );

        $('.delivery_type input').each(function(index){ this.disabled="disabled" });

        if (type == 0){
            $('#deliveryAddressInfo')[0].style.display = "block";
            $('#deliveryAddressInfo input').each(function(index){ this.disabled=null; });            

        }else if (type == 1){
            $('#pickupInfo')[0].style.display = "block";
            $('#pickupInfo input').each(function(index){ this.disabled=null; });
        }
    }

    var numb = '0123456789';
    var lwr = 'abcdefghijklmnopqrstuvwxyz';
    var upr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

    function isValid(parm,val) {
        if (parm == "") return true;
        for (i=0; i<parm.length; i++) {
            if (val.indexOf(parm.charAt(i),0) == -1) return false;
        }
        return true;
    }

    function isDigit(parm){
        return isValid(parm, numb);
    }

    function isCapital(parm){
        return isValid(parm.toUpperCase(), upr)
    }

    function postalCodeIsValid(part1, part2){
        if (part1.length < 3 || part2.length < 3)
            return false;

        return (isCapital(part1.charAt(0)) && isDigit(part1.charAt(1)) && isCapital(part1.charAt(2))) &&
               (isDigit(part2.charAt(0)) && isCapital(part2.charAt(1)) && isDigit(part2.charAt(2)));
    }

    function getStoreListTopForPickup(){
        var postalCode1 = $('#postalCode_1')[0].value;
        var postalCode2 = $('#postalCode_2')[0].value;

        var latitude = null;
        var longitude = null;
        var city = null;
        $("#storeListByCity option").each(function(index){
            if (this.selected){
                latitude = $(this).attr("latitude");
                longitude = $(this).attr("longitude");
                city = this.value;
                return;
            }
        });

        if ((postalCode1 == "" && postalCode2 == "") && (city == "0")){
            $('#stores').html("<span style='color:#DA0F00;'><bean:message key="message.find.postalcode.city" /></span>");
            return;
        }

        if ((postalCode1 != "" || postalCode2 != "") && !postalCodeIsValid(postalCode1, postalCode2)){
            $('#stores').html("<span style='color:#DA0F00;'><bean:message key='errors.isNotValid.postalCode'/></span>");
            return;
        }

        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'getStoreListTopForPickup', postalCode1: postalCode1, postalCode2: postalCode2, city: city,
                latitude: latitude, longitude: longitude},
            success:function(res) {
                if (res != "") {
                    $('#stores').html(res);

                    <c:if test="${!empty deliveryAddressForm.store}">
                        $('.ajax_block_product').children('input').each(
                                function(index){
                                    if (this.value == '${deliveryAddressForm.store}')
//                                        this.checked = "checked";
                                        uncheckAllOther(this);
                                });

                    </c:if>
                }

            },
            failure: function() {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
    }

    function changeDeliveryTime(type){
        if (type == 0){
            $('#deliverLaterInfo')[0].style.display = "none";
            $('#deliverLaterInfo input').each(function(index){ this.disabled="disabled"; });
        }
        else{
            $('#deliverLaterInfo')[0].style.display = "block";
            $('#deliverLaterInfo input').each(function(index){ this.disabled=null; });
        }
    }

    function goNextElemnt(sender, next){
        var len = sender.value.length;
        var maxLen = sender.maxLength;

        if (len >= maxLen)
            $('#' + next).focus();
    }

    function onDeliveryTypeChange(type){
        if (type == 0)
        {
            $('#labelDeliverNow')[0].innerHTML = '<bean:message key="label.delivery.now"/>';
            $('#labelDeliverLater')[0].innerHTML = '<bean:message key="label.delivery.later"/>';
        }
        else{
            $('#labelDeliverNow')[0].innerHTML = '<bean:message key="label.deliver.will.be.ready"/>';
            $('#labelDeliverLater')[0].innerHTML = '<bean:message key="label.deliver.choose.time"/>';
        }
    }

    $(document).ready(function(){
        var deliveryTime = $('#deliverTime');
        deliveryTime.datetimepicker();
        <c:if test="${empty deliveryAddressForm.deliverTime}">
            var now = new Date();
            deliveryTime[0].value = (now.getMonth() + 1) + '/' + now.getDate() + '/' + now.getFullYear() + ' ' + now.getHours() + ':' + now.getMinutes();
        </c:if>


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
            $('#deliverNow').click()
        </c:if>
        <c:if test="${deliveryAddressForm.deliverLater}">
            $('#deliverLater').click()
        </c:if>

    });

</SCRIPT>
<script type="text/javascript" src="<%=context%>/js/jquery/jquery-ui-timepicker-addon.js"></script>

<div id="columns2">
<!-- Center -->
<div id="center_column" class="center_column">
<div id="center_column_inner2">

<h5><bean:message key="label.page.title.delivery.information"/></h5>

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

<form action="<%=context%>/deliveryAddress.do?operation=createUserForDeliveryAddress" method="post" id="deliveryAddressForm" name="deliveryAddressForm" class="std">
<fieldset class="account_creation">
    <h3><bean:message key="lable.your.personal.information"/></h3>

    <p class="radio required">
        <span><bean:message key="lable.title"/></span>

        <input name="title" id="title" value="<%=User.Title.MALE.toString()%>" type="radio"
               <c:if test="<%= deliveryAddressForm == null %>">checked</c:if>
               <c:if test="<%= deliveryAddressForm != null && deliveryAddressForm.getTitle().equalsIgnoreCase(User.Title.MALE.toString()) %>">checked</c:if>
            />
        <label class="top"><bean:message key="lable.mr"/></label>
        <input name="title" id="title" value="<%=User.Title.FEMALE.toString()%>" type="radio" 
                <c:if test="<%= deliveryAddressForm != null && deliveryAddressForm.getTitle().equalsIgnoreCase(User.Title.FEMALE.toString()) %>">checked</c:if>
                />
        <label class="top"><bean:message key="lable.ms"/></label>
    </p>

    <p class="required text">
        <label for="lastName"><bean:message key="label.delivery.address.name"/></label>
        <input onkeyup="$('#lastname').val(this.value);" id="lastName" name="lastName" type="text" maxlength="50"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.lastName}"
        </logic:present>
                >
        <sup>*</sup>

    </p>

    <p class="required text">
        <label for="email"><bean:message key="lable.registration.email"/></label>
        <input id="email" name="email" type="text" maxlength="50"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.email}"
        </logic:present>
        <logic:present parameter="email">
               value="${param['email']}"
        </logic:present>
                >
        <sup>*</sup>
    </p>

    <p class="checkbox required">
        <input name="reception" id="reception" type="checkbox" checked>
        <label for="reception"><bean:message key="label.registration.reception"/></label>
    </p>

</fieldset>
<div style="margin-bottom:25px;">
    <h3><bean:message key="label.delivery.type"/></h3>
    <input class="radio-group-item" type="radio" name="deliveryType" onchange="onDeliveryTypeChange(0);" id="deliveryMode" onclick="changeDeliveryType(0);" value="<%= Order.DeliveryType.DELIVERY.toString()%>"/>
    <label class="label-group-item" for="deliveryMode"><bean:message key="label.title.delivery.delivery"/></label>
    <input class="radio-group-item" type="radio" name="deliveryType"onchange="onDeliveryTypeChange(1);"  id="pickupMode" onclick="changeDeliveryType(1);" value="<%= Order.DeliveryType.PICKUP.toString()%>"/>
    <label class="label-group-item" for="pickupMode"><bean:message key="label.delivery.pickup"/></label>
</div>
<fieldset id="deliveryAddressInfo" class="account_creation delivery_type">
    <span style="color: #616161; font: bold 13px Arial; margin-left:12px;"><bean:message key="label.fill.your.address.info"/></span><br>

    <%--Postal Code--%>
    <p class="required postcode text" style="display:inline-block;">

        <label for="postalCode1" style="margin-left:20px;"><bean:message key="lable.registration.postalCode"/></label>
        <input style="width:26px; text-transform:uppercase;" name="postalCode1" id="postalCode1" type="text" size="3" maxlength="3"
               onkeyup= "autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value); goNextElemnt(this, 'postalCode2');"
               onchange="autoCompleteStreet(document.getElementById('streetNo').value, this.value, document.getElementById('postalCode2').value);"               
        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.postalCode1}"
        </logic:present>
                >


        <span style="vertical-align:middle; font-size:14px;padding-bottom: 10px;">-</span>
        <input style="width: 26px; text-transform:uppercase;" name="postalCode2" id="postalCode2" type="text" size="3" maxlength="3"
               onkeyup= "autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
               onchange="autoCompleteStreet(document.getElementById('streetNo').value, document.getElementById('postalCode1').value, this.value);"
        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.postalCode2}"
        </logic:present>
                >


        <sup>*</sup>
    </p>

    <%--Street NO--%>
    <p class="required text">
        <label for="streetNo"><bean:message key="lable.registration.streetNo"/></label>

        <input name="streetNo" id="streetNo" type="text" maxlength="8"
        onkeyup= "autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"
        onchange="autoCompleteStreet(this.value, document.getElementById('postalCode1').value, document.getElementById('postalCode2').value);"

        <logic:present name="deliveryAddressForm">
            value="${deliveryAddressForm.streetNo}"
    </logic:present>

        >
        <sup>*</sup>
    </p>

    <%--Street(this field should be filled automaticaly)--%>
    <p id="p_street" class="required text">
        <label for="street"><bean:message key="lable.registration.street"/></label>
        <input id="street" name="street" type="text" maxlength="15"
                <logic:present name="deliveryAddressForm">
                    value="${deliveryAddressForm.street}"
                </logic:present>

                />
        <sup>*</sup>
    </p>

    <%--Suite/Apt--%>
    <p class="text">
        <label for="suiteApt"><bean:message key="lable.registration.suite.apt"/></label>
        <input name="suiteApt" id="suiteApt" type="text" maxlength="15"

                <logic:present name="deliveryAddressForm">
                    value="${deliveryAddressForm.suiteApt}"
                </logic:present>

                />
    </p>

    <%--City--%>
    <p class="required text">
        <label for="city"><bean:message key="lable.registration.city"/></label>
        <input name="city" id="city" type="text" maxlength="50"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.city}"
        </logic:present>

                >
        <sup>*</sup>
    </p>

    <%--DoorCode--%>
    <p class="text">
        <label for="doorCode"><bean:message key="lable.registration.doorCode"/></label>
        <input name="doorCode" id="doorCode" type="text" maxlength="15"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.doorCode}"
        </logic:present>

                >
    </p>

    <%--Building--%>
    <p class="required text">
        <label for="building"><bean:message key="lable.registration.building"/></label>
        <input name="building" id="building" type="text" maxlength="15"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.building}"
        </logic:present>
                >

    </p>

    <%--Home Phone--%>
    <div style="margin-bottom:20px;">
        <p style="display:inline-block;">
            <p2 class="required cellphone text">
                <label style="padding-top:6px;" for="cellPhone1"><bean:message key="lable.registration.home.phone"/></label>
                <input style="vertical-align:middle;" name="cellPhone1" id="cellPhone1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone2');"

                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone1}"
                </logic:present>
                        >
                <%--<a style="vertical-align:middle;"> -</a>--%>
                <span style="vertical-align:middle;">-</span>
                <input style="vertical-align:middle;" name="cellPhone2" id="cellPhone2" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'cellPhone3');"

                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone2}"
                </logic:present>
                        >
                <%--<a style="vertical-align:middle;"> -</a>--%>
                <span style="vertical-align:middle;">-</span>

            </p2>

            <p3 class="required cellphone text">
                <input style="vertical-align:middle;" name="cellPhone3" id="cellPhone3" type="text" size="4"
                       maxlength="4"

                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone3}"
                </logic:present>

                        >
                <sup>*</sup>
            </p3>
        </p>

        <p style="margin-left:-10px; max-width:40%; display:inline-block;">
            <p2 class="required cellphone text" style="width:100%;" >
                <label style="width:12%;text-align:left;padding-top:6px;" for="ext"><bean:message key="lable.registration.home.ext"/></label>
                <input style="width:40%;vertical-align:middle;" name="ext" id="ext" type="text" size="15" maxlength="15"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.ext}"
                </logic:present>
                        >
            </p2>
        </p>
    </div>

</fieldset>
<fieldset id="pickupInfo" class="account_creation delivery_type">
    <span style="color: #616161; font: bold 13px Arial; margin-left:12px;"><bean:message key="label.fill.your.pickup.info"/></span><br>

    <%--Home Phone--%>
    <div>
        <p style="display:inline-block;">
            <p2 class="required cellphone text">
                <label style="padding-top:6px;" for="cellPhone_1"><bean:message key="lable.registration.home.phone"/></label>
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

            </p2>

            <p3 class="required cellphone text">
                <input style="vertical-align:middle;" name="cellPhone3" id="cellPhone_3" type="text" size="4"
                       maxlength="4"

                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.cellPhone3}"
                </logic:present>

                        >
                <sup>*</sup>
            </p3>
        </p>

        <p style="margin-left:0px; max-width:40%; display:inline-block;">
            <p2 class="required cellphone text" style="width:100%;" >
                <label style="width:12%;text-align:left;padding-top:6px;" for="ext"><bean:message key="lable.registration.home.ext"/></label>
                <input style="width:40%;vertical-align:middle;" name="ext" id="ext" type="text" size="15" maxlength="15"
                <logic:present name="deliveryAddressForm">
                       value="${deliveryAddressForm.ext}"
                </logic:present>
                        >
            </p2>
        </p>
    </div>

        <div style="color: #616161; font: bold 13px Arial; margin-left:12px; line-height:25px;margin-top:15px;"><bean:message key="label.find.store.for.pickup"/></div><br>
        <label for="postalCode1" style="margin-left:95px;"><bean:message key="lable.registration.postalCode"/></label>
        <input style="width:26px; text-transform:uppercase;" name="postalCode1" id="postalCode_1" type="text" size="3" maxlength="3" onkeyup="goNextElemnt(this, 'postalCode_2');"
        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.postalCode1}"
        </logic:present>
                >

        <span style="vertical-align:middle; font-size:14px;padding-bottom: 10px;">-</span>
        <input style="width: 26px; text-transform:uppercase;" name="postalCode2" id="postalCode_2" type="text" size="3" maxlength="3"
        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.postalCode2}"
        </logic:present>
                >
        <span style="position:relative; top: -4px;"><bean:message key="label.filter.or"/></span>
        <select id="storeListByCity" style="height:20px; position:relative; top: -4px; width: 142px;">
                <option value="0" latitude="0" longitude="0"><bean:message key="label.item.nothing"/></option>
            <c:forEach var="postalCode" items="${postalCodeList}">
                <option value="${postalCode.city}" latitude="${postalCode.latitude}" longitude="${postalCode.longitude}">${postalCode.city}</option>
            </c:forEach>
        </select>
        <a href="javascript: void(0);" class="exclusive_large" style="float:right; position:relative; top:-8px; margin-left: 3px; width:120px; font-weight: bold;font-size: 16px; margin-right:100px;text-transform: capitalize;" onclick="getStoreListTopForPickup();"><bean:message key="label.button.find"/></a>
    <div id="stores">

    </div>

</fieldset>
<fieldset class="account_creation">
    <h3><bean:message key="label.delivery.time"/></h3>
    <input class="radio-group-item" type="radio" name="deliverLater" id="deliverNow" onclick="changeDeliveryTime(0);" value="0"
            <c:if test="${!deliveryAddressForm.deliverLater}">checked</c:if> />
    <label id="labelDeliverNow" class="label-group-item" for="deliverNow"><bean:message key="label.delivery.now"/></label>
    <input class="radio-group-item" type="radio" name="deliverLater" id="deliverLater" onclick="changeDeliveryTime(1);" value="1"
            <c:if test="${deliveryAddressForm.deliverLater}">checked</c:if> />
    <label id="labelDeliverLater" class="label-group-item" for="deliverLater"><bean:message key="label.delivery.later"/></label>

    <p class="required text" id="deliverLaterInfo" style="margin-left:72px;">
        <label style="font-size:14px;" for="deliverTime"><bean:message key="label.delivery.time"/></label>
        <input style="font-size:14px; line-height:25px; height:20px; width:150px;" type="text" name="deliverTime" id="deliverTime" readonly="true"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.deliverTime}"
        </logic:present>

                >
        <sup>*</sup>
    </p>

</fieldset>
<fieldset class="account_creation">
    <h3><bean:message key="label.your.company"/></h3>

    <p class="text">
        <label for="company"><bean:message key="label.your.company"/></label>

        <input id="company" name="company" type="text"

        <logic:present name="deliveryAddressForm">
               value="${deliveryAddressForm.company}"
        </logic:present>
                >
    </p>

</fieldset>

<p class="required required_desc">
    <span><sup>*</sup><bean:message key="lable.required.field"/></span>
</p>


<p class="submit" style="padding-left:100px;">

    <input style="display:inline-block; width:200px;" name="goHome" id="goHome" value="&laquo; <bean:message key="button.continueShopping"/>"
           class="exclusive_large" type="button" onclick="continueShopping();">
    <input style="display:inline-block; width:87px;" name="submitAccount" id="submitAccount"
           value=" <bean:message key="button.next"/> &raquo;" class="exclusive" type="submit">


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
<div id="map" style="display:none">
</div>