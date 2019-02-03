package server

class Receipt {
    String name
    String description
    String beerType
    String processType
    boolean reinheitsGebot
    String ibu
    Double wort
    String color
    String alcohol
    String yeastPosition
    String sourceUrl
    String brewer
    String meshWater
    String water
    String carbonisation
    String rinseWater
    String finalGravity
    Brewer addedBy
    
    static hasMany = [steps:Step]
    
    static constraints = {
        yeastPosition nullable:true
        sourceUrl maxSize:1024, nullable:true
        brewer nullable:true
        reinheitsGebot nullable:true
        meshWater nullable:true
        water nullable:true
        carbonisation nullable:true
        rinseWater nullable:true
        finalGravity nullable:true
        addedBy nullable:true
        description maxSize:2048, nullable:true
    }
}
