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
    private JLabel lbl_adhocProfit;
    private JLabel lbl_passProfit;
    private JLabel lbl_reservationProfit;
    private JLabel lbl_totalProfit;

    private ArrayList<JLabel> labels;

    /**
     * Contructor of TextView.
     * @param simulatorView, The simulatorView to use for the TextView.
     */
    public TextView(SimulatorView simulatorView) {
        super(simulatorView, "TextView");
        ArrayList<JLabel> labels = new ArrayList<>();

        setLayout((new GridLayout(0, 1)));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));


        lbl_adhocProfit = new JLabel("Profit made from regular customers: " + simulatorView.getProfit("AD_HOC"));
        labels.add(lbl_adhocProfit);

        lbl_passProfit = new JLabel("Profit made from parking passes: " + simulatorView.getProfit("PASS"));
        labels.add(lbl_passProfit);

        lbl_reservationProfit = new JLabel("Profit made from reservations: " + simulatorView.getProfit("RESERVATION"));
        labels.add(lbl_reservationProfit);

        lbl_totalProfit = new JLabel("Total profit made: " + simulatorView.getProfit("TOTAL"));
        labels.add(lbl_totalProfit);
        //Zet de font size en maak zichtbaar voor elke label
        for (JLabel label: labels) {
            label.setFont(new Font(label.getName(), Font.PLAIN, 30));
            add(label);
        }
    }

    /**
     * Update the view with up-to-date information about the profit.
     */
    public void updateView() {

        lbl_adhocProfit.setText("Profit made from regular customers: $" + Math.round(simulatorView.getProfit("AD_HOC")/ 100));
        lbl_passProfit.setText("Profit made from parking passes: $" + Math.round(simulatorView.getProfit("PASS") / 100));
        lbl_reservationProfit.setText("Profit made from reservations: $" + Math.round(simulatorView.getProfit("RESERVATION") / 100));
        lbl_totalProfit.setText("Total profit made: $" + Math.round(simulatorView.getProfit("TOTAL") / 100));
    }
}
