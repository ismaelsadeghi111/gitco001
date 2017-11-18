<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%--
  Created by IntelliJ IDEA.
  User: Sepehr
  Date: Feb 5, 2012
  Time: 2:17:47 PM
  To change this template use File | Settings | File Templates.
--%>
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
<%---------------------------------------------------%>

<script type="text/javascript">

    var numb = '0123456789';
    var lwr = 'abcdefghijklmnopqrstuvwxyz';
    var upr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

    function isValid(parm,val) {
        if (parm == "") return true;
        for (i=0; i<parm.length; i++) {
            if (val.indexOf(parm.charAt(i),0) == -1) return false;
        }
        return true;
    }

    function isDigit(parm){
        return isValid(parm, numb);
    }

    function isCapital(parm){
        return isValid(parm.toUpperCase(), upr)
    }

    function postalCodeIsValid(part1, part2){
        if (part1.length < 3 || part2.length < 3)
            return false;

        return (isCapital(part1.charAt(0)) && isDigit(part1.charAt(1)) && isCapital(part1.charAt(2))) &&
               (isDigit(part2.charAt(0)) && isCapital(part2.charAt(1)) && isDigit(part2.charAt(2)));
    }

    function hideValidationError(){
        $('#validation-error')[0].innerHTML = "";
    }

    function showValidationError(text){
        $('#validation-error')[0].innerHTML = "<div class='validation-error'><bean:message key='label.registration.errors'/>:<ol><li>"+ text +"</li></ol></div>";
    }    

    function getStoreList(cityId) {
        hideValidationError();
        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'getStoreListOfSelectedCity', cityId: cityId},
            success:function(res) {
                if (res != "") {
                    $('#product_list').html(res);
                }
                highLightItem(cityId+'City');

            },
            failure: function() {
                alert('FAILURE');
            }
        });
    }

    function getStoreListOfSelectedCityOrderByStoreDistance() {
        var cityId = $('#city_block').children('li').children('.selected')[0].id;
        cityId = cityId.substring(0, cityId.length - 4);
        var postalCode1 = $('#postalCode1')[0].value;
        var postalCode2 = $('#postalCode2')[0].value;

        if (!postalCodeIsValid(postalCode1, postalCode2))
            return showValidationError("<bean:message key='errors.isNotValid.postalCode'/>");

        hideValidationError();

        $.ajax({
            type    :'POST',
            url     : '<%=context%>/frontend.do',
            data    :{operation: 'getStoreListOfSelectedCityOrderByStoreDistance', cityId: cityId, postalCode1: postalCode1, postalCode2: postalCode2},
            success:function(res) {
                if (res != "") {
                    $('#product_list').html(res);
                }
                highLightItem(cityId+'City');

            },
            failure: function() {
                alert('FAILURE');
            }
        });
    }

    function highLightItem(item) {

        var linkList = document.getElementById("city_block").getElementsByTagName("a");
        for (i = 0; i < linkList.length; i++) {
            linkList[i].className = "";
        }

        var selectedMenuItem = document.getElementById(item);
        if (selectedMenuItem != null)
            selectedMenuItem.setAttribute("class","selected");

    };

    $(window).load(function() {
        var homeAlert = getCookie("homeAlert");
        if (homeAlert == "fromFindStore"){
            setCookie("homeAlert", "", 0);
            tAlert('<bean:message key='message.error.find.store.for.user'/>', '', 'error', 10000);
        }
    });

</script>