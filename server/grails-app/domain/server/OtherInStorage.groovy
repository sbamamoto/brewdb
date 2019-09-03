package server

class OtherInStorage {
    Brewer owner
    Source source
    Other other
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
