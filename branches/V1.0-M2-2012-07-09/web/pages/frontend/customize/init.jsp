<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Sepehr
  Date: Feb 5, 2012
  Time: 2:17:47 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    String context = request.getContextPath();
    pageContext.setAttribute("loadOtherOptions", "1");
    Category curCategry = (Category)session.getAttribute(Constant.LAST_CATEGORY);
%>
<bean:define id="cus_mod" value="<%=((Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)).toString()%>" />

<script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>

<script  type="text/javascript" src="<%=context%>/js/custom-form-elements.js"> </script>
<link href="<%=context%>/css/custom-form-elements.css" rel="stylesheet" type="text/css"  />

<script type="text/javascript" src="<%=context%>/js/jquery/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=context%>/js/jquery/jquery.vaccordion.js"></script>

<link rel="stylesheet" href="<%=context%>/css/zebra_dialog.css" type="text/css">
<script type="text/javascript" src="<%=context%>/js/jquery/zebra_dialog.js"></script>

<script type="text/javascript">
var carousel_autoplay = 0;
var carousel_items_visible = 3;
var carousel_scroll = 1;

var isAnyTopSelected = false;
var captionArray = new Array();
var currentCaption = 'combinedType0';

var lastSelectedCategory = <%= request.getParameter("catId") %>;
var lastSelectedCarouselItem = null;
var lastSelectedSingleItem = null;
var lastSelectedCombinedTypeItem = null;
var lastSelectedCombinedTypeIndex = 0;
var lastSelectedCombinedItemProdNum = 'null';
var lastSelectedCombinedItemGroupId = 'null';
var lastSelectedCombinedItemElId = 'combinedItem_' + <%=request.getParameter("orderNumber")%>;

var firstCaptionClickCounter = 0;

var lastSelectedItemType = null;
var lastSelectedSIngleItemId = null;
var lastSelectedSIngleItemGroupId = null;
var lastSelectedSingleBasketItem = null;

var lastSelectedBasketSingle = null;
var lastSelectedCaption = null;

var customizedBasketItemId = null;

var captionArraySize = null;
var gotoCustomizePage = true;


var lastSelectedCaptionElementId = 'defaultSingle0';
var toppingSelectedState = 'false';
var customizing = <%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)%>;
var isInCustomizingMode = false;

var preselectedSingleItemsIsChanged = false;
var preselectedToppingsItemsIsChanged = false;


function setCustomizedBasketItemId(id) {
    customizedBasketItemId = id;
}

function getCustomizedBasketItemId() {
    return customizedBasketItemId;
}


function setCurrentCaptionStatusTrue(){
    var index = currentCaption.substr(12);
    captionArray[index] = true;
}


function setCurrentCaption(curCap) {
    currentCaption = curCap;
}

function getCurrentCaption() {
    return currentCaption;
}


function initialCaptionArray(size) {
    captionArraySize = size;
    captionArray = new Array(size);

    for (var i = 0; i < size; i++) {
        captionArray[i] = false;
    }
}

function setCaptionArrayTrue() {
    for (var i = 0; i < captionArray.length ; i++) {
        captionArray[i] = true;
    }
}
function resetCaptionArray(){
    for(var i = 0; i < captionArray.length; i++) {
        captionArray[i] = false;
    }

}


function gotoLoginPage(isFromCheckout) {

    var myForm = document.createElement('form');
    myForm.setAttribute('method','post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type' ,'hidden');
    operation.setAttribute('name' ,'operation');
    operation.setAttribute('value' ,'goToLoginPage');

    var latestUrl = document.createElement('input');
    latestUrl.setAttribute('type' ,'hidden');
    latestUrl.setAttribute('name' ,'<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>');
    latestUrl.setAttribute('value' ,isFromCheckout);

    myForm.appendChild(operation);
    myForm.appendChild(latestUrl);

    myForm.submit();
}



function setLastCaptionElId(id) {
    lastSelectedCaptionElementId = id;
}

function setLastCaptionElIdDefault() {
    lastSelectedCaptionElementId = 'defaultSingle0';
}

function getLastCaptionElId() {
    return lastSelectedCaptionElementId;
}

function setToppingSelected(status) {
    toppingSelectedState = status;
}

function setLastSelectedCaption(id) {
    lastSelectedCaption = id;
}

function setIdAndGroupId(id, groupId){
    lastSelectedSIngleItemId = id;
    lastSelectedSIngleItemGroupId = groupId;
}

function setLastCombinedProdNumAndGoupId(prodNum, groupId){
    lastSelectedCombinedItemProdNum = prodNum;
    lastSelectedCombinedItemGroupId = groupId;
}

function resetIdAndGroupId() {
    lastSelectedSIngleItemId = null;
    lastSelectedSIngleItemGroupId = null;
}

function setLastSelectedItemType(type) {
    lastSelectedItemType = type;
}


$('#categoryItems').html("");
$('#center_column_inner_mir').html("");
$('#combinedTypesMenu').html("");
$('#basketList').html("");



