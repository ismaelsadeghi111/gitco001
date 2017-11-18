<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Category" %>
<%--
  Created by IntelliJ IDEA.
  User: Mona
  Date: May 23, 2012
  Time: 10:47:24 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
%>

<%-- google Map scripts --%>
<script
        src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=AIzaSyCBUEQhcNKIBWDGbA8pqNHPHVG4bhW9P2Q&sensor=true"
        type="text/javascript">
</script>

<script type="text/javascript">
    function loadMap(lat,lng) {
        if (GBrowserIsCompatible()) {
            map = new GMap2(document.getElementById("map_canvas"));
            map.setCenter(new GLatLng(lat, lng), 15);
            map.setUIToDefault();
            var marker = new GMarker( new GLatLng(lat, lng));
            map.addOverlay(marker);

        }
    }

    $(function() {
        $("#map").dialog({
            bgiframe: true,
            width: 650,
            modal: true,
            resizable:false,
            autoOpen:false
        });
    });

    function showMap(sotreId,storeName){
        var ajaxUrl  =  "<%=context%>/viewMap.do?operation=viewMap&<%=Constant.STORE_ID%>="+sotreId;
        $.ajax({
            type: "GET",
            url: ajaxUrl ,  // Send the login info to this page
            success: function(msg){
                $("#map").ajaxComplete(function(event, request, settings){
                    $("#map").attr("title",'view Map');
                    $("#map").html(msg.toString());
                });
                $("#map").dialog('option', 'title', storeName);
                $("#map").dialog('open');
            }
        });
    }

</script>
