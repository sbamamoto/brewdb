package brewdb


import grails.rest.*
import grails.converters.*

class MaterialController {
    static responseFormats = ['json', 'xml']
	
    def index() { 
        def materials = Material.findAll()
        return [materialList:materials]
    }
}
