function viewLocation(url){
    $.ajax({
        url: url,
        type: "GET",
        contentType:
            "application/json",
        success: function(location) {
            getMap(location)
        }
    });
}


function getMap(location) {
    let popup = "<b>"+location.city+"</b><br>";
    let mymap = L.map('mapid').setView([location.latitude,location.longitude], 13);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(mymap);

    L.marker([location.latitude,location.longitude]).addTo(mymap).bindPopup(popup).openPopup();
}