package Parkeersimulator.main;
import Parkeersimulator.controller.Menu;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */

//Maak dit misschien een JFrame (extending)
public class Parkeergarage {


    private Simulator simulator;
    private View view;
    private Menu menu;
    private Settings settings;

    private JFrame frame;

    public Parkeergarage() {
        simulator = new Simulator();
        view = new CarParkView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);
        menu = new Menu(simulator, this);
        settings = new Settings(simulator, this);

        frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().add(menu, BorderLayout.NORTH);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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
