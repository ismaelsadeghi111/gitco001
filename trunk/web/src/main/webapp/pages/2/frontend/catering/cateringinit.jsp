<%@ page import="com.sefryek.doublepizza.model.CateringOrderDetail" %>
<%@ page import="java.util.List" %>
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
    List<CateringOrderDetail> cateringOrderDetails = (List<CateringOrderDetail>) request.getAttribute("cateringOrderDetails");
    int cateringOrderDetailsSize = (Integer) request.getAttribute("cateringOrderDetailsSize");

%>
<link rel="stylesheet" href="<%=context%>/css/zebra_dialog.css" type="text/css">
<script type="text/javascript" src="<%=context%>/js/jquery/zebra_dialog.js"></script>
<script>
    var isAdded = 0;
</script>

<script type="text/javascript" language="JavaScript">
    function addCateringItem(title, id, index) {
        isAdded = isAdded + 1;
//        $("#nextbtn").css("display", "block");
//         alert('isAdded:'+isAdded);
        var quantity = 0;
        quantity = $('#cart_quantity_button' + index).find('input[name=quantity_input]')[0].value;
        $.ajax({
            type: 'POST',
            url: '<%=context%>/catering.do',
            data: {operation: 'addCateringItem', cateringId: id, index: index, quantity: quantity},
            success: function (res) {
                new $.Zebra_Dialog('<span style="font-size:18px;"><bean:message key="button.add"/> ' + quantity + ' ' + title + ' <bean:message key="catering.items.successfully"/></span>'
                        , {
                            'buttons':  false,
                            'width':500,
                            'type':'confirmation',
                            'modal': false,
                            'position':['center', 'top + 20'] ,
                            'auto_close': 2000});
            },
            failure: function () {
                alert('FAILURE');
            }
        });
    }

    function changeQuantity(sender, diff) {

        var quantityEl = $($(sender).parent()).children('input')[0];

        if ((parseInt(quantityEl.value) + diff ) < 1) {
            quantityEl.value = 1;

        } else if ((parseInt(quantityEl.value) + diff ) > 50) {
            quantityEl.value = 50;
        }
        else {
            quantityEl.value = (parseInt(quantityEl.value) + diff );
        }

    }

    function getCateringItemsOrder() {
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);
        myForm.method = 'POST';
        myForm.action = '<%=context%>/catering.do';
        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'goToCaternigOrder');
        myForm.appendChild(operation);
        myForm.submit();
    }

    function getCateringContactInfo() {

        var myForm = document.createElement('form');
        document.body.appendChild(myForm);
        myForm.method = 'GET';
        myForm.action = '<%=context%>/catering.do';
        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'goToCaternigContactInfo');
        myForm.appendChild(operation);
        myForm.submit();
    }

    function getContinueShopping() {
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);
        myForm.method = 'POST';
        myForm.action = '<%=context%>/catering.do';
        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'goToCountinueShopping');
        myForm.appendChild(operation);
        myForm.submit();
    }


</script>