package server

class Ingredient {
    
    String type
    String description
    String notes

    static belongsTo = [source: Source]

    static constraints = {
        description nullable:true
        notes nullable:true
    }
    
    static mapping = {
        source lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
