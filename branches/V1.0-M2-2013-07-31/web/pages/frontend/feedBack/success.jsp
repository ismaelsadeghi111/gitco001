<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
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
            <!-- Breadcrumb -->
            <h5><bean:message key="label.page.footer.link.contact"/></h5>
            
            <img src="<%=context%>/img/success.png" align="middle" style="float:left;" alt=""><br>
            <h4 id="success-message"><bean:message key="message.feedback.success.to.send"/></h4>

            <ul class="footer_links">
                <li><a href="<%=context%>/frontend.do" title="Home"><img src="<%=context%>/img/icon/home.png"
                        alt="Home" class="icon"></a><a href="<%=context%>/frontend.do" title="Home"><bean:message key="label.page.menu.home" />
                </a>
                </li>
            </ul>

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
                            <span id="cart_block_total" class="price ajax_block_cart_total"><%=CurrencyUtils.toMoney(totalPrice)%></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br class="clear">
    </div>
</div>
