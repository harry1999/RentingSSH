/* 
用户管理 
*/


$(function () {

    checkLogin();

    init();

    //  页面加载后，先关闭创建用户信息的弹窗、对话框的组件等，在需要使用的时候再启用

    closeUserInfoWindow();   //  页面加载后，关闭创建用户信息的弹窗

    loadRoles();  // 页面加载后，加载该用户创建的角色到创建用户的window

    loadDepts();  //  页面加载后，加载该用户所属部门及其子部门到创建用户的window

    addUserInfoTipsHideListener();    //  指定事件触发后，隐藏用户名或密码不合法的提示组件

    closeDialogTips();   //   页面加载后，关闭通用提示窗

    closeDialogComfirm();     //  页面加载后，关闭确认删除用户的提示窗

    addUsernameInputListener();   //   //  添加创建用户时，对用户名进行监听的函数

    bindAuCannelListener();    // 确认弹窗的取消按钮的点击事件是确定的，而确认按钮的点击事件需要在不同场景分别指定


});


function checkLogin() {

    var params = {

        url: "/RentingSSH/user/isLogin",

        success: function (responseData) {

            if (responseData ['msg'] == 'LOGINED' && responseData ['status'] == 1) {

                sessionStorage.setItem("currentUser", JSON.stringify(responseData ['data']['e']));

                var $node = $(
                    '<span>欢迎您，' + responseData ['data']['e']['username'] + '     </span>' +

                    '<a href="" onclick="logout()">退出</a>'
                );

                $("#divAccount").html($node);

            } else {

                window.location.href = "/RentingSSH/pages/login.htm";

            }

        },

        error: function () {

        }

    };

    $.ajax(params);

}



// 初始化函数，加载菜单到页面左边区域

function init() {

    $.ajax({

        type: "get",

        url: "/RentingSSH/user/getUserPrivileges",

        async: false,

        dataType: "json",

        success: function (responseData) {

            if (responseData ['status'] == 1 && responseData['msg'] == 'OK') {

                var menuInfoList = responseData['data']['list'];

                $("#divLayoutWest").append(createMenu(menuInfoList));

                $("li").css({"text-decoration": "underline"});

            }

        },

        error: function (data) {

        }

    });

    // 菜单加载完后，打开其中一个菜单的窗口到主界面

    createMenuTag(JSON.parse(sessionStorage.getItem("menusId"))[0]);

}


function bindAuCannelListener() {

    $("#auCannel").click(function () {

        closeDialogComfirm();

    });

}


// 打开创建用户或修改用户信息的窗口的函数

function openUserInfoWindow(type) {

    $("#selectRoles").removeAttr("disabled");

    $("#auUserCannel").click(function () {

        closeUserInfoWindow();

    });

    switch (type) {

        case 1:

            $("input[name=username]").val("");
            $("input[name=password]").val("");
            $("input[name=realname]").val("");
            $("input[name=tel]").val("");

            $("#auUserSubmit").click(function () {

                createUser();

            });

            $("#windowUserInfo").window({title: "新建用户"});
            $("#windowUserInfo").window('open');

            break;

        case 2:

            var rows = $('#userList').datagrid('getSelections');

            if (rows.length == 0) {

                openDialogTips("请选择您要修改的用户信息");

                return;

            }

            if (rows.length > 1) {

                openDialogTips("一次只能修改一个用户的信息，请重新选择");
                return;

            }

            $("input[name=userId]").val(rows[0].id);
            $("input[name=username]").val(rows[0].username);
            $("input[name=password]").val(rows[0].password);
            $("input[name=realname]").val(rows[0].realname);
            $("input[name=tel]").val(rows[0].telephone);
            $("#selectRoles").val(rows[0].roleName);
            $("#selectDepts").val(rows[0].departmentName);

            if (rows[0].id == (JSON.parse(sessionStorage.getItem("currentUser")).id)) {

                $("#selectRoles").attr("disabled", "disabled");

                $("#selectDepts").attr("disabled", "disabled");

            }

            $("#windowUserInfo").window({title: "修改用户信息"});

            $("#windowUserInfo").window('open');

            $("#auUserSubmit").click(function () {

                if ($("#selectRoles").val() == "出租人" || $("#selectRoles").val() == "普通用户") {

                    $("#selectDepts").val("非内部员工");

                }

                updateUser();

            });

            break;

        default:

            break;

    }

}


// 关闭创建用户或修改用户信息的窗口的函数

function closeUserInfoWindow() {

    $("#windowUserInfo").window('close');

}


// 根据用户点击的菜单创建 tag 窗口的函数

function createMenuTag(menuId) {

    var menuName = sessionStorage.getItem(menuId);

    if ($('#tt').tabs('exists', menuName)) {

        $('#tt').tabs('select', menuName);

    } else {

        var $tag = $('<div></div>');

        switch (menuName) {

            case "用户管理" :

                $tag.append(createUserDatagrid());

                $('#tt').tabs('add', {

                    title: menuName,
                    content: $tag,
                    closable: true

                });

                loadUsersInfo();

                break;


            case "房屋信息管理" :

                $tag.append(createHouseDatagrid());

                $('#tt').tabs('add', {

                    title: menuName,
                    content: $tag,
                    closable: true

                });

                loadHousesInfo();

                break;


            case "权限设置":

                javascript:void(0);

                break;


            case "角色管理":

                $tag.append(createRoleDatagrid());

                $('#tt').tabs('add', {

                    title: menuName,
                    content: $tag,
                    closable: true

                });

                loadRolesInfo();

                break;


            case "数据库备份":
            case "权限管理":
            case "订单管理":

                openDialogTips("该功能待完善");

                break;

            default:

                $('#tt').tabs('add', {

                    title: menuName,
                    content: $tag,
                    closable: true

                });

                break;

        }

    }

}


