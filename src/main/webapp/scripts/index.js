//  定义全局变量

var $tbody;

var currentPage = 1;
var totalPage = 1;


$(function () {

    $tbody = $("table[class=house-list] tbody");

    checkLogin();

    getHouseTypes();

    getDistricts();

    /*
     *  页面加载后，
     *  初始化页面展示的房屋信息，
     *  给每个下拉选框添加监听，
     *  给搜索按钮和重置按钮添加监听
     * */

    init();

    onGetwithTitle();

    onReset();

    onHouseOptionChanged($("#price"));
    onHouseOptionChanged($("#area"));
    onHouseOptionChanged($("#street"));
    onHouseOptionChanged($("#type"));

    toPage($("a[name='1']"));
    toPage($("a[name='2']"));
    toPage($("a[name='3']"));
    toPage($("a[name='4']"));

});


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




//  获取所有房屋类型，设置到下拉列表

function getHouseTypes() {

    var params = {

        url: "/RentingSSH/common/getHouseTypes",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $select = $("select#type");

                $select.append('<OPTION value="0">不限</OPTION>');

                var houseTypes = responseData['data']['list'];

                for (var i = 0; i < houseTypes.length; i++) {

                    var $node = $('<OPTION value=' + houseTypes[i]['id'] + '>' + houseTypes[i]['type'] + '</OPTION>');

                    $select.append($node);

                }

            }

        },

        error: function (data) {

        }

    };

    $.ajax(params);

}


//  初始化街道信息，设置到下拉框

function getDistricts() {

    var params = {

        url: "/RentingSSH/common/getDistricts?tag=2",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $selectStreet = $('#street');

                $selectStreet.append('<OPTION value="0" >不限</OPTION>');

                var subDistrictsInfosObject = responseData['data']['list'];

                var subDistrictOptionArray = createDistrictNodes(subDistrictsInfosObject);

                for (var i = 0; i < subDistrictOptionArray.length; i++) {

                    $selectStreet.append(subDistrictOptionArray[i]);

                }

            }

        },
        error: function (data) {

        }

    };

    $.ajax(params);

}


//  创建地区或者街道下拉节点的函数

function createDistrictNodes(districtInfoObject) {

    var optionArray = new Array();

    for (var i = 0; i < districtInfoObject.length; i++) {

        var $node = $('<OPTION value=' + districtInfoObject[i]['id'] + '>' + districtInfoObject[i]['name'] + '</OPTION>');

        optionArray.push($node);

    }

    return optionArray;
}


// 创建封装了查询条件的对象的函数，函数返回的封装对象发送给服务器去获取房屋数据

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


//  页面初始化函数，加载不经过任何条件刷选的第一页房屋信息，页面加载时和点击重置按钮时都要调用

function init() {

    if (localStorage.getItem("keywords") != null) {

        var messagerObj = createMessagerObj(null, localStorage.getItem("keywords"), null, null, null, null, 1);

        var responseData = getHouses(messagerObj);

        loadHousesInfo(responseData);

        localStorage.removeItem("keywords");

    } else {

        $("#price").val("0");
        $("#street").val("0");
        $("#type").val("0");
        $("#area").val("0");

        var messagerObj = createMessagerObj(null, null, null, null, null, null, 1);
        var responseData = getHouses(messagerObj);

        loadHousesInfo(responseData);

    }

}


// 点击重置按钮时触发的方法，清空搜索框，重置下拉框

function onReset() {

    $("input[name=reset]").click(function () {

        $("input[name=title]").val("");

        init();

    });

}


//  点击查询按钮时触发的函数，获取与title模糊匹配，且不经过option过滤的房屋信息

function onGetwithTitle() {

    $("input[name=search]").click(function () {

        var title = $("input[name=title]").val();

        if (title != null && title != "") {

            var messagerObj = createMessagerObj(null, title, null, null, null, null, 1);

            var responseData = getHouses(messagerObj);

            loadHousesInfo(responseData);

        }

    });

}


// 监听下拉框选项变化的函数，每个下拉框都要被监听

function onHouseOptionChanged($select) {

    $select.change(function () {

        var title = $("input[name=title]").val();
        var priceRange = $("#price").val();
        var areaRange = $("#area").val();
        var street = $("#street").val();
        var type = $("#type").val();

        var messagerObj = createMessagerObj(null, title, priceRange, areaRange, street, type, 1);

        var responseData = getHouses(messagerObj);

        loadHousesInfo(responseData);

    });

}


// 发送查询参数，获取服务器返回房屋信息的函数

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

        }

    };

    $.ajax(params);

    return responseData;

}


// 用户点击某个房屋信息时，保存该房屋的id，以便在房屋详情页面获取

function saveHouseId(id) {

    localStorage.setItem("houseId", id);

}


// 获取房屋的主图名字的函数

function getMainImageName(imageList) {

    var mainImgName = "";

    for (var i = 0; i < imageList.length; i++) {

        if (imageList[i]['type'] == 'main') {

            mainImgName = imageList[i]['image']['imagePath'];

        }

    }

    return mainImgName;

}


// 根据返回的数据，设置页面显示的页码，以及导航按钮的颜色

