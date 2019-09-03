package server

class HopInStorage {
    Brewer owner
    Source source
    Hop hop
    String quantity
    Date storageDate
    Date orderDate
    Date expirationDate    

    static constraints = {
        source nullable:true
        storageDate nullable:true
        orderDate nullable:true
        expirationDate nullable:true
    }
}
