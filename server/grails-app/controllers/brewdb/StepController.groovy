package brewdb


import grails.rest.*
import grails.converters.*
import server.Step
import server.Receipt

class StepController {
	static responseFormats = ['json', 'xml']
	
    def index() { 
        def receipts = Receipt.findAll()
        return [receiptList:receipts]
    }

    def show() {
        def s
        println(" ********  " + params['id'])
        if (params['id']!='-1') { 
            s = Step.findById(params['id'])
        }
        else {
            s = new Step();
        }
        
        render s as JSON
    }
    
    def delete() {
        def i = Receipt.findById(params['id'])
        i.delete(flush:true)
        respond Receipt.findAll()
    }    
    
    def update() {
        def r = request.JSON
        println ("update ---- "+r)
        def i = Step.findById(params['id'])
    //    def s = Source.findById(r[1])
    //    println(s.name)
        i.properties = r[0]
    //    i.source = s
        i.save(flush:true)
        respond Step.findAll()
    }
    
    
    def save() {
        def r = request.JSON

        
        def i = new Step(r[0])
        
        println "****************"
        println r[0]
        println "****************"
        
//        def s = Source.findById(r[1])
//        i.source = s
        i.orderNumber = Step.createCriteria().get {   
            projections {
                max "orderNumber"
            }       
        } as Long

        if (i.orderNumber == null ) {
            i.orderNumber = 0;
        }
        else {
            i.orderNumber = i.orderNumber +1
        }

        if (!i.save(flush:true)) {
            i.errors.allErrors.each {
                println it
            }
        } else {
            def receipt = Receipt.findById(r.receiptId);
            receipt.addToSteps(i);
            if (!receipt.save(flush:true)) {
                receipt.errors.allErrors.each {
                    println it
                }
            }
        }


        respond Step.findAll()
    }
}
