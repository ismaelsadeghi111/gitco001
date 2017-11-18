<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/1/13
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    String context = request.getContextPath();
    List<Popular> popularItems = (List<Popular>) session.getAttribute("popularItems");
    List<Slide> slideItemstmp =(List<Slide>) session.getAttribute("sliderList");
    List<Slide> slideItems =  new ArrayList<Slide>();
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    String slidesMiddlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_SLIDES_PATH);
    Float todayDPDollarsPercentage = (Float) request.getSession().getAttribute("todayDPDollarsPercentage");
    List<Map<String,Float>> dpDollarsFirstHalfWeeklyList = new ArrayList<Map<String, Float>>();
    List<Map<String,Float>> dpDollarsSecondHalfWeeklyList = new ArrayList<Map<String, Float>>();
    List<Map<String,Float>> dpDollarsWeeklyList = (List<Map<String,Float>>) request.getSession().getAttribute("dpDollarsWeeklyList");
    if (dpDollarsWeeklyList != null && dpDollarsWeeklyList.size() > 0) {
        ListIterator dpDollarsWeeklyListIterator = dpDollarsWeeklyList.listIterator();
        while (dpDollarsWeeklyListIterator.hasNext()){
            if (dpDollarsWeeklyListIterator.nextIndex() <7){
                dpDollarsFirstHalfWeeklyList.add((Map<String, Float>) dpDollarsWeeklyListIterator.next());
            } else {
                dpDollarsSecondHalfWeeklyList.add((Map<String, Float>) dpDollarsWeeklyListIterator.next());
            }
        }
    }

%>
<!-- Middle Block Div Start Here -->
<div id="MiddleBlock">
<c:set var="enState" value='<%=! (session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE)) %>'/>
<c:set var="frState" value='<%=(session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRENCH) || session.getAttribute(Globals.LOCALE_KEY).equals(Locale.FRANCE))%>'/>

<!-- Slider Div Start Here -->
<%--<div id="slider"><div class="slider-ad"><img src="<%=context%>/images/slider-ad.png" alt="Double Pizza" /></div></div>--%>
<div class="" id="" style=" height: 465px;">
</div>

