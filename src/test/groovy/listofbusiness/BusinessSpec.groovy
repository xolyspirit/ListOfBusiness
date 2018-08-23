package listofbusiness

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class BusinessSpec extends Specification implements DomainUnitTest<Business> {

    void "test persistence"() {
        setup:
        new Business(
                name: 'myBusiness',
                email: 'email@ya.ru',
                street: 'улица Притыцкого 49, Минск',
                zip: '55025').save()
        new Business(
                name: 'myBusiness2',
                email: 'email2@ya.ru',
                street: 'улица Притыцкого 48, Минск',).save()

        expect:
        Business.count == 2
    }

    void "test name"(){
        when:
        domain.name = value
        then:
        expected == domain.validate(['name'])
        domain.errors['name']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'y'                    |  false   | 'size.toosmall'
        'a'*51                 |  false   | 'size.toobig'
        'someName'             |  true    | null
    }

    void "test email"(){
        when:
        domain.email = value
        then:
        expected == domain.validate(['email'])
        domain.errors['email']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'a'*50 + '@ya.ru'      |  false   | 'size.toobig'
        'email@ya.ru'          |  true    | null
        'notEmail'             |  false   | 'email.invalid'
    }

    void "test street"(){
        when:
        domain.street = value
        then:
        expected == domain.validate(['street'])
        domain.errors['street']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  false   | 'nullable'
        'st'                    |  false   | 'size.toosmall'
        'a'*51                 |  false   | 'size.toobig'
        'someName'             |  true    | null
    }

    void "test zip"(){
        when:
        domain.zip = value
        then:
        expected == domain.validate(['zip'])
        domain.errors['zip']?.code == expectedErrorCode
        where:
        value                  | expected | expectedErrorCode
        null                   |  true    | null
        '123'                  |  false   | 'size.toosmall'
        '5'*51                 |  false   | 'size.toobig'
        '154786'               |  true    | null
    }
}