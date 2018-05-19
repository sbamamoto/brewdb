package brewdb


import grails.rest.*
import grails.converters.*
import server.Material
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
       
        def t = MaterialType.values()
        
        def responseData = [
            'types': t,
            'material': m
        ]
        
        render responseData as JSON
    }
}
