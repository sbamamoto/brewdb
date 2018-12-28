package server

class Hop {
    String name
    String type
    String description
    String notes
    Integer alpa
    Integer beta
    String form
    
    static hasMany = [sources:Source] 
 
    static constraints = {
        description nullable:true
        notes nullable:true
    }
}