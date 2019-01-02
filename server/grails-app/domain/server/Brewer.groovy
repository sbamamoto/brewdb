package server

class Brewer {
    String firstname
    String lastname
    String userId
    String password
    Date lastLogin
    Date lastPublished
    Integer receiptsPublished = 0
    Integer receiptsConfirmed = 0
    boolean isActive = true
    boolean canWrite = false
    Water water
    
    static hasMany = [
        profiles:Profile,
        receipts:Receipt
    ]

    static constraints = {
        firstname nullable:true
        lastname nullable:true
        lastLogin nullable:true
        lastPublished nullable:true
        water nullable:true
    }
}
