<%--
  Created by IntelliJ IDEA.
  User: Mostafa Jamshid
  Date: 12/25/13
  Time: 12:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
%>
<script type="text/javascript" language="JavaScript">


    var lastMenuName = 'Menu';

    $(document).ready(function(){
        getFoodByType('menu');
    });
function getFoodByType(type) {
    $.ajax({
        type: 'POST',
        url: '<%=context%>/quickmenu.do',
        data: {operation:'getFoodByType', foodType:type},
        success: function (res) {
            if (res != "") {
               $('#food-by-type').html(res);
                }
        },
        failure: function () {
            alert('<bean:message key="message.error.in.server"/>');
        }
    });
}

    function gotoCustomizepage(paramName, type, catId, id, groupId, orderNum) {
//        alert('paramName:'+paramName);
        var customizing = 0;
        var  lastSelectedCategory = catId;
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

        var hid_menuType = document.createElement('input');
        hid_menuType.setAttribute('type', 'hidden');
        hid_menuType.setAttribute('name', 'menuType');
        hid_menuType.setAttribute('value', lastMenuType);

        var hid_orderNumber = document.createElement('input');
        hid_orderNumber.setAttribute('type', 'hidden');
        hid_orderNumber.setAttribute('name', 'orderNumber');
        hid_orderNumber.setAttribute('value', orderNum);

        var hid_customizing = document.createElement('input');
        hid_customizing.setAttribute('type', 'hidden');
        hid_customizing.setAttribute('name', 'customizing');
        hid_customizing.setAttribute('value', customizing);


        var hid_menuName = document.createElement('input');
        hid_menuName.setAttribute('type', 'hidden');
        hid_menuName.setAttribute('name', 'menuName');
        hid_menuName.setAttribute('value', lastMenuName);

        myForm.method = 'POST';
        myForm.action = '<%=context%>/frontend.do';

        myForm.appendChild(hid_operation);
        myForm.appendChild(hid_catId);
        myForm.appendChild(hid_paramName);
        myForm.appendChild(hid_type);
        myForm.appendChild(hid_groupId);
        myForm.appendChild(hid_menuType);
        myForm.appendChild(hid_orderNumber);
        myForm.appendChild(hid_customizing);
        myForm.submit();
    }

</script>