<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/1/13
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<%
    String context = request.getContextPath();
    String defaultSelectedCategoryId = (String) request.getAttribute("defaultSelectedCategoryId");
    Integer defaultSelectedCategoryIndex = (Integer) request.getAttribute("defaultSelectedCategoryIndex");
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
//    session.setAttribute(Globals.LOCALE_KEY, new Locale("en", "EN"));
    String menuType = (String) request.getParameter("menuType");
    Category innerCategory = InMemoryData.getInnerCategoryById(defaultSelectedCategoryId);
    List<Category> menu_special = (List<Category>) request.getAttribute("menu_special");
    Double minPrice = Double.valueOf("00.00");
    List<Popular> popularItems = (List<Popular>) session.getAttribute("popularItems");
%>

<!-- Middle Block
Div Start Here -->
<%--<div id="MiddleBlock" class="nav-line">--%>

<%
    if (menuType.contentEquals("menu")){
%>

<table border="0" width="100%"  style="padding: 15px;">
    <tr>
        <td width="25%">
            <h1  class="cusfood" ><bean:message key="label.page.title.menu"/></h1>
        </td>
        <td width="50%">
            <div class="Floatleft">
                <%--<span class="font5"><bean:message key="label.Order.From.Our"/>:</span>--%>
                <a style="padding: 12px 32px;" class="btn-second" href="javascript: void(0);" style="margin:3px;"><bean:message key="label.page.title.menu"/> </a>
                <a style="padding: 12px 24px;" class="btn-first" href="javascript: void(0);" onclick="setMenuType('special'); getMenuList('special');" style="margin:3px;"><bean:message key="label.page.title.specials"/> </a>
            </div>
        </td>
    </tr>
</table>


<%
} else if (menuType.contentEquals("special")){
%>
<table border="0" width="100%"  style="padding: 15px;">
    <tr>
        <td width="25%">
            <h1  class="cusfood" ><bean:message key="label.page.title.specials"/></h1>
        </td>
        <td width="50%">
            <div class="FloatLeft">
                <%--<span class="font5"><bean:message key="label.Order.From.Our"/>:</span>--%>
                    <a style="padding: 12px 32px;" class="btn-first" href="javascript: void(0);" onclick="setMenuType('menu'); getMenuList('menu');" style="margin:3px;"><bean:message key="label.page.title.menu"/> </a>
                    <a   style="padding: 12px 24px;" class="btn-second" href="javascript: void(0);"  style="margin:3px;"><bean:message key="label.page.title.specials"/> </a>
            </div>
        </td>
    </tr>
</table>
<%
    }
%>

