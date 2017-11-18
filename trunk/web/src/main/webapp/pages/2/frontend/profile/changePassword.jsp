<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%
    String context = request.getContextPath();
%>

<div class="cusfood"><bean:message key="change.password"/></div>
<div class="LeftMiddleWrapper" style="" id="LeftMiddleWrapperDiv">

        <form action="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=savePassword"
              id="profileForm"
              name="profileForm" method="post">
            <input type="hidden" id="mainPassword" value="${sessionScope.user_transient.password}"/>
            <input type="hidden" id="passMessage" value="${passMessage}"/>


            <table class="font" width="1200px" style="padding-top:10px;" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="font" colspan="2" align="left">

                            <h2>

                                <label id="successfulLabel" style="display: none;">
                                    <bean:message key='label.msg.change.pass.success' />
                                </label>
                                </font>
                                <font color="red">
                                <label id="wrongLabel" style="display: none;">
                                    <bean:message key='wrong.Password'/>
                                </label>
                                </font>
                            </h2>
                            <br>

                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="font" width="15%" ><bean:message key="old.Password"/>:</td>
                    <td width="19%">
                        <input class="default-textbox" type="password" name="oldPassword" id="oldPassword" onkeyup="nospaces(this)"/>
                        <sup class="required">*</sup>
                    </td>
                    <td>
                        <label class="error" for="oldPassword"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font" width="15%"><bean:message key="new.Password"/>:</td>
                    <td width="19%">
                        <input class="default-textbox" type="password" name="newPassword" id="newPassword" onkeyup="nospaces(this)"/>
                        <sup class="required">*</sup>
                    </td>
                    <td>
                        <label class="error" for="newPassword"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"width="15%"><bean:message key="confirm.Password"/>:</td>
                    <td width="19%">
                        <input class="default-textbox" type="password" name="confirmPassword" id="confirmPassword" onkeyup="nospaces(this)"/>
                        <sup class="required">*</sup>
                    </td>
                    <td>
                        <label class="error" for="confirmPassword"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: left; ">
                        <a href="javascript: void(0);" class="btn-first floatright" onclick="document.getElementById('profileForm').submit()"><bean:message key="label.Save"/></a>
                        <a href="javascript: void(0);" class="btn-second floatright" onclick="backToProfile()"  ><bean:message key="label.page.title.back.to.login"/> </a>
                    </td>
                    <td ></td>

                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </form>
    </div>
    <%--<div class="edit-per-info-right"><img src="images/img9.png" alt="Double pizza "/></div>--%>

</div>
<script type="text/javascript">
    function backToProfile(){
        window.location.href="<c:out value="${pageContext.request.contextPath}"/>/profile.do?operation=gotoMainPage";
    }


    function nospaces(t){
        if(t.value.match(/\s/g)){
            t.value=t.value.replace(/\s/g,'');
        }
    }

    $(document).ready(function () {
        /*jQuery.validator.addMethod("notSpace", function (value, element) {
         return value.indexOf(" ") > -1 && value != "";
         }, '<%--<bean:message key="message.registration.no.space" />--%>');
         */
        if(document.getElementById('passMessage').value == "wrong"){
            document.getElementById('successfulLabel').style.display = "none";
            document.getElementById('wrongLabel').style.display = "";
        }else if(document.getElementById('passMessage').value == "successfully"){
            document.getElementById('successfulLabel').style.display = "";
            document.getElementById('wrongLabel').style.display = "none";
        }

        $("#profileForm").validate({
            rules:{
                oldPassword:{required:true, minlength:5,equalTo:"#mainPassword"},
                newPassword:{required:true, minlength:5/*, notSpace:true*/},
                confirmPassword:{required:true, minlength:5/*, notSpace:true*/, equalTo:"#newPassword"}
            },
            messages:{
                oldPassword:{
                    required:'<bean:message key="message.registration.change.required" />',
                    minlength:'<bean:message key="message.registration.change.minlength"/>',
                    equalTo:'<bean:message key="message.registration.incorrect.password" />'
                },
                newPassword:{
                    required:'<bean:message key="message.registration.change.required" />',
                    minlength:'<bean:message key="message.registration.change.minlength"/>'
                },
                confirmPassword:{
                    equalTo:'<bean:message key="message.registration.change.confirm" />',
                    required:'<bean:message key="message.registration.change.required" />',
                    minlength:'<bean:message key="message.registration.change.minlength"/>'
                }
            }
        });
    });

</script>