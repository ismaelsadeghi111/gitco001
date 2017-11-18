<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">

    $(document).ready(function () {

        $("#reload_button").click(function () {
            $("#finished").css("display", "none");

            $.ajax({
                type:'POST',
                url:'<c:out value="${pageContext.request.contextPath}"/>/reload.do?operation=reload',
//                dataType:'json',
                success:function (response) {
                    $("#finished").css("display", "block");
                    $("#wait").css("display", "none");
                },
                error:function (e) {
                    alert('Error: ' + e);

                }
            });
        });

        $(document).ajaxStart(function () {
            $("#wait").css("display", "block");
        });

        $(document).ajaxComplete(function () {
        });

    });

</script>
<div id="admin-right">
    <div>
        <div class="admin-middlewrapper">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="font2" colspan="2">Press Relaoad Data to load last updated data from database to memory
                    </td>
                    <td width="20%"><a href="#" id="reload_button" class="admin-btn FloatRight">Reload Data</a></td>
                </tr>
                <tr id="wait" style="display: none;">
                    <td width="5%" height="70"><img src="images/loading.gif"/></td>
                    <td width="75%"><span class="font3">The data is reloading. It may take a few minuets.</span></td>
                    <td width="20%">&nbsp;</td>
                </tr>
            </table>
            <%--
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr style="background:url(images/reload-bg.png) repeat ; ">
                    <td width="46%" height="40" class="font4" style="color:#fff; padding-left:10px;" >After the reloading is finished the icon was changed to </td>
                    <td width="54%"><img src="images/right.png"  alt="Right" /></td>
                </tr>
            </table>--%>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr style="background:#62ac25;display: none; " id="finished">
                    <td width="6%" height="40" class="font4" style="padding-left:10px;"><img src="images/right.png"
                                                                                             alt="Right"/></td>
                    <td width="94%" class="font4" style="color:#fff;">The data is reloading successfully</td>
                </tr>
                <tr>
                    <td height="70" colspan="2">&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</div>