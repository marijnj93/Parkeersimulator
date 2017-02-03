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
    private int profit = 0;
    //cost/minute in centen
    private int cost = 5;

    private int tickPause = 100;

    //Misschien setters voor maken.
    int weekDayArrivals= 150; // average number of arriving cars per hour
    int weekendArrivals = 250; // average number of arriving cars per hour
    int weekDayPassArrivals; // average number of arriving cars per hour
    int weekendPassArrivals; // average number of arriving cars per hour
    int specialOccasionArivals = 400;
    int newReservations = 4; //Average amount of reservations per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 6; // number of cars that can leave per minute

    public void setWeekDayArrivals(int val) {weekDayArrivals = val;}
    public void setWeekendArrivals(int val) {weekendArrivals = val;}
    public void setWeekDayPassArrivals(int val) {weekendPassArrivals = val;}
    public void setWeekendPassArrivals(int val) {weekendPassArrivals = val;}
    public void setEnterSpeed(int val) {enterSpeed = val;}
    public void setPaymentSpeed(int val) {paymentSpeed = val;}
    public void setExitSpeed(int val) {exitSpeed = val;}
    public void setTickPause(int val) {tickPause = val;}

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        ReservedCars = new ArrayList<ReservedCar>();
        simulatorView = new SimulatorView(3, 6, 30, this);
    }

    public SimulatorView getSimulatorView() {
        return simulatorView;
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
                tick();
        }
    }

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
    }

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

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	handleReservations();
    }
    private void handleReservations() {
        int reservechance = 1;
        Random random = new Random();
        int reservations = Math.round((random.nextInt((50) + 20) * newReservations) / 100) ;
        for (int i = 0; i < reservations; i++) {
            Location location = simulatorView.getFirstFreeLocation();
            if (location != null) {
                ReservedCar car = new ReservedCar(hour);
                ReservedCars.add(car);
                simulatorView.setCarAt(simulatorView.getFirstFreeLocation(), car);
            }
        }

    }
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	simulatorView.tick();

        simulatorView.updateView();	
    }
    
    private void carsArriving(){
        weekendPassArrivals = PassHolders / 3;
        weekDayPassArrivals = PassHolders / 2;
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals, AD_HOC);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals, PASS);
        addArrivingCars(numberOfCars, PASS);    	
    }

    /**
     *
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

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment. (deels gedaan nu)
            profit += cost * car.getStayTime();
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
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
        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        int specialoccasioncars = getSpecialOccasionCars();

        // Calculate the number of cars that arrive this minute.
        System.out.println(" " + averageNumberOfCarsPerHour);
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCars = (int)Math.round(numberOfCarsPerHour / 60) + specialoccasioncars;

        //Possibility of people not entering line if it's long
        int skipped = 0;
        for (int i = 0; i < numberOfCars; i++) {
            int carswaiting = 0;
            if (type == PASS) {
                carswaiting = entrancePassQueue.carsInQueue();
            }
            else {
                carswaiting = entranceCarQueue.carsInQueue();
            }
            double x = Math.random();
            double skipchance =  0.8 * (double) carswaiting;

            if (x <= (skipchance / 100)) {
                missedCustomers++;
                skipped++;
            }
        }
        numberOfCars -= skipped;
        //Zorgt ervoor dat er niet meer parking passes komen te staan dan dat er passen zijn..
        int parkedParkingPass = simulatorView.getTotalCars("ParkingPass" ) + entrancePassQueue.carsInQueue();
        if (parkedParkingPass >= PassHolders && type == PASS) {
            return 0;
        }
        else if (type == PASS && numberOfCars >= parkedParkingPass) {
            return PassHolders - parkedParkingPass;
        }
        return numberOfCars;
    }

    /**
     *
     * @param numberOfCars the number of cars
     * @param type the type of the car (normal car, or a car with a pass.
     */
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
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
     *
     * @param car, a car.
     */
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    //Calculates the exta cars/minute during special occasions (theater for example)
    public int getSpecialOccasionCars() {
        int cars = 0;
        Random random = new Random();
        if ((day == 4 &&  hour == 19) || (day == 5 && hour == 19) || (day == 6 && hour == 14)) {
            int var = random.nextInt(specialOccasionArivals / 3) + (specialOccasionArivals / 3) * 2;
            cars = var / 60;
        }
        return cars;
    }

    public CarQueue getQueue(String type) {

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
    public int getMissedCustomers() {
        return missedCustomers;
    }
    public String getTime() {
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
    public int getProfit() {
        return profit;
    }
}
