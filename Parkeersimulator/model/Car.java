package Parkeersimulator.model;

import java.awt.*;
import java.util.Random;

/**
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public abstract class Car {
    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int stayTime;

    /**
     * Constructor of class Car.
     * Create a random staytime.
     */
    Car() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        stayTime = stayMinutes;
    }

    /**
     * Get the location of the car.
     * @return location, The location of the car.
     */
    Location getLocation() {
        return location;
    }

    /**
     * Set the location of the car.
     * @param location, The location to place the car.
     */
    void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get the remaining minutes before the car leaves.
     * @return minutesLeft, Amount of minutes before a car leaves.
     */
    int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Set the remaining minutes before the car leaves.
     * @param minutesLeft, Amount of minutes before a car leaves.
     */
    void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * Check if a car is paying.
     * @return isPaying, Used for paying cars.
     */
    boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Set that a car is paying.
     * @param isPaying, Used for paying cars.
     */
    void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Check if a car has to pay.
     * @return hasToPay, Used for checking if a car needs to pay.
     */
    boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Set that a car has to pay or does not have to pay.
     * @param hasToPay, Used for checking if a car needs to pay.
     */
    void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Get the total time a car has parked.
     * @return stayTime, The total time a car has parked.
     */
    int getStayTime() {
        return stayTime;
    }

    /**
     * Decrease the time a car can stay by 1 minute.
     */
    public void tick() {
        minutesLeft--;
    }

    public abstract Color getColor();
}