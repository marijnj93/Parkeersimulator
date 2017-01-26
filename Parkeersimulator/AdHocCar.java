package Parkeersimulator;

import java.util.Random;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
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
