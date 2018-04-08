package brewdb

import server.Ingredient
import server.Source

import grails.rest.*
import grails.converters.*

class IngredientController {
    static responseFormats = ['json', 'xml']
	
    def index() { 
        println ("TEST the East...")
        def ingredients = Ingredient.findAll()
        return [ingredientList:ingredients]
    }
    
    def save() {
        def r = request.JSON
        def i = new Ingredient(r)
        println (i.type)
        println ("TEST the West...")
        i.save(flush:true)
        respond Ingredient.findAll()
    }
    
    def delete() {
        def i = Ingredient.findById(params['id'])
        println(i.description)
        i.delete(flush:true)
        respond Ingredient.findAll()
    }

    def update() {
        def r = request.JSON
        def i = Ingredient.findById(params['id'])
        def s = r.source
        s = Source.findById(r.source)
        println(s.name)
        i.properties = r
        i.source = s
        i.save(flush:true)
        respond Ingredient.findAll()
    }
    
    def show() {
        def i
        if (params['id']!='-1') { 
            i = Ingredient.findById(params['id'])
        }
        else {
            i = new Ingredient();
        }
       
        def s = Source.findAll()
        
        def responseData = [
            'sources': s,
            'ingredient': i
        ]
        
        render responseData as JSON
    }
    
}
