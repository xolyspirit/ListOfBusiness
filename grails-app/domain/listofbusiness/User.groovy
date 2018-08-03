package listofbusiness

class User {
    String login
    String password
    String role = "user"

    static constraints = {
        login(size: 4..50, unique: true, nullable: false, blank: false)
        password(size: 4..255,password:true, blank: false, nullable: false)
        role(inList: ["admin","user"], blank: false, nullable: false)
    }

    static transients = ['admin']

    String toString() {
        login
    }

    def beforeInsert = {
        password = password.encodeAsSHA256()
    }

    boolean isAdmin(){
        role=="admin"
    }
}
