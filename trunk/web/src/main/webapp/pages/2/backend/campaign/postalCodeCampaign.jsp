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
        <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="4" height="36" class=""><span class="admin-table-heading">Postal Code Compaign</span></td>
                <td width="11%" colspan="3"><span class="admin-table-heading2"></span></td>
            </tr>
            <tr>
                <td class="Compaign-form" colspan="7">
                    <div>
                        <form action="<c:out value="${pageContext.request.contextPath}"/>/campaign.do?operation=insertPostalCampaign" name="campaignForm"
                              method="post" id="campaignForm">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10%">Send</td>
                            <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="42%">
                                        <select class="default-textbox" style="width:323px;" name="food">
                                            <% Map<String , String> specialList = (Map<String , String>) request.getAttribute("menuList");
                                                for (Map.Entry<String , String> c : specialList.entrySet()) {
                                            %>
                                            <option value="<%=c.getKey()%>">
                                                <%=c.getValue()%>
                                            </option>
                                            <%
                                                } %>
                                        </select>
                                    </td>
                                    <td width="15%">To users in area</td>
                                    <td width="43%"><input type="text" class="default-textbox" style="width:100px;" value="XYZ" name="postalCode" />
                                        &nbsp;&nbsp;Postal Code</td>
                                </tr>
                            </table></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td width="71%">&nbsp;</td>
                            <td width="19%">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Title (English)</td>
                            <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="49%"><input type="text"  name="titleEn" class="default-textbox" style="width:315px;" value="Delicious deal of the week"  /></td>
                                </tr>
                            </table></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td width="71%">&nbsp;</td>
                            <td width="19%">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Title (French)</td>
                            <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="49%"><input type="text" name="titleFr"  class="default-textbox" style="width:315px;" value="Delicious deal of the week"  /></td>
                                    <td width="8%">Image</td>
                                    <td width="43%"><input type="file"  name="imageFile" /></td>
                                </tr>
                            </table></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>At</td>
                            <td>
                                <input  type="text" class="default-textbox celander" style="width:180px;" value="2013-05-12  13:14 PM" name="campaignDate" id="campaignDate"/></td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td><a href="#" onclick="$('#campaignForm').submit();" class="black_btn2 FloatLeft" style=" padding:0 50px;">Add</a></td>
                            <td>&nbsp;</td>
                    </table>
                        </form>
                </div></td>
            </tr>
        </table>
    </div>
    <div class="admin-table">
        <form name="postalCodeForm" method="post" id="historyForm"
        <%-- action="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=editSchadule"--%>>
            <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0" id="stable" >
                <tr>
                    <td colspan="7" height="36" class="admin-table-heading">
                        <span >Regular Campaign History </span>
                    </td>
                </tr>
                <tr class="admin-data">
                    <td width="10%" height="40" >Title</td>
                    <td width="20%">Campaign Date/Time</td>
                    <td width="20%">Food</td>
                    <td width="20%" style="padding:0px;" align="center">Postal Code</td>
                    <td width="10%" style="padding:0px;" align="center" >Image</td>
                </tr>

                <c:forEach items="${campaignList}" var="campaign">
                    <tr class="admin-data">
                        <td height="50" width="14%">${campaign.campaign_title_en}</td>
                        <td>${campaign.campaign_date}</td>
                        <td>${campaign.menu_id}</td>
                        <td>${campaign.postalCode}</td>
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
        </form>
    </div>
</div>