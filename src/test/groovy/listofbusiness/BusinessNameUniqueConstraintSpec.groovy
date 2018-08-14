package listofbusiness

import grails.test.hibernate.HibernateSpec

class BusinessNameUniqueConstraintSpec extends HibernateSpec{
    List<Class> getDomainClasses() { [Business] }

    def "Test email unique constraint"(){
        when: "save first business"
        def b = new Business(
                name: 'someName',
                email: 'someEmail@ya.ru',
                street: 'some Street 15, someCity')
        then:
        b.validate()
        b.save()
        Business.count == old(Business.count +1)

        when: "try to save duplicate business"
        def b2 = new Business(
                name: 'someName',
                email: 'someEmail@ya.ru',
                street: 'some Street 15, someCity')
        then:
        !b2.validate(['name'])
        b2.errors['name']?.code == 'unique'
        !b2.save()
        Business.count == old(Business.count)
    }

}
