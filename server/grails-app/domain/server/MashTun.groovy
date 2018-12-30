package server

class MashTun {
    String name
    String product
    Integer volume
    String description
    String notes
    Double heatRate                     // in degrees Celsius per Minute
    SmartController controller
    static constraints = {
        description nullable:true
        notes nullable:true
        controller nullable:true
    }
}
