package server

class MaltInStorage {
    Brewer owner
    Source source
    Malt malt
    String quantity
    Date storageDate
    Date orderDate
    Date expirationDate

    static constraints = {
        storageDate nullable:true
        orderDate nullable:true
        expirationDate nullable:true
        source nullable:true
    }
}