<div id="slider" >
    <div style="overflow:hidden; width:1211px; margin: 0px -7px; calc();">
        <div class="home_slide" >

            <%-- Slide 1 ===========================================================================================================================  --%>


            <%
                for(Slide slidetmp:slideItemstmp){
                    if(slidetmp.getStatus().equals(Boolean.TRUE)&&!(slidetmp.getImageURLEN().equalsIgnoreCase("")|| slidetmp.getImageURLFR().equalsIgnoreCase(""))){
                        slideItems.add(slidetmp);
                    }
                }
                for(int i=0;i<slideItems.size();i++){
                    CombinedMenuItem combinedMenuItem =null;
                    Category category =null;
                    MenuSingleItem menuSingleItem=null;
                    Slide slide=new Slide();
                    slide=slideItems.get(i);
                    Integer menuItemId;
                    Integer groupId;
                    String preId="";
                    menuItemId=Integer.parseInt(slide.getProductNo());
                    if((menuItemId>1)&&(menuItemId<10))preId="000"+menuItemId;
                    if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
                    if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
                    groupId=Integer.parseInt(slide.getGroupId());
                    combinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(slide.getProductNo(),slide.getGroupId().toString());
                    category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, menuItemId);
                    menuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(preId, "");
            %>

            <% if(slide.getImageURLFR()!=null || slide.getImageURLFR()!=null){ %>

            <%
                if (combinedMenuItem != null) {
            %>

            <div data-time="4000">
                <%if(slide.getExtraYrl()!=null && !slide.getExtraYrl().equalsIgnoreCase("")){%>
                <a href="<%=slide.getExtraYrl()%>">
                    <c:if test="${enState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>">
                    </c:if>
                    <c:if test="${frState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/fr/<%=slide.getImageURLFR()%>">
                    </c:if>
                </a>
                <%}else {%>
                <%if(combinedMenuItem.getProductNo().equalsIgnoreCase("722")){%>

                <a onclick="return gotoCustomizepage('combinedId', 'COMBINED','1<%=slide.getCatId()%>','<%=combinedMenuItem.getProductNo()%>','<%=combinedMenuItem.getGroupId()%>', '1');"
                   href="javascript: void(0);">
                    <c:if test="${enState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>">
                    </c:if>
                    <c:if test="${frState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/fr/<%=slide.getImageURLFR()%>">
                    </c:if>
                </a>
                <%}else{%>
                <a onclick="return gotoCustomizepage('combinedId', 'COMBINED','1<%=slide.getCatId()%>','<%=combinedMenuItem.getProductNo()%>','<%=combinedMenuItem.getGroupId()%>', '0');"
                   href="javascript: void(0);">
                    <c:if test="${enState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>">
                    </c:if>
                    <c:if test="${frState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/fr/<%=slide.getImageURLFR()%>">
                    </c:if>
                </a>
                <%}%>
                <%}%>
            </div>

            <% } else if (menuSingleItem != null) {
            %>

            <div data-time="4000">
                <a onclick="return   gotoCustomizepage('singleId','SINGLEMENUITEM','16','<%=preId%>', '',  '<%=slide.getGroupId()%>')"
                   href="javascript: void(0);">
                    <c:if test="${enState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>">
                    </c:if>
                    <c:if test="${frState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/fr/<%=slide.getImageURLFR()%>">
                    </c:if>

                </a>
            </div>

            <% } else if (category != null) {%>
            <div data-time="4000">
                <a onclick="return gotoCustomizepage('categoryId','CATEGORY','<%=slide.getProductNo()%>','<%=slide.getProductNo()%>', null,  '0')"
                   href="javascript: void(0);">
                    <c:if test="${enState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>">
                    </c:if>
                    <c:if test="${frState}">
                        <img src="<%=context%><%=slidesMiddlePath%>/fr/<%=slide.getImageURLFR()%>">
                    </c:if>
                </a>
            </div>
            <%}%>

            <%}%>

            <%}%>
            <%-- Slide 2 ===========================================================================================================================  --%>
            <%--
                        <div  data-time="4000">
                            <a onclick="return gotoCustomizepage('combinedId', 'COMBINED','5','296', '16', '0');"
                               href="javascript: void(0);">

                                <c:if test="${enState}">
                                    <img src="<%=context%>/images/slides/slideball.jpg">
                                </c:if>
                                <c:if test="${frState}">
                                    <img src="<%=context%>/images/slides/slideball.jpg">
                                </c:if>
                            </a>
                        </div>
            --%>

            <%-- Slide 3 ===========================================================================================================================  --%>
            <%--
                    <div data-thumb="<%=context%>/images/slides/thumbs/slide2.png" data-time="5000">
                        <a onclick="return gotoCustomizepage('combinedId', 'COMBINED','18','486', '4', '0');" title="" href="javascript: void(0);">

                            <c:if test="${enState}">
                                <img src="<%=context%>/images/slides/sideorder-en.jpg">
                            </c:if>
                            <c:if test="${frState}">
                                <img src="<%=context%>/images/slides/sideorder-fr.jpg">
                            </c:if>
                        </a>
                    </div>
            --%>
            <%-- Slide 4 ===========================================================================================================================  --%>
            <%--
                    <div data-thumb="<%=context%>/images/slides/thumbs/slide6.png" data-time="2000">

                        <a    onclick=" return gotoCustomizepage('combinedId', 'COMBINED','16','343', '4', '1');">
                            <c:if test="${enState}">
                                <img src="<%=context%>/images/slides/cookie-en.jpg">
                            </c:if>
                            <c:if test="${frState}">
                                <img src="<%=context%>/images/slides/cookie-fr.jpg">
                            </c:if>
                             </a>
                       </div>
            --%>
            <%-- Slide 4 ===========================================================================================================================  --%>
            <%--
                    <div data-thumb="<%=context%>/images/slides/thumbs/slide6.png" data-time="2000">

                        <a    onclick=" return gotoCustomizepage('combinedId', 'COMBINED','16','343', '4', '1');">
                            <c:if test="${enState}">
                                <img src="<%=context%>/images/slides/sideorder-en.jpg">
                            </c:if>
                            <c:if test="${frState}">
                                <img src="<%=context%>/images/slides/sideorder-fr.jpg">
                            </c:if>
                             </a>
                       </div>
            --%>

            <%--=========================================  --%>
        </div><!-- #home_slide -->
    </div>
