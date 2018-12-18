package server

class Profile {
    String profileName
    Stove stove
    MashTun mashTun
    LauterTun lauterTun
    Fermenter fermenter
    
    static constraints = {
        profileName nullable:true
        stove nullable:true
        mashTun nullable:true
        lauterTun nullable:true
        fermenter nullable:true
    }
}
