package server

class Brewer {
    String firstname
    String lastname
    String userId
    String password
    boolean isActive
    boolean canWrite
    
    static hasMany = [
        profiles:Profile,
        receipts:Receipt
    ]

    static constraints = {
    }
}
