package brewdb


import grails.rest.*
import grails.converters.*
import server.Receipt
import server.Step
import server.Material
import server.Ingredient

class BeerXmlController {
    static responseFormats = ['json', 'xml']

    def gravityToPlato (String strGravity) {
        Double gravity = Double.parseDouble(strGravity.replaceAll("[^0-9.]", ""))
        Double normGrav
        if (gravity > 1.5 && gravity < 30) {   // value is already in degrees Plato
            return gravity
        }
        if (gravity > 1000) {                  // value is in 1000 notatin 1048 means 1.048
            normGrav = gravity / 1000.0
        } 
        else if (gravity < 1.5) {               // gravity is OK for Plato degree calculation
            normGrav = gravity
        }
        Double plato = (-1 * 616.868) + (1111.14 * normGrav) - (630.272 * normGrav**2) + (135.997 * normGrav**3)
        plato = plato.round(1)
        return plato
    }

    def processHopSteps (recipe, stepOrder, receipt) {
        def hopTimings = [:]
        def i = stepOrder
        recipe.RECIPE.HOPS.HOP.findAll().each {
            println (it.USE + "  " + it.TIME + " " + it.NAME)
            if (it.USE == "Boil") {
                if (!hopTimings.get(it.TIME.text())) {
                    hopTimings.put(it.TIME.text(),[])
                }
                hopTimings.get(it.TIME.text())<<it
            }
        }

        def hopMinutes = hopTimings.sort()*.key.reverse()
        // Start cooking
        def step = new Step()
        step.name = "Hopfenkochen"
        step.stepType = "COOK"
        step.duration = recipe.RECIPE.BOIL_TIME.text()
        step.timeUnit = "MIN"
        step.temperature = "100"
        step.orderNumber = i++

        def material = Material.findByType("Wasser")
        def ingredient = new Ingredient()
        ingredient.measure = receipt.water
        ingredient.units = "LITER"
        ingredient.material = material
        step.addToIngredients(ingredient).save() 
        println step.name          
        receipt.addToSteps(step).save() 

        // calculating hop adding times
        Integer intervalStart = Integer.parseInt(step.duration)
        Integer addTime = Integer.parseInt(step.duration)
        hopMinutes.each() {
            //intervalStart = Integer.parseInt(recipe.RECIPE.BOIL_TIME) - Integer.parseInt(it)
            addTime = Integer.parseInt(recipe.RECIPE.BOIL_TIME.text()) - Integer.parseInt(it)
            println (hopTimings.get(it).NAME + " - " + it)
            step = new Step()
            step.name = "Hopfenkochen"
            step.stepType = "ADD"
            step.duration = ""
            step.timeUnit = "MIN"
            step.temperature = "100"
            step.orderNumber = i++
            println step.name     
            hopTimings[it].each() {
                material = Material.findByName(it.NAME.text())
                ingredient = new Ingredient()
                ingredient.measure = it.AMOUNT.text()
                ingredient.units = "GRAMM"
                ingredient.material = material
                step.addToIngredients(ingredient).save()  
            }
    
            receipt.addToSteps(step).save()
        }
        return hopTimings
    }

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
                if (!material) {
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
                if (!material) {
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
            receipt.beerType = recipes.RECIPE.STYLE.NAME
            receipt.reinheitsGebot = false
            receipt.wort = gravityToPlato(recipes.RECIPE.OG.text())
            receipt.brewer = recipes.RECIPE.BREWER
            receipt.alcohol = "0"
            receipt.color = "0"
            receipt.ibu = "0"
            receipt.carbonisation = recipes.RECIPE.CARBONATION
            receipt.water = recipes.RECIPE.BOIL_SIZE
            receipt.finalGravity = gravityToPlato(recipes.RECIPE.FG.text())
            
            recipes.RECIPE.WATERS.WATER.findAll().each {
                if (it.NAME == "Hauptguss") {
                    receipt.meshWater = it.AMOUNT
                }
                else if (it.NAME == "Nachguss") {
                    receipt.rinseWater = it.AMOUNT
                }
                else {
                    println ("#### ERROR unknown type of water: "+it.NAME)
                }
            }

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
                println step.name
                if (it.MASH_STEP.TYPE) {                  // check for mashing type
                    switch (it.MASH_STEP.TYPE) {
                        case "Infusion": 
                            step.stepType ="MASH" 
                            receipt.processType = "INF"
                            break      
                        
                        case "Temperature": 
                            step.stepType = "ADD"
                            receipt.processType = "INF"
                            break
                        
                        case "Dekoktion": 
                            step.setpTyp = "DEKOK"
                            break
                        
                        default:
                            break
                    }
                    if (it.MASH_STEP.TYPE == "Infusion") {     // we look for infusion
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
                    else if (it.MASH_STEP.TYPE == "Temperature") {
                        // Set up mash
                        def material = Material.findByType("Wasser")
                        def ingredient = new Ingredient()
                        ingredient.material = material
                        ingredient.measure = receipt.water
                        ingredient.units = "LITER"
                        step.addToIngredients(ingredient).save()    
                        step.duration = ""                
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
                        step.name = "Ansetzen"
                        step.temperature = ""
                        receipt.addToSteps(step).save()
                        // Heat up Mash
                        step = new Step()
                        step.name = "Heizen"
                        step.stepType = "HEAT"
                        step.duration = ""
                        step.timeUnit = "MIN"
                        step.temperature = it.MASH_STEP.STEP_TEMP
                        step.orderNumber = i++
                        println step.name          
                        receipt.addToSteps(step).save()              
                        // Wait mashing time 
                        step = new Step()
                        step.name = "Maischen"
                        step.stepType = "WAIT"
                        step.duration = it.MASH_STEP.STEP_TIME
                        step.timeUnit = "MIN"
                        step.temperature = it.MASH_STEP.STEP_TEMP
                        step.orderNumber = i++
                        println step.name
                        // Lauter
                        receipt.addToSteps(step).save()              
                        step = new Step()
                        step.name = "Läutern"
                        step.stepType = "LAUTER"
                        step.duration = ""
                        step.timeUnit = "MIN"
                        step.temperature = it.MASH_STEP.STEP_TEMP
                        step.orderNumber = i++
                        println step.name
                    }
                }
                processHopSteps(recipes, i, receipt)
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