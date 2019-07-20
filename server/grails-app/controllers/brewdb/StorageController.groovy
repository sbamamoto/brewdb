package brewdb


import grails.rest.*
import grails.converters.*
import server.Storage
import server.Material

class StorageController {
	static responseFormats = ['json', 'xml']
	
    def index() {
        def materials = Storage.list();

        def responseData = [
            'materials': materials
           ]
        
        render responseData as JSON
    }

    def show() {         
        def types = Material.executeQuery ('select distinct type from Material');
        def materials = Material.list();
        def storageIten = new Storage()
        def responseData = [
            'types': types,
            'materials': materials,
            'storage': storageIten
           ]
        println ("SHOOOOOW")
        render responseData as JSON
    }
}