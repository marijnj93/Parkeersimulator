package Parkeersimulator;
import java.util.LinkedList;
import java.util.Queue;

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

    /**
     *
     * @return the size of the queue.
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
