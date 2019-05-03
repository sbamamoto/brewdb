package brewdb

import server.Brewer
import grails.rest.*
import grails.converters.*

class BrewerController {
    static responseFormats = ['json', 'xml']

    def show() { 
        
        def r
        
        if (params['id']!='-1') { 
            r = Brewer.findById(params['id'])
        }
        else {
            r = new Brewer();
        }
        r.password = ''; // dont send password to frontend
        render r as JSON
    }

    def index() { 
        def brewers = Brewer.findAll()
        return [brewerList:brewers]
    }

    def update() {
        def r = request.JSON
        println ("update ---- "+r)
        def i = Brewer.findById(params['id'])
        if (r[0].password) {
            r[0].password = r[0].password.encodeAsSHA256()
        }
        i.properties = r[0]

        i.save(flush:true)
        respond Brewer.findAll()
    }

    def save() {

        def r = request.JSON
        def i = new Brewer(r[0])
        i.password = i.password.encodeAsSHA256()
        println "****************"
        println r[0]
        println "****************"
        println i.userId
        println "****************"
        
//        def s = Source.findById(r[1])
//        i.source = s

        if (!i.save(flush:true)) {
            i.errors.allErrors.each {
                println it
            }
        }
        respond Brewer.findAll()
    }

}
