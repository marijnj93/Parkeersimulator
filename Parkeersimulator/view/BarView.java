package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class BarView, used to create a BarView.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class BarView extends Garageview {

    private ArrayList<Bar> bars = new ArrayList<>();

    /**
     * Constructor for objects of class BarView
     * @param simulatorView A simulatorView.
     */
    public BarView(SimulatorView simulatorView) {
        super(simulatorView, "BarView");
        new Dimension(0, 0);

        ArrayList<Color>colors = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            colors.add(randomColor());
        }

        int colorindex = 0;
        //Maak de bars en stel de naam en ondertiteling in.
                bars.add(new Bar(0, "Regular cars", "totalAD_HOC", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Pass holder cars", "totalParkingPass", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Parked cars", "totalcars", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Open spots", "openspots", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Regular entrance", "entranceadhoc", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Pass holder entrance", "entrancepass", colors.get(colorindex)));
                colorindex++;
                bars.add(new Bar(0, "Missed customers", "missedcustomers", colors.get(colorindex)));

        setLayout((new BoxLayout(this, BoxLayout.PAGE_AXIS)));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));
        updateView();
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     * @param g, A graphics object.
     */
    public void paintComponent(Graphics g) {
        //De super, (JPanel) moet ook getekend worden (?)
        super.paintComponent(g);
        displayTime(g);
        displayProfit(g);
        displayType(g);
        drawGraph(g);
    }

    /**
     * Draw a graph.
     * @param g, A graphics object.
     */
    private void drawGraph(Graphics g) {
            int baseline = getHeight() - 80;
            int scale = getScale();
            int gap = 150;

            for (Bar bar:bars) {
                drawBar(100  + gap * bars.indexOf(bar), baseline, bar, g, scale);
            }
        }


    /**
     * Draw a bar.
     * @param x, The horizontal width of the bar.
     * @param y, The vertical height of the bar.
     * @param bar, A bar.
     * @param g, A graphics object.
     * @param scale, The scale to draw the bar at.
     */
    private void drawBar(int x, int y, Bar bar, Graphics g, int scale) {
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

    /**
     * Update the view with up-to-date information.
     */
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
            switch (bar.getName()) {
                case "totalcars":
                    bar.setValue(totalcars);
                    break;
                case "openspots":
                    bar.setValue(openspots);
                    break;
                case "totalAD_HOC":
                    bar.setValue(totalAD_HOC);
                    break;
                case "totalParkingPass":
                    bar.setValue(totalParkingPass);
                    break;
                case "entranceadhoc":
                    bar.setValue(entrancequeue);
                    break;
                case "entrancepass":
                    bar.setValue(exitqueue);
                    break;
                case "missedcustomers":
                    bar.setValue(missedcustomers);
                    break;
            }
        }
        repaint();
    }

    /**
     * Get the scale of the height.
     * @return scale, 3 if height is below 400, 2 if below 650, or default 1.
     */
    //Scale van 1 is 1 val = 1 pixel.
    private int getScale() {
        int scale = 1;
        if (getHeight() < 400) {
            scale = 3;
        }
        else if (getHeight() < 650) {
            scale = 2;
        }
        return scale;
    }

    /**
     * Generate a random color.
     * @return Color, A color object with random rbg.
     */
    private Color randomColor() {
        Random rand = new Random();

        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return new Color(r, g, b);
    }

    /**
     * Class Bar, used to create bars.
     */
    private class Bar {
        private int value;
        private int width = 80;
        private String subtitle;
        private Color color;
        private String name;

        /**
         * Constructor to create a bar.
         * @param value, The value of the bar.
         * @param subtitle, The subtitle of the bar.
         * @param name, The name of the bar.
         * @param color, The color of the bar.
         */
        Bar(int value, String subtitle, String name, Color color) {
            this.value = value;
            this.subtitle = subtitle;
            this.color = color;
            this.name = name;
        }

        /**
         * Get the value of a bar.
         * @return value, The value of a bar.
         */
        int getValue() {
            return value;
        }

        /**
         * Get the width of a bar.
         * @return width, The width of a bar.
         */
        private int getWidth() {
            return width;
        }

        /**
         * Get the name of a bar.
         * @return name, The name of a bar.
         */
        String getName() {
            return name;
        }

        /**
         * Get the subtitle of a bar.
         * @return subtitle, The subtitle of a bar.
         */
        String getSubtitle(){
            return subtitle;
        }

        /**
         * Get the color of a bar.
         * @return color, The color of a bar.
         */
        public Color getColor() {
            return color;
        }

        /**
         * Set the value of a bar.
         * @param value, the value of a bar.
         */
        void setValue(int value) {
            this.value = value;
        }
    }
}


