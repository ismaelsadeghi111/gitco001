<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ page import="com.sefryek.doublepizza.model.OrderHistory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sefryek.doublepizza.model.OrderDetailHistory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 1/22/14
  Time: 1:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String context = request.getContextPath();
    List<OrderHistory> orderHistories = (List<OrderHistory>) request.getSession().getAttribute("orderHistories");
    List<OrderDetailHistory> orderDetailHistories = null;
    StringBuffer orderCaption =null;
%>
<div id="MiddleBlockLeft">
    <h1><bean:message key="order.history.title"/></h1>
    <div class="MiddleWrapper order-history-box">
        <table style="padding-top:10px;" class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="33" class="font4" style="background:#2e2d2e; color:#fff;">
                <td width="54%" style="padding-left:5px;"><bean:message key="order.history.Order"/></td>
                <td width="18%"><bean:message key="order.history.Order.Date"/></td>
                <td width="8%"><bean:message key="order.history.Price"/></td>
                <td width="11%"><bean:message key="order.history.Status"/></td>
                <td width="11%"><bean:message key="order.history.Details"/></td>
            </tr>
            <%
               if(orderHistories != null && orderHistories.size() > 0){
                for(OrderHistory orderHistory : orderHistories){
            %>
            <tr class="border">
                <td style="padding-left:5px;" height="40">
                    <%
                        orderDetailHistories = orderHistory.getOrderDetailHistories();
                        if(orderDetailHistories != null && orderDetailHistories.size()>0 ){
                            orderCaption = new StringBuffer();
                            for(OrderDetailHistory orderDetailHistory : orderDetailHistories){
                                if (orderDetailHistory.getItemName() != null && !orderDetailHistory.getItemName().isEmpty()){
                                orderCaption.append(orderDetailHistory.getItemName()).append(", ");
                                }
                                if (orderDetailHistory.getItemName2() != null && !orderDetailHistory.getItemName2().isEmpty()){
                                    orderCaption.append(orderDetailHistory.getItemName2());
                                }
                            }
                        }
                    %>
                    <%=orderCaption.toString()%>
                </td>
                <td class="orange_color"><%=orderHistory.getTodaydate().toString()%></td>
                <td class="red"><%=orderHistory.getTotal()%></td>
                <td><%=orderHistory.getDeliveryType().name()%></td>
                <td>
                    <a href="javascript: void(0);" onclick="getOrderHistoryDetails('<%=orderHistory.getDocnumber()%>');" title="Details">
                        <img width="22px" height="22px" src="<%=context%>/img/icon/info-red-255-255.png">
                    </a>
                    <%--<a href="javascript: void(0);" onclick="">
                        <img width="26px" height="26px" src="<%=context%>/img/icon/basket-icon.jpg">
                    </a>--%>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <%
                }
              } else{
            %>
            <tr class="border">
                <td colspan="5" style="padding:15px 5px 15px 10px"><bean:message key="order.history.EmptyOrder"/></td>

            </tr>
            <%
                }
            %>
        </table>
 <%--       <div class="paging-box">
            <ul>
                <li><a href="#">First</a></li>
                <li><a href="#">Previous</a></li>
                <li><a href="#" class="active">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">...</a></li>
                <li><a href="#">22</a></li>
                <li><a href="#">Next</a></li>
                <li><a href="#">Last</a></li>
            </ul>
        </div>--%>
    </div>
</div>
<%--=========================================================================================================================--%>
<div id="orderHisPopup" style="display: none;">
 </div>

