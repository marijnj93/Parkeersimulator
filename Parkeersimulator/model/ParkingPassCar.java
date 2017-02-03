package Parkeersimulator.model;

import java.awt.*;

/**
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;

    /**
     * Set to false that this car has to pay.
     */
    ParkingPassCar() {
        this.setHasToPay(false);
    }

    /**
     *
     * @return COLOR, the color for a ParkingPassCar car.
     */
    public Color getColor(){
    	return COLOR;
    }
}
