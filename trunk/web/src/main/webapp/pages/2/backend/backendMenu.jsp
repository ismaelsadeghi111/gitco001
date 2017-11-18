<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.common.config.ApplicationConfig" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);

    if (basket != null) {
        int totalOfItems = basket.getNumberOfItems();
        BigDecimal totalPrice = basket.calculateTotalPrice();
    }

    ApplicationConfig applicationConfig = new ApplicationConfig();

%>

<script type="text/javascript">

    function showSubMenu(element) {
        $(".admin-left-inner-menu").css("display", "block");
    }

    function hideSubMenu(element) {
        $(".admin-left-inner-menu").css("display", "none");
    }
/*

    function setAsActiveItem(item) {
        var elementItem = item;
        alert(item);
//    var elementItem = document.getElementById(id);
        //    document.getElementById(elementItem.id).setAttribute("class", "active");
        var parntNode = elementItem.parentNode;
        $(parntNode).children().each(function () {
            $(this).removeClass("active");
        });
//    $('#'+elemntId).addClass("active");
        $(elementItem).addClass("active");
    }
*/

</script>

<div id="admin-left">
    <ul>
        <li id="fi_li" onclick="hideSubMenu(this);"><a
                href="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do">
            Double Pizza Dollar Setting </a></li>
        <li id="se_li" onclick="hideSubMenu()"><a href="<c:out value="${pageContext.request.contextPath}"/>/reload.do">
            Reload Data</a></li>

        <%if(applicationConfig.getRunMode().equals("DEV") || applicationConfig.getRunMode().equals("TEST")){%>

        <li onclick="showSubMenu(this);"><a href="javascript:void(0)">Manage Compaign</a>

            <ul class="admin-left-inner-menu" style="display: none">
                <li><a href="<c:out value="${pageContext.request.contextPath}"/>/campaign.do">Regular Compaign</a></li>
                <!-- <li><a href="<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=showPostal">Postal Compaign</a></li>
                <li><a href="<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=showBirthday">Birthday Compaign</a></li>-->
                <li><a href="<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=showMaskedEmails">Mask Emails</a></li>
            </ul>
        </li>
        <%}%>

        <li onclick="hideSubMenu(this);"><a href="<c:out value="${pageContext.request.contextPath}"/>/popular.do">Manage Popular Images</a></li>
        <li onclick="hideSubMenu(this);"><a href="<c:out value="${pageContext.request.contextPath}"/>/slide.do">Manage Slide Show</a></li>
        <li onclick="showSubMenu(this);"><a href="<c:out value="${pageContext.request.contextPath}"/>/frontend.do?operation=testServices">Test dpDevice Services</a></li>


    </ul>
</div>
