package listofbusiness

import grails.test.hibernate.HibernateSpec

class UserPasswordBeforeInsertSpec  extends  HibernateSpec{
    List<Class> getDomainClasses() { [User] }

    void "Test for encoding with SHA"(){
        when: "save user"
        def u = new User(
                login: 'someName',
                password: 'somePassword')
        u.save()
        then:
        u.password == 'somePassword'.encodeAsSHA256()
    }
}
