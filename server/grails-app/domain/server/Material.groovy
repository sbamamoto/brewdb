package server

class Material {
 
    String type
    String name
    String description
    String notes
    String sourceUrl
    String value      //depends on type malt -> EBC, Hop -> alpha, ...

    static belongsTo = [source: Source]

    static constraints = {
        description maxSize:2048, nullable:true   
        notes maxSize:2048, nullable:true
        sourceUrl nullable:true
        source nullable:true
    }
    
    static mapping = {
        source lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
