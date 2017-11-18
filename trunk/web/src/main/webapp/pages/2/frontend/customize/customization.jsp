<%--
  Created by IntelliJ IDEA.
  User: Hossein.Sadeghifard
  Date: 10/6/13
  Time: 12:03 PM
  To change this te
  mplate use File | Settings | File Templates.
--%>
<%@ page import="com.sefryek.doublepizza.model.Basket" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.doublepizza.model.CombinedMenuItem" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.sefryek.doublepizza.model.MenuSingleItem" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="cc" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sefryek.doublepizza.core.Constant" %>
<%@ page import="com.sefryek.doublepizza.web.action.FrontendAction" %>
<%@ page import="com.sefryek.common.util.serialize.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.sefryek.doublepizza.model.*" %>
<%@ page import="com.sefryek.doublepizza.InMemoryData" %>
<%@ page import="com.sefryek.common.MessageUtil" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic" %>
<%
    String context = request.getContextPath();
    Integer liCounter = 0;
    Integer xLiCounter = 0;
    String middlePath = InMemoryData.getImageServicePath(Constant.DATA_RESOURCES_FOODS_PATH);
%>


<!-- Middle Block Div Start Here -->
<div id="MiddleBlock" class="nav-line">
<h1>Cutomize Your Food <small style="font:18px Calibri">&raquo; 2 For 1 Pizza (Medium)</small></h1>
<div class="MiddleWrapper">
<table class="border Fullbasket-tilte" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="60%">First Pizza, Second Pizza</td>
        <td width="10%">
            <a class="add" href="#"><img src="<%=context%>/images/sub.png" alt="Sub"></a>
            <input type="text" class="popup-text-box" value="1">
            <a class="add" href="#"><img src="<%=context%>/images/add.png" alt="Add"></a>
        </td>
        <td width="16%"><a href="#" class="orange-btn">Default Details</a></td>
        <td width="14%"><a href="#" class="orange-btn">Add to Cart</a></td>
    </tr>
</table>
<div class="for_1_pizza_left">
    <div style="position:absolute; top:20px;">
        <div class="left_box active">
            <strong class="title">First Pizza</strong>
            <span class="des">All Deresed</span>
            <%--<span class="price">$12.40</span>--%>
            <label style="display:inline-block;width:94px;" id="down_price">${requestScope.price} $</label>
        </div>
        <div class="left_box">
            <strong class="title">Second Pizza</strong>
            <span class="des">Pepperoni</span>
            <span class="price">$43.59</span>
        </div>
    </div>
</div>
<div class="for_1_pizza_right">
<%--====== image box and alternative--%>
<div style="float:left; padding-bottom:30px; width:100%;" class="border">
    <div class="ImageBox">
        <div><img  src="<%=context%><%= middlePath%>${requestScope.imageAddress}"  width="256" height="194" alt="Double pizza" /></div>
        <div style="margin:10px 0px;"><img src="<%=context%>/images/fb-2.png" alt="Facebook" /></div>
        <div class="foodname FloatLeft">Hawaiian</div><div class="foodname FloatRight"><bean:message key="label.price"/> </div>
    </div>
    <%--================= Start Alternatives--%>
    <div style=" width:570px; float:right; margin-top:5px;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Cheese
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Pepperoni
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    All Dressed
                </label></td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Mexican
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Halal
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Quebecoise
                </label></td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Pilly Steak
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Meat Lovet
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Vegetarian
                </label></td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Hot Dog
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Smoked Meat
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Parmesan
                </label></td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Hawaiian
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Chicken
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Mediter/Parme
                </label></td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Godfather
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    BBQ Chicken
                </label>


                </td>
                <td><label class="button">
                    <input type="radio" name="button" />
                    <span class="outer"><span class="inner"></span></span>
                    Pulled Porc
                </label></td>
            </tr>
        </table>
    </div>
    <%--================= end Alternatives--%>

