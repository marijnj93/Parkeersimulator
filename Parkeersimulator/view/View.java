package Parkeersimulator.view;
import javax.swing.*;
import Parkeersimulator.model.*;
/**
 * Created by Jeronimo on 1/27/2017.
 */
public abstract class View extends JPanel{
    protected SimulatorView simulatorView;
    public View(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
    }
    public abstract void updateView();
}