//  创建用户列表表格的函数

function createUserDatagrid() {

    var $datagrid = $('  <div class="easyui-panel">\n' +
        '\n' +
        '      <div id="toolBarUser" style="padding: 2px 5px;">\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-add" plain = "true"\n' +
        '             onclick="openUserInfoWindow( 1 )">新建用户</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-edit" plain = "true"\n' +
        '             onclick="openUserInfoWindow( 2 )">修改用户信息</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-remove" plain = "true"\n' +
        '             onclick="comfirmDelUser()">删除用户</a>\n' +
        '\n' +

        '      </div>\n' +
        '\n' +
        '   <table id="userList" class="easyui-datagrid" style="height:250px"\n' +
        '\n' +
        '          pagination="true">\n' +
        '\n' +
        '   </table>\n' +
        '\n' +
        '  </div>')

    return $datagrid;
}


//  加载用户信息到用户列表的函数

function loadUsersInfo() {

    $("#userList").datagrid({

        singleSelect: false,
        url: "/RentingSSH/user/loadUsers",
        queryParams: {},
        pageSize: 5,
        pageList: [1, 5, 10],
        rownumbers: true,
        fit: true,
        fitColumns: true,
        toolbar: "#toolBarUser",
        striped: false,
        header: "",
        columns: [[

            {

                title: "序号",
                field: "id",
                align: "center",
                checkbox: true

            },


            {

                title: "用户名",
                field: "username",
                width: 100


            },


            {

                title: "手机号码",
                field: "telephone",
                width: 100


            },

            {

                title: "真实姓名",
                field: "realname",
                width: 100


            },


            {

                title: "注册时间",
                field: "createDate",
                width: 100


            },


            {

                title: "所属角色",
                field: "roleName",
                width: 100


            },

            {

                title: "所属部门",
                field: "departmentName",
                width: 100

            }

        ]]

    });

}


// 将当前用户所属角色及其创建的所有角色，加载到创建用户的窗口中的函数

function loadRoles() {

    var params = {

        url: "/RentingSSH/user/getRoles",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $select = $("#selectRoles");

                var roles = responseData ['data']['list'];

                for (var i = 0; i < roles.length; i++) {

                    var $option = $('<option value=' + roles[i]["roleName"] + '>' + roles[i]["roleName"] + ' </option>');

                    $select.append($option);

                }

            }

        }

    };

    $.ajax(params);

}


// 将当前用户所属部门及其子部门加载到创建用户的窗口

function loadDepts() {

    var params = {

        url: "/RentingSSH/user/getDepts",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $select = $("#selectDepts");

                var depts = responseData ['data']['list'];

                for (var i = 0; i < depts.length; i++) {

                    var $option = $('<option value=' + depts[i]["name"] + '>' + depts[i]["name"] + ' </option>');

                    $select.append($option);

                }

            }

        }

    };

    $.ajax(params);

}


// 创建用户时，检查用户名是否已经存在的函数

var isUsernameExist = false;

function addUsernameInputListener() {

    $("input[name=username]").blur(function () {

        var username = $("input[name=username]").val();

        if (username != null && username.replace(/(^\s*)|(\s*$)/g, "") != '') {

            var obj = new Object();
            obj['user.username'] = username;

            $.ajax({

                type: "POST",
                data: obj,
                async: true,
                url: "/RentingSSH/user/checkUsername",

                success: function (responseData) {

                    if (responseData['status'] == 1 && responseData['msg'] == 'USER_EXITS') {

                        showUserInfoTips("用户名已存在，请使用其他用户名");

                        isUsernameExist = true;

                    } else {

                        isUsernameExist = false;

                    }

                },
                error: function (data) {

                }

            });

        }

    });

}


// 更新用户信息的函数

function updateUser() {

    var obj = new Object();
    var username = $("input[name=username]").val();
    var password = $("input[name=password]").val();

    if (username == null || username.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showUserInfoTips("用户名不能为空");
        return;

    }

    if (password == null || password.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showUserInfoTips("密码不能为空");
        return;

    }

    if (isUsernameExist == true) {

        showUserInfoTips("用户名已存在，请使用其他用户名");
        return;
    }

    obj['user.username'] = username;
    obj['user.password'] = password;
    obj['user.id'] = $("input[name=userId]").val();
    obj['user.realname'] = $("input[name=realname]").val();
    obj['user.telephone'] = $("input[name=tel]").val();
    obj['user.roleName'] = $("#selectRoles").val();
    obj['user.departmentName'] = $("#selectDepts").val();

    var params = {

        url: "/RentingSSH/user/update",
        type: "post",
        data: obj,
        dataType: 'json',

        success: function (responseData) {

            if (responseData ['status'] == 1 && responseData['msg'] == "OK") {

                closeUserInfoWindow();

                openDialogTips("用户信息修改成功");

                refreshUserDatagrid();

            } else {

                showUserInfoTips("网络异常，请重试");

            }

        }

    };

    $.ajax(params);

}


