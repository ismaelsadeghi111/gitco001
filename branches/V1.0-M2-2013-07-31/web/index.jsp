<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%
    String context = request.getContextPath();
    session.setAttribute(Globals.LOCALE_KEY, new Locale("fr", "FR"));
    response.sendRedirect(context + "/frontend.do");

%>

