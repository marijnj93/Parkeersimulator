package Parkeersimulator;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import java.util.ArrayList;
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
        view = new BarView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);
        controller = new Controller(simulator, this);

        frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().add(controller, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        simulator.getSimulatorView().updateView();

    }
    public void changeView() {
        frame.remove(view);

        if (view.getType() == "TextView") {
            view = new CarParkView(simulator.getSimulatorView());
        }
        else if (view.getType() == "CarParkView")  {
            view = new BarView(simulator.getSimulatorView());
        }
        else if (view.getType() == "BarView") {
            view = new TextView(simulator.getSimulatorView());
        }

        simulator.getSimulatorView().setView(view);
        frame.getContentPane().add(view);
        frame.revalidate();
        frame.pack();
        simulator.getSimulatorView().updateView();
    }
}
