
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
    String slidesMiddlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_SLIDES_PATH);

    String context = request.getContextPath();
    List<Popular> popularList = (List<Popular>) request.getAttribute("popularSpecialList");
    List<Popular> currentPopulars = (List<Popular>) request.getAttribute("currentPopulars");
    List<Slide> slideItems = (List<Slide>) request.getAttribute("sliderList");
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

<link rel="stylesheet" href="<%=context%>/css/zebra_dialog.css" type="text/css">
<script type="text/javascript" src="<%=context%>/js/jquery/zebra_dialog.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context%>/css/jquery.datetimepicker.css"/>
<div id="admin-right" class="admin-right2">
<style>
    .accordion {
        font-family:Arial, Helvetica, sans-serif;
        margin:0 auto;
        font-size:14px;
        border:1px solid #542437;


        padding:10px;
        background:#fff;
    }
    .accordion ul {
        list-style:none;
        margin:0;
        padding:0;
    }
    .accordion li {
        margin:0;
        padding:0;
    }
    .accordion [type=radio], .accordion [type=checkbox] {
        display:none;
    }
    .accordion label {

        display:block;
        font-size:16px;
        line-height:16px;
        background:#FFFFF0;
        color:#542437;
        text-shadow:1px 1px 1px rgba(255,255,255,0.3);
        font-weight:700;
        cursor:pointer;

    }
    .accordion ul li label:hover, .accordion [type=radio]:checked ~ label, .accordion [type=checkbox]:checked ~ label {
        background:#C02942;
        color:#FFF;
        text-shadow:1px 1px 1px rgba(0,0,0,0.5)
    }
    .accordion .content {
        padding:0 10px;
        overflow:hidden;
        border:1px solid #fff;
        -webkit-transition: all .5s ease-out;
        -moz-transition: all .5s ease-out;
    }
    .accordion p {
        color:#333;
        margin:0 0 10px;
    }
    .accordion h3 {
        color:#542437;
        padding:0;
        margin:10px 0;
    }



    .vertical ul li {
        overflow:hidden;
        margin:0 0 1px;
    }
    .vertical ul li label {
        padding:10px;
    }
    .vertical [type=radio]:checked ~ label, .vertical [type=checkbox]:checked ~ label {
        border-bottom:0;
    }
    .vertical ul li label:hover {
        border:1px solid #542437;
    }
    .vertical ul li .content {
        height:0px;
        border-top:0;
        transition: all .5s ease-out;
    }
    .vertical [type=checkbox]:checked ~ label ~ .content {
        height:330px;
        border:1px solid #542437;
    }
</style>

