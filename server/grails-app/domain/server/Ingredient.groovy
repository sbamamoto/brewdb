package server

class Ingredient {
    
    String units
    String measure
    Integer temperature
    Integer cookingTime

    static constraints = {
        temperature nullable:true
        cookingTime:true
    }
}
