package Parkeersimulator.model;

import java.awt.*;
import java.util.Random;

/**
 *
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
     * Constructor for objects of class Car
     */
    public Car() {
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        stayTime = stayMinutes;
    }

    /**
     *
     * @return location, The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @param location, The location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return minutesLeft.
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     *
     * @param minutesLeft
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     *
     * @return isPaying
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     *
     * @param isPaying
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     *
     * @return hasToPay
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     *
     * @param hasToPay
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    //Returns the total time the car was parked
    public int getStayTime() {
        return stayTime;
    }

    public void tick() {
        minutesLeft--;
    }

    public abstract Color getColor();
}