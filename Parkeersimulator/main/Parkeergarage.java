package Parkeersimulator.main;
import Parkeersimulator.controller.Menu;
import Parkeersimulator.model.*;
import Parkeersimulator.view.*;
import Parkeersimulator.controller.*;
import java.awt.*;
import javax.swing.*;

/**
 * Class Parkeergarage, used to make a new simulator, views, settings and to change views.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */

public class Parkeergarage {
    private Simulator simulator;
    private View view;
    private Settings settings;
    private JFrame frame;

    /**
     * Creates a new parkeergarage.
     */
    private Parkeergarage() {
        simulator = new Simulator();
        view = new LineView(simulator.getSimulatorView());
        simulator.getSimulatorView().setView(view);
        Menu menu = new Menu(simulator, this);
        settings = new Settings(simulator, this);

        frame = new JFrame();
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().add(menu, BorderLayout.NORTH);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setMinimumSize(new Dimension(1200,500));
        frame.setVisible(true);
        simulator.getSimulatorView().updateView();
    }

    /**
     * Starts the simulator.
     */
    public static void main(String[] args) {
        new Parkeergarage();
    }

    /**
     * Changes the view of the simulator.
     */
    public void changeView() {
        if (view.isDisplayable()) {
            frame.remove(view);
            switch (view.getType()) {
                case "TextView":
                    view = new CarParkView(simulator.getSimulatorView());
                    break;
                case "CarParkView":
                    view = new BarView(simulator.getSimulatorView());
                    break;
                case "BarView":
                    view = new PieView(simulator.getSimulatorView());
                    break;
                case "PieView":
                    view = new LineView(simulator.getSimulatorView());
                    break;
                case "LineView":
                    view = new TextView(simulator.getSimulatorView());
                    break;
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

    /**
     * Switch to the change settings view.
     */
    public void changeSettings() {

        if (!settings.isDisplayable()) {
            frame.remove(view);
            settings = new Settings(simulator, this);
            frame.getContentPane().add(settings, BorderLayout.CENTER);
            frame.revalidate();
            frame.pack();
            simulator.getSimulatorView().updateView();
        }
    }
}
