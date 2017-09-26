/*
 *  如果用户未预约租房，则跳转到首页
 *  防止用户未点击预约租房就直接打开该页面
 */

if (sessionStorage.getItem("Reservable") != 1) {

    window.location.href = "/RentingSSH/pages/index.htm";

}


$(function () {

    $("#spanDeposit").text(sessionStorage.getItem("houseDeposit"));

    checkLogin();

    countBackwards();

    addbtnListener();

});


window.onbeforeunload = function () {

    sessionStorage.clear();

}


/*
*  如果用户未登录，则跳转到首页
*  防止用户未登录直接打开该页面
* */

function checkLogin() {

    var params = {

        url: "/RentingSSH/user/isLogin",

        success: function (responseData) {

            $("#divAccount").empty();

            if (responseData ['msg'] == 'LOGINED' && responseData ['status'] == 1) {

                var $node = $(
                    '<span>欢迎您，' + responseData ['data']['e']['username'] + '     </span>' +

                    '<a href="/RentingSSH/pages/personal.jsp">个人中心</a>' +

                    '<a href=""   onclick="logout()">退出</a>'
                );

                $("#divAccount").append($node);

            } else {

                window.location.href = "/RentingSSH/pages/index.htm";

            }

        },


        error: function () {

        }

    };

    $.ajax(params);

}




//  禁用“确认付款”和“取消订单”按钮的函数，在用户确认付款或取消交易后调用，防止跳转页面前用户再次点击按钮

function disableBtn() {

    $("#btnComfirm").attr("disabled", "true");
    $("#btnCannel").attr("disabled", "true");

}


function comfirmReserve() {

    var orderId = sessionStorage.getItem("orderId");

    var params = {

        url: "/RentingSSH/business/comfirmReserve?tag=1&orderId=" + orderId,

        success: function (data) {

            if (data['status'] == 1 && data['msg'] == 'OK') {

                var innerHtml = "恭喜您成功预定该房屋，系统将跳转到首页" + "<a id='auViewOrders' href='/RentingSSH/pages/personal.jsp'>查看订单</a>";

                reserveFeedback(innerHtml);

            } else {

                var innerHtml = "网络异常，系统将跳转到首页，请重新预约";

                reserveFeedback(innerHtml);

            }

        },

        error: function (data) {

            var innerHtml = "网络异常，系统将跳转到首页，请重新预约";

            reserveFeedback(innerHtml);

        }

    };

    $.ajax(params);

}


function cannelReserve(type) {

    var orderId = sessionStorage.getItem("orderId");

    $.ajax({

        url: "/RentingSSH/business/comfirmReserve?tag=2&orderId=" + orderId,

        success: function (data) {

            if (data['status'] == 1 && data['msg'] == 'OK') {


                var innerHtml = type == 1 ? "取消订单成功，系统将跳转到首页" : "由于您未支付预约金，订单取消，系统将跳转到首页";

                reserveFeedback(innerHtml);

            } else {

                var innerHtml = "网络异常，系统将跳转到首页，请重新预约";

                reserveFeedback(innerHtml);

            }

        },

        error: function (data) {

            var innerHtml = "网络异常，系统将跳转到首页，请重新预约";

            reserveFeedback(innerHtml);

        }

    });

}


function addbtnListener() {

    $("#btnComfirm").click(function () {

        clearInterval(interval);

        disableBtn();

        comfirmReserve();

    });


    $("#btnCannel").click(function () {

        clearInterval(interval);

        disableBtn();

        cannelReserve(1);

    });

}


var interval;

// 交易有效时间倒数

function countBackwards() {

    interval = setInterval(function () {

        var time = $("#spanTime").text();

        if (time == 1) {

            cannelReserve(2);

            disableBtn();


        }

        $("#spanTime").text(--time);

    }, 1000);

}


function reserveFeedback(innerHtml) {

    $("#divTips").html(innerHtml);

    setTimeout(function () {

        window.location.href = "/RentingSSH/pages/index.htm";

    }, 5000);

}
