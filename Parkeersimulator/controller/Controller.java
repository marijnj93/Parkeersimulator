package Parkeersimulator.controller;

import Parkeersimulator.Parkeergarage;
import Parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Jeronimo on 1/31/2017.
 */
public abstract class Controller extends JPanel implements ActionListener {
    Simulator simulator;
    Parkeergarage parkeergarage;
    public Controller(Simulator simulator, Parkeergarage parkeergarage) {
        this.simulator = simulator;
        this.parkeergarage = parkeergarage;
    }
}
