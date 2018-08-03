package listofbusiness

import grails.gorm.services.Service

@Service(Business)
interface BusinessService {

    Business get(Serializable id)

    List<Business> list(Map args)

    Long count()

    void delete(Serializable id)

    Business save(Business business)

}