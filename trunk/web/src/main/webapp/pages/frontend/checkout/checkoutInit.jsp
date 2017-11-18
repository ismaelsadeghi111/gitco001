<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.User" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Mar 29, 2012
  Time: 10:47:24 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    User user = (User)request.getSession().getAttribute(Constant.USER_TRANSIENT);

%>


<script type="text/javascript">

var lastEnteredStreetNo = '';
var lastEnteredPostalCode1 = '';
var lastEnteredPostalCode2 = '';
var counter = 0;
var lastSubTotalPrice = 0;
var isInCustomizingMode = false;
var customizing = 0;

$(document).ready(function(){
    if(<%=basket != null && basket.getBasketItemList().size() > 0%>){
        getInvoice();
    }
});

function setLastSubTotalPrice(price){
    lastSubTotalPrice = price;
}

function getLastSubTotalPrice(price){
    return lastSubTotalPrice;
}

$(window).load(function() {
    var homeAlert = getCookie("homeAlert");
    if (homeAlert == "fromNoDeliveryCode"){
        setCookie("homeAlert", "", 0);
        tAlert('<bean:message key='message.error.find.store.for.user'/>', '', 'error', 10000);
    }
});


function setOrderCouponCodeOnServer() {
    var el = document.getElementById('coupon-code');
    if (el != null) {
        var couponCode = el.value;

        $.ajax({
            method: 'POST',
            url: '<%=context%>/checkout.do',
            data: {operation: 'setOrderCouponCode', couponCode : couponCode},
            success: function(res) {
            },
            failure: function() {
                alert('FAILURE');
            }
        });
    }
}

function logout() {
    var currentUrl = window.location;
    window.location.href = '<%=context%>/logout.do?operation=logout&<%=Constant.LATEST_USER_URL%>=' + currentUrl;
}

function continueShopping() {
    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);


    var hid_operation = document.createElement('input');
    hid_operation.setAttribute('type', 'hidden');
    hid_operation.setAttribute('name', 'operation');
    hid_operation.setAttribute('value', 'goToMainPage');

    var hid_param = document.createElement('input');
    hid_param.setAttribute('type', 'hidden');
    hid_param.setAttribute('name', 'fromContinueShopping');
    hid_param.setAttribute('value', 'true');

    myForm.appendChild(hid_operation);
    myForm.appendChild(hid_param);

    myForm.submit();
}

function removeItemFromBasket(basketItemId, basketItemType, trId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'removeItemFromBasket', basketItemId: basketItemId, basketItemType: basketItemType},
        success:function(res) {
            if (res != "") {
                var element = document.getElementById(trId);
                element.parentNode.removeChild(element);

                var itemNumEl = document.getElementById('item_num');
                var itemNumber = itemNumEl.innerHTML;
                if (itemNumber != 0) {
                    itemNumber--;
                    itemNumEl.innerHTML = itemNumber;
                }


                var cartItemsTableEl = document.getElementById('cart_items').getElementsByTagName('tr');
                if (cartItemsTableEl.length < 1) {
                    alert('items lower than 0');
                }
            }
        },
        failure: function() {
            alert('FAILURE');
        }
    });
}

function removeItemUpdateCheckout(basketItemId, basketItemType) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'removeItemUpdateCheckout', basketItemId: basketItemId, basketItemType: basketItemType},
        success: function(res) {
            document.getElementById('center_column').innerHTML = res;

            var itemNumEl = document.getElementById('item_num');
            var itemNumber = itemNumEl.innerHTML;
            if (itemNumber != 0) {
                itemNumber--;
                itemNumEl.innerHTML = itemNumber;
                var itemCount = document.getElementById("cart_block_shipping_cost");
                if (itemCount != null)
                    itemCount.innerHTML = itemNumber;
            }

            var itemPriceFrom = document.getElementById("checkout_prices");
            if (itemPriceFrom != null) {
                var itemPriceTo = document.getElementById("cart_block_total");
                if (itemPriceTo != null)
                    itemPriceTo.innerHTML = itemPriceFrom.innerHTML;
            }


            var cartItemsTableEl = document.getElementById('cart_items').getElementsByTagName('tr');
            if (cartItemsTableEl.length < 1) {
                setCookie("homeAlert", "fromEmptyCart", 1);
                var path = getHomeUrl();
                location.href = path;
            }


        },
        failure: function() {
        }
    });

}