function getToppingsAsString() {
    var topHid_NX_Array = $('[id^="top_hid_"]');
    var topHid_X_Array = $('[id^="extHid_"]');
    var result = "";
    var resultX = "";
    $.each(topHid_X_Array, function() {
        if (this.value != '' && this.value != null && this.value != undefined)
        {
            resultX += this.value + '#';
        }
    });


    var resultNX = "";
    $.each(topHid_NX_Array, function(){
        if (this.value != '' && this.value != null && this.value != undefined)
        {
            resultNX += this.value + '#';
        }
    });
    result = resultNX + '*' + resultX;
    return result;
}

function getBasketItems(){
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    : "operation=getBasketItems",
        success:function(res) {
            if (res != "") {
                $('#basketList').html(res);

                var elementName = '<%=request.getParameter("itemToClick")%>';
                if (elementName != null && elementName != "null" && gotoCustomizePage) {
                    $('#' + elementName).click();
                    gotoCustomizePage = false;
                }
            }
        },
        failure: function()     {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function addItemToBasket(basketItemType, catId, groupId, productNo, menuSingleId){
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'addItemToBasket', basketItemType: basketItemType, catId: catId, groupId: groupId, productNo: productNo, menuSingleId: menuSingleId},
        success:function(res) {
            if (res != "") {
                $('#basketList').html(res);
            }


            window.location.reload();

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function addCombinedItemInSessionToBasket(singleCount){
    var singleItems = "";
    var msg = "<bean:message key='message.choose.basketItem' arg0=' notSelectedItems '/>";
    for(var i = 0; i < singleCount; i++){
        var value = "";
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null"){
            tAlert(msg.replace('notSelectedItems', notSelectedCombinedTypeToString()), '', 'error', 10000);
            return;
        }

        singleItems += value + "-";

        var el = document.getElementById("defaultSingleGroupId" + i);
        if (el != null)
            value = el.getAttribute("value");

        singleItems += value + "#";
    }

    if (singleItems == ""){
        tAlert(msg.replace('notSelectedItems', notSelectedCombinedTypeToString()), '', 'error', 10000);

        return;
    }

    if (isAnyTopSelected) {
        zConfirm('<bean:message key="message.u.will.looz.ur.selected.tops.apply.them.be4.adding.to.cart"/>', 'Confirm', 400,
                function(button) {
                    var addCombinedFunc = function(){
                        $.ajax({
                            type    :'POST',
                            url     : '<%=context%>/frontend.do',
                            data    :{operation: 'addCombinedItemFromSessionToBasket'},
                            success:function(res) {
                                if (res != "") {
                                    $('#basketList').html(res);

                                    var el = document.getElementById("imgClearSelectedItems");
                                    if (el != null)
                                        el.onclick();
                                    setCookie("homeAlert", "fromCustomize", 1);
                                    var path = getHomeUrl();
                                    location.href = path;
                                }
                            },
                            failure: function() {
                                alert('<bean:message key="message.error.in.server"/>');
                            }
                        });

                    };
                    if (button == 'Yes') {
                        //                        document.getElementById('apply_but').onclick();
                        applyToppingsOnSession(addCombinedFunc);
                    }else{
                        addCombinedFunc();
                    }
                });
    } else {

        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'addCombinedItemFromSessionToBasket'},
            success:function(res) {
                if (res != "") {
                    $('#basketList').html(res);

                    var el = document.getElementById("imgClearSelectedItems");
                    if (el != null)
                        el.onclick();
                    setCookie("homeAlert", "fromCustomize", 1);
                    var path = getHomeUrl();
                    location.href = path;
                }
            },
            failure: function() {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });

    }

}


function addSingleItemToBasket(groupId, menuSingleId){
    addItemToBasket("<%= FrontendAction.ClassTypeEnum.SINGLEMENUITEM.toString() %>", 0, groupId, 0,menuSingleId);
}

function addCombinedItemToBasket(groupId, productNo){
    addItemToBasket("<%= FrontendAction.ClassTypeEnum.COMBINED.toString() %>", lastSelectedCategory, groupId, productNo, 0);
}

function getSelectedCarouselItemFromRequest(){
    return document.getElementById("<%= "frontDiv" + request.getAttribute("selectedMenuItem") %>");
}

function selectCarouselItem(elemntId){
    var elementItem = document.getElementById(elemntId);
    if (lastSelectedCarouselItem != null)
        lastSelectedCarouselItem.removeAttribute("style");
    document.getElementById(elementItem.id).setAttribute("style", " background:url(./img/carousel_active.png) 0 0 no-repeat;");

    if (elementItem.id.search("combined") > 0){
        var combinedMenuItem = $('#' + elementItem.id + ' h5 a')[0].innerHTML;
        updateBottomCombined(combinedMenuItem);
    }

    lastSelectedCarouselItem = elementItem;

    $('#mycarousel').children('li').each(function(index) {
        this.style.opacity = 0.5;
    });

    elementItem.parentNode.style.opacity = 1;
}


