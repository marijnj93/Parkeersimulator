package Parkeersimulator.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 *
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    /**
     *
     * @param car, A car to add to the queue.
     * @return Add a car to the queue.
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     *
     * @return Retrieve and remove the head (first element) of this list.
     */
    public Car removeCar() {
        return queue.poll();
    }

    public Car nextCar() {return queue.peek();}

    /**
     *
     * @return the size of the queue.
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