var isInCustomizingMode = false;

function gotoMiniBasketCustomizePage(paramName, type, id, groupId, identifier, itemElem) {
        isInCustomizingMode = true;
        if (type == 'COMBINED') {
            customizing = 1;
        } else if (type == 'SINGLEMENUITEM') {
            customizing = 2;
        } else if (type == 'CATEGORY') {
            customizing = 3;
        }
        var myForm = document.createElement('form');
        myForm.setAttribute('method', 'POST');
        myForm.setAttribute('action', '<%=context%>/frontend.do');
        document.body.appendChild(myForm);

        var hid_paramName = document.createElement('input');
        hid_paramName.setAttribute('name', paramName);
        hid_paramName.setAttribute('value', id);

        var hid_operation = document.createElement('input');
        hid_operation.setAttribute('type', 'hidden');
        hid_operation.setAttribute('name', 'operation');
        hid_operation.setAttribute('value', 'goToCustomizePage');

        var hid_identifier = document.createElement('input');
        hid_identifier.type = 'hidden';
        hid_identifier.setAttribute('type', 'hidden');
        hid_identifier.setAttribute('name', 'identifier');
        hid_identifier.setAttribute('value', identifier);

        var hid_type = document.createElement('input');
        hid_type.setAttribute('type', 'hidden');
        hid_type.setAttribute('name', 'type');
        hid_type.setAttribute('value', type);

        var hid_groupId = document.createElement('input');
        hid_groupId.setAttribute('type', 'hidden');
        hid_groupId.setAttribute('name', 'groupId');
        hid_groupId.setAttribute('value', groupId);

        var hid_catId = document.createElement('input');
        hid_catId.setAttribute('type', 'hidden');
        hid_catId.setAttribute('name', 'catId');
        <%
        if(((Category)session.getAttribute(Constant.LAST_CATEGORY))!= null){
        %>
            hid_catId.setAttribute('value', '<%=((Category)session.getAttribute(Constant.LAST_CATEGORY)).getId()%>');
        <%
        }
        %>

        var hid_menuName = document.createElement('input');
        hid_menuName.setAttribute('type', 'hidden');
        hid_menuName.setAttribute('name', 'menuName');
        hid_menuName.setAttribute('value', '<%=session.getAttribute(Constant.MENU_NAME)%>');

        var hide_itemElem = document.createElement('input');
        hide_itemElem.setAttribute('type', 'hidden');
        hide_itemElem.setAttribute('name', 'itemToClick');
        hide_itemElem.setAttribute('value', itemElem);

        var hid_customizing = document.createElement('input');
        hid_customizing.setAttribute('type', 'hidden');
        hid_customizing.setAttribute('name', 'customizing');
        hid_customizing.setAttribute('value', customizing);

        myForm.appendChild(hid_operation);
        myForm.appendChild(hid_catId);
        myForm.appendChild(hid_paramName);
        myForm.appendChild(hid_type);
        myForm.appendChild(hid_groupId);
        myForm.appendChild(hid_menuName);
        myForm.appendChild(hid_identifier);
        myForm.appendChild(hide_itemElem);
        myForm.appendChild(hid_customizing);

        myForm.submit();
    }


function gotoCustomizePage2(clickedItem) {
    window.location = '<%=context%>/frontend.do?operation=goToCustomizePage&' +
                      'groupId=0&catId=11&type=COMBINED&combinedId=0&menuName=Menu&itemToClick=' + clickedItem;
}

function showBasket() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showBasket'},
        success: function(res) {
            alert(res);
        },
        failure: function() {
            alert('FAILURE');
        }

    });
}

function postalCodeAutoFocus1() {
    var valueLengh = document.getElementById("postalCode1").value.length;
    if (valueLengh >= 3)
        document.getElementById("postalCode2").focus();
}

function postalCodeAutoFocus2() {
    var valueLengh = document.getElementById("postalCode2").value.length;
    if (valueLengh >= 3)
        document.getElementById("streetNo").focus();
}

