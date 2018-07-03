package server

class Step {

    String name
    String type
    String duration
    String timeUnit
    String temperature
    String description
    Integer orderNumber
    
    
    static hasMany = [
        ingredients:Ingredient, 
        equipment:Material
    ]
    
    static constraints = {
    }
}
