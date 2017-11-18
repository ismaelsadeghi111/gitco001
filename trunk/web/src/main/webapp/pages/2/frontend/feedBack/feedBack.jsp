<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context= request.getContextPath();
%>


<h1 class="cusfood"  style="padding: 15px;"><bean:message key="label.page.footer.link.contact"/></h1>

<%--show errors--%>
<logic:messagesPresent message="false">
    <div class="error">
        <ol>
            <bean:message key="label.registration.errors"/> :
            <html:messages id="aMsg" message="false">
                <logic:present name="aMsg">
                    <li><bean:write name="aMsg" filter="false"/></li>
                </logic:present>
            </html:messages>
        </ol>
    </div>
</logic:messagesPresent>

<div class="LeftMiddleWrapper">

    <div class="clear" ></div>
    <div class="edit-per-info-left">
        <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr><td width="80%">&nbsp;</td></tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr>
                <td width="20%"><h2 style="font-size: 40px"><bean:message key="contactus.label.customer.service"/></h2>
            </tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr>
                <td><h3><bean:message key="contactus.label.phone.number"/></h3></td>

            </tr>
            <tr>
                <td><h3><html:link style="color: blue;text-decoration: none;border-bottom: 1px solid blue;" href="mailto:service@doublepizza.ca"><bean:message key="contactus.label.email"/></html:link></h3></td>
            </tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr><td width="80%">&nbsp;</td></tr>


            <tr>
                <td width="20%"><h2 style="font-size: 40px">
                <bean:message key="contactus.label.franchise.opportunities"/></h2>
            </tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr>
                <td><h3><html:link style="color: blue;text-decoration: none;border-bottom: 1px solid blue;" href="mailto:franchises@doublepizza.ca"><bean:message key="contactus.label.franchise.opportunities.email"/></html:link></h3></td>
            </tr>
            <tr>
                <td><h3><bean:message key="contactus.label.head.office"/></h3>
            </tr>
            <tr>
                <td><h3><bean:message key="contactus.label.head.office.add1"/></h3>
            </tr>
            <tr>
                <td><h3><bean:message key="contactus.label.head.office.add2"/></h3>
            </tr>
            <tr>
                <td><h3><bean:message key="contactus.label.head.office.add3"/></h3>
            </tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr><td width="80%">&nbsp;</td></tr>
            <tr><td width="80%">&nbsp;</td></tr>


        </table>
    </div>
    <%--<div class="edit-per-info-left">
        <form action="<%=context%>/feedBack.do?operation=makeFeedBack" method="post" class="std" id="feedBackForm"
                name="feedBackForm">
            <table class="font" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>
                <tr>
                    <td width="20%" class="font"><bean:message key="contactus.label.to.email"/> </td>
                   <td width="80%" class="text">
                       <div style="position: relative;left: 5px"><select name="toEmail" class="default-textbox" style="width: 191px">
                        <option value="customer" selected><bean:message key="contactus.label.customer.service"/></option>
                        <option value="franshise"><bean:message key="contactus.label.franchise.opportunities"/></option>
                    </select></div></td>
                </tr>
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"><bean:message key="label.name"/>:</td>
                    <td class="required text">
                        <input type="text" id="name" name="name" class="default-textbox"   type="text" style=""
                                <logic:present name="feedBackForm">
                                    value="${feedBackForm.name}"
                                </logic:present>
                                />
                        <sup class="required">*</sup>
                    </td>
                </tr>
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="font"> <bean:message key="lable.registration.email"/>:</td>
                    <td  class="required text">
                        <input type="text" id="email" name="email"   class="default-textbox"
                                <logic:present name="feedBackForm">
                                    value="${feedBackForm.email}"
                                </logic:present>
                                />
                        <sup class="required">*</sup>
                    </td>
                </tr>
                <tr>
                    <td width="20%">&nbsp;</td>
                    <td width="80%">&nbsp;</td>
                </tr>

                <tr>
                    <td class="font" valign="top"><bean:message key="label.message"/>:</td>
                    <td><div style="position: relative;left: 5px"><textarea id="message" name="message" class="send-order-textarea" style="float:left;  resize: none;"
                            <logic:present name="feedBackForm">

                            </logic:present>
                            >${feedBackForm.message}</textarea>
                        <sup style="float:left; margin:-3px 0 0 4px;" class="required">*</sup></div>
                    </td>

                </tr>
                <tr>
                    <td>

                    </td>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td></td>
                    <td class="font">
                        <a href="javascript: void(0);" class="btn-second"  onclick="window.location = '<%=context%>/frontend.do';" ><bean:message key="label.cancel"/></a>
                        <a   href="javascript: void(0);" class="btn-first"  onclick="document.getElementById('feedBackForm').submit()"><bean:message key="label.send"/></a>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </form>
    </div>--%>
</div>