function autoCompleteStreet(streetNo, postalcode_1st, postalcode_2nd) {
    //alert("im here");

    var digitRegex = /^\s*\d+\s*$/;

    var isAnyFieldEmpty = true;
    var isAnyDataChanged = false;

    if (streetNo != null && postalcode_1st != null && postalcode_2nd != null && streetNo != "" && postalcode_1st != "" && postalcode_2nd != "" && streetNo.search(digitRegex) != -1) {
        isAnyFieldEmpty = false;
        //alert("ok");
    }
    if (lastEnteredStreetNo != streetNo || lastEnteredPostalCode1 != postalcode_1st || lastEnteredPostalCode2 != postalcode_2nd) {
        isAnyDataChanged = true;
    }

    if (!isAnyFieldEmpty) {
        //alert("no empty field");
        $.ajax({
            method: 'POST',
            url: '<%=context%>/finalcheckout.do',
            data:{
                operation: 'getStreetName',
                postalCode: postalcode_1st + ' ' + postalcode_2nd,
                streetNo: streetNo
            },
            success: function(res) {
                var streetName = '';
                var city = '';

                var strArray = res.split('*');
                streetName = strArray[0];
                city = strArray[1];

                if (document.getElementById('streetNo').value == streetNo) {
                    if (city == 'null' && streetName == 'null') {
                        document.getElementById('street').setAttribute('value', streetName);
                        document.getElementById('street').value = '';
                        document.getElementById('city').setAttribute('value', city);
                        document.getElementById('city').value = '';


                    } else if (city != 'null' && streetName == 'null') {
                        document.getElementById('street').setAttribute('value', streetName);
                        document.getElementById('street').value = '';
                        document.getElementById('city').setAttribute('value', city);
                        document.getElementById('city').value = city;

                    } else if (city == 'null' && streetName != 'null') {
                        document.getElementById('street').value = streetName;
                        document.getElementById('city').setAttribute('value', city);
                        document.getElementById('city').value = '';

                    } else {
                        $('#street').css('color', '#000000');
                        $('#city').css('color', '#000000');
                        document.getElementById('street').setAttribute('value', streetName);
                        document.getElementById('street').value = streetName;
                        document.getElementById('city').setAttribute('value', city);
                        document.getElementById('city').value = city;
                    }

                }
            },
            failure: function() {
                document.getElementById('street').removeAttribute('value');
            }
        });

    } else {
        if (isAnyFieldEmpty) {
            document.getElementById('street').setAttribute('value', '');
            document.getElementById('street').value = null;

            document.getElementById('city').setAttribute('value', '');
            document.getElementById('city').value = null;
        }
    }

    lastEnteredStreetNo = streetNo;
    lastEnteredPostalCode1 = postalcode_1st;
    lastEnteredPostalCode2 = postalcode_2nd;
}

function resetInput() {
    document.getElementById('street').value = '';
    $('#street').css('color', '#000000');

    document.getElementById('city').value = '';
    $('#city').css('color', '#000000');

}

function changeQuantity(sender, diff) {

    var quantityEl = $($(sender).parent()).children('input')[0];

    if ((parseInt(quantityEl.value) + diff ) < 0) {
        quantityEl.value = 0;

    } else {
        quantityEl.value = (parseInt(quantityEl.value) + diff );
    }
}

function goForCheckOut(suggestionsHasBennReviewed) {

    if (suggestionsHasBennReviewed) {
        gotoLoginPage('true', false);
        setOrderCouponCodeOnServer();

    } else {
        showSuggestions();
    }

}

function showSuggestions() {
    $("#suggestions").dialog({
        bgiframe: true,
        modal: true,
        resizable:false,
        autoOpen:false,
        width: 900,
        height: 'auto',
        close: function () {
            $('body').css('overflow', 'scroll');
            $('#suggestions').css('display', 'none');
        },
        open: function(event, ui) {
            $(event.target).parent().css('position', 'fixed');
            $(event.target).parent().css('top', '50%');
            $(event.target).parent().css('left', '50%');
            $(event.target).parent().css('margin-top', '-340px');
            $(event.target).parent().css('margin-left', '-400px');
            $('body').css('overflow', 'hidden'); //this line does the actual hiding
            $('#suggestions').css('display', 'block'); //this line show popup div
        }
    });
    $("#suggestions").dialog('option', 'title', '<bean:message key="suggestions.form.title"/>');
    $("#suggestions").dialog('open');
}

