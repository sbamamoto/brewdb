package brewdb


import grails.rest.*
import grails.converters.*
import server.Receipt
import server.Step
import server.Material
import server.Ingredient
import server.Profile
import server.Hop

class BeerXmlController {
    static responseFormats = ['json', 'xml']

    ImportBeerXMLService importBeerXMLService
    ImportMuMJSONService importMuMJSONService

    def save () {
        println request
        def file = request.getFile("beerxml")
        if(file && !file.empty){
            def newFile = File.createTempFile('grails', 'beerxml')
            file.transferTo(newFile)
            String content = newFile.text
            if (content.trim().startsWith("<?xml")) {
                importBeerXMLService.importXMLFile(newFile)
            }
            else if (content.trim().startsWith("{")) {
                importMuMJSONService.importJSONFile(newFile)
            } 
        }
        redirect(url: "http://localhost:4200")
    }
} 