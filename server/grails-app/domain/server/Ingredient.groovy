package server

class Ingredient {
    
    String units
    String measure
    Integer temperature

    static constraints = {
        temperature nullable:true
    }
}
