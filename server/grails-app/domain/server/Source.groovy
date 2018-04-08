package server

import grails.rest.Resource

@Resource(uri='/source')
class Source {
    String name
    String description
    String url
    String rating
    
    static constraints = {
    }
    
    String toString() {
        name
    }
    
}