/*$(function() {

});*/

function addSuggestionsToBasket(){
    var selectedItems = "";
    $('#suggestionitems tr').each(
            function(index, sender){
                selectedItems += $(sender).find('input[name=quantity]')[0].value;
                selectedItems += ":";
                selectedItems += ($(sender).find('input[name=quantity_input]')[0].value) + ',';
            } );
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/checkout.do';

    var operation = document.createElement('input');
    operation.setAttribute('type','hidden');
    operation.setAttribute('name','operation');
    operation.setAttribute('value','addSuggestionsToBasket');

    var selectedSuggestions = document.createElement('input');
    selectedSuggestions.setAttribute('type','hidden');
    selectedSuggestions.setAttribute('name','selectedSuggestions');
    selectedSuggestions.setAttribute('value',selectedItems.toString());

    var isUserUncheckedDpDollar=document.createElement('input');
    isUserUncheckedDpDollar.setAttribute('type','hidden');
    isUserUncheckedDpDollar.setAttribute('name','isDiscountUsed');
    isUserUncheckedDpDollar.setAttribute('value','true');

    myForm.appendChild(operation);
    myForm.appendChild(selectedSuggestions);
    myForm.appendChild(isUserUncheckedDpDollar);
    myForm.submit();
}

function addSuggestions(){
   var selectedItems = "";
   $('#suggestionitems tr').each(
           function(index, sender){
               selectedItems += $(sender).find('input[name=quantity]')[0].value;
               selectedItems += ":";
               selectedItems += ($(sender).find('input[name=quantity_input]')[0].value) + ',';
           } );

     $.ajax({
            method: 'POST',
            url: '<%=context%>/checkout.do',
            data: {operation: 'addSuggestionsToBasket', selectedSuggestions : selectedItems.toString()},
            success: function(res) {
//                $("#suggestions").dialog('close');
                recommendsDialog('close');
                document.getElementById('center_column').innerHTML = res;

                var count = $('tr.cart_item').length;
                var price = $('#checkout_price_info #checkout_prices')[3].innerHTML;

                var itemNumEl = document.getElementById('item_num');
                if (itemNumEl != null)
                    itemNumEl.innerHTML = count;

                itemNumEl = document.getElementById("cart_block_shipping_cost");
                if (itemNumEl != null)
                    itemNumEl.innerHTML = count;

                var itemPriceTo = document.getElementById("cart_block_total");
                if (itemPriceTo != null)
                    itemPriceTo.innerHTML = price; 

                var itemPriceTo = document.getElementById("account_info");
                if (itemPriceTo != null)
                    itemPriceTo.innerHTML = '<bean:message key="label.rightmenu.header.total.price"/> ' + price;

                var cartItemsTableEl = document.getElementById('cart_items').getElementsByTagName('tr');
                if (cartItemsTableEl.length < 1) {
                    setCookie("homeAlert", "fromEmptyCart", 1);
                    var path = getHomeUrl();
                    location.href = path;
                }

            },
            failure: function() {
                $("#suggestions").dialog('close');
                alert('FAILURE');
            }
        });
}

function removeAllIBasketItems(){
//        return document.getElementById('menu_item_home').href;

    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/frontend.do';

    var operation = document.createElement('input');
    operation.setAttribute('type','hidden');
    operation.setAttribute('name','operation');
    operation.setAttribute('value','removeAllIBasketItems');

    var paramName = document.createElement('input');
    paramName.setAttribute('type','hidden');
    paramName.setAttribute('name','menuType');
    paramName.setAttribute('value',getMenuType());

    myForm.appendChild(operation);
    myForm.appendChild(paramName);
    myForm.submit();
}

