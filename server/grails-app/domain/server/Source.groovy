package server

import grails.rest.Resource

@Resource(uri='/source')
class Source {
    String name
    String description
    String url
    String rating
    
    static hasMany = [
        malts:Malt,
        hops:Hop,
        yeasts:Yeast
    ]

    static constraints = {
    }
    
    String toString() {
        name
    }
    
}
