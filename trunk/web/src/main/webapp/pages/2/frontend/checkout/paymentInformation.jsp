<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
    int totalOfItems = basket.getNumberOfItems();
    BigDecimal totalPrice = basket.calculateTotalPrice();
%>

<div class="LeftMiddleWrapper">
    <p class="cusfood" style="padding: 15px;"><bean:message key="lable.payment.info"/> </p>

    <div class="font5" style="color: #f10b0a; padding: 15px;"> <img  src="<%=context%>/images/checkout.png"  style="margin-top:-1px;float:left;" alt="CheckOut" /><bean:message key="label.page.title.checkout"/> </div>
            <div id="center_column" class="center_column">
                <div id="center_column_inner2">
                    <form action="<%=context%>/finalcheckout.do?operation=checkoutBasket" method="post" id="paymentInfoForm"
                          name="paymentInfoForm" class="std">

                        <fieldset >

                            <br class="clear"/>
                            <p class="radio required">
                            <div class="font5"><bean:message key="lable.choose.your.payment.type"/>:</div>
                            <br class="clear"/>

                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="15%">&nbsp;</td>
                                    <td>
                                        <img  src="<%=context%>/images/cash.png"  alt="Cash" />
                                    </td>
                                    <td>
                                        <label class="button"  for="paymentType1">
                                            <input type="radio" name="paymentType" id="paymentType1" value="Cash" checked/>
                                            <span class="outer"><span class="inner"></span></span> <bean:message key="label.payment.cash"/>
                                        </label>
                                    </td>
                                    <td width="35%">&nbsp;</td>
                                    <td>
                                        <img  src="images/visa.png"  alt="Visa" />
                                    </td>
                                    <td>
                                        <label class="button" for="paymentType3">
                                            <input type="radio" name="paymentType" id="paymentType3" value="Visa" />
                                            <span class="outer"><span class="inner"></span></span> Visa Card
                                        </label>
                                    </td>
                                    <td width="15%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td width="10%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td width="10%">&nbsp;</td>
                                    <td>
                                        <img  src="images/debit.png"  alt="Debit" />
                                    </td>
                                    <td>
                                        <label class="button"  for="paymentType2" >
                                            <input type="radio" name="paymentType" id="paymentType2" value="Debit" />
                                            <span class="outer"><span class="inner"></span></span> Debit
                                        </label>
                                    </td>

                                    <td width="35%">&nbsp;</td>
                                    <td>
                                        <img  src="images/master.png"  alt="Debit" />
                                    </td>
                                    <td>
                                        <label class="button" for="paymentType4">
                                            <input type="radio" name="paymentType" id="paymentType4" value="Master" />
                                            <span class="outer"><span class="inner"></span></span> Master Card
                                        </label>
                                    </td>
                                    <td width="10%">&nbsp;</td>
                                </tr>
                            </table>


                            </p>

                            <div class="clear"></div>
                            				<br /><br />
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td width="20%">&nbsp;</td>
                                    <td width="80%">
                                        <a   href="javascript: void(0);" class="btn-first FloatRight" onclick="document.getElementById('paymentInfoForm').submit()"><bean:message key="button.checkout"/></a>
                                        <a   href="<%=context%>/frontend.do?operation=getMenuTypeItems&menuType=menu"
                                             onclick="setMenuType('menu');" class="btn-second  floatright"><bean:message key="button.continueShopping"/></a>
                                    </td>
                                </tr>

                            </table>
                        </fieldset>
                    </form>
                </div>
            </div>
    </div>