//  创建新用户的函数

function createUser() {

    var obj = new Object();
    var username = $("input[name=username]").val();
    var password = $("input[name=password]").val();

    if (username == null || username == '') {

        showUserInfoTips("用户名不能为空");
        return;

    }

    if (password == null || password == '') {

        showUserInfoTips("密码不能为空");
        return;

    }

    if (isUsernameExist == true) {

        showUserInfoTips("用户名已存在，请使用其他用户名");
        return;
    }

    obj['user.username'] = username;
    obj['user.password'] = password;
    obj['user.realname'] = $("input[name=realname]").val();
    obj['user.telephone'] = $("input[name=tel]").val();
    obj['user.roleName'] = $("#selectRoles").val();
    obj['user.departmentName'] = $("#selectDepts").val();

    var params = {

        url: "/RentingSSH/user/add",
        type: "post",
        data: obj,
        dataType: 'json',

        success: function (responseData) {

            if (responseData ['status'] == 1 && responseData['msg'] == "OK") {

                closeUserInfoWindow();

                openDialogTips("用户创建成功");

                refreshUserDatagrid();

            } else {

                showUserInfoTips("网络异常，请重试");

            }

        }

    };

    $.ajax(params);

}


//  确认是否删除指定用户的函数

function comfirmDelUser() {

    var rows = $('#userList').datagrid('getSelections');

    if (rows.length == 0) {

        openDialogTips("您没有选择任何用户");

        return;

    }

    var innerHtml = "您确认删除以下用户吗？<br/>";
    var usersId = new Array();

    for (var i = 0; i < rows.length; i++) {

        if (rows[0].id == (JSON.parse(sessionStorage.getItem("currentUser")).id)) {

            openDialogTips("用户不允许删除自己，请重新选择");

            return;

        }

        usersId.push(rows[i].id);
        innerHtml += rows[i].username + "<br/>";

    }

    sessionStorage.setItem("usersId", JSON.stringify(usersId));

    openDialogComfirm(innerHtml);

    $("#auComfirm").click(function () {

        deleteUsers();

    });

}


// 删除用户的函数

function deleteUsers() {

    var usersId = JSON.parse(sessionStorage.getItem("usersId"));

    var obj = $.param({'usersId': usersId}, true);

    var params = {

        type: "post",

        url: "/RentingSSH/user/delete",

        cache: false,

        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                openDialogTips("成功删除用户");
                closeDialogComfirm();

            } else {

                openDialogTips("网络异常，请重试");
                closeDialogComfirm();

            }

        },
        error: function (data) {

            openDialogTips("网络异常，请重试");
            closeDialogComfirm();

        }

    };

    $.ajax(params);

}


//  创建菜单的函数

function createMenu(menuInfoList) {

    var $ul = $('<ul></ul>');

    var menusId = new Array();

    for (var i = 0; i < menuInfoList.length; i++) {

        if (menuInfoList[i]['isMenu'] == 1) {

            var menuId = menuInfoList[i]["id"];
            var menuName = menuInfoList[i]["text"];

            menusId.push(menuId);
            sessionStorage.setItem(menuId, menuName);

            var $li = $('<li><a id="' + menuId + '"  href="javascript:void(0)" onclick="createMenuTag(' + menuId + ')" >' + menuName + '</a></li>');

            if (menuInfoList[i]['children'] != null && menuInfoList[i]['children'].length != 0) {

                $li.append(createMenu(menuInfoList[i]['children']));

            }

            $ul.append($li);

        }

    }

    menusId.sort();
    sessionStorage.setItem("menusId", JSON.stringify(menusId));

    return $ul;

}


//  刷新用户列表的函数

function refreshUserDatagrid() {

    $('#userList').datagrid('reload');

}


// 打开提示框的函数

function openDialogTips(msg) {

    $("#dialogTips").html(msg);

    $("#dialogTips").dialog('open');

}


//  关闭提示框的函数

function closeDialogTips() {

    $("#dialogTips").dialog('close');

}


// 打开确认框的函数

function openDialogComfirm(msg) {

    $("#dialogComfirm").html(msg);

    $("#dialogComfirm").dialog('open');

}

//  关闭确认框的函数

function closeDialogComfirm() {

    $("#dialogComfirm").dialog('close');

    refreshUserDatagrid();

}


function showUserInfoTips(msg) {

    $("#tipsUserInfo").text(msg);
    $("#tipsUserInfo").css("display", "block");

}


//  监听用户输入事件以及窗口关闭等事件的函数，事件发生后关闭“用户名或密码不合法”的提示组件

