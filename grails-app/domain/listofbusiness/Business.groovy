package listofbusiness

class Business implements Serializable,Comparable{
    String name
    String email
    String street
    String zip
    String latitude
    String longitude

    static constraints = {
        name(size: 1..50, unique: true)
        email(email: true, size: 5..50)
        street( size: 1..50)
        zip(blank: true,nullable: true, size: 1..50)
        latitude(display:false, blank: true,nullable: true)
        longitude(display:false, blank: true, nullable: true)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Business business = (Business) o

        if (email != business.email) return false
        if (name != business.name) return false
        if (street != business.street) return false
        if (zip != business.zip) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (street != null ? street.hashCode() : 0)
        result = 31 * result + (zip != null ? zip.hashCode() : 0)
        return result
    }

    @Override
    int compareTo(Object o) {
        Business b = o as Business
        if (this.name==b.name)
            return o
        else if (this.name>b.name)
            return 1
        else
            return -1
    }
}
