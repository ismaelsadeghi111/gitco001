<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<div id="columns2">
    <!-- Center -->
    <div id="center_column" class="center_column">
        <div id="center_column_inner2">
            <div class="rte">
                <h5><bean:message key="lable.fun.zone"/></h5>
                <li class="static_page_content">
                    <img class="static_page_image" src="<%=request.getContextPath()%>/img/about-us.png" alt="doublepizza"/>
                    <bean:message key="body.fun.zone" />
                </li>
                <p></p>
            </div>
        </div>
    </div>

    <!-- Right -->
    <div id="right_column" class="column carousel_on">
        <script type="text/javascript">
            var CUSTOMIZE_TEXTFIELD = 1;
            var customizationIdMessage = 'Customization #';
            var removingLinkText = 'remove this product from my cart';
        </script>
        <!-- MODULE Block cart -->
        <div id="cart_block" class="block exclusive">
            <h4>
                <bean:message key="lable.follow.us"/>
                <span id="block_cart_expand" class="hidden">&nbsp;</span>
                <span id="block_cart_collapse">&nbsp;</span>
            </h4>
            <div class="block_content">
                <!-- block summary -->
                <div id="cart_block_summary" class="collapsed">

                </div>
                <!-- block list of products -->
                <div id="cart_block_list" class="expanded">
                    <p>&nbsp;</p>
                    <ul>
                       <bean:message key="lable.follow.us.in.social.network"/>
                        <p> &nbsp; </p>
                        <li class="first_item" style="margin-bottom:4px;">
                            <img class="static_page_icon" src="<%=request.getContextPath()%>/img/facebook.png" alt="Facebook"/>
                            <a class="static_page_link" href="http://www.facebook.com/pages/Double-pizza/162092927142348"
                               style="font-size:19px; padding-top:22px;">
                                &nbsp; <bean:message key="label.page.footer.link.facebook"/>
                            </a>
                        </li>
                        <li class="first_item" style="margin-bottom:4px;">
                        <img class="static_page_icon" src="<%=request.getContextPath()%>/img/twitter.png" alt="Twitter"/>
                            <a class="static_page_link" href="http://twitter.com/dbpizzamtl"
                               style="font-size:19px; padding-top:22px;">
                                &nbsp;   <bean:message key="label.page.footer.link.twitter"/>
                            </a>
                        </li>
                        <li class="first_item" style="margin-bottom:4px;">
                            <img class="static_page_icon" src="<%=request.getContextPath()%>/img/youtube.png" alt="YouTube"/>
                            <a class="static_page_link" href="http://www.youtube.com/user/Doublepizzamtl"
                               style="font-size:19px; padding-top:22px;">
                                &nbsp;   <bean:message key="label.page.footer.link.youtube"/>
                            </a>
                        </li>
                    </ul>
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
