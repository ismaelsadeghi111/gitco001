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

<a href="<%=request.getContextPath()%>/Locale.do?method=changeLangToFrench">French</a>
<br />
<a href="<%=request.getContextPath()%>/Locale.do?method=changeLangToEnglish">English</a>

<%--<html:link page="/Locale?method=chinese">Chinese</html:link>--%>
<%--<html:link page="/Locale.do?method=english">English</html:link>--%>
<%--<html:link page="/Locale.do?method=german">German</html:link>--%>
<%--<html:link page="/Locale.do?method=france">France</html:link>--%>

<html:form action="/Submit.do">
    <br />
    <bean:message key="label.common.username" /> :
    <html:text property="username" />
    <br />
    <br />
    <bean:message key="label.common.password" /> :
    <html:text property="password" />
    <br />
    <br />

    <html:submit>
        <bean:message key="label.common.button.submit" />
    </html:submit>
</html:form>

</body>
</html>