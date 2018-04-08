package server

class Step {

    String type
    String duration
    String timeUnit
    
    static hasMany = [ingredients:Ingredient]
    
    static constraints = {
    }
}
