package server

class Step {

    String name
    String stepType
    String startTime
    String duration
    String timeUnit
    String temperature
    String description
    Integer orderNumber
    
    
    static hasMany = [
        ingredients:Ingredient, 
        hops:Hop,
        equipment:Material
    ]
    
    static constraints = {
        timeUnit nullable:true
        temperature nullable:true
        description nullable:true
        duration nullable:true
    }
}
