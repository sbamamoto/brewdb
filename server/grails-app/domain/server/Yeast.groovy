package server

class Yeast {
    String name
    String notes
    String description    
    String fermentationType                     // TOP / BOTTOM
    String type                                 // DRY / FLUID
    String manufacturer
    Integer lowTemp
    Integer highTemp
    String flocculation
    Integer fermentation
    
    static hasMany = [sources:Source] 
 
    static constraints = {
        notes nullable:true
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