function removeFullBasketItem(basketItemId, basketItemType){
//        return document.getElementById('menu_item_home').href;

    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/frontend.do';

    var operation = document.createElement('input');
    operation.setAttribute('type','hidden');
    operation.setAttribute('name','operation');
    operation.setAttribute('value','removeFullBasketItem');

    var basketItemId_param = document.createElement('input');
    basketItemId_param.setAttribute('type','hidden');
    basketItemId_param.setAttribute('name','basketItemId');
    basketItemId_param.setAttribute('value',basketItemId);

    var basketItemType_param = document.createElement('input');
    basketItemType_param.setAttribute('type','hidden');
    basketItemType_param.setAttribute('name','basketItemType');
    basketItemType_param.setAttribute('value',basketItemType);

    myForm.appendChild(operation);
    myForm.appendChild(basketItemId_param);
    myForm.appendChild(basketItemType_param);
    myForm.submit();
}

function gotoRedeemUerDollar(id,minVal){
    var el = Document.getElementById(id);
    var redeemedDollar = el.val;
    if(redeemedDollar < minVal){
        tAlert( 'Your Redeemed dollar is less than $' +minVal, '', 'error', 5000);
    }

    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/checkout.do';

    var operation = document.createElement('input');
    operation.setAttribute('type','hidden');
    operation.setAttribute('name','operation');
    operation.setAttribute('value','redeem');

    var redeemedDollar_param = document.createElement('input');
    redeemedDollar_param.setAttribute('type','hidden');
    redeemedDollar_param.setAttribute('name','redeemedDollar');
    redeemedDollar_param.setAttribute('value',redeemedDollar);

    myForm.appendChild(operation);
    myForm.appendChild(redeemedDollar_param);
    myForm.appendChild(basketItemType_param);
    myForm.submit();

}
function callDiscountOnTotalPrice(){

    var el = document.getElementById("isDiscountUsed");
//    var isChecked = $('.subtotal').find(':checkbox:checked');
//    var el = $('.subtotal').find('input[type=checkbox]')
    if ($(el).is(':checked')) {
        isUserUncheckedDpDollar = false;

    } else {
        isUserUncheckedDpDollar = true;
    }
    $.ajax({
        method: 'POST',
        url: '<%=context%>/checkout.do',
        data: {operation: 'calDiscountOnTotalPrice', isUserUncheckedDpDollar:isUserUncheckedDpDollar},
        success: function(res) {

            if(res.indexOf("pla�ant cette commande.")>-1){
                var res2=res.replace('pla�ant cette commande.','pla\u00E7ant cette commande.');
                $('.subtotal').html(res2);
            }else{

                $('.subtotal').html(res);
            }
            $('#basketTotalPrice').html($('.subtotal').find('.total-price').html());
            if(isUserUncheckedDpDollar){
                $(el).attr('checked',false);
                $('.subtotal').find('input[type=checkbox]').prop('checked',false);
            }
        }
    });
}

function getInvoice(){
    $.ajax({
        method: 'POST',
        url: '<%=context%>/checkout.do',
        data: {operation: 'calDiscountOnTotalPrice'},
        success: function(res) {

            if(res.indexOf("pla\u00E7ant cette commande.")>-1){
            var res2=res.replace('pla\u00E7ant cette commande.','pla\u00E7ant cette commande.');
                $('.subtotal').html(res2);
            }else{

            $('.subtotal').html(res);
            }

        }
    });
}

function showRecommend(action){
    window.alert("action1"+action);
    if(action == 'open'){
        $('#recommendsPopup').css({'display': 'block'});
        window.alert("action2"+action);
    } else if (action == 'close'){
        $('#recommendsPopup').css({'display': 'none'});
        window.alert("action3"+action);
    }
}

function getDeliveryUserContactInfo(val){

    var contactInfoListIndex = val.value;

    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/deliveryAddress.do';

    var operation = document.createElement('input');
    operation.setAttribute('type','hidden');
    operation.setAttribute('name','operation');
    operation.setAttribute('value','getDeliveryUserContactInfo');

    var contactInfoListIndex_param = document.createElement('input');
    contactInfoListIndex_param.setAttribute('type','hidden');
    contactInfoListIndex_param.setAttribute('name','contactInfoListIndex');
    contactInfoListIndex_param.setAttribute('value',contactInfoListIndex);

    myForm.appendChild(operation);
    myForm.appendChild(contactInfoListIndex_param);
    myForm.submit();
}
</script>