</div>

<!-- Slider Div End Here -->
<h1 class="cusfood" id="popularItems" style="margin-top: -429px;margin-left: 36px;font-size: 26px; "><bean:message key="label.page.title.home.populars"/></h1>
<!-- Box Section Start Here -->


<%
    if (popularItems != null && popularItems.size() > 0){
%>
<div style="width:100%;height:auto;padding-left: 30px;margin-top: 20px">
    <%      Category category;
        String paramName="";
        String type="";
        Integer catId;
        Integer menuItemId;
        Integer groupId;
        Integer orderNum=0;
        String preId="";
        BigDecimal prico= BigDecimal.valueOf(0);
        for(Popular popular : popularItems){
            catId=popular.getCategoryId();
            menuItemId=popular.getMenuitemId();
            if((menuItemId>1)&&(menuItemId<10))preId="000"+menuItemId;
            if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
            if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
            groupId=popular.getGroupId();
            CombinedMenuItem popularCombinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(popular.getMenuitemId().toString(),popular.getGroupId().toString());
            Category popularCategory = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, popular.getMenuitemId());
            MenuSingleItem popularMenuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(preId, "");
            Double price = popular.getPrice();
            if(popularCombinedMenuItem!=null){
                paramName="combinedId";
                type="COMBINED";
                popular.setType(CombinedMenuItem.class);
                BigDecimal decimalPrice =  InMemoryData.getCombinedRealPrice(popularCombinedMenuItem);
                price = decimalPrice == null ? price : decimalPrice.doubleValue();
                price = CurrencyUtils.doubleRoundingFormat(price);
                popular.setPrice(price);
                prico=popularCombinedMenuItem.getPrice();
                price=CurrencyUtils.doubleRoundingFormat(prico);

            }
            else if(popularMenuSingleItem!=null){
                paramName="singleId";
                type="SINGLEMENUITEM";
                popular.setType(MenuSingleItem.class);


            }
            else if(popularCategory!=null)   {
                paramName="categoryId";
                type="CATEGORY";
                popular.setType(Category.class);
                category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, popular.getMenuitemId());
                if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                    prico=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }   else {
                    prico=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getPrice();
                }
                price=CurrencyUtils.doubleRoundingFormat(prico);


            }
    %>
    <div class="box">
        <div class="box-price"><span style="text-align:left;color:#20303c; font-size: 20px;"><bean:message key="label.from"/> </span>$<%=price%></div>
        <div class="box-img"><a href="#"><img style="margin-top: 15px;border-radius: 5px" height="183" width="237" src="<%=context%><%=middlePath%><%=popular.getImageUrl()%>" alt="Image" /></a></div>
        <a href="#" class="box-name"><%=popular.getFoodName((Locale) session.getAttribute(Globals.LOCALE_KEY))%></a>
        <div class="Clear"></div>
        <div class="box-des"><%=popular.getDescription((Locale) session.getAttribute(Globals.LOCALE_KEY))%></div>
        <div class="Clear"></div>
        <br>
        <%if(type.equalsIgnoreCase("SINGLEMENUITEM")){ %>
        <a style="float:none; " class="add-to-cart" onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','1<%=catId%>','<%=preId%>', '',  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
        <%}
        else if(type.equalsIgnoreCase("COMBINED")){%>
        <a style="float:none; " class="add-to-cart" onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','1<%=catId%>','<%=menuItemId%>', '<%=groupId%>',  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
        <%}else {%>
        <a style="float:none; " class="add-to-cart" onclick="gotoCustomizepage('<%=paramName%>','<%=type%>','<%=menuItemId%>','<%=menuItemId%>', null,  '<%=orderNum%>')" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>

        <%
            }
        %>
        <%--<div style="float: right;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>--%>
    </div>
    <%
        }
    %>
</div>
<%
    }