function onDataLoad(responseData) {

    if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

        var pageObject = responseData['data']['e'];
        totalPage = pageObject['totalPage'];

    } else {

        // 如果没有查询到任何房屋信息，则应该把totalPage重置为1

        totalPage = 1;

    }

    currentPage = ( totalPage == 1 ) ? 1 : currentPage;

    $("span.total").text(currentPage + "/" + totalPage + "页");

    // 如果只有一页数据，那么全部按钮不可点击
    // 每次加载房屋数据之前必须将按钮重置，再重新判断

    $("a[name='1']").css("color", " rgb(0, 0, 238)");
    $("a[name='2']").css("color", " rgb(0, 0, 238)");
    $("a[name='3']").css("color", " rgb(0, 0, 238)");
    $("a[name='4']").css("color", " rgb(0, 0, 238)");

    if (totalPage == 1) {

        $("a[name='1']").css("color", "gray");
        $("a[name='2']").css("color", "gray");
        $("a[name='3']").css("color", "gray");
        $("a[name='4']").css("color", "gray");
    }

    if (currentPage == 1) {

        $("a[name='1']").css("color", "gray");
        $("a[name='2']").css("color", "gray");
    }


    if (currentPage == totalPage) {

        $("a[name='3']").css("color", "gray");
        $("a[name='4']").css("color", "gray");

    }

}


// 监听每个导航按钮的点击事件

function toPage($au) {

    $au.click(function () {

        if (totalPage != 1 && $au.css("color") != "rgb(128, 128, 128)") {

            var index = 1;

            switch ($au.attr("name")) {

                case "1":

                    index = 1;
                    currentPage = 1;
                    break;

                case "2":

                    index = --currentPage;

                    break;

                case "3":

                    index = ++currentPage;

                    break;

                case "4":

                    index = totalPage;
                    currentPage = totalPage;

                    break;

            }

            var title = $("input[name=title]").val();
            var priceRange = $("#price").val();
            var areaRange = $("#area").val();
            var street = $("#street").val();
            var type = $("#type").val();


            var messagerObj = createMessagerObj(null, title, priceRange, areaRange, street, type, index);

            var responseData = getHouses(messagerObj);

            loadHousesInfo(responseData);

        }

    });


}


// 将从服务器返回的房屋信息加以展示的函数，如果没有房屋信息，则给出提示

function loadHousesInfo(responseData) {

    $tbody.empty();

    if (responseData['status'] == 2 && responseData['msg'] == 'EMPTY') {

        onDataLoad(responseData);

        $tbody.append('<span>不存在匹配的房源信息，请重新搜索</span>');

        return;

    }

    if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

        onDataLoad(responseData);

        var houseList = responseData['data']['list'];

        for (var i = 0; i < houseList.length; i++) {

            var url = "/RentingSSH/images/house/" + getMainImageName(houseList[i]['houseImages']);

            if ((i + 1) % 2 == 1) {

                $node = $(' <TR><TD class=house-thumb><span>\n' +
                    '      <A href="/RentingSSH/pages/detail.htm"  onclick="saveHouseId( ' + houseList[i]['id'] + ' )"   target="_blank">\n' +
                    '      <img src=' + url + ' width="100" height="75" alt="">\n' +
                    '      </a>\n' +
                    '    </span></TD>\n' +
                    '    <TD>\n' +
                    '      <DL>\n' +
                    '        <DT><A href="/RentingSSH/pages/detail.htm"  onclick="saveHouseId( ' + houseList[i]['id'] + ' )"   target="_blank">' + houseList[i]['title'] + '</A></DT>\n' +
                    '        <DD>' + houseList[i]['subDistrict']['name'] + ',' + houseList[i]['area'] + '平米<BR>\n' +
                    '联系方式：' + houseList[i]['telephone'] + '</DD></DL></TD>\n' +
                    '    <TD class=house-type> ' + houseList[i]['houseType']['type'] + '</TD>\n' +
                    '    <TD class=house-price><SPAN>' + houseList[i]['price'] + '</SPAN>元/月</TD>\n' +
                    '  </TR> ');

                $tbody.append($node);

            } else {

                $node = $(' <TR class="odd"><TD class=house-thumb><span>\n' +
                    '      <A href="/RentingSSH/pages/detail.htm"  onclick="saveHouseId( ' + houseList[i]['id'] + ' )"   target="_blank">\n' +
                    '      <img src=' + url + ' width="100" height="75" alt="">\n' +
                    '      </a>\n' +
                    '    </span></TD>\n' +
                    '    <TD>\n' +
                    '      <DL>\n' +
                    '        <DT><A href="/RentingSSH/pages/detail.htm"  onclick="saveHouseId( ' + houseList[i]['id'] + ' )"   target="_blank">' + houseList[i]['title'] + '</A></DT>\n' +
                    '        <DD>' + houseList[i]['subDistrict']['name'] + ',' + houseList[i]['area'] + '平米<BR>\n' +
                    '联系方式：' + houseList[i]['telephone'] + '</DD></DL></TD>\n' +
                    '    <TD class=house-type> ' + houseList[i]['houseType']['type'] + '</TD>\n' +
                    '    <TD class=house-price><SPAN>' + houseList[i]['price'] + '</SPAN>元/月</TD>\n' +
                    '  </TR> ');

                $tbody.append($node);

            }
        }
    }
}

