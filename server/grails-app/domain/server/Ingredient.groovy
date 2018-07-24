package server

class Ingredient {
    
    String units
    String measure

    static belongsTo = [material: Material]
 
    static mapping = {
        material lazy: false
    }
    
    String toString() {
        description + "("+type+")"
    }
}
