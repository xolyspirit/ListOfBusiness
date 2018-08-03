package listofbusiness

class MapController {
    def index = {
        showAll(params)
    }
    def showAll = {
        Business[] bList = Business.getAll()
        render(view: "map", model:[bList:bList])
    }
    def showNear = {
        Double lng = params['lng'] as Double
        Double lat = params['lat'] as Double
        Integer range = params['range'] as Integer
        Float r = range/100

        String minRangeLat = "${lat-r}"
        String maxRangeLat = "${lat+r}"
        String minRangeLng = "${lng-r}"
        String maxRangeLng = "${lng+r}"

        Business[] bList = Business.findAllByLongitudeBetweenAndLatitudeBetween(
                minRangeLng,maxRangeLng,minRangeLat,maxRangeLat)
        render(view: "map", model:[bList:bList])
    }
}
