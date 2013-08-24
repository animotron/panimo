/**
 * Created with IntelliJ IDEA.
 * User: wstarcev
 * Date: 13.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

function createMarker(map, home){
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(home.location.lat, home.location.lang),
        map: map,
        animation: google.maps.Animation.DROP,
        title: home.name
    });
    marker.addListener("click", function () {
        $(".homeInfo").html("<h4>Info " + home.description.cost + "</h4>" +
            "<div>" + home.description.text + "</div>");
    });
}

function loadAllToMap(map){
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "/allLots",
        success: function(data){
            var list = JSON.parse(data).list;
            for(var i in list){
                createMarker(map, list[i]);
            }
        }
    })

}

$(function(){


})