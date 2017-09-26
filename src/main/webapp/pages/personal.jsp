<%@ page import="com.harry.entity.User" %>
<%@ page import="com.harry.entity.Order" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.harry.entity.OrderDetail" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.harry.utils.SetToListUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="code" uri="http://java.sun.com/jstl/core_rt" %>


<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="../css/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/icon.css">
    <link rel="stylesheet" type="text/css" href="../css/demo.css">
    <link rel="stylesheet" type="text/css" href="../css/personal.css">
    <link rel="stylesheet" type="text/css" href="../css/common.css">


    <script src="../scripts/jquery.min.js"></script>
    <script src="../scripts/jquery.easyui.min.js"></script>
    <script src="../scripts/personal.js"></script>
    <script src="../scripts/common.js"></script>


    <%

        User user = (User) session.getAttribute("loginUser");

        Set<Order> orders = user.getOrders();

        Map<Integer, OrderDetail> orderDetailMap = new HashMap<>();

        for (Order order : orders) {

            if (order.getOrderDetails() != null && order.getOrderDetails().size() != 0) {

                orderDetailMap.put(order.getId(), SetToListUtil.setToList(order.getOrderDetails()).get(0));

            }

        }


        int count = 0;

        session.setAttribute("user", user);
        session.setAttribute("count", count);
        session.setAttribute("orders", orders);
        session.setAttribute("orderDetailMap", orderDetailMap);

    %>


</head>

<body>

<div id="divAccount">
    <a href="/RentingSSH/pages/index.htm">返回首页</a>
    <a href="" onclick="logout()">退出</a>
</div>

<span class="title">基本资料</span>

<div class="info">

    <table cellspacing="0" cellpadding="5">

        <tr>
            <td>
                真实姓名
            </td>

            <td>
                ${ user.realname }
            </td>
        </tr>

        <tr>
            <td>
                电话号码
            </td>

            <td>
                ${ user.telephone }
            </td>
        </tr>


        <tr>
            <td>
                积分
            </td>

            <td>
                ${ user.point }
            </td>
        </tr>


        <tr>
            <td>
                会员等级
            </td>

            <td>
                ${ user.grade }
            </td>
        </tr>


    </table>


</div>


<span class="title">我的订单</span>


<div class="info">

    <table cellspacing="0" cellpadding="5">

        <tr id="trContent">


            <td>序号</td>
            <td>订单编号</td>
            <td>标题</td>
            <td>房屋面积（平方米）</td>
            <td>租金（月 / 元）</td>
            <td>出租人</td>
            <td>创建时间</td>
            <td>订单状态</td>
            <td>操作</td>


        </tr>


        <code:forEach items="${orders}" var="order">


            <tr>

                <td>${ count = count + 1 }</td>

                <td class="tdOrderId">${order.id}</td>

                <td>${orderDetailMap.get( order.id ).title }</td>

                <td>${orderDetailMap.get( order.id ).area }</td>

                <td>${orderDetailMap.get( order.id ).price }</td>

                <td>${orderDetailMap.get( order.id ).username }</td>

                <td>${order.createDate}</td>

                <code:choose>

                    <code:when test="${ order.status == 1}">

                        <td>待付款</td>

                    </code:when>

                    <code:when test="${ order.status == 2}">

                        <td>已完成</td>

                    </code:when>

                    <code:otherwise>

                        <td>已取消</td>

                    </code:otherwise>

                </code:choose>

                <td><input type="button" name="${order.id}" value="删除"/></td>


            </tr>


        </code:forEach>


    </table>

</div>


<div id="dialogComfirm" class="easyui-dialog" style="padding:5px;width:400px;height:200px;"
     title=" tips " iconCls="icon-ok"
     buttons="#dlg-buttons">

</div>


<div id="dlg-buttons">

    <a href="javascript:void(0)" id="auComfirm" class="easyui-linkbutton" iconCls="icon-ok">确认</a>
    <a class="easyui-linkbutton" id="auCannel" data-options="iconCls:'icon-cancel'"
       style="width:80px">取消</a>

</div>


<div id="dialogTips" class="easyui-dialog" style="padding:5px;width:400px;height:200px;"
     title=" tips " iconCls="icon-ok"
     buttons="#dlg-buttons2">

</div>


<div id="dlg-buttons2">

    <a href="javascript:void(0)" class="easyui-linkbutton" id="auGot" iconCls="icon-ok">Ok</a>

</div>


</body>

</html>
