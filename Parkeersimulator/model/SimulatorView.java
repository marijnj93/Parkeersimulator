package Parkeersimulator.model;

import Parkeersimulator.view.*;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
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
     * Constructor of class SimulatorView.
     * @param numberOfFloors, The number of floors.
     * @param numberOfRows, the number of rows.
     * @param numberOfPlaces, the number of places.
     */
    SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*numberOfPlaces;
        this.simulator = simulator;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

    }

    /**
     * Set a view.
     * @param view, A view to set.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Update the view.
     */
    public void updateView() {
        view.updateView();
    }

    /**
     * Get the numberOfFloors.
     * @return numberOfFloors, The number of floors.
     */
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Get the numberOfRows.
     * @return numberOfRows, The number of rows.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Get the getNumberOfPlaces.
     * @return numberOfPlaces, The number of places.
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Get the numberOfOpenSpots.
     * @return numberOfOpenSpots, The number of open spots.
     */
    int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }

    /**
     * Get a car at the specified location.
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
     * Set a car at the specified location.
     * @param location, A location to set a car.
     * @param car, A car.
     * @return boolean
     */
    boolean setCarAt(Location location, Car car) {
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
     * Remove a car at the specified location.
     * @param location, A location to remove a car.
     * @return car, a car.
     */
    Car removeCarAt(Location location) {
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
     * Get the first free location.
     * @return location, The first location that is free.
     */
    Location getFirstFreeLocation() {
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

    /**
     * Get the first reserved location.
     * @return location, A reserved location.
     */
    Location getFirstReservedLocation() {
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
     * Get a car that has no time left to stay parked.
     * @return car, A car that has no time left to stay parked.
     */
    Car getFirstLeavingCar() {
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

    /**
     * Loop over each parked car, and run the tick method of a car.
     */
    void tick() {
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
     * Check if a location is valid.
     * @param location, A valid location.
     * @return true, if a location exists.
     */
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        return !(floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces);
    }

    /**
     * Get the total number of cars.
     * @param type, The type of car.
     * @return total, The total number of cars.
     */
    public int getTotalCars(String type) {
        int total = 0;
        Color color;
        if (type.equals("AD_HOC")) {
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

    /**
     * Calculate and find the closest location between 2 locations.
     * @param loc1, A location.
     * @param loc2 The location to compare the first location to.
     * @return closest, the closest location.
     */
    Location calculateClosestLocation(Location loc1, Location loc2) {
        Location closest;
        if (loc1.getFloor() < loc2.getFloor()) {
            closest = loc1;
        }
        else if (loc1.getRow() < loc2.getRow() && loc1.getFloor() <= loc2.getFloor()) {
            closest = loc1;
        }
        else if (loc1.getPlace() < loc2.getPlace() && loc1.getRow() <= loc2.getRow() && loc1.getFloor() <= loc2.getFloor()) {
            closest = loc1;
        }
        else {
            closest = loc2;
        }
        return closest;
    }

    /**
     * Get the profit.
     * @param type, the type of profit.
     * @return profit, the profit.
     */
    public double getProfit(String type) {
       return simulator.getProfit(type);
    }

    /**
     * Get the queue with a specified type.
     * @param type, The type of queue.
     * @return queue, A queue.
     */
    public CarQueue getQueue(String type) {
        return simulator.getQueue(type);
    }

    /**
     * Get the missed customers.
     * @return missed customers
     */
    public int getMissedCustomers() {
        return simulator.getMissedCustomers();
    }

    /**
     * Get the time.
     * @return The time.
     */
    public String getTime() {return simulator.getTime();}

    /**
     * Get the time in a short notation.
     * @return The time in a short notation.
     */
    public String getShortTime() {
        return simulator.getShortTime();
    }

}
