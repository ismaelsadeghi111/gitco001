<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">


    $(document).ready(function () {
        $("#input1").datepick({dateFormat:'yyyy-mm-dd'});
        $("#input2").datepick({dateFormat:'yyyy-mm-dd'});

    });


    function activeDisplayModePeriodic () {
        $("#edit_mode_periodic").hide();
        $("#submit_mode_periodic").hide();
        $("#display_mode_periodic").show();
    }

   function edit_minVal() {
        $("#edit_mode_minVal").show();
        $("#display_mode_minVal").hide();
    }

    function close_minVal() {
        $("#edit_mode_minVal").hide();
        $("#display_mode_minVal").show();
    }


    function addNewSchadule() {
        $("#edit_mode_schadule").show();
        $("#submit_mode_schadule").show();
        $("#input1").val('');
        $("#input2").val('');
        $("#input3").val('');
        $("#submitMode").val('add');
    }


    function activeEditModePeriodic() {
        $("#edit_mode_periodic").show();
        $("#submit_mode_periodic").show();
        $("#display_mode_periodic").hide();

        var tds = $("#display_mode_periodic").find('td');
        var values = new Array();

        tds.each(function (index, item) {
            values[index] = $(item).html();
        });

        $("#input_periodic1").val(values[0]);
        $("#input_periodic2").val(values[1]);
        $("#input_periodic3").val(values[2]);
        $("#input_periodic4").val(values[3]);
        $("#input_periodic5").val(values[4]);
        $("#input_periodic6").val(values[5]);
        $("#input_periodic7").val(values[6]);
    }

    function editIt(element) {
        $("#submitMode").val('edit');
        $("#edit_mode_schadule").show();
        $("#submit_mode_schadule").show();


        $("#activeDisplayModeSchadule").click(function () {
            $("#edit_mode_schadule").hide();
            $("#submit_mode_schadule").hide();
        });

        var tds = $(element).closest('tr').find('td');
        var values = new Array();

        tds.each(function (index, item) {
            values[index] = $(item).html();
        });
        $("#schaduleId").val(values[0]);
        $("#input1").val(values[1]);
        $("#input2").val(values[2]);
        $("#input3").val(values[3]);

    }

    function deleteSchedule(element) {

        var tds = $(element).closest('tr').find('td');
        var values = new Array();
        tds.each(function (index, item) {
            values[index] = $(item).html();
        });
        $("#schaduleId").val(values[0]);
        $('#schaduleForm').attr("action", "<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=removeSchadule");
        $('#schaduleForm').submit();

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
        $('#schaduleForm').attr("action", "<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=paging");
        $('#schaduleForm').submit();
    }


</script>

