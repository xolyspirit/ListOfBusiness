package listofbusiness

import grails.test.hibernate.HibernateSpec

class UserLoginUniqueConstraintSpec extends HibernateSpec{
    List<Class> getDomainClasses() { [User] }

    def "Test login unique constraint"(){
        when: "save first user"
        def u = new User(
                login: 'someName',
                password: 'somePassword')
        then:
        u.validate()
        u.save()
        User.count == old(User.count +1)

        when: "try to save duplicate user"
        def u2 = new User(
                login: 'someName',
                password: 'somePassword')
        then:
        !u2.validate(['login'])
        u2.errors['login']?.code == 'unique'
        !u2.save()
        User.count == old(User.count)
    }
}
