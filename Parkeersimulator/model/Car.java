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
     * Constructor for objects of class Car.
     * Create a random staytime.
     */
    Car() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        stayTime = stayMinutes;
    }

    /**
     * @return location, The location of the car.
     */
    Location getLocation() {
        return location;
    }

    /**
     * @param location, The location to place the car.
     */
    void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return minutesLeft, Amount of minutes before a car leaves.
     */
    int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * @param minutesLeft, Amount of minutes before a car leaves.
     */
    void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * @return isPaying, Used for paying cars.
     */
    boolean getIsPaying() {
        return isPaying;
    }

    /**
     * @param isPaying, Used for paying cars.
     */
    void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * @return hasToPay, Used for checking if a car needs to pay.
     */
    boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * @param hasToPay, Used for checking if a car needs to pay.
     */
    void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     *
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