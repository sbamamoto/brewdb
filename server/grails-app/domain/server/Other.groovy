package server

class Other {
    String name
    String notes
    String description


    static constraints = {
        notes nullable:true
        description nullable:true
    }
}