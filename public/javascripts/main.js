/**
 * Created with IntelliJ IDEA.
 * User: wstarcev
 * Date: 13.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

function credentials(){
    return {
        user : $("#loginForm .username input[type=text]").val(),
        pass : $("#loginForm .password input[type=password]").val()
    }
}

function post(url, type, data ){
    $.ajax({
        type: "POST",
        contentType: type,
        url: url,
        data: JSON.stringify(data)
    })
}

function attach_login_action(buttons){
    $(buttons).children(".reg").on("click", function(event){
        post(
            "/users/register",
            "application/json; charset=utf-8",
            { register: credentials() }
        )
    })

    $(buttons).children(".login").on("click", function (event) {
        post(
            "/users/login",
            "application/json; charset=utf-8",
            { login: credentials() }
        )
    })
}

$(function(){
    attach_login_action($("#loginForm #loginButtons"));
})