<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.common.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
    String context = request.getContextPath();
    session.setAttribute(Globals.LOCALE_KEY, new Locale("fr", "FR"));
    String currentDayName = DateUtil.getNameOfDayByLocale(new Date(), Locale.ENGLISH);
    session.setAttribute("currentDayName", currentDayName);
//    session.setAttribute(Globals.LOCALE_KEY, new Locale("en", "EN"));
    response.sendRedirect(context + "/frontend.do");

%>