function selectSingleItem(elemntId){
    var elementItem = document.getElementById(elemntId);
    if (lastSelectedSingleItem != null)
        lastSelectedSingleItem.removeAttribute("style");
    if (elementItem != null)
    //        document.getElementById(elementItem.id).setAttribute("style", " background:url(./img/carousel_active.png) 0 0 no-repeat;");
        elementItem.setAttribute("style", "background:url(./img/carousel_active.png) 0 0 no-repeat;");

    lastSelectedSingleItem = elementItem;
    if(elemntId != null){
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');

    }
}

function selectCombinedTypeItem(elemntId, listOrder){
    var elementItem = document.getElementById(elemntId);
    if (lastSelectedCombinedTypeItem != null)
        lastSelectedCombinedTypeItem.setAttribute("class", "");
    document.getElementById(elementItem.id).setAttribute("class", "current");

    $('#main_menu').children('ul').children('li').each(function(index) {
        this.style.opacity = 0.5;
        this.style.backgroundColor = "#fff";
        this.style.borderWidth = "2px";
        this.style.borderColor = "#E7D259";
        this.style.borderBottom = this.style.borderTop;
        this.style.height = "53px";
        this.className = "";


        $(this).children('#customizeLink').each(function(index) {
            this.onclick = null;
        });

        $(this).children('#customizeLink').hover(
                function(){

                    if (index == listOrder){
                        var el = document.getElementById('defaultSingleId' + index);
                        if (el != null){
                            var value = el.getAttribute("value");
                            if (value != null & value != "" & value != "null"){
                                this.style.color = "lime";
                            }
                        }
                    }
                    else{
                        this.style.color = "#756262";

                    }
                },
                function(){
                    this.style.color = "#756262";
                }
                );

    });

    var liEl = $('#main_menu').children('ul').children('li')[listOrder];
    liEl.style.opacity = 1;
    liEl.style.backgroundColor = "#fccc04";
    liEl.style.borderWidth = "6px";
    liEl.style.borderColor = "#fccc04";
    liEl.style.borderBottom = "none";
    liEl.style.height = "57px";
    liEl.style.zIndex = 10;
    liEl.className += "selectedItem-shadow";


    var myLink = $(liEl).children('#customizeLink')[0];
    myLink.onclick = function (){
        var el = document.getElementById('defaultSingleId' + listOrder);
        if (el != null){
            var value = el.getAttribute("value");
            if (value != null & value != "" & value != "null"){
                scrollToToppings();
            }
        }
    }


    lastSelectedCombinedTypeItem = elementItem;
    lastSelectedCombinedTypeIndex = listOrder;
}

function scrollToToppings(){
    var new_position = $('#featured-products_block_center').offset();
    $('html,body').animate({scrollTop: new_position.top},'slow');
}

function scrollToToppingsIfNotCombined(){
    if ($('#main_menu').length <= 0)
        scrollToToppings();
}


function changeLangFlag(currentLang){
    if (currentLang == 'fr') {
        document.getElementById('frn_link').setAttribute('href', 'javascript: void(0);');
        document.getElementById('frn_flag').removeAttribute('class');
        document.getElementById('frn_flag').setAttribute('class', 'unselected_lang');
        document.getElementById('eng_link').setAttribute('href', '<%=request.getContextPath()%>'+
                                                                 '/locale.do?method=changeLangToEnglish&forward=forwardToCustomizePage');//forward=welcome is not a good solution
        document.getElementById('eng_flag').removeAttribute('class');
        document.getElementById('eng_flag').setAttribute('class', 'selected_lang');
        document.getElementById('menu_item_lang').setAttribute('href', '<%=request.getContextPath()%>'+
                                                                       '/locale.do?method=changeLangToEnglish&forward=forwardToCustomizePage');
    } else if ('en_US') {
        document.getElementById('frn_link').setAttribute('href', '<%=request.getContextPath()%>'+
                                                                 '/locale.do?method=changeLangToFrench&forward=forwardToCustomizePage');
        document.getElementById('frn_flag').removeAttribute('class');
        document.getElementById('frn_flag').setAttribute('class', 'selected_lang');
        document.getElementById('eng_link').setAttribute('href','javascript: void(0);');
        document.getElementById('eng_flag').removeAttribute('class');
        document.getElementById('eng_flag').setAttribute('class', 'unselected_lang');
        document.getElementById('menu_item_lang').setAttribute('href', '<%=request.getContextPath()%>'+
                                                                       '/locale.do?method=changeLangToFrench&forward=forwardToCustomizePage');
    }

}

