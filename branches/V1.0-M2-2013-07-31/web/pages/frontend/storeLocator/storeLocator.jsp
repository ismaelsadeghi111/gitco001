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

<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="center_column">
        <div id="center_column_inner2" style="min-height:650px;">
            <div class="rte">

                <h5><bean:message key="store.loacator.title" /></h5>
                <div id="validation-error">

                </div>

                <ul id="product_list" class="clear">
                    <%@include file="storeList.jsp"%>
                </ul>
                <p></p>
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
        <!-- MODULE Block cart -->
        <div id="cart_block" class="block exclusive">
            <h4>
                <bean:message key="store.loacator.right.menu.title"/>
                <span id="block_cart_expand" class="hidden">&nbsp;</span>
                <span id="block_cart_collapse">&nbsp;</span>
            </h4>
            <div class="block_content">
                <!-- block summary -->
                <div style="margin-left:7px; margin-bottom:2%;">
                    <span style="position:relative; top:1%; bottom:1%; margin-right:7px; line-height:23px;"><bean:message key="label.button.postal.code"/></span>
                </div>
                <div id="find" style="margin-left: 7px;">
                    <input style="text-transform:uppercase; width: 26px; height:20px; padding-left:3px;padding-right:3px;" name="postalCode1" id="postalCode1" type="text" size="3" maxlength="3"/> -
                    <input style="text-transform:uppercase; width: 26px;height:20px; padding-left:3px;padding-right:3px;" name="postalCode2" id="postalCode2" type="text" size="3" maxlength="3"/>
                    <a href="javascript: void(0);" onclick="getStoreListOfSelectedCityOrderByStoreDistance();"><img src="<%= context %>/img/search.png" style="top: -5px;position:relative;"/></a>
                </div>
                <div id="cart_block_summary" class="collapsed">

                </div>
                <!-- block list of products -->
                <div id="cart_block_list" class="expanded">
                    <p>&nbsp;</p>

                    <ul id="city_block">

                        <logic:iterate id="city" name="<%=Constant.STORE_CITY%>" >

                            <li class="first_item" style="margin-bottom:4px;">

                                <a id="${city}City" href="javascript:getStoreList('${city}');" title=""  class="<%=city.equals(MessageUtil.get("store.loacator.all.cities", (Locale)session.getAttribute(Globals.LOCALE_KEY)))?"selected":""%>">
                                <%--<a id="${city}City" href="javascript:getStoreList('${city}');" title=""  class="<%=city.equals(Constant.ALTERNATIVE_ALL_CITIES)?"selected":""%>">--%>
                                    &nbsp; ${city}
                                </a>

                            </li>

                        </logic:iterate>

                    </ul>
                </div>
            </div>
        </div>
        <!-- /MODULE Block cart -->
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
<div id="map" style="display:none">
</div>