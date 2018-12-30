package server

class Hop {
    String name
    String type
    String description
    String notes
    Double alpha
    Double beta
    String form
    Double hsi
    String origin
    String substitutes                          //FIXME: reference to other hops in db
    Double humulene
    Double caryophylene
    Double cohumulone
    Double mycrene
    
    static hasMany = [sources:Source] 
 
    static constraints = {
        description nullable:true
        notes nullable:true
        alpha nullable:true
        beta nullable:true
        form nullable:true
        hsi nullable:true
        origin nullable:true
        substitutes nullable:true
        humulene nullable:true
        caryophylene nullable:true
        cohumulone nullable:true
        mycrene nullable:true
    }
}