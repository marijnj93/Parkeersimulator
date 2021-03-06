package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A PieView (circle diagram).
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class PieView extends Garageview {
    private Dimension size;
    private ArrayList<Slice> slices = new ArrayList<>();
    private int totalvalue = 0;

    /**
     * Constructor for objects of class BarView
     * @param simulatorView A simulatorView.
     */
    public PieView(SimulatorView simulatorView) {
        super(simulatorView, "PieView");
        size = new Dimension(0, 0);

        slices.add(new Slice(343, Color.red, "totalAD_HOC"));
        slices.add(new Slice(343, Color.pink, "totalParkingPass"));
        slices.add(new Slice(343, Color.blue, "openspots"));



        setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));
        updateView();
    }

    /**
     * Display the time, profit, type, and chart.
     * @param g, A graphics object.
     */
    public void paintComponent(Graphics g) {
        //De super, (JPanel) moet ook getekend worden (?)
        super.paintComponent(g);
        displayTime(g);
        displayProfit(g);
        displayType(g);
        drawChart(g);
    }
    /**
     * Draw the chart.
     * @param g, A graphics object.
     */
    private void drawChart(Graphics g) {
        int width = 300;
        int height = 300;
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);

        g.setColor(Color.white);
        g.fillRect(x + width + 50, y + height / 2, 175, 150);
        g.setColor(Color.black);

        //Eventueel floats veranderen naar longs
        float startangle = 0;
        int stringy = y + height / 2 + 25;
        for (Slice slice : slices) {
            int value = slice.getValue();
            float percentage = (float) value / (float) totalvalue * 100.0f;
            float angle = (360 / 100.0f) * percentage;
            g.setColor(slice.getColor());
            g.fillArc(x, y, width,height, (int)startangle,(int) angle );
            startangle += angle;

            g.drawString(slice.getName() +" " + slice.getValue(), x + width + 75, stringy);
            stringy += 25;
        }



    }


    /**
     * Update the view.
     */
    public void updateView() {
        totalvalue = simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfFloors() * simulatorView.getNumberOfRows();
        for (Slice slice:slices) {
            if (slice.getName().equals("totalAD_HOC")) {
                slice.setValue(simulatorView.getTotalCars("AD_HOC"));
            }
            if (slice.getName().equals("openspots")) {
                slice.setValue(totalvalue - (simulatorView.getTotalCars("AD_HOC") + simulatorView.getTotalCars("ParkingPass")));
            }
            if (slice.getName().equals("totalParkingPass")) {
                slice.setValue(simulatorView.getTotalCars("ParkingPass"));
            }
        }
        repaint();
    }

    private class Slice {
        private int value;
        private Color color;
        private String name;

        /**
         * Constructor to create a slice.
         * @param value, The value of a slice.
         * @param color, The color of a slice.
         * @param name, The name of a slice.
         */
        Slice(int value, Color color, String name) {
            this.value = value;
            this.color = color;
            this.name = name;
        }

        /**
         * Get the value of a slice.
         * @return value, The value of a slice.
         */
        int getValue() {
            return value;
        }

        /**
         * Get the name of a slice.
         * @return name, The name of a slice.
         */
        String getName() {
            return name;
        }
        /**
         * Get the color of a slice.
         * @return color, The color of a slice.
         */
        public Color getColor() {
            return color;
        }

        /**
         * Set a value.
         * @param value, The value to set.
         */
        void setValue(int value) {
            this.value = value;
        }
    }
}
