package Parkeersimulator.model;

import Parkeersimulator.view.*;
import Parkeersimulator.model.Car;
import Parkeersimulator.model.Location;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class SimulatorView {
    private View view;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private Car[][][] cars;
    private Simulator simulator;

    /**
     *
     * @param numberOfFloors, The number of floors.
     * @param numberOfRows, the number of rows.
     * @param numberOfPlaces, the number of places.
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        this.simulator = simulator;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    }
    public void setView(View view) {
        this.view = view;
    }
    public void updateView() {
        view.updateView();
    }

    /**
     *
     * @return numberOfFloors, The number of floors.
     */
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     *
     * @return numberOfRows, The number of rows.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     *
     * @return numberOfPlaces, The number of places.
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     *
     * @return numberOfOpenSpots, The number of open spots.
     */
    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }

    /**
     *
     * @param location, the location of a car.
     * @return cars, with the floor, row and place
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     *
     * @param location, A location to set a car.
     * @param car, A car.
     * @return boolean
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    /**
     *
     * @param location, A location to remove a car.
     * @return car, a car.
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    /**
     *
     * @return location, The first location that is free.
     */
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && !location.reserved()) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    public Location getFirstRservedLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && location.reserved()) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @return car
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     *
     * @param location, A valid location.
     * @return boolean
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public int getTotalCars(String type) {
        int total = 0;
        Color color;
        if (type == "AD_HOC") {
            color = Color.red;
        }
        else {
            color = Color.blue;
        }
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getColor() == color) {
                        total += 1;
                    }
                }
            }
        }
        return total;
    }
    public CarQueue getQueue(String type) {
        return simulator.getQueue(type);
    }
    public int getMissedCustomers() {
        return simulator.getMissedCustomers();
    }
    public String getTime() {
        return simulator.getTime();
    }
}
