<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%
    String context = request.getContextPath();
    String middlepath = InMemoryData.getFileServicePath(Constant.DATA_RESOURCES_CATERING_PATH);
%>

<div id="container">

    <div id="MiddleBlock" class="nav-line">
        <div id="MiddleBlockLeft">
            <div style="overflow:hidden; width:960px; margin: 10px auto; padding:0 20px;">
                <div class="pix_diapo">
                    <div data-thumb="<%=context%>/css/images/thumbs/catering01.JPG">
                        <img src="<%=context%>/css/images/slides/slide01.jpg">
                        <div class="caption elemHover fromLeft">
                            <bean:message key="catering.body.content1"/>
                        </div>
                    </div>
                    <div data-thumb="<%=context%>/css/images/thumbs/catering02.JPG" data-time="5000">
                        <img src="<%=context%>/css/images/slides/slide02.jpg">
                        <div class="caption elemHover fromRight" style="bottom:65px; padding-bottom:5px; color:#ff0; text-transform:uppercase">
                            <bean:message key="catering.body.content2.1"/>
                        </div>
                        <div class="caption elemHover fromLeft" style="padding-top:5px;">
                            <bean:message key="catering.body.content2.2"/>
                        </div>
                    </div>

                    <div data-thumb="<%=context%>/css/images/thumbs/catering03.JPG" data-time="7000">
                        <img src="<%=context%>/css/images/slides/slide03.jpg">
                        <div class="elemHover caption fromLeft" style="bottom:70px; width:auto; -webkit-border-top-right-radius: 6px; -webkit-border-bottom-right-radius: 6px; -moz-border-radius-topright: 6px; -moz-border-radius-bottomright: 6px; border-top-right-radius: 6px; border-bottom-right-radius: 6px;">
                            <bean:message key="catering.body.content3.1"/>
                        </div>

                    </div>
                </div>
            </div>


            <div class="MiddleWrapper font2"><bean:message key="catering.body.content0"/></div>
            <br />
            <div class="MiddleWrapper font2">
                <table class="font3 border" style="padding-bottom:15px; margin-bottom:10px;" width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="3%"><img src="<%=context%>/images/information.png" alt="information" /></td>
                        <td width="45%" ><label class="orange_color font3" ><bean:message key="catering.body.title"/></label></td>
                        <td width="35%"> <bean:message key="catering.download.title"/> <a  href="<%=context + middlepath%>assortiment_doublePizza_V4_English.pdf"  class="orange_color font3" style="text-decoration:underline;">English</a> <bean:message key="label.filter.or"/> <a   href="<%=context + middlepath%>assortiment_doublePizza_V4_french.pdf" class="orange_color font3" style="text-decoration:underline;" >French</a></td>
                </table>
                <p class="font3" style="padding-bottom:15px;"><%--If you are planning a  corporate or personal event, we can help with food and catering. Whether it’s company picnics, conferences, business luncheons, sporting events or birthday parties, think Double Pizza Catering Serviece. We take the hassle out of event planning by perparing great party platters for 10 to 500 people, tailored to your groupaTM s desires and tastes. Contact us today and allow us to assist in making your function one that is sure to impress. <br />--%>
                    <bean:message key="catering.body.content1"/>
                </p>
                <p class="font3"  style="padding-bottom:15px;">
                    <bean:message key="catering.body.content2"/>
                </p>
                <p   class="font3"  style="padding-bottom:15px;">
                    <bean:message key="catering.body.content3"/>
                </p>
                <table   width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="40" class="orange_color font3"> <bean:message key="catering.body.content4"/></td>
                        <td><a class="btn-first FloatRight" href="javascript: void(0);" onclick="getCateringItemsOrder();"><bean:message key="label.online.ordering"/></a></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!-- Middle Block Div End Here -->
    <textarea id="input"  style="display: none"></textarea>

</div>
<!-- container div end here -->

</body>

<script>
    $(document).ready(function () {
        isSend=0;
        (function (global) {
            document.getElementById("input").value=isSend;
            global.localStorage.setItem("mySharedData", document.getElementById("input").value);
        }(window));

    });
    $(function(){
        $('.pix_diapo').diapo();
    });
    function getCateringItemsOrder() {
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);
        myForm.method = 'POST';
        myForm.action = '<%=context%>/catering.do';

        var operation = document.createElement('input');
        operation.setAttribute('type','hidden');
        operation.setAttribute('name','operation');
        operation.setAttribute('value','goToCaternigOrder');

        myForm.appendChild(operation);

        myForm.submit();
    }


</script>