package Parkeersimulator.view;
import Parkeersimulator.model.*;
import javax.swing.*;
import java.awt.*;
/**
 * Created by Jeronimo on 1/27/2017.
 */
public class TextView extends View {

    private JLabel lbl_OpenSpots;
    private JLabel lbl_Places;
    private JLabel lbl_Floors;
    private JLabel lbl_Rows;
    public TextView(SimulatorView simulatorView) {
        super(simulatorView, "TextView");

        setBackground(Color.red);

        lbl_OpenSpots = new JLabel("Open spots: " + simulatorView.getNumberOfOpenSpots());
        add(lbl_OpenSpots);

        lbl_Places = new JLabel("Number of spots: " + simulatorView.getNumberOfPlaces());
        add(lbl_Places);

        lbl_Floors = new JLabel("Number of floors: " + simulatorView.getNumberOfFloors());
        add(lbl_Floors);
    }
    public void updateView() {
        lbl_OpenSpots.setText("Open spots: " + simulatorView.getNumberOfOpenSpots());
    }
    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }
}
