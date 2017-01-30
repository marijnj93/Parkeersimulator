package Parkeersimulator;

import Parkeersimulator.model.SimulatorView;
import Parkeersimulator.view.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jeronimo on 1/30/2017.
 */
public class BarView extends View {


        private Dimension size;
        private int openspots;
        private int totalcars;


        /**
         * Constructor for objects of class BarView
         * @param simulatorView A simulatorView.
         */
        public BarView(SimulatorView simulatorView) {
            super(simulatorView, "BarView");
            size = new Dimension(0, 0);
            setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));
            //setBackground(Color.red);
        }

        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         * @return The dimension of the GUI
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * Overridden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         * @param g
         */
        public void paintComponent(Graphics g) {
            //De super, (JPanel) moet ook getekend worden (?)
            super.paintComponent(g);
            drawBars(g);
        }

        public void drawBars(Graphics g) {
            int baseline = getHeight() - 100;
            totalcars = totalcars / 2;
            openspots = openspots / 2;

            g.setColor(Color.red);
            g.fillRect(200, baseline - totalcars, 80, totalcars);
            g.drawString("Amount of parked cars", 175, baseline + 30);

            g.setColor(Color.blue);
            g.fillRect(380, baseline - openspots, 80, openspots);
            g.drawString("Amount of free spots", 355, baseline + 30);
        }
        public void drawBar(int x, int y, int value, Graphics g, String text) {
            
        }
        public void updateView() {
            totalcars = simulatorView.getTotalCars("AD_HOC") + simulatorView.getTotalCars("ParkingPass");
            openspots = simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfRows() * simulatorView.getNumberOfFloors();
            openspots -= totalcars;
            repaint();
        }

}