%>
<%--<div style="width:100%;height:auto;padding-left: 30px;">
    <div class="box">
        <div class="box-price"><span style="text-align:left;color:#20303c; font-size: 20px;"><bean:message key="label.from"/> </span>&nbsp;$18.99</div>
        <div class="box-img"><img src="<%=context%>/images/popular1.jpg" alt="Image" /></div>
        <a onclick="return gotoCustomizepage('combinedId', 'COMBINED','6','24', '0', '0');" href="javascript: void(0);" class="box-name"><bean:message key="popular1.name"/></a>

        <br>
        <span class="box-des"><bean:message key="popular1.des"/></span>
        <br><br>
        <a style="float:none; " class="add-to-cart" onclick="gotoCustomizepage('combinedId', 'COMBINED','6','24', '0', '0');" href="javascript: void(0);" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
         &lt;%&ndash;<div style="float: right;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>&ndash;%&gt;
    </div>
    <div class="box">
        <div class="box-price"><span style="text-align:left;color:#20303c; font-size: 20px;"><bean:message key="label.from"/></span>&nbsp;$15.99</div>
        <div class="box-img"><img src="<%=context%>/images/popular2.jpg" alt="Image" /></div>
        <a onclick="gotoCustomizepage('combinedId', 'COMBINED','11','1', '0', '1');" href="javascript: void(0);"  class="box-name"><bean:message key="popular2.name"/></a>
        <br><br>
        <span class="box-des"><bean:message key="popular2.des"/></span>
        <br><br>
        <a style="float:none; " class="add-to-cart" onclick="gotoCustomizepage('combinedId', 'COMBINED','11','1', '0', '1');" href="javascript: void(0);"  title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
         &lt;%&ndash;<div style="float: right;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>&ndash;%&gt;
    </div>
    <div class="box">
        <div class="box-price"><span style="text-align:left;color:#20303c; font-size: 20px;"><bean:message key="label.from"/></span>&nbsp;$9.99</div>
        <div class="box-img"><img src="<%=context%>/images/popular3.jpg" alt="Image" /></div>
        <a  onclick="gotoCustomizepage('combinedId', 'COMBINED','113','148', '0', '0');" href="javascript: void(0);"class="box-name"><bean:message key="popular3.name"/></a>
        <br><br>
        <span class="box-des"><bean:message key="popular3.des"/></span>
        <br><br>
        <a  onclick="gotoCustomizepage('combinedId', 'COMBINED','113','148', '0', '0');"href="javascript: void(0);" style="float:none; " class="add-to-cart" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
       &lt;%&ndash;<div style="float: right;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>&ndash;%&gt;
    </div>
    <div class="box">
        <div class="box-price" ><span style="text-align:left;color:#20303c; font-size: 20px;"><bean:message key="label.from"/></span>&nbsp;$24.99</div>
        <div class="box-img"><img width="237px" height="183px" src="<%=context%>/images/popular5.jpg" alt="Image" /></div>
        <a  onclick="gotoCustomizepage('combinedId', 'COMBINED','1','296', '16', '0');" href="javascript: void(0);"class="box-name">Coupe du Monde</a>
        <br>
        <span class="box-des">(Deux pizzas moyennes 12 po + Petite frites + Un ballon de soccer.)</span>
        <br><br>
        <a onclick="gotoCustomizepage('combinedId', 'COMBINED','1','296', '16', '0');" href="javascript: void(0);" style="float:none; " class="add-to-cart" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
       &lt;%&ndash;<div style="float: right;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>&ndash;%&gt;
    </div>
    <br>
    gotoCustomizepage(paramName, type, catId, id, groupId, orderNum) <br>
    gotoCustomizepage('combinedId', 'COMBINED','18','486', '4', '1')<br>
    <a onclick="gotoCustomizepage('combinedId', 'COMBINED','18','486', '4', '1');" href="javascript: void(0);" style="float:none; " class="add-to-cart" title="<bean:message key="label.customizeIt"/>"><bean:message key="label.customizeIt"/></a>
</div>--%>

<!-- Box Section Start Here -->
</div>
<!-- Middle Block Div End Here -->

<script>
    $(function(){
        $('.home_slide').diapo({fx:'fade',loader:'none',thumbs:'false',navigation:'false'});
    });
</script>