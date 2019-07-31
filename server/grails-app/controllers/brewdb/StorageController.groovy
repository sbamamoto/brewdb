package brewdb


import grails.rest.*
import grails.converters.*
import server.Storage
import server.Material
import server.Source

class StorageController {
	static responseFormats = ['json', 'xml']
	
    def index() {
        def materials = Storage.list();

        def responseData = [
            'materials': materials
           ]
        println ("INDEEEEX");
        JSON.use('deep'){
            render responseData as JSON
        }
    }

    def show() {         
        def types = Material.executeQuery ('select distinct type from Material');
        def materials = Material.list();
        def storageItem = new Storage()
        def sources = Source.list();
        def responseData = [
            'types': types,
            'materials': materials,
            'sources': sources,
            'storage': storageItem
           ]
        JSON.use('deep'){
            render responseData as JSON
        }
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

        
        def i = new Storage(r[0])
        i.owner = session['user']
        i.material = Material.findById(r['material'])
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
        respond Storage.findAll()
    }
    

}