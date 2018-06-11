package server

class Material {
 
    String type
    String name
    String description
    String notes
    String sourceUrl
    String stockUnits
    String measure
    String value      //depends on type malt -> EBC, Hop -> alpha, ...

    static belongsTo = [source: Source]

    static constraints = {
        description nullable:true
        notes nullable:true
        sourceUrl nullable:true
        stockUnits nullable:true
        measure nullable:true
        source nullable:true
    }
    
    static mapping = {
        source lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