<div class="admin-table">

    <form action="<c:out value="${pageContext.request.contextPath}"/>/slide.do?operation=apply" method="post"
          name="sliderForm" id="sliderForm">
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td  class=""><span
                        class="admin-table-heading">Selected slides for slide show in home page </span></td>
            </tr>
            <tr>
                <td>
                    <div>
                        <div class="accordion vertical">
                            <ul>
                                <li>
                                    <input type="checkbox" id="checkbox-1" name="checkbox-accordion" />
                                    <label for="checkbox-1">Help&nbsp;<span class="font4">(click here to show/hide guide)</span></label>

                                    <div class="content">
                                        <p>
                                            <br>
                                            <b> 1-You must copy slide image files (1200x478 pixel) to server
                                                (/home/doublepizza/home-data/slides) </b> <br>
                                            Select images and put to slides folder <br>
                                            [/slides] <br>
                                            | <br>
                                            |_[/en] For English language <br>
                                            | <br>
                                            |_[/fr] For French language <br>
                                            <br> <b>
                                            2-Update slides from control panel [here] that needs 4 steps: </b> <br>
                                            <span style="margin-left:3em;">
                                        1-Click on "Search" button for show menuitems  <br>    </span>
                                                 <span style="margin-left:3em;">
                                        2-Select your slide for update menu item link then click on slide image.  <br>  </span>
                                                      <span style="margin-left:3em;">
                                        3-Click on the "Replace" button item's that you're going to replace it. <br></span>
                                                           <span style="margin-left:3em;">
                                        4-Click on "Apply Changes" button to save and update your changes. <br>
                                                </span>
                                            <br><br>
                                            <b>How to disable a slide?</b><br>
                                            Click on Change status option box and select "NotActive" then click "Apply
                                            Changes" button. <br>
                                            <br>
                                            <b>
                                                How to assign an external link to a slide? </b> <br>
                                            You can enter your link in URL textbox below of slide image.
                                            <br>
                                        </p>
                                    </div>
                                </li>
                                </ul>
                            </div>

                    </div>

                </td>
            </tr>
            <tr>
                <td class="Compaign-form">Current slides </td>
            </tr>
            <tr>
                <td>

            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <%
                        for (int i=0;i<slideItems.size();i++) {
                            Slide slide=new Slide();
                            slide=slideItems.get(i);
                            if((i) % 3==0 )
                            {
                    %>
                     </tr>
                     <tr >
                         <%}%>

                        <td class="Compaign-form" style="padding-bottom:35px;" width="23%"  align="left" >
                            <span class="gray">Slide <%=i+1%></span>
                            <%if(slide.getStatus().equals(Boolean.TRUE)){%>
                            <span class="green">(Active)</span>
                            <%}else {%>
                            <span class="red">(Not Active)</span>
                            <%}%>
                            <br>
                            <label>Change status:</label>
                            <select  name="status<%=i+1%>"  onchange="document.getElementById('box<%=i+1%>isupdate').value='yes'">
                                <option value=""></option>
                                <option value="Active">Active</option>
                                <option value="NotActive" >Not Active</option>
                            </select>
                            <br>
                            <hr>
                            <div class="font3" style="top: 10px"> <%=slide.getWebDescEN()%></div>
                            <div class="current-slide-box active" onclick="activateClass(this);"  id="box<%=i+1%>" ><img width="240" height="93"
                                                                       src="<%=request.getContextPath()%><%=slidesMiddlePath%>/en/<%=slide.getImageURLEN()%>"/>

                                <input  id="box<%=i+1%>isupdate" type="hidden" value="" name="isupdate<%=i+1%>" />
                                <input  id="box<%=i+1%>SID" type="hidden" value="<%=slide.getId()%>" name="slideId<%=i+1%>" />
                                <input  id="box<%=i+1%>ID" type="hidden"  value="<%=slide.getProductNo()%>" name="menuitemId<%=i+1%>" />
                                <input  id="box<%=i+1%>FoodNameInput"  value="" type="hidden" name="title<%=i+1%>"/>
                                <input  id="box<%=i+1%>CategoryId"  value="<%=slide.getCatId()%>" type="hidden" name="categoryId<%=i+1%>" />
                                <input  id="box<%=i+1%>GroupId"  value="<%=slide.getGroupId()%>" type="hidden" name="groupId<%=i+1%>"/>
                                <br>
                                <label>URL:</label><input class="default-textbox" style="margin-top: 20px;" id="box<%=i+1%>Url"  value="<%=slide.getExtraYrl()%>" type="text" name="url<%=i+1%>"/>

                            </div>

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

 </table>

    <div class="clear"></div>
    <br>
    <table>
        <tr>
            <td width="46%"></td>
            <td width="9%">
                <a class="btn-first"
                   href="javascript: void(0);" onclick="document.getElementById('sliderForm').submit();">Apply Changes</a>
            </td>

            <td width="2%">
                <a class="btn-second"
                   href="<c:out value="${pageContext.request.contextPath}"/>/slide.do">Reset</a>
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
                   onclick="replacePic(this,
                           '<%=menuItem.getProductNo()%>',
                           '<%=menuItem.getDescEn()%>',
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
        $("div.current-slide-box").each(function () {
            $(this).removeClass("current-slide-box active");
            $(this).addClass("current-slide-box");
        });

        $(element).addClass("current-slide-box active");

    });

    var activeBoxId;
    function replacePic(element,  menuItemId,  foodName, categoryId, groupId) {

//        alert('\n'+menuItemId+'\n'+foodName+'\n'+'\n'+categoryId+'\n'+groupId);
        $("#" + activeBoxId + "ID").val(menuItemId);
        $("#" + activeBoxId + "FoodNameInput").val(foodName);
        $("#" + activeBoxId + "CategoryId").val(categoryId);
        $("#" + activeBoxId + "GroupId").val(groupId);
        $("#" + activeBoxId + "isupdate").val('yes');
        alert('Replaced this item with '+foodName);


//      alert('aaray:'+arr.length);
    }

    function activateClass(element) {
        activeBoxId = $(element).attr('id');
        $("div.current-slide-box").each(function () {
            $(this).removeClass("current-slide-box active");
            $(this).addClass("current-slide-box");
        });

        $(element).addClass("current-slide-box active");
    }


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
        myForm.action = '<%=context%>/slide.do';

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


    function showReplace(){
        new $.Zebra_Dialog('<span style="font-size:20px;">This Slide Replaced</span>'
                , {
                    'buttons':  false,
                    'type':'confirmation',
                    'modal': false,
                    'position':['center', 'top + 20'] ,
                    'auto_close': 1000});
    }
</script>