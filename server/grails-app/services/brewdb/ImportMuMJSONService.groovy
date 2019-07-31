package brewdb

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import server.Receipt
import server.Receipt
import server.Step
import server.Material
import server.Ingredient
import server.Profile
import server.Hop

@Transactional
class ImportMuMJSONService {

    def processInfusionSteps (recipe, content, stepOrder, startTime) {
        println " ***** Running Infusion"
        int i = stepOrder
        int stepStartMinute = startTime
        double totalInfusedWater = 0
        
        // Heat Water
        Material material = Material.findByType("Wasser")
        Ingredient ingredient = new Ingredient()
        Step step = new Step()
        def startStepMinute = startTime
        Profile profile = Profile.findByProfileName("REFERENCEPROFILE")
        Double startMashTemperature = (Double)content["Infusion_Einmaischtemperatur"]
        
        println "";
        println "### Start Mash Temp: "+startMashTemperature

        step.stepType = "HEAT"
        step.timeUnit = "MIN"
        step.orderNumber = i++
        step.name = "Wasser Erhitzen"
        step.startTime = stepStartMinute
        
        step.timeUnit = "MIN"   

        ingredient.material = material
        ingredient.units = "LITER"
        ingredient.measure = content["Infusion_Hauptguss"]
        ingredient.temperature = 20
        step.addToIngredients(ingredient).save(flush:true)  

        step.duration = (profile.mashTun.heatRate * (startMashTemperature-(Double)ingredient.temperature)).intValue()

        if (!recipe.addToSteps(step).save(flush:true)){
            recipe.errors.allErrors.each {
                println it
            }
        }

        // Add Malt Add first mashing step
        step = new Step()
        step.stepType = "ADD"
        step.name = "Einmaischen"
        step.startTime = stepStartMinute
        step.duration = 0
        step.timeUnit = "MIN"
        step.orderNumber = i++

        int counter = 1

        while (content["Malz"+counter] != null) {
            material = Material.findByName(content["Malz"+counter])
            println "#### "+content["Malz"+counter]
            println "---- ["+material+"]"
            if (!material) {
                material = new Material()
                material.type = "Malz"
                material.value = 0
                material.notes = ""
                material.name = content["Malz"+counter]
                if (!material.save(flush:true)) {
                    material.errors.allErrors.each {
                        println it
                    }
                }
                
            }
            counter+=1
        } 
        counter = 1
        while (content["Malz"+counter] != null) {
            println content["Malz"+counter]
            ingredient = new Ingredient()
            material = Material.findByName(content["Malz"+counter])
            println "Using material: "+material
            if (material) {
                ingredient.material = material
                ingredient.units = "GRAM"
                ingredient.measure = content["Malz"+counter+"_Menge"]
                ingredient.temperature = 20
                step.addToIngredients(ingredient).save(flush:true)  
            }
            else {
                println "ERROR:  Material ["+it.NAME+"] not found"
            }
          
            counter+=1
        }
        if (!recipe.addToSteps(step).save(flush:true)){
            recipe.errors.allErrors.each {
                println it
            }
        }

        counter =1
        while (content["Infusion_Rasttemperatur"+counter]!=null) {
            // Add Malt Add first mashing step
            step = new Step()
            step.stepType = "HEAT"
            step.name = "Erwärmen"
            step.startTime = stepStartMinute
            step.duration = 0
            step.temperature = Integer.parseInt(content["Infusion_Rasttemperatur"+counter])
            step.timeUnit = "MIN"
            step.orderNumber = i++
            if (!recipe.addToSteps(step).save(flush:true)){
                recipe.errors.allErrors.each {
                    println it
                }
            }
        }


        /* Add all fermentables to the first Step
        recipe.RECIPE.FERMENTABLES.FERMENTABLE.findAll().each {
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
        Double finalTemp;
        // Step through Mashing Steps
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
            finalTemp = Double.parseDouble(it.STEP_TEMP.text())
            println ("Mash Step "+it);
            if (it.INFUSE_AMOUNT != null) {
                ingredient.material = Material.findByType("Wasser")
                ingredient.measure = it.INFUSE_AMOUNT
                ingredient.units = "LITER"
                step.addToIngredients(ingredient).save()    
                totalInfusedWater += getDouble(it.INFUSE_AMOUNT.text())
            }
            receipt.addToSteps(step).save()   
        }

        // Lauter
        def profile = Profile.findByProfileName("REFERENCEPROFILE")
        step = new Step()
        step.name = "Läutern"
        step.stepType = "LAUTER"
        //step.temperature = Double.parseDouble(it.STEP_TEMP.text()).intValue()
        step.startTime = stepStartMinute
        Double spargeTemp=80.0

        if (getDouble(recipe.RECIPE.MASH_PROFILE.SPARGE_TEMP.text()) != null) {
            println ("got MashProfile: "+recipe.RECIPE.MASH_PROFILE.SPARGE_TEMP.text())
            spargeTemp = getDouble(recipe.RECIPE.MASH_PROFILE.SPARGE_TEMP.text())
        }
        println("**** TEMPS ****: "+finalTemp+"  "+spargeTemp)
        step.duration = ((finalTemp - spargeTemp)/profile.mashTun.heatRate).intValue()
        stepStartMinute += step.duration 
        step.timeUnit = "MIN"
        step.orderNumber = i++
        if (totalInfusedWater < getDouble(recipe.RECIPE.BOIL_SIZE.text())) {             
            material = Material.findByType("Wasser")
            ingredient = new Ingredient()
            ingredient.material = material
            ingredient.temperature = spargeTemp
            ingredient.measure = getDouble(recipe.RECIPE.BOIL_SIZE.text()) - totalInfusedWater
            ingredient.units = "LITER"
            step.addToIngredients(ingredient).save()    
        }
        receipt.addToSteps(step).save()   */
    }

    def importJSONFile(jsonFile) {
        def jsonSlurper = new JsonSlurper()
        def content = jsonSlurper.parse(jsonFile)
        println "MUM Reached"
        println content.Name
        Receipt recipe = new Receipt()
        recipe.name = content.Name
        recipe.description = content.Kurzbeschreibung +"\r\n\r\n"+content.Anmerkung_Autor
        recipe.beerType = content.Sorte
        recipe.ibu = content.Bittere
        recipe.wort = content.Stammwuerze
        recipe.color = content.Farbe
        recipe.brewer = content.Autor
        recipe.meshWater = content.Infusion_Hauptguss
        
        Tools tools = new Tools()

        println tools.gravityToPlato("1.008")

        recipe.carbonisation = content.Karbonisierung
        recipe.rinseWater = content.Nachguss
        recipe.alcohol = content.Alkohol
        if (content.Maischform == "infusion") {
            recipe.processType = "INF"
            recipe.water = Double.parseDouble(content.Nachguss) + Double.parseDouble(content.Infusion_Hauptguss)
        }
        else {
            recipe.processType = "DEK"
        }
        if (!recipe.save(flush:true)) {
            recipe.errors.allErrors.each {
                println it
            }
        }
        processInfusionSteps (recipe, content, 1, 1)
    }
}
