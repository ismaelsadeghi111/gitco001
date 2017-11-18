
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.core.helpers.CurrencyUtils" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.math.BigDecimal" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>


<%
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
    String context = request.getContextPath();
    List<Popular> popularList = (List<Popular>) request.getAttribute("popularSpecialList");
    List<Popular> currentPopulars = (List<Popular>) request.getAttribute("currentPopulars");
    List<PopularCategory> menu_special = (List<PopularCategory>) request.getAttribute("allMenuItemList");
    String searchFromAt = (String) request.getAttribute("searchFromAt");
    String searchToAt = (String) request.getAttribute("searchToAt");
    String searchFoodNameAt = (String) request.getAttribute("searchFoodNameAt");
    if(searchFromAt==""){searchFromAt=null;}
    if(searchFoodNameAt==""){searchFoodNameAt=null;}
    DecimalFormat df=new DecimalFormat();
    df.applyPattern("###,###");
%>
<script src="<%=context%>/js/jquery/jquery.js"></script>
<script src="<%=context%>/js/jquery/jquery.datetimepicker.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datetimepicker.css"/>
<div id="admin-right" class="admin-right2">

<div class="admin-table">
    <form action="<c:out value="${pageContext.request.contextPath}"/>/popular.do?operation=apply" method="post"
          name="popularForm" id="popularForm">
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td  class=""><span
                        class="admin-table-heading">Selected popular for frontend </span></td>

            </tr>
            <tr>
                <td class="Compaign-form">Current popular items</td>
            </tr>
            <tr>
                <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <%            for (Popular mcurrentPopular :currentPopulars) {

                    %>


                        <td class="Compaign-form" style="padding-left:15px;" width="23%" align="left">
                            <div class="current-image-box active"><img width="176" height="173"
                                                                       src="<%=request.getContextPath()%><%=middlePath%><%=mcurrentPopular.getImageUrl()%>"
                                                                       alt="Double pizza"/>
                            </div>
                        </td>

                   <%}%>
                </tr>
                <tr>
                <%            for (Popular mcurrentPopular :currentPopulars) {

                %>

                <td class="Compaign-form" style="padding-left:15px;" width="23%"
                    align="left"><%=mcurrentPopular.getPriority()%>)&nbsp;<%=mcurrentPopular.getFoodName(Locale.ENGLISH)%><br/>
                    <%=df.format((Integer) mcurrentPopular.getQuantity())%> Times Orderd
                </td>
                <%}%>
                </tr>
            </table>
                </td>
             </tr>

            <tr>
                <td ><span class="admin-table-heading orange_color"></span>
                </td>
            </tr>
            <tr>
                <td class="Compaign-form" id="Futureitems">Future popular items</td>
            </tr>
                <tr>
                    <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="choosePopular">
                        <tr>
                            <td class="Compaign-form" style="padding-left:15px;" width="23%" align="left">
                                <%--<div class="current-image-box active">--%>
                                <div class="current-image-box active" id="box1" onclick="activateClass(this);">
                                    <input type="hidden" name="menuitemId1" id="box1ID"/>
                                    <input type="hidden" name="foodName1" id="box1FoodNameInput"/>
                                    <input type="hidden" name="imageUrl1" id="box1ImageUrl"/>
                                    <input type="hidden" name="quantity1" id="box1QuantityInput"/>
                                    <input type="hidden" name="groupId1" id="box1GroupId"/>
                                    <input type="hidden" name="categoryId1" id="box1CategoryId"/>
                                    <input type="hidden" name="priority1" id="priority1" value="1"/>
                                    <img width="176" height="173" src="" alt="Not selected"/>

                                </div>
                            </td>
                            <td width="4%" align="center">&nbsp;</td>
                            <td class="Compaign-form" width="22%" align="left">
                                <div class="current-image-box" id="box2" onclick="activateClass(this);">
                                    <input type="hidden" name="menuitemId2" id="box2ID"/>
                                    <input type="hidden" name="foodName2" id="box2FoodNameInput"/>
                                    <input type="hidden" name="imageUrl2" id="box2ImageUrl"/>
                                    <input type="hidden" name="quantity2" id="box2QuantityInput"/>
                                    <input type="hidden" name="groupId2" id="box2GroupId"/>
                                    <input type="hidden" name="categoryId2" id="box2CategoryId"/>
                                    <input type="hidden" name="priority2" id="priority2" value="2"/>
                                    <img width="176" height="173" src="" alt="Not selected"/>
                                </div>
                            </td>
                            <td width="3%" align="center">&nbsp;</td>
                            <td class="Compaign-form" width="22%" align="left">
                                <div class="current-image-box" id="box3" onclick="activateClass(this);">
                                    <input type="hidden" name="menuitemId3" id="box3ID"/>
                                    <input type="hidden" name="foodName3" id="box3FoodNameInput"/>
                                    <input type="hidden" name="imageUrl3" id="box3ImageUrl"/>
                                    <input type="hidden" name="quantity3" id="box3QuantityInput"/>
                                    <input type="hidden" name="groupId3" id="box3GroupId"/>
                                    <input type="hidden" name="categoryId3" id="box3CategoryId"/>
                                    <input type="hidden" name="priority3" id="priority3" value="3"/>
                                    <img width="176" height="173" src="" alt="Not selected"/>
                                </div>
                            </td>
                            <td width="3%" align="center">&nbsp;</td>
                            <td class="Compaign-form" width="23%" align="left">
                                <div class="current-image-box" id="box4" onclick="activateClass(this);">
                                    <input type="hidden" name="menuitemId4" id="box4ID"/>
                                    <input type="hidden" name="foodName4" id="box4FoodNameInput"/>
                                    <input type="hidden" name="imageUrl4" id="box4ImageUrl"/>
                                    <input type="hidden" name="quantity4" id="box4QuantityInput"/>
                                    <input type="hidden" name="groupId4" id="box4GroupId"/>
                                    <input type="hidden" name="categoryId4" id="box4CategoryId"/>
                                    <input type="hidden" name="priority4" id="priority4" value="4"/>
                                    <img width="176" height="173" src="" alt="Not selected"/>

                                </div>
                            </td>
                        </tr>

                        <tr>
                                <td style="padding-left:15px;" width="23%"
                                    align="left">1)&nbsp;<span  name="foodname1" id="box1FoodName"></span><br/>
                                    <span  name="quantity1" id="box1Quantity"></span>Times Orderd
                                </td>
                                <td width="4%" align="center">&nbsp;</td>
                                <td style="padding-left:15px;" width="23%"
                                    align="left">2)&nbsp;<span  name="foodname2" id="box2FoodName"></span><br/>
                                    <span  name="quantity2" id="box2Quantity"></span>Times Orderd
                                </td>
                                <td width="4%" align="center">&nbsp;</td>
                                <td style="padding-left:15px;" width="23%"
                                    align="left">3)&nbsp;<span  name="foodname3" id="box3FoodName"></span><br/>
                                    <span  name="quantity3" id="box3Quantity"></span>Times Orderd
                                </td>
                                <td width="4%" align="center">&nbsp;</td>
                                <td style="padding-left:15px;" width="23%"
                                    align="left">4)&nbsp;<span  name="foodname4" id="box4FoodName"></span><br/>
                                    <span  name="quantity4" id="box4Quantity"></span>Times Orderd
                                </td>
                                <td width="4%" align="center">&nbsp;</td>
                        </tr>
                    </table>
                    </td>
                    </tr>
         </table>

