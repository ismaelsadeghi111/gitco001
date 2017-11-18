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
    Category curCategry = (Category) session.getAttribute(Constant.LAST_CATEGORY);
    Integer  customizingMod = (Integer) session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM);
    customizingMod = customizingMod == null ? 0 : customizingMod;
    String menuType = (String) session.getAttribute(Constant.MENU_TYPE);
    FrontendAction.ClassTypeEnum lastSelectedItemType = ((FrontendAction.ClassTypeEnum) session.getAttribute(Constant.LAST_SELECTED_ITEM_TYPE));
    String price = (String) request.getAttribute("price");
    pageContext.setAttribute("menuType", menuType);
    pageContext.setAttribute("loadOtherOptions", "1");
//    String itemTypeStr = ((FrontendAction.ClassTypeEnum) request.getAttribute("itemType")).name().toString();
%>
<bean:define id="cus_mod" value="<%=((Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)).toString()%>"/>

<script type="text/javascript" src="<%=context%>/js/jquery.jcarousel.pack.js"></script>

<script type="text/javascript" src="<%=context%>/js/custom-form-elements.js"></script>
<link href="<%=context%>/css/custom-form-elements.css" rel="stylesheet" type="text/css"/>

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
//var currentCaption = 'combinedType0';
var currentCaption = 'combinedType_0';

var lastSelectedCategory = <%= request.getParameter("catId") %>;
var lastSelectedCarouselItem = null;
var lastSelectedSingleItem = null;
var lastSelectedCombinedTypeItem = null;
var lastSelectedCombinedTypeIndex = 0;
var lastSelectedCombinedIndex=0;

var lastSelectedCombinedItemProdNum = 'null';
var lastSelectedCombinedItemGroupId = 'null';
var lastSelectedCombinedItemElId = 'combinedItem_' + <%=request.getParameter("orderNumber")%>;

var firstCaptionClickCounter = 0;

var lastSelectedItemType = null;
<%--var lastSelectedItemType = <%=lastSelectedItemType%>;--%>
var lastSelectedSIngleItemId = null;
var lastSelectedSIngleItemGroupId = null;
var lastSelectedSingleBasketItem = null;

var lastSelectedBasketSingle = null;
var lastSelectedCaption = null;

var customizedBasketItemId = <%=request.getAttribute("customizedBasketItemId")%>;

var captionArraySize = null;
var gotoCustomizePage = true;


var lastSelectedCaptionElementId = 'defaultSingle0';
var toppingSelectedState = 'false';
var customizing = <%=customizingMod%>;
var isInCustomizingMode = false;

var preselectedSingleItemsIsChanged = false;
var preselectedToppingsItemsIsChanged = false;

var reloadData = 0
var defaultToppingsCount =0;
var selectedToppingsCount = 0;
var lastSelectedPrice = '$00.00';

var typeLast='none';

function setTypeLast(type){
    typeLast = type;
}
function getTypeLast(){
    return typeLast;
}


function setDefaultToppingsCount(tcount){
    defaultToppingsCount = tcount
}

function getDefaultToppingsCount(tcount){
    return defaultToppingsCount;
}
function setLastSelectedPrice(val){
    if(val=='$null'){
        var priceLabels = $('[id^="down_price"]');
        $.each(priceLabels, function () {
            this.style.display='none';
        });
    }


    lastSelectedPrice = val;

    var priceLabels = $('[id^="down_price"]');
//    var newPrice=val.replace('$','');
//    var oldPriceX= document.getElementById("down_price");
//    var oldPriceY= oldPriceX.innerHTML;
//    var oldPrice= oldPriceY.replace('$','');
    <%--oldPrice= oldPrice.replace('<bean:message key="label.price"/>','');--%>
//    oldPrice= oldPrice.replace('null','');
//    oldPrice= oldPrice.trim();
    if(val!='$0.00'){
    $.each(priceLabels, function () {
        this.innerHTML = "<bean:message key="label.price"/> " + val;
    });
    }
}

function getLastSelectedPrice(val){
    lastSelectedPrice = val;
}

//function getDefaultTopping(element){
//    if (element != 0){
//
//        defaultToppingsCount +1;
//    }
//}


function setSelectedToppingsCount(val){
    selectedToppingsCount = val;
}

function getSelectedToppingsCount(){
    return selectedToppingsCount;
}

function setCustomizedBasketItemId(id) {
    customizedBasketItemId = id;
}

function getCustomizedBasketItemId() {
    return customizedBasketItemId;
}


function setCurrentCaptionStatusTrue() {
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
    for (var i = 0; i < captionArray.length; i++) {
        captionArray[i] = true;
    }
}
function resetCaptionArray() {
    for (var i = 0; i < captionArray.length; i++) {
        captionArray[i] = false;
    }

}


