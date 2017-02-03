package Parkeersimulator.model;

import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;
	
    public ParkingPassCar() {
        this.setHasToPay(false);
    }

    /**
     *
     * @return COLOR
     */
    public Color getColor(){
    	return COLOR;
    }
}
