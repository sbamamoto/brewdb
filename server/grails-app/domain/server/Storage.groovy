package server

class Storage {
    Brewer owner                        // Storage Owner
    Source source                       // Source of material    

    String quantity
    String unit
    
    static hasMany = [
        malts:MaltInStorage,
        hops:HopInStorage,
        yeasts:YeastInStorage
    ]

    static constraints = {
        source nullable:true
    }
}
