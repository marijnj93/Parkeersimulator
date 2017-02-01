package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeronimo on 1/30/2017.
 */
public class PieView extends View {
    private Dimension size;
    private ArrayList<Slice> slices = new ArrayList<Slice>();
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


    public void paintComponent(Graphics g) {
        //De super, (JPanel) moet ook getekend worden (?)
        super.paintComponent(g);
        displayTime(g);
        displayProfit(g);
        displayType(g);
        drawChart(g);
    }

    public void drawChart(Graphics g) {
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



    public void updateView() {
        totalvalue = simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfFloors() * simulatorView.getNumberOfRows();
        for (Slice slice:slices) {
            if (slice.getName() == "totalAD_HOC") {
                slice.setValue(simulatorView.getTotalCars("AD_HOC"));
            }
            if (slice.getName() == "openspots") {
                slice.setValue(totalvalue - (simulatorView.getTotalCars("AD_HOC") + simulatorView.getTotalCars("ParkingPass")));
            }
            if (slice.getName() == "totalParkingPass") {
                slice.setValue(simulatorView.getTotalCars("ParkingPass"));
            }
        }
        repaint();
    }
    private class Slice {
        private int value;
        private Color color;
        private String name;

        public Slice(int value, Color color, String name) {
            this.value = value;
            this.color = color;
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
        public Color getColor() {
            return color;
        }
        public void setValue(int value) {
            this.value = value;
        }
    }
}
