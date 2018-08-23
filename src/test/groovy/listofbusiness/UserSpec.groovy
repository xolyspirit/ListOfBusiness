package listofbusiness

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class UserSpec extends Specification implements DomainUnitTest<User> {

    void "test persistence"() {

        setup:
        new User(login: 'User', password: 'Password123').save()
        new User(login: 'Admin', password: 'Password123456', role: 'admin').save()

        expect:
        User.count == 2
        User.findByLogin('Admin').isAdmin()
        !User.findByLogin('User').isAdmin()
    }

    void "test login"(){
        when:
        domain.login = value
        then:
        expected == domain.validate(['login'])
        domain.errors['login']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'yyy'                  |  false   | 'size.toosmall'
        'a'*51                 |  false   | 'size.toobig'
        'someLogin'            |  true    | null
    }

    void "test password"(){
        when:
        domain.password = value
        then:
        expected == domain.validate(['password'])
        domain.errors['password']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'yyy'                  |  false   | 'size.toosmall'
        'a'*256                |  false   | 'size.toobig'
        'somePassword'         |  true    | null
    }

    void "test role"(){
        when:
        domain.role = value
        then:
        expected == domain.validate(['role'])
        domain.errors['role']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'client'               |  false   | 'not.inList'
        'user'                 |  true    | null
        'admin'                |  true    | null
    }


}

