package brewdb;

import grails.rest.*
import grails.converters.*

class LogoutController {
    static responseFormats = ['json']

    def show() {
        println (" ##### User auth request received")
        session['user'] = null;
        render '{"isActive": false}'
    }


    def index() {
        println (" ##### User auth request received")
        session['user'] = null;
        render '{"isActive": false}'
    }

}