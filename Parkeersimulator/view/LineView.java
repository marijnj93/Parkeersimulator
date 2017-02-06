package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A lineview.
 * @author Marijn, Mark, Vincent, Bart,
 * @version 04-02-2017
 */
public class LineView extends Garageview {

    private static ArrayList<Moment> values = new ArrayList<>();
    private static int intervalcount = 0;
    private int interval = 5; //1 new point every 1 minute, 1 new point every 2 minutes if 2.. etc..

    /**
     * Constructor of LineView.
     * @param simulatorView, a SimulatorView.
     */
    public LineView(SimulatorView simulatorView) {
        super(simulatorView, "LineView");
        if (values.isEmpty()) {
            values.add(new Moment(0, simulatorView.getTime()));
        }
    }

    /**
     * Update the view.
     */
    public void updateView() {
        intervalcount++;
        Random random = new Random();
        double newval = simulatorView.getProfit("TOTAL");
        if (intervalcount == interval) {
            values.add(new Moment((int)newval / 100, simulatorView.getShortTime()));
            intervalcount = 0;
        }

        if (values.size() > 20) {
            values.remove(0);
        }
        repaint();

    }

    /**
     * Display the background, profit, type, and time.
     * @param g, A graphics object.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBase(g);
        displayProfit(g);
        displayType(g);
        displayTime(g);
    }

    /**
     * Draw the background.
     * @param g, A graphics object.
     */
    private void drawBase(Graphics g) {
        int x = 100;
        int y = 650;
        int height = 20; //in interval
        int width = 20; //in intervals
        int unitwidth = 50;
        int unitheight = 25;
        double maxvalue = 0;

        for (Moment moment : values) {
            if (moment.getValue() > maxvalue) {
                maxvalue = moment.getValue();
            }
        }

        g.drawLine(x, y, x + unitwidth * width, y);
        g.drawLine(x, y, x, y + unitheight * height * -1);

        //Draw interval graph info
        g.drawString("Current interval is once every " + interval + " minutes", x, y + 50 );
        g.drawString("Displaying total profit on the y-axis, time on the x-axis", x, y + 75);

        //Draw marks
        for (int i = 0; i <= width; i++) {
            double markvalue = Math.round(maxvalue / height);
            //X marks
             g.drawLine(x + unitwidth * i, y, x + unitwidth * i, y - 10);
            //Y marks
             g.drawLine(x, y - unitheight * i, x + 10, y - unitheight * i);
             //Draw Y values
            g.drawString("" + markvalue * i, x - 50, y - unitheight * i);

        }
        for (Moment moment : values) {
            int listindex = 0;
            Moment previous = null;
            try {
                listindex = values.indexOf(moment);
                previous = values.get(listindex - 1);
            }
            catch(java.lang.ArrayIndexOutOfBoundsException e) {
            }

            if (previous != null) {
                //Draw the line from the previous value to the current value
                double yval = (moment.getValue() / maxvalue * height);
                double prevyval = (previous.getValue() / maxvalue * height);
                g.drawLine(x + (listindex - 1) * unitwidth, y - (int)(prevyval * unitheight), x + listindex * unitwidth, y - (int)(yval * unitheight));

                g.fillOval(x + listindex * unitwidth - 2, y - (int)(yval * unitheight) - 2, 4, 4);
                //Display time of value
                String time = moment.getTime();
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int stringwidth = metrics.stringWidth(time);
                g.drawString(time, (x + listindex * unitwidth) - stringwidth / 2, y + 25);

                //Display value on point
                String stringvalue = moment.getValue() + "";
                metrics = g.getFontMetrics(g.getFont());
                stringwidth = metrics.stringWidth(stringvalue);
                g.drawString(stringvalue, (x + listindex * unitwidth + 12) - stringwidth / 2, y - (int)yval * unitheight + 12);
            }
            else {
                g.drawLine(x + (listindex) * unitwidth, y, x + listindex * unitwidth * (listindex + 1), y - moment.getValue());
            }
        }
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }
    private class Moment {
        private int value;
        private String time;

        /**
         * Constructor of Moment.
         * @param value, The value of a moment.
         * @param time, The time of a moment.
         */
        Moment(int value, String time) {
            this.value = value;
            this.time = time;
        }

        /**
         * Get the value of a moment.
         * @return value, The value of a moment.
         */
        int getValue() {
            return value;
        }

        /**
         * Get the time of a moment.
         * @return time, The time of a moment.
         */
        String getTime() {
            return time;
        }
    }
}
