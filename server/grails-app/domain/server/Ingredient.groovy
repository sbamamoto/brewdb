package server

class Ingredient {
    
    String units
    String measure

    static belongsTo = [material: Material]

    static constraints = {
        description nullable:true
        notes nullable:true
    }
    
    static mapping = {
        material lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
