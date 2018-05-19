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

        def i = new Ingredient(r[0])
        
        def s = Source.findById(r[1])
        i.source = s

        if (!i.save(flush:true)) {
            i.errors.allErrors.each {
                println it
            }
        }
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
        println ("update ---- "+r)
        def i = Ingredient.findById(params['id'])
        def s = Source.findById(r[1])
        println(s.name)
        i.properties = r[0]
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
