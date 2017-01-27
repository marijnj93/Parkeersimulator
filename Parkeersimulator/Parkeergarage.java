package Parkeersimulator;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import javax.swing.*;
/**
 * Created by Jeronimo on 1/27/2017.
 */

//Maak dit misschien een JFrame (extending)
public class Parkeergarage {

    //Zorg ervoor dat bij meerdere views een array wordt gebuikt en door heen wordt geloopt..

    private Simulator simulator;
    private View view;
    private Controller controller;
    private JFrame frame;

    public Parkeergarage() {
        simulator = new Simulator();
        view = new CarParkView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);

        controller = new Controller(simulator, this);

        frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.SOUTH);
        frame.getContentPane().add(controller, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        simulator.run(5);

    }
    public void changeView() {
        frame.remove(view);
        if (view.getType() == "TextView") {
            view = new CarParkView(simulator.getSimulatorView());
        }
        else if (view.getType() == "CarParkView") {
            System.out.println("CPView");
            view = new TextView(simulator.getSimulatorView());
        }
        simulator.getSimulatorView().setView(view);
        frame.getContentPane().add(view);
        frame.revalidate();
        frame.pack();
        simulator.getSimulatorView().updateView();
    }
}
