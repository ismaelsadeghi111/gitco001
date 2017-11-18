<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.BasketSingleItem" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%--
  Created by IntelliJ IDEA.
  User: hassan.abdi
  Date: Feb 7, 2012
  Time: 12:08:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String context = request.getContextPath();
    String selectedSingleItemForType = (String)request.getAttribute("selectedSingleItemForType");
    String selectedSingleItemPrice = (String)request.getAttribute("selectedSingleItemPrice");
    String imageAddress = (String) request.getAttribute("imageAddress");
    pageContext.setAttribute("selectedSingleItemPrice",selectedSingleItemPrice);
    pageContext.setAttribute("imageAddress",imageAddress);
    Double priceNum = ((Double) request.getAttribute("price"));
    String price = priceNum == null ? selectedSingleItemPrice : priceNum.toString();
    String itemName = (String) request.getAttribute("itemName");
    itemName = itemName == null ? " " : itemName;
    Integer customizeMode = (Integer)session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM);
    customizeMode = customizeMode == null ? 0 : customizeMode;
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=context%>/js/jquery.jcarouselm.pack.js"></script>
<script type="text/javascript">
    var f;
    var indexo;
</script>
<script type="text/javascript" src="<%=context%>/js/homecarouselm.js"></script>


<%--<div id="homecarousel_mir2">--%>
<%--
    <div class="ImageBox">
        <div><img class="img-customiz" src="<%=context%><%= middlepath%>${requestScope.imageAddress}"  width="250" height="190" alt="Double pizza" /></div>
        <input type="hidden" name="imageAddress" value="${requestScope.imageAddress}" />
        <div class="foodname"><c:choose><c:when test="${not empty requestScope.categoryName}">${requestScope.categoryName}</c:when><c:otherwise><%=itemName%></c:otherwise></c:choose></div>
        <div id="down_price" class="foodname"><bean:message key="label.price"/> <c:choose><c:when test="${not empty requestScope.price}">${requestScope.price}</c:when><c:otherwise><%=price%></c:otherwise></c:choose></div>
        <a style="cursor: pointer;"><div style="padding-top: 12px;padding-bottom: 6px;"><img  src="<%=context%>/images/fb-2.png" alt="Facebook" /></div></a>

    </div>
--%>

