package Parkeersimulator.view;
import javax.swing.*;
import Parkeersimulator.model.*;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public abstract class View extends JPanel{
    protected SimulatorView simulatorView;
    protected String type;
    public View(SimulatorView simulatorView, String type) {
        this.simulatorView = simulatorView;
        this.type = type;
    }
    public abstract void updateView();
    public String getType() {
        return type;
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
        g.drawString("Total profit: $" + (simulatorView.getProfit() / 100), 20, 60); //Profit is in centen, dus delen door 100
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
    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI
     */
    public Dimension getPreferredSize() {
        return new Dimension(1200, 500);
    }
}
