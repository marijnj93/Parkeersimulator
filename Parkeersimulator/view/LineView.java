package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jeronimo on 2/3/2017.
 */
public class LineView extends Garageview {

    private static ArrayList<Moment> values = new ArrayList<Moment>();
    int o = 1;
    public LineView(SimulatorView simulatorView) {
        super(simulatorView, "LineView");
        values.add(new Moment(0, simulatorView.getTime()));
    }
    public void updateView() {
        Random random = new Random();
        double newval = simulatorView.getProfit("TOTAL");
        values.add(new Moment((int)newval / 100, simulatorView.getShortTime()));
        if (values.size() > 20) {
            values.remove(0);
        }
        o++;
        repaint();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBase(g);
    }
    public void drawBase(Graphics g) {
        int x = 100;
        int y = 650;
        int height = 20; //in minutes
        int width = 20;
        int unitwidth = 50;
        int unitheight = 30;
        double maxvalue = 0;

        for (Moment moment : values) {
            if (moment.getValue() > maxvalue) {
                maxvalue = moment.getValue();
            }
        }

        g.drawLine(x, y, x + unitwidth * width, y);
        g.drawLine(x, y, x, y + unitwidth * height * -1);

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
                double yval = (moment.getValue() / maxvalue * height);
                double prevyval = (previous.getValue() / maxvalue * height);
                g.drawLine(x + (listindex - 1) * unitwidth, y - (int)prevyval * unitheight, x + listindex * unitwidth, y - (int)yval * unitheight);

                String time = moment.getTime();
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int stringwidth = metrics.stringWidth(time);
                g.drawString(time, (x + listindex * unitwidth) - stringwidth / 2, y + 25);

                String stringvalue = moment.getValue() + "";
                metrics = g.getFontMetrics(g.getFont());
                stringwidth = metrics.stringWidth(stringvalue);
                g.drawString(stringvalue, (x + listindex * unitwidth) - stringwidth / 2, y - (int)yval * unitheight - 25);

            }
            else {
                g.drawLine(x + (listindex) * unitwidth, y, x + listindex * unitwidth * (listindex + 1), y - moment.getValue());
            }
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }
    private class Moment {
        private int value;;
        private String time;
        public Moment(int value, String time) {
            this.value = value;
            this.time = time;
        }
        public int getValue() {
            return value;
        }
        public String getTime() {
            return time;
        }
    }
}
