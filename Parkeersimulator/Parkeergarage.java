package Parkeersimulator;
import Parkeersimulator.controller.Menu;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by Jeronimo on 1/27/2017.
 */

//Maak dit misschien een JFrame (extending)
public class Parkeergarage {

    //Zorg ervoor dat bij meerdere views een array wordt gebuikt en door heen wordt geloopt..

    private Simulator simulator;
    private View view;
    private Menu menu;
    private Settings settings;

    private JFrame frame;

    public Parkeergarage() {
        simulator = new Simulator();
        view = new BarView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);
        menu = new Menu(simulator, this);
        settings = new Settings(simulator, this);

        frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().add(menu, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        simulator.getSimulatorView().updateView();

    }
    public void changeView() {

        //Gelabeled zodat er uit een nested loop gebroken kan worden. Loopt door alle views en controllers..
        setviewloop:
        if (view.isDisplayable()) {
            frame.remove(view);

            if (view.getType() == "TextView") {
                view = new CarParkView(simulator.getSimulatorView());
            } else if (view.getType() == "CarParkView") {
                view = new BarView(simulator.getSimulatorView());
            } else if (view.getType() == "BarView") {
                view = new PieView(simulator.getSimulatorView());
            } else if (view.getType() == "PieView") {
                frame.getContentPane().add(settings, BorderLayout.CENTER);
                break setviewloop;
            }
            simulator.getSimulatorView().setView(view);
            frame.getContentPane().add(view, BorderLayout.CENTER);
        }
        else {
            frame.remove(settings);
            view = new TextView(simulator.getSimulatorView());
            simulator.getSimulatorView().setView(view);
            frame.getContentPane().add(view, BorderLayout.CENTER);
        }

        frame.revalidate();
        frame.pack();
        simulator.getSimulatorView().updateView();
    }
}
