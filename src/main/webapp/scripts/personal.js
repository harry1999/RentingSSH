window.onload = function () {

    getMe();

    checkOrderTable();

    closeDialogComfirm();

    closeDialogTips();

    bindBtnClickEvent();

}

// 刷新页面后重新获取当前用户的相关信息

window.onunload = function () {

    getMe();

}


function getMe() {

    $.ajax({

        url: "/RentingSSH/user/me",

        success: function (data) {

        },

        error: function (data) {

        }

    });

}


function checkOrderTable() {

    var item = sessionStorage.getItem("orderId");
    var $input = $("input");

    for (var i = 0; i < $input.length; i++) {

        if ($input[i].name == item) {

            sessionStorage.clear();
            window.location.reload();

        }
    }
}


function bindBtnClickEvent() {

    $("input[value=删除]").click(function () {

        var orderId = this.name;

        sessionStorage.setItem("orderId", orderId);

        openDialogComfirm("确认删除这条订单吗?");

    });


    $("#auComfirm").click(function () {

        closeDialogComfirm();

        delOrder();

    });


    $("#auCannel").click(function () {

        closeDialogComfirm();

    });

    $("#auGot").click(function () {

        closeDialogTips();

        window.location.reload();

    });

}




function delOrder() {

    $.ajax({

        url: "/RentingSSH/user/deleteOrder?orderId=" + sessionStorage.getItem("orderId"),

        success: function (data) {

            if (data['status'] == 1 && data['msg'] == 'OK') {

                openDialogTips("订单删除成功");

            } else {

                openDialogTips("网络异常，请重试");

            }

        },

        error: function (data) {

            openDialogTips("网络异常，请重试");

        }

    });

}


function openDialogComfirm(msg) {

    $("#dialogComfirm").html(msg);

    $("#dialogComfirm").dialog('open');

}


function closeDialogComfirm() {

    $("#dialogComfirm").dialog('close');

}


function openDialogTips(msg) {

    $("#dialogTips").html(msg);

    $("#dialogTips").dialog('open');

}


function closeDialogTips() {

    $("#dialogTips").dialog('close');

}
