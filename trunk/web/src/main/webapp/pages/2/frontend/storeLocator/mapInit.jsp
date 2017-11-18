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



    function highLightItem(item) {

        var linkList = document.getElementById("city_block").getElementsByTagName("a");
        for (i = 0; i < linkList.length; i++) {
            linkList[i].className = "";
        }

        var selectedMenuItem = document.getElementById(item);
        if (selectedMenuItem != null)
            selectedMenuItem.setAttribute("class","selected");

    };



    function loadMap(lat,lng) {
        if (GBrowserIsCompatible()) {
            map = new GMap2(document.getElementById("map"));
            map.setCenter(new GLatLng(lat, lng), 15);
            map.setUIToDefault();
            var marker = new GMarker( new GLatLng(lat, lng));
            map.addOverlay(marker);

        }
    }

    $(function() {
        $("#map").dialog({
            autoOpen: false,
            draggable: false,
            resizable: false,
            modal: true,
            width: 650,
            height: 450,
            close: function () {
                $('body').css('overflow', 'scroll'); },
            title:'<bean:message key="label.page.menu.doublePizzaDOLLARS"/> ',
            open: function(event, ui) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '50%');
                $(event.target).parent().css('left', '50%');
                $(event.target).parent().css('margin-top', '-250px');
                $(event.target).parent().css('margin-left', '-325px');
                $('body').css('overflow', 'hidden'); //this line does the actual hiding
            }
        });
    });
    function showMap(sotreId,storeName,lat,lng,address1,address2){
        loadMap(lat,lng);
        var ajaxUrl  =  "<%=context%>/viewMap.do?operation=viewMap&<%=Constant.STORE_ID%>="+sotreId;
        $.ajax({
            type: "GET",
            url: ajaxUrl ,  // Send the login info to this page
            success: function(msg){
                $("#map").ajaxComplete(function(event, request, settings){
                    $("#map").attr("title",'view Map');
                });
                $("#map").dialog('option', 'title', storeName + " ("+address1 + ", "+address2+")");
                $("#map").dialog('open');
            }
        });
    }

    function getStoreListOfSelectedCityOrderByStoreDistance() {
        var cityId = $('#city_block').children('.selected')[0].id;
        cityId = cityId.substring(0, cityId.length - 4);
        var postalCode1 = $('#postalCode1')[0].value;
        var postalCode2 = $('#postalCode2')[0].value;
        if (!postalCodeIsValid(postalCode1, postalCode2)) {
            $('#validation-error').html("<span style='color:#DA0F00;'><bean:message key='errors.isNotValid.postalCode'/></span>");

            $('.error').css({'display': 'block'});
        } else
        {
            $('.error').css({'display': 'none'});
        }

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


</script>