function gotoLoginPage(isFromCheckout) {

    var myForm = document.createElement('form');
    myForm.setAttribute('method', 'post');
    myForm.setAttribute('action', '<%=context%>/frontend.do');
    document.body.appendChild(myForm);

    var operation = document.createElement('input');
    operation.setAttribute('type', 'hidden');
    operation.setAttribute('name', 'operation');
    operation.setAttribute('value', 'goToLoginPage');

    var latestUrl = document.createElement('input');
    latestUrl.setAttribute('type', 'hidden');
    latestUrl.setAttribute('name', '<%=Constant.LOGIN_OR_REGIISTER_SOURCE%>');
    latestUrl.setAttribute('value', isFromCheckout);

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

function setIdAndGroupId(id, groupId) {
    lastSelectedSIngleItemId = id;
    lastSelectedSIngleItemGroupId = groupId;
}

function setLastCombinedProdNumAndGoupId(prodNum, groupId) {
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


//$('#categoryItems').html("");
//$('#center_column_inner_mir').html("");
//$('#center_column_inner_mir').html("");
//$('#combinedTypesMenu').html("");
//$('#basketList').html("");


function getToppingsAsString() {
    var topHid_NX_Array = $('[id^="top_hid_"]');
    var topHid_X_Array = $('[id^="extHid_"]');
    var result = "";
    var resultX = "";
    $.each(topHid_X_Array, function () {
        if (this.value != '' && this.value != null && this.value != undefined) {
            resultX += this.value + '#';
        }
    });


    var resultNX = "";
    $.each(topHid_NX_Array, function () {
        if (this.value != '' && this.value != null && this.value != undefined) {
            resultNX += this.value + '#';
        }
    });
    result = resultNX + '*' + resultX;
    return result;
}

function getBasketItems() {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: "operation=getBasketItems",
        success: function (res) {
            if (res != "") {
//                $('#footer-cart-block').append('<div id="basketList" class="footer_popup" style="display: none;"></div>').before('<div class="footer-cart">');
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').html(res);
                } else {
                    $('#basketList').addClass("footer_popup");
                    $('#basketList').html(res);
                }

                var elementName = '<%=request.getParameter("itemToClick")%>';
                if (elementName != null && elementName != "null" && gotoCustomizePage) {
                    $('#' + elementName).click();
                    gotoCustomizePage = false;
                }
            }  else if(res == null || res == "" || res == 'undefined'){
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').removeClass("footer_popup");
                }

            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function addItemToBasket(basketItemType, catId, groupId, productNo, menuSingleId) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'addItemToBasket', basketItemType: basketItemType, catId: catId, groupId: groupId, productNo: productNo, menuSingleId: menuSingleId},
        success: function (res) {
            if (res != "") {
//                $('#footer-cart-block').append('<div id="basketList" class="footer_popup" style="display: none;"></div>');
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').html(res);
                } else {
                    $('#basketList').addClass("footer_popup");
                    $('#basketList').html(res);
                }
            }  else if(res == null || res == "" || res == 'undefined'){
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').removeClass("footer_popup");
                }
//                $('#basketList').html('<label style="">Your Basket is empty</label>');

            }


            window.location.reload();

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function addCombinedItemInSessionToBasket(singleCount) {
    var singleItems = "";
    var msg = "<bean:message key='message.choose.basketItem' arg0=' notSelectedItems '/>";
    for (var i = 0; i < singleCount; i++) {
        var value = "";
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null") {
            tAlert(msg.replace('notSelectedItems', notSelectedCombinedTypeToString()), '', 'error', 10000);
            return;
        }

        singleItems += value + "-";

        var el = document.getElementById("defaultSingleGroupId" + i);
        if (el != null)
            value = el.getAttribute("value");

        singleItems += value + "#";
    }

    if (singleItems == "") {
        tAlert(msg.replace('notSelectedItems', notSelectedCombinedTypeToString()), '', 'error', 10000);

        return;
    }

    if (isAnyTopSelected) {
        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'addCombinedItemFromSessionToBasket'},
            success:function(res) {
                alert('yes');
                if (res != "") {
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').html(res);
                    } else {
                        $('#basketList').addClass("footer_popup");
                        $('#basketList').html(res);
                    }
                    var el = document.getElementById("imgClearSelectedItems");
                    if (el != null)
                        el.onclick();
                    setCookie("homeAlert", "fromCustomize", 1);
                    var path = getHomeUrl();
                    location.href = path;
                }  else if(res == null || res == "" || res == 'undefined'){
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').removeClass("footer_popup");
                    }
//                                    $('#basketList').html('<label style="">Your Basket is empty</label>');

                }
            },
            failure: function() {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
        applyToppingsOnSession(true);
/*        zAlert('<bean:message key="message.u.will.looz.ur.selected.tops.apply.them.be4.adding.to.cart"/>', 'information', 400,
                function(button) {
                    var addCombinedFunc = function(){
                        $.ajax({
                            type    :'POST',
                            url     : '<%=context%>/frontend.do',
                            data    :{operation: 'addCombinedItemFromSessionToBasket'},
                            success:function(res) {
                                if (res != "") {
                                    if($('#basketList').hasClass("footer_popup")) {
                                        $('#basketList').html(res);
                                    } else {
                                        $('#basketList').addClass("footer_popup");
                                        $('#basketList').html(res);
                                    }
                                    var el = document.getElementById("imgClearSelectedItems");
                                    if (el != null)
                                        el.onclick();
                                    setCookie("homeAlert", "fromCustomize", 1);
                                    var path = getHomeUrl();
                                    location.href = path;
                                }  else if(res == null || res == "" || res == 'undefined'){
                                    if($('#basketList').hasClass("footer_popup")) {
                                        $('#basketList').removeClass("footer_popup");
                                    }
//                                    $('#basketList').html('<label style="">Your Basket is empty</label>');

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
                })*/;
    } else {

        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'addCombinedItemFromSessionToBasket'},
            success:function(res) {
                if (res != "") {
//                    $('#footer-cart-block').append('<div id="basketList" class="footer_popup" style="display: none;"></div>').before('<div class="footer-cart">');
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').html(res);
                    } else {
                        $('#basketList').addClass("footer_popup");
                        $('#basketList').html(res);
                    }

                    var el = document.getElementById("imgClearSelectedItems");
                    if (el != null)
                        el.onclick();
                    setCookie("homeAlert", "fromCustomize", 1);
                    var path = getHomeUrl();
                    location.href = path;
                }  else if(res == null || res == "" || res == 'undefined'){
                    if($('#basketList').hasClass("footer_popup")) {
                        $('#basketList').removeClass("footer_popup");
                    }
//                    $('#basketList').html('<label style="">Your Basket is empty</label>');

                }
            },
            failure: function() {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });

    }
//    loadMiniBasketData('customizationBody.jsp');
}


