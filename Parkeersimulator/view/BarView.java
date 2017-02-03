package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class BarView extends Garageview {


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
            bars.add(new Bar(0, "Entrance ADHOC", "entranceadhoc", colors.get(colorindex)));
            colorindex++;
            bars.add(new Bar(0, "Entrance Pass", "entrancepass", colors.get(colorindex)));
            colorindex++;
            bars.add(new Bar(0, "Missed Customers", "missedcustomers", colors.get(colorindex)));

            setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
            setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));
            updateView();
        }

        /**
         * Overridden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         * @param g
         */
        public void paintComponent(Graphics g) {
            //De super, (JPanel) moet ook getekend worden (?)
            super.paintComponent(g);
            displayTime(g);
            displayProfit(g);
            displayType(g);
            drawGraph(g);
        }

        public void drawGraph(Graphics g) {
            int baseline = getHeight() - 80;
            int scale = getScale();
            int gap = 150;

            for (Bar bar:bars) {
                drawBar(100  + gap * bars.indexOf(bar), baseline, bar, g, scale);
            }

        }

        public void drawBar(int x, int y, Bar bar, Graphics g, int scale) {
            int barvalue = bar.getValue() / scale;
            int barwidth = bar.getWidth();
            y = y - barvalue;
            g.setColor(bar.getColor());
            g.fillRect(x, y, barwidth, barvalue);

            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int stringwidth = metrics.stringWidth(bar.getSubtitle());
            g.setColor(Color.black);
            g.drawString(bar.getSubtitle(), x + 40 - stringwidth / 2, y + barvalue + 30 );

            stringwidth = metrics.stringWidth("" + barvalue);
            g.drawString("" + bar.getValue(), x + 40 - stringwidth / 2, y + barvalue + 50);

        }



        public void updateView() {
            int totalcars = simulatorView.getTotalCars("AD_HOC") + simulatorView.getTotalCars("ParkingPass");
            int openspots = simulatorView.getNumberOfPlaces() * simulatorView.getNumberOfRows() * simulatorView.getNumberOfFloors();
            openspots = openspots - totalcars;
            int totalAD_HOC = simulatorView.getTotalCars("AD_HOC");
            int totalParkingPass = simulatorView.getTotalCars("ParkingPass");
            int entrancequeue = simulatorView.getQueue("entranceCarQueue").carsInQueue();
            int exitqueue = simulatorView.getQueue("entrancePassQueue").carsInQueue();
            int missedcustomers = simulatorView.getMissedCustomers();

            for (Bar bar:bars) {

                if (bar.getName().equals("totalcars")) {
                    bar.setValue(totalcars);
                }
                else if (bar.getName().equals("openspots")) {
                    bar.setValue(openspots);
                }
                else if (bar.getName().equals("totalAD_HOC")) {
                    bar.setValue(totalAD_HOC);
                }
                else if (bar.getName().equals("totalParkingPass")) {
                    bar.setValue(totalParkingPass);
                }
                else if (bar.getName().equals("entranceadhoc")) {
                    bar.setValue(entrancequeue);
                }
                else if (bar.getName().equals("entrancepass")) {
                    bar.setValue(exitqueue);
                }
                else if (bar.getName().equals("missedcustomers")) {
                    bar.setValue(missedcustomers);
                }
            }

            repaint();
        }
    //Scale van 1 is 1 val = 1 pixel.
    public int getScale() {
        int scale = 1;
        if (getHeight() < 400) {
            scale = 3;
        }
        else if (getHeight() < 650) {
            scale = 2;
        }
        return scale;
    }

    public Color randomColor() {
        Random rand = new Random();

        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
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


