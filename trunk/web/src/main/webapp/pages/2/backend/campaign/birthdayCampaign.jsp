<%@ page import="java.util.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" >
<%String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);%>
    $(document).ready(function () {
        $("#campaignDate").datepick({dateFormat:'yyyy-mm-dd'});
    });

</script>
    <div id="admin-right" class="admin-right2">
    <div class="admin-table">
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0" id="stable" >
            <tr>
                <td colspan="7" height="36" class="admin-table-heading">
                    <span >Regular Campaign History </span>
                </td>
            </tr>
            <tr class="admin-data">
                <td width="10%" height="40" >Title</td>
                <td width="20%">Food</td>
                <td width="20%" style="padding:0px;" align="center">User name</td>
                <td width="10%" style="padding:0px;" align="center" >Image</td>
            </tr>

            <c:forEach items="${campaignList}" var="campaign">
                <tr class="admin-data">
                    <td height="50" width="14%">${campaign.campaign_title_en}</td>
                    <td>${campaign.menu_id}</td>
                    <td>${campaign.contactinfo_id}</td>
                    <td><img height="136" width="164" src="<%=request.getContextPath()%><%=middlePath%>${campaign.imageUrl}"/></td>
                </tr>
            </c:forEach>

            <tr class="admin-data" id="submit_mode_schadule" style="display: none">
                <td height="40" colspan="6"><a class="admin-btn" style="margin:0px 5px;" href="#"
                                               id="activeDisplayModeSchadule">Cancel</a>
                    <a class="admin-btn" href="#" id="editSchadule" onclick="$(this).closest('form').submit()">
                        Save</a></td>
            </tr>
            <tr style="background:#575757;">
                <td height="31" colspan="6">
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
                <td colspan="1"><span class="admin-total-page">${from} - ${to} of ${totalRecords}</span></td>
                <input type="hidden" id='pagingAction' name="pagingAction"/>
            </tr>
        </table>
    </div>
    <div class="admin-table">

        <form action="<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=insertBirthdayCampaign" name="campaignForm"
        method="post" id="campaignForm">

        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="4" height="36" class=""><span class="admin-table-heading">Edit Birthday Compaign</span></td>
                <td width="11%" colspan="3"><span class="admin-table-heading2"></span></td>
            </tr>
            <tr>
                <td class="Compaign-form" colspan="7"><div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="12%">Choose food</td>
                            <td colspan="2">
                                <select class="default-textbox" style="width:323px;" name="food">
                                        <% Map<String , String> specialList = (Map<String , String>) request.getAttribute("menuList");
                                                for (Map.Entry<String , String> c : specialList.entrySet()) {
                                        %>
                                    <option value="<%=c.getKey()%>">
                                        <%=c.getValue()%>
                                    </option>
                                        <%  } %>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td width="80%">&nbsp;</td>
                            <td width="8%">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Title (English) </td>
                            <td colspan="2">
                                <input type="text" name="titleEn" class="default-textbox" style="width:315px;" value="Delicious deal of the week"  /></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Title (French) </td>
                            <td colspan="2">
                                <input type="text" name="titleFr" class="default-textbox" style="width:315px;" value="Delicious deal of the week"  /></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td valign="top">Image</td>
                            <td>
                                <input type="file"  name="imageFile" /></td>
                            <td>&nbsp;</td>
                        </tr>

                    </table>
                </div></td>
            </tr>
            <tr>
                <td class="Compaign-form" colspan="7">
                    <a href="#" onclick="$('#campaignForm').submit();" class="black_btn2 FloatLeft" style=" padding:0 50px;">Add</a></td>
            </tr>
        </table>
      </form>
    </div>
</div>