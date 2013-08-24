/**
 * Created with IntelliJ IDEA.
 * User: wstarcev
 * Date: 13.08.13
 * Time: 23:37
 * To change this template use File | Settings | File Templates.
 */

var infoWdws = new Array();

function createMarker(map, home){
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(home.location.lat, home.location.lang),
        map: map,
        animation: google.maps.Animation.DROP,
        title: home.name
    });
    var infoWindow = new google.maps.InfoWindow({
        position: marker.position,
        content: "<h4>Info " + home.description.cost + "</h4>" +
            "<div>" + home.description.text + " <a href='/lot/" + home.id +
            "/'>show</a></div>"
    });
    infoWdws.push(infoWindow);
    marker.addListener("click", function () {
        for (var i in infoWdws) infoWdws[i].close();
        infoWindow.open(map, marker);
    });
}

function loadAllToMap(map){
    $.get("/allLots", function(data){
            var list = JSON.parse(data).list;
            for(var i in list){
                createMarker(map, list[i]);
            }
        }
    )

}

$(function(){


})