/**
 * Created with IntelliJ IDEA.
 * User: wstarcev
 * Date: 13.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

var homes;
function loadAllToMap(map){
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: "/allLots",
        success: function(data){
            homes = eval('(' + data + ')').list;
            for(var i = 0; i < homes.length; i++){
                var houme = homes[i];
                new google.maps.Marker({
                    position: new google.maps.LatLng(houme.location.lat, houme.location.lang),
                    map: map,
                    animation: google.maps.Animation.DROP,
                    title: houme.name
                }).addListener("click", function () {
                        $(".houmeInfo").html("")
                        $(".houmeInfo").html("<h4>Info " + houme.description.cost + "</h4>" +
                            "<div>" + houme.description.text + "</div>");
                    });
            }
        }
    })

}

$(function(){


})