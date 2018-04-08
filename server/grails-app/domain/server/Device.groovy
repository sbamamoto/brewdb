package server
import grails.rest.Resource

@Resource(uri='/device')
class Device {
String name
String type
String source

    static constraints = {
    }
}
