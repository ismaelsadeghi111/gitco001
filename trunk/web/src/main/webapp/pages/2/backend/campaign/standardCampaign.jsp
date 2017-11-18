<%@ page import="java.util.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.common.config.ApplicationConfig" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_CAMPAIGN_PATH);
    String contextForIncludedPage = request.getContextPath();
    List<PopularCategory> menu_special = (List<PopularCategory>) request.getAttribute("allMenuItemList");
    List<StandardCampaign> stCampaignLst = (List<StandardCampaign>) request.getAttribute("campaignList");
    Map<String, String> foodMapList = new HashMap();
    Date nowDateTime = new Date();
    int from = Integer.parseInt(request.getAttribute("from").toString());
%>

<script src="<%=contextForIncludedPage%>/js/jquery/jquery.datetimepicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet" type="text/css" href="<%=contextForIncludedPage%>/css/jquery.datetimepicker.css"/>

<script type="text/javascript">
  /*  $(document).ready(function () {
        $("#campaignDate").datepick({dateFormat:'yyyy-mm-dd'});
        disableElement('customNotOrderInLastDays'); disableElement('customOrderInLastDays'); disableElement('orderInLastDays');disableElement('orderNumbers') ;
        clearData('customNotOrderInLastDays') ;clearData('orderNumbers') ; clearData('orderInLastDays') ;
        enableElement('customOrderInLastDays') ;
        $('#campaignType').attr('value', 'CustomerOrder');
    });*/



    //Saeid AmanZadeh
   function radioIssue(element) {
       var value = $(element).val();
       if (value.indexOf("allUsers") == 0) {
           disableElement('customNotOrderInLastDays');
           disableElement('customOrderInLastDays');
           disableElement('orderInLastDays');
           disableElement('orderNumbers');
           clearData('customNotOrderInLastDays');
           clearData('orderNumbers');
           clearData('orderInLastDays');
           disableElement('customOrderInLastDays');
           disableElement('orderedSign');
           $('#campaignType').attr('value','allUsers');
           $('#CustomerNotOrderDayLabel').html("&nbsp;day");
           $('#customizedOrderedDayLabel').html("&nbsp;day");
           $('#CustomerOrderDayLabel').html("&nbsp;day");

       }else if (value.indexOf("CustomerOrder") == 0) {
            disableElement('customNotOrderInLastDays');
            disableElement('customOrderInLastDays');
            disableElement('orderInLastDays');
            disableElement('orderNumbers');
            clearData('customNotOrderInLastDays');
            clearData('orderNumbers');
            clearData('orderInLastDays');
            enableElement('customOrderInLastDays');
            disableElement('orderedSign');
            $('#campaignType').attr('value', 'CustomerOrder');
            $('#CustomerNotOrderDayLabel').html("&nbsp;day");
            $('#customizedOrderedDayLabel').html("&nbsp;day");
            $('#CustomerOrderDayLabel').html("<sup class='required' >*</sup>&nbsp;day");

        }
        else if(value.indexOf("CustomerNotOrder") == 0) {
            disableElement('orderInLastDays');
            disableElement('orderNumbers');
            disableElement('customOrderInLastDays');
            clearData('orderNumbers');
            clearData('orderInLastDays');
            clearData('customOrderInLastDays');
            enableElement('customNotOrderInLastDays');
            disableElement('orderedSign');
            $('#campaignType').attr('value', 'CustomerNotOrder') ;
            $('#CustomerOrderDayLabel').html("&nbsp;day");
            $('#customizedOrderedDayLabel').html("&nbsp;day");
            $('#CustomerNotOrderDayLabel').html("<sup class='required' >*</sup>&nbsp;day");

        }
        else if(value.indexOf("customizedOrdered") == 0) {
            disableElement('customNotOrderInLastDays');
            disableElement('customOrderInLastDays');
            clearData('customNotOrderInLastDays');
            clearData('customOrderInLastDays');
            enableElement('orderInLastDays');
            enableElement('orderNumbers');
            enableElement('orderedSign');
            $('#campaignType').attr('value', 'customizedOrdered');
            $('#CustomerNotOrderDayLabel').html("&nbsp;day");
            $('#CustomerOrderDayLabel').html("&nbsp;day");
            $('#customizedOrderedDayLabel').html("<sup class='required' >*</sup>&nbsp;day");
        }
    }

    //----


    function disableElement(element) {
        $('#'+element).attr('disabled', 'disabled');
    }

    function enableElement(element) {
        $('#'+element).attr( "disabled", false);
    }

    function clearData(element) {
        $('#'+element).attr('value', '');
    }

    var pageNo = <%=request.getAttribute("pageNumber")%>;
    var totalpages = <%=request.getAttribute("totalPages")%>;
    function go(element, action) {
        if (action == 'lastPage' || action == 'nextPage') {
            if (pageNo >= totalpages) {
                return;
            }
        }

        if (action == 'firstPage' || action == 'previousPage') {
            if (pageNo == 1) {
                return;
            }
        }

        $('#pagingAction').val(action);
        $('#historyForm').attr("action", "<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=paging");
        $('#historyForm').submit();
    }



    function validateStandardCampaign(element) {

       // alert($("#campaignForm input[type='radio']:checked").val());
        if($("#campaignForm input[type='radio']:checked").val() == "CustomerOrder"){
            $("#campaignForm").validate({
                rules:{
                    titleEn:"required",
                    titleFr:"required",
                    campaignDate:"required",
                    food:"required",
                    imageFile:"required",
                    customOrderInLastDays :"required",
                    subjectEn :"required",
                    subjectFr:"required"
                }
            });


        }else if($("#campaignForm input[type='radio']:checked").val() == "CustomerNotOrder"){
            $("#campaignForm").validate({
                rules:{
                    titleEn:"required",
                    titleFr:"required",
                    campaignDate:"required",
                    food:"required",
                    imageFile:"required",
                    customNotOrderInLastDays:"required"
                }
            });

        }else if($("#campaignForm input[type='radio']:checked").val() == "customizedOrdered"){
            $("#campaignForm").validate({
                rules:{
                    titleEn:"required",
                    titleFr:"required",
                    campaignDate:"required",
                    food:"required",
                    imageFile:"required",
                    orderNumbers:"required",
                    orderInLastDays:"required"
                  }
            });

        }else if($("#campaignForm input[type='radio']:checked").val() == "allUsers"){
            $("#campaignForm").validate({
                rules:{
                    titleEn:"required",
                    titleFr:"required",
                    campaignDate:"required",
                    food:"required",
                    imageFile:"required"

                }
            });

        }

        if ($(element).closest('form').valid()) {
            $('#titleFr').attr('value', correctFrenchHTMLChars($('#titleFr').val()));
            $(element).closest('form').submit();
        }

    }

    //Saeid AmanZadeh
    function showResult(standardCampaignId){
       $('#standardCampaignId').attr('value', standardCampaignId);
        window.open('campaign.do?operation=getJoinRegularCampaignUserOrder&standardCampaignId='+standardCampaignId,'popUpWindow','height=600,width=720,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');

    }

  function downloadUserList(standardCampaignId, lang){
      $('#standardCampaignId').attr('value', standardCampaignId);
      window.open('campaign.do?operation=generateCampaignFile&standardCampaignId='+standardCampaignId+'&fileType=csv&lang='+lang,'popUpWindow','height=1,width=1,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');

  }

  function downloadHtmlFile(standardCampaignId, lang){
      $('#standardCampaignId').attr('value', standardCampaignId);
      window.open('campaign.do?operation=generateCampaignFile&standardCampaignId='+standardCampaignId+'&fileType=html&lang='+lang,'popUpWindow','height=1,width=1,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes');

  }

  ///

  function runAndPause(standardCampaignId, mode){

      $('#newStatus').attr('value', mode);
      $('#standardCampaignId').attr('value', standardCampaignId);
      $('#operation').attr('value', "goToMainPage");
      $('#campaignForm').submit();
  }


  function sendMailList(){
      if (!confirm("Do you want really send emails ?"))
      {
          e.preventDefault();
          return;
      }else{
          $('#standardCampaignId').attr('value', standardCampaignId);
          $('#campaignForm').submit();
      }
  }

  function correctFrenchHTMLChars(str1){
     // alert("im here");
      var finalizeStr = str1.replace(/À/g, "&#192;");
      finalizeStr = finalizeStr.replace(/à/g, "&#224;");
      finalizeStr = finalizeStr.replace(/Â/g, "&#194;");
      finalizeStr = finalizeStr.replace(/â/g, "&#226;");
      finalizeStr = finalizeStr.replace(/Æ/g, "&#198;");
      finalizeStr = finalizeStr.replace(/æ/g, "&#230;");
      finalizeStr = finalizeStr.replace(/Ç/g, "&#199;");
      finalizeStr = finalizeStr.replace(/ç/g, "&#231;");
      finalizeStr = finalizeStr.replace(/È/g, "&#200;");
      finalizeStr = finalizeStr.replace(/è/g, "&#232;");
      finalizeStr = finalizeStr.replace(/É/g, "&#201;");
      finalizeStr = finalizeStr.replace(/é/g, "&#233;");
      finalizeStr = finalizeStr.replace(/Ê/g, "&#202;");
      finalizeStr = finalizeStr.replace(/ê/g, "&#234;");
      finalizeStr = finalizeStr.replace(/Ë/g, "&#203;");
      finalizeStr = finalizeStr.replace(/ë/g, "&#235;");
      finalizeStr = finalizeStr.replace(/Î/g, "&#206;");
      finalizeStr = finalizeStr.replace(/î/g, "&#238;");
      finalizeStr = finalizeStr.replace(/Ï/g, "&#207;");
      finalizeStr = finalizeStr.replace(/ï/g, "&#239;");
      finalizeStr = finalizeStr.replace(/Ô/g, "&#212;");
      finalizeStr = finalizeStr.replace(/ô/g, "&#244;");
      finalizeStr = finalizeStr.replace(/Œ/g, "&#140;");
      finalizeStr = finalizeStr.replace(/œ/g, "&#156;");
      finalizeStr = finalizeStr.replace(/Ù/g, "&#217;");
      finalizeStr = finalizeStr.replace(/ù/g, "&#249;");
      finalizeStr = finalizeStr.replace(/Û/g, "&#219;");
      finalizeStr = finalizeStr.replace(/û/g, "&#251;");
      finalizeStr = finalizeStr.replace(/Ü/g, "&#220;");
      finalizeStr = finalizeStr.replace(/ü/g, "&#252;");
      finalizeStr = finalizeStr.replace(/«/g, "&#171;");
      finalizeStr = finalizeStr.replace(/»/g, "&#187;");
      finalizeStr = finalizeStr.replace(/€/g, "&#128;");
      finalizeStr = finalizeStr.replace(/₣/g, "&#8355;");
      //alert(finalizeStr);
      return finalizeStr;
  }
    //---

