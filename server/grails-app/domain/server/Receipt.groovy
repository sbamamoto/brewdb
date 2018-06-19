package server

class Receipt {
    String name
    String description
    String beerType
    String processType
    String reinheitsGebot
    String ibu
    String wort
    String color
    String alcohol
    String yeastPosition
    String sourceUrl
    String brewer
    

    static hasMany = [steps:Step]
    
    static constraints = {
    }
}
