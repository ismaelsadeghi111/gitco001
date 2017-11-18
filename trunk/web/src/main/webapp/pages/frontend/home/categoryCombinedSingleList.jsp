<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 31, 2012
  Time: 1:38:18 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);

%>
<bean:define id="OR_CONDITION" value="FALSE"/>
<logic:present name="innerCategoryList">
    <bean:define id="OR_CONDITION" value="TRUE"/>
</logic:present>
<logic:present name="menuSingleList">
    <bean:define id="OR_CONDITION" value="TRUE"/>
</logic:present>
<logic:present name="combinedItemList">
    <bean:define id="OR_CONDITION" value="TRUE"/>
</logic:present>

<logic:equal name="OR_CONDITION" value="TRUE">
    <div id="homecarousel_mir">
        <ul id="mycarousel_mir">
            <logic:iterate id="innerCategoryItem" name="innerCategoryList" indexId="index">
                <li href="javascript: void(0);" onclick="return gotoCustomizepage('categoryId', '<%=FrontendAction.ClassTypeEnum.CATEGORY%>','${innerCategoryItem.id}', null, ${index});">
                    <a title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"
                       class="product_image_mir">
                        <c:choose>
                            <c:when test="${!empty innerCategoryItem.imageURL}">
                                <img height="210" width="267" alt="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" src="<%=context%><%=middlePath%>${innerCategoryItem.imageURL}"/>
                            </c:when>
                            <c:otherwise>
                               <img height="210" width="267" alt="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" src="<%=context%><%=middlePath%>/no_image.jpg"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <div><span class="price_mir"></span>
                        <h5>
                            <a href="javascript: void(0);" title="<%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                                    <%=((Category)innerCategoryItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                            </a>
                        </h5>
                    </div>
                </li>
            </logic:iterate>

            <logic:iterate id="singleItem" name="menuSingleList" indexId="index">
                <li href="javascript: void(0);" onclick="return gotoCustomizepage('singleId', '<%=FrontendAction.ClassTypeEnum.SINGLEMENUITEM%>',
                                    '${singleItem.id}', '${singleItem.groupId}', ${index}); setIdAndGroupId('${singleItem.id}', '${singleItem.groupId}');">
                    <a title="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"
                       class="product_image_mir">
                        <c:choose>
                            <c:when test="${!empty singleItem.imageURL}">
                                <img height="210" width="267" alt="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" src="<%=context%><%=middlePath%>${singleItem.imageURL}"/>
                            </c:when>
                             <c:otherwise>
                                <img height="210" width="267" alt="<%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" src="<%=context%>/img/no_image.jpg"/>
                             </c:otherwise>
                        </c:choose>
                    </a>
                    <div>
                        <span class="price_mir">
                        ${singleItem.formattedPrice}

                        </span>
                        <h5>
                            <a href="#" title=""><%=((MenuSingleItem)singleItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%></a>
                        </h5>
                    </div>
                </li>
            </logic:iterate>

            <logic:iterate id="combinedItem" name="combinedItemList" indexId="index">
                <li href="javascript: void(0);" onclick="return gotoCustomizepage('combinedId', '<%=FrontendAction.ClassTypeEnum.COMBINED%>',
                                    '${combinedItem.productNo}', '${combinedItem.groupId}', ${index});">
                    <a title="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>"
                       class="product_image_mir">
                                <img height="210" width="267" alt="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" src="<%=context%><%=middlePath%>${combinedItem.imageURl}"/>
                    </a>
                    <div>
                                    <span class="price_mir">
                                        <bean:message key="label.combined.price.from"/> <%=CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem)combinedItem))%>
                                    </span>
                        <h5>
                            <a class="price_mir" href="javascript: void(0);"
                               title="<%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                                    <%=((CombinedMenuItem)combinedItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>
                            </a>
                        </h5>
                    </div>
                </li>
            </logic:iterate>
        </ul>
        <h4>${categoryName}</h4>
    </div>

</logic:equal>
