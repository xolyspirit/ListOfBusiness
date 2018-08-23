package listofbusiness

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class UserControllerSpec extends Specification implements ControllerUnitTest<UserController> {

    void "Test the index action returns the correct model"() {
        given:
        List<User> someUsers = [new User(login: 'User', password: 'Password'),
                                new User(login: 'Admin', password: 'Password2', role: 'admin')]
        controller.userService = Stub(UserService){
            list(_)>>someUsers
            count()>>someUsers.size()
        }

        when: 'The index action is executed'
        controller.index()

        then: 'The model is correct'
        model.userList
        model.userList.size() == 2
        model.userList.find { it.login == 'User' && it.password == 'Password'}
        model.userList.find { it.login == 'Admin' && it.password == 'Password2'}
        model.userCount == 2
    }

    void "Test the save action with correct user"(){
        given:
        String login = 'User'
        String password = 'Password'
        controller.userService = Stub(UserService){
            save(_)>>new User(login: login, password: password,)
        }

        when:
        request.method = 'POST'
        request.contentType = FORM_CONTENT_TYPE
        controller.save(new User(login: login, password: password))

        then: 'a message indicating that the user has been saved is placed'
        flash.message

        and: 'the user is redirected to show the user'
        response.redirectedUrl.startsWith('/user/show')

        and: 'a found response code is used'
        response.status == 302
    }

    void "Test the save action with incorrect user"(){
        given:
        String login = 'User'
        String password = 'Password'
        controller.userService = Stub(UserService){
            save(_)>>new User(login: login, password: password,)
        }

        when:
        request.method = 'POST'
        request.contentType = FORM_CONTENT_TYPE
        controller.save(null)

        then:"a message indicating that the user is incorrect"
        flash.message

        and: 'the user is redirected to index page'
        response.redirectedUrl.startsWith('/user/index')

        and: 'a found response code is used'
        response.status == 302
    }

}