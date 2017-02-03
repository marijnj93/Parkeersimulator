package Parkeersimulator.model;

import java.awt.*;

/**
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;

    /**
     * Set to true that this car has to pay.
     */
    AdHocCar() {
        this.setHasToPay(true);
    }

    /**
     * Get the color of a AdHocCar.
     * @return COLOR, the color for an AdHocCar car.
     */
    public Color getColor(){
    	return COLOR;
    }
}
