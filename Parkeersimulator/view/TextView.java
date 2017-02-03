package Parkeersimulator.view;
import Parkeersimulator.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A view with text displaying information.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class TextView extends View {
    private JLabel lbl_OpenSpots;
    private JLabel lbl_totalAD_HOC;
    private JLabel lbl_totalParkingPass;

    /**
     * Contructor of TextView.
     * @param simulatorView, The simulatorView to use for the TextView.
     */
    public TextView(SimulatorView simulatorView) {
        super(simulatorView, "TextView");
        ArrayList<JLabel> labels = new ArrayList<>();

        //setBackground(Color.red);

        setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));

        int totalspots = simulatorView.getNumberOfFloors() * simulatorView.getNumberOfRows() * simulatorView.getNumberOfPlaces();

        JLabel lbl_tSpots = new JLabel("Number of total spots: " + totalspots);
        labels.add(lbl_tSpots);


        lbl_OpenSpots = new JLabel("Open spots: " + simulatorView.getNumberOfOpenSpots());
        labels.add(lbl_OpenSpots);

        //Empty Row
        labels.add(new JLabel(" "));

        lbl_totalAD_HOC = new JLabel("Amount of normal cars: " + simulatorView.getTotalCars("AD_HOC"));
        labels.add(lbl_totalAD_HOC);

        lbl_totalParkingPass = new JLabel("Amount of parkingpass-cars: " + simulatorView.getTotalCars("ParkingPass"));
        labels.add(lbl_totalParkingPass);

        //Empty Row
        labels.add(new JLabel(" "));

        JLabel lbl_Places = new JLabel("Number of spots/row: " + simulatorView.getNumberOfPlaces());
        labels.add(lbl_Places);

        JLabel lbl_Rows = new JLabel("Number of rows/floor: " + simulatorView.getNumberOfRows());
        labels.add(lbl_Rows);

        JLabel lbl_Floors = new JLabel("Number of floors: " + simulatorView.getNumberOfFloors());
        labels.add(lbl_Floors);

        //Zet de font size en maak zichtbaar voor elke label
        for (JLabel label: labels) {
            label.setFont(new Font(label.getName(), Font.PLAIN, 30));
            add(label);
        }
    }

    /**
     * Update the view with up-to-date information of the open spots and cars.
     */
    public void updateView() {
        lbl_OpenSpots.setText("Open spots: " + simulatorView.getNumberOfOpenSpots());
        lbl_totalAD_HOC.setText("Amount of normal cars: " + simulatorView.getTotalCars("AD_HOC"));
        lbl_totalParkingPass.setText("Amount of parkingpass-cars: " + simulatorView.getTotalCars("ParkingPass"));
    }
}