function getSubCategoryItems(categoryId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getSubCategoryItems', catId: categoryId},
        success:function(res) {
            if (res != "") {
                $('#center_column_inner_mir').html(res);
                $('#mycarousel_mir').jcarousel({
                    auto: carousel_autoplay,
//                    wrap: 'last',
                    visible: carousel_items_visible,
                    scroll: carousel_scroll
                });
                getItemToppingsView(null, null, null, 'CATEGORY');

            }
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getCombinedItems(productNo, groupId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getCombinedItems', productNo: productNo, groupId : groupId, catId: lastSelectedCategory},
        success:function(res) {
            if (res != "") {
                $('#combinedTypesMenu').html(res);

                selectCombinedTypeItem("combinedType0", 0);
            }
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function setLastSelectedCombinedOnSession(productNo, groupId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'setLastSelectedCombinedOnSession', productNo: productNo, groupId : groupId, catId: lastSelectedCategory},
        success:function(res) {

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}


function getSingleMenuItems(singleMenuId, singleMenuGroupId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getSingleMenuItems', singleMenuId: singleMenuId, singleMenuGroupId: singleMenuGroupId, selectedCatId: lastSelectedCategory},
        success:function(res) {
            if (res != "") {
                //                    $('#main_menu').html(res);
            }

            getItemToppingsView(singleMenuId, singleMenuGroupId, null, 'SINGLEMENUITEM');

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getSingleMenuItemsForType(productNo,groupId,itemIndex) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getSingleMenuItemsForType', productNo: productNo, groupId: groupId, itemIndex: itemIndex, catId: lastSelectedCategory},
        success:function(res) {
            if (res != "") {
                $('#center_column_inner_mir').html(res);
                $('#mycarousel_mir').jcarousel({
                    auto: carousel_autoplay,
//                    wrap: 'last',
                    visible: carousel_items_visible,
                    scroll: carousel_scroll
                });

                var el = document.getElementById("defaultSingleId" + itemIndex);
                var el1 = document.getElementById("defaultSingleGroupId" + itemIndex);
                if (el != null){
                    var singleId = el.getAttribute("value");
                    var groupId  = el1.getAttribute("value");
                    if (singleId != null){
                        setIdAndGroupId(singleId, groupId);
                        selectSingleItem("endDivsingle" + singleId);
                    }
                }

            }
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getCustomizePage(itemid) {
    $.ajax({
        type    :'POST',
        url     :'<%=context%>/frontend.do',
        data    :{operation: 'getSingleItemDefaultToppings', menuSingleItemId: itemid},
        success :function(res){
            alert(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

}

function setLastSelectedBasketItem(lastBasketSingle) {
    lastSelectedSingleBasketItem = lastBasketSingle;
}

function requestToppings(choose) {
    var exData;
    if (choose == 'COMBINED' || choose == 'CATEGORY') {
        exData = {
            operation: 'getSingleItemToppings',
            id: null,
            groupId: null,
            type: lastSelectedItemType};
    }else if(choose == 'SINGLEMENUITEM'){
        exData = {
            operation: 'getSingleItemToppings',
            id:                 lastSelectedSIngleItemId,
            groupId:            lastSelectedSIngleItemGroupId,
            catId:              lastSelectedCategory,
            type:               lastSelectedItemType,
            singleBasketItemId: lastSelectedBasketSingle
        };
    }else if (choose == 'SINGLEBASKET') {
        exData = {
            operation: 'getSingleItemToppings',
            id: null,
            groupId: null,
            type: lastSelectedItemType,
            singleBasketItemId: lastSelectedSingleBasketItem};
    }

    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: exData,
        success:function(res) {
            $('#featured-products_block_center').html(res);
        },
        failure:function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getCombinedOrcatPic() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getCombinedOrCatPic',
            itemType: lastSelectedItemType
        },
        success:function(res) {
            $('#featured-products_block_center').html(res);
        },
        failure:function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

}

function getCominedPicBack() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getCombinedPicBack',
            itemType: lastSelectedItemType
        },
        success:function(res) {
            $('#featured-products_block_center').html(res);
        },
        failure:function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function applyToppingValue() {
    var result = getToppingsAsString();
    $.ajax({
        method: 'post',
        url: '<%=context%>/toppingAction.do',
        data:{operation:'applyTopings',
            toppings: result,
            combinedItemProdNum: lastSelectedCombinedItemProdNum,
            combinedItemGroupId: lastSelectedCombinedItemGroupId,
            singleItemId:        lastSelectedSIngleItemId,
            singleItemGroupId:   lastSelectedSIngleItemGroupId,
            basketSingleIdentifier:        lastSelectedBasketSingle,
            singlebasketInSesionId : customizedBasketItemId

        },
        success: function(res) {
            setCookie("homeAlert", "fromCustomize", 1);
            var path = getHomeUrl();
            //            var homeLink = document.getElementById('menu_item_home');
            //            homeLink.click();
            //            location.href = path;

            //            var path = document.getElementById('menu_item_home').href;
            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", path);
            document.body.appendChild(form);
            form.submit();


        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function getNewPrice() {
    var topHid_NX_Array = $('[id^="top_hid_"]');
    var topHid_X_Array = $('[id^="extHid_"]');
    var result = "";
    var resultX = "";
    $.each(topHid_X_Array, function() {
        if (this.value != '' && this.value != null && this.value != undefined)
        {
            resultX += this.value + '#';
        }
    });


    var resultNX = "";
    $.each(topHid_NX_Array, function(){
        if (this.value != '' && this.value != null && this.value != undefined)
        {
            resultNX += this.value + '#';
        }
    });
    result = resultNX + '*' + resultX;

    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getNewPriceByNewToppings', singleItemId: lastSelectedSIngleItemId,  singleItemGroupIdStr: lastSelectedSIngleItemGroupId, toppings: result},
        success: function(res){
            //            $('#up_price').html(res);
            //            $('#down_price').html(res + " $");
            var priceLabels = $('[id^="down_price"]');

            $.each(priceLabels, function() {
                this.innerHTML = res + ' $';
            });

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });



}

function updatePrice(price) {
    var el = document.getElementById("up_price");
    if (el != null)
        el.innerHTML = price;

    var el = document.getElementById("down_price");
    if (el != null)
        el.innerHTML = "<bean:message key="label.capital.price"/> " + price + "$";
}

function updateDefaultMenuSingleOnType(menuSingleName, menuSingleId, menuSingleGroupId){
    var el = document.getElementById("defaultSingle" + lastSelectedCombinedTypeIndex);
    if (el != null)
        el.innerHTML = menuSingleName;
    var preSingleId;
    var preGroupId;

    var el = document.getElementById("defaultSingleId" + lastSelectedCombinedTypeIndex);
    if (el != null){
        preSingleId = el.getAttribute("value");
        el.setAttribute("value", menuSingleId);
    }
    var el = document.getElementById("defaultSingleGroupId" + lastSelectedCombinedTypeIndex);
    if (el != null){
        preGroupId = el.getAttribute("value");
        el.setAttribute("value", menuSingleGroupId);
    }

    if (!preselectedSingleItemsIsChanged)
        setPreselectedSingleItemsIsChanged( (preSingleId != menuSingleId || preGroupId != menuSingleGroupId) );
}

function addSelectedCombinedToBasket(productNo, groupId, singleCount){

    var singleItems = "";
    for(var i = 0; i < singleCount; i++){
        var value = "";
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null"){
            tAlert("<bean:message key='message.choose.basketItem'/>", '', 'error', 10000);
            return;
        }

        singleItems += value + "-";

        var el = document.getElementById("defaultSingleGroupId" + i);
        if (el != null)
            value = el.getAttribute("value");

        singleItems += value + "#";
    }

    if (singleItems == ""){
        tAlert("<bean:message key='message.choose.basketItem'/>", '', 'error', 10000);
        return;
    }

    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'addBasketCombinedItemToBasket', groupId: groupId, productNo: productNo, menuSingleItems: singleItems},
        success:function(res) {
            if (res != "") {
                $('#basketList').html(res);
            }

            window.location.reload();

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

}

function setToNoSelectedItem(singleCount, noSelectedItemCaption){
    for(var j = 0; j < singleCount; j++){
        setToNoSelectedTypeItem(j, noSelectedItemCaption);
    }
}

function setToNoSelectedTypeItem(itemIndex, noSelectedItemCaption){
    var el = document.getElementById("defaultSingle" + itemIndex);
    if (el != null)
        el.innerHTML = noSelectedItemCaption;

    var el = document.getElementById("defaultSingleId" + itemIndex);
    if (el != null)
        el.setAttribute("value", "");

    var el = document.getElementById("defaultSingleGroupId" + itemIndex);
    if (el != null)
        el.setAttribute("value", "");
}


function resetCombinedAndSingleMenu(){
    $('#center_column_inner_mir').html("");
    $('#combinedTypesMenu').html("");
}

function combinedTypeItemOnClick(itemIndex){
    var item = document.getElementById("combinedType" + itemIndex);
    if (item != null)
        item.onclick();

}

function setBasketSingleItemIdentifier(id) {
    lastSelectedBasketSingle = id;

}

function applyToppingsOnSession(onSuccess) {
    var toppings = getToppingsAsString();
    $.ajax({
        method: 'POST',
        url: '<%=context%>/frontend.do',
        data: { operation:          'applyToppingsOnBasketCombinedItemInSession',
            basketSingleItemId: lastSelectedBasketSingle,
            singleId: lastSelectedSIngleItemId,
            singleGroupId: lastSelectedSIngleItemGroupId,
            toppings:           toppings,
            basketItemIdentifier : customizedBasketItemId

        },
        success: function(res) {
            if (onSuccess != null){
                onSuccess();
            }
            else{
                if (customizing != 0)
                    getBasketItems();
                window.scrollTo(0, 0);
                tAlert(res, '', 'success', 5000);
            }
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}


function putCombinedInSession(productNo, groupId, basketItemId) {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'putCombinedToSessionAsBasketCombined',
            Pragma: "No-Cash",
            Cash: "max-age=0,no-cache,no-store",
            combinedprodNum: productNo,
            combinedGroupId: groupId,
            basketItemId: basketItemId
        },
        success: function() {
            getCombinedItems(productNo, groupId);
            getItemToppingsView(groupId, productNo, null, null);
            if (basketItemId != null)
                getBasketItems();
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

}



function showCaptionStatus() {
    var result = '';
    for (var i = 0; i < captionArray.length; i++) {
        result += i + '-' + captionArray[i] + '\n';
    }
    alert(result);
}



function showBasket() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showBasket'},
        success: function(res){
            alert(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function showSessionItemNums() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showSession'},
        success: function(res){
            alert(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function showBasketSingleInCombinedInSession() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showBasketSingleInCombinedInSession'},
        success: function(res){
            alert(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}
function updateBasketItemCounts(value){
    $('.basket-items-count').each(function(index) {
        this.innerHTML = value;
    });
}

function getPreSelectedToppings () {
    $.ajax({
        method : 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation:                   'getPreSelectedToppings',
            basketSingleIdentifier :     lastSelectedBasketSingle
        },
        success: function(res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function updateBasketTotalPrice(value){
    var el = document.getElementById("basketTotalPrice");
    if (el != null)
        el.innerHTML = value;

}

function removeItemFromBasket(basketItemId, basketItemType){
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'removeItemFromBasket', basketItemId: basketItemId, basketItemType: basketItemType},
        success:function(res) {
            if (res != "") {
                $('#basketList').html(res);

                setCookie("homeAlert", "fromDelete", 1);
                var path = getHomeUrl();
                location.href = path;
            }
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });


}

function checkAnyItemIsSelectedAndConfirmFromUserToContinue(itemIndex){
    var singleCount = $('#main_menu').children('ul').children('li').length;

    if (singleCount <= 0)
        return false;

    if (lastSelectedCarouselItem != null){
        if (lastSelectedCarouselItem.parentNode == $('#mycarousel').children('li')[itemIndex])
            return false;
    }

    var confirmFromUser = false;
    for(var i = 0; i < singleCount; i++){
        var value = "";
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value != null & value != "" & value != "null"){
            confirmFromUser = true;
            break;
        }
    }


    return confirmFromUser;

}



function allowSelectMoreToppings(toppingElId){
    var defaultToppingsCount = 0;
    var maxToppingsCount = 5;

    var el = document.getElementById(toppingElId);
    var className = el.className;
    var countable = el.getAttribute("countable");

    if (className != "btn_clear" || countable == "false")
        return true;

    var selectedToppingsCount = 0;
    $('input[value*="/full"]').each(
            function(index){
                if ($(this).attr("countable")  == "true")
                    selectedToppingsCount++;
            });

    $('input[value*="/left"]').each(
            function(index){
                if ($(this).attr("countable")  == "true")
                    selectedToppingsCount++;
            });

    $('input[value*="/right"]').each(
            function(index){
                if ($(this).attr("countable")  == "true")
                    selectedToppingsCount++;
            });

    if (selectedToppingsCount + defaultToppingsCount < maxToppingsCount) {
        return true;
    }
    else{
        var msg = "<bean:message key='message.error.max.select.toppings' arg0="maxValue"/>";
        tAlert(msg.replace("maxValue", maxToppingsCount) , '', 'error', 10000);
        return false;
    }
}

function editBasketCombined(identifier, categoryId, combinedProductNo, combinedgroupId){
    customizing = 1;

    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, productNo: combinedProductNo,
            groupId: combinedgroupId, type: "COMBINED", customizeMode: customizing},
        success:function(res) {
            lastSelectedCategory = categoryId;

            $.ajax({
                type    :'POST',
                url     : '<%=context%>/frontend.do',
                data    :{operation: 'getCategoryItems', catId: categoryId},
                success:function(res) {
                    $('#categoryItems').html(res);
                    $('#mycarousel').jcarousel({
                        auto: carousel_autoplay,
//                        wrap: 'last',
                        visible: carousel_items_visible,
                        scroll: carousel_scroll
                    });
                    onCustomizing();
                    //                    setCaptionArrayTrue();
                },
                failure: function() {
                    alert('<bean:message key="message.error.in.server"/>');
                }
            });
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

    window.scrollTo(0, 0);

}

function getItemToppingsView(groupId, prodOrId, basketSingleIdentifier, itemType) {
    $.ajax({
        method: 'POST',
        url:    '<%=context%>/toppingAction.do',
        data:   {operation: 'getItemToppingsView',
            groupId: groupId,
            prodNum_id: prodOrId,
            basketSingleIdentifier : basketSingleIdentifier,
            customizedBasketItemId : customizedBasketItemId,
            itemType: itemType
        },
        success: function(res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function resetBasketItemToppings(basketSingleIdentifier) {
    $.ajax({
        method: 'POST',
        url:    '<%=context%>/toppingAction.do',
        data:   {operation: 'resetBasketItemToppings',
            basketSingleIdentifier : basketSingleIdentifier
        },
        success: function(res) {
            captionArray[lastSelectedCombinedTypeIndex] = true;
            setPreselectedToppingsItemsIsChanged(false);
            getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, null, 'SINGLEMENUITEM');

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}


function editBasketSingle(identifier, categoryId, singleId, groupId){
    $('#categoryItems').html("");
    $('#center_column_inner_mir').html("");
    $('#combinedTypesMenu').html("");
    customizing = 2;

    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, singleId: singleId,
            groupId: groupId, type: "SINGLEMENUITEM", customizeMode: customizing},
        success:function(res) {
            lastSelectedCategory = categoryId;

            $.ajax({
                type    :'POST',
                url     : '<%=context%>/frontend.do',
                data    :{operation: 'getCategoryItems', catId: categoryId},
                success:function(res) {
                    $('#categoryItems').html(res);
                    $('#mycarousel').jcarousel({
                        auto: carousel_autoplay,
//                        wrap: 'last',
                        visible: carousel_items_visible,
                        scroll: carousel_scroll
                    });

                    lastSelectedItemType = "SINGLEMENUITEM";
                    lastSelectedSIngleItemId = singleId;
                    lastSelectedSIngleItemGroupId = groupId;

                    requestToppings(lastSelectedItemType);
                    onCustomizing();

                },
                failure: function() {
                    alert('<bean:message key="message.error.in.server"/>');
                }
            });

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
    window.scrollTo(0, 0);
}

function editBasketSingleInSubCategory(identifier, categoryId, subCategoryId, singleId, groupId) {
    $('#categoryItems').html("");
    $('#center_column_inner_mir').html("");
    $('#combinedTypesMenu').html("");
    customizing = 3;

    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, subCatId: subCategoryId , singleId: singleId,
            groupId: groupId, type: "CATEGORY", customizeMode: customizing},
        success:function(res) {
            lastSelectedCategory = categoryId;

            $.ajax({
                type    :'POST',
                url     : '<%=context%>/frontend.do',
                data    :{operation: 'getCategoryItems', catId: categoryId},
                success:function(res) {
                    $('#categoryItems').html(res);
                    $('#mycarousel').jcarousel({
                        auto: carousel_autoplay,
//                        wrap: 'last',
                        visible: carousel_items_visible,
                        scroll: carousel_scroll
                    });

                    lastSelectedItemType = "SINGLEMENUITEM";
                    setIdAndGroupId(singleId, groupId);
                    onCustomizing();


                },
                failure: function() {
                    alert('<bean:message key="message.error.in.server"/>');
                }
            });

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
    window.scrollTo(0, 0);
}

function getToppingsOrView() {
    //    var index =  currentCaption.substr(12);

    if (document.getElementById(getLastCaptionElId()).innerHTML == '<bean:message key="label.no.item.selected"/>') {
        getItemToppingsView(null, null, null, 'COMBINED');
    }else {
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');
    }
}

function putThisSingleInSession(id, groupId) {
    if (captionArray.length >= 1) {
        var identifier = lastSelectedBasketSingle;
        $.ajax({
            method: 'POST',
            url:     '<%=context%>/toppingAction.do',
            data: {operation: 'setThisSingleInSession',
                id: id,
                groupId: groupId,
                identifier: lastSelectedBasketSingle},
            success: function(){
                selectSingleItem('endDivsingle' + id);
                getBasketItems();
            },
            failure: function() {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
    }
}


function loadCustomizePage(catId, type, singleId, groupId){
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getCategoryItems', catId: catId},
        success:function(res) {
            $('#categoryItems').html(res);
            $('#mycarousel').jcarousel({
                auto: carousel_autoplay,
//                wrap: 'last',
                visible: carousel_items_visible,
                scroll: carousel_scroll
            });

        },
        failure: function() {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

    getBasketItems();
    lastSelectedItemType = type;
    lastSelectedSIngleItemId = singleId;
    lastSelectedSIngleItemGroupId = groupId;

    lastSelectedCombinedItemGroupId = '<%=request.getParameter("groupId")%>';
    lastSelectedCombinedItemProdNum = '<%=request.getParameter("combinedId")%>';

    if('<%=request.getParameter("type")%>' == 'COMBINED') {
        putCombinedInSession(lastSelectedCombinedItemGroupId, lastSelectedCombinedItemProdNum, null);
    }
    getItemToppingsView(lastSelectedCombinedItemGroupId, lastSelectedCombinedItemProdNum, null, null);
}



function trim(s){
    var l=0; var r=s.length -1;
    while(l < s.length && s[l] == '  ')
    {	l++; }
    while(r > l && s[r] == ' ')
    {	r-=1;	}
    return s.substring(l, r+1);
}

function notSelectedCombinedTypeToString(){
    var itemCaptions = new Array();
    var result = "";
    var items = $('#main_menu').children('ul').children('li');
    var itemCount = items.length;
    items.each(function(index){
        var value = null;
        var el = document.getElementById("defaultSingleId" + index);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null"){
            var caption = null;
            var el = document.getElementById("combinedType" + index);
            if (el != null)
                caption = el.innerHTML;

            if (!itemCaptions.contains(caption)){
                itemCaptions[itemCaptions.length] = caption;
                if (index == 0)
                    result = caption;
                else if (index + 1 == itemCount){ //==> means last item
                    if (result.length > 0)
                        result = result + " and " + caption;
                    else
                        result = caption;
                }
                else{
                    if (result.length > 0)
                        result = result + " , " + caption;
                    else
                        result = caption;
                }
            }
        }

    });
    return result;
}

function isPageSameOfSession(state, ItemOrder, productNo, groupId) {
    var singleName = document.getElementById('defaultSingle' + currentCaption.substr(12));
    var topps = getToppingsAsString();
    var singleNamesStr = '';

    var i = 0;
    while (true) {
        var captionEl = document.getElementById('defaultSingle' + i);
        if (captionEl == null || captionEl == 'null') {
            break;
        } else {
            singleNamesStr += captionEl.innerHTML + '#';
            i++;
        }
    }
    
    var itemsIsChanged = getPreselectedItemsIsChanged();
    if (itemsIsChanged) {
        zConfirm('<bean:message key="message.confirm.want.to.leave"/>', 'Confirm', 0, function(button){
            if (button == 'Yes')
                changeCombinedItem(productNo, groupId, ItemOrder);
        });
    }
    else
        changeCombinedItem(productNo, groupId, ItemOrder);
}

function changeCombinedItem(productNo, groupId, ItemOrder){
    setPreselectedSingleItemsIsChanged(false);
    isAllCaptionsClicked = false;
    resetCombinedAndSingleMenu();
    putCombinedInSession(productNo, groupId, null);
    resetIdAndGroupId();
    selectCarouselItem('frontDivcombined' + productNo + groupId);
    setToppingSelected('false');
    setLastCombinedProdNumAndGoupId(productNo, groupId);
    setToppingSelectedState(false);
    lastSelectedCombinedItemElId = 'combinedItem_' + ItemOrder;
    currentCaption = 'combinedType0';
}

function resetCaption() {

<%--<c:set value="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)%>" var="cusState"/>--%>

//    if (isInCustomizingMode) {
    if (false) {
        $.ajax({
            type    :'POST',
            url     : '<%=context%>/toppingAction.do',
            data    :{operation: 'resetBasket',
                combinedGroupId: lastSelectedCombinedItemProdNum,
                combinedProdNum: lastSelectedCombinedItemGroupId},
            success:function(res) {
                if (res != "") {

                }
            },
            error: function(){}
        });
        getBasketItems();
    }


    getItemToppingsView(lastSelectedSIngleItemGroupId , lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');

    isAllCaptionsClicked = false;
    resetCombinedAndSingleMenu();
    if (!isInCustomizingMode){
        putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, null);
    }else{
        putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, getCustomizedBasketItemId());
    }

    resetIdAndGroupId();
    selectCarouselItem('frontDivcombined' + lastSelectedCombinedItemProdNum + lastSelectedCombinedItemGroupId);
    setToppingSelected('false');
    setLastCombinedProdNumAndGoupId(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId);
    setToppingSelectedState(false);
    lastSelectedCombinedItemElId = 'combinedItem_0';
    currentCaption = 'combinedType0';
    setPreselectedSingleItemsIsChanged(false);
}



function logout() {
    var currentUrl = window.location;
    window.location.href = '<%=context%>/logout.do?operation=logout&<%=Constant.LATEST_USER_URL%>=' + currentUrl;
}



function zConfirm(text, title, width, onClose){
    $.Zebra_Dialog(text,{'title': title,'type': 'question', 'overlay_close' : false, 'keyboard' : true, 'width' : width , 'onClose' : onClose });
}

function zAlert(text, title, width, onClose){
    $.Zebra_Dialog(text,{'title': title,'type': 'information', 'overlay_close' : false, 'keyboard' : true, 'width' : width , 'onClose' : onClose });
}

function setToppingSelectedState(state){
    isAnyTopSelected = state;
}

function alertIfAnyTopIsSelected() {
    if (isAnyTopSelected) {
        zConfirm('<bean:message key="message.u.will.looz.ur.selected.tops.apply.them.be4.leave"/>', 'Warning', 0, function(button) {
            if (button == 'Yes') {
                alert(button);
            }else {
                return false;
            }
        });
        return true
    }else {
        return true;
    }
}

function updateBottomCombined(text){
    var el = $('#bottom-selected-combinedItem')[0];
    if (el != null)
        el.innerHTML = text;     
}

function onCustomizing(){
    $('#bottom-selected-block').remove();
}

function onToppingsItemClicked(sender){
    setPreselectedToppingsItemsIsChanged(true);
}

function setPreselectedSingleItemsIsChanged(value){
    preselectedSingleItemsIsChanged = value;
    if (!value)
        setPreselectedToppingsItemsIsChanged(false);
}

function setPreselectedToppingsItemsIsChanged(value){
    preselectedToppingsItemsIsChanged = value;
}

function getPreselectedItemsIsChanged(){
    return preselectedSingleItemsIsChanged || preselectedToppingsItemsIsChanged;
}

$(document).ready(function(){
    loadCustomizePage(lastSelectedCategory, '<%=request.getParameter("type")%>', '<%=request.getParameter("singleId")%>', '<%=request.getParameter("groupId")%>');

    Array.prototype.contains = function (element) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == element) {
                return true;
            }
        }
        return false;
    };

});

</script>                                                                       