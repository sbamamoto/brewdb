package server

class Ingredient {
    
    String units
    String measure
    Integer temperature

    static belongsTo = [material: Material]
 
    static mapping = {
        material lazy: false
    }

    static constraints = {
        temperature nullable:true
    }
}
