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
        
        render r as JSON
    }

    def index() { 
        def brewers = Brewer.findAll()
        return [brewerList:brewers]
    }

    def save() {

        def r = request.JSON
        def i = new Brewer(r[0])
        
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
