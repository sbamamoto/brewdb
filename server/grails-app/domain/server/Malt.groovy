package server

class Malt {
    String name
    Double water                                //in percent
    Double pH 
    Double color
    Double colorAfterCooking
    Double dms
    Double extract
    Double extractDiff
    Integer saccharificationTime1
    Integer saccharificationTime2
    Integer endFermentation                          //FIXME: reference to other hops in db
    Double vZ45
    Double nitrogen
    Integer solubleNitrogen
    Double kolbachIndex
    Double aminoNitrogen
    Double friability
    Double viscosity
    Double betaGlucan

    static hasMany = [sources:Source] 
 
    static constraints = {
        water nullable:true
        pH nullable:true
        color nullable:true
        colorAfterCooking nullable:true
        dms nullable:true
        extract nullable:true
        extractDiff nullable:true
        saccharificationTime1 nullable:true
        saccharificationTime1 nullable:true
        endFermentation nullable:true
        vZ45 nullable:true
        nitrogen nullable:true
        solubleNitrogen nullable:true
        kolbachIndex nullable:true
        aminoNitrogen nullable:true
        friability nullable:true
        viscosity nullable:true
        betaGlucan nullable:true
    }
}