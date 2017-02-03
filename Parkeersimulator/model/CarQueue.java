package Parkeersimulator.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for cars.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     * Add a car to the queue.
     * @param car, A car to add to the queue.
     * @return A queue with a car added.
     */
    boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Remove a car from the queue.
     * @return Retrieve and remove the head (first element) of this list.
     */
    Car removeCar() {
        return queue.poll();
    }

    /**
     * Look at the next car in the queue.
     * @return A car in the next position in the car.
     */
    Car nextCar() {
        return queue.peek();
    }

    /**
     * Check the total number of cars in the queue.
     * @return the size of the queue.
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
