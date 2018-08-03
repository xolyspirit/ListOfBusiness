package listofbusiness

class GeocodeService {
    static transactional = true
    def base = 'http://maps.google.com/maps/api/geocode/xml?'
    def key = 'key=AIzaSyCouSzulg3ebDNdayRuRovhAooawOmkm7I'
    def slurper = new XmlSlurper()

    def fillInLatLong(Business business) {
            def params = [sensor : false,
                          address: [business.street, 'RU'].collect {
                              URLEncoder.encode(it, 'UTF-8')
                          }.join(',+')]
            def url = base + params.collect { k, v -> "$k=$v" }.join('&') + key
            def response = slurper.parse(url)
            business.latitude = response.result.geometry.location.lat
            business.longitude = response.result.geometry.location.lng
        return business
        }
}