function addSingleItemToBasket(groupId, menuSingleId) {
    addItemToBasket("<%= FrontendAction.ClassTypeEnum.SINGLEMENUITEM.toString() %>", 0, groupId, 0, menuSingleId);
}

function addCombinedItemToBasket(groupId, productNo) {
    addItemToBasket("<%= FrontendAction.ClassTypeEnum.COMBINED.toString() %>", lastSelectedCategory, groupId, productNo, 0);
}

function getSelectedCarouselItemFromRequest() {
    return document.getElementById("<%= "frontDiv" + request.getAttribute("selectedMenuItem") %>");
}

function selectCarouselItem(elemntId) {
    var elementItem = document.getElementById(elemntId);
    /*  if (lastSelectedCarouselItem != null)
     lastSelectedCarouselItem.removeAttribute("style");
     document.getElementById(elementItem.id).setAttribute("style", " background:url(./img/carousel_active.png) 0 0 no-repeat;");
     */
    if (elementItem.id.search("combined") > 0) {
        var combinedMenuItem = $('#' + elementItem.id + ' h5 a')[0].innerHTML;
        updateBottomCombined(combinedMenuItem);
    }

    lastSelectedCarouselItem = elementItem;

    /* $('#mycarousel').children('li').each(function (index) {
     this.style.opacity = 0.5;
     });

     elementItem.parentNode.style.opacity = 1;*/
}


function selectSingleItem(elemntId) {
    var elementItem = document.getElementById(elemntId);
    var parentItem = elementItem.parentNode;

    lastSelectedSingleItem = elementItem;
    if (elemntId != null) {
        $(parentItem).find('input[type=radio]').attr('checked', true);
//        $(elementItem).attr('checked', true);
        if ($(parentItem).hasClass("button")) {
            $(parentItem).addClass("inner");
        } else {
            $(parentItem).addClass("button inner");
        }
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');
    }
}
function setAsActiveItem(item) {
    var elementItem = item;
//    var elementItem = document.getElementById(id);
    //    document.getElementById(elementItem.id).setAttribute("class", "active");
    var parntNode = elementItem.parentNode;
    $(parntNode).children().each(function () {
        $(this).removeClass("active");
    });
//    $('#'+elemntId).addClass("active");
    $(elementItem).addClass("active");
}

function selectCombinedTypeItem(elemntId, listOrder) {
    var elementItem = document.getElementById(elemntId);
/*    if (lastSelectedCombinedTypeItem != null)
        lastSelectedCombinedTypeItem.setAttribute("class", "");*/

/*
            $('#main_menu').children('ul').children('li').each(function (index) {
                this.style.opacity = 0.5;
                this.style.backgroundColor = "#fff";
                this.style.borderWidth = "2px";
                this.style.borderColor = "#E7D259";
                this.style.borderBottom = this.style.borderTop;
                this.style.height = "53px";
                this.className = "";


                $(this).children('#customizeLink').each(function (index) {
                    this.onclick = null;
                });

                $(this).children('#customizeLink').hover(
                        function () {

                            if (index == listOrder) {
        //                        var el = document.getElementById('defaultSingleId' + index);
                                var el = document.getElementById('defaultSingleId' + index);
                                if (el != null) {
                                    var value = el.getAttribute("value");
                                    if (value != null & value != "" & value != "null") {
                                        this.style.color = "lime";
                                    }
                                }
                            }
                            else {
                                this.style.color = "#756262";

                            }
                        },
                        function () {
                            this.style.color = "#756262";
                        }
                );

            });
*/

            var liEl = $('#main_menu').children('ul').children('li')[listOrder];
         /*   liEl.style.opacity = 1;
            liEl.style.backgroundColor = "#fccc04";
            liEl.style.borderWidth = "6px";
            liEl.style.borderColor = "#fccc04";
            liEl.style.borderBottom = "none";
            liEl.style.height = "57px";
            liEl.style.zIndex = 10;*/
//            liEl.className += "selectedItem-shadow";


//    var myLink = $(liEl).children('#customizeLink')[0];
    var myLink = $(liEl).children('#combinedType_'+listOrder);
    myLink.onclick = function () {
//        var el = document.getElementById('defaultSingleId' + listOrder);
        var el = document.getElementById('defaultSingleId' + listOrder);
        if (el != null) {
            var value = el.getAttribute("value");
            if (value != null & value != "" & value != "null") {
//                scrollToToppings();
            }
        }
    }


    lastSelectedCombinedTypeItem = elementItem;
    lastSelectedCombinedTypeIndex = listOrder;
}

function scrollToToppings() {
    var new_position = $('#featured-products_block_center').offset();
    $('html,body').animate({scrollTop: new_position.top}, 'slow');
}

function scrollToToppingsIfNotCombined() {
    if ($('#main_menu').length <= 0)
        scrollToToppings();
}


function changeLangFlag(currentLang) {
    if (currentLang == 'fr') {
        document.getElementById('frn_link').setAttribute('href', 'javascript: void(0);');
        document.getElementById('frn_flag').removeAttribute('class');
        document.getElementById('frn_flag').setAttribute('class', 'unselected_lang');
        document.getElementById('eng_link').setAttribute('href', '<%=request.getContextPath()%>' +
                '/locale.do?method=changeLangToEnglish&forward=forwardToCustomizePage');//forward=welcome is not a good solution
        document.getElementById('eng_flag').removeAttribute('class');
        document.getElementById('eng_flag').setAttribute('class', 'selected_lang');
        document.getElementById('menu_item_lang').setAttribute('href', '<%=request.getContextPath()%>' +
                '/locale.do?method=changeLangToEnglish&forward=forwardToCustomizePage');
    } else if ('en_US') {
        document.getElementById('frn_link').setAttribute('href', '<%=request.getContextPath()%>' +
                '/locale.do?method=changeLangToFrench&forward=forwardToCustomizePage');
        document.getElementById('frn_flag').removeAttribute('class');
        document.getElementById('frn_flag').setAttribute('class', 'selected_lang');
        document.getElementById('eng_link').setAttribute('href', 'javascript: void(0);');
        document.getElementById('eng_flag').removeAttribute('class');
        document.getElementById('eng_flag').setAttribute('class', 'unselected_lang');
        document.getElementById('menu_item_lang').setAttribute('href', '<%=request.getContextPath()%>' +
                '/locale.do?method=changeLangToFrench&forward=forwardToCustomizePage');
    }

}

