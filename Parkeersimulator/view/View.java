package Parkeersimulator.view;
import javax.swing.*;
import Parkeersimulator.model.*;
/**
 * Created by Jeronimo on 1/27/2017.
 */
public abstract class View extends JPanel{
    protected SimulatorView simulatorView;
    protected String type;
    public View(SimulatorView simulatorView, String type) {
        this.simulatorView = simulatorView;
        this.type = type;
    }
    public abstract void updateView();
    public String getType() {
        return type;
    }
}