</script>
<div id="admin-right" class="admin-right2">
<div class="admin-table">
    <table class="font3" width="100%" border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="4" height="36" class=""><span class="admin-table-heading">Regular Compaign</span></td>
            <td width="11%" colspan="3"><span class="admin-table-heading2"></span></td>
        </tr>
        <tr>
            <td class="Compaign-form" colspan="7">
                <div>
                    <form action="<c:out value="${pageContext.request.contextPath}" />/campaign.do"
                          name="campaignForm" method="post" id="campaignForm" enctype="multipart/form-data" class="std" acceptcharset="UTF-8" >

                        <input type="hidden" name="operation" id="operation" value="insertStandardCampaign">
                        <input type="hidden" name="campaignType" id="campaignType" value="allUsers"/>
                        <input type="hidden" name="newStatus" id="newStatus"/>
                        <input type="hidden" name="standardCampaignId" id="standardCampaignId"/>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr id="addMsgTr" style="display: none; background-color: #62ac25;height: 30px;">
                                <td colspan="3" class="font4" style="color:#fff;">
                                    <img src="images/right.png" alt="Right"/> &nbsp;
                                    <label id="addMassageLb"></label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" class="font2">
                                    <fieldset >
                                        <legend>Choose one of following items</legend>
                                        <table>
                                            <tr>
                                                <td>
                                                    &nbsp;
                                                </td>
                                                <td colspan="2">
                                                    <label class="button info-title">
                                                        <input type="radio" name="rd" value="allUsers" checked="checked" onchange="radioIssue(this)"  />
                                                        <span class="outer"><span class="inner"></span></span> All Users
                                                    </label>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td width="10%" >
                                                    &nbsp;
                                                </td>

                                                <td width="40%">
                                                    <label class="button info-title">
                                                    <input type="radio" name="rd" value="CustomerOrder"  onchange="radioIssue(this)"/>
                                                    <span class="outer"><span class="inner"></span></span> Customers Order in last
                                                </label></td>
                                                <td class="gray" width="50%">
                                                    <input type="text" class="default-textbox" name="customOrderInLastDays"
                                                           id="customOrderInLastDays" style="width:50px;"/>
                                                    <label id="CustomerOrderDayLabel" >
                                                        &nbsp;day
                                                    </label>
                                                </td>

                                            </tr>

                                            <tr>
                                                <td >
                                                    &nbsp;
                                                </td>
                                                <td >
                                                    <label class="button info-title">
                                                        <input type="radio" name="rd" value="CustomerNotOrder" onchange="radioIssue(this)"  />
                                                        <span class="outer"><span class="inner"></span></span> Customers Not Order in last
                                                    </label></td>
                                                <td class="gray">
                                                    <input type="text" class="default-textbox" disabled="disabled" name="customNotOrderInLastDays"
                                                           id="customNotOrderInLastDays" style="width:50px;"/>
                                                    <label id="CustomerNotOrderDayLabel" >
                                                        &nbsp;day
                                                    </label>
                                                </td>

                                            </tr>

                                            <tr>
                                                <td >
                                                    &nbsp;
                                                </td>
                                                <td>
                                                    <label class="button info-title">
                                                        <input type="radio" name="rd" value="customizedOrdered" onchange="radioIssue(this)"/>
                                                        <span class="outer"><span class="inner"></span></span> Customer Ordered </label>
                                                </td>
                                                <td  aligne="left" class="gray">
                                                    <select disabled="disabled" class="default-textbox" style="width:120px;" id="orderedSign"
                                                            name="orderSign">
                                                        <option value="=">equals</option>
                                                        <option value=">">more than</option>
                                                        <option value="<">less than</option>
                                                    </select>

                                                    <input disabled="disabled" style="width:50px;" type="text" class="default-textbox"
                                                           name="orderNumbers"
                                                           id="orderNumbers"/>
                                                    &nbsp;&nbsp;&nbsp;in last
                                                    <input disabled="disabled" style="width:50px; " type="text" class="default-textbox"
                                                           name="orderInLastDays"
                                                           id="orderInLastDays"/>
                                                    <label id="customizedOrderedDayLabel" >
                                                        &nbsp;day
                                                    </label>
                                                </td>

                                            </tr>


                                    </table>
                                </fieldset>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                                                       <!--
                            <tr>
                                <td>Choose food</td>
                                <td colspan="2" class="gray">
                                    <select class="default-textbox" style="width:250px;" name="food">
                                        <option value="">
                                            ---
                                        </option>
                                        <% Map<String , String> specialList = (Map<String , String>) request.getAttribute("menuList");
                                            for (Map.Entry<String , String> c : specialList.entrySet()) {
                                        %>
                                        <option value="<%=c.getKey()%>">
                                            <%=c.getValue()%>
                                        </option>
                                        <%
                                            } %>
                                    </select>
                                    <sup  class="required">*</sup>




                                </td>

                            </tr>
                            -->
                            <tr>
                                <td>Choose food</td>
                                <td colspan="2" class="gray">
                                    <select class="default-textbox" style="width:250px;" name="food">
                                        <option value="">
                                            ---
                                        </option>
                                        <%
                                            Category category;
                                            String popDescription="";
                                            Integer menuItemId;
                                            Integer groupId;
                                            String preId="";
                                            String finalProdNumber = "";
                                            String type = "";
                                            int counter = 0;
                                            int allCnt = 0;
                                            if (menu_special != null) {
                                                for (PopularCategory menuItem : menu_special) {
                                                    if(!(menuItem.getProductNo().equalsIgnoreCase("0033"))){

                                                        menuItemId= Integer.parseInt(menuItem.getProductNo());
                                                        if(menuItemId==1){popDescription="Two medium 12 in. pizzas.";}
                                                        if(menuItemId==0){popDescription="Two small 10 in. pizzas.";}
                                                        if((menuItemId>0)&&(menuItemId<10))preId="000"+menuItemId;
                                                        if((menuItemId>10)&&(menuItemId<100))preId="00"+menuItemId;
                                                        if((menuItemId>100)&&(menuItemId<1000))preId="0"+menuItemId;

                                                        groupId = menuItem.getGroupId();
                                                        finalProdNumber = preId;
                                                        type = "PopularCategory";
                                                        //String gId = ((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getProductNo()
                                                        Category popularCategory = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME, Integer.valueOf(menuItem.getProductNo()));
                                                        CombinedMenuItem popularCombinedMenuItem = InMemoryData.findCombinedMenuItemByProdNoAndGroupId(menuItem.getProductNo(), groupId.toString());
                                                        MenuSingleItem popularMenuSingleItem = InMemoryData.findMenuSingleItemByIdAndGroupId(preId, groupId.toString());


                                                        if(popularCombinedMenuItem!=null){
                                                            popDescription = popularCombinedMenuItem.getDescription(Locale.ENGLISH);
                                                            if(popularCombinedMenuItem.getProductNo() != null && popularCombinedMenuItem.getProductNo().equals("null")){
                                                                finalProdNumber = popularCombinedMenuItem.getProductNo();
                                                                type = "CombinedMenuItem1";
                                                            }
                                                        }
                                                        else if(popularMenuSingleItem!=null){
                                                            popDescription = popularMenuSingleItem.getDescription(Locale.ENGLISH);
                                                            if(popularMenuSingleItem.getProductNo() != null && popularMenuSingleItem.getProductNo().equals("null")){
                                                                finalProdNumber = popularMenuSingleItem.getProductNo();
                                                                type = "popularMenuSingleItem";

                                                            }
                                                        }
                                                        else if(popularCategory!=null)   {
                                                            category = InMemoryData.findCategoryById(Constant.ORDER_MENU_NAME,  Integer.valueOf(menuItem.getProductNo()));
                                                            if (category.getSubCategoryList().get(0).getObject() instanceof MenuSingleItem){
                                                                popDescription=((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getDescription(Locale.ENGLISH);
                                                                if(((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getProductNo() != null ){
                                                                    finalProdNumber = "0" + ((MenuSingleItem) category.getSubCategoryList().get(0).getObject()).getProductNo();
                                                                    type = "MenuSingleItem";
                                                                }
                                                            }   else if (category.getSubCategoryList().get(0).getObject() instanceof CombinedMenuItem){
                                                                popDescription=((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getDescription(Locale.ENGLISH);
                                                                if(((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getProductNo() != null){
                                                                    finalProdNumber = "0"+((CombinedMenuItem) category.getSubCategoryList().get(0).getObject()).getProductNo();
                                                                    type = "CombinedMenuItem2";
                                                                    counter++;
                                                                }

                                                            }

                                                        }
                                                        foodMapList.put(menuItem.getProductNo() + "/"+menuItem.getCategId() + "/" + menuItem.getGroupId(), menuItem.getDescEn() + " - " + popDescription);
                                                        allCnt++;

                                        %>

                                        <option value="<%= menuItem.getProductNo() + "/" + menuItem.getCategId() + "/" + menuItem.getGroupId() %>">
                                            <%=menuItem.getDescEn() + " - " + popDescription + " [" + menuItem.getOrderCount() + "]"%>
                                        </option>
                                        <%
                                                }
                                                }
                                            } %>
                                    </select>
                                    <sup  class="required">*</sup>
                                </td>

                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>French Food Image</td>
                                <td colspan="2" class="gray">
                                    <html:file property="imageFile" size="50" />
                                    <input type="file" name="imageFile"/>
                                    <sup class="required">*</sup>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>English Food Image</td>
                                <td colspan="2" class="gray">
                                    <html:file property="imageFileEn" size="50" />
                                    <input type="file" name="imageFileEn"/>
                                    <sup class="required">*</sup>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>

                                </td>
                                <td class="gray">
                                    English Title
                                </td>
                                <td class="gray">
                                    France Title
                                </td>
                            </tr>

                            <tr>
                                <td>Campaign Title</td>
                                <td class="gray" style="font-size: 12px;" >
                                    <input type="text" name="titleEn" id="titleEn" class="default-textbox" style="width:200px;"
                                           value="Delicious deal of the week" /><sup class="required">*</sup>
                                </td>
                                <td class="gray" style="font-size: 12px;">
                                    <input type="text" id="titleFr" name="titleFr" class="default-textbox"  style="width:200px;"
                                           value="Delicieux affaire de la semaine"  /><sup class="required">*</sup>
                                </td>

                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>

                                </td>
                                <td class="gray">
                                    English Subject
                                </td>
                                <td class="gray">
                                    France Subject
                                </td>
                            </tr>

                            <tr>
                                <td>Campaign Subject</td>
                                <td class="gray" style="font-size: 12px;">
                                    <input type="text" name="subjectEn" class="default-textbox" style="width:200px;"
                                           value="Back To School"/><sup class="required">*</sup>
                                </td>
                                <td class="gray" style="font-size: 12px;">
                                    <input type="text" name="subjectFr" class="default-textbox" style="width:200px;"
                                           value="Retour a lecole"/><sup class="required">*</sup>
                                </td>

                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td>Campaign Date</td>
                                <td class="gray">
                                    <!--
                                    <input class="default-textbox celander" type="text" name="deliverTime" id="datetimepicker" readonly="true" style="width:180px;"
                                    -->
                                    <input type="text" class="default-textbox celander" readonly="true" style="width:180px;"
                                            name="campaignDate" id="campaignDate"/>

                                    <sup class="required">*</sup>
                                </td>
                                <td>
                                    Status
                                    <select class="default-textbox" name="status">
                                        <option value="pause" selected>
                                            Pause
                                        </option>
                                        <option value="run">
                                            Run
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td colspan="2">
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td style="vertical-align: top;">Campaign Description(English-HTML)</td>
                                <td align="left" valign="top" colspan="2">
                                    <textarea  class="default-textbox" style="width: 600; height: 100;" id="itemHtmlEn" name="itemHtmlEn" cols="50" rows="3">
                                        <tr>
                                            <td valign="top" width="37"><img src="httpPath/img/campaignIcon/icon1.jpg"  width="27" height="36" style="display:block;" border="0" align="left"></td>
                                            <td valign="middle" style="font-family:Arial, Tahoma, Helvetica, sans-serif; font-size:15px; line-height:18px; color:#ffffff; text-align:left;">1 Large Pizza(English)</td>
                                        </tr>

                                        <tr>
                                            <td valign="top" width="37"><img src="httpPath/img/campaignIcon/cans.jpg"   width="27" height="36" style="display:block;" border="0" align="left"></td>
                                            <td valign="middle" style="font-family:Arial, Tahoma, Helvetica, sans-serif; font-size:15px; line-height:18px; color:#ffffff; text-align:left;">3 Cans Of Pepsi(Englsih)</td>
                                        </tr>
                                        <tr>
                                            <td valign="top" width="37" colspan="2"><br><br></td>
                                        </tr>
                                    </textarea>
                                </td>
                            </tr>
                            <tr>
                                <td style="vertical-align: top;">Campaign Description(French-HTML)</td>
                                <td align="left" valign="top" colspan="2">
                                    <textarea  class="default-textbox" style="width: 600; height: 100;" id="itemHtmlFr" name="itemHtmlFr" cols="50" rows="3">
                                        <tr>
                                            <td valign="top" width="37"><img src="httpPath/img/campaignIcon/icon1.jpg" width="27" height="36" style="display:block;" border="0" align="left"></td>
                                            <td valign="middle" style="font-family:Arial, Tahoma, Helvetica, sans-serif; font-size:15px; line-height:18px; color:#ffffff; text-align:left;">1 Large Pizza(French)</td>
                                        </tr>

                                        <tr>
                                            <td valign="top" width="37"><img src="httpPath/img/campaignIcon/cans.jpg"  width="27" height="36" style="display:block;" border="0" align="left"></td>
                                            <td valign="middle" style="font-family:Arial, Tahoma, Helvetica, sans-serif; font-size:15px; line-height:18px; color:#ffffff; text-align:left;">3 Cans Of Pepsi(French)</td>
                                        </tr>
                                        <tr>
                                            <td valign="top" width="37" colspan="2"><br><br></td>
                                        </tr>
                                    </textarea>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a onclick="validateStandardCampaign(this);" class="black_btn2 FloatLeft"
                                       style=" padding:0 50px;">Add</a></td>
                                <td>&nbsp;&nbsp;


                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </td>
        </tr>
    </table>
</div>
<div class="admin-table">
    <form name="dpDollarForm" method="post" id="historyForm"
         <%-- action="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=editSchadule"--%>>
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0" id="stable" >
            <tr>
                <td colspan="7">
                    <table>
                        <tr>
                            <td height="36" class="admin-table-heading" id="campaignLabel">
                                <span>Campaigns</span>
                            </td>
                        </tr>
                    </table>
                </tr>
            </tr>
            <tr class="admin-table-headline" style="background:#999999;">
                <td style="text-align: center" width="4%">Id</td>
                <td width="36%">Sending to Who?</td>
                <td width="25%">Title</td>
                <td width="10%">Date</td>
                <td width="25%">Food Description</td>
                <td align="center">Image</td>
                <td aligne="center">Action</td>
            </tr>
            <% int int1 = from;
               int campaignCounter = 0;
            %>
            <c:forEach items="${campaignList}" var="campaign">
                <tr class="admin-data" height="65">
                    <td >
                        <%=int1++%>
                    </td>
                    <!-- Description Type of campaig -->
                           <c:choose>
                                <c:when test="${campaign.orderType == 'allUsers'}">
                                        <td>All Users who want to recieve the campaign</td>
                                </c:when>
                                <c:when test="${campaign.orderType == 'CustomerOrder'}">
                                        <td>Users who ordered in last ${campaign.orderDays} days</td>
                                </c:when>

                                <c:when test="${campaign.orderType == 'CustomerNotOrder'}">
                                        <td>Users who did not order in last ${campaign.orderDays} days</td>
                                </c:when>

                                <c:when test="${campaign.orderType == 'customizedOrdered'}">
                                    <c:if test="${campaign.orderSign == '='}">
                                        <td>Users who ordered <font style="color: red;">exactly ${campaign.orderNumbers} times </font> in last ${campaign.orderDays} days</td>
                                    </c:if>
                                    <c:if test="${campaign.orderSign == '>'}">
                                        <td>Users who ordered <font style="color: red;">more than ${campaign.orderNumbers} times </font> in last ${campaign.orderDays} days</td>
                                    </c:if>
                                    <c:if test="${campaign.orderSign == '<'}">
                                        <td>Users who ordered <font style="color: red;"> less than ${campaign.orderNumbers} times </font> in last ${campaign.orderDays} days</td>
                                    </c:if>

                                </c:when>


                            </c:choose>
                    <!-- Description Type of campaig -->

                    <td>
                        <font style="font-size:12px">
                        ${campaign.campaign_title_en}
                        <hr style="width: 90%; color: #c4c3c3;">
                        ${campaign.campaign_title_fr}
                        </font>
                    </td>
                    <td>
                        <font style="font-size:14px">
                            ${campaign.campaign_date}
                        </font>
                    </td>
                    <td>
                        <font style="font-size:14px">
                            <%=foodMapList.get(stCampaignLst.get(campaignCounter++).getMenu_id())%>
                       </font>
                    </td>
                    <td align="center">
                        <img align="middle" width="100" height="100" src="<%=contextForIncludedPage%><%=middlePath%>${campaign.imageUrl}"/>
                    </td>
                    <td aligne="center">

                        <!-- <c:if test="${campaign.status == 'run'}">
                                <img id="pauseImg" onclick="runAndPause(${campaign.standardcampaign_id}, 'pause')" title="Set to Pause" style="cursor: pointer" width="16" src="img/icon/pause.png" height="16" />&nbsp;
                        </c:if>
                        <c:if test="${campaign.status == 'sending'}">
                                <img id="pauseImg" title="This campaign is Sending now" width="18" src="images/loading.gif" height="18" />&nbsp;
                                <img id="pauseImg" onclick="runAndPause(${campaign.standardcampaign_id}, 'pause')" title="Set to Pause" style="cursor: pointer" width="16" src="img/icon/pause.png" height="16" />&nbsp;
                        </c:if>

                        <c:if test="${campaign.status == 'pause'}">
                            <img id="runImg" onclick="runAndPause(${campaign.standardcampaign_id}, 'run')" title="Set to Run" style="cursor: pointer" src="img/icon/run.png"  width="16" height="16"/>&nbsp;
                        </c:if>

                        <c:if test="${campaign.status == 'sent'}">
                            <img id="pauseImg" title="This campaign has been sent" width="15" src="img/slideshow_active.png" height="15" />&nbsp;
                        </c:if>-->

                        <img onclick="downloadHtmlFile(${campaign.standardcampaign_id}, 'Fr')" title="Download French HTML file of this campaign" style="cursor: pointer" width="24" src="img/icon/htmlFr.png" height="24" />&nbsp;

                        <img onclick="downloadHtmlFile(${campaign.standardcampaign_id}, 'En')" title="Download English HTML file of this campaign" style="cursor: pointer" width="24" src="img/icon/html.png" height="24" />&nbsp;

                        <img onclick="downloadUserList(${campaign.standardcampaign_id}, 'Fr')" title="Download French list of users that have been included in this Campaign" style="cursor: pointer" width="24" src="img/icon/csvFr.png" height="24" />&nbsp;

                        <img onclick="downloadUserList(${campaign.standardcampaign_id}, 'En')" title="Download English list of users that have been included in this Campaign" style="cursor: pointer" width="24" src="img/icon/csv.png" height="24" />&nbsp;

                        <img onclick="showResult(${campaign.standardcampaign_id})" title="Show Result and Send Emails Form" style="cursor: pointer" width="16" src="img/icon/order.png" height="16" />&nbsp;
                    </td>
                </tr>
            </c:forEach>




            <tr class="admin-data" id="submit_mode_schadule" style="display: none">
                <td height="40" colspan="7"><a class="admin-btn" style="margin:0px 5px;" href="#"
                                               id="activeDisplayModeSchadule">Cancel</a>
                    <a class="admin-btn" href="#" id="editSchadule" onclick="$(this).closest('form').submit()">
                        Save</a>
                </td>
            </tr>

            <tr style="background:#575757;">
                <td height="31" colspan="4">
                    <div class="admin-paging">
                        <ul>
                            <li><a href="#"><img src="images/pre.png" onclick="go(this,'previousPage');"/></a></li>
                            <li class="admin-page-border"><a href="#" onclick="go(this,'firstPage')"><img
                                    src="images/first.png"/></a></li>
                            <li>Page</li>
                            <li>
                                <input class="admin-paging-textbox" type="text" value="${pageNumber}"/>
                            </li>
                            <li class="admin-page-border">of ${totalPages}</li>
                            <li>
                                <a href="#" onclick="go(this,'lastPage');"><img src="images/last.png"/></a>
                            </li>
                            <li class="admin-page-border"><a href="#" onclick="go(this,'nextPage');"><img
                                    src="images/next.png"/></a></li>
                            <li><a href="#"><img src="images/refresh.png"/></a></li>
                            <li>
                                <select class="admin-paging-select">
                                    <option>${count}</option>
                                </select>
                            </li>
                        </ul>
                    </div>
                </td>
                <td colspan="3"><span class="admin-total-page">${from} - ${to} of ${totalRecords}</span></td>
                <input type="hidden" id='pagingAction' name="pagingAction"/>
            </tr>
        </table>

    </form>
</div>
</div>

<script>

    var focusElement = document.getElementById("campaignLabel");
    focusElement.scrollIntoView(true);

    var now = new Date();
    var dateval= now.getFullYear() + '/' +(now.getMonth() + 1) + '/' + now.getDate() + ' ' + now.getHours() + ':' + now.getMinutes();
    $('#campaignDate').datetimepicker();
    $('#campaignDate').datetimepicker({value:dateval,step:10,minDate:dateval});
    var addMsg = "<%=request.getAttribute("message")%>";
    if(addMsg == "saved"){
        document.getElementById("addMassageLb").innerHTML = "Standard Campaign has been saved successfully!";
        $('#addMsgTr').show();
    }
    $('#addMsgTr').fadeOut(15000);

</script>
<script>
//$(document).ready(function () {
//var campaignDate = $('#campaignDate');
//
//});
</script>
