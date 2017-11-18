<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 20, 2012
  Time: 5:42:29 PM
--%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

<html:messages id="err_name" property="common.username.err">
<div style="color:red">
	<bean:write name="err_name" />
</div>
</html:messages>

<html:messages id="err_password" property="common.password.err">
<div style="color:red">
	<bean:write name="err_password" />
</div>
</html:messages>


<%--<html:errors/>--%>

<br />
<br />

<a href="<%=request.getContextPath()%>/menu.do?method=updateMenuList">getAllCategory</a>
<a href="<%=request.getContextPath()%>/menu.do?method=getSpecialMenuCategoryList">getAllCategory</a>
<a href="<%=request.getContextPath()%>/menu.do?method=getSubCategoryList">getAllCategory</a>
<a href="<%=request.getContextPath()%>/menu.do?method=getMenuSingleItemList">getAllCategory</a>
<br />

</body>
</html>