<%--
    <div class="admin-table">
        <table  width="100%">
            <tr style="background:url(/images/reload-bg.png) repeat;">


                <td colspan="7" height="40" class="font3" style="color:#fff; padding-left:10px;">
                    <br>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="20%"></td>
                            <td width="15%"></td>
                            <td width="7%" align="center">To</td>
                            <td width="15%"></td>
                            <td width="15%"><a href="#" onclick="searchPopularsByDate();"
                                               class="admin-btn FloatLeft">Apply</a></td>
                        </tr>

                  </table>
                    <br><br class="clear">
                        <table width="100%" border="1" cellspacing="0" cellpadding="0">
                            <thead>
                            <tr class="admin-table">
                            <td width="9%" align="center">Rank</td>
                            <td width="15%" style="padding:0px;" align="center">Image</td>
                            <td width="20%" align="center">Title</td>
                            <td width="34%" align="center">Number of Order</td>
                        </tr>
                            </thead>

                            <%
                                int index = 1;
                                if (popularList != null) {
                                    for (Popular menuItem : popularList) {

                            %>


                            <tr  class="admin-table">
                                <td height="75" width="9%" align="center"><%=index++%></td>
                                <td width="15%" style="padding:0px;" align="center">
                                    <img width="70" height="70"
                                         title="<%=menuItem.getDescriptionEn()%>"
                                         src="<%=request.getContextPath()%><%=middlePath%><%= menuItem.getImageUrl()%>"
                                         alt="<%=menuItem.getImageUrl()%>"/>

                                </td>
                                <td width="40%" align="center">
                                    <%=menuItem.getDescriptionEn()%>

                                </td>
                                <td width="24%" align="center"><%=menuItem.getQuantity()%>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                    </table>
                </td>
            </tr>
        </table>

</div>
--%>
    <div class="clear"></div>
    <br>
    <table>
        <tr>
            <td width="46%"></td>
            <td width="9%">
                <a class="btn-first"
                   href="javascript: void(0);" onclick="
                                     var  x1 = document.getElementById('box1FoodNameInput').value;
                                     var  x2 = document.getElementById('box2FoodNameInput').value;
                                     var  x3 = document.getElementById('box3FoodNameInput').value;
                                     var  x4 = document.getElementById('box4FoodNameInput').value;
                                     if (x1 != '' && x2 != '' && x3 != '' && x4 != '') {document.getElementById('popularForm').submit();}">Apply Changes</a>
            </td>

            <td width="2%">
                <a class="btn-second"
                   href="<c:out value="${pageContext.request.contextPath}"/>/popular.do">Reset</a>
            </td>
        </tr>
    </table>
    </form>
    <br>
<div class="admin-table">

    <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="admin-table-heading">
            <td>
                You can search items based on a phrase or two dates or both.
            </td>
        </tr>
    </table>
    <hr>
    <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td>Text in Food Name&nbsp;<input id="searchFoodName" type="text" value="${searchFoodName}"
                         name="searchFoodName" onclick=""
                         class="default-textbox"/>
            </td>
            <td  width="8%">&nbsp;</td>
            <td>From&nbsp;<input type="text" class="default-textbox celander" style="width:170px;"   name="searchFrom"  id="searchFrom"/></td>
            <td>To&nbsp;<input type="text" class="default-textbox celander" style="width:170px;"   name="searchTo" id="searchTo"/></td>
            <td><span>
                <a style="margin-right:10px;" class="admin-btn FloatRight" onclick="searchByDate();"
                   href="javascript: void(0);">Search</a></span>
            </td>
        </tr>
       </table>

    <% if(searchFromAt!=null && searchToAt!=null && searchFoodNameAt==null){%>
    <hr>
    <br>
    <div class="font3">

        Found &nbsp;<span class="red"><%=menu_special.size()%></span>&nbsp;results  from&nbsp;<span class="red"><%=searchFromAt%></span>&nbsp;to&nbsp;<span class="red"><%=searchToAt%></span>
    </div>
    <br>
    <%}else if(searchFromAt==null && searchFoodNameAt!=null){%>
    <hr>
    <br>
    <div class="font3">
    Found &nbsp;<span class="red"><%=menu_special.size()%>&nbsp;</span> results containing the words: <span class="red"><%=searchFoodNameAt%></span>
    </div>
    <br>
    <%}else if(searchFromAt!=null && searchToAt!=null && searchFoodNameAt!=null){%>
    <hr>
    <br>
    <div class="font3">

        Found &nbsp;<span class="red"><%=menu_special.size()%></span>&nbsp;results  from&nbsp;<span class="red"><%=searchFromAt%></span>&nbsp;to&nbsp;<span class="red"><%=searchToAt%></span> that include the words: <span class="red"><%=searchFoodNameAt%></span>
    </div>
    <br>

    <% }%>
        <hr>
    <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr class="admin-data" >

            <td height="40" width="6%">Rank</td>
            <td width="10%" style="padding:0px;" align="center">Image</td>
            <td width="40%">Title</td>
            <td width="10%">Number of Order</td>
            <td width="10%" align="center" style="padding-left:0px;">option</td>
        </tr>
        <%
            Category category;
            String popDescription="";
            Integer menuItemId;
            Integer groupId;
            String preId="";
             int index = 1;
            if (menu_special != null) {
                for (PopularCategory menuItem : menu_special) {
                    if(!(menuItem.getProductNo().equalsIgnoreCase("0033"))){
                        menuItemId= Integer.parseInt(menuItem.getProductNo());
                        if((menuItemId>0)&&(menuItemId<10))preId="000"+menuItemId;
                        if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
                        if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;
                        groupId=menuItem.getGroupId();
                        CombinedMenuItem popularCombinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(menuItem.getProductNo(),groupId.toString());
                        Category popularCategory = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(menuItem.getProductNo()));
                        MenuSingleItem popularMenuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(preId, "");


                if(popularCombinedMenuItem!=null){
                    popDescription =popularCombinedMenuItem.getDescription(Locale.ENGLISH);

                }
                else if(popularMenuSingleItem!=null){
                    popDescription=popularMenuSingleItem.getDescription(Locale.ENGLISH);


                }
                else if(popularCategory!=null)   {
                    category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME,  Integer.valueOf(menuItem.getProductNo()));
                    if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                        popDescription=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getDescription(Locale.ENGLISH);
                    }   else if (category.getSubCategoryList().get(0).getObject() instanceof CombinedMenuItem){
                        popDescription=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getDescription(Locale.ENGLISH);
                    }

                }
        %> 

        <tr class="admin-data">
            <td height="75" width="6%"><%=index++%>
            </td>
            <td width="10%" style="padding:0px;" align="center">
                <img width="70" height="70"
                     title="<%=menuItem.getDescEn()%>"
                     src="<%=request.getContextPath()%><%=middlePath%><%= menuItem.getWebImage()%>"
                     alt="<%=menuItem.getWebImage()%>"/>

            </td>
            <td width="40%">
                <%=menuItem.getDescEn()%> <br>
                <%if(menuItemId==1){popDescription="Two medium 12 in. pizzas.";} %>
                <%if(menuItemId==0){popDescription="Two small 10 in. pizzas.";} %>
                <%if(popDescription!=""){%>
                <span class="gray">(<%=popDescription%>)</span>
                <%}%>


            </td>
            <td width="10%"><%= df.format((Integer)menuItem.getOrderCount())%>
            </td>
            <%--<%=CurrencyUtils.toMoney(InMemoryData.getCombinedRealPrice((CombinedMenuItem) combinedItem))%>--%>
            <%--<td width="24%"><%=menuItem.getClass()%>--%>
            <%--</td>--%>

            <td width="10%">
                <% menuItem.setDescEn(menuItem.getDescEn().replaceAll("\""," inch"));%>
                <% menuItem.setDescEn(menuItem.getDescEn().replaceAll("\''"," inch"));%>
                <a  href="#Futureitems" class="admin-btn FloatLeft"
                   onclick="replacePic(this,'<%=request.getContextPath()%><%=middlePath%><%=menuItem.getWebImage()%>',
                           '<%=menuItem.getProductNo()%>',
                           '<%= menuItem.getWebImage()%>',
                           '<%=menuItem.getDescEn()%>',
                           '<%= menuItem.getOrderCount()%>',
                           '<%=menuItem.getCategId()%>',
                           '<%=menuItem.getGroupId()%>'
                           );">

                    Replace</a>
            </td>
        </tr>
        <%
                    }
                }
            }
        %>
    </table>