function addUserInfoTipsHideListener() {

    // 必须在关闭窗口时隐藏提示信息，否则在下一次打开窗口时会显示上一次操作的提示信息

    $("#windowUserInfo").window({

        onClose: function () {

            $("#tipsUserInfo").css("display", "none");

        }

    });


    $("input[name=username]").focus(function () {

        $("#tipsUserInfo").css("display", "none");

    });

    $("input[name=password]").focus(function () {

        $("#tipsUserInfo").css("display", "none");

    });


    $("input[name=realname]").focus(function () {

        $("#tipsUserInfo").css("display", "none");

    });

    $("input[name=tel]").focus(function () {

        $("#tipsUserInfo").css("display", "none");

    });

    $("input[name=password]").focus(function () {

        $("#tipsUserInfo").css("display", "none");

    });


    $("#selectRoles").click(function () {

        $("#tipsUserInfo").css("display", "none");

    });


    $("#selectDepts").click(function () {

        $("#tipsUserInfo").css("display", "none");

    });

}


/*
房屋信息管理
*/


$(function () {

    addHouseInfoTipsHideListener();

    closeHouseInfoWindow();

    onDistrictChanged();

    getHouseTypes();

    getDistricts();

});


/*
 *  在新增租房信息页面，初始化房屋类型信息
 *
 * */

function getHouseTypes() {

    var params = {

        url: "/RentingSSH/common/getHouseTypes",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $select = $('select[name="house.houseType.id"]');

                var houseTypes = responseData['data']['list'];

                for (var i = 0; i < houseTypes.length; i++) {

                    var $node = $('<OPTION value=' + houseTypes[i]['id'] + '>' + houseTypes[i]['type'] + '</OPTION>');

                    /*
                     *
                     *  将第一条创建的选项节点设置为选中
                     *
                     * */

                    if (i == 0) {

                        $node.attr("selected", "selected");
                    }

                    $select.append($node);

                }
            }
        },

        error: function (data) {

        }

    };

    $.ajax(params);

}


/*
*  初始化区域和街道信息的函数
* */

