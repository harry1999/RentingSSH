$(function () {

    $("#auGot").click(function () {

        closeDialogTips();

    });


    checkLogin();

    closeDialogTips();

    getCompanyInfo();

    // 从sessionStorage中获取房屋id，请求并加载该房屋的详细信息

    var houseId = parseInt(localStorage.getItem("houseId"));
    var messagerObj = createMessagerObj(houseId, null, null, null, null, null, 1);
    var responseData = getHouses(messagerObj);

    loadHouseInfo(responseData);


    $("#btnReserveHouse").click(function () {

        beforeReserveHouse(localStorage.getItem("houseId"));

    });

});


/*
 *
 *  检查用户登录状态的函数
 *
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

                var $node = $('<a href="/RentingSSH/pages/login.htm">请登录</a>     ' +

                    '<a href="/RentingSSH/pages/register.htm">马上注册</a>');

                $("#divAccount").append($node);

            }

        },

        error: function () {

        }

    };

    $.ajax(params);

}




// 获取公司信息的函数

function getCompanyInfo() {

    var params = {

        url: "/RentingSSH/common/getCompanyInfo",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var companyInfo = responseData['data']['e'];

                var $node = $(' <P><A class=bold href="">' + companyInfo['name'] + '</A></P>\n' +
                    '<P>资质证书：有</P>\n' +
                    '<P>内部编号:' + companyInfo['innerNo'] + '</P>\n' +
                    '<P>联 系 人：' + companyInfo['contact'] + '</P>\n' +
                    '<P>联系电话：<SPAN>' + companyInfo['hotLine'] + '</SPAN></P>\n' +
                    '<P>手机号码：<SPAN> ' + companyInfo['cellphone'] + '</SPAN></P> ');

                $("div.side").append($node);

            }

        }

    };

    $.ajax(params);
}


// 创建封装了查询参数的对象的函数

function createMessagerObj(houseId, title, priceRange, areaRange, street, type, index) {

    var obj = new Object();

    obj['messager.houseId'] = houseId;
    obj['messager.title'] = title;
    obj['messager.priceRange'] = priceRange;
    obj['messager.areaRange'] = areaRange;
    obj['messager.street'] = street;
    obj['messager.type'] = type;
    obj['messager.index'] = index;

    return obj;

}


// 发送查询参数，获取房屋信息的函数

function getHouses(messageObj) {

    var responseData = new Object();

    var params = {

        url: "/RentingSSH/common/getHouses",

        type: "post",

        data: messageObj,

        async: false,

        datatype: 'json',

        success: function (data) {

            responseData = data;

        },

        error: function (data) {

            openDialogTips("网络异常，请刷新页面");

        }

    };

    $.ajax(params);

    return responseData;

}


// 用户点击预约租房按钮后，先检查用户是否已经登陆
// 如果是，则请求预约并跳转到付款页面，否则跳转到登陆页面

function beforeReserveHouse(houseId) {

    var params = {

        url: "/RentingSSH/user/isLogin",

        success: function (responseData) {

            if (responseData ['msg'] == 'LOGINED' && responseData ['status'] == 1) {

                reserveHouse(houseId);

            } else {

                localStorage.setItem("toPage", "/RentingSSH/pages/detail.htm");

                window.location.href = "/RentingSSH/pages/login.htm";

            }

        },

        error: function () {

        }

    };

    $.ajax(params);

}


function reserveHouse(houseId) {

    var obj = new Object();

    obj['houseId'] = houseId;

    var params = {

        type: "post",

        url: "/RentingSSH/business/reserveHouse",

        data: obj,

        success: function (data) {

            if (data ['status'] == 1 && data ['msg'] == 'OK') {

                sessionStorage.setItem("Reservable", 1);
                sessionStorage.setItem("orderId", data['data']['e']);

                window.location.href = "/RentingSSH/pages/payment.html";

            } else if (data ['status'] == 1 && data ['msg'] == 'LOCKED') {

                openDialogTips("该房屋正在被其他用户预定，请选择其他房屋");

            } else if (data ['status'] == 1 && data ['msg'] == 'ROLE_INVALID') {

                openDialogTips("您所属的角色不是普通用户，不能执行该操作");

            } else {

                openDialogTips("网络异常，请重试");

            }

        },

        error: function (data) {

            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


//  加载房屋信息到当前页面的函数

function loadHouseInfo(responseData) {

    if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

        var houseInfo = responseData['data']['e'];

        localStorage.setItem("houseId", houseInfo['id']);

        sessionStorage.setItem("houseDeposit", houseInfo['deposit']);

        var $div = $("div.houseinfo");

        var $node1 = $(' <H1>' + houseInfo['title'] + '</H1>' +
            ' <DIV class=subinfo>' + houseInfo['issueDate'] + '</DIV> ');

        $div.before($node1);


        if (houseInfo['houseImages'] != null && houseInfo['houseImages'].length != 0) {

            for (var i = 0, k = 0; i < houseInfo['houseImages'].length && k < 5; i++) {


                if (houseInfo['houseImages'][i]['type'] == 'detail') {

                    var url = "/RentingSSH/images/house/" + houseInfo['houseImages'][i]['image']['imagePath'];

                    var $node2 = $('<img src=' + url + '  class="imgHouse"/>  ');

                    $div.append($node2);

                    k++;  // 自增操作必须放在这里，因为前5张图片中可能有一张是main.jpg

                }

            }

        }


        var $node3 = $('<P>户　　型：<SPAN>' + houseInfo['houseType']['type'] + '</SPAN></P>\n' +

            '<P>面　　积：<SPAN>' + houseInfo['area'] + 'm<SUP>2</SUP></SPAN></P>\n' +
            '<P>位　　置：<SPAN>' + houseInfo['subDistrict']['name'] + '</SPAN></P>\n' +
            '<P>联系方式：<SPAN>' + houseInfo['telephone'] + '</SPAN></P>\n' +
            '\n' +
            '\n');

        $div.append($node3);


        if (houseInfo['bookingUser'] != null) {


            var $node4 = $(' <input type="button"  disabled value="预定该房"/>\n' +
                '<span>该房屋已经被预定</span> ');

            $div.append($node4);

        } else {

            var $node4 = $(' <input type="button"  id="btnReserveHouse"  value="预定该房"/>');

            $div.append($node4);

        }

        var $node5 = $('<p>' + houseInfo['description'] + '</p>');

        $("div.content").append($node5);

    }

}


// 点击重置按钮后的响应函数

function onReset() {

    $("input[name=keywords]").val("");

}


// 点击搜索按钮后触发的函数

function onSearch() {

    var keywords = $('input[name=keywords]').val();

    if (keywords != null && keywords != "") {

        localStorage.setItem("keywords", keywords);

        window.location.href = "/RentingSSH/pages/index.htm";

    }

}


//  打开提示框

function openDialogTips(msg) {

    $("#dialogTips").html(msg);

    $("#dialogTips").dialog('open');

}


//  关闭提示框

function closeDialogTips() {

    $("#dialogTips").dialog('close');

}