function getSubCategoryItems(categoryId) {
    lastSelectedCategory = categoryId;
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getSubCategoryItems', catId: categoryId},
        success: function (res) {
            if (res != "") {
                $('#center_column_inner_mir').html(res);
                $("#center_column_inner_mir ul:has(li)").find("li:first").children('input[type=radio]').attr('checked', true);

                $('#mycarousel_mir').jcarousel({
                    auto: carousel_autoplay,
//                    wrap: 'last',
                    visible: carousel_items_visible,
                    scroll: carousel_scroll
                });
                var el = document.getElementById("defaultSingleId0");
                var el1 = document.getElementById("defaultSingleGroupId0");
                var selectedAlt = document.getElementById("singleInCmbined_0");
                $(selectedAlt).attr('checked',true);
                if (el != null) {
                    var singleId = el.getAttribute("value");
                    var groupId = el1.getAttribute("value");
                    if (singleId != null) {
                        setIdAndGroupId(singleId, groupId);
                        selectSingleItem("endDivsingle" + singleId);
                    }
                }
//                getItemToppingsView(null, null, null, 'CATEGORY');

            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getCombinedItems(productNo, groupId) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getCombinedItems', productNo: productNo, groupId: groupId, catId: lastSelectedCategory},
        success: function (res) {
            if (res != "") {
//                $('res').find('#packageList:first-child').addClass("active");;
//                $combList.find('#packageList li')[0].addClass("active");
                $('#combinedTypesMenu').html(res);
//                $('#combinedTypesMenu ul').children().index(0).addClass("active");
             /*   $('#combinePackage ul').children().each(function(){
                    $(this).removeClass("active");
                });*/
//                $('#combinePackage').find('#packageList:first-child').addClass("active");
                $("#main_menu ul:has(li)").find("li:first").addClass("active");
//                $("#combinePackage ul").scrollTop(185);
//                $("ul li").first().addClass("active");
//                $('#combinedTypesMenu ul:first-child').addClass("active");

//                        .scrollTop($('#main_menu ul')[0].addClass("active").scrollHeight).removeClass("fullpackage");

//                selectCombinedTypeItem("combinedType0", 0);
                selectCombinedTypeItem("combinedType_0", 0);
            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function setLastSelectedCombinedOnSession(productNo, groupId) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'setLastSelectedCombinedOnSession', productNo: productNo, groupId: groupId, catId: lastSelectedCategory},
        success: function (res) {

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}


function getSingleMenuItems(singleMenuId, singleMenuGroupId) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getSingleMenuItems', singleMenuId: singleMenuId, singleMenuGroupId: singleMenuGroupId, selectedCatId: lastSelectedCategory},
        success: function (res) {
            if (res != "") {
                //                    $('#main_menu').html(res);
            }

            getItemToppingsView(singleMenuId, singleMenuGroupId, null, 'SINGLEMENUITEM');

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getSingleMenuItemsForType(productNo, groupId, itemIndex) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getSingleMenuItemsForType', productNo: productNo, groupId: groupId, itemIndex: itemIndex, catId: lastSelectedCategory},
        success: function (res) {
            if (res != "") {
                $('#center_column_inner_mir').html(res);
                var el = document.getElementById("defaultSingleId" + itemIndex);
                var el1 = document.getElementById("defaultSingleGroupId" + itemIndex);
                var selectedAlt = document.getElementById("singleInCmbined_" + itemIndex);
                $(selectedAlt).attr('checked',true);
                if (el != null) {
                    var singleId = el.getAttribute("value");
                    var groupId = el1.getAttribute("value");
                    if (singleId != null) {
                        setIdAndGroupId(singleId, groupId);
                        selectSingleItem("endDivsingle" + singleId);
                    }
                }

            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function getCustomizePage(itemid) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getSingleItemDefaultToppings', menuSingleItemId: itemid},
        success: function (res) {
            alert(res);
        },
        failure: function () {
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
    } else if (choose == 'SINGLEMENUITEM') {
        exData = {
            operation: 'getSingleItemToppings',
            id: lastSelectedSIngleItemId,
            groupId: lastSelectedSIngleItemGroupId,
            catId: lastSelectedCategory,
            type: lastSelectedItemType,
            singleBasketItemId: lastSelectedBasketSingle
        };
    } else if (choose == 'SINGLEBASKET') {
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
        success: function (res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function () {
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
        success: function (res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function () {
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
        success: function (res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function () {
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
    $.each(topHid_X_Array, function () {
        if (this.value != '' && this.value != null && this.value != undefined) {
            resultX += this.value + '#';
        }
    });


    var resultNX = "";
    $.each(topHid_NX_Array, function () {
        if (this.value != '' && this.value != null && this.value != undefined) {
            resultNX += this.value + '#';
        }
    });
    result = resultNX + '*' + resultX;

    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getNewPriceByNewToppings', singleItemId: lastSelectedSIngleItemId, singleItemGroupIdStr: lastSelectedSIngleItemGroupId, toppings: result},
        success: function (res) {
//            alert('getNewPrice:'+res);
            var priceLabels = $('[id^="down_price"]');
            $.each(priceLabels, function () {
                var newPrice=res.replace('$','');
                var oldPriceX= document.getElementById("down_price");
                var oldPriceY= oldPriceX.innerHTML;
                var oldPrice= oldPriceY.replace('$','');
                oldPrice= oldPrice.replace('<bean:message key="label.price"/>','');
                oldPrice= oldPrice.replace('null','');
                oldPrice= oldPrice.trim();
//                if(((res!='$0.00')&&(newPrice>oldPrice))||(oldPrice=='')){
                    $.each(priceLabels, function () {
                        this.innerHTML = '<bean:message key="label.price"/> ' + '$' + res;
                    });
                var el = document.getElementById('down_price');
                var fl = document.getElementById('defaultSinglePrice'+lastSelectedCombinedTypeIndex);
                if (el != null) {
                    var pr= el.innerHTML;
                    fl.innerHTML  =      pr.replace('<bean:message key="label.price"/>','');
                }
                $.each(priceLabels, function () {
                    this.innerHTML = '<bean:message key="label.price"/> ' + '$' + getCombinedItemsPrice();
                });
//                }
            });
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });


}


function updateDefaultMenuSingleOnType(menuSingleName, menuSingleId, menuSingleGroupId, menuSinglePrice) {
    var el = document.getElementById("defaultSingle" + lastSelectedCombinedTypeIndex);
    if (el != null)
        el.innerHTML = menuSingleName;

    var preSingleId;
    var preGroupId;
    var preSinglePrice;

//    var el = document.getElementById("defaultSingleId" + lastSelectedCombinedTypeIndex);
    var el = document.getElementById("defaultSingleId" + lastSelectedCombinedTypeIndex);
    if (el != null) {
        preSingleId = el.getAttribute("value");
        el.setAttribute("value", menuSingleId);
    }
    var el = document.getElementById("defaultSingleGroupId" + lastSelectedCombinedTypeIndex);
    if (el != null) {
        preGroupId = el.getAttribute("value");
        el.setAttribute("value", menuSingleGroupId);
    }
    var el = document.getElementById("defaultSinglePrice" + lastSelectedCombinedTypeIndex);
    if (el != null) {
        el.innerHTML = menuSinglePrice;
    }
    if (!preselectedSingleItemsIsChanged)
        setPreselectedSingleItemsIsChanged((preSingleId != menuSingleId || preGroupId != menuSingleGroupId));

    var priceLabels = $('[id^="down_price"]');
    $.each(priceLabels, function () {
        var fl = document.getElementById("defaultSingle" + lastSelectedCombinedTypeIndex);
        if (fl != null) {
            this.innerHTML = '<bean:message key="label.price"/> ' + '$' + getCombinedItemsPrice();
        }
        else {
            var priceLabels = $('[id^="down_price"]');
            $.each(priceLabels, function () {
                this.style.display='block';
            });
            this.innerHTML = '<bean:message key="label.price"/>'+' ' + menuSinglePrice;
        }
    });
//                }
}
function addSelectedCombinedToBasket(productNo, groupId, singleCount) {

    var singleItems = "";
    for (var i = 0; i < singleCount; i++) {
        var value = "";
//        var el = document.getElementById("defaultSingleId" + i);
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null") {
            tAlert("<bean:message key='message.choose.basketItem'/>", '', 'error', 10000);
            return;
        }

        singleItems += value + "-";

        var el = document.getElementById("defaultSingleGroupId" + i);
        if (el != null)
            value = el.getAttribute("value");

        singleItems += value + "#";
    }

    if (singleItems == "") {
        tAlert("<bean:message key='message.choose.basketItem'/>", '', 'error', 10000);
        return;
    }

    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'addBasketCombinedItemToBasket', groupId: groupId, productNo: productNo, menuSingleItems: singleItems},
        success: function (res) {
            if (res != "") {
//                $('#footer-cart-block').append('<div id="basketList" class="footer_popup" style="display: none;"></div>').before('<div class="footer-cart">');
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').html(res);
                } else {
                    $('#basketList').addClass("footer_popup");
                    $('#basketList').html(res);
                }

            } else if(res == null || res == "" || res == 'undefined'){
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').removeClass("footer_popup");
                }
                $('#basketList').html('<label style="">Your Basket is empty</label>');
            }

            window.location.reload();

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

}

function setToNoSelectedItem(singleCount, noSelectedItemCaption) {
    for (var j = 0; j < singleCount; j++) {
        setToNoSelectedTypeItem(j, noSelectedItemCaption);
    }
}

function setToNoSelectedTypeItem(itemIndex, noSelectedItemCaption) {
    var el = document.getElementById("defaultSingle" + itemIndex);
    if (el != null)
        el.innerHTML = noSelectedItemCaption;

//    var el = document.getElementById("defaultSingleId" + itemIndex);
    var el = document.getElementById("defaultSingleId" + itemIndex);
    if (el != null)
        el.setAttribute("value", "");

    var el = document.getElementById("defaultSingleGroupId" + itemIndex);
    if (el != null)
        el.setAttribute("value", "");
}


function resetCombinedAndSingleMenu() {
//    $('#center_column_inner_mir').html("");
//    $('#center_column_inner_mir2').html("");
//    $('#combinedTypesMenu').html("");
}

function combinedTypeItemOnClick(itemIndex) {
    if ($('.ZebraDialog').length >= 1)
        return;
//    var item = document.getElementById("combinedType" + itemIndex);
    var item = document.getElementById("combinedType_" + itemIndex);
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
        data: { operation: 'applyToppingsOnBasketCombinedItemInSession',
            basketSingleItemId: lastSelectedBasketSingle,
            singleId: lastSelectedSIngleItemId,
            singleGroupId: lastSelectedSIngleItemGroupId,
            toppings: toppings,
            basketItemIdentifier: customizedBasketItemId

        },
        success: function (res) {
            if (onSuccess != null) {
                onSuccess();
            }
            else {
                if (customizing != 0)
                    getBasketItems();
//                window.scrollTo(0, 0);
//                tAlert(res, '', 'success', 5000);
            }
        },
        failure: function () {
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
        success: function () {
            getCombinedItems(productNo, groupId);
            getItemToppingsView(groupId, productNo, null, null);
            if (basketItemId != null)
                getBasketItems();
        },
        failure: function () {
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
        success: function (res) {
            alert(res);
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function showSessionItemNums() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showSession'},
        success: function (res) {
            alert(res);
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function showBasketSingleInCombinedInSession() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'showBasketSingleInCombinedInSession'},
        success: function (res) {
            alert(res);
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}
function updateBasketItemCounts(value) {
    $('.basket-items-count').each(function (index) {
        this.innerHTML = value;
    });
}

function getPreSelectedToppings() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getPreSelectedToppings',
            basketSingleIdentifier: lastSelectedBasketSingle
        },
        success: function (res) {
            $('#featured-products_block_center').html(res);
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function updateBasketTotalPrice(value) {
    var el = document.getElementById("basketTotalPrice");
    if (el != null)
        el.innerHTML = value;

}



function checkAnyItemIsSelectedAndConfirmFromUserToContinue(itemIndex) {
    var singleCount = $('#main_menu').children('ul').children('li').length;

    if (singleCount <= 0)
        return false;

    if (lastSelectedCarouselItem != null) {
        if (lastSelectedCarouselItem.parentNode == $('#mycarousel').children('li')[itemIndex])
            return false;
    }

    var confirmFromUser = false;
    for (var i = 0; i < singleCount; i++) {
        var value = "";
//        var el = document.getElementById("defaultSingleId" + i);
        var el = document.getElementById("defaultSingleId" + i);
        if (el != null)
            value = el.getAttribute("value");

        if (value != null & value != "" & value != "null") {
            confirmFromUser = true;
            break;
        }
    }


    return confirmFromUser;

}

function allowSelectMoreToppings(toppingElId) {
//    var defaultToppingsCount = 0;
    var toppingCountselect=[];
//    toppingCountselect.length = 0;
    var maxToppingsCount = getMaxTopping();

    var el = document.getElementById(toppingElId);
    var className = el.className;
    var countable = el.getAttribute("countable");
    var parentEL = el.parentNode;

    if ($(el).attr("countable") == "true") {
        if ($(el).is(':checked')) {
            selectedToppingsCount++;

        } else {
            if (selectedToppingsCount > 0) {
                selectedToppingsCount--;
            }

        }

//        toppingCountselect = $("#toppingList li").find('input[type=checkbox]:checked').is('[value="true"]');
        toppingCountselect = $("#toppingList li").find(':checkbox:checked[countable="true"]');

        if (toppingCountselect.length <= maxToppingsCount) {

            return true;
        } else {
            $(el).attr('checked',false);
            var msg = "<bean:message key='message.error.max.select.toppings' arg0="maxValue"/>";
            new $.Zebra_Dialog('<span style="font-size:18px;">'+msg.replace("maxValue", maxToppingsCount)+'</span>'
                    , {
                        'buttons':  false,
                        'width':500,
                        'type':'error',
                        'modal': false,
                        'position':['center', 'top + 20'] ,
                        'auto_close': 5000});

            return false;
        }

    }
}

function editBasketCombined(identifier, categoryId, combinedProductNo, combinedgroupId){
    customizing = 1;

    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, productNo: combinedProductNo,
                  menuType: lastMenuType, groupId: combinedgroupId, type: "COMBINED", customizeMode: customizing},
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
    var start=new Date().getTime();
    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'getItemToppingsView',
            groupId: groupId,
            prodNum_id: prodOrId,
            basketSingleIdentifier: basketSingleIdentifier,
            customizedBasketItemId: customizedBasketItemId,
            itemType: itemType
        },
        success: function(res) {
            $("#loadingimg").hide();
//            $('#featured-products_block_center').html(res);
//            var total_features = res.html();
//           var howcooktopping = res.find('#howcooktopping').html();
            if(res != null && res != "undefined"){
                $('#featured-products_block_center').html(res);
                $('#howcookfood').html($("#featured-products_block_center #howcooktopping").html());
                $("#featured-products_block_center #howcooktopping").remove();
                var el = $('#featured-products_block_center').find('#cut-footer-detail').html();
                if(el != null && el != "undefined"){
                    $('#cust-button-table').html($("#featured-products_block_center #cut-footer-detail").html());
                    $("#cus-img-box #down_price").html($("#featured-products_block_center #cut-footer-detail").find('#down_price').html());
                }
            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}

function resetBasketItemToppings(basketSingleIdentifier) {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/toppingAction.do',
        data: {operation: 'resetBasketItemToppings',
            basketSingleIdentifier: basketSingleIdentifier
        },
        success: function (res) {
            captionArray[lastSelectedCombinedTypeIndex] = true;
            setPreselectedToppingsItemsIsChanged(false);
            getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, null, 'SINGLEMENUITEM');

        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }

    });
}


function editBasketSingle(identifier, categoryId, singleId, groupId) {
//    $('#categoryItems').html("");
//    $('#center_column_inner_mir').html("");
//    $('#center_column_inner_mir2').html("");
//    $('#combinedTypesMenu').html("");
    customizing = 2;

    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, singleId: singleId,
            groupId: groupId, type: "SINGLEMENUITEM", customizeMode: customizing},
        success: function (res) {
            lastSelectedCategory = categoryId;

            $.ajax({
                type: 'POST',
                url: '<%=context%>/frontend.do',
                data: {operation: 'getCategoryItems', catId: categoryId},
                success: function (res) {
//                    $('#categoryItems').html(res);
                    $('#categoryItems').html(res);
   /*                 $('#mycarousel').jcarousel({
                        auto: carousel_autoplay,
//                        wrap: 'last',
                        visible: carousel_items_visible,
                        scroll: carousel_scroll
                    });*/

                    lastSelectedItemType = "SINGLEMENUITEM";
                    lastSelectedSIngleItemId = singleId;
                    lastSelectedSIngleItemGroupId = groupId;

                    requestToppings(lastSelectedItemType);
                    onCustomizing();

                },
                failure: function () {
                    alert('<bean:message key="message.error.in.server"/>');
                }
            });

        },
        failure: function () {
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
                  menuType: lastMenuType, groupId: groupId, type: "CATEGORY", customizeMode: customizing},
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
    } else {
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');
    }
}

function putThisSingleInSession(id, groupId) {
//    alert(groupId+'<-->'+id);
    if (captionArray.length >= 1) {
        var identifier = lastSelectedBasketSingle;
        $.ajax({
            method: 'POST',
            url: '<%=context%>/toppingAction.do',
            data: {operation: 'setThisSingleInSession',
                id: id,
                groupId: groupId,
                identifier: lastSelectedBasketSingle},
            success: function () {
                selectSingleItem('endDivsingle' + id);
                getBasketItems();

            },
            failure: function () {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
    }
}


function loadCustomizePage(catId, type, singleId, groupId) {
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getCategoryItems', catId: catId, type: type, groupId: groupId },
        success:function(res) {
            $('#categoryItems').html(res);
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });

    getBasketItems();
    lastSelectedItemType = type;
    lastSelectedSIngleItemId = singleId;
    lastSelectedSIngleItemGroupId = groupId;

    lastSelectedCombinedItemGroupId = '<%=request.getParameter("groupId")%>';
    lastSelectedCombinedItemProdNum = '<%=request.getParameter("combinedId")%>';


    if ('<%=request.getParameter("type")%>' == 'COMBINED') {
        if (customizing == 0 ) {
            putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, null);
            getPreSelectedToppings();
        } else {
            putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, getCustomizedBasketItemId());
        }

    } else if ('<%=request.getParameter("type")%>' == 'SINGLEMENUITEM') {
        $('#combinedTypesMenu').remove();
        $('#featured-products_block_center').remove();
        $('.for_1_pizza_right').css({'width': '93%', 'vertical-align': 'middle'});
        getSubCategoryItems(catId);
//        alert('lastSelectedSIngleItemGroupId:\n'+lastSelectedSIngleItemGroupId+'lastSelectedSIngleItemId\n'+'lastSelectedBasketSingle\n'+lastSelectedBasketSingle);
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');
    } else {
        $('#combinedTypesMenu').remove();
        $('.for_1_pizza_right').css({'width': '93%', 'vertical-align': 'middle'});
        getSubCategoryItems(catId);
//        alert('lastSelectedSIngleItemGroupId:\n'+lastSelectedSIngleItemGroupId+'\nlastSelectedSIngleItemId\n'+lastSelectedSIngleItemId+'\nlastSelectedBasketSingle\n'+lastSelectedBasketSingle);

//        getItemToppingsView('0111', '0233', null, 'SINGLEMENUITEM');
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'CATEGORY');
    }

/*    putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, getCustomizedBasketItemId());
    getPreSelectedToppings();
    getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle,'<%=request.getParameter("type")%>');*/

}


function trim(s) {
    var l = 0;
    var r = s.length - 1;
    while (l < s.length && s[l] == '  ') {
        l++;
    }
    while (r > l && s[r] == ' ') {
        r -= 1;
    }
    return s.substring(l, r + 1);
}

function notSelectedCombinedTypeToString() {
    var itemCaptions = new Array();
    var result = "";
    var items = $('#main_menu').children('ul').children('li');
    var itemCount = items.length;
    items.each(function (index) {
        var value = null;
//        var el = document.getElementById("defaultSingleId" + index);
        var el = document.getElementById("defaultSingleId" + index);
        if (el != null)
            value = el.getAttribute("value");

        if (value == null || value == "" || value == "null") {
            var caption = null;
//            var el = document.getElementById("combinedType" + index);
            var el = document.getElementById("combinedType_" + index);
            if (el != null)
                caption = el.innerHTML;

            if (!itemCaptions.contains(caption)) {
                itemCaptions[itemCaptions.length] = caption;
                if (index == 0)
                    result = caption;
                else if (index + 1 == itemCount) { //==> means last item
                    if (result.length > 0)
                        result = result + " and " + caption;
                    else
                        result = caption;
                }
                else {
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
        zConfirm('<bean:message key="message.confirm.want.to.leave"/>', 'Confirm', 0, function (button) {
            if (button == 'Yes')
                changeCombinedItem(productNo, groupId, ItemOrder);
        });
    }
    else
        changeCombinedItem(productNo, groupId, ItemOrder);
}

function changeCombinedItem(productNo, groupId, ItemOrder) {
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
//    currentCaption = 'combinedType0';
    currentCaption = 'combinedType_0';
}

function resetCaption() {

    <%--<c:set value="<%=session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM)%>" var="cusState"/>--%>

//    if (isInCustomizingMode) {
    if (false) {
        $.ajax({
            type: 'POST',
            url: '<%=context%>/toppingAction.do',
            data: {operation: 'resetBasket',
                combinedGroupId: lastSelectedCombinedItemProdNum,
                combinedProdNum: lastSelectedCombinedItemGroupId},
            success: function (res) {
                if (res != "") {

                }
            },
            error: function () {
            }
        });
        getBasketItems();
    }


    getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');

    isAllCaptionsClicked = false;
    resetCombinedAndSingleMenu();
    if (!isInCustomizingMode) {
        putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, null);
    } else {
        putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, getCustomizedBasketItemId());
    }

    resetIdAndGroupId();
    selectCarouselItem('frontDivcombined' + lastSelectedCombinedItemProdNum + lastSelectedCombinedItemGroupId);
    setToppingSelected('false');
    setLastCombinedProdNumAndGoupId(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId);
    setToppingSelectedState(false);
    lastSelectedCombinedItemElId = 'combinedItem_0';
//    currentCaption = 'combinedType0';
    currentCaption = 'combinedType_0';
    setPreselectedSingleItemsIsChanged(false);
}

function zConfirm(text, title, width, onClose) {
    $.Zebra_Dialog(text, {'title': title, 'type': 'question', 'overlay_close': false, 'keyboard': true, 'width': width, 'onClose': onClose });
}

function zAlert(text, title, width, onClose) {
    $.Zebra_Dialog(text, {'title': title, 'type': 'information', 'overlay_close': false, 'keyboard': true, 'width': width, 'onClose': onClose });
}

function setToppingSelectedState(state) {
    isAnyTopSelected = state;
}

function alertIfAnyTopIsSelected() {
    if (isAnyTopSelected) {
        zConfirm('<bean:message key="message.u.will.looz.ur.selected.tops.apply.them.be4.leave"/>', 'Warning', 0, function (button) {
            if (button == 'Yes') {
                alert(button);
            } else {
                return false;
            }
        });
        return true
    } else {
        return true;
    }
}

function updateBottomCombined(text) {
    var el = $('#bottom-selected-combinedItem')[0];
    if (el != null)
        el.innerHTML = text;
}

function onCustomizing() {
    $('#bottom-selected-block').remove();
}

function onToppingsItemClicked(sender) {
    setPreselectedToppingsItemsIsChanged(true);
}

function setPreselectedSingleItemsIsChanged(value) {
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
function loadMiniBasketData(page) {
    $('#footer-cart-block').load(page, function() {
        if (reloadData != 0)
            window.clearTimeout(reloadData);
        reloadData = window.setTimeout(loadData, 10000)
    });
}

function getMenuTypeItems(type, id) {
    lastSelectedCategory = id;
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'getMenuTypeItems', menuType: type},
        success: function (res) {
            $('#homecarousel').html(res);
            $('#mycarousel').jcarousel({
                auto: 0,
//                wrap: 'last',
                visible: carousel_items_visible,
                scroll: carousel_scroll
            });
        },
        failure: function () {
            alert('FAILURE');
        }
    });
}

$(document).ready(function(){
    selectedToppingsCount = 0;
    defaultToppingsCount = 0;
    lastSelectedCategory =  '<%=request.getSession().getAttribute(Constant.CATEGORYID)%>';
    lastSelectedItemType = '<%=request.getParameter("type")%>';
    setMenuType('<%=menuType%>');

    <%--loadCustomizePage(lastSelectedCategory, '<%=request.getParameter("type")%>', '<%=request.getParameter("singleId")%>', '<%=request.getParameter("groupId")%>');--%>
    loadCustomizePage(lastSelectedCategory, lastSelectedItemType, '<%=request.getParameter("singleId")%>', '<%=request.getParameter("groupId")%>');

    Array.prototype.contains = function (element) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == element) {
                return true;
            }
        }
        return false;
    };
    $(".cart2").mouseover(function() {
        $(".footer_popup").show();
    });

    $(".footer_popup").mouseleave(function() {
        $(".footer_popup").hide();
    });
});


 function goToCheckoutPage(){
     $.ajax({
         type: 'POST',
         url: '<%=context%>/checkout.do',
         data: {operation: 'goToCheckoutPage'},
         success: function (res) {
         },
         failure: function () {
             alert('FAILURE');
         }
     });
 }

function setlastSelectedCombinedIndex(listOrder){
    lastSelectedCombinedIndex = listOrder;
}
function getlastSelectedCombinedIndex(){
    return lastSelectedCombinedIndex;
}


function getCombinedItemsPrice() {
    var sum=0; var doubleMax=0; var doubleMin=0;
    var firstPizzaPrice=0; var secondPizzaPrice=0;
    var pizzaCount=0;
    var combinedItems = [];
    var combinedPrices = [];
    var n = getlastSelectedCombinedIndex();
    for (var i = 0; i <= n; i++) {
        var el1 = document.getElementById("defaultSinglePrice" + i);
        var el2 = document.getElementById("combinedItem" + i);
        if (el1 != null) {
            var price=el1.innerHTML;
            price= price.replace('$','');
            price= price.replace('<bean:message key="label.price"/>','');
            combinedPrices.push(price.trim());
            sum=sum+parseFloat(price.trim());
        }
        if (el1 != null) {
            var item=el2.innerHTML;
            combinedItems.push(item.trim());
        }
    }
    for (var i = 0; i <= n; i++) {
        if((combinedItems[i]=='Pizza')|| (combinedItems[i]=='First Pizza')|| (combinedItems[i]=='Second Pizza')|| (combinedItems[i]=='Première Pizza')|| (combinedItems[i]=='Deuxième Pizza') ){
            pizzaCount++;
            if(i==0)firstPizzaPrice=combinedPrices[i];
            if(i==1) secondPizzaPrice=combinedPrices[i];
        }
    }
    doubleMin = (firstPizzaPrice < secondPizzaPrice)?firstPizzaPrice:secondPizzaPrice;
    doubleMax = (firstPizzaPrice > secondPizzaPrice)?firstPizzaPrice:secondPizzaPrice;

    if(pizzaCount==2){
        sum=sum-doubleMin;
    }
    return sum.toFixed(2);
}

function sumcombinedPrices(combinedPrices){
    var sum=0;
    for (var i = 0; i <= combinedPrices.size; i++) {
              sum=sum+combinedPrices[i];
    }
    return sum;
}

</script>
<script type="text/javascript">

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-32736322-1']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();

</script>