$(function () {

    $("input[value=注册]").click(function () {

        window.location.href = "/RentingSSH/pages/register.htm";

    });

    $("input[value=登陆]").click(function () {

        beforeLogin();

    });

    checkLogin();

    checkRememberMe();

    clearSessionStorage();

    setSecurityCode();

    addTipsHideListener();

});


function checkLogin() {

    var params = {

        url: "/RentingSSH/user/isLogin",

        success: function (responseData) {

            if (responseData ['msg'] == 'LOGINED' && responseData ['status'] == 1) {

                window.location.href = "/RentingSSH/pages/index.htm";

            } else {

                // do nothing ...

            }

        },

        error: function () {

            // do nothing ...

        }

    };

    $.ajax(params);

}


/*
*  加载登录页面后，向服务器请求cookie中的用户信息
*  如果存在用户信息，则直接填充到登录信息框中
* */

function checkRememberMe() {

    var params = {

        url: "/RentingSSH/user/checkRememberMe",

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'OK') {

                $("#user_name").val(responseData['data']['e']['username']);
                $("#user_password").val(responseData['data']['e']['password']);

            }

        },

        error: function (data) {

        }

    };

    $.ajax(params);

}


// 清空sessionStorage的函数，用户在后台管理页面按登出按钮或返回键的时候触发

function clearSessionStorage() {

    sessionStorage.clear();

}


var securityCode = "";

//  生成随机验证码的函数

function generateSecurityCode() {

    var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    var num = -1;
    var securityCode = "";

    for (var i = 0; i < 4; i++) {

        num = Math.ceil(Math.random() * 35);

        securityCode += chars[num] + " ";

    }

    return securityCode;

}

//  将随机验证码设置到img节点的函数

function setSecurityCode() {

    securityCode = generateSecurityCode();

    $("#imgShowCode").attr("title", securityCode);

}


function beforeLogin() {

    if (isUsernameValid() & isPwdValid() & isCodeMatch()) {

        login();

    } else {

        setSecurityCode();

    }

}


function login() {

    var obj = new Object();

    obj['user.username'] = $("#user_name").val();
    obj['user.password'] = $("#user_password").val();

    if ($("input[name='checkbox']").is(':checked')) {

        obj['user.isChecked'] = "on";

    } else {

        obj['user.isChecked'] = "off";

    }

    var params = {

        url: "/RentingSSH/user/login",
        type: 'POST',
        data: obj,

        success: function (responseData) {

            if (responseData['status'] == 1 && responseData['msg'] == 'USER_EXITS') {

                if (localStorage.getItem("toPage") != null) {

                    var item = localStorage.getItem("toPage");

                    localStorage.removeItem("toPage");

                    window.location.href = item;

                }

                else if (responseData['data']['e'] == 3) {
                    window.location.href = "/RentingSSH/pages/index.htm";
                }
                else {
                    window.location.href = "/RentingSSH/pages/management/management.html";
                }

            } else {

                showTips($("#tipsFeedback"), "用户名或密码不正确，请重试");

            }

        },

        error: function (data) {

            showTips($("#tipsFeedback"), "网络异常，请重试");

        }

    };

    $.ajax(params);

}


// 检查用户输入的验证码与生成的验证码是否匹配，忽略大小写

function isCodeMatch() {

    if (securityCode.replace(/\s+/g, "").toLowerCase() ==
        $("#inputVerifyCode").val().replace(/\s+/g, "").toLowerCase()) {

        hideTips($("#tipsCode"));

        return true;

    }

    showTips($("#tipsCode"), "验证码不匹配，请重新输入");

    setSecurityCode();

    return false;

}


function isUsernameValid() {

    var username = $("#user_name").val();

    if (username == null || username.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showTips($("#tipsUsername"), "用户名不能为空");

        return false;


    } else if (username.length < 4) {

        showTips($("#tipsUsername"), "用户名长度不能小于4");

        return false;

    }

    else if (username.match("^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$") == null) {

        showTips($("#tipsUsername"), "用户名只能包含数字字母和下划线");

        return false;

    }

    else {

        return true;

    }

}


function isPwdValid() {

    var password = $("#user_password").val();

    if (password == null || password.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showTips($("#tipsPassword"), "密码不能为空");

        return false;


    } else if (password.length < 5) {

        showTips($("#tipsPassword"), "密码长度不能小于5");

        return false;

    } else if (password.match("^(?!_)(?!.*?_$)[a-zA-Z0-9=_\u4e00-\u9fa5]+$") == null) {

        showTips($("#tipsPassword"), "密码只能包含数字字母和等号下划线");

        return false;

    } else {

        return true;

    }

}


// 指定事件触发时，隐藏提示信息

function addTipsHideListener() {

    $("#user_name").focus(function () {

        hideTips($("#tipsUsername"));
        hideTips($("#tipsFeedback"));

    });


    $("#user_password").focus(function () {

        hideTips($("#tipsPassword"));
        hideTips($("#tipsFeedback"));

    });


    $("#inputVerifyCode").focus(function () {

        hideTips($("#tipsCode"));
        hideTips($("#tipsFeedback"));

    });

}


function showTips($element, msg) {

    $element.text(msg);
    $element.css("display", "block");

}


function hideTips($element) {

    $element.css("display", "none");

}
