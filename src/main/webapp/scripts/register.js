$(function () {

    $("#btn_register").click(function () {

        hideTips($("#tipsRealname"));

        beforeRegister();

    });


    checkLogin();

    addTipsHideListener();

    addUsernameInputListener();

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

        }

    };

    $.ajax(params);

}


function onRegisterSuccess() {

    $("table").css("text-align", "center");

    $("table").html('恭喜您注册成功 !<br/>' +
        '<a href="/RentingSSH/pages/login.htm">立即登陆</a><br/>' +
        '<a href="/RentingSSH/pages/index.htm">前往首页</a>');

    $("#btn_register").css("display", "none");

}


// 注册之前检查用户输入的信息是否合法

function beforeRegister() {

    if (isUsernameValid() & isUsernameExist & isPwdValid() & isRepwdValid() & isPhoneValid() & isRealnameValid()) {

        register();

    }
}


function register() {

    var obj = new Object();

    obj['user.username'] = $("input[name=name]").val();
    obj['user.password'] = $("input[name=password]").val();
    obj['user.telephone'] = $("input[name=telephone]").val();
    obj['user.realname'] = $("input[name=realname]").val();

    var params = {

        type: "post",
        url: "/RentingSSH/user/register",
        data: obj,

        success: function (responseData) {

            if (responseData['msg'] == 'OK' && responseData['status'] == 1) {

                onRegisterSuccess();

            } else {

                showTips($("#tipsRealname"), "注册失败，请检查您输入的信息");

            }
        },

        error: function (data) {

            showTips($("#tipsRealname"), "网络异常，请重试");

        }
    };

    $.ajax(params);

}


function isUsernameValid() {

    var username = $("input[name='name']").val();

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

    var password = $("input[name='password']").val();

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


function isRepwdValid() {

    var repassword = $("input[name='repassword']").val();

    if (repassword == null || repassword.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showTips($("#tipsRePwd"), "确认密码不能为空");

        return false;


    } else if (repassword.length < 5) {

        showTips($("#tipsRePwd"), "确认密码长度不能小于5");

        return false;

    } else if (repassword.match("^(?!_)(?!.*?_$)[a-zA-Z0-9=_\u4e00-\u9fa5]+$") == null) {

        showTips($("#tipsRePwd"), "确认密码只能包含数字字母和等号下划线");

        return false;

    } else if ($("input[name='password']").val() != repassword) {

        showTips($("#tipsRePwd"), "确认密码与上面的密码不匹配，请重新输入");

        return false;

    }

    else {

        return true;

    }

}


function isPhoneValid() {

    var telephone = $("input[name='telephone']").val();

    if (telephone == null || telephone.replace(/(^\s*)|(\s*$)/g, "") == '') {

        showTips($("#tipsPhone"), "电话号码不能为空");

        return false;

    }

    if (!(/^1(3|4|5|7|8)\d{9}$/.test(telephone)) || !/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(telephone)) {

        showTips($("#tipsPhone"), "您输入的不是固话也不是移动电话，请重新输入");

        return false;

    }

    return true;

}


function isRealnameValid() {

    var realname = $("input[name='realname']").val();

    if (realname.length > 30) {

        showTips($("#tipsRealname"), "真实姓名长度不能超过30个字符，请重新输入");

        return false;

    }

    return true;

}


function addTipsHideListener() {

    $("input[name='name']").focus(function () {

        hideTips($("#tipsUsername"));
        hideTips($("#tipsRealname"));

    });


    $("input[name='password']").focus(function () {

        hideTips($("#tipsPassword"));
        hideTips($("#tipsRealname"));

    });

    $("input[name='repassword']").focus(function () {

        hideTips($("#tipsRePwd"));
        hideTips($("#tipsRealname"));

    });


    $("input[name='telephone']").focus(function () {

        hideTips($("#tipsPhone"));
        hideTips($("#tipsRealname"));

    });


    $("input[name='realname']").focus(function () {

        hideTips($("#tipsRealname"));

    });


}


var isUsernameExist = false;

function addUsernameInputListener() {

    $("input[name='name']").blur(function () {

        var username = $("input[name='name']").val();

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

                        showTips($("#tipsUsername"), "该用户名已存在，请使用其他用户名");

                        isUsernameExist = false;

                    } else {

                        isUsernameExist = true;

                    }

                },

                error: function (data) {

                    showTips($("#tipsUsername"), "网络异常，请重试");

                    isUsernameExist = false;

                }

            });

        }

    });

}


function showTips($element, msg) {

    $element.text(msg);
    $element.css("display", "block");

}


function hideTips($element) {

    $element.css("display", "none");

}