</div>
<%--====== how cooked--%>
<table style="margin:20px 0 0 0; float:left;" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="49%" class="how-cooked">How Cooked?</td>
        <td width="2%">&nbsp;</td>
        <td width="49%" class="how-cooked">How Crust?</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>

    <tr>
        <td><table class="how-cooked-box"  width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><label class="button">
                    <input type="radio" name="button2" />
                    <span class="outer"><span class="inner"></span></span>
                    Cooked Normal
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button2" />
                    <span class="outer"><span class="inner"></span></span>
                    Wll Done
                </label>
                </td>
                <td><label class="button">
                    <input type="radio" name="button2" />
                    <span class="outer"><span class="inner"></span></span>
                    Lightly Cooked
                </label></td>
            </tr>
        </table>
        </td>
        <td>&nbsp;</td>
        <td><table class="how-cooked-box"  width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><label class="button">
                    <input type="radio" name="button3" />
                    <span class="outer"><span class="inner"></span></span>
                    Regular Crust
                </label></td>
                <td><label class="button">
                    <input type="radio" name="button3" />
                    <span class="outer"><span class="inner"></span></span>
                    Thin Crust
                </label>
                </td>
                <td><label class="button">
                    <input type="radio" name="button3" />
                    <span class="outer"><span class="inner"></span></span>
                    Thick Crust
                </label></td>
            </tr>
        </table></td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td><a class="orange-btn FloatRight" href="#">Reset</a></td>
    </tr>
