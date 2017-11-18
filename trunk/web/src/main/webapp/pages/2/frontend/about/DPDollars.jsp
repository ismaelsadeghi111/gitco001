<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: Mostafa Jamshid
  Date: 4/13/14

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>

    <title><bean:message key="doubble.PizzaDollar.title"/></title>
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

<h1 class="cusfood"  style="padding: 15px;"><bean:message key="doubble.PizzaDollar.whatIs"/></h1>
<div class="LeftMiddleWrapper">
    <p style="font-size:18px;font-family:'ItcKabelMedium',Arial; color: #002144;line-height: 35px;" >

    <%--<p style="font-size: 16px;font-family:'ItcKabelMedium',Arial; color: #002144;line-height: 35px;" >--%>
        <bean:message key="doubble.PizzaDollar.title"/>
        <br>
        <span style="font-size:16px;font-family:'ItcKabelMedium',Arial;"><bean:message key="doubble.PizzaDollar.how.earn"/></span>
        <br>
        <bean:message key="doubble.PizzaDollar.title1"/>
    </p>
    <br>
    <table id="rounded-corner" style="font-family:'ItcKabelMedium',Arial;width:100%; margin-left:auto;font-size: 16px;">
        <thead>
        <tr>
            <th scope="col"  style="font-size: 18px;text-align: center;" class="rounded-company"><bean:message key="doubble.PizzaDollar.Days"/> </th>
            <th scope="col" style="font-size: 18px;text-align: center;" class="rounded-q4"><span><bean:message key="label.page.menu.doublePizzaDOLLARS"/> </span></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Monday"/></td>
            <td>4.0%</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Tuesday"/></td>
            <td>4.0%</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Wednesday"/></td>
            <td>2.0%</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Thursday"/></td>
            <td>2.0 %</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Friday"/></td>
            <td>2.0%</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Saturday"/></td>
            <td>2.0%</td>
        </tr>
        <tr>
            <td><bean:message key="doubble.PizzaDollar.Sunday"/></td>
            <td>2.0%</td>
        </tr>
        </tbody>
    </table>
    <br>
    <p style="font-size:14px;font-family:'ItcKabelMedium',Arial; color: #002144;line-height: 35px;" >
        <bean:message key="doubble.PizzaDollar.ex"/>
        <br>
        <span style="font-size: 16;font-family:'ItcKabelMedium',Arial;"><bean:message key="doubble.PizzaDollar.how.spend"/></span>
        <br>
        <bean:message key="doubble.PizzaDollar.after"/>
        <br>
        <bean:message key="doubble.PizzaDollar.redeemed"/>
        <br>
        <bean:message key="doubble.PizzaDollar.exchanged"/>
        <br>
        <bean:message key="doubble.PizzaDollar.Percentages"/>
    </p>
   <br>
</div>

</body>
</html>
