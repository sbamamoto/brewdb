package brewdb

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

@Transactional
class ImportMuMJSONService {

    def importJSONFile(jsonFile) {
        def jsonSlurper = new JsonSlurper()
        def content = jsonSlurper.parse(jsonFile)
        println "MUM Reached"
        println content.Name
    }
}
