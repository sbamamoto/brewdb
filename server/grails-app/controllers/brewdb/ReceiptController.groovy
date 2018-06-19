package brewdb


import grails.rest.*
import grails.converters.*
import server.Receipt

class ReceiptController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
        def receipts = Receipt.findAll()
        return [receiptList:receipts]
    }

    def show() {
        def m
        if (params['id']!='-1') { 
            m = Receipt.findById(params['id'])
        }
        else {
            m = new Receipt();
        }
        
        def responseData = [
            'material': m,
            'types': ["Malz","Hopfen","Hefe","Wasser","Andere","Aurüstung"]
        ]
        
        render responseData as JSON
    }
    
    def delete() {
        def i = Receipt.findById(params['id'])
        i.delete(flush:true)
        respond Receipt.findAll()
    }    
    
    def update() {
        def r = request.JSON
        println ("update ---- "+r)
        def i = Receipt.findById(params['id'])
    //    def s = Source.findById(r[1])
    //    println(s.name)
        i.properties = r[0]
    //    i.source = s
        i.save(flush:true)
        respond Receipt.findAll()
    }
    
    
    def save() {
        def r = request.JSON

        
        def i = new Receipt(r[0])
        
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
        respond Receipt.findAll()
    }
}
