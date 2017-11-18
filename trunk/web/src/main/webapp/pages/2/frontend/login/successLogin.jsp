<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Apr 2, 2012
  Time: 11:49:11 AM
--%>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    if (basket != null){
        int totalOfItems = basket.getNumberOfItems();
        BigDecimal totalPrice = basket.calculateTotalPrice();
    }
%>

<script type="text/javascript">
    var counter = 6;
    function countdown() {
        counter = counter-1;
        if (counter<0) {
            window.location = "<%=context%>/frontend.do";
        }
        else {
            document.getElementById("countdown").innerHTML=counter;
            window.setTimeout("countdown()", 1000);
        }
    }
</script>
<%--<div id="MiddleBlock" class="nav-line">--%>
<body onload="countdown()">
<div class="LeftMiddleWrapper">
        <div id="columns2">
            <!-- Center -->
            <div id="center_column" class="center_column">
                <div id="center_column_inner2">
                    <h5>
                        <c:if test='<%=session.getAttribute(Constant.REGISTER_OR_LOGIN).equals("register")%>'>
                            <bean:message key="label.page.title.register"/>
                        </c:if>
                        <c:if test='<%=session.getAttribute(Constant.REGISTER_OR_LOGIN).equals("login")%>'>
                            <bean:message key="label.page.title.login"/>
                        </c:if>
                    </h5>
                    <%--show messages--%>
                    <img src="<%=context%>/img/success.png" style="float:left;" alt=""><br>
                    <h4 id="success-message"><bean:message key="message.registration.is.successful"/></h4>
                    <br class="clear_float"/>
                    <ul id="success_links">
                        <li><a href="<%=context%>/frontend.do" title="Home"><img src="<%=context%>/img/icon/home.png"
                                                                                 alt="Home" class="icon"
                                                                                 style="margin-right:3px;"></a><a
                                href="<%=context%>/frontend.do" title="Home"><bean:message
                                key="label.page.menu.home"/></a></li>
                        <li><a href="javascript: logout();" title="Home"><img src="<%=context%>/img/icon/return.png"
                                                                              alt="Home" class="icon"
                                                                              style="margin-right:3px;"></a><a
                                href="javascript: logout();" title="Home"><bean:message
                                key="label.page.title.logout"/></a></li>
                    </ul>

                    <br class="clear">

                    <div style="display:inline-block; float:right; margin-top:-100px; margin-right:65px;">
                        <h1 id="redirect_msg" align="center" style="margin-top:50px; display:inline-block;">
                <span style="color:#000000; font-size:17px;">
                    <bean:message key="message.you.will.be.redirected.to.homepage.after"/>
                </span>
                            <br/>
                <span id="countdown" style="color:#000000; font-size:50px;">
                    6
                </span>
                            <br/>
                <span style="color:#000000; font-size:17px;">
                    <bean:message key="message.seconds"/>
                </span>

                        </h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%--</div>--%>

</body>