function getDistricts() {

    var params = {

        url: "/RentingSSH/common/getDistricts?tag=1",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                var $selectDistrict = $('select[name="district_id"]');
                var $selectStreet = $('select[name="house.subDistrict.id"]');
                var districtsInfosObject = responseData['data']['list'];

                sessionStorage.setItem("districtInfos", JSON.stringify(districtsInfosObject));
                var districtOptionArray = createDistrictNodes(districtsInfosObject);

                for (var i = 0; i < districtOptionArray.length; i++) {

                    $selectDistrict.append(districtOptionArray[i]);

                }

                var subDistrictOptionArray = createDistrictNodes(districtsInfosObject[0]['subDistricts']);

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


function createDistrictNodes(districtInfoObject) {

    var optionArray = new Array();

    for (var i = 0; i < districtInfoObject.length; i++) {

        var $node = $('<OPTION  value=' + districtInfoObject[i]['id'] + '>' + districtInfoObject[i]['name'] + '</OPTION>');

        /*
        *
        *  将第一条创建的选项节点设置为选中
        *
        * */

        if (i == 0) {

            $node.attr("selected", "selected");

        }

        optionArray.push($node);

    }

    return optionArray;
}


// 地区信息变动的监听函数，地区信息变动时更改街道信息

function onDistrictChanged() {

    var $selectDistrict = $('select[name="district_id"]');
    var $selectStreet = $('select[name="house.subDistrict.id"]');

    $selectDistrict.change(function () {

        var value = this.value;
        var districtsInfosObject = JSON.parse(sessionStorage.getItem("districtInfos"));
        var subDistrictOptionArray = createDistrictNodes(districtsInfosObject[parseInt(value) - 1]['subDistricts']);

        $selectStreet.empty();

        for (var i = 0; i < subDistrictOptionArray.length; i++) {

            $selectStreet.append(subDistrictOptionArray[i]);

        }
    });
}


//  创建房屋信息列表表格的函数

function createHouseDatagrid() {

    var $datagrid = $('  <div class="easyui-panel">\n' +
        '\n' +
        '      <div id="toolBarHouse" style="padding: 2px 5px;">\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-add" plain = "true"\n' +
        '             onclick="openHouseInfoWindow( 1 )">新建房屋信息</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-edit" plain = "true"\n' +
        '             onclick="openHouseInfoWindow( 2 )">修改房屋信息</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-ok" plain = "true"\n' +
        '             onclick="beforeIssueHouses()">发布房屋信息</a>\n' +
        '\n' +

        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-no" plain = "true"\n' +
        '             onclick="beforeUnShelveHouses()">下架房屋信息</a>\n' +
        '\n' +

        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-remove" plain = "true"\n' +
        '             onclick="beforeDelHouses()">删除房屋信息</a>\n' +
        '\n' +

        '      </div>\n' +
        '\n' +
        '   <table id="houseList" class="easyui-datagrid" style="height:250px"\n' +
        '\n' +
        '          pagination="true">\n' +
        '\n' +
        '   </table>\n' +
        '\n' +
        '  </div>')

    return $datagrid;

}


function showHouseInfoTips($field, msg) {

    $field.text(msg);

    $field.css("display", "block");

}


function hideAllHouseInfoTips() {

    $(".spanInfoTips").css("display", "none");

}


function addHouseInfoTipsHideListener() {

    $("#windowHouseInfo").window({

        onClose: function () {

            hideAllHouseInfoTips();

        }

    });


    $("input[name='house.title']").focus(function () {

        $("#tipsTitle").css("display", "none");

    });


    $("input[name='house.area']").focus(function () {

        $("#tipsArea").css("display", "none");

    });


    $("input[name='house.price']").focus(function () {

        $("#tipsPrice").css("display", "none");

    });


    $("input[name='house.deposit']").focus(function () {

        $("#tipsDeposit").css("display", "none");

    });


    $("input[name='house.telephone']").focus(function () {

        $("#tipsTel").css("display", "none");

    });


}


//  加载房屋信息到房屋列表的函数

function loadHousesInfo() {

    $("#houseList").datagrid({

        singleSelect: false,
        url: "/RentingSSH/common/loadHouses",
        queryParams: {},
        pageSize: 5,
        pageList: [1, 5, 10],
        rownumbers: true,
        fit: true,
        fitColumns: true,
        toolbar: "#toolBarHouse",
        striped: false,
        header: "",
        columns: [[

            {

                title: "序号",
                field: "id",
                align: "center",
                checkbox: true

            },


            {

                title: "标题",
                field: "title",
                width: 100
            },


            {

                title: "房屋面积（平方米）",
                field: "area",
                width: 100


            },


            {

                title: "租金（元/月）",
                field: "price",
                width: 100


            },

            {

                title: "发布时间",
                field: "issueDate",
                width: 100


            },


            {

                title: "出租人电话",
                field: "telephone",
                width: 100


            },

            {

                title: "出租人",
                field: "userName",
                width: 100


            },

            {

                title: "租房人",
                field: "bookingUserName",
                width: 100


            },


            {

                title: "预约金（元）",
                field: "deposit",
                width: 100


            },

            {

                title: "已发布（1是 / 0否）",
                field: "issued",
                width: 100

            }

        ]]

    });

}


//  新建房屋信息时，检查房屋信息的标题的函数

function checkHouseTitle() {

    var houseTitle = $("input[name='house.title']").val();

    if (houseTitle == null || houseTitle.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showHouseInfoTips($("#tipsTitle"), "房屋信息标题不能为空");

        return false;

    }

    if (houseTitle.length > 100) {

        showHouseInfoTips($("#tipsTitle"), "房屋信息标题长度不能大于100个字符");

        return false;

    }

    return true;

}


function checkHouseArea() {

    var houseArea = $("input[name='house.area']").val();

    if (houseArea == null || houseArea.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showHouseInfoTips($("#tipsArea"), "房屋的面积信息不能为空");

        return false;

    }

    if (isNaN(houseArea)) {

        showHouseInfoTips($("#tipsArea"), "房屋的面积必须为数字，请重新输入");

        return false;

    }

    if (houseArea < 0) {

        showHouseInfoTips($("#tipsArea"), "房屋的面积不能是负数，请重新输入");

        return false;

    }

    return true;

}


function checkHousePrice() {

    var housePrice = $("input[name='house.price']").val();

    if (housePrice == null || housePrice.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showHouseInfoTips($("#tipsPrice"), "房屋的租金信息不能为空");

        return false;

    }

    if (isNaN(housePrice)) {

        showHouseInfoTips($("#tipsPrice"), "房屋的租金必须为数字，请重新输入");

        return false;

    }

    if (housePrice < 0) {

        showHouseInfoTips($("#tipsPrice"), "房屋的租金不能是负数，请重新输入");

        return false;

    }

    return true;

}


function checkHouseDeposit() {

    var houseDeposit = $("input[name='house.deposit']").val();

    if (houseDeposit == null || houseDeposit.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showHouseInfoTips($("#tipsDeposit"), "房屋的预约金不能为空");

        return false;

    }


    if (isNaN(houseDeposit)) {

        showHouseInfoTips($("#tipsDeposit"), "房屋的预约金必须为数字，请重新输入");

        return false;

    }


    if (houseDeposit < 0) {

        showHouseInfoTips($("#tipsDeposit"), "房屋的预约金不能是负数，请重新输入");

        return false;

    }

    return true;

}


function checkHouseTel() {

    var houseTel = $("input[name='house.telephone']").val();

    if (houseTel == null || houseTel.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showHouseInfoTips($("#tipsTel"), "房屋出租人的联系电话不能为空");

        return false;

    }

    if (!(/^1(3|4|5|7|8)\d{9}$/.test(houseTel)) || !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(houseTel)) {

        showHouseInfoTips($("#tipsTel"), "您输入的不是固话也不是移动电话，请重新输入");

        return false;

    }

    return true;

}


function createHouse() {

    var params = {

        url: "/RentingSSH/common/createHouse",

        type: "post",
        async: false,
        data: new FormData($("#addHouseAction")[0]),
        processData: false,
        contentType: false,
        cache: false,
        success: function (responseData) {

            closeHouseInfoWindow();

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                openDialogTips("房屋信息创建成功");

                refreshHouseDatagrid();

            } else {

                openDialogTips("网络异常，请重试");

            }


        },

        error: function (responseData) {

            closeHouseInfoWindow();

            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


function updateHouse() {

    var params = {

        url: "/RentingSSH/common/updateHouse",

        type: "post",
        async: false,
        data: new FormData($("#addHouseAction")[0]),
        processData: false,
        contentType: false,
        cache: false,
        success: function (responseData) {


            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                closeHouseInfoWindow();

                openDialogTips("房屋信息修改成功");

                refreshHouseDatagrid();


            } else {

                closeHouseInfoWindow();

                openDialogTips("网络异常，请重试");

                refreshHouseDatagrid();

            }


        },

        error: function (responseData) {

            closeHouseInfoWindow();

            openDialogTips("网络异常，请重试");

            refreshHouseDatagrid();

        }

    };

    $.ajax(params);

}


function beforeIssueHouses() {

    var rows = $('#houseList').datagrid('getSelections');

    if (rows.length < 1) {

        openDialogTips("请选择您要发布的房屋信息");

        return;

    }

    var housesId = new Array();

    for (var i = 0; i < rows.length; i++) {

        if (rows[i].issued == 0) {

            housesId.push(rows[i].id);

        }

    }


    if (housesId.length < 1) {

        openDialogTips("您选择的房屋信息已全部发布，不需要再次发布");

        return;

    }

    openDialogComfirm("确认发布选中的房屋信息吗?");

    $("#auComfirm").click(function () {

        sessionStorage.setItem("housesId", JSON.stringify(housesId));

        issueHouses();

    });

}


function beforeUnShelveHouses() {

    var rows = $('#houseList').datagrid('getSelections');

    if (rows.length < 1) {

        openDialogTips("请选择您要下架的房屋信息");

        return;

    }

    var housesId = new Array();

    for (var i = 0; i < rows.length; i++) {

        if (rows[i].issued == 1) {

            housesId.push(rows[i].id);

        }
    }

    if (housesId.length < 1) {

        openDialogTips("您选择的房屋信息已全部下架，不需要再次执行下架操作");

        return;

    }

    openDialogComfirm("确认下架选中的房屋信息吗?");

    $("#auComfirm").click(function () {

        sessionStorage.setItem("housesId", JSON.stringify(housesId));

        unShelveHouses();

    });
}


function beforeDelHouses() {

    var rows = $('#houseList').datagrid('getSelections');

    if (rows.length < 1) {

        openDialogTips("请选择您要删除的房屋信息");

        return;

    }

    var housesId = new Array();

    for (var i = 0; i < rows.length; i++) {

        housesId.push(rows[i].id);

    }

    openDialogComfirm("确认删除选中的房屋信息吗?");

    $("#auComfirm").click(function () {

        sessionStorage.setItem("housesId", JSON.stringify(housesId));

        delHouses();

    });
}


function delHouses() {

    var housesId = JSON.parse(sessionStorage.getItem("housesId"));

    var obj = $.param({'housesId': housesId}, true);

    var params = {

        type: "post",

        url: "/RentingSSH/common/delHouses",

        cache: false,

        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                closeDialogComfirm();
                openDialogTips("所选房屋信息已删除");
                refreshHouseDatagrid();


            } else {

                closeDialogComfirm();
                openDialogTips("网络异常，请重试");

            }

        },

        error: function (responseData) {

            closeDialogComfirm();
            openDialogTips("网络异常，请重试");

        }
    };

    $.ajax(params);

}


function unShelveHouses() {

    var housesId = JSON.parse(sessionStorage.getItem("housesId"));

    var obj = $.param({'housesId': housesId}, true);

    var params = {

        type: "post",

        url: "/RentingSSH/common/unShelveHouses",

        cache: false,

        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                closeDialogComfirm();
                openDialogTips("房屋信息已下架");
                refreshHouseDatagrid();


            } else {

                closeDialogComfirm();
                openDialogTips("网络异常，请重试");

            }

        },

        error: function (responseData) {

            closeDialogComfirm();
            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


function issueHouses() {

    var housesId = JSON.parse(sessionStorage.getItem("housesId"));

    var obj = $.param({'housesId': housesId}, true);

    var params = {

        type: "post",

        url: "/RentingSSH/common/issueHouses",

        cache: false,

        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                closeDialogComfirm();
                openDialogTips("房屋信息发布成功");
                refreshHouseDatagrid();


            } else {

                closeDialogComfirm();
                openDialogTips("网络异常，请重试");

            }

        },

        error: function (responseData) {

            closeDialogComfirm();
            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


//  刷新房屋列表的函数

function refreshHouseDatagrid() {

    $('#houseList').datagrid('reload');

}


function closeHouseInfoWindow() {

    $("#windowHouseInfo").window('close');

}


function openHouseInfoWindow(type) {

    $("#auHouseCannel").click(function () {

        closeHouseInfoWindow();

    });

    switch (type) {

        case 1:

            $("input[name='house.title']").val("");
            $("input[name='house.area']").val("");
            $("input[name='house.price']").val("");
            $("input[name='house.deposit']").val("");
            $("input[name='house.telephone']").val("");
            $("input[name='house.description']").val("");

            $("#auHouseSubmit").click(function () {

                if (checkHouseTitle() & checkHouseArea() & checkHousePrice() & checkHouseTel() & checkHouseDeposit()) {

                    createHouse();

                }

            });


            $("#windowHouseInfo").window({title: "新建房屋信息"});

            $("#windowHouseInfo").window('open');


            break;

        case 2:

            var rows = $('#houseList').datagrid('getSelections');

            if (rows.length == 0) {

                openDialogTips("请选择您要修改的房屋信息");

                return;

            }

            if (rows.length > 1) {

                openDialogTips("一次只能修改一个房屋信息，请重新选择");

                return;

            }

            $("input[name='house.id']").val(rows[0].id);
            $("input[name='house.title']").val(rows[0].title);
            $("input[name='house.area']").val(rows[0].area);
            $("input[name='house.price']").val(rows[0].price);
            $("input[name='house.deposit']").val(rows[0].deposit);
            $("input[name='house.telephone']").val(rows[0].telephone);
            $("input[name='house.description']").val(rows[0].description);

            $("input[name='house.houseType.id']").val(rows[0].houseType.type);
            $("input[name='house.subDistrict.id']").val(rows[0].subDistrict.name);

            $("#windowHouseInfo").window({title: "修改房屋信息"});

            $("#windowHouseInfo").window('open');

            $("#auHouseSubmit").click(function () {

                if (checkHouseTitle() & checkHouseArea() & checkHousePrice() & checkHouseTel() & checkHouseDeposit()) {

                    updateHouse();

                }

            });

            break;

        default:

            break;

    }

}


/*
角色管理
* */


$(function () {


    addRoleInfoTipsHideListener();


});


//  页面关闭后清空localStorage

window.onbeforeunload = function () {

    localStorage.clear();

}


//  创建角色列表表格的函数

function createRoleDatagrid() {

    var $datagrid = $('  <div class="easyui-panel">\n' +
        '\n' +
        '      <div id="toolBarRole" style="padding: 2px 5px;">\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-add" plain = "true"\n' +
        '             onclick="openRoleInfoWindow( 1 )">新建角色</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-edit" plain = "true"\n' +
        '             onclick="openRoleInfoWindow( 2 )">修改角色信息</a>\n' +
        '\n' +
        '          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls = "icon-remove" plain = "true"\n' +
        '             onclick="beforeDelRoles()">删除角色</a>\n' +
        '\n' +

        '      </div>\n' +
        '\n' +
        '   <table id="roleList" class="easyui-datagrid" style="height:250px"\n' +
        '\n' +
        '          pagination="true">\n' +
        '\n' +
        '   </table>\n' +
        '\n' +
        '  </div>')

    return $datagrid;

}


//  加载角色信息到角色列表的函数

function loadRolesInfo() {

    $("#roleList").datagrid({

        singleSelect: false,
        url: "/RentingSSH/common/loadRoles",
        queryParams: {},
        pageSize: 5,
        pageList: [1, 5, 10],
        rownumbers: true,
        fit: true,
        fitColumns: true,
        toolbar: "#toolBarRole",
        striped: false,
        header: "",
        columns: [[

            {

                title: "序号",
                field: "id",
                align: "center",
                checkbox: true

            },


            {

                title: "角色名称",
                field: "roleName",
                width: 100
            },


            {

                title: "角色描述",
                field: "roleDesc",
                width: 100

            },

            {

                title: "父级角色",
                field: "parentRoleName",
                width: 100

            }

        ]]

    });

}


function openRoleInfoWindow(type) {

    createPrivilegesTree();

    $("#auRoleCannel").click(function () {

        closeRoleInfoWindow();

    });


    switch (type) {

        // 新增角色信息

        case 1:

            $("input[name=roleName]").val("");
            $("input[name=roleDesc]").val("");

            $("#auRoleSubmit").click(function () {

                if (checkRoleName()) {

                    createRole();

                }

            });


            $("#windowRoleInfo").window({title: "新建角色"});

            $("#windowRoleInfo").window('open');

            break;

        case 2:

            // 修改角色信息

            var rows = $('#roleList').datagrid('getSelections');

            if (rows.length == 0) {

                openDialogTips("请选择您要修改的角色信息");

                return;

            }

            if (rows.length > 1) {

                openDialogTips("一次只能修改一个角色信息，请重新选择");

                return;

            }

            $("input[name=roleId]").val(rows[0].id);
            $("input[name=roleName]").val(rows[0].roleName);
            $("input[name=roleDesc]").val(rows[0].roleDesc);


            var rolePrivileges = rows[0].rolePrivileges;
            var privilegesId = new Array();


            for (var i = 0; i < rolePrivileges.length; i++) {

                privilegesId.push(rolePrivileges[i].privilege.privilegeId);

            }

            $("#windowRoleInfo").window({title: "修改角色信息"});
            $("#windowRoleInfo").window('open');

            //  在权限树加载完之后，勾选当前角色所拥有的权限

            $('#ulPrivilegeTree').tree({

                onLoadSuccess: function (data, node) {

                    for (var i = 0; i < privilegesId.length; i++) {

                        $("#ulPrivilegeTree").tree('check', $("#ulPrivilegeTree").tree('find', privilegesId[i]).target);

                    }

                }

            });


            $("#auRoleSubmit").click(function () {

                if (checkRoleName()) {

                    updateRole();

                }

            });


            break;


        default:

            break;

    }

}


// 关闭创建角色或修改角色信息的窗口的函数

function closeRoleInfoWindow() {

    $("#windowRoleInfo").window('close');

}


//  刷新角色列表的函数

function refreshRoleDatagrid() {

    $('#roleList').datagrid('reload');

}


function createRole() {

    var roleName = $("input[name=roleName]").val();
    var roleDesc = $("input[name=roleDesc]").val();
    var privileges = $("#ulPrivilegeTree").tree("getChecked");

    var privilegesId = new Array();

    if (privileges.length != 0) {

        for (var i = 0; i < privileges.length; i++) {

            privilegesId.push(privileges[i].id);

        }

    }

    var obj = $.param({
        'role.roleName': roleName,
        'role.roleDesc': roleDesc,
        'privilegesId': privilegesId
    }, true);

    var params = {

        type: "post",

        url: "/RentingSSH/common/createRole",

        cache: false,

        data: obj,

        success: function (responseData) {

            closeRoleInfoWindow();

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {


                refreshRoleDatagrid();
                openDialogTips("成功创建角色");

            } else {

                openDialogTips("网络异常，请重试");

            }

        },

        error: function (responseData) {

            closeRoleInfoWindow();
            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


function updateRole() {

    var roleId = $("input[name=roleId]").val();
    var roleName = $("input[name=roleName]").val();
    var roleDesc = $("input[name=roleDesc]").val();

    var privileges = $("#ulPrivilegeTree").tree("getChecked");

    var privilegesId = new Array();

    if (privileges.length != 0) {

        for (var i = 0; i < privileges.length; i++) {

            privilegesId.push(privileges[i].id);

        }

    }

    var obj = $.param({
        'role.id': roleId,
        'role.roleName': roleName,
        'role.roleDesc': roleDesc,
        'privilegesId': privilegesId
    }, true);

    var params = {

        type: "post",
        url: "/RentingSSH/common/updateRole",
        cache: false,
        data: obj,

        success: function (responseData) {

            closeRoleInfoWindow();

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                refreshRoleDatagrid();
                openDialogTips("成功修改角色信息");

            } else {

                openDialogTips("网络异常，请重试");

            }

        },

        error: function (responseData) {

            closeRoleInfoWindow();
            openDialogTips("网络异常，请重试");

        }

    };

    $.ajax(params);

}


function checkRoleName() {

    var roleName = $("input[name=roleName]").val();

    if (roleName == null || roleName.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showRoleInfoTips($("#tipsRoleName"), "角色名称不能为空");

        return false;

    }

    if (roleName.length > 30) {

        showRoleInfoTips($("#tipsRoleName"), "角色名称长度不能大于30个字符");

        showHouseInfoTips($("#tipsTitle"), "房屋信息标题");

        return false;

    }

    return true;

}


function showRoleInfoTips($field, msg) {

    $field.text(msg);

    $field.css("display", "block");

}


function addRoleInfoTipsHideListener() {

    $("#windowRoleInfo").window({

        onClose: function () {

            $("#tipsRoleName").css("display", "none");

        }

    });


    $("input[name='roleName']").focus(function () {

        $("#tipsRoleName").css("display", "none");

    });

    $("input[name='roleDesc']").focus(function () {

        $("#tipsRoleName").css("display", "none");

    });

}


function createPrivilegesTree() {

    $("#ulPrivilegeTree").tree({

        url: "/RentingSSH/user/getUserPrivileges",
        animate: true,
        checkbox: true,

        loadFilter: function (responseData) {

            return responseData['data']['list'];

        }

    });
}


function beforeDelRoles() {

    var rows = $('#roleList').datagrid('getSelections');

    if (rows.length < 1) {

        openDialogTips("请选择您要删除的角色信息");

        return;

    }

    var rolesId = new Array();

    for (var i = 0; i < rows.length; i++) {

        rolesId.push(rows[i].id);
        localStorage.setItem(rows[i].id, rows[i].roleName);

    }

    if (checkRolesCorrelation(rolesId)) {

        return;

    }

    openDialogComfirm("确认删除选中的角色信息吗?");

    $("#auComfirm").click(function () {

        sessionStorage.setItem("rolesId", JSON.stringify(rolesId));

        delRoles();

    });

}


function checkRolesCorrelation(rolesId) {

    var result = false;

    var obj = $.param({'rolesId': rolesId}, true);

    var params = {

        type: "post",
        data: obj,
        async: false,
        url: "/RentingSSH/common/checkRolesCorrelation",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                result = true;

                var relativedRoles = responseData['data']['list'];
                var innerHtml = "以下角色已经关联用户，不允许删除，请重新选择：" + "<br/>";

                for (var i = 0; i < relativedRoles.length; i++) {

                    innerHtml += localStorage.getItem(relativedRoles[i]) + "<br/>";

                }

                openDialogTips(innerHtml);

            } else {

            }
        },

        error: function (data) {

            result = true;

            openDialogTips("网络异常，请重试");

        }
    };

    $.ajax(params);

    return result;

}


function delRoles() {

    var rolesId = JSON.parse(sessionStorage.getItem("rolesId"));
    var obj = $.param({'rolesId': rolesId}, true);

    var params = {

        type: "post",
        url: "/RentingSSH/common/delRoles",
        cache: false,
        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                openDialogTips("成功删除角色");
                closeDialogComfirm();
                refreshRoleDatagrid();

            } else {

                openDialogTips("网络异常，请重试");
                closeDialogComfirm();

            }

        },
        error: function (data) {

        }

    };

    $.ajax(params);

}