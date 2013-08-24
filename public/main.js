/**
 * Created with IntelliJ IDEA.
 * User: wstarcev
 * Date: 13.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

var homes;

function createMarker(map, houme){
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(houme.location.lat, houme.location.lang),
        map: map,
        animation: google.maps.Animation.DROP,
        title: houme.name
    });
    marker.set("houme", houme);
    marker.addListener("click", function () {
        var h = marker.get("houme");
        $(".houmeInfo").html("<h4>Info " + h.description.cost + "</h4>" +
            "<div>" + h.description.text + "</div>");
    });
}

function loadAllToMap(map){
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "/allLots",
        success: function(data){
            homes = eval('(' + data + ')').list;
            for(var i = 0; i < homes.length; i++){
                createMarker(map, homes[i]);
            }
        }
    })

}

$(function(){


})