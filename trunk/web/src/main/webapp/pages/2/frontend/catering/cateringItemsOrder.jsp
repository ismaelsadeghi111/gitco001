<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.model.CateringOrderDetail" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%
    String context = request.getContextPath();
    List<CateringOrderDetail> cateringOrderDetails = (List<CateringOrderDetail>) request.getAttribute("cateringOrderDetails");
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_CATERING_PATH);
    String flagSave= (String) request.getAttribute("flagSave");
    if(flagSave==null)
        flagSave="";

%>
<script type="text/javascript">
    $(document).ready(function () {
        $(function() {
            var flagSave=document.getElementById("flagSaveID");
            if(flagSave.value=="True"){
                var myMessage = '<bean:message key="catering.msg.sendOrder" />';
                new $.Zebra_Dialog('<span style="font-size:20px;">'+myMessage+'</span>', {
                            'buttons':  false,
                            'width':400,
                            'type':'confirmation',
                            'modal': false,
                            'position':['center', 'top + 20'] ,
                            'auto_close': 4000});

                }else if(flagSave.value=="False"){
                var myMessageFlase = '<bean:message key="catering.msg.select" />';
                new $.Zebra_Dialog('<span style="font-size:20px;">'+myMessageFlase+'</span>', {
                    'buttons':  false,
                    'width':400,
                    'type':'error',
                    'modal': false,
                    'position':['center', 'top + 20'] ,
                    'auto_close': 4000});

            }

            flagSave.value="";
        });

    });
    function tAlert(message, title, type, autoClose){
        hideAllMessages(false);
        type = 't' + type;
        var panel = $('.' + type)[0];
        var titleEl = document.getElementById(type + "Header");
        var messageEl = document.getElementById(type + "Message");
        titleEl.innerHTML = title;
        messageEl.innerHTML = message;
        $(panel).animate({top:"0"}, 500);
        if (autoClose != 0) {
            clearTimeout(lastTimeout);
            lastTimeout = setTimeout("hideAllMessages(true)", autoClose);
        }
    }
</script>
<input type="text" style="display: none"  value="<%=flagSave%>" id="flagSaveID"/>
<h1 class="cusfood"><bean:message key="catering.page2.title"/></h1>
<div class="clear"></div>
<div id="MiddleBlockLeft">
<logic:iterate id="cateringItems" name="cateringForm" property="caterings" indexId="i">
    <bean:define id="catering" type="com.sefryek.doublepizza.model.Catering" name="cateringItems"/>
    <div class="catering-box border">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td witdh="35%">
                    <div class="image-box"><img class="img-menulist" width="180" height="149" src="<%=context%><%=middlePath%><%=catering.getImageURL()%>" alt="Double pizza" /></div>
                </td>
        <td  witdh="65%">
        <div class="catering-des">
            <table width="70%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="80%">
                        <h2><%=catering.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></h2>
                    </td>

                    <td width="20%">
                        <div id="cart_quantity_button${i}" class="recommend-counter">
                            <a href="javascript: void (0);" class="add" title="Subtract" onclick="changeQuantity(this, -1);" id="cart_quantity_down" rel="nofollow" style="opacity: 1;"><img alt="Subtract"  src="<%=context%>/images/sub.png"></a>
                            <input type="text" name="quantity_input" value="1"  maxlength="2" max="50" size="2" class="popup-text-box" readonly="true" >
                            <a href="javascript: void (0);" class="add" title="Add" onclick="changeQuantity(this, 1);" id="cart_quantity_up" ><img alt="Add" src="<%=context%>/images/add.png"></a>
                        </div>
                        <input type="hidden" value="" name="quantity"/>
                    </td>
                </tr>

                <tr>
                    <td><p>	  <%=catering.getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                    </p></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><span><bean:message key="catering.10to12"/></span></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><a  href="javascript: void(0);"  class="catering-btn" onclick="addCateringItem('<%=catering.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>','<%=catering.getId()%>',${i});"><bean:message key="button.add"/></a></td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </div>
        </td>
            </tr>
       </table>
    </div>
</logic:iterate>
<a href="javascript: void(0);" id="nextbtn" class="btn-first FloatRight" onclick="getCateringContactInfo();"><bean:message key="button.next"/> </a>
</div>
