<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 31, 2012
  Time: 9:10:03 AM                                                               
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String defaultSelectedCategoryId = (String)request.getAttribute("defaultSelectedCategoryId");
    Integer defaultSelectedCategoryIndex = (Integer)request.getAttribute("defaultSelectedCategoryIndex");
    String middlepath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
%>
<logic:notEmpty name="menu_special">
    <h4 id="step_1">
        <logic:equal  value="menu" name="menuType">
                <bean:message key="label.page.category.first.step.menu" />
        </logic:equal>
        <logic:equal value="special" name="menuType">
                <bean:message key="label.page.category.first.step.Special" />
        </logic:equal>
    </h4>
    <ul id="mycarousel">
        <logic:iterate id="menuItem" name="menu_special" indexId="itemIndex">
            <li style="z-index:1002;" title="<%=((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>">
                <a id="linkCategory${itemIndex}" href="javascript: void(0);" class="product_image" onclick="reqeustCategoryAllItem('${menuItem.id}'); selectCategoryItem(${itemIndex})">
                    <img src="<%=request.getContextPath()%><%=middlepath%>${menuItem.imageURL}" alt="<%=((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY))%>" onclick="return reqeustCategoryAllItem('${menuItem.id}');"/>
                </a>
                <div style="z-index: 1003; padding-bottom: -120px; margin-bottom:-150px;" onclick="document.getElementById('linkCategory${itemIndex}').onclick(); ">
                    <span class="price" style="padding-top:34px;">
                         <%=StringUtil.shortify(((Category)menuItem).getName((Locale)session.getAttribute(Globals.LOCALE_KEY)), Constant.MAX_LENGTH_CATEGORY_NAME)%>
                    </span>
                </div>
            </li>
        </logic:iterate>
    </ul>
    <script type="text/javascript">
        reqeustCategoryAllItem('<%= defaultSelectedCategoryId%>');
        selectCategoryItem(<%= defaultSelectedCategoryIndex %>);
    </script>
</logic:notEmpty>
    
