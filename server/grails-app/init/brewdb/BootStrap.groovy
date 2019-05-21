package brewdb

import server.*

class BootStrap {
  
    def init = { servletContext ->
        def profile = Profile.findByProfileName("REFERENCEPROFILE")
        if (profile==null) {
            def hk = new HopKettle (name:"REFHOPKETTLE", 
                product:"REFERENCE", 
                volume:30, 
                description:"Internal reference system", 
                heatRate: 10.0).save()
            def mt = new MashTun (name: "REFMASHTUN", 
                product:"REFERENCE",
                volume:30, 
                description:"Internal reference system", 
                heatRate: 10.0).save()
            def lt = new LauterTun (name: "REFMASHTUN", 
                product:"REFERENCE",
                volume:30, 
                description:"Internal reference system",
                filteringSpeed:10
                ).save()
            new Profile (
                profileName:"REFERENCEPROFILE",
                mashTun: mt,
                lauterTun: lt,
                hopKettle: hk
            ).save()
        }
        def material = Material.findByType("Wasser")
        if (material==null) {
            new Material (
                type: "Wasser",
                name: "Wasser",
                value: "0"
            ).save()
        }
        def brewer = Brewer.findByUserId("admin")
        if (brewer == null) {
            new Brewer(
                firstname: "admin",
                lastname: "admin",
                userId: "admin",
                password: "admin".encodeAsSHA256(),                
            ).save()
        }
    }
    def destroy = {
    }
}
