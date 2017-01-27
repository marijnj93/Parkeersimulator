package Parkeersimulator.view;
import Parkeersimulator.model.*;
import javax.swing.*;

/**
 * Created by Jeronimo on 1/27/2017.
 */
public class TextView extends View {

    private JLabel lbl_OpenSpots;
    public TextView(SimulatorView simulatorView) {
        super(simulatorView, "TextView");
        lbl_OpenSpots = new JLabel("Open spots: " + simulatorView.getNumberOfOpenSpots());
        add(lbl_OpenSpots);
    }
    public void updateView() {
        lbl_OpenSpots.setText("Open spots: " + simulatorView.getNumberOfOpenSpots());
    }
}