</div>
<br>
<br>
</div>
</div>


<script type="text/javascript">

    $(document).ready(function () {
        var sFrom='<%=searchFromAt%>';
        var sTo='<%=searchToAt%>';
        var searchFrom = $('#searchFrom');
        var searchTo = $('#searchTo');
        var now = new Date();
        var dateval= now.getFullYear() + '/' +(now.getMonth() + 1) + '/' + now.getDate() + ' ' + now.getHours() + ':' + now.getMinutes();
        searchFrom.datetimepicker();

        searchTo.datetimepicker({value:dateval,step:10,maxDate:dateval});

        searchFrom.datetimepicker();
        activeBoxId = 'box1';
        $("div.current-image-box").each(function () {
            $(this).removeClass("current-image-box active");
            $(this).addClass("current-image-box");
        });

        $(element).addClass("current-image-box active");

    });

    var activeBoxId;
    function replacePic(element, imagePath, menuItemId, imageUrl, foodName, quantity, categoryId, groupId) {
//        alert(imagePath+'\n'+imageUrl+'\n'+menuItemId+'\n'+foodName+'\n'+quantity+'\n'+categoryId+'\n'+groupId);
//        alert(activeBoxId);
        var Quantity= document.getElementById(activeBoxId + "Quantity");
        var FoodName= document.getElementById(activeBoxId + "FoodName");
        Quantity.innerHTML=quantity+" ";
        FoodName.innerHTML=foodName+""
        $("#" + activeBoxId + " img").attr("src", imagePath);
        $("#" + activeBoxId + "ID").val(menuItemId);
        $("#" + activeBoxId + "ImageUrl").val(imageUrl);
        $("#" + activeBoxId + "FoodNameInput").val(foodName);
        $("#" + activeBoxId + "QuantityInput").val(quantity);
        $("#" + activeBoxId + "CategoryId").val(categoryId);
        $("#" + activeBoxId + "GroupId").val(groupId);
        $("#" + foodName4 ).val(foodName);
    }

    function activateClass(element) {
        activeBoxId = $(element).attr('id');
        $("div.current-image-box").each(function () {
            $(this).removeClass("current-image-box active");
            $(this).addClass("current-image-box");
        });

        $(element).addClass("current-image-box active");
    }

