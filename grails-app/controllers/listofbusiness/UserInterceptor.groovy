package listofbusiness


class UserInterceptor {
    int order = HIGHEST_PRECEDENCE
    UserInterceptor(){
        match(controller:'*', action:'*').excludes(controller:'user', action:'login').
                excludes(controller:'user', action:'authenticate')
    }

    boolean before() {
        if (!session.user){
            flash.message = "For login user only"
            redirect(controller: "user", action: "login")
            return false
         }
        return true
    }

}
