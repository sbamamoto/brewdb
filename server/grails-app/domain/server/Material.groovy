package server

class Material {

    String articleNo

    static constraints = {
        articleNo nullable:true
    }
    
    String toString() {
        description + "("+type+")"
    }
}
