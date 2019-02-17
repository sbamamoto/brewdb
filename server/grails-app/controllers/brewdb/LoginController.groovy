package brewdb


import grails.rest.*
import grails.converters.*
import server.Material
import server.Brewer
import server.MaterialType


class LoginController {
static responseFormats = ['json']
def save() {
        def r = request.JSON
        println "****************"
        println r
        println r.password.encodeAsSHA256()
        println "****************"

        def user = Brewer.findByUserId(r.username); 
        println user
        if (user) {
            println r.password.encodeAsSHA256() + "==" + user.password
            if (r.password.encodeAsSHA256() == user.password) {
                session['user'] = user
                render user as JSON
            }
            else {
                render '{"isActive": false}'
            }
        }   
        else {
            render '{"isActive": false}'
        }
    }

    def show() {
        if (session['user']) {
            render session['user'] as JSON
        }
        else {
            render '{"isActive": false}'
        }
    }
}