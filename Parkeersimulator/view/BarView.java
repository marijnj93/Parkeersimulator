package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;
import Parkeersimulator.view.View;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeronimo on 1/30/2017.
 */
public class BarView extends View {


        private Dimension size;
        private ArrayList<Bar> bars = new ArrayList<Bar>();


        /**
         * Constructor for objects of class BarView
         * @param simulatorView A simulatorView.
         */
        public BarView(SimulatorView simulatorView) {
            super(simulatorView, "BarView");
            size = new Dimension(0, 0);

            ArrayList<Color>colors = new ArrayList<Color>();
            for (int i = 0; i < 50; i++) {
                colors.add(randomColor());
            }

            int colorindex = 0;

            //Maak de bars en stel de naam en ondertiteling in.
            bars.add(new Bar(0, "ADHOC cars", "totalAD_HOC", colors.get(colorindex)));
            colorindex++;
            bars.add(new Bar(0, "ParkingPass cars", "totalParkingPass", colors.get(colorindex)));
            colorindex++;
            bars.add(new Bar(0, "Parked cars", "totalcars", colors.get(colorindex)));
            colorindex++;
            bars.add(new Bar(0, "Open spots", "openspots", colors.get(colorindex)));
            colorindex++;


            setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));
            updateView();
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
            drawGraph(g);
        }

        public void drawGraph(Graphics g) {
            int baseline = getHeight() - 80;
            //Scale van 1 is 1 val = 1 pixel.
            int scale = 1;
            int gap = 150;

            if (getHeight() < 400) {
                scale = 3;
            }
            else if (getHeight() < 700) {
                scale = 2;
            }
            else if (getHeight() < 1000 ) {
                scale = 1;
            }

            for (Bar bar:bars) {
                drawBar(100 + gap * bars.indexOf(bar), baseline, bar, g, scale);
            }

        }
        public void drawBar(int x, int y, Bar bar, Graphics g, int scale) {
            int barvalue = bar.getValue() / scale;
            y = y - barvalue;
            g.setColor(bar.getColor());
            g.fillRect(x, y, 80, barvalue);

            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int stringwidth = metrics.stringWidth(bar.getSubtitle());
            g.setColor(Color.black);
            g.drawString(bar.getSubtitle(), x + 40 - stringwidth / 2, y + barvalue + 30 );


        }
        public Color randomColor() {
            Random rand = new Random();

            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            return new Color(r, g, b);
        }

        public void updateView() {
            int totalcars = simulatorView.getTotalCars("AD_HOC") + simulatorView.getTotalCars("ParkingPass");
            int openspots = simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfRows() * simulatorView.getNumberOfFloors();
            openspots = openspots - totalcars;
            int totalAD_HOC = simulatorView.getTotalCars("AD_HOC");
            int totalParkingPass = simulatorView.getTotalCars("ParkingPass");
            for (Bar bar:bars) {

                if (bar.getName() == "totalcars") {
                    bar.setValue(totalcars);
                }
                else if (bar.getName() == "openspots") {
                    bar.setValue(openspots);
                }
                else if (bar.getName() == "totalAD_HOC") {
                    bar.setValue(totalAD_HOC);
                }
                else if (bar.getName() == "totalParkingPass") {
                    bar.setValue(totalParkingPass);
                }
            }

            repaint();
        }

    private class Bar {
        private int value;
        private int width = 80;
        private String subtitle;
        private Color color;
        private String name;

        public Bar(int value, String subtitle, String name, Color color) {
            this.value = value;
            this.subtitle = subtitle;
            this.color = color;
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public int getWidth() {
            return width;
        }
        public String getName() {
            return name;
        }
        public String getSubtitle(){
            return subtitle;
        }
        public Color getColor() {
            return color;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}


