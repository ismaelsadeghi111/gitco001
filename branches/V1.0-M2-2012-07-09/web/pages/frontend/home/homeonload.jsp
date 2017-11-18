<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Feb 4, 2012
  Time: 3:50:15 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
%>
<script type="text/javascript">

var lastMenuName = 'Menu';
var lastSelectedCategory;

var carousel_autoplay = 0;
var carousel_items_visible = 3;
var carousel_scroll = 1;

function reqeustCategoryAllItem(id)
{
    lastSelectedCategory = id;
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        //            data    :{operation: 'getCategoryAllItemList', catId: id, menuName: lastMenuName},
        data    :{operation: 'getCategoryAllItemList', catId: id, menuName: lastMenuName},
        success:function(res) {
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
                if (is_chrome || is_safari){
                    $('#homecarousel_mir')[0].style.marginTop = "-53px";
                }
            }
        },
        failure: function() {
            alert('FAILURE');
        }
    });
}

function gotoCustomizepage(paramName, type, id, groupId, orderNum)
{
    var myForm = document.createElement('form');
    document.body.appendChild(myForm);

    var hid_operation = document.createElement('input');
    hid_operation.setAttribute('type' ,'hidden');
    hid_operation.setAttribute('name' ,'operation');
    hid_operation.setAttribute('value' ,'goToCustomizePage');

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

    var hid_orderNumber = document.createElement('input');
    hid_orderNumber.setAttribute('type', 'hidden');
    hid_orderNumber.setAttribute('name', 'orderNumber');
    hid_orderNumber.setAttribute('value', orderNum);

    myForm.method = 'POST';
    myForm.action = '<%=context%>/frontend.do';

    myForm.appendChild(hid_operation);
    myForm.appendChild(hid_catId);
    myForm.appendChild(hid_paramName);
    myForm.appendChild(hid_type);
    myForm.appendChild(hid_groupId);
    myForm.appendChild(hid_menuName);
    myForm.appendChild(hid_orderNumber);

    myForm.submit();
}

function changeStepTitle(title)
{
    lastMenuName = title;
    document.getElementById('step_1').innerHTML = title;
    return true;
}

function getMenuTypeItems(type, id)
{
    lastSelectedCategory = id;
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'getMenuTypeItems', menuType: type},
        success:function(res) {
            $('#homecarousel').html(res);
            $('#mycarousel').jcarousel({
                auto: 0,
//                wrap: 'last',
                visible: carousel_items_visible,
                scroll: carousel_scroll
            });
        },
        failure: function() {
            alert('FAILURE');
        }
    });
}

$(document).ready(function()
{
    $('#homecarousel').html("");
    $('#homecarousel_mir').html("");    
    getMenuTypeItems('menu');

});

function resetToDefaultSelection(menuName)
{
    $.ajax({
        type    :'POST',
        url     : '<%=context%>/frontend.do',
        data    :{operation: 'resetToDefaultSelection', menuName : menuName},
        success:function(res) {
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
        failure: function() {
            alert('FAILURE');
        }
    });
}

function selectCategoryItem(itemIndex){
    $('#mycarousel').children('li').each(function(index) {
        this.style.opacity = 0.5;
        var currDiv = $(this).children('div')[0];
        currDiv.removeAttribute("style");

        if (index == itemIndex){
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
        success: function(res){
            alert(res);
        },
        failure: function() {
            alert('FAILURE');
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
            alert('FAILURE');
        }

    });
}

function invalidateCurrentSession(){
    $.ajax({
        method: 'POST',
        url: '<%=context%>/locale.do',
        data: {method: 'invalidateSession'},
        success: function(res){
            if (res){
                alert("session invalidated successfully");
            }else {
                alert("failure in invalidation");
            }
        },
        failure: function() {
            alert('FAILURE');
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
            alert('FAILURE');
        }
    });
}

function logout() {
    var currentUrl = window.location;
    window.location.href = '<%=context%>/logout.do?operation=logout&<%=Constant.LATEST_USER_URL%>=' + currentUrl;
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




</script>