<div id="admin-right">
    <div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="display_mode_minVal">
            <tr>
                <td width="95%" height="40" class="font3">Min value for spending Double Pizza Dollar: $ ${minValue}</td>
                <td width="5%"><a href="#" class="orange_color font3" onclick="edit_minVal()">Edit</a></td>
            </tr>
        </table>
        <div class="admin-middlewrapper" style="display: none;" id="edit_mode_minVal">
            <form action="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=insertMinval"
                  name="dpDollarForm" method="post">

                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="33%" class="font3">Min value for spending Double Pizza Dollar: $</td>
                        <td width="63%" id="minvalTd">
                            <input style=" width:60px;" type="text" name="minValue" value='<%=request.getAttribute("minValue")%>'
                                   class="default-textbox"/>
                        </td>
                        <td width="4%"><a href="#" class="red font3" onclick="close_minVal()">Close</a></td>
                    </tr>
                    <tr>
                        <td height="40" colspan="2"><a class="admin-btn FloatLeft" style="margin-right:5px;"
                                                       href="#" onclick="$(this).closest('form').submit()">Save</a>
                            <a class="admin-btn FloatLeft" href="#" onclick="close_minVal()">Cancel</a>
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
    <div class="admin-table">
        <form action="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=insertPeriodic"
              name="dpDollarForm" method="post">
            <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="4" height="36" class=""><span class="admin-table-heading">Periodic Percentage</span>
                    </td>
                    <td width="11%" colspan="3"><span class="admin-table-heading2">
                    <a class="admin-head-btn" onclick="activeEditModePeriodic()" href="#">Edit</a>
                </span></td>
                </tr>
                <tr class="admin-data">
                    <td height="50" width="14%">Sunday</td>
                    <td width="14%">Monday</td>
                    <td width="14%">Tuesday</td>
                    <td width="14%">Wednesday</td>
                    <td width="14%">Thursday</td>
                    <td width="14%">Friday</td>
                    <td width="16%">Saturday</td>
                </tr>
                <tr class="admin-data" id="edit_mode_periodic" style="display: none;">
                    <td height="50" width="14%">%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic1" name="sunday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic2" name="monday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic3" name="tuesday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic4" name="wednesday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic5" name="thursday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic6" name="friday"
                               class="default-textbox"/>
                    </td>
                    <td>%
                        <input style=" width:70px; margin-left:5px;" type="text" id="input_periodic7" name="saturday"
                               class="default-textbox"/>
                    </td>
                </tr>
                <tr class="admin-data" id="submit_mode_periodic" style="display: none">
                    <td height="40" colspan="7">
                        <a class="admin-btn" style="margin:0px 5px;" href="#" onclick="activeDisplayModePeriodic();">Cancel</a>
                        <a class="admin-btn" href="#" onclick="$(this).closest('form').submit()">Save</a>
                    </td>
                </tr>

                <tr class="admin-data" id="display_mode_periodic">
                    <td height="50" width="14%">${dpDollarDays.sunday}</td>
                    <td>${dpDollarDays.monday}</td>
                    <td>${dpDollarDays.tuesday}</td>
                    <td>${dpDollarDays.wednesday}</td>
                    <td>${dpDollarDays.thursday}</td>
                    <td>${dpDollarDays.friday}</td>
                    <td>${dpDollarDays.saturday}</td>
                </tr>
            </table>
        </form>
    </div>
    <div class="admin-table">
        <form name="dpDollarForm" method="post" id="schaduleForm"
              action="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=editSchadule">

            <table class="font3" width="100%" border="0" cellspacing="0" cellpadding="0" id="stable">
                <tr>
                    <td colspan="3" height="36" class=""><span
                            class="admin-table-heading">Double Pizza Dollars Schadule </span></td>
                    <td width="11%"><span class="admin-table-heading2"><a class="admin-head-btn" href="#"
                                                                          onclick="addNewSchadule()">Add New
                        Schadule</a></span></td>
                </tr>
                <tr class="admin-data">
                    <td height="40" width="22%">From</td>
                    <td width="22%">To</td>
                    <td width="22%">Percentage</td>
                    <td width="34%">Options</td>
                </tr>

                <c:forEach items="${dpDollarSchadule}" var="schadule">
                    <tr class="admin-data">
                        <input type="hidden" id="schaduleId" name="schaduleId"/>
                        <td style="display: none;">${schadule.id}</td>
                        <td height="50" width="14%">${schadule.startDate}</td>
                        <td>${schadule.endDate}</td>
                        <td>${schadule.percentage}</td>
                        <td>
                            <a href="#" onclick="editIt(this);" style="margin-right:5px;"><img src="images/edit.png"
                                                                                               width="16" height="16"
                                                                                               alt="Edit"/></a>
                            <a href="#" onclick="deleteSchedule(this);"><img src="images/close.png" width="16"
                                                                             height="16" alt="Edit"/></a>
                        </td>
                    </tr>
                </c:forEach>

                <tr class="admin-data" id="edit_mode_schadule" style="display: none">
                    <td colspan="4" style="padding:15px 0 5px 10px;">
                        <div class="edit-schadule"><span>From</span>
                            <input type="hidden" id="submitMode" name="submitMode" value="edit"/>
                            <input type="text" id="input1" name="startDate" class="default-textbox"
                                   style="width:150px;"/>
                        </div>
                        <div class="edit-schadule"><span>To</span>
                            <input type="text" id="input2" name="endDate" class="default-textbox" style="width:150px;"/>
                        </div>
                        <div class="edit-schadule"><span>Percentage </span>
                            <input type="text" id="input3" name="percentage" class="default-textbox"
                                   style="width:150px;"/>
                            &nbsp;%
                        </div>
                    </td>
                </tr>
                <tr class="admin-data" id="submit_mode_schadule" style="display: none">
                    <td height="40" colspan="4"><a class="admin-btn" style="margin:0px 5px;" href="#"
                                                   id="activeDisplayModeSchadule">Cancel</a>
                        <a class="admin-btn" href="#" id="editSchadule" onclick="$(this).closest('form').submit()">
                            Save</a></td>

                </tr>
                <font color="red"  class="font3">${message}</font><br/>
                <tr style="background:#575757;">
                    <td height="31" colspan="3">
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

    </form>
    <a href="<c:out value="${pageContext.request.contextPath}"/>/dollarSetting.do?operation=calculateDPDollar" >Calculate DP Dollar</a>

</div>