<div  class="" >

    <script>
        var isSet=0;
    </script>
    <logic:iterate id="singleItem" name="menuSingleList" indexId="itemIndex">
        <%String singleName = StringUtil.quotedString(((MenuSingleItem) pageContext.getAttribute("singleItem")).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)));
        %>
        <script>
            var mystring = "<%=singleName%>";
            var alt=${itemIndex};

           if  (alt==0 && f!=0){
               indexo=${singleItem.id};
               setAlternative(mystring);
            }


        </script>
        <label class="button">
            <script>
                if(isSet==0){
                  var itemNo=${singleItem.id}
                  isSet=1;
                }
            </script>
            <input id="singleInCmbined_${itemIndex}" type="radio" title="xxx" name="button"
                   onclick="
                           $('#loadingimg').show();
                           f=0;
                           setAlternative('<%=singleName%>');
                           updateDefaultMenuSingleOnType('<%=singleName%>', '${singleItem.id}', '${singleItem.groupId}','<%=((MenuSingleItem)singleItem).getFormattedPrice()%>');

                           setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                           getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');
                           putThisSingleInSession('${singleItem.id}', '${singleItem.groupId}');

                           sumPrice();
                           <%--changePrice('<%=((MenuSingleItem)singleItem).getFormattedPrice()%>');--%>

                           if (captionArray[currentCaption.substr(12)]) {
                           zConfirm('<bean:message key='message.confirm.looz.your.toppings1'/><b>' + document.getElementById(currentCaption).innerHTML + '</b><bean:message key='message.confirm.looz.your.toppings2'/>','Confirm', 500,
                       <%--zConfirm('<bean:message key='message.confirm.looz.your.toppings' arg0="${singleItem.name}"/>','Confirm', 500,--%>
                           function(button) {
                           if (button == 'Yes') {

                           setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                           getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');
                           putThisSingleInSession('${singleItem.id}', '${singleItem.groupId}');
                       <%--selectSingleItem('singleInCmbined_${itemIndex}');--%>
                           updateDefaultMenuSingleOnType('<%=singleName%>', '${singleItem.id}', '${singleItem.groupId}','<%=((MenuSingleItem)singleItem).getFormattedPrice()%>');

                           captionArray[currentCaption.substr(12)] = false;
                           getBasketItems();
                       <%--selectSingleItem('singleInCmbined_${itemIndex}');--%>
                           } else {
                           return false;
                           }
                           }
                           );
                           } else {
                           setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                           putThisSingleInSession('${singleItem.id}', '${singleItem.groupId}');
                           if (document.getElementById('main_menu') == null){
                           getItemToppingsView('${singleItem.groupId}', '${singleItem.id}', null, 'SINGLEMENUITEM');
                           selectSingleItem('endDivsingle${singleItem.id}');
                           }
                           updateDefaultMenuSingleOnType('<%=singleName%>', '${singleItem.id}', '${singleItem.groupId}','<%=((MenuSingleItem)singleItem).getFormattedPrice()%>');

                           getBasketItems();
//                           scrollToToppingsIfNotCombined();

                       <%--changePrice( <%=((MenuSingleItem)singleItem).getFormattedPrice()%>);--%>
                           }

                           var el = document.getElementById('down_price');
                           var fl = document.getElementById('defaultSinglePrice'+lastSelectedCombinedTypeIndex);
                           if (el != null) {
                           el.innerHTML = '<bean:message key="label.price"/> ' + fl.innerHTML;
                           }
                           "/>
                            <span class="outer">
                                <span class="inner"></span>
                            </span>

                           <span  id="endDivsingle${singleItem.id}" onclick="">
                            <%=singleName%>&nbsp;<span style="color: #ed982f" ><small>(<%=((MenuSingleItem)singleItem).getFormattedPrice()%>)</small></span>
                           </span>
            <p id="alt_tooltip${itemIndex}" class="tooltip_pizza">
                <%--<%=singleName%>--%>
                    <%=singleName%>
                <br/>
                <%=((MenuSingleItem)singleItem).getFormattedPrice()%>
                <input type="hidden" name="price" value="<%=((MenuSingleItem)singleItem).getFormattedPrice()%>">
            </p>
        </label>
        <br>

        <%--================= end Alternatives--%>
        <script type="text/javascript" language="JavaScript">
            if ("${itemIndex}" == "0" & '<%=customizeMode%>' != '3'){
                setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');
                <%--setLastSelectedPrice('<%=((MenuSingleItem)singleItem).getFormattedPrice()%>');--%>
            }
        </script>
    </logic:iterate>
</div>
<script type="text/javascript">
    function parse(a, b, c) {
        alert(c);
    }
    function getap(str){

    }
    if ('<%=customizeMode%>' == '3')
        selectSingleItem("endDivsingle" + lastSelectedSIngleItemId);
    <c:if test="<%=!session.getAttribute(Constant.CUSTOMIZING_BASKET_ITEM).equals(0)%>">
    setCaptionArrayTrue();
    </c:if>

</script>

<script type="text/javascript">
    var  indexEndDivsingl=0;

//    function setIndex(index){
//                indexEndDivsingl=index;
//
//    }
    $(document).ready(function(){
        var typeLast=getTypeLast();
        if(typeLast=='Category'){
            var el=document.getElementById("singleInCmbined_0");
            el.onclick();
        }
    });

    function changePrice(val){
//        alert('changePrice:'+val);
        var priceLabels = $('[id^="down_price"]');
        var newPrice=val.replace('$','');
        var oldPriceX= document.getElementById("down_price");
        var oldPriceY= oldPriceX.innerHTML;
        var oldPrice= oldPriceY.replace('$','');
        oldPrice= oldPrice.replace('<bean:message key="label.price"/>','');
        oldPrice= oldPrice.replace('null','');
        oldPrice= oldPrice.trim();
        if(((val!='$0.00')&&(newPrice>oldPrice))||(oldPrice=='')){
            $.each(priceLabels, function () {
                this.innerHTML = "<bean:message key="label.price"/> " + val;
            });
        }
        sumPrice();
    }




</script>