package listofbusiness

import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

class BusinessController {

    def businessService
    def geocodeService
    def ReadCSVService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def upload(){
        def file = params.myFile
        MultipartFile[] files = file as MultipartFile[]
        InputStreamReader isr = new InputStreamReader(files[0].inputStream,"windows-1251")

        Map<String,Integer> status = new HashMap<>(
                saved:0,
                failed:0,
                updated:0,
                existed:0)

        List<Business> bList = ReadCSVService.readCSV(isr)
        bList.each {
            def b = Business.findByName(it.name)
            if (b){
                if (b == it) {
                    status.existed++
                }
                else {
                    b.name = it.name
                    b.email = it.email
                    b.zip = it.zip
                    if(b.street != it.street){
                        b.street = it.street
                        b = geocodeService.fillInLatLong(b)
                    }
                    b.save(flush:true)
                    status.updated++
                }
            }
            else {
                geocodeService.fillInLatLong(it)
                it.save(flush:true)
                status.saved++
            }
        }

        def message = ''
        flash.message = message

        status.each {
            if(it.value!=0) {
                message = message + " ${it.value} business were ${it.key} "
            }
        }
        request.withFormat {
            form multipartForm {
                redirect action:"index", method:"GET"
            }
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond businessService.list(params), model:[businessCount: businessService.count()]
    }

    def search(){
        String key = "%${params.key}%"
        Business[] bList = Business.findAllByNameLikeOrEmailLikeOrStreetLikeOrZipLike(key,key,key,key)
        flash.message = "${bList.size()} business was found"
        respond view: "search",model: [bList:bList]
    }

    def show(Long id) {
        respond businessService.get(id)
    }

    def create() {
        respond new Business(params)
    }

    def save(Business business) {
        if (business == null) {
            notFound()
            return
        }
        while (business.longitude.length()==0||business.latitude==0)
        geocodeService.fillInLatLong(business)

        try {
            businessService.save(business)
        } catch (ValidationException e) {
            respond business.errors, view: 'create'
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'business.label', default: 'Business'), business.id]) as Object
            redirect business
            }
            '*' { respond business, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond businessService.get(id)
    }

    def update(Business business) {
        if (business == null) {
            notFound()
            return
        }
        while (business.longitude.length()==0||business.latitude==0)
            geocodeService.fillInLatLong(business)
            try {
                businessService.save(business)
            } catch (ValidationException e) {
                respond business.errors, view: 'edit'
                return
            }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'business.label', default: 'Business'), business.id]) as Object
            redirect business
            }
            '*'{ respond business, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        businessService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'business.label', default: 'Business'), id]) as Object
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'business.label', default: 'Business'), params.id]) as Object
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Override
    String toString() {
        return "Business"
    }
}
