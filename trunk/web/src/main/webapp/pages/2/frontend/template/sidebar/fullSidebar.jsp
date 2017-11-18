<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<%--
  Created by IntelliJ IDEA.
  User: Hossein Sadeghi Fard
  Date: 1/8/14
  Time: 12:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    Float todayDPDollarsPercentage = (Float) request.getSession().getAttribute("todayDPDollarsPercentage");
    List<Popular> popularItems = (List<Popular>) session.getAttribute("popularItems");
    List<Map<String,Float>> dpDollarsWeeklyList = (List<Map<String,Float>>) request.getSession().getAttribute("dpDollarsWeeklyList");
    todayDPDollarsPercentage = todayDPDollarsPercentage == null ? new Float(0) : todayDPDollarsPercentage;
    Menu menuObj = InMemoryData.getMenuByName(Constant.SPECIAL_MENU_NAME);
    List<Category> specialList = menuObj.getCategoryList();
%>
<div id="MiddleBlockRight">
    <c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
    <c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>

    <div class="sidebar">
        <img src="<%=context%>/images/sidebar-top.jpg"  style="border-radius: 10px;margin-left:-10px;">
        <div class="sidebar-title">
            <h4><bean:message key="label.page.title.specials"/> </h4>
        </div>
        <ul >
            <%
                ListIterator<Category> categoryListIteratorInd = specialList.listIterator();
                for (Category menuItem :  specialList){
                    int index = categoryListIteratorInd.nextIndex();
            %>
            <li>
                <a href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=special#cat<%=index%>" onclick="setMenuType('special');">
                    <%=StringUtil.shortify(((Category) menuItem).getName((Locale) session.getAttribute(Globals.LOCALE_KEY)), Constant.MAX_LENGTH_CATEGORY_NAME)%>
                </a>
            </li>
            <%
                    categoryListIteratorInd.next();
                }
            %>

        </ul>

        <ul>
            <%
                if (popularItems != null && popularItems.size() >0){
            %>
            <div class="sidebar-title">
                <h4><bean:message key="label.page.title.populars"/> </h4>
            </div>

            <%      Category category;
                String paramName="";
                String type="";
                Integer catId;
                Integer menuItemId;
                Integer groupId;
                Integer orderNum=0;
                String preId="";
                BigDecimal prico= BigDecimal.valueOf(0);
                for(Popular popular : popularItems){
                    catId=popular.getCategoryId();
                    menuItemId=popular.getMenuitemId();
                    if((menuItemId>0)&&(menuItemId<10))preId="000"+menuItemId;
                    if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
                    if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
                    groupId=popular.getGroupId();
                    CombinedMenuItem popularCombinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(popular.getMenuitemId().toString(),popular.getGroupId().toString());
                    Category popularCategory = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, popular.getMenuitemId());
                    MenuSingleItem popularMenuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(preId, "");
                    Double price = popular.getPrice();
                    if(popularCombinedMenuItem!=null){
                        paramName="combinedId";
                        type="COMBINED";
                        popular.setType(CombinedMenuItem.class);
                        BigDecimal decimalPrice =  InMemoryData.getCombinedRealPrice(popularCombinedMenuItem);
                        price = decimalPrice == null ? price : decimalPrice.doubleValue();
                        price = CurrencyUtils.doubleRoundingFormat(price);
                        popular.setPrice(price);
                        prico=popularCombinedMenuItem.getPrice();
                        price= CurrencyUtils.doubleRoundingFormat(prico);

                    }
                    else if(popularMenuSingleItem!=null){
                        paramName="singleId";
                        type="SINGLEMENUITEM";
                        popular.setType(MenuSingleItem.class);


                    }
                    else if(popularCategory!=null)   {
                        paramName="categoryId";
                        type="CATEGORY";
                        popular.setType(Category.class);
                        category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, popular.getMenuitemId());
                        if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                            prico=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                        }   else {
                            prico=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                        }
                        price=CurrencyUtils.doubleRoundingFormat(prico);
                    }
            %>


            <%if(type.equalsIgnoreCase("SINGLEMENUITEM")){

            %>

            <li> <a  onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','1<%=catId%>','<%=preId%>', '',  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><%=popular.getFoodName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></a>    </li>
            <%}else if(type.equalsIgnoreCase("COMBINED")){%>
            <li> <a onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','1<%=catId%>','<%=menuItemId%>', '<%=groupId%>',  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><%=popular.getFoodName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></a></li>
            <%}else {%>
            <li> <a  onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','<%=menuItemId%>','<%=menuItemId%>', null,  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><%=popular.getFoodName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></a></li>

            <%
                        }
                    }
                    %>
        </ul>
                    <%
                }
            %>


        <a href="#">
            <div class="slider-ad">

                <span style="font-size: 18px;"><bean:message key="slideShow.rightAds.text1"/> </span>

                <c:if test="${enState}">
                    <b style="margin-bottom: 0px; margin-left: 18px;color:#fff;"> <bean:message key="slideShow.rightAds.text2"/></b>
                </c:if>
                <c:if test="${frState}">
                    <b style="font-size: 38px;font-family:'ItcKabelMedium',Arial;
                    margin-bottom: -2px;color:#fff;" > <bean:message key="slideShow.rightAds.text2"/></b>
                </c:if>
                <c:if test="${enState}">
                            <span class="font2" style="margin-left:8; text-align: left;width: auto;">
             </c:if>
             <c:if test="${frState}">
                            <span class="font2" style="margin: 0; padding-left:16px;text-align: left;width: auto;">
             </c:if>
             <bean:message key="slideShow.rightAds.text3"/><br/>
             <bean:message key="slideShow.rightAds.text4"/> <i style="color:yellow;margin-left: 17px;"><%=todayDPDollarsPercentage%>%</i><br/>
             <bean:message key="slideShow.rightAds.text5"/>
             </span>
             <div style="margin-top:10px; color:#fcefa1; float:left; width:100%;
                 font-size: 18px;
                 font-family:'ItcKabelMedium',Arial;
             ">
                 <div style="width: inherit;">
                     <ul>
                         <%
                             if (dpDollarsWeeklyList != null && dpDollarsWeeklyList.size() > 0) {
                                 for (Map<String, Float> dpDollarsWeekly : dpDollarsWeeklyList) {
                                     for (Map.Entry entry : dpDollarsWeekly.entrySet()) {
                         %>
                         <li>
                             <label style="float: left;"><%=StringUtils.capitalize(entry.getKey().toString())%></label>
                             <label style="float: right;"><%=entry.getValue()%>%</label>
                         </li>
                         <br />
                         <%
                                     }
                                 }
                             }
                         %>

                     </ul>
                 </div>
             </div>
             <br/>
            </div>
        </a>
        <%--<img src="<%=context%>/images/sidebar-bottom.jpg"  style="margin-top:25px;border-radius: 10px;margin-left:-10px;">--%>
    </div>
</div>

<div class="Clear"></div>
