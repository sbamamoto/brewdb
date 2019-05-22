package brewdb

class Tools {

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

}