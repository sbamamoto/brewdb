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

        JSON.use("deep") {
            render s as JSON
        }

//        render s as JSON
    }
    
    def delete() {
        def i = Receipt.findById(params['id'])
        def s = Step.findById(params['stepId'])
        println "ddddddddddd"
        println s
        println "ddddddddddd"
        i.removeFromSteps(s)
        if (!i.save(flush:true)) {
                i.errors.allErrors.each {
                    println it
                }
            }
        respond Receipt.findById(params['id']).steps
    }    
    
    def update() {
        def r = request.JSON
        println ("update ---- "+r)
        def i = Step.findById(params['id'])
        println "uuuuuuuuuuuuuuuu"
        println r[0]
        println "uuuuuuuuuuuuuuuu"
        i.properties = r[0]
        println i.ingredients
        println "uuuuuuuuuuuuuuu"

        if (!i.save(flush:true)) {
                i.errors.allErrors.each {
                    println it
                }
            }
        respond Step.findAll()
    }
    
    
    def save() {
        def r = request.JSON

        
        def i = new Step(r[0])
        
        println "****************"
        println r[0]
        println "****************"

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
