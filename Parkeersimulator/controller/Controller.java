package Parkeersimulator.controller;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.Simulator;
import javax.swing.*;

/**
 * An abstract controller.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
abstract class Controller extends JPanel {
    Simulator simulator;
    Parkeergarage parkeergarage;

    /**
     * Constructor of Controller.
     * @param simulator, A simulator.
     * @param parkeergarage, A parkeergarage.
     */
    Controller(Simulator simulator, Parkeergarage parkeergarage) {
        this.simulator = simulator;
        this.parkeergarage = parkeergarage;
    }
}