</table>
</div>
<div class="Clear"></div>
</div>
<br />
<div class="MiddleWrapper" style="padding:35px 35px;">
    <div class="menu-block">
        <div class="menu-title">
            <div class="icon"><img src="<%=context%>/images/dairy.png" alt="Dairy" /></div>
            <span class="name">Dairy</span>
        </div>
        <div class="menu-middle-box">
            <ul>
                <li>
                    <input id="demo_box_1" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_1" name="demo_lbl_1" class="css-label">Extra Cheese</label>
                </li>
                <li>
                    <input id="demo_box_2" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_2" name="demo_lbl_2" class="css-label">Parmesan</label>
                </li>
                <li>
                    <input id="demo_box_3" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_3" name="demo_lbl_3" class="css-label">Double Cheese</label>
                </li>
                <li>
                    <input id="demo_box_4" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_4" name="demo_lbl_4" class="css-label">Feta Cheese</label>
                </li>
                <li>
                    <input id="demo_box_5" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_5" name="demo_lbl_5" class="css-label">Light Cheese</label>
                </li>
                <li>
                    <input id="demo_box_6" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_6" name="demo_lbl_6" class="css-label">No Cheese</label>
                </li>
            </ul>
        </div>
    </div>
    <div class="menu-block">
        <div class="menu-title">
            <div class="icon"><img src="<%=context%>/images/meats.png" alt="Meats" /></div>
            <span class="name">Meats</span>
        </div>
        <div class="menu-middle-box">
            <ul>
                <li>
                    <input id="demo_box_7" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_7" name="demo_lbl_7" class="css-label">Pepperoni</label>
                </li>
                <li>
                    <input id="demo_box_8" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_8" name="demo_lbl_8" class="css-label">Halal Pepperoni</label>
                </li>
                <li>
                    <input id="demo_box_9" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_9" name="demo_lbl_9" class="css-label">Bacon</label>
                </li>
                <li>
                    <input id="demo_box_10" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_10" name="demo_lbl_10" class="css-label">Italian Ham</label>
                </li>
                <li>
                    <input id="demo_box_11" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_11" name="demo_lbl_11" class="css-label">Italian Sausage</label>
                </li>
                <li>
                    <input id="demo_box_12" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_12" name="demo_lbl_12" class="css-label">Ground Beef</label>
                </li>
                <li>
                    <input id="demo_box_13" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_13" name="demo_lbl_13" class="css-label">Anchovies</label>
                </li>
                <li>
                    <input id="demo_box_14" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_14" name="demo_lbl_14" class="css-label">Philly Steak</label>
                </li>
                <li>
                    <input id="demo_box_15" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_15" name="demo_lbl_15" class="css-label">Chicken Strips</label>
                </li>
                <li>
                    <input id="demo_box_16" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_16" name="demo_lbl_16" class="css-label">Smoked Meat</label>
                </li>
                <li>
                    <input id="demo_box_17" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_17" name="demo_lbl_17" class="css-label">Meatballs</label>
                </li>
                <li>
                    <input id="demo_box_18" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_18" name="demo_lbl_18" class="css-label">Pulled Pork</label>
                </li>
                <li>
                    <input id="demo_box_19" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_19" name="demo_lbl_19" class="css-label">Hot-Dogs</label>
                </li>
            </ul>
        </div>
    </div>
    <div class="menu-block">
        <div class="menu-title">
            <div class="icon"><img src="<%=context%>/images/vegetarian.png" alt="Vegetarian" /></div>
            <span class="name">Vegetarian</span>
        </div>
        <div class="menu-middle-box">
            <ul>
                <li>
                    <input id="demo_box_20" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_20" name="demo_lbl_20" class="css-label">Mushrooms</label>
                </li>
                <li>
                    <input id="demo_box_21" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_21" name="demo_lbl_21" class="css-label">Green Peppers</label>
                </li>
                <li>
                    <input id="demo_box_22" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_22" name="demo_lbl_22" class="css-label">Green Olives</label>
                </li>
                <li>
                    <input id="demo_box_23" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_23" name="demo_lbl_23" class="css-label">Kalamata Olives</label>
                </li>
                <li>
                    <input id="demo_box_24" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_24" name="demo_lbl_24" class="css-label">Tomato Slices</label>
                </li>
                <li>
                    <input id="demo_box_25" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_25" name="demo_lbl_25" class="css-label">Onions</label>
                </li>
                <li>
                    <input id="demo_box_26" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_26" name="demo_lbl_26" class="css-label">Hot Banana Peppers</label>
                </li>
                <li>
                    <input id="demo_box_27" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_27" name="demo_lbl_27" class="css-label">Pineapple</label>
                </li>
                <li>
                    <input id="demo_box_28" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_28" name="demo_lbl_28" class="css-label">Jalapino Peppers</label>
                </li>
            </ul>
        </div>
    </div>
    <div class="menu-block" style="margin:0px;">
        <div class="menu-title">
            <div class="icon"><img src="<%=context%>/images/gift.png" alt="Free" /></div>
            <span class="name">Free</span>
        </div>
        <div class="menu-middle-box">
            <ul>
                <li>
                    <input id="demo_box_29" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_29" name="demo_lbl_29" class="css-label">Oregano</label>
                </li>
                <li>
                    <input id="demo_box_30" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_30" name="demo_lbl_30" class="css-label">Light Sauce</label>
                </li>
                <li>
                    <input id="demo_box_31" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_31" name="demo_lbl_31" class="css-label">No Sauce</label>
                </li>
                <li>
                    <input id="demo_box_32" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_32" name="demo_lbl_32" class="css-label">Garlic</label>
                </li>
                <li>
                    <input id="demo_box_33" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_33" name="demo_lbl_33" class="css-label">Chili Peppers</label>
                </li>
                <li>
                    <input id="demo_box_34" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_34" name="demo_lbl_34" class="css-label">Extra Sauce</label>
                </li>
            </ul>
        </div>
    </div>
    <div class="menu-block" style="margin:0px;">
        <div class="menu-title">
            <div class="icon"><img src="<%=context%>/images/sauce.png" alt="Sauce" /></div>
            <span class="name">Sauce</span>
        </div>
        <div class="menu-middle-box">
            <ul>
                <li>
                    <input id="demo_box_35" class="css-checkbox" type="checkbox" />
                    <label for="demo_box_35" name="demo_lbl_35" class="css-label">BBQ Sauce</label>
                </li>
            </ul>
        </div>
    </div>
    <div class="Clear"></div>
</div>
</div>
<!-- Middle Block Div End Here -->


