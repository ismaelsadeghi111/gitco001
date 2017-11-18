<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.CateringOrderDetail" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%--
  Created by IntelliJ IDEA.
  User: Mostafa.Jamshid
  Date: 2014/26/01
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket)request.getSession().getAttribute(Constant.BASKET);
    List<CateringOrderDetail> cateringOrderDetails = (List<CateringOrderDetail>) request.getAttribute("cateringOrderDetails");
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_CATERING_PATH);


%>

<div id="footer-cart-block">
    <div class="footer_popup">
        <div class="DivWithScroll">
            <div class="light-arrwo"></div>
            <span class="title">Cart Products (2)</span>

            <%
                if(cateringOrderDetails != null && cateringOrderDetails.size()>0){
                    for(CateringOrderDetail cateringOrderDetail : cateringOrderDetails){
                        if(cateringOrderDetail.getCatering() != null){
            %>

            <div class="lightBox-inner">
                <div class="ImageBox">

                    <img width="100px" height="100px" src="<%=context%><%=middlePath%><%=cateringOrderDetail.getCatering().getImageURL()%>" alt="Double Pizza"/>
                </div>
                <div class="light-des">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td colspan="2" align="left" valign="top" height="25"><a
                                    href="#"><%=cateringOrderDetail.getCatering().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                            </a></td>
                            <td><strong>Number : <%=cateringOrderDetail.getQuantity()%></strong></td>
                            <td width="4%" align="left" valign="top"><a href="#"><img
                                    src="<%=context%>/images/close.png" alt="Close"/></a></td>
                        </tr>
                    </table>
                </div>
            </div>
            <%
                        }
                    }
                }
            %>

        </div>
    </div>
    <%--<div class="footer-cart"> <a href="#" class="cart2"> <strong>View Card</strong> <span>1 item(s)</span> </a>--%>
        <%--<a href="#" class="btn2" onclick="getCateringContactInfo();">Next</a> </div>--%>
</div>

<script type="text/javascript" language="JavaScript">
    $(".cart2").click(function() {
        $('.footer_popup').toggle()
    });
</script>

