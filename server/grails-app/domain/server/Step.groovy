package server

class Step {

    String name
    String stepType
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
        timeUnit nullable:true
        temperature nullable:true
        description nullable:true
    }
}
