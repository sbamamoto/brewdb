package server

class LauterTun {
    String name
    String product
    Integer volume   // volume in liter
    String description
    String notes
    Double filteringSpeed // in Liter per minute
    Double temperatureDegrationRate // degration in degrees Celsius per minute
    SmartController controller
    static constraints = {
        description nullable:true
        notes nullable:true
        controller nullable:true
        temperatureDegrationRate nullable:true
    }
}
