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
    Basket basket= (Basket)request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems=basket.getNumberOfItems();
    BigDecimal totalPrice= basket.calculateTotalPrice();
%>


<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="center_column">
        <div id="center_column_inner2">
            <h5><bean:message key="label.page.footer.link.contact" /></h5>

            <%--show errors--%>
            <logic:messagesPresent message="false">
                <div class="error">
                    <ol>
                        <bean:message key="label.registration.errors"/> :
                        <html:messages id="aMsg" message="false">
                            <logic:present name="aMsg">
                                <li><bean:write name="aMsg" filter="false" /></li>
                            </logic:present>
                        </html:messages>
                    </ol>
                </div>
            </logic:messagesPresent>

            <form action="<%=context%>/feedBack.do?operation=makeFeedBack" method="post" class="std" id="feedBackForm" name="feedBackForm">
                <p style="margin-left:0px; font-size:16px; font-weight:bold;">
                    <bean:message key="message.contactUs"/>
                </p>

                <fieldset>
                    <h3><bean:message key="label.feedback.send.message"/></h3>
                    <p id="desc_contact0" class="desc_contact">&nbsp;</p>
                    <p id="desc_contact2" class="desc_contact" style="display:none;">
                    </p>

                    <p id="desc_contact1" class="desc_contact" style="display:none;">

                    </p>
                    <p class="text">
                        <label for="name"><bean:message key="label.name"/></label>
                        <input type="text" id="name" name="name"

                                <logic:present name="feedBackForm">
                                    value="${feedBackForm.name}"
                                </logic:present>

                                />
                    </p>
                    <p class="required text">
                        <label for="email"><bean:message key="lable.registration.email"/></label>
                        <input type="text" id="email" name="email"

                                <logic:present name="feedBackForm">
                                    value="${feedBackForm.email}"
                                </logic:present>

                                />
                        <sup>*</sup>
                    </p>
                    <p class="required textarea">

                        <label for="message"><bean:message key="label.message"/></label>
                        <textarea id="message" name="message" rows="15" cols="20">${feedBackForm.message}</textarea>
                        <sup>*</sup>
                    </p>
                    <p class="submit">
                        <input style="display:inline-block;width:160px;" type="submit" name="submitMessage" id="submitMessage" value="<bean:message key="label.send"/>" class="exclusive" />
                        <input style="display:inline-block; width:160px;" name="goHome" id="goHome" value="<bean:message key="button.home"/>" class="exclusive" type="button" onclick="window.location = '<%=context%>/frontend.do';">
                    </p>
                </fieldset>
            </form>
        </div>
    </div>
    <!-- Right -->
    <div id="right_column" class="column carousel_on">
        <!-- MODULE Block cart -->
        <div id="cart_block" class="block exclusive">
            <h4>
                <bean:message key="lable.cart"/>
                <span id="block_cart_expand" class="hidden">&nbsp;</span>
                <span id="block_cart_collapse">&nbsp;</span>
            </h4>
            <div class="block_content2">
                <div id="cart_block_list" class="expanded">
                    <div class="cart-prices">
                        <div class="cart-prices-block">
                            <span><bean:message key="label.item.no"/></span>
                            <span id="cart_block_shipping_cost" class="price ajax_cart_shipping_cost"> <%=totalOfItems%></span>
                        </div>

                        <div class="cart-prices-block">
                            <span><bean:message key="lable.total.price"/> </span>
                            <span id="cart_block_total" class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice))%></span>
                        </div>
                    </div>

                    <p id="cart-price-precisions">
                        <bean:message key="message.price.with.tax"/>

                    </p>

                </div>
            </div>
        </div>
        <!-- /MODULE Block cart -->
    </div>
    <div id="right_column_continue" >
        <ul>
            <li>
                <a class="two_chef" title="<bean:message key="label.page.footer.link.franchise"/>" href="<%=request.getContextPath()%>/frontend.do?operation=goToFranchisingPage">
                </a>
            </li>
        </ul>

    </div>
    <br class="clear">
</div>
