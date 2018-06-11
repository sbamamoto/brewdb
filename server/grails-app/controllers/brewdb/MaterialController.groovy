package brewdb


import grails.rest.*
import grails.converters.*
import server.Material
import server.Source
import server.MaterialType


class MaterialController {
    static responseFormats = ['json', 'xml']
	
    def index() { 
        def materials = Material.findAll()
        return [materialList:materials]
    }
    
    def show() {
        def m
        if (params['id']!='-1') { 
            m = Material.findById(params['id'])
        }
        else {
            m = new Material();
        }
        
        def responseData = [
            'material': m,
            'types': ["Malz","Hopfen","Hefe","Wasser","Andere","Aurüstung"]
        ]
        
        render responseData as JSON
    }
    
    def delete() {
        def i = Material.findById(params['id'])
        i.delete(flush:true)
        respond Material.findAll()
    }    
    
    def update() {
        def r = request.JSON
        println ("update ---- "+r)
        def i = Material.findById(params['id'])
    //    def s = Source.findById(r[1])
    //    println(s.name)
        i.properties = r[0]
    //    i.source = s
        i.save(flush:true)
        respond Material.findAll()
    }
    
    
    def save() {
        def r = request.JSON

        
        def i = new Material(r[0])
        
        println "****************"
        println r[0]
        println "****************"
        
//        def s = Source.findById(r[1])
//        i.source = s

        if (!i.save(flush:true)) {
            i.errors.allErrors.each {
                println it
            }
        }
        respond Material.findAll()
    }
    
}
