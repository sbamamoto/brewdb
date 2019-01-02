package class

class BeerType {
    String name
    String type      // Type identifier unique id to programmatical identify beer type
    Water water 

    static constraints = {
        type nullable:true
        water nullable:true
    }

}