package server

class Hop {
    String name
    String fermentationType                     // TOP / BOTTOM
    String type                                 // DRY / FLUID
    String manufacturer
    Integer lowTemp
    Integer highTemp
    String flocculation
    Integer fermentation
    String description
    
    static hasMany = [sources:Source] 
 
    static constraints = {
        description nullable:true
        fermentationType nullable:true
        type nullable:true
        manufacturer nullable:true
        lowTemp nullable:true
        highTemp nullable:true
        flocculation nullable:true
        fermentation nullable:true
    }
}