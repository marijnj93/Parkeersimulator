package Parkeersimulator.model;

import java.awt.*;
import java.util.Random;

/**
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class ReservedCar extends Car
{
    private int arrivingtime;
    private int waitTime;
    private Color color = Color.green;
    public boolean arrived = false;


    public ReservedCar(int time) {
        super();
        arrivingtime = time;
        this.setHasToPay(false);
        Random random = new Random();
        waitTime = random.nextInt(10) + 5;
    }
    public Color getColor() {
        return color;
    }
    @Override
    public void tick() {
        setMinutesLeft(getMinutesLeft() - 1);
        waitTime--;
        if (checkIfArrived()) {
            arrived = true;
            color = Color.orange;
        }
    }
    public boolean checkIfArrived() {
        if (waitTime < 1) {
            return true;
        }
        return false;
    }
}
