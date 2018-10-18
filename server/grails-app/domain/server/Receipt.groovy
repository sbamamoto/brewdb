package server

class Receipt {
    String name
    String description
    String beerType
    String processType
    boolean reinheitsGebot
    String ibu
    String wort
    String color
    String alcohol
    String yeastPosition
    String sourceUrl
    String brewer
    

    static hasMany = [steps:Step]
    
    static constraints = {
        yeastPosition nullable:true
        sourceUrl nullable:true
        brewer nullable:true
        reinheitsGebot nullable:true
    }
}
