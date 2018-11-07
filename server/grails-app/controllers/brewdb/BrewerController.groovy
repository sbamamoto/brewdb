package brewdb

import server.Brewer
import grails.rest.*
import grails.converters.*

class BrewerController {
    static responseFormats = ['json', 'xml']

    def show() { 
        def brewers = Brewer.findAll()
        return [brewerList:brewers]
    }
    def index() { 
        def brewers = Brewer.findAll()
        return [brewerList:brewers]
    }
}
