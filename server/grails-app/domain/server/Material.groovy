package server

class Material {
 
    String type
    String name
    String description
    String notes
    String value      //depends on type malt -> EBC, Hop -> alpha, ...

    static constraints = {
        description maxSize:2048, nullable:true   
        notes maxSize:2048, nullable:true
    }
    
    String toString() {
        description + "("+type+")"
    }
}
