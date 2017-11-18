<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/27/14
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="dpDollar-PopUp-title">
  <span  style="font-size:18px;font-family:'ItcKabelMedium',Arial;color: #FFFFE0"><bean:message key="doubble.PizzaDollar.whatIs"/></span>
</div>
<div class="dpDollar-PopUp-body" style="font-size:18px;font-family:'ItcKabelMedium',Arial;">

       <p style="font-size:14px; color: #002144;line-height: 25px;" >
         <bean:message key="doubble.PizzaDollar.title"/>
           <br>
           <span style="font-size: 16;"><bean:message key="doubble.PizzaDollar.how.earn"/></span>
           <br>
           <bean:message key="doubble.PizzaDollar.title1"/>
       </p>
      <br>
<table id="rounded-corner" style="font-size:14px;font-family:'ItcKabelMedium',Arial;">
    <thead>
    <tr>
        <th scope="col" class="rounded-company" style="text-align: center;font-size:18px;font-family:'ItcKabelMedium',Arial;"><bean:message key="doubble.PizzaDollar.Days"/> </th>
        <th scope="col" class="rounded-q4" style="text-align: center;font-size:18px;font-family:'ItcKabelMedium',Arial;"><bean:message key="label.page.menu.doublePizzaDOLLARS"/></th>
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
        <td>2.0%</td>
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
    <span style="font-family:'ItcKabelBold'">
    <p style="font-size:14px;font-family:'ItcKabelMedium',Arial; color: #002144; line-height:25px;" >
        <bean:message key="doubble.PizzaDollar.ex"/>
        <br>
        <span style="font-size: 16;"><bean:message key="doubble.PizzaDollar.how.spend"/></span>
        <br>
        <bean:message key="doubble.PizzaDollar.after"/>
        <br>
        <bean:message key="doubble.PizzaDollar.redeemed"/>
        <br>
        <bean:message key="doubble.PizzaDollar.exchanged"/>
        <br>
        <bean:message key="doubble.PizzaDollar.Percentages"/>
    </p>
</div>
