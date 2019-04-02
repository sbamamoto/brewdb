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
    
    def getDouble = { it?.isDouble() ? Double.parseDouble(it) : null }
    
    def processHopSteps (recipe, stepOrder, receipt, startTime) {
        Integer stepStartMinute = startTime
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
        step.duration = Double.parseDouble(recipe.RECIPE.BOIL_TIME.text()).intValue() // FIXME: add heatup time from end of lauter temperature to 100 degress
        step.timeUnit = "MIN"
        step.temperature = 100
        step.startTime = stepStartMinute
        step.orderNumber = i++

        def material = Material.findByType("Wasser")

        def ingredient = new Ingredient()
        ingredient.measure = receipt.water
        ingredient.units = "LITER"
        ingredient.material = material
        ingredient.temperature = 20
        step.addToIngredients(ingredient).save() 
         
        receipt.addToSteps(step).save() 

        // calculating hop adding times
        Integer intervalStart = step.duration
        Integer addTime = step.duration
        hopMinutes.each() {
            //intervalStart = Integer.parseInt(recipe.RECIPE.BOIL_TIME) - Integer.parseInt(it)
            addTime = Double.parseDouble(recipe.RECIPE.BOIL_TIME.text()).intValue() - Double.parseDouble(it).intValue()
            println (hopTimings.get(it).NAME + " - " + it)
            step = new Step()
            step.name = "Hopfenkochen"
            step.stepType = "ADD"
            step.duration = 0
            step.startTime = stepStartMinute+(Double.parseDouble(recipe.RECIPE.BOIL_TIME.text()).intValue()-Double.parseDouble(it)).intValue()
            step.timeUnit = "MIN"
            step.temperature = 100
            step.orderNumber = i++
            println step.name     
            hopTimings[it].each() {
                material = Material.findByName(it.NAME.text())
                ingredient = new Ingredient()
                ingredient.temperature = 20
                ingredient.measure = it.AMOUNT.text()
                ingredient.units = "KILO"
                ingredient.material = material
                step.addToIngredients(ingredient).save()  
            }
    
            receipt.addToSteps(step).save()
        }
        return hopTimings
    }

    def processInfusionSteps (recipe, stepOrder, receipt, startTime) {
        println " ***** Running Infusion"
        int i = stepOrder
        // Heat Water
        def material = Material.findByType("Wasser")
        def ingredient = new Ingredient()
        def step = new Step()
        def startStepMinute = startTime
        step.stepType = "HEAT"
        step.timeUnit = "MIN"
        step.orderNumber = i++

        ingredient.material = material
        ingredient.temperature = 20
        ingredient.measure = it.MASH_STEP.INFUSE_AMOUNT
        ingredient.units = "LITER"
        step.addToIngredients(ingredient).save()    
        step.duration = (Double.parseDouble(it.MASH_STEP.STEP_TEMP.text()).intValue() - ingredient.temperature)/profile.mashTun.heatRate
        step.temperature = Double.parseDouble(it.MASH_STEP.STEP_TEMP.text())

        stepStartMinute += step.duration
        step.name = "Heizen"
        receipt.addToSteps(step).save()
        println (" Step ")

        // Add Malt
        step = new Step()
        step.stepType = "ADD"
        step.name = "Einmaischen"
        step.startTime = stepStartMinute
        step.duration = 0
        step.timeUnit = "MIN"
        step.orderNumber = i++

        recipes.RECIPE.FERMENTABLES.FERMENTABLE.findAll().each {
            ingredient = new Ingredient()
            material = Material.findByName(it.NAME)
            println "Using material: "+material
            if (material) {
                ingredient.material = material
                ingredient.units = "KILO"
                ingredient.measure = it.AMOUNT
                ingredient.temperature = 20
                step.addToIngredients(ingredient).save()  
            }
            else {
                println "ERROR:  Material ["+it.NAME+"] not found"
            }
        }
        receipt.addToSteps(step).save()

        //Mashing
        recipe.RECIPE.MASH.MASH_STEPS.MASH_STEP.findAll().each {            
            step = new Step()
            step.stepType = "MASH"
            step.name = "Maischen"
            step.timeUnit = "MIN"
            step.startTime = stepStartMinute
            step.duration = Double.parseDouble(it.STEP_TIME.text()).intValue()
            step.orderNumber = i++
            stepStartMinute += step.duration
            step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
            if (it.INFUSE_AMOUNT != null) {
                
            }
            receipt.addToSteps(step).save()   
        }

        // Lauter
        step = new Step()
        step.name = "Läutern"
        step.stepType = "LAUTER"
        step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
        step.startTime = stepStartMinute
        step.duration = (step.temperature - ingredient.temperature)/profile.mashTun.heatRate
        stepStartMinute += step.duration 
        step.timeUnit = "MIN"
        step.orderNumber = i++
        if (getDouble(it.INFUSE_AMOUNT.text())!=null) {             
            material = Material.findByType("Wasser")
            ingredient = new Ingredient()
            ingredient.material = material
            ingredient.temperature = getDouble(it.SPARGE_TEMP.text())
            ingredient.measure = getDouble(recipes.RECIPE.BOIL_SIZE.text()) - getDouble(it.INFUSE_AMOUNT.text())
            ingredient.units = "LITER"
            step.addToIngredients(ingredient).save()    
        }
        receipt.addToSteps(step).save()   
    }


    def save () {
        println request
        def profile = Profile.findByProfileName("REFERENCEPROFILE")
        
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
                    material.notes = it.NOTES
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
                    def hop = new Hop()
                    hop.type = it.TYPE
                    hop.name = it.NAME
                    hop.notes = it.NOTES
                    hop.alpha = getDouble(it.ALPHA.text())
                    hop.beta = getDouble(it.BETA.text())
                    hop.hsi = getDouble(it.HSI.text())
                    hop.origin = it.ORIGIN 
                    hop.humulene = getDouble(it.HUMULENE.text())
                    hop.caryophylene = getDouble(it.CARYOPHYLENE.text())
                    hop.mycrene = getDouble(it.MYCRENE.text())
                    hop.form = it.FORM
                    if (!hop.save(flush:true)) {
                        hop.errors.allErrors.each {
                            println "* HOPSAVE * " + it
                        }
                    }

                    material = new Material()
                    material.type = "Hopfen"
                    material.name = it.NAME
                    material.value = it.ALPHA
                    material.notes = it.NOTES
                    if (!material.save(flush:true)) {
                        material.errors.allErrors.each {
                            println "* HOPMATERIALSAVE *" + it
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
                            println "* YEASTMATERIALSAVE *" + it
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
            receipt.ibu = getDouble(recipes.RECIPE.IBU.text())
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
            Integer stepStartMinute = 0

            recipes.RECIPE.MASH.MASH_STEPS.MASH_STEP.findAll().each {
                def step = new Step()
                step.name = it.NAME
                step.stepType = it.TYPE
                step.startTime = stepStartMinute
                step.timeUnit = "MIN"
                step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
                step.orderNumber = i++
                println " *** Step Type: "+ it.TYPE
                if (it.TYPE) {                  // check for mashing type
                    switch (it.TYPE) {
                        case "Infusion": 
                            receipt.processType = "INF"
                            break      
                        
                        case "Temperature": 
                            step.stepType = "ADD"
                            receipt.processType = "TEMP"
                            break
                        
                        case "Dekoktion": 
                            step.setpTyp = "DEKOK"
                            break
                        
                        default:
                            break
                    }

                    if (it.TYPE == "Temperature") {
                        // Set up mash
                        def material = Material.findByType("Wasser")
                        def ingredient = new Ingredient()
                        ingredient.material = material
                        ingredient.measure = receipt.water
                        ingredient.temperature = 20
                        ingredient.units = "LITER"
                        step.addToIngredients(ingredient).save()    
                        step.duration = (Double.parseDouble(it.STEP_TEMP.text()).intValue() - ingredient.temperature)/profile.mashTun.heatRate

                        recipes.RECIPE.FERMENTABLES.FERMENTABLE.findAll().each {
                            ingredient = new Ingredient()
                            material = Material.findByName(it.NAME)
                            println material
                            if (material) {
                                ingredient.temperature = 20
                                ingredient.material = material
                                ingredient.units = "KILO"
                                ingredient.measure = it.AMOUNT
                                step.addToIngredients(ingredient).save()  
                            }
                            else {
                                println "ERROR:  Material ["+it.NAME+"] not found"
                            }
                        }
                        step.name = "Ansetzen"
                        step.temperature = 20
                        receipt.addToSteps(step).save()
                        // Heat up Mash
                        step = new Step()
                        step.name = "Heizen"                            //FIXME: i18n support
                        step.stepType = "HEAT"  
                        step.startTime = stepStartMinute
                        step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
                        step.duration = (step.temperature - ingredient.temperature)/profile.mashTun.heatRate
                        step.timeUnit = "MIN" 
                        
                        step.orderNumber = i++
                        println step.name          
                        receipt.addToSteps(step).save()              
                        // Wait mashing time 
                        step = new Step()
                        step.name = "Maischen"
                        step.stepType = "WAIT"
                        step.startTime = stepStartMinute
                        step.duration = Double.parseDouble(it.STEP_TIME.text()).intValue()
                        stepStartMinute += step.duration 
                        step.timeUnit = "MIN"
                        step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
                        step.orderNumber = i++
                        println step.name
                        // Lauter
                        receipt.addToSteps(step).save()              
                        step = new Step()
                        step.name = "Läutern"
                        step.stepType = "LAUTER"
                        step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
                        step.startTime = stepStartMinute
                        step.duration = (step.temperature - ingredient.temperature)/profile.mashTun.heatRate
                        stepStartMinute += step.duration 
                        step.timeUnit = "MIN"
                        
                        step.orderNumber = i++
                        println step.name
                    }
                    println " beforer receipt save"
                    receipt.addToSteps(step).save()
                    println " ############## step added"
                }
                
            }

            println "receipt type: "+receipt.processType
            if (receipt.processType == "INF") {
                processInfusionSteps(recipes, i, receipt, stepStartMinute)
            }
            println " before hop stepping"
            processHopSteps(recipes, i, receipt, stepStartMinute)
            println "s## " + receipt.steps[0].name
            println "i## " + receipt.steps[0].ingredients[0]
            println "m## " + receipt.steps[0].ingredients[0].material
            if (!receipt.save(flush:true)) {
                receipt.errors.allErrors.each {
                    println it
                }
            }
        }
        redirect(url: "http://localhost:4200")
    }
} 