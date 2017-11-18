<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.model.Store" %>
<%@ page import="com.sefryek.doublepizza.model.WorkingHour" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sefryek.common.MessageUtil" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>

<%
    String context = request.getContextPath();
%>

<%--<link rel="stylesheet" href="<%=context%>/css/jquery-ui.css">
<script src="<%=context%>/js/jquery/jquery-1.10.2.js"></script>

<script src="<%=context%>/js/jquery/jquery-ui.js"></script>--%>
<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="">
        <div id="center_column_inner2" style="min-height:650px;">
            <div class="rte">
                <table border="0" width="100%"  style="padding: 15px;">
                    <tr>
                        <td width="30%">
                            <div class="cusfood"><bean:message key="store.loacator.title" /></div>
                        </td>
                        <td width="50%">
                            <div class="Floatleft">
                                <%--<span class="font5"><bean:message key="label.Order.From.Our"/>:</span>--%>
                                <a style="padding: 12px 32px;" class="btn-first" href="javascript: void(0);"  onclick="setMenuType('menu'); getMenuList('menu');" style="margin:3px;"><bean:message key="label.page.title.menu"/> </a>
                                <a style="padding: 12px 24px;" class="btn-first" href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" style="margin:3px;"><bean:message key="label.page.title.specials"/> </a>
                            </div>
                        </td>
                    </tr>
                </table>



                <div id="validation-error" style="display:none" >  </div>
                <div id="error" class="error" style="display:none" >
                    <span><bean:message key='label.registration.errors'/>:</span>
                    <br>
                    <ol>
                        <li ><bean:message key='errors.isNotValid.postalCode'/></li>
                    </ol>
                </div>
                <div class="MiddleWrapper">
                    <div id="citysticky_container">




                    <div class="store-locator-title"><bean:message key="store.loacator.right.menu.title"/></div>

                    <div>
                        <ul id="city_block" class="ui-dialog-title">
                            <logic:iterate id="city" name="<%=Constant.STORE_CITY%>"   indexId="index" length="size">
                                <a style="font-size: 14px;font-family:'ItcKabelMedium',Arial; line-height: 28px; " id="${city}City" href="javascript:hideError();getStoreList('${city}');" title=""  class="<%=city.equals(MessageUtil.get("store.loacator.all.cities", (Locale)session.getAttribute(Globals.LOCALE_KEY)))?"selected":""%>">
                                        <%--<a id="${city}City" href="javascript:getStoreList('${city}');" title=""  class="<%=city.equals(Constant.ALTERNATIVE_ALL_CITIES)?"selected":""%>">--%>
                                    &nbsp; ${city}
                                </a>
                            </logic:iterate>
                        </ul>
                    </div>

                        <div style="margin-top:5px;margin-bottom:5px; ">
                    <!-- block summary -->
                        <span class="store-locator-title" ><bean:message key="label.button.postal.code"/></span>
                    <span id="find" style="margin-left: 7px;">
                       <input id="postalCode1" class="default-textbox location_textbox" type="text" onkeyup="goNextElemnt(this, 'postalCode2');" maxlength="3" size="3" name="postalCode1" style="text-transform:uppercase;">
                        <b>-</b>
                        <input id="postalCode2" class="default-textbox location_textbox" type="text" maxlength="3" size="3" name="postalCode2" style="text-transform:uppercase;">
                    </span>
                    <a style="margin-left: 15px;" onclick="getStoreListOfSelectedCityOrderByStoreDistance();" class="btn-first" href="javascript: void(0);"><bean:message key="label.button.find"/></a>
                    <div id="cart_block_summary" class="collapsed">
                    </div>
                    <!-- block list of products -->
                    <div id="cart_block_list" class="expanded">
                    </div>
                </div>
                    </div>
                <div id="product_list" class="stores-column" style="border:  0px solid;margin-top: 20px">
                    <%@include file="storeList.jsp"%>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>

    <div style="display:inline-block; width:230px;margin-left:10px;">
        <div id="right_column" class="column carousel_on">
        <script type="text/javascript">
            var CUSTOMIZE_TEXTFIELD = 1;
            var customizationIdMessage = 'Customization #';
            var removingLinkText = 'remove this product from my cart';
        </script>

    </div>
        <div id="right_column_continue" >
        <ul>
            <li>
                <a class="two_chef" title="<bean:message key="label.page.footer.link.franchise"/>" href="<%=request.getContextPath()%>/frontend.do?operation=goToFranchisingPage">
                </a>
            </li>
        </ul>

    </div>
    </div>
    <!-- Right -->

    <br class="clear">
</div>
<div id="map"  height="450" style="display:none; height:450px;">
</div>

<script>
    function goNextElemnt(sender, next) {
        var len = sender.value.length;
        var maxLen = sender.maxLength;

        if (len >= maxLen)
            $('#' + next).focus();
    }
    function hideError(){
        $('.error').css({'display': 'none'});
    }
</script>