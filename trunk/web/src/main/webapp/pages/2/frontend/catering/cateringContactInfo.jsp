<%--
  Created by IntelliJ IDEA.
  User: Mostafa Jamshid
  Date: 01/25/2014
  Time: 16:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.CateringOrderDetail" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.common.MessageUtil" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.ToppingCategoryAlt" %>
<%
    String context = request.getContextPath();
    List<CateringOrderDetail> cateringOrderDetails = (List<CateringOrderDetail>) request.getSession().getAttribute("cateringOrderDetails");
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_CATERING_PATH);
%>
<script type="text/javascript" src="<%=context%>/js/jquery.datepick.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datepick.css">

<div id="container">
<!-- Middle Block Div Start Here -->
<span class="cusfood"><bean:message key="catering.lable.orderSummery"/> </span>

<div id="MiddleBlock" class="nav-line">

<div id="MiddleBlockLeft">
<logic:messagesPresent message="false">
    <div class="error" name="errorBlock">
        <ol>
            <bean:message key="label.registration.errors"/> :
            <html:messages id="aMsg" message="false">
                <logic:present name="aMsg">
                    <li><bean:write name="aMsg" filter="false"/></li>
                </logic:present>
            </html:messages>
        </ol>
    </div>
</logic:messagesPresent>
<h1></h1>
<div class="MiddleWrapper" id="MiddleWrapper">
    <%
        if(cateringOrderDetails != null && cateringOrderDetails.size()>0){
            for(CateringOrderDetail cateringOrderDetail : cateringOrderDetails){
                if(cateringOrderDetail.getCatering() != null){
    %>
    <div class="catering-box border" id="<%=cateringOrderDetail.getId()%>">
        <div class="send-order-image-box">
            <img 	height="153px" width="167px" src="<%=context%><%=middlePath%><%=cateringOrderDetail.getCatering().getImageURL()%>" alt="Double pizza" /></div>
        <div class="send-order-des">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="80%"><h2><%=cateringOrderDetail.getCatering().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></h2></td>
                    <td width="20%">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <p class="font">
                            <%=cateringOrderDetail.getCatering().getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                        </p>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><span class="font4" ><bean:message key="lable.quantity"/>: <span class="orange_color"><%=cateringOrderDetail.getQuantity()%></span></span></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td> <a style="float: left;" href="javascript:void(0);" class="cart_quantity_delete"
                            rel="nofollow"
                            title="Delete"
                            onclick="removeDiv('<%=cateringOrderDetail.getId()%>');removeCateringItem('<%=cateringOrderDetail.getCatering().getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>','<%=cateringOrderDetail.getId()%>');"
                            >
                        <img src="<%=context%>/images/close.png" alt="Close"/>
                    </a>   <span style="font-size:9;padding-left:3px;color: #b06969;"><bean:message key="catering.removeThis"/></span>
                    </td>
                    <td> &nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
    <%
                }
            }
        }
        else
                {
                    %>
    <div id="empty">
        <img src="<%=context%>/img/messages/info-icon-50px.png">
        <span class="info-message"><bean:message key="fullbasket.empty"/></span>
    </div>
    <%
                }
    %>
    <div class="Clear"></div>
