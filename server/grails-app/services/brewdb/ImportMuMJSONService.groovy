package brewdb

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper
import server.Receipt
import server.Receipt
import server.Step
import server.Other
import server.Ingredient
import server.Profile
import server.Hop
import server.MaltIngredient
import server.Malt

@Transactional
class ImportMuMJSONService {

    def processInfusionSteps (recipe, content, stepOrder, startTime) {
        println " ***** Running Infusion"
        int i = stepOrder
        int stepStartMinute = startTime
        double totalInfusedWater = 0
        
        // Heat Water
        Other material = Other.findByType("Wasser")
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
            Malt malt = Malt.findByName(content["Malz"+counter])
            println "#### "+content["Malz"+counter]
            println "---- ["+malt+"]"
            if (!malt) {
                malt = new Malt()
                malt.name = content["Malz"+counter]
                if (!malt.save(flush:true)) {
                    malt.errors.allErrors.each {
                        println it
                    }
                }
            }
            counter+=1
        } 
        counter = 1
        
        while (content["Malz"+counter] != null) {
            println content["Malz"+counter]
            MaltIngredient maltIngredient = new MaltIngredient()
            maltIngredient = Malt.findByName(content["Malz"+counter])
            println "Using material: "+malt
            if (malt) {
                maltIngredient.material = material
                maltIngredient.units = "GRAM"
                maltIngredient.measure = content["Malz"+counter+"_Menge"]
                maltIngredient.temperature = 20
                step.addToIngredients(maltIngredient).save(flush:true)  
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
        int lastTemperature = Integer.parseInt(content["Infusion_Rasttemperatur"+counter])
        while (content["Infusion_Rasttemperatur"+counter]!=null) {
            // Add Malt Add mashing step - Heat to target temperature
            step = new Step()
            step.stepType = "HEAT"
            step.name = "Erwärmen"
            step.startTime = stepStartMinute
            step.temperature = Integer.parseInt(content["Infusion_Rasttemperatur"+counter])
            step.duration = (((Double)(step.temperature - lastTemperature)) * profile.mashTun.heatRate).intValue()
            step.timeUnit = "MIN"
            step.orderNumber = i++
            if (!recipe.addToSteps(step).save(flush:true)){
                recipe.errors.allErrors.each {
                    println it
                }
            }
            step = new Step()
            step.stepType = "WAIT"
            step.name = "Maischen"
            step.startTime = stepStartMinute
            step.duration = Integer.parseInt(content["Infusion_Rastzeit"+counter])
            step.temperature = Integer.parseInt(content["Infusion_Rasttemperatur"+counter])
            step.timeUnit = "MIN"
            step.orderNumber = i++
            counter +=1
            if (!recipe.addToSteps(step).save(flush:true)){
                recipe.errors.allErrors.each {
                    println it
                }
            }
        }

        // Heat up sparge water
        step = new Step()
        step.stepType = "HEAT"
        step.timeUnit = "MIN"
        step.orderNumber = i++
        step.name = "Nachguss Erhitzen"
        step.startTime = stepStartMinute
        step.parallel = true
        step.timeUnit = "MIN"   

        ingredient.material = material
        ingredient.units = "LITER"
        ingredient.measure = content["Nachguss"]
        ingredient.temperature = 20
        step.addToIngredients(ingredient).save(flush:true)  

        step.duration = (profile.mashTun.heatRate * (startMashTemperature-(Double)ingredient.temperature)).intValue()

        if (!recipe.addToSteps(step).save(flush:true)){
            recipe.errors.allErrors.each {
                println it
            }
        }

        step = new Step()
        step.name = "Läutern"
        step.stepType = "LAUTER"
        
        step.startTime = stepStartMinute
        step.duration = 0
        stepStartMinute += step.duration 
        step.timeUnit = "MIN"
        step.orderNumber = i++
        if (!recipe.addToSteps(step).save(flush:true)){
            recipe.errors.allErrors.each {
                println it
            }
        }


        // VWH Hop check
        step = new Step()
        step.stepType = "ADD"
        step.name = "Einmaischen"
        step.startTime = stepStartMinute
        step.duration = 0
        step.timeUnit = "MIN"
        step.orderNumber = i++

        counter = 1
        Hop hop

        while (content["Hopfen_VWH_"+counter+"_Sorte"] != null) {
            hop = Hop.findByName(content["Hopfen_VWH_"+counter+"_Sorte"])
            println "#### "+content["Hopfen_VWH_"+counter+"_Sorte"]
            println "---- ["+hop+"]"
            if (!hop) {
                hop = new Hop()
                hop.name = content["Hopfen_VWH_"+counter+"_Sorte"]
                hop.alpha = Double.parseDouble(content["Hopfen_VWH_"+counter+"_alpha"])
                material.name = content["Hopfen_VWH_"+counter+"_Sorte"]
                if (!material.save(flush:true)) {
                    material.errors.allErrors.each {
                        println it
                    }
                }
                
            }
            counter+=1
        } 
        counter = 1
        
        while (content["Hopfen_VWH_"+counter+"_Sorte"] != null) {
            println content["Hopfen_VWH_"+counter+"_Sorte"]
            ingredient = new Ingredient()
            material = Material.findByName(content["Hopfen_VWH_"+counter+"_Sorte"])
            println "Using material: "+material
            if (material) {
                ingredient.material = material
                ingredient.units = "GRAM"
                ingredient.measure = content["Hopfen_VWH_"+counter+"_Menge"]
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

        // Hop cooking
        step = new Step()
        step.stepType = "HEAT"
        step.timeUnit = "MIN"
        step.orderNumber = i++
        step.name = "Erhitzen bis zum Kochen"
        step.startTime = stepStartMinute
        step.parallel = false
        step.timeUnit = "MIN"   
        counter = 1
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
