package Parkeersimulator.model;

import java.awt.*;
import java.util.Random;

/**
 * A class for cars that have reserved a place.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class ReservedCar extends Car
{

    private int waitTime;
    private Color color = Color.green;
    private boolean arrived = false;

    /**
     * Constructor for a ReservedCar.
     * @param time, The time a car has reserved a place.
     */
    ReservedCar(int time) {
        super();
        this.setHasToPay(true);
        Random random = new Random();
        waitTime = random.nextInt(10) + 5;
    }

    /**
     * Get the color of a ReservedCar
     * @return color, The color of a ReservedCar.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Run the actions of a ReservedCar.
     */
    @Override
    public void tick() {
        setMinutesLeft(getMinutesLeft() - 1);
        waitTime--;
        if (checkIfArrived()) {
            arrived = true;
            color = Color.orange;
        }
    }

    /**
     * Check if a ReservedCar has arrived at his reserved place.
     * @return true, if a ReservedCar has arrived at his reserved place.
     */
    private boolean checkIfArrived() {
        return waitTime < 1;
    }
}
