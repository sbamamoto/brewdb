package brewdb


import grails.rest.*
import grails.converters.*
import server.Storage
import server.Material

class StorageController {
	static responseFormats = ['json', 'xml']
	
    def show() {         
        def types = Material.executeQuery ('select distinct type from Material');

        def responseData = [
            'types': types
           ]
        
        render responseData as JSON
    }
}