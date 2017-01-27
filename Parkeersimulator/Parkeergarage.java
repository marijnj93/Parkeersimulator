package Parkeersimulator;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import javax.swing.*;
/**
 * Created by Jeronimo on 1/27/2017.
 */
public class Parkeergarage {

    private Simulator simulator;
    private View view;
    private Controller controller;
    private JFrame frame;

    public Parkeergarage() {
        simulator = new Simulator();
        view = new TextView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);
        controller = new Controller(simulator);

        frame = new JFrame();

        frame.getContentPane().add(view, BorderLayout.SOUTH);
        frame.getContentPane().add(controller, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);

        simulator.run(5);

    }
}
