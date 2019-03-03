package brewdb


import grails.rest.*
import grails.converters.*
import server.Material
import server.Brewer
import server.MaterialType


class AuthController {
    def index() {
        println (" ##### User auth request received")
        if (session['user']) {
            println "      user is logged in"
            render session['user'] as JSON
        }
        else {
            println "      user is NOT logged ing"
            render '{"isActive": false}'
        }
    }
}