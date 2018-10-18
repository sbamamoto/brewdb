package brewdb


import grails.rest.*
import grails.converters.*
import server.Receipt
import server.Step
import server.Material
import server.Ingredient

class BeerXmlController {
    static responseFormats = ['json', 'xml']

    def save () {
        println request
        def file = request.getFile("beerxml")
        if(file && !file.empty){
            def newFile = File.createTempFile('grails', 'beerxml')
            file.transferTo(newFile)
            def recipes = new XmlSlurper().parseText(newFile.text)

            recipes.RECIPE.FERMENTABLES.FERMENTABLE.findAll().each {
                def material = Material.findByName(it.NAME)
                println "#### "+it.NAME
                println "---- ["+material+"]"
                if (material) {
                    println "Schon da: "+material.name
                }
                else {
                    material = new Material()
                    material.type = "Malz"
                    material.name = it.NAME
                    material.value = it.COLOR
                    if (!material.save(flush:true)) {
                        material.errors.allErrors.each {
                            println it
                        }
                    }
                }                
            }

            recipes.RECIPE.HOPS.HOP.findAll().each {
                def material = Material.findByName(it.NAME)
                println "#### "+it.NAME
                println "---- ["+material+"]"
                if (material) {
                    println "Schon da: "+material.name
                }
                else {
                    material = new Material()
                    material.type = "Hopfen"
                    material.name = it.NAME
                    material.value = it.ALPHA
                    if (!material.save(flush:true)) {
                        material.errors.allErrors.each {
                            println it
                        }
                    }
                }                
            }

            recipes.RECIPE.YEASTS.YEAST.findAll().each {
                def material = Material.findByName(it.NAME)
                println "#### "+it.NAME
                println "---- ["+material+"]"
                if (material) {
                    println "Schon da: "+material.name
                }
                else {
                    material = new Material()
                    material.type = "Hefe"
                    material.name = it.NAME
                    material.value = it.MIN_TEMPERATURE
                    if (!material.save(flush:true)) {
                        material.errors.allErrors.each {
                            println it
                        }
                    }
                }                
            }


            println ("+++++ "+recipes.RECIPE.NAME)
            def receipt = new Receipt()
            receipt.name = recipes.RECIPE.NAME
            receipt.description = recipes.RECIPE.NOTES
            receipt.beerType = recipes.RECIPE.STYLE
            receipt.reinheitsGebot = false
            receipt.wort = recipes.RECIPE.OG
            receipt.brewer = recipes.RECIPE.BREWER
            receipt.alcohol = "0"
            receipt.color = "0"
            receipt.ibu = "0"
            receipt.processType = "unbekannt"
            if (!receipt.save(flush:true)) {
                receipt.errors.allErrors.each {
                    println it
                }
            }
            println receipt.id
            int i = 0
            recipes.RECIPE.MASH.MASH_STEPS.findAll().each {
                def step = new Step()
                step.name = it.MASH_STEP.NAME
                step.stepType = it.MASH_STEP.TYPE
                step.duration = it.MASH_STEP.STEP_TIME
                step.timeUnit = "MIN"
                step.temperature = it.MASH_STEP.STEP_TEMP
                step.orderNumber = i++
                if (it.MASH_STEP.TYPE) {
                    if (it.MASH_STEP.TYPE == "Infusion") {
                        receipt.processType = "INF"
                        def material = Material.findByType("Wasser")
                        def ingredient = new Ingredient()
                        ingredient.material = material
                        step.stepType = "MASH"
                        ingredient.measure = it.MASH_STEP.INFUSE_AMOUNT
                        ingredient.units = "LITER"
                        step.addToIngredients(ingredient).save()                    
                        recipes.RECIPE.FERMENTABLES.FERMENTABLE.findAll().each {
                            ingredient = new Ingredient()
                            material = Material.findByName(it.NAME)
                            println material
                            if (material) {
                                ingredient.material = material
                                ingredient.units = "GRAM"
                                ingredient.measure = it.AMOUNT
                                step.addToIngredients(ingredient).save()  
                            }
                            else {
                                println "ERROR:  Material ["+it.NAME+"] not found"
                            }
                        }
                    }
                }
                receipt.addToSteps(step).save()
            }
            if (!receipt.save(flush:true)) {
                receipt.errors.allErrors.each {
                    println it
                }
            }

        }
        redirect(url: "http://localhost:4200")
    }
} 