</div>
<br />
<div class="MiddleWrapper">
    <table class="border Fullbasket-tilte font2" width="90%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td ><bean:message key="cateringContactInfoForm.information"/></td>
    </table>
    <div class="">
        <form action="<%=context%>/cateringContactInformation.do?operation=save" method="post" id="cateringContactInfoForm" name="cateringContactInfoForm" class="std">
            <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="cateringContactInfoForm.firstName"/>:</td>
                    <td class="required text" width="25%">
                        <input  class="default-textbox" name="firstName"  type="text" style="width:180px;"
                        <logic:present name="cateringContactInfoForm">
                               value="${cateringContactInfoForm.firstName}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>

                    </td>
                </tr>
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>
                <tr>
                    <%--Last Name--%>
                    <td  class="font"><bean:message key="lable.registration.lastName"/>:</td>
                    <td class="required text" width="25%">
                        <input class="default-textbox"  name="lastName" type="text" style="width:180px;"
                        <logic:present name="cateringContactInfoForm">
                               value="${cateringContactInfoForm.lastName}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><bean:message key="lable.title"/>:</td>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="40%">
                                <label class="button info-title">
                                    <input type="radio" name="button" checked="checked">
                                    <span  class="outer"><span class="inner"></span></span> <bean:message
                                        key="lable.mr"/>
                                </label>
                                <label class="button info-title"style="padding-left: 26px;">
                                    <input type="radio" name="button"/>
                                    <span class="outer"><span class="inner"></span></span> <bean:message
                                        key="lable.ms"/>
                                </label>
                            </td>
                            <td width="60%">

                            </td>
                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="store.loacator.phone"/>:</td>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20%">
                                <input name="cellPhone1" style="width:54px;" type="text" size="3" maxlength="3" class="default-textbox" type="text"
                                <logic:present name="cateringContactInfoForm">
                                       value="${cateringContactInfoForm.cellPhone1}"
                                </logic:present>
                                       onkeyup="goNextElemnt(this, 'cellPhone2');">
                                &nbsp;<span>-</span>
                                <input name="cellPhone2" id="cellPhone2"style="width:54px;" type="text" size="3" maxlength="3"  class="default-textbox" type="text"
                                <logic:present name="cateringContactInfoForm">
                                       value="${cateringContactInfoForm.cellPhone2}"
                                </logic:present>
                                       onkeyup="goNextElemnt(this, 'cellPhone3');"  >
                                &nbsp;<span>-</span>
                                <input name="cellPhone3" id="cellPhone3" style="width:54px;" type="text" size="4" maxlength="4"  class="default-textbox" type="text"
                                <logic:present name="cateringContactInfoForm">
                                       value="${cateringContactInfoForm.cellPhone3}"
                                </logic:present>
                                        >
                                <sup class="required">*</sup></td>
                            <td width="46%">Ext:
                                <input name="ext" style="width:105px;" class="default-textbox" type="text"
                                <logic:present name="cateringContactInfoForm">
                                       value="${cateringContactInfoForm.ext}"
                                </logic:present>
                                        >
                            </td>
                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="cateringContactInfoForm.deliveryDate"/>:</td>
                    <td>
                        <input id="date" name="deliveryDate" style="width:191px;" class="default-textbox celander" readonly="true"  type="text"
                        <logic:present name="cateringContactInfoForm">
                               value="${cateringContactInfoForm.deliveryDate}"
                        </logic:present>
                                >
                        <sup class="required">*</sup>

                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font" valign="top"><bean:message key="store.loacator.open.address"/>:</td>
                    <td><textarea name="address" class="send-order-textarea" style="float:left;  resize: none;"
                            <logic:present name="cateringContactInfoForm">
                                value="${cateringContactInfoForm.address}"
                            </logic:present>
                            ></textarea>
                        <sup style="float:left; margin:-3px 0 0 4px;" class="required">*</sup></td>
                </tr>
                <tr>
                    <td class="font" valign="top"><bean:message key="cateringContactInfoForm.note"/>:</td>
                    <td><textarea name="customerNote" class="send-order-textarea" style="float:left;  resize: none;"
                            <logic:present name="cateringContactInfoForm">
                                value="${cateringContactInfoForm.customerNote}"
                            </logic:present>
                            ></textarea>
                        <sup style="float:left; margin:-3px 0 0 4px;" ></sup></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td></td>
                    <td width="100%">
                        <a  href="javascript: void (0);" class="btn-second " onclick="getContinueShopping();"><bean:message key="cateringContactInfoForm.continueOrdering"/></a>
                        <a  href="javascript: void(0);"   class="btn-first " onclick="document.getElementById('cateringContactInfoForm').submit();" > <bean:message key="cateringContactInfoForm.sendOrder"/></a>
                    </td>
                </tr>

            </table>
        </form>
    </div>
    <div class="Clear"></div>
</div>
</div>

</div>
<textarea id="input"  style="display: none;"></textarea>
</div>
<script type="text/javascript">
    function removeDiv(id){
        var child = document.getElementById(id);
        var parent = document.getElementById('MiddleWrapper');
        parent.removeChild(child);
    }
    $(document).ready(function () {
        $(function() {
            $( "#date" ).datepicker({
                dateFormat : 'yy-mm-dd',
                changeMonth : true,
                yearRange: 'c+nn:+2',
                changeYear : true
            });
        });

    });

    function getCateringItemsOrder() {
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);
        myForm.method = 'POST';
        myForm.action = '<%=context%>/catering.do';
        var operation = document.createElement('input');
        operation.setAttribute('type','hidden');
        operation.setAttribute('name','operation');
        operation.setAttribute('value','goToCaternigOrder');
        myForm.appendChild(operation);
        myForm.submit();
    }

    /*    function showMessage() {
     tAlert( 'Your order has been sent successfully', '', 'success', 4000);
     }

     $("#send").click(function()  {
     showMessage();
     });*/
    function goNextElemnt(sender, next) {
        var len = sender.value.length;
        var maxLen = sender.maxLength;

        if (len >= maxLen)
            $('#' + next).focus();
    }

    (function (global) {
        document.getElementById("send").addEventListener("click", function () {
            document.getElementById("input").value=isSend;
            global.localStorage.setItem("mySharedData", document.getElementById("input").value);
        }, false);
    }(window));

    function changeisSend(){
        isSend=1;
    }
    function removeCateringItem(title,id){
        $.ajax({
            type: 'POST',
            url: '<%=context%>/catering.do',
            data: {operation: 'removeCateringItem', cateringOrderDetailId:id},
            success: function (res) {
                $.Zebra_Dialog('<span style="font-size:18px;">Remove' + title + ' <bean:message key="catering.items.successfully"/></span>'
                        , {
                            'buttons':  false,
                            'width':500,
                            'type':'warning',
                            'modal': false,
                            'position':['center', 'top + 20'] ,
                            'auto_close': 4000});
                <%
                if(cateringOrderDetails!=null){
                 String str=String.valueOf(cateringOrderDetails.size());
                 if(cateringOrderDetails.size()==1){
                %>
                getCateringItemsOrder();
                <%
               }
               }%>
            },
            failure: function () {
                alert('FAILURE');
            }
        });
    }

</script>
