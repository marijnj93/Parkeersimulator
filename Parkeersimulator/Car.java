package Parkeersimulator;

import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;

    /**
     * Constructor for objects of class Car
     */
    public Car() {
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

    public void tick() {
        minutesLeft--;
    }

    public abstract Color getColor();
}