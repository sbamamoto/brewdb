package server

class YeastInStorage {
    Brewer owner
    Source source
    Yeast yeast
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
