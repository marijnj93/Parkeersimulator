package Parkeersimulator.controller;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.Simulator;
import javax.swing.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public abstract class Controller extends JPanel {
    Simulator simulator;
    Parkeergarage parkeergarage;
    public Controller(Simulator simulator, Parkeergarage parkeergarage) {
        this.simulator = simulator;
        this.parkeergarage = parkeergarage;
    }
}
