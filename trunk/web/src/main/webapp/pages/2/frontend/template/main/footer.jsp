<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/1/13
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String context = request.getContextPath();
%>
<link rel="stylesheet" href="<%=context%>/css/jquery-ui.css">
<link rel="stylesheet" href="<%=context%>/css/style.css">
<script src="<%=context%>/js/jquery/jquery-ui.js"></script>

<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>

<div class="Clear"></div>
<div id="Footer">
    <div id="Footer-box">
        <div id="Footer-inner">
            <div class="footer-top">
                <div class="phone"><img src="<%=context%>/images/phone.png" alt="Phone" /><img style="margin-left: 30px;" src="<%=context%>/images/phone2.png" alt="Phone" /></div>
                <%--<a target="_blank" class="iphone-app" href="http://itunes.apple.com/us/app/doublepizza/id527593994?mt=8">--%>
                    <%--<c:choose>--%>
                        <%--<c:when test='${enState}'>--%>
                            <%--<img src="<%=context%>/images/iphone-app.png" alt="Iphone App" />--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<img src="<%=context%>/images/iphone-app-fr.png" alt="Iphone App" />--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>

                <%--</a>--%>
                <div class="social-icon2">
                    <ul>
                        <li>
                            <c:choose>
                                <c:when test='${enState}'>
                                    <img src="<%=context%>/images/connect.png" alt="Connect"  />
                                </c:when>
                                <c:otherwise>
                                    <img src="<%=context%>/images/connect2.png" alt="Connect"  />
                                </c:otherwise>
                            </c:choose>

                        </li>
                        <li><a target="_blank" class="facebook2" href="http://www.facebook.com/doublepizzaca" title="Facebook"></a></li>
                        <li><a target="_blank"  class="instagram2"  href="http://www.instagram.com/doublepizzaca" title="Instagram"></a></li>
                        <li><a target="_blank" class="youtube2"  href="http://www.youtube.com/user/DoublePizzaPepe" title="Youtube"></a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-menu">
                <ul>
                    <li><a  href="<%=context%>/frontend.do" title="Double Pizza.ca"> <bean:message key="label.page.menu.home"/></a></li>
                    <li><a id="quickmenu"  href="javascript: void(0);" onclick="setMenuType('menu'); getQuickMenu('menu');" title="<bean:message key="label.page.quickMenu"/>"><bean:message key="label.page.quickMenu"/></a></li>
                    <li><a  href="javascript: return false;" title="<bean:message key="label.page.menu.catering"/>" onclick="getCateringItems();" ><bean:message key="label.page.menu.catering"/></a></li>
                    <li>                <a id="menu_item_storeLocator" href="<%=context%>/frontend.do?operation=goToStoreLocator" title="<bean:message key="label.page.menu.store.locator"/>" class="">
                        <bean:message key="label.page.menu.store.locator"/>
                    </a>
                    </li>
                    <li><a id="menu_item_franchising" href="<%=context%>/frontend.do?operation=goToFranchisingPage"
                           title="<bean:message key="label.page.menu.franchising"/>"><bean:message key="label.page.menu.franchising"/></a></li>
                    <li><a href="<%=context%>/frontend.do?operation=goToAboutPage"  title="<bean:message key="label.page.menu.about.us"/>"><bean:message key="label.page.menu.about.us"/></a></li>
                </ul>
            </div>

            <%--<div class="contact-form"  style="padding-top:12px;">--%>
             <%--&lt;%&ndash;    <form action="<%=context%>/feedBack.do?operation=makeFeedBack" method="post" class="std" id="feedBackForm"--%>
                     <%--name="feedBackForm">&ndash;%&gt;--%>
                                    <%--<table width="100%" border="0" cellspacing="0" cellpadding="0">--%>
                                       <%--<tr>--%>
                                        <%--<td valign="top">--%>
