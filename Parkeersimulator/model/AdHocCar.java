package Parkeersimulator.model;

import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar() {

        this.setHasToPay(true);
    }

    /**
     *
     * @return COLOR
     */
    public Color getColor(){
    	return COLOR;
    }
}