/*        function searchByDate() {
    $('#popularForm').attr("action", "<c:out value="${pageContext.request.contextPath}"/>/popular.do?operation=search");
            $('#popularForm').submit();
        }*/
    function searchByDate() {
        var searchFoodName= document.getElementById("searchFoodName");
        var searchFrom= document.getElementById("searchFrom");
        var searchTo= document.getElementById("searchTo");
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);

        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'search');

        var paramSearchFrom = document.createElement('input')
        paramSearchFrom.setAttribute('type', 'hidden');
        paramSearchFrom.setAttribute('name', 'searchFrom');
        paramSearchFrom.setAttribute('value', searchFrom.value);

        var paramSearchTo = document.createElement('input')
        paramSearchTo.setAttribute('type', 'hidden');
        paramSearchTo.setAttribute('name', 'searchTo');
        paramSearchTo.setAttribute('value', searchTo.value);

        var paramFoodNam = document.createElement('input')
        paramFoodNam.setAttribute('type', 'hidden');
        paramFoodNam.setAttribute('name', 'searchFoodName');
        paramFoodNam.setAttribute('value', searchFoodName.value);

        myForm.method = 'POST';
        myForm.action = '<%=context%>/popular.do';

        myForm.appendChild(operation);
        myForm.appendChild(paramSearchFrom);
        myForm.appendChild(paramSearchTo);
        myForm.appendChild(paramFoodNam);

        myForm.submit();
    }
    function searchPopularsByDate() {
        var searchFrom= document.getElementById("searchFrom");
        var searchTo= document.getElementById("searchTo");
        var myForm = document.createElement('form');
        document.body.appendChild(myForm);

        var operation = document.createElement('input');
        operation.setAttribute('type', 'hidden');
        operation.setAttribute('name', 'operation');
        operation.setAttribute('value', 'getPopularsByDate');

        var paramName = document.createElement('input')
        paramName.setAttribute('type', 'hidden');
        paramName.setAttribute('name', 'searchFrom');
        paramName.setAttribute('value', searchFrom.value);

        var paramName = document.createElement('input')
        paramName.setAttribute('type', 'hidden');
        paramName.setAttribute('name', 'searchTo');
        paramName.setAttribute('value', searchTo.value);

        myForm.method = 'POST';
        myForm.action = '<%=context%>/popular.do';

        myForm.appendChild(operation);
        myForm.appendChild(paramName);

        myForm.submit();
    }
    function addCommas(nStr)
    {
        nStr += '';
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        return x1 + x2;
    }

</script>