package server

class Material {
    String type
    String description
    String notes
    String sourceUrl

    static belongsTo = [source: Source]

    static constraints = {
        description nullable:true
        notes nullable:true
        sourceUrl nullable:true
    }
    
    static mapping = {
        source lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
