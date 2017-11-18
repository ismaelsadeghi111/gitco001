<%--
  Created by IntelliJ IDEA.
  User: Mostafa.Jamshid
  Date: 2013-12-09
  Time: 08:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.web.action.QuickMenuAction" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="com.sefryek.doublepizza.model.SubCategory" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String context = request.getContextPath();
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    String menuType = (String) request.getParameter("menuType");
    Double minPrice = Double.valueOf("00.00");


%>
<link type="text/css" rel="stylesheet" href="<%=context%>/css/qmstyle.css" />
<logic:iterate id="menuItem" name="quickMenuForm" property="quickMenuSpecialList" indexId="index">
    <bean:define id="item" name="menuItem" type="com.sefryek.doublepizza.model.Category"/>
    <div class="title"><%=item.getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
    </div>
    <%--========COMBINED ITEMS======--%>
    <%
        List<CombinedMenuItem> combinedMenuItemList = new ArrayList<CombinedMenuItem>();
        for (SubCategory subCategoryItem : item.getSubCategoryList()) {
            if (subCategoryItem.getType().equals(CombinedMenuItem.class)) {
                combinedMenuItemList.add((CombinedMenuItem) subCategoryItem.getObject());
            }
        }
        ListIterator combinedMenuItemListIterator = combinedMenuItemList.listIterator();
        for (CombinedMenuItem combinedItem : combinedMenuItemList) {

    %>
    <div class="qmenu-item-box">
        <div class="menu-item-text">
            <table  style="padding:15px 0 5px 10px;" width="100%"  border="0"   cellspacing="0" cellpadding="0">
                <tr>
                    <td width="25%">
                        <%--<%=combinedItem.getProductNo()%>--%>
                        <a style="color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           onclick="gotoCustomizepage('combinedId', '<%=QuickMenuAction.ClassTypeEnum.COMBINED%>','<%= item.getId()%>','<%=combinedItem.getProductNo()%>', '<%=combinedItem.getGroupId()%>', '<%=combinedMenuItemListIterator.nextIndex()%>');"
                           title="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%=((CombinedMenuItem) combinedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                        </a>
                    </td>
                    <td width="25%"></td>
                    <td width="15%">
                        <a style="float:right; color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           onclick="return gotoCustomizepage('combinedId', '<%=QuickMenuAction.ClassTypeEnum.COMBINED%>','<%= item.getId()%>','<%=combinedItem.getProductNo()%>', '<%=combinedItem.getGroupId()%>', '<%=combinedMenuItemListIterator.nextIndex()%>');"
                           title="<%=CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem) combinedItem))%>">
                            <%if((combinedItem).getMenuSingleItemList().get(0).getToppingCategoryList().size()>1){%><bean:message key="label.from"/> <%}%>
                        </a>
                    </td>
                    <td width="15%">
                         <span style="float:left;padding-left:4px;color: #c00e14;font-size: 22px;">
                            <%=CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem) combinedItem))%>
                         </span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <table class="" style="margin-left: 30px; padding:0px 0 5px 25px;" width="90%"  border="0"   cellspacing="0" cellpadding="0">
            <tr>
                <td width="60%">
                    <span  class="gray  font4"><%=combinedItem.getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%></span>
                </td>
            </tr>
        </table>
    <hr>

    <%
            combinedMenuItemListIterator.next();
        }
    %>


    <%
        List<Category> categoryList = new ArrayList<Category>();
        for (SubCategory subCategoryItem : item.getSubCategoryList()) {
            if (subCategoryItem.getType().equals(Category.class)) {
                categoryList.add((Category) subCategoryItem.getObject());
            }
        }
        ListIterator categoryListIterator = categoryList.listIterator();
        for (Category innerCategoryItem : categoryList) {
    %>
    <div class="qmenu-item-box">
        <div class="menu-item-text">
            <table style="padding:15px 0 5px 10px;" width="100%"  border="0"   cellspacing="0" cellpadding="0">
                <tr>
                    <td width="25%">
                        <a style="color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           onclick="return gotoCustomizepage('categoryId', '<%=QuickMenuAction.ClassTypeEnum.CATEGORY%>','<%=innerCategoryItem.getId()%>','<%=innerCategoryItem.getId()%>', null, '<%=categoryListIterator.nextIndex()%>');"
                           title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%=((Category) innerCategoryItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                        </a>
                    </td>
                    <td width="25%"></td>
                    <td width="15%">
                        <%
                            List<MenuSingleItem> subMenuItemList = InMemoryData.getCategoryMenuSingleItemList(innerCategoryItem);
                            for (MenuSingleItem subSingleItem : subMenuItemList) {
                                minPrice = subSingleItem.getPrice().doubleValue();
                                if (subSingleItem.getPrice().doubleValue() < minPrice) {
                                    minPrice = subSingleItem.getPrice().doubleValue();
                                }
                            }
                        %>
                        <a style="float:right;color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           onclick="return gotoCustomizepage('categoryId', '<%=QuickMenuAction.ClassTypeEnum.CATEGORY%>','<%=innerCategoryItem.getId()%>','<%=innerCategoryItem.getId()%>', null, '<%=categoryListIterator.nextIndex()%>');"
                           title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                            <%if(subMenuItemList.size()>1){%>  <bean:message key="label.from"/> <%}%>
                            </a>
                        </td>
                    <td width="15%">
                         <span style="float:left;padding-left:4px;color: #c00e14;font-size: 22px;">
                            $<%=minPrice%>
                            </span>

                    </td>
                </tr>
            </table>
        </div>
    </div>
    <table class="" style="    margin-left: 30px; padding:0px 0 5px 25px;" width="90%"  border="0"   cellspacing="0"
           cellpadding="0">
        <tr>
            <td width="60%">
            </td>
        </tr>
    </table>
    <%
        }
    %>

    <%--========SINGLE ITEMS======--%>
    <%
        List<MenuSingleItem> menuSingleItemList = new ArrayList<MenuSingleItem>();
        for (SubCategory subCategoryItem : item.getSubCategoryList()) {
            if (subCategoryItem.getType().equals(MenuSingleItem.class)) {
                menuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
            }
        }
        ListIterator menuSingleItemListIterator = menuSingleItemList.listIterator();
        for (MenuSingleItem singleItem : menuSingleItemList) {

    %>
    <div class="qmenu-item-box">
        <div class="menu-item-text">
            <table style="padding:15px 0 5px 10px;" width="100%"  border="0"   cellspacing="0" cellpadding="0">
                <tr>
                    <td width="25%">
                        <%--<%=singleItem.getId()%>--%>
                        <a style="color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           title=""
                           onclick="return gotoCustomizepage('singleId', '<%=QuickMenuAction.ClassTypeEnum.SINGLEMENUITEM%>', '<%= item.getId()%>',
                                   '<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>', '<%=menuSingleItemListIterator.nextIndex()%>');
                                   setIdAndGroupId('<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>');">

                            <%=((MenuSingleItem) singleItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                            <%--<%=singleItem.getGroupId()%>--%>
                        </a>

                    </td>
                    <td width="25%"></td>
                    <td width="15%">
                        <a style="float:right;color: #c00e14;font-size: 22px;" href="javascript: void(0);"
                           onclick="return gotoCustomizepage('singleId', '<%=QuickMenuAction.ClassTypeEnum.SINGLEMENUITEM%>', '<%= item.getId()%>',
                                   '<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>', '<%=menuSingleItemListIterator.nextIndex()%>');
                                   setIdAndGroupId('<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>');">

                            <%if(menuSingleItemList.size()>1){%><bean:message key="label.from"/> <%}%> </a>
                        </td>
                    <td width="15%">
                         <span style="float:left;padding-left:4px;color: #c00e14;font-size: 22px;">
                        <%=singleItem.getFormattedPrice()%>
                             </span>

                        <%--<%=singleItem.getGroupId()%>--%>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <table class="" style="    margin-left: 30px; padding:0px 0 5px 25px;" width="90%"  border="0"   cellspacing="0"
           cellpadding="0">
        <tr>
            <td width="60%">
                <span class="gray font4"><%=singleItem.getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%></span>
            </td>
        </tr>
    </table>
    <hr>

    <%
            menuSingleItemListIterator.next();
        }
    %>
    <%--==========================--%>
</logic:iterate>


<script>
    function toggleContent(contentId) {
        // Get the DOM reference
        var contentId = document.getElementById(contentId);
        // Toggle
        contentId.style.display == "block" ? contentId.style.display = "none" :
                contentId.style.display = "block";
    }
 (function ($) {

                    $(document).ready(function () {
                        // #category uses the default options.
//                        $("#category").tabs();
                    });

}(jQuery));
</script>
