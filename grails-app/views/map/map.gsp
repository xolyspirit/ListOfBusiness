<%@ page import="grails.converters.JSON" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <title>Google Maps</title>
    <script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCouSzulg3ebDNdayRuRovhAooawOmkm7I&callback=initMap">
    </script>
</head>
<body >
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div
     id="map" style="width: 1200px; height: 600px">
</div>
<script>
    var map;
    var infoWindow;
    function initMap() {
        var myLatLng = {lat: 53.8992691, lng: 27.5624294};
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 12,
            center: myLatLng
        });
        var markers =[];
        <g:each var = "b" in= "${bList}">
        <g:if test="${b.latitude||b.longitude}" >
        var coords = {lat: ${b.latitude}, lng: ${b.longitude}};
        var marker = new google.maps.Marker({
            position: coords,
            title: "${b.name}",
            map: map
        });
        markers.push(marker);
        </g:if>
        </g:each>
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});

    }
</script>

<g:form controller="map" action="showNear" method="POST">
    <h3>My coordinates is</h3>
    <dt> Latitude</dt>
    <dd><g:textField name="lat" value="53.8954955"/></dd>
    <dt> Longitude</dt>
    <dd><g:textField name="lng" value="27.5357226"/></dd>
    <dt> Show me range</dt>
    <dd><g:textField name="range" value="5"/></dd>
    <dd>
        <g:actionSubmit name="show" action="showNear" value="show"/>
    </dd>
</g:form>

</body>
</html>