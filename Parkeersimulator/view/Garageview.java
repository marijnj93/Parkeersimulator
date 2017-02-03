package Parkeersimulator.view;

import Parkeersimulator.model.SimulatorView;

import java.awt.*;

/**
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public abstract class Garageview extends View {

    public Garageview(SimulatorView simulatorView, String type) {
        super(simulatorView, type);
    }
    public void displayTime(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 130, 30);
        g.setColor(Color.black);
        g.drawString(simulatorView.getTime(), 20, 20);
    }
    public void displayProfit(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 40, 130, 30);
        g.setColor(Color.black);
        int profit = (int)simulatorView.getProfit("TOTAL") / 100;
        g.drawString("Total profit: $" + (profit), 20, 60); //Profit is in centen, dus delen door 100
    }
    public void displayType(Graphics g) {
        Font oldfont = g.getFont();
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int stringwidth = metrics.stringWidth(type);

        g.drawString(type, (getWidth() / 2) - (stringwidth / 2), 50);
        g.setFont(oldfont);
    }
}
