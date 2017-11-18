<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 1/29/14
  Time: 12:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String context = request.getContextPath();
%>
<script>
    function getOrderHistoryDetails(orderId){
        $.ajax({
            method: 'POST',
            url: '<%=context%>/order.do',
            data: {operation: 'getOrderHistoryDetails', orderId : orderId},
            success: function(res) {
                $('#orderHisPopup').html(res).css({'display': 'block'});
            }
            });
    }
</script>