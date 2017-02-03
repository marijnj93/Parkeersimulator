package Parkeersimulator.controller;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.Simulator;
import javax.swing.*;

/**
 * An abstract controller.
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
abstract class Controller extends JPanel {
    Simulator simulator;
    Parkeergarage parkeergarage;
    Controller(Simulator simulator, Parkeergarage parkeergarage) {
        this.simulator = simulator;
        this.parkeergarage = parkeergarage;
    }
}
