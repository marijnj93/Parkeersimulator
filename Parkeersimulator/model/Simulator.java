package Parkeersimulator.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class Simulator {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private ArrayList<ReservedCar> ReservedCars;
    private SimulatorView simulatorView;

    private int day = 4;
    private int hour = 18;
    private int minute = 30;

    private int PassHolders = 150;
    private int missedCustomers = 0;
    private int adhocprofit = 0;
    private int reservationprofit = 0;
    private double passprofit = 0;

    //cost/minute in cents
    private int cost = 5;

    //cost/month for a pass in euro's
    private int passcost = 75;

    //cost/reservation in euro's. Reservationparkers still pay the normal minute rate on top of this.
    private int reservationcost = 4;

    private int tickPause = 100;


    private int weekDayArrivals= 150; // average number of arriving cars per hour
    private int weekendArrivals = 250; // average number of arriving cars per hour
    private int weekDayPassArrivals; // average number of arriving cars per hour
    private int weekendPassArrivals; // average number of arriving cars per hour
    private int specialOccasionArivals = 400;
    private int newReservations = 10; //Average amount of reservations per hour


    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 6; // number of cars that can leave per minute

    public void setWeekDayArrivals(int val) {weekDayArrivals = val;}
    public void setWeekendArrivals(int val) {weekendArrivals = val;}
    public void setWeekDayPassArrivals(int val) {weekendPassArrivals = val;}
    public void setWeekendPassArrivals(int val) {weekendPassArrivals = val;}
    public void setEnterSpeed(int val) {enterSpeed = val;}
    public void setParkingPasses(int val) {PassHolders = val;}
    public void setTickPause(int val) {tickPause = val;}
    public void setReservations(int val) { newReservations = val;}

    /**
     * Constructor to create a new simulator.
     */
    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        ReservedCars = new ArrayList<>();
        simulatorView = new SimulatorView(3, 6, 30, this);
    }

    /**
     * Get the simulatorView of the simulator.
     * @return simulatorView, a view of the simulator.
     */
    public SimulatorView getSimulatorView() {
        return simulatorView;
    }

    /**
     * Run the simulator for the specified amount of steps.
     * @param steps, The amount of steps the simulator should run for.
     */
    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
                tick();
        }
    }

    /**
     * Start the following tasks: Advance the time, handle the exit, update the views,
     * handle the entrance, handle the reservations.
     */
    private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
        handleReservations();
    }

    /**
     * Advance the time.
     */
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
    }

    /**
     * Handle cars arriving, and entering the entranceCarQueue and entrancePassQueue.
     */
    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    }

    /**
     * Handle the reservations of specific places for ReservedCars.
     */
    private void handleReservations() {
        Random random = new Random();
        int reservations = 0 ;
        for (int x = 0; x < newReservations; x++) {
            int rand = random.nextInt(100);
            if (rand < 0.3 * newReservations) {
                reservations++;
            }
        }
        for (int i = 0; i < reservations; i++) {
            Location location = simulatorView.getFirstFreeLocation();
            if (location != null) {
                ReservedCar car = new ReservedCar(hour);
                ReservedCars.add(car);
                simulatorView.setCarAt(simulatorView.getFirstFreeLocation(), car);
            }
            //Multiplied by 100 because profit is tracked in cents.
            reservationprofit += reservationcost * 100;
        }
    }

    /**
     * Handle the exit of cars.
     */
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Update the view.
     */
    private void updateViews(){
    	simulatorView.tick();

        simulatorView.updateView();	
    }

    /**
     * Calculate the number of cars arriving and handle them.
     */
    private void carsArriving(){
        weekendPassArrivals = PassHolders / 3;
        weekDayPassArrivals = PassHolders / 2;
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals, AD_HOC);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals, PASS);
        addArrivingCars(numberOfCars, PASS);    	
    }

    /**
     * Handle the cars entering.
     * @param queue a queue for cars.
     */
    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        // Houdt rekening met bepaalde plekken die alleen voor abbonomentshouders bedoeld zijn, aan de hand van een boolean in Location.

        while (queue.carsInQueue()>0 &&
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Location freeLocation = simulatorView.getFirstFreeLocation();
            Location freeReservedLocation = simulatorView.getFirstReservedLocation();
            Car car = queue.nextCar();

            if (car.getColor() == Color.blue && freeReservedLocation != null && freeLocation != null) {
                Location closestSpot = simulatorView.calculateClosestLocation(freeLocation, freeReservedLocation);
                simulatorView.setCarAt(closestSpot, car);
                queue.removeCar();
                i++;
            }
            else if (car.getColor() == Color.blue && freeLocation == null && freeReservedLocation != null) {
                simulatorView.setCarAt(freeReservedLocation, car);
                i++;
                queue.removeCar();
            }
            else if (car.getColor() == Color.blue && freeLocation != null){
                simulatorView.setCarAt(freeLocation, car);
                i++;
                queue.removeCar();
            }
            else if (car.getColor() == Color.red && freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                i++;
                queue.removeCar();
            }
            else {
                break;
            }

        }
    }

    /**
     * Handle cars that are leaving the garage.
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    /**
     * Let cars pay.
     */
    private void carsPaying(){
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            adhocprofit += cost * car.getStayTime();
            carLeavesSpot(car);
            i++;
    	}
    	double profit = 0.00;
    	for (int x = 0; x < PassHolders; x++) {
            profit += ((double)passcost/31/24/60);
        }
        //profit = Math.round(profit);
        //Multiplied by 100 because profit is tracked in cents.
    	passprofit += profit * 100;

    }

    /**
     * Let cars leave.
     */
    private void carsLeaving(){
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }

    /**
     *
     * @param weekDay
     * @param weekend
     * @return The number of cars per hour / 60
     */
    private int getNumberOfCars(int weekDay, int weekend, String type){
        Random random = new Random();
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        int specialoccasioncars = getSpecialOccasionCars();

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCars = (int)Math.round(numberOfCarsPerHour / 60) + specialoccasioncars;

        //Possibility of people not entering line if it's long
        int skipped = 0;
        for (int i = 0; i < numberOfCars; i++) {
            int carswaiting = 0;
            if (type.equals(PASS)) {
                carswaiting = entrancePassQueue.carsInQueue();
            }
            else {
                carswaiting = entranceCarQueue.carsInQueue();
            }
            double x = Math.random();
            double skipchance =  1 * (double) carswaiting;

            if (x <= (skipchance / 100)) {
                missedCustomers++;
                skipped++;
            }
        }
        numberOfCars -= skipped;
        //Zorgt ervoor dat er niet meer parking passes komen te staan dan dat er passen zijn..
        int parkedParkingPass = simulatorView.getTotalCars("ParkingPass" ) + entrancePassQueue.carsInQueue();
        if (parkedParkingPass >= PassHolders && type.equals(PASS)) {
            return 0;
        }
        else if (type.equals(PASS) && numberOfCars >= (PassHolders - parkedParkingPass)) {
            return PassHolders - parkedParkingPass;
        }
        return numberOfCars;
    }

    /**
     * Add a specific amount of arriving cars with with a type to a entrancePassQueue.
     * @param numberOfCars the number of cars
     * @param type the type of the car (normal car, or a car with a pass.
     */
    private void addArrivingCars(int numberOfCars, String type){
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;	            
    	}
    }

    /**
     * Remove a car from the spot it is parked, and add it to the exitCarQueue.
     * @param car, a car.
     */
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    /**
     * Calculate and get getSpecialOccasionCars.
     * @return cars, The amount of extra cars during special occasions.
     */
    private int getSpecialOccasionCars() {
        int cars = 0;
        Random random = new Random();
        if ((day == 4 &&  hour == 19) || (day == 5 && hour == 19) || (day == 6 && hour == 14)) {
            int var = random.nextInt(specialOccasionArivals / 3) + (specialOccasionArivals / 3) * 2;
            cars = var / 60;
        }
        return cars;
    }

    /**
     * Get a queue corresponding to the specified type.
     * @param type, The type of queue.
     * @return CarQueue, A queue corresponding to its type.
     */
    CarQueue getQueue(String type) {
        switch (type) {
            case "entranceCarQueue":
                return entranceCarQueue;
            case "entrancePassQueue":
                return entrancePassQueue;
            case "paymentCarQueue":
                return paymentCarQueue;
            case "exitCarQueue":
                return exitCarQueue;
        }
        return null;
    }

    /**
     * Get the amount of missedCustomers.
     * @return missedCustomers, The amount of missedCustomers.
     */
    int getMissedCustomers() {
        return missedCustomers;
    }

    /**
     * Get the day, hour and minute in a String.
     * @return the day, hour and minute in a String
     */
    String getTime() {
        Map days = new HashMap<Integer, String>();
        days.put(0, "Monday");
        days.put(1, "Tuesday");
        days.put(2, "Wednesday");
        days.put(3, "Thursday");
        days.put(4, "Friday");
        days.put(5, "Saturday");
        days.put(6, "Sunday");
        return days.get(day) + "  " + hour + ":" + minute;
    }
    //Returns a shorter version of the time for use in linegraph.
    String getShortTime() {
        return hour + ":" + minute;
    }
    /**
     * Get the total profit by combining the profit from: adhoc, pass and reserving cars.
     * @return The total profit.
     * @param type, the type of profit.
     */
    public double getProfit(String type) {
        switch (type) {
            case "AD_HOC":
                return adhocprofit;
            case "PASS":
                return passprofit;
            case "RESERVATION":
                return reservationprofit;
            case "TOTAL":
                return adhocprofit + passprofit + reservationprofit;
        }
        return 0.0;

    }
}
