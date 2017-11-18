<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Feb 4, 2012
  Time: 3:50:15 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    String menuType = request.getParameter("menuType");
    String defaultSelectedCategoryId = (String) request.getAttribute("defaultSelectedCategoryId");
%>
<script type="text/javascript">

var lastMenuName = 'Menu';
var lastSelectedCategory = <%= request.getParameter("catId") %>;

var carousel_autoplay = 0;
var carousel_items_visible = 3;
var carousel_scroll = 1;

var lastSelectedBasketSingle = null;
var lastSelectedSIngleItemId = null;
var lastSelectedSIngleItemGroupId = null;

var customizedBasketItemId = null;
var customizing = 0;

var lastEnteredStreetNo = '';
var lastEnteredPostalCode1 = '';
var lastEnteredPostalCode2 = '';

var lastEnteredStreetNoDynamic = '';
var lastEnteredPostalCode1Dynamic = '';
var lastEnteredPostalCode2Dynamic = '';

var counter = 0;
var lastSubTotalPrice = 0;
var isInCustomizingMode = false;

function setCustomizedBasketItemId(id) {
    customizedBasketItemId = id;
}

function getCustomizedBasketItemId() {
    return customizedBasketItemId;
}

function reqeustCategoryAllItem(id) {
    lastSelectedCategory = id;
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        //            data    :{operation: 'getCategoryAllItemList', catId: id, menuName: lastMenuName},
        data: {operation: 'getCategoryAllItemList', catId: id, menuName: lastMenuName},
        success: function (res) {
            if (res != "") {
                //                    $('#homecarousel_mir').addClass('homecarousel_mir_2nd');
                $('#center_column_inner_mir').html(res);
                $('#mycarousel_mir').jcarousel({
//                    wrap: 'last',
                    visible: carousel_items_visible,
                    scroll: carousel_scroll
                });
                var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
                var is_safari = navigator.userAgent.toLowerCase().indexOf('safari') > -1;
                if (is_chrome || is_safari) {
                    $('#homecarousel_mir')[0].style.marginTop = "-53px";
                }
            }
        },
        failure: function () {
            alert('FAILURE');
        }
    });
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
        alert('lastSelectedSIngleItemGroupId:\n'+lastSelectedSIngleItemGroupId+'lastSelectedSIngleItemId\n'+'lastSelectedBasketSingle\n'+lastSelectedBasketSingle);
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'SINGLEMENUITEM');
    } else {
        $('#combinedTypesMenu').remove();
        $('.for_1_pizza_right').css({'width': '93%', 'vertical-align': 'middle'});
        getSubCategoryItems(catId);
        alert('lastSelectedSIngleItemGroupId:\n'+lastSelectedSIngleItemGroupId+'\nlastSelectedSIngleItemId\n'+lastSelectedSIngleItemId+'\nlastSelectedBasketSingle\n'+lastSelectedBasketSingle);
        getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle, 'CATEGORY');
    }

    /*    putCombinedInSession(lastSelectedCombinedItemProdNum, lastSelectedCombinedItemGroupId, getCustomizedBasketItemId());
     getPreSelectedToppings();
     getItemToppingsView(lastSelectedSIngleItemGroupId, lastSelectedSIngleItemId, lastSelectedBasketSingle,'<%=request.getParameter("type")%>');*/

}

function gotoCustomizepage(paramName, type, catId, id, groupId, orderNum) {
    customizing = 0;
    lastSelectedCategory = catId;
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    var hid_operation = document.createElement('input');
    hid_operation.setAttribute('type', 'hidden');
    hid_operation.setAttribute('name', 'operation');
    hid_operation.setAttribute('value', 'goToCustomizePage');

    var hid_catId = document.createElement('input');
    hid_catId.setAttribute('type', 'hidden');
    hid_catId.setAttribute('name', 'catId');
    hid_catId.setAttribute('value', lastSelectedCategory);

    var hid_paramName = document.createElement('input');

    hid_paramName.setAttribute('name', paramName);
    hid_paramName.setAttribute('value', id);

    var hid_type = document.createElement('input');
    hid_type.setAttribute('type', 'hidden');
    hid_type.setAttribute('name', 'type');
    hid_type.setAttribute('value', type);

    var hid_groupId = document.createElement('input');
    hid_groupId.setAttribute('type', 'hidden');
    hid_groupId.setAttribute('name', 'groupId');
    hid_groupId.setAttribute('value', groupId);

    var hid_menuName = document.createElement('input');
    hid_menuName.setAttribute('type', 'hidden');
    hid_menuName.setAttribute('name', 'menuName');
    hid_menuName.setAttribute('value', lastMenuName);

    var hid_menuType = document.createElement('input');
    hid_menuType.setAttribute('type', 'hidden');
    hid_menuType.setAttribute('name', 'menuType');
    hid_menuType.setAttribute('value', getMenuType());

    var hid_orderNumber = document.createElement('input');
    hid_orderNumber.setAttribute('type', 'hidden');
    hid_orderNumber.setAttribute('name', 'orderNumber');
    hid_orderNumber.setAttribute('value', orderNum);

    var hid_customizing = document.createElement('input');
    hid_customizing.setAttribute('type', 'hidden');
    hid_customizing.setAttribute('name', 'customizing');
    hid_customizing.setAttribute('value', customizing);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/frontend.do';

    myForm.appendChild(hid_operation);
    myForm.appendChild(hid_catId);
    myForm.appendChild(hid_paramName);
    myForm.appendChild(hid_type);
    myForm.appendChild(hid_groupId);
    myForm.appendChild(hid_menuName);
    myForm.appendChild(hid_menuType);
    myForm.appendChild(hid_orderNumber);
    myForm.appendChild(hid_customizing);

    myForm.submit();
}

