<%@ page import="java.io.*" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Feb 23, 2012
  Time: 8:09:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%
    String context = request.getContextPath();
  %>
<html>
  <head>
      <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
      <title>Simple jsp page</title></head>
  <body>
  <!-- Google Tag Manager (noscript) -->
  <noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                    height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
  <!-- End Google Tag Manager (noscript) -->

    <%--<img src='<%=context%>/fromslides/slide_00.jpg' alt="" />--%>
    <%--<img src='<%=context%>/fromslides/slide_01.jpg' alt="" />--%>
    <%--<img src='<%=context%>/fromslides/slide_02.jpg' alt="" />  --%>
    <%
       String str = "Not Found";
      File file = new File("/home/doublepizzatest/ehsan.txt");

      FileReader fiStream = new FileReader(file);
      BufferedReader bufInStream = new BufferedReader(fiStream);
      try{
          str = bufInStream.readLine();
      }catch (Exception e){
          e.printStackTrace();
          bufInStream.close();
          fiStream.close();
      }finally {
          bufInStream.close();
          fiStream.close();
      }
  %>
  <h3>str: <%=str%></h3>
  <h7><%=file.getAbsolutePath()%></h7></br>
  <h7><%=file.getPath()%></h7></br>
  <h7><%=file.getCanonicalPath()%></h7></br>

  </body>
</html>