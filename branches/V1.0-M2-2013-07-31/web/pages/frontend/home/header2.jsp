<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.model.Slider" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.sefryek.doublepizza.model.TaxHandler" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: ehsan
  Date: Jan 25, 2012
  Time: 12:14:49 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String context = request.getContextPath();
    String middlepath1 = InMemoryData.getImageServicePath("slides");
%>

<div id="slide_holder">
    <div id="slider">
        <a href='#'><img src='<%=context%><%=middlepath1%>slide_00.png' alt="" title="#slider-caption-0" /></a>
        <a href='#'><img src='<%=context%><%=middlepath1%>slide_01.png' alt="" title="#slider-caption-1" /></a>
        <a href='#'><img src='<%=context%><%=middlepath1%>slide_02.png' alt="" title="#slider-caption-2" /></a>
    </div>
    <c:if test="${!empty requestScope.sliderList}">
        <c:forEach items="${requestScope.sliderList}" begin="0" var="slider" varStatus="curIndex">
            <%
                Slider slider = (Slider)pageContext.getAttribute("slider");
            %>
            <div id="slider-caption-${curIndex.index}" class="nivo-html-caption">
                <c:if test="${!empty slider.price}">
                    <span class="slider-price"><%= CurrencyUtils.getMoneyWholePart(slider.getPrice()) %>
                        <%--<span class="slider-price-decimal">.</span>--%>
                        <span class="slider-price-decimal" style="top:-14px; margin-left: 1px;"><%= CurrencyUtils.getMoneyFractionalPart(slider.getPrice()) %></span>
                        <%--<span class="slider-dollarsign">$</span>--%>
                    </span><br>
                </c:if>
                <span class="slider-firsttext">${slider.firstText}</span><br>
                <span class="slider-secondtext">${slider.secondText}</span><br>
                <span class="slider-thirdtext">${slider.thirdText}</span>
                <script type="text/javascript">
                    $('#slider a')[${curIndex.index}].href="${slider.link}";
                </script>
            </div>
        </c:forEach>
    </c:if>

</div>

<div id="menu_sp">
    <a  href="javascript:void(0);"  class="menu_btn" title="menu" onclick=" getMenuTypeItems('menu', '11'); resetToDefaultSelection('menu');"> </a>
    <a  href="javascript:void(0);" class="special_btn"  title="special" onclick="getMenuTypeItems('special', '1');resetToDefaultSelection('special'); ">  </a>
    <%
        Basket basket = (Basket) request.getSession().getAttribute(Constant.BASKET);
        BigDecimal totalPrice = null;
        Integer basketItemsCount = 0;
        boolean isItemInBasket = basket != null && basket.getNumberOfItems() > 0;
        if (isItemInBasket){
            totalPrice = basket.calculateTotalPrice();
            basketItemsCount = basket.getNumberOfItems();
        }
    %>
    <c:if test="<%=isItemInBasket%>">
        <a href="<%=context%>/checkout.do?operation=goToCheckoutPage" class="cart_btn" title="<bean:message key="lable.cart"/>">
            <span style="font-weight:normal; top:2px;"><%= basketItemsCount %> <bean:message key="label.page.basket.items"/></span>
            <span style="font-weight:bold; font-size:18px; top:3px;"><%= CurrencyUtils.toMoney(new TaxHandler().getTotalPriceWithTax(totalPrice)) %></span>
        </a>        
    </c:if>
</div>

<script type="text/javascript">
    function ScrollToOnlineOrder(){
        var new_position = $('#homecarousel').offset();
        $('html,body').animate({scrollTop: new_position.top - 130},'slow');
    }
        
    $(window).load(function() {
        
        $('#slider').nivoSlider({
            effect:'fade',
            slices:9,
            animSpeed:500,
            pauseTime:5000,
            startSlide:0,
            directionNav:false,
            directionNavHide:false,
            controlNav:true,
            controlNavThumbs:false,
            controlNavThumbsFromRel:false,
            controlNavThumbsSearch: '.jpg',
            controlNavThumbsReplace: '_thumb.jpg',
            keyboardNav:true,
            pauseOnHover:true,
            manualAdvance:false,
            captionOpacity:0.8,
            beforeChange: function(){},
            afterChange: function(){},
            slideshowEnd: function(){}
        });
        <c:if test="${!empty param.toOnlineOrder}">
            ScrollToOnlineOrder();
        </c:if>

        var homeAlert = getCookie("homeAlert");
        if (homeAlert == "fromCustomize"){
            setCookie("homeAlert", "", 0);
            tAlert('<bean:message key='message.info.add.item.success'/>', '', 'success', 5000);
            ScrollToOnlineOrder();
        }
        if (homeAlert == "fromDelete"){
            setCookie("homeAlert", "", 0);
            tAlert('<bean:message key="message.successfully.deleted.item.from.basket"/>', '', 'success', 5000);
            ScrollToOnlineOrder();
        }

        <c:if test='<%=Boolean.parseBoolean(request.getParameter("fromContinueShopping"))%>'>
            ScrollToOnlineOrder();
        </c:if>

        if (homeAlert == "fromEmptyCart"){
            setCookie("homeAlert", "", 0);
            tAlert('<bean:message key="message.info.add.more.info"/>', '', 'success', 5000);
            ScrollToOnlineOrder();
        }

        if (homeAlert == "fromInvalidRequest"){
            setCookie("homeAlert", "", 0);
            tAlert('<bean:message key="message.error.invalid.request"/>', '', 'error', 50000);
        }

        

    });
   
</script>


