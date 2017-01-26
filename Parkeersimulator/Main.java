package Parkeersimulator;
/**
 * Created by Marijn on 20-1-2017.
 *
 * This class is used to start the program.
 */
public class Main {
static Simulator sim;
    public static void main(String[] args) {
        sim = new Simulator();
        sim.run();
    }
}