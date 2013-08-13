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

function post(url, data ){
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: url,
        data: JSON.stringify(data)
    })
}

function attach_login_action(buttons){
    $(buttons).children(".reg").on("click", function(event){
        post(
            "/users/register",
            { register: credentials() }
        )
    })

    $(buttons).children(".login").on("click", function (event) {
        post(
            "/users/login",
            { login: credentials() }
        )
    })
}

$(function(){
    attach_login_action($("#loginForm #loginButtons"));

    var socket = new WebSocket("ws:/localhost:9000/ws");
    socket.onmessage = function(data){
        console.log(data)
    }
    socket.send("Привет");

})