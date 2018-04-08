package server

class Receipt {
    String name
    String description
    String beerType
    String processType
    String reinheitsGebot

    static hasMany = [steps:Step]
    
    static constraints = {
    }
}