function changeStepTitle(title) {
    lastMenuName = title;
    document.getElementById('step_1').innerHTML = title;
    return true;
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
function getBasketItems() {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: "operation=getBasketItems",
        success: function (res) {
            if (res != "") {
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
            }
           /* else if(res == null || res == "" || res == 'undefined'){
                if($('#basketList').hasClass("footer_popup")) {
                    $('#basketList').removeClass("footer_popup");
                }
//                                    $('#basketList').html('<label style="">Your Basket is empty</label>');

            }*/
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

function addCombinedItemInSessionToBasket() {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'addCombinedItemFromSessionToBasket'},
        success: function (res) {
            $('.footer_popup').hide();
            if (res != "") {
                $('.footer_popup').html(res);
                var el = document.getElementById("imgClearSelectedItems");
                if (el != null)
                    el.onclick();
                setCookie("homeAlert", "fromCustomize", 1);
                var path = getHomeUrl();
                location.href = path;
            }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

$(document).ready(function () {
    $('#homecarousel').html("");
    $('#homecarousel_mir').html("");
//    $('#basketList').addClass("footer_popup");
    $(".cart2").mouseover(function() {
        $(".footer_popup").show();
    });

    $(".footer_popup").mouseleave(function() {
        $(".footer_popup").hide();
    });
    lastSelectedCategory =  <%=defaultSelectedCategoryId%>;
    getMenuTypeItems('menu');
    getBasketItems();
});

function resetToDefaultSelection(menuName) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'resetToDefaultSelection', menuName: menuName},
        success: function (res) {
            if (res != "") {
                //                    $('#homecarousel_mir').addClass('homecarousel_mir_2nd');
                $('#center_column_inner_mir').html(res);
                $('#mycarousel_mir').jcarousel({
//                    wrap: 'last',
                    visible: carousel_items_visible,
                    scroll: carousel_scroll
                });
            }
        },
        failure: function () {
            alert('FAILURE');
        }
    });
}

function selectCategoryItem(itemIndex) {
    $('#mycarousel').children('li').each(function (index) {
        this.style.opacity = 0.5;
        var currDiv = $(this).children('div')[0];
        currDiv.removeAttribute("style");

        if (index == itemIndex) {
            this.style.opacity = 1;
            currDiv.setAttribute("style", " background:url(./img/carousel_active_arrow.png) 0 0 no-repeat; height: 120px;; z-index: 1000; margin-left: -16px;width:220px;");

        }
    });
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
            alert('FAILURE');
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
            alert('FAILURE');
        }

    });
}

function invalidateCurrentSession() {
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'invalidateSession'},
        success: function (res) {
            if (res) {
                alert("session invalidated successfully");
            } else {
                alert("failure in invalidation");
            }
        },
        failure: function () {
            alert('FAILURE');
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
            alert('FAILURE');
        }
    });
}



function setBasketSingleItemIdentifier(id) {
    lastSelectedBasketSingle = id;
}

function setIdAndGroupId(id, groupId) {
    lastSelectedSIngleItemId = id;
    lastSelectedSIngleItemGroupId = groupId;
}

function updateBasketItemCounts(value) {
    $('.basket-items-count').each(function (index) {
        this.innerHTML = value;
    });
}

function updateBasketTotalPrice(value) {
    var el = document.getElementById("basketTotalPrice");
    if (el != null)
        el.innerHTML = value;

}

