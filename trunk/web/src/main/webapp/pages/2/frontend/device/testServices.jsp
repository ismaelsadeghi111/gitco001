<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String context = request.getContextPath();
    %>
    <script src="<%=context%>/js/jquery/google-tag-manager.js" type="text/javascript"></script>
    <title>Test DpDevice Services</title>

    <%--<% String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); %>--%>
    <script type="text/javascript" language="JavaScript">

        var millisec = 0;
        var seconds = 0;
        var timer;

        function display() {

            if (millisec >= 9) {
                millisec = 0
                seconds += 1
            }
            else
                millisec += 1
            document.getElementById("seconds").innerHTML = seconds;
            document.getElementById("mseconds").innerHTML = millisec;
            timer = setTimeout("display()", 100);
        }

        function stoptimer() {
            clearTimeout(timer);
            timer = 0;
        }
        function starttimer() {
            display();
        }

        function startstoptimer() {
            if (timer > 0) {
                clearTimeout(timer);
                timer = 0;
            } else {
                display();
            }
        }

        function resettimer() {
            stoptimer();
            millisec = 0;
            seconds = 0;
        }

        function checkSelectedService() {
            display();

            var st = "";
            var item = document.getElementById("stype").value;

            if (item == "menusrv") {
                st = "getMenuSrv"
            } else if (item == "menuspsrv") {
                st = "getSpecialSrv";
            } else if (item == "submenusrv") {
                st = "getSubMenuSrv";
            } else if (item == "submenuspsrv") {
                st = "getSubSpecialSrv";
            } else if (item == "altsrv") {
                st = "getAlternativesSrv";
            } else if (item == "altspsrv") {
                st = "getAlternativeSpecSrv";
            } else if (item == "menusinglesrv") {
                st = "getMenuSingleSrv";
            } else if (item == "menusinglespsrv") {
                st = "getMenuSingleSpecSrv";
            } else if (item == "toppingsrv") {
                st = "getToppingMenuSrv";
            } else if (item == "toppingspsrv") {
                st = "getToppingSpecialSrv";
            } else if (item == "storelistsrv") {
                st = "getStoreListSrv";
            } else if (item == "popularslistsrv") {
                st = "getPopularListSrv";
            } else if (item == "taxsrv") {
                st = "getTax";
            } else if (item == "dpDollarsWeeklysrv") {
                st = "getDPDollarPercent";
            } else if (item == "dpDollaPerUser") {
                st = "getDPDollarService";
            }
            else {
                alert("Please Select One Item.")
            }


            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                data: {operation: 'testServicesResponse', st: st},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });

        }

        function reset() {
            document.getElementById("seconds").innerHTML = "00";
            document.getElementById("mseconds").innerHTML = "00";
            document.getElementById("resultOfService").innerHTML = "";
            stoptimer();
            millisec = 0;
            seconds = 0;
        }

        function checkLoginService() {
            display();

            var uname = document.getElementById("uname").value;
            var passwd = document.getElementById("passwd").value;
            var st = "loginService"
            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                data: {operation: 'testServicesResponse', st: st, uname: uname, passwd: passwd},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }

        function checkRegisterService() {
            display();

            var fname = document.getElementById("fname").value;
            var lname = document.getElementById("lname").value;
            var birthday = document.getElementById("birthday").value;
            var title = document.getElementById("title").value;
            var company = document.getElementById("company").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var subscribed=document.getElementById("subscribed").value;
            var addname = document.getElementById("addname").value;
            var pcode = document.getElementById("pcode").value;
            var streetno = document.getElementById("streetno").value;
            var street = document.getElementById("street").value;
            var ci = document.getElementById("cityorder").value;
            var building = document.getElementById("building").value;
            var phone = document.getElementById("phone").value;
            var ext = document.getElementById("ext").value;
            var sa = document.getElementById("sa").value;
            var dc = document.getElementById("dc").value;

            var st = "registerService"
            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                /*data: {operation: 'testServicesResponse', st: st, fname: fname, lname: lname, birthday: birthday, title: title, company: company, email: email, password: password},*/
                data: {operation: 'testServicesResponse', st: st, fname: fname, lname: lname, birthday: birthday, title: title, company: company, email: email, password: password, addressName: addname, postalCode: pcode, streetNo: streetno, street: street, city: ci, building: building, phone: phone, ext: ext, suiteApt: sa,subscribed: subscribed, doorCode: dc},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }
        function checkDeliveryService() {
            display();

            var fname = document.getElementById("delfname").value;
            var lname = document.getElementById("dellname").value;
            var title = document.getElementById("deltitle").value;
            var company = document.getElementById("delcompany").value;
            var email = document.getElementById("delemail").value;
            var pcode = document.getElementById("delpcode").value;
            var streetno = document.getElementById("delstreetno").value;
            var street = document.getElementById("delstreet").value;
            var ci = document.getElementById("delcityorder").value;
            var building = document.getElementById("delbuilding").value;
            var phone = document.getElementById("delphone").value;
            var ext = document.getElementById("delext").value;
            var sa = document.getElementById("delsa").value;
            var dc = document.getElementById("deldc").value;

            var st = "deliveryService"
            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                /*data: {operation: 'testServicesResponse', st: st, fname: fname, lname: lname, birthday: birthday, title: title, company: company, email: email, password: password},*/
                data: {operation: 'testServicesResponse', st: st, fname: fname, lname: lname, title: title, company: company, email: email, postalCode: pcode, streetNo: streetno, street: street, city: ci, building: building, phone: phone, ext: ext, suiteApt: sa, doorCode: dc},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }
 function checkgetContactInfoService() {
            display();

            var uid = document.getElementById("contactUid").value;
            var fname = document.getElementById("contactfname").value;
            var lname = document.getElementById("contactlname").value;
            var title = document.getElementById("contacttitle").value;
            var company = document.getElementById("contactcompany").value;
            var email = document.getElementById("contactemail").value;
            var pcode = document.getElementById("contactpcode").value;
            var streetno = document.getElementById("contactstreetno").value;
            var street = document.getElementById("contactstreet").value;
            var ci = document.getElementById("contactcityorder").value;
            var building = document.getElementById("contactbuilding").value;
            var phone = document.getElementById("contactphone").value;
            var ext = document.getElementById("contactext").value;
            var sa = document.getElementById("contactsa").value;
            var dc = document.getElementById("contactdc").value;

            var st = "getContactInfoService"
            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                /*data: {operation: 'testServicesResponse', st: st, fname: fname, lname: lname, birthday: birthday, title: title, company: company, email: email, password: password},*/
                data: {operation: 'testServicesResponse', st: st,uid:uid, addressName: fname, lname: lname, title: title, company: company, email: email, postalCode: pcode, streetNo: streetno, street: street, city: ci, building: building, phone: phone, ext: ext, suiteApt: sa, doorCode: dc},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }
 function checkgetDPDollarService() {
            display();

            var uid = document.getElementById("dpdollarUid").value;
            var st = "getDPDollarService"
            $.ajax({
                method: 'GET',
                url: '<%=context%>/frontend.do',
                data: {operation: 'testServicesResponse', st: st,uid:uid},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }

        function checkOrderService() {
            display();

            var basketItems= [];
            var order={ userId:"100",addressId:"1000",deliveryType:"PICKUP",storeId:"021",paymentType:"CASH",orderPrice:"500"}
            basketItems=[{
                          productNoRefrence:"1"
                         ,groupIdRefrence:"1"
                         ,classType:""
                         ,quantity:""
                ,basketSingleItemList:{
                     id:"1"
                    ,groupId:"1"
                    ,basketquantity:"1"
                    ,selectedToppings:{
                         key:"1"
                        ,value:"1"

                    }
                }
            }
            ];

            var orduserId = document.getElementById("orduserId").value;
            var ordaddressId = document.getElementById("ordaddressId").value;
            var orddeliveryType = document.getElementById("orddeliveryType").value;
            var ordcompany = document.getElementById("ordcompany").value;
            var ordpaymentType = document.getElementById("ordpaymentType").value;
            var ordproductNoRefrence = document.getElementById("ordproductNoRefrence").value;
            var ordgroupIdRefrencee = document.getElementById("ordgroupIdRefrencee").value;
            var ordclassType = document.getElementById("ordclassType").value;
            var ordquantity = document.getElementById("ordquantity").value;
            var ordBasketId = document.getElementById("ordBasketId").value;
            var phone = document.getElementById("ordgroupId").value;
            var ordbasketquantity = document.getElementById("ordbasketquantity").value;
            var ordkey = document.getElementById("ordkey").value;
            var ordvalue = document.getElementById("ordvalue").value;

            var st = "orderService"

            $.ajax({
                method: 'POST',
                url: '<%=context%>/frontend.do',
/*                contentType: 'application/json',
                operation: 'testServicesResponse',
                st: st,
                order:order,
                data: { json: JSON.stringify({
                    productNoRefrence:"1"
                    ,groupIdRefrence:"1"
                    ,classType:""
                    ,quantity:""
                    ,basketSingleItemList:{
                        id:"1"
                        ,groupId:"1"
                        ,basketquantity:"1"
                        ,selectedToppings:{
                            key:"1"
                            ,value:"1"

                        }
                    }
                })},
              dataType: 'json',*/
                data: {operation: 'testServicesResponse', st: st, basketItems: basketItems,order:order},
                success: function (response) {
                    document.getElementById("resultOfService").innerHTML = response;
                    stoptimer();
                },
                failure: function (e) {
                    alert("Error: " + e);
                }
            });
        }

        function divSelect() {
            var a = document.getElementById('stype').value;
            if (a == "loginu") {
                document.getElementById('lin').style.display = 'block';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
            }
            else if (a == "regu") {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
                document.getElementById('reg').style.display = 'block';
            }
            else if (a == "ord") {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
                document.getElementById('ord').style.display = 'block';
            }
            else if (a == "order") {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('order').style.display = 'block';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
            }
            else if (a == "contact") {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
                document.getElementById('contact').style.display = 'block';
            }
            else if (a == "dpDollar") {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'block';
            }
            else {
                document.getElementById('lin').style.display = 'none';
                document.getElementById('reg').style.display = 'none';
                document.getElementById('order').style.display = 'none';
                document.getElementById('ord').style.display = 'none';
                document.getElementById('contact').style.display = 'none';
                document.getElementById('dpDollar').style.display = 'none';
            }

        }

    </script>
</head>

<body>
<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-P29MS2G"
                  height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->
<br/>
<br/>

<div style="padding: 5px; margin: 5px; border: 2px darkgray solid; width: 75%; margin-left: 5px; float: right;"
     dir="ltr"/>


<label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
    Service :
</label>
<br/>
&nbsp;
<select name="stype" id="stype"
        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif;"
        onchange="divSelect();">
    <option value="0">Please Select ...</option>
    <option value="menusrv">Check Menu Service</option>
    <option value="menuspsrv">Check Menu Special Service</option>
    <option value="submenusrv">Check SubMenu Service</option>
    <option value="submenuspsrv">Check SubSpecial Service</option>
    <option value="altsrv">Check Alternatives Service</option>
    <option value="altspsrv">Check Alternatives Special Service</option>
    <option value="menusinglesrv">Check Menu Single Item Service</option>
    <option value="menusinglespsrv">Check Menu Single Item Special Service</option>
    <option value="toppingsrv">Check Topping Service</option>
    <option value="toppingspsrv">Check Topping Special Service</option>
    <option value="storelistsrv">Check Stores List Service</option>
    <option value="popularslistsrv">Check Popular items List Service</option>
    <option value="loginu">Login User Service</option>
    <option value="regu">Register User Service</option>
    <option value="ord">Delivery  Service</option>
    <option value="order">Order  Service</option>
    <option value="contact">Get Contact Info  Service</option>
    <option value="taxsrv">Get Tax Service</option>
    <option value="dpDollarsWeeklysrv">Get Weekly DPDollar</option>
    <option value="dpDollar">Get DPDollar per User</option>

</select>
&nbsp;
<button type="button" onclick="checkSelectedService();">Check Selected Srvice</button>
&nbsp;
<button type="button" onclick="reset();">reset</button>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<label id="seconds">00</label>&nbsp;s&nbsp;:&nbsp;<label id="mseconds">00</label>&nbsp;ms

<br/><br/>

<div id="lin" style="display:none;">
    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        Login Service :
    </label>
    <br/>
    &nbsp;
    User name <input type="text" name="uname" id="uname"
                     style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif;">
    &nbsp;
    Password <input type="password" name="passwd" id="passwd"
                    style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif;">
    &nbsp;
    <button type="button" onclick="checkLoginService();">Login</button>
</div>
<br/><br/>


<div id="reg" style="display:none;">
    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        Register Service :
    </label>
    <br/>
    <table border="0" cellpadding="2" cellspacing="2" width="auto">
        <tr>
            <td>First name</td>
            <td><input type="text" name="fname" id="fname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>

            <td>Last name</td>
            <td><input type="text" name="lname" id="lname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>birthday</td>
            <td><input type="text" name="birthday" id="birthday"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>

            <td>subscribed</td>
            <td><select name="subscribed" id="subscribed"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="TRUE">Yes</option>
                <option value="FALSE">No</option>
            </select></td>
            <td>Title</td>
            <td><select name="title" id="title"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="MALE">Mr</option>
                <option value="FEMALE">Ms</option>
            </select></td>
        </tr>

        <tr>
            <td>Company</td>
            <td><input type="text" name="company" id="company"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Email</td>
            <td><input type="text" name="email" id="email"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>
        <tr>
            <td>password</td>
            <td><input type="password" name="password" id="password"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>

        <tr>
            <td>Address Name</td>
            <td><input type="text" name="addname" id="addname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Postal Code</td>
            <td><input type="text" name="pcode" id="pcode"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Street NO.</td>
            <td><input type="text" name="streetno" id="streetno"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Street</td>
            <td><input type="text" name="street" id="street"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Suit/Apt.</td>
            <td><input type="text" name="sa" id="sa"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>City</td>
            <td><input type="text" name="cityorder" id="cityorder"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Door Code</td>
            <td><input type="text" name="dc" id="dc"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Building</td>
            <td><input type="text" name="building" id="building"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Phone</td>
            <td><input type="text" name="phone" id="phone"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Ext</td>
            <td><input type="text" name="ext" id="ext"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <td>
        <td colspan="2">
            &nbsp;
        </td>
        <td colspan="2">
            <button type="button" onclick="checkRegisterService();">Register</button>
        </td>
        </tr>
    </table>
</div>

<div id="ord" style="display:none;">

    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        Register Service :
    </label>
    <br/>
    <table border="0" cellpadding="2" cellspacing="2" width="auto">
        <tr>
            <td>First name</td>
            <td><input type="text" name="firstName" id="delfname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>

            <td>Last name</td>
            <td><input type="text" name="lastName" id="dellname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>

            <td>Title</td>
            <td><select name="title" id="deltitle"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="Mr">Mr</option>
                <option value="Ms">Ms</option>
            </select></td>
        </tr>

        <tr>
            <td>Company</td>
            <td><input type="text" name="company" id="delcompany"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Email</td>
            <td><input type="text" name="email" id="delemail"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>

        <tr>
            <td>Postal Code</td>
            <td><input type="text" name="postalCode" id="delpcode"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Street NO.</td>
            <td><input type="text" name="streetno" id="delstreetno"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Street</td>
            <td><input type="text" name="street" id="delstreet"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Suit/Apt.</td>
            <td><input type="text" name="suiteApt" id="delsa"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>City</td>
            <td><input type="text" name="city" id="delcityorder"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Door Code</td>
            <td><input type="text" name="doorCode" id="deldc"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Building</td>
            <td><input type="text" name="building" id="delbuilding"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Phone</td>
            <td><input type="text" name="phone" id="delphone"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Ext</td>
            <td><input type="text" name="ext" id="delext"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <td>
        <td colspan="2">
            &nbsp;
        </td>
        <td colspan="2">
            <button type="button" onclick="checkDeliveryService();">Submit</button>
        </td>
        </tr>
    </table>
</div>

<div id="contact" style="display:none;">

    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        getcontactinfo Service :
    </label>
    <br/>
    <table border="0" cellpadding="2" cellspacing="2" width="auto">
        <tr>
            <td>User Id</td>
            <td><input type="text" name="contactUid" id="contactUid"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>
        <tr>
            <td>First name</td>
            <td><input type="text" name="firstName" id="contactfname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>

            <td>Last name</td>
            <td><input type="text" name="lastName" id="contactlname"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>

            <td>Title</td>
            <td><select name="title" id="contacttitle"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="mr">Mr</option>
                <option value="ms">Ms</option>
            </select></td>
        </tr>

        <tr>
            <td>Company</td>
            <td><input type="text" name="company" id="contactcompany"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Email</td>
            <td><input type="text" name="email" id="contactemail"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>

        <tr>
            <td>Postal Code</td>
            <td><input type="text" name="postalCode" id="contactpcode"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Street NO.</td>
            <td><input type="text" name="streetno" id="contactstreetno"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Street</td>
            <td><input type="text" name="street" id="contactstreet"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Suit/Apt.</td>
            <td><input type="text" name="suiteApt" id="contactsa"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>City</td>
            <td><input type="text" name="city" id="contactcityorder"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Door Code</td>
            <td><input type="text" name="doorCode" id="contactdc"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Building</td>
            <td><input type="text" name="building" id="contactbuilding"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>
            <td>Phone</td>
            <td><input type="text" name="phone" id="contactphone"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>Ext</td>
            <td><input type="text" name="ext" id="contactext"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <td>
        <td colspan="2">
            &nbsp;
        </td>
        <td colspan="2">
            <button type="button" onclick="checkgetContactInfoService();">Submit</button>
        </td>
        </tr>
    </table>
</div>

<div id="dpDollar" style="display:none;">

    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        getcontactinfo Service :
    </label>
    <br/>
    <table border="0" cellpadding="2" cellspacing="2" width="auto">
        <tr>
            <td>User Id</td>
            <td><input type="text" name="dpdollarUid" id="dpdollarUid"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <td colspan="2">
            <button type="button" onclick="checkgetDPDollarService();">Submit</button>
        </td>
        </tr>
    </table>
</div>
<div id="order" style="display:none;">

    <label style="color: #000000; font-size: 12px; font-family: serif; font-weight: bold;">
        Order Service :
    </label>
    <br/>
    <table border="0" cellpadding="2" cellspacing="2" width="auto">
        <tr>
            <td>userId</td>
            <td><input type="text" name="userId" id="orduserId"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>

            <td>addressId</td>
            <td><input type="text" name="addressId" id="ordaddressId"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
        </tr>

        <tr>

            <td>deliveryType</td>
            <td><select name="deliveryType" id="orddeliveryType"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="mr">PICKUP</option>
                <option value="ms">DELIVERY</option>
            </select></td>
        </tr>

        <tr>
            <td>storeId</td>
            <td><input type="text" name="storeId" id="ordcompany"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
            </td>
            <td>paymentType</td>
            <td><select name="deliveryType" id="ordpaymentType"
                        style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                <option value="cash">Cash</option>
                <option value="visa">Visa Card</option>
                <option value="debit">Debit</option>
                <option value="master">Master Card</option>
            </select></td>
        </tr>
    </table>
    <fieldset title="Basket">
        <legend>basketItems</legend>
        <table border="0" title="">
            <tr>
                <td  width="50%">productNoRefrence</td>
                <td  width="50%">
                <input type="text" name="productNoRefrence" id="ordproductNoRefrence"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                </td>
                </tr>
            <tr>
                <td>groupIdRefrence</td>
                <td>
                <input type="text" name="groupIdRefrence" id="ordgroupIdRefrencee"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                </td>
            </tr>
            <tr>
                <td>classType</td>
                <td><select name="classType" id="ordclassType"
                            style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                    <option value="MenuSingleItem">Single Menu Item</option>
                    <option value="CombinedMenuItem">Combined Menu Item</option>
                </select></td>
                </tr>
            <tr>
                <td>quantity</td>
                <td>
                <input type="text" name="quantity" id="ordquantity"
                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                </td>
            </tr>
            <tr>
                <td>
                <fieldset title="BasketSingle">
                    <legend>basketSingleItemList</legend>
                    <table title="">
                        <tr>
                            <td>id</td>
                            <td>
                                <input type="text" name="BasketId" id="ordBasketId"
                                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                            </td>
                            </tr>
                        <tr>
                            <td>groupId</td>
                            <td>
                                <input type="text" name="groupId" id="ordgroupId"
                                       style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                            </td>
                        </tr>
                        <tr>
                        <td>basketquantity</td>
                        <td>
                            <input type="text" name="quantity" id="ordbasketquantity"
                                   style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                        </td>
                        </tr>
                        <tr>
                            <td>
                            <fieldset title="selectedToppingMap">
                                <legend>selectedToppings</legend>
                                <table title="">
                                    <tr>
                                        <td>key</td>
                                        <td>
                                            <input type="text" name="key" id="ordkey"
                                                   style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                                        </td>
                                        <td>value</td>
                                        <td>
                                            <input type="text" name="value" id="ordvalue"
                                                   style="border: 1px orange solid; background-color: #FF991A; font-size: 12px; color: #000000; font-family: serif; margin: 3px;">
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                </td>
            </tr>
        </table>
    </fieldset>
    <button onclick="checkOrderService();" >Send Order</button>
</div>

<br/>


<div id="resultOfService" style="width: 75%; height: 20%; border: 2px solid #000000; overflow: auto;"></div>

</div>
</body>
</html>
