package brewdb


import grails.rest.*
import grails.converters.*
import server.Step
import server.Receipt

class StepListController {
	static responseFormats = ['json', 'xml']
	
    def show() {
        def r
        if (params['id']!='-1') { 
            r = Receipt.findById(params['id'])
        }
                
        def responseData = [
            'receiptId': params['id'],
            'receiptName': r.name,
            'steps': r.steps
        ]
        
        render responseData as JSON
    }
}