<%--&lt;%&ndash;                                               <input class="text-box" type="text"--%>
                                                      <%--onblur="if(this.value=='')this.value='<bean:message key="label.name"/>*'"--%>
                                                      <%--onfocus="if(this.value=='<bean:message key="label.name"/>*')this.value=''"--%>
                                                      <%--value="<bean:message key="label.name"/>*"--%>
                                               <%--<logic:present name="feedBackForm">--%>
                                                   <%--value="${feedBackForm.name}"--%>
                                               <%--</logic:present>/>--%>

                                               <%--<input class="text-box" type="text"--%>
                                                      <%--onblur="if(this.value=='')this.value='<bean:message key="lable.registration.email"/>*'"--%>
                                                      <%--onfocus="if(this.value=='<bean:message key="lable.registration.email"/>*')this.value=''"--%>
                                                      <%--value="<bean:message key="lable.registration.email"/>*"--%>
                                               <%--<logic:present name="feedBackForm">--%>
                                                   <%--value="${feedBackForm.email}"--%>
                                               <%--</logic:present>/>&ndash;%&gt;--%>

                                           <%--</td>--%>

                                           <%--<td valign="top" rowspan="2">--%>
                                              <%--&lt;%&ndash; <textarea class="textarea"--%>
                                                         <%--onblur="if(this.value=='')this.value='<bean:message key="label.feedback.send.message"/>'"--%>
                                                         <%--onfocus="if(this.value=='<bean:message key="label.feedback.send.message"/>')this.value=''"--%>
                                                         <%--rows="4" cols="4" ><bean:message key="label.feedback.send.message"/>--%>
                                                   <%--</textarea>--%>
                                               <%--<a class="contact-form-btn" href="#"  ><bean:message key="label.send"/></a>&ndash;%&gt;--%>
                                           <%--</td>--%>
                       <%--<td></td>--%>
                       <%--<td></td>--%>
                        <%--<td rowspan="2">--%>
                            <%--&lt;%&ndash;<a class="floatright"  href="<%=context%>/frontend.do?operation=goToFeedBackPage" ><img src="<%=context%>/images/contact-bg.png"  alt="Image" /></a>&ndash;%&gt;--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
             <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
            <%--</div>--%>

            <div class="subscribe-border subscribe-color-black">
                    <div class="subscribe-center">
                        <bean:message key="subscribe.label"/>
                    </div>

                    <div class="subscribe-center-percent">
                        <input type="text" id="email-input-id" name="Email" maxlength="50" size="20" placeholder=<bean:message key="subscribe.enter.email"/>>
                        <span class="subscribe-font-bold" id="validation-message-id"></span>
                    </div>

                    <div class="subscribe-center">
                        <button id="button-subscribe-id" class="subscribe-button" type="button" onclick="validate()"><span><bean:message key="subscribe.button"/></span></button>
                    </div>
            </div>

            <div class="dpDollar-footer">
                <a href="javascript: void(0);" onclick="dpDollarsPopUp();">
                    <c:choose>
                        <c:when test='${enState}'>
                            <img src="<%=context%>/img/dpDollar/dpdollar-en.png"  />
                        </c:when>
                        <c:otherwise>
                            <img src="<%=context%>/img/dpDollar/dpdollar-fr2.png"  />
                        </c:otherwise>
                    </c:choose>

                </a>
            </div>

            <span class="copy-right">
                  &copy;<bean:message key="label.page.footer.all.right"/>
          <span style="padding-top: 10px;">  <bean:message key="label.page.footer.poweredby"/> <a href="http://www.zoitek.com" target="_blank" style="text-decoration: underline;"><bean:message key="label.page.footer.zoitek"/></a></span> <br> <div class="copy-right"> <span>Version:<bean:message key="app.version"/></span> &nbsp;<span id="build"><bean:message key="app.build"/></span></div>
              </span>
              </span>
        </div>
    </div>
</div>
<div id="dpDollars" >
    <%@include file="dpDollars.jsp"%>
</div>

<script language="JavaScript">
    $(document).ready(function(){
        var build= document.getElementById("build");
        var buildCaption=build.innerHTML;
        build.innerHTML= buildCaption.replace('Build','<bean:message key="label.build"/>');
    });
    $(function() {
        $("#dpDollars").dialog({
            autoOpen: false,
            draggable: false,
            resizable: false,
            modal: true,
            width: 900,
            height: 650,
            close: function () {
                $('body').css('overflow', 'scroll'); },
            title:'<bean:message key="label.page.menu.doublePizzaDOLLARS"/> ',
            open: function(event, ui) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '50%');
                $(event.target).parent().css('left', '50%');
                $(event.target).parent().css('margin-top', '-340px');
                $(event.target).parent().css('margin-left', '-450px');
                $('body').css('overflow', 'hidden'); //this line does the actual hiding
            }
        });
    });

    function dpDollarsPopUp(){
        $("#dpDollars").dialog('open');
    }
</script>

<script>
    function validate() {
        document.getElementById('validation-message-id').style.visibility = 'visible';
        document.getElementById('validation-message-id').style.opacity = "1.0";
        var email = document.getElementById("email-input-id").value;
        if (validateEmail(email)) {
            document.getElementById('validation-message-id').style.color = "#00ff00";
            document.getElementById('validation-message-id').innerHTML = "<bean:message key="subscribe.successful"/>";
            document.getElementById('validation-message-id').style.visibility = 'visible';
            document.getElementById("validation-message-id").addEventListener('click', fadeOutEffectTimer());
        } else {
            document.getElementById('validation-message-id').style.color = "#ff3300";
            document.getElementById('validation-message-id').innerHTML = "<bean:message key="subscribe.email.not.valid"/>";
            document.getElementById('validation-message-id').style.visibility = 'visible';
            document.getElementById("validation-message-id").addEventListener('click', fadeOutEffectTimer());
        }
        return false;
    }

    function validateEmail(email) {
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    function fadeOutEffectTimer() {
        setTimeout(function () {
            fadeOutEffect()
        }, 2000)
    }

    function fadeOutEffect() {
        var fadeTarget = document.getElementById("validation-message-id");
        var fadeEffect = setInterval(function () {
            if (!fadeTarget.style.opacity) {
                fadeTarget.style.opacity = 1;
            }
            if (fadeTarget.style.opacity < 0.1) {
                clearInterval(fadeEffect);
            } else {
                fadeTarget.style.opacity -= 0.1;
            }
        }, 50);
    }
</script>