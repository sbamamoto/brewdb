package brewdb


import grails.rest.*
import grails.converters.*
import server.Storage
import server.Receipt

class StorageController {
	static responseFormats = ['json', 'xml']
	
    def show() {
        def r
        def s
        println ("["+params['up']+"]")
        if (params['up']!=null) {
            s = Step.findById(params['up'])
            if (s.orderNumber == null) {
                s.orderNumber = 0
            } 
            else {
                s.orderNumber = s.orderNumber + 1
            }
            if (!s.save(flush:true)) {
                s.errors.allErrors.each {
                    println it
                }
            }
            println("++++++* "+s.orderNumber)
        }

        if (params['down']!=null) {
            s = Step.findById(params['down'])
            if (s.orderNumber == null) {
                s.orderNumber = 0
            } 
            else if (s.orderNumber > 0) {
                s.orderNumber = s.orderNumber - 1
            }
            if (!s.save(flush:true)) {
                s.errors.allErrors.each {
                    println it
                }
            }
            println("++++++* "+s.orderNumber)
        }
        

        if (params['id']!='-1') { 
            r = Receipt.findById(params['id'])
        }
                
        def responseData = [
            'receiptId': params['id'],
            'receiptName': r.name,
            'steps': r.steps.sort { it.orderNumber }
        ]
        
        render responseData as JSON
    }
}