$(function () {

    $("#logo").click(function () {

        window.location.href = "/RentingSSH/pages/index.htm";

    });

});


// 退出登陆

function logout() {

    var params = {

        url: "/RentingSSH/user/logout",

        success: function (responseData) {

        },

        error: function (responseData) {

            // do nothing

        }
    };

    $.ajax(params);

}