<div id="MiddleBlockLeft">

    <logic:notEmpty name="menu_special">
    <%--<ul id="mycarousel" style="z-index:1002; list-style-type: none;>--%>
    <ul style="z-index:1002; list-style-type: none;">

        <%--<logic:iterate id="menuItem" name="menu_special" indexId="index">--%>
    <%
        ListIterator<Category> categoryListIteratorIndex = menu_special.listIterator();
       for (Category menuItem :  menu_special){
           int index = categoryListIteratorIndex.nextIndex();
    %>



            <%--<li style="z-index:1002; list-style-type: none;"--%>
            <li style="list-style-type: none;">

        <div class="menu-item-box" id="cat<%=index%>">
                <div class="image-box">
                        <%--<a id="linkCategory${index}"--%>
                        <%--href="<%=context%>/frontend.do?operation=getCategoryAllItemList&catId=${menuItem.id}&menuName=Menu"--%>
                        <%--class="product_image"--%>
                        <%--onload="reqeustCategoryAllItem('${menuItem.id}'); selectCategoryItem(${index})">--%>
                        <%--<img height="136" width="164" src="<%=request.getContextPath()%><%=middlePath%>${menuItem.imageURL}" alt="<%=((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" onclick="reqeustCategoryAllItem('${menuItem.id}');"/>--%>
                    <img class="img-menulist" height="136" width="164" title="<%=((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"
                         src="<%=request.getContextPath()%><%=middlePath%><%= ((Category) menuItem).getImageURL()%>"
                         alt="<%=((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"/>
                        <%--</a>--%>
                </div>
                <div class="menu-item-text"
                    <%--onclick="document.getElementById('linkCategory${index}').onclick(); "--%>
                        >
                <span href="#" class="title">
                    <%=StringUtil.shortify(((Category) menuItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)), Constant.MAX_LENGTH_CATEGORY_NAME)%>
                </span>

                <table width="100%" border="0" cellspacing="0" cellpadding="0">

                                <%--========COMBINED ITEMS======--%>
                            <%
                                List<CombinedMenuItem> combinedMenuItemList = new ArrayList<CombinedMenuItem>();
                                for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                                    if (subCategoryItem.getType().equals(CombinedMenuItem.class)) {
                                        combinedMenuItemList.add((CombinedMenuItem) subCategoryItem.getObject());
                                    }
                                }
                                ListIterator combinedMenuItemListIterator = combinedMenuItemList.listIterator();
                                for (CombinedMenuItem combinedItem : combinedMenuItemList) {
                            %>

                                    <tr onclick="return gotoCustomizepage('combinedId', '<%=FrontendAction.ClassTypeEnum.COMBINED%>','<%= ((Category) menuItem).getId()%>','<%=combinedItem.getProductNo()%>', '<%=combinedItem.getGroupId()%>', '<%=combinedMenuItemListIterator.nextIndex()%>');">
                                                <td width="22%">
                                                    <a  class="hint--right" href="javascript: void(0);"  data-hint="<%=((CombinedMenuItem)combinedItem).getDescription((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                                                       <%=((CombinedMenuItem) combinedItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                                    </a>

                                                </td>
                                                <td width="30%">
                                                    <c:choose>
                                                        <c:when test="<%=(((CombinedMenuItem) combinedItem).getIsRedeemable() != null && ((CombinedMenuItem) combinedItem).getIsRedeemable().equals(true))%>">
                                                            <a href="javascript: void(0);">
                                                                <small style="font-style:italic; color:#8e8d8d;">
                                                                    <span><bean:message key="spend.dp$"/> </span>
                                                                </small>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="javascript: void(0);">
                                                                &nbsp;
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td width="12%">
                                                    <a href="javascript: void(0);">
                                                                <%if((combinedItem).getMenuSingleItemList().size()>0){%>
                                                       <span style="padding-left:30px;float:right"> <%if((combinedItem).getMenuSingleItemList().get(0).getToppingCategoryList().size()>1){%><bean:message key="label.from"/><%}%></span>
                                                        <%}%>
                                                    </a>
                                                </td>
                                                <td width="10%">
                                                    <a href="javascript: void(0);">
                                                       <span style="float:left;padding-left:4px;"><%=CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem) combinedItem))%></span>
                                                    </a>
                                                </td>
                                    </tr>

                            <%
                                    combinedMenuItemListIterator.next();
                                }
                                combinedMenuItemList.clear();
                            %>
                                <%--========INNERCATEGORY======--%>
                            <%
                                List<Category> categoryList = new ArrayList<Category>();
                                for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                                    if (subCategoryItem.getType().equals(Category.class)) {
                                        categoryList.add((Category) subCategoryItem.getObject());
                                    }
                                }
                                ListIterator categoryListIterator = categoryList.listIterator();
                                for (Category innerCategoryItem : categoryList) {
                            %>
                                    <tr onclick="return gotoCustomizepage('categoryId', '<%=FrontendAction.ClassTypeEnum.CATEGORY%>','<%=innerCategoryItem.getId()%>','<%=innerCategoryItem.getId()%>', null, '<%=categoryListIterator.nextIndex()%>');">
                                        <td width="22%">
                                            <a  class="hint--right" href="javascript: void(0);"
                                                data-hint="<%=((Category)innerCategoryItem).getDescription((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                                                <%=((Category) innerCategoryItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                            </a>
                                        </td>
                                        <td width="30%">
                                            <c:choose>
                                                <c:when test="<%=(((Category) innerCategoryItem).getIsRedeemable() != null && ((Category) innerCategoryItem).getIsRedeemable().equals(true))%>">
                                                    <a href="javascript: void(0);">
                                                        <small style="font-style:italic; color:#8e8d8d;">
                                                            <span><bean:message key="spend.dp$"/></span>
                                                        </small>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript: void(0);">
                                                        &nbsp;
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>


                                        <td width="12%">
                                            <%
                                                List<MenuSingleItem> subMenuItemList = InMemoryData.getCategoryMenuSingleItemList(innerCategoryItem);
                                                for (MenuSingleItem subSingleItem : subMenuItemList) {
                                                    minPrice = subSingleItem.getPrice().doubleValue();
                                                    if (subSingleItem.getPrice().doubleValue() < minPrice) {
                                                        minPrice = subSingleItem.getPrice().doubleValue();
                                                    }
                                                }
                                            %>


                                            <a href="javascript: void(0);">
                                                <span style="padding-left:30px;float: right;"><bean:message key="label.from"/></span>
                                            </a>
                                        </td>
                                        <td width="10%">
                                            <a href="javascript: void(0);">
                                            <span style="float:left;padding-left:4px;">$<%=minPrice%> </span>
                                            </a>
                                        </td>
                                    </tr>
                            <%
                                }
                                categoryList.clear();
                            %>
                                <%--========SINGLE ITEMS======--%>
                            <%
                                List<MenuSingleItem> menuSingleItemList = new ArrayList<MenuSingleItem>();
                                for (SubCategory subCategoryItem : ((Category) menuItem).getSubCategoryList()) {
                                    if (subCategoryItem.getType().equals(MenuSingleItem.class)) {
                                        menuSingleItemList.add((MenuSingleItem) subCategoryItem.getObject());
                                    }
                                }
                                ListIterator menuSingleItemListIterator = menuSingleItemList.listIterator();
                                for (MenuSingleItem singleItem : menuSingleItemList) {
                            %>
                                    <tr onclick="return  gotoCustomizepage('singleId', '<%=FrontendAction.ClassTypeEnum.SINGLEMENUITEM%>', '<%= ((Category) menuItem).getId()%>',
                                            '<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>', '<%=menuSingleItemListIterator.nextIndex()%>');
                                            setIdAndGroupId('<%=singleItem.getId()%>', '<%=singleItem.getGroupId()%>');">

                                        <td width="22%">
                                            <a href="javascript: void(0);">
                                                <%=((MenuSingleItem) singleItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY))%>
                                            </a>
                                        </td>
                                        <td width="30%">
                                            <c:choose>
                                                <c:when test="<%=(((MenuSingleItem) singleItem).getIsRedeemable() != null && ((MenuSingleItem) singleItem).getIsRedeemable().equals(true))%>">
                                                    <a href="javascript: void(0);">
                                                        <small style="font-style:italic; color:#8e8d8d;">
                                                            <span><bean:message key="spend.dp$"/></span>
                                                        </small>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="javascript: void(0);">
                                                        &nbsp;
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td width="12%">
                                            <%if(menuSingleItemList.size()>1){%>
                                            <a href="javascript: void(0);">
                                                 <span style="padding-left:30px;float: right;"><bean:message key="label.from"/></span>
                                            </a>
                                            <%}%>
                                        </td>
                                        <td  width="10%">
                                            <a>
                                                <span style="float:left;padding-left: 4px;"><%=singleItem.getFormattedPrice()%></span>
                                            </a>
                                        </td>
                                    </tr>
                            <%
                                    menuSingleItemListIterator.next();
                                }
                                 menuSingleItemList.clear();
                            %>
                </table>
        </div>
        </div>
        </li>

          <%
                  categoryListIteratorIndex.next();
                        }
          %>
<%--</logic:iterate>--%>


</ul>
<script type="text/javascript">
    <%--reqeustCategoryAllItem('<%= defaultSelectedCategoryId%>');--%>
    <%--selectCategoryItem(<%= defaultSelectedCategoryIndex %>);--%>
    setMenuType('<%=menuType%>');
</script>
</logic:notEmpty>



</div>

<%--</div>--%>