function editBasketCombined(identifier, categoryId, combinedProductNo, combinedgroupId) {
    customizing = 1;

    $.ajax({
        type: 'POST',
        url: '<%=context%>/frontend.do',
        data: {operation: 'resetMenuObjectsOnSession', identifier: identifier, catId: categoryId, productNo: combinedProductNo,
            groupId: combinedgroupId, type: "COMBINED", customizeMode: customizing},
        success: function (res) {
            lastSelectedCategory = categoryId;

            $.ajax({
                type: 'POST',
                url: '<%=context%>/frontend.do',
                data: {operation: 'getCategoryItems', catId: categoryId},
                success: function (res) {
//                    $('#categoryItems').html(res);
                    $('#categoryItems').html(res);
                    /*                    $('#mycarousel').jcarousel({
                     auto: carousel_autoplay,
                     //                        wrap: 'last',
                     visible: carousel_items_visible,
                     scroll: carousel_scroll
                     });*/
                    onCustomizing();
                    //                    setCaptionArrayTrue();
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



function setActiveNavigate(){
//    var elementItem = document.getElementById(elemntId);
//    document.getElementById(elementItem.id).setAttribute("class","");
//    document.getElementById(elementItem.id).setAttribute("class", "active");
    window.alert("test2");
}

</script>
<script>

    function getCombinedItemsForQuickMenu(productNo, groupId, category) {
        $.ajax({

            type: 'POST',
            url: '<%=context%>/frontend.do',
            data: {operation: 'findCombinedInnerCategory', productNo: productNo, groupId: groupId, catId: catId},
            success: function (res) {
                if (res != "") {
                    $('#combinedTypesQuickMenu').html(res);
                }
            },
            failure: function () {
                alert('<bean:message key="message.error.in.server"/>');
            }
        });
    }
    function autoCompleteStreet(streetNo, postalcode_1st, postalcode_2nd) {
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

        if (streetNo == "0") {
            document.getElementById('street').setAttribute('value', '');
            document.getElementById('street').value = null;

            document.getElementById('city').setAttribute('value', '');
            document.getElementById('city').value = null;
        }

        lastEnteredStreetNo = streetNo;
        lastEnteredPostalCode1 = postalcode_1st;
        lastEnteredPostalCode2 = postalcode_2nd;
    }

    //Saeid AmanZadeh
    function autoCompleteStreetDaynamic(index, streetNo, postalcode_1st, postalcode_2nd) {
        var digitRegex = /^\s*\d+\s*$/;
        var isAnyFieldEmpty = true;
        var isAnyDataChanged = false;
        if (digits != null && postalcode_1st != null && postalcode_2nd != null && digits != "" && postalcode_1st != "" && postalcode_2nd != "" && digits.search(digitRegex) != -1) {
            isAnyFieldEmpty = false;
        }
        if (lastEnteredStreetNoDynamic != digits || lastEnteredPostalCode1Dynamic != postalcode_1st || lastEnteredPostalCode2Dynamic != postalcode_2nd) {
            //alert(lastEnteredStreetNoDynamic + " " + digits);
            isAnyDataChanged = true;
        }
        if (!isAnyFieldEmpty) {
            $.ajax({
                method: 'POST',
                url: '<%=context%>/finalcheckout.do',
                data:{
                    operation: 'getStreetName',
                    postalCode: postalcode_1st + ' ' + postalcode_2nd,
                    streetNo: digits
                },
                success: function(res) {
                    var streetName = '';
                    var city = '';

                    var strArray = res.split('*');
                    streetName = strArray[0];
                    city = strArray[1];
                    if (document.getElementById('streetNo_'+index).value == digits) {
                        if (city == 'null' && streetName == 'null') {
                            document.getElementById('street_'+index).setAttribute('value', streetName);
                            document.getElementById('street_'+index).value = '';
                            document.getElementById('city_'+index).setAttribute('value', city);
                            document.getElementById('city_'+index).value = '';


                        } else if (city != 'null' && streetName == 'null') {
                            document.getElementById('street_'+index).setAttribute('value', streetName);
                            document.getElementById('street_'+index).value = '';
                            document.getElementById('city_'+index).setAttribute('value', city);
                            document.getElementById('city_'+index).value = city;

                        } else if (city == 'null' && streetName != 'null') {
                            document.getElementById('street_'+index).value = streetName;
                            document.getElementById('city_'+index).setAttribute('value', city);
                            document.getElementById('city_'+index).value = '';

                        } else {
                            $('#street_'+index).css('color', '#000000');
                            $('#city_'+index).css('color', '#000000');
                            document.getElementById('street_'+index).setAttribute('value', streetName);
                            document.getElementById('street_'+index).value = streetName;
                            document.getElementById('city_'+index).setAttribute('value', city);
                            document.getElementById('city_'+index).value = city;
                        }

                    }
                },
                failure: function() {
                    document.getElementById('street_'+index).removeAttribute('value');
                }
            });

        } else {
            if (isAnyFieldEmpty) {
                document.getElementById('street_'+index).setAttribute('value', '');
                document.getElementById('street_'+index).value = null;

                document.getElementById('city_'+index).setAttribute('value', '');
                document.getElementById('city_'+index).value = null;
            }
        }

        if (digits == "0") {
            document.getElementById('street_'+index).setAttribute('value', '');
            document.getElementById('street_'+index).value = null;

            document.getElementById('city_'+index).setAttribute('value', '');
            document.getElementById('city_'+index).value = null;
        }

        lastEnteredStreetNoDynamic = digits;
        lastEnteredPostalCode1Dynamic = postalcode_1st;
        lastEnteredPostalCode2Dynamic = postalcode_2nd;
    }


</script>
