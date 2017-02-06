package Parkeersimulator.view;
import javax.swing.*;
import Parkeersimulator.model.*;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public abstract class View extends JPanel{
    SimulatorView simulatorView;
    String type;

    /**
     * @param simulatorView, a simulatorView.
     * @param type, The type of a view.
     */
    View(SimulatorView simulatorView, String type) {
        this.simulatorView = simulatorView;
        this.type = type;
    }

    public abstract void updateView();

    /**
     * Get the type of view.
     * @return type, A type of a view.
     */
    public String getType() {
        return type;
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI.
     */
    public Dimension getPreferredSize() {
        return new Dimension(1200, 500);
    }
}
