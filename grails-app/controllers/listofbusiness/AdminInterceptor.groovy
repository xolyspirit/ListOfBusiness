package listofbusiness


class AdminInterceptor {

    AdminInterceptor(){
        match(controller:'*', action:"(create|edit|update|delete|save)")
        match(controller:'user',action:'*').excludes(controller:'user', action:'login').
                excludes(controller:'user', action:'authenticate')
    }
    boolean before() {
        if (session?.user?.role !='admin') {
            flash.message = "For admins only"
            redirect(controller: "user", action: "login")
            return false
        }
        return true
    }
}
