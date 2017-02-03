package Parkeersimulator.view;

import Parkeersimulator.model.Car;
import Parkeersimulator.model.Location;
import Parkeersimulator.model.SimulatorView;
import java.awt.*;
import java.lang.reflect.Array;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class CarParkView extends Garageview {

    private Dimension size;
    private Image carParkImage;


    /**
     * Constructor for objects of class CarPark
     * @param simulatorView A simulatorView.
     */
    public CarParkView(SimulatorView simulatorView) {
        super(simulatorView, "CarParkView");
        size = new Dimension(0, 0);
    }


    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, (0), 50, null);
        } else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 50, currentSize.width, currentSize.height, null);
        }
        drawLegenda(g);
        displayTime(g);
        displayProfit(g);
        displayType(g);
    }

    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for (int floor = 0; floor < simulatorView.getNumberOfFloors(); floor++) {
            for (int row = 0; row < simulatorView.getNumberOfRows(); row++) {
                for (int place = 0; place < simulatorView.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = simulatorView.getCarAt(location);
                    Color color;
                    if (car == null) {
                        color = Color.white;
                        if (location.reserved()) {
                            color = Color.lightGray;
                        }
                    }
                    else {
                        color = car.getColor();
                    }
                    //Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     * @param graphics,
     * @param location, The location to paint at.
     * @param color, The color that is used.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
    private void drawLegenda(Graphics g) {

        int x = getWidth() - 350;
        int y = getHeight() - 250;
        g.setColor(Color.white);
        g.fillRect(x, y, 325, 150);
        g.setColor(Color.black);
        Color[] colors = new Color[5];
        colors[0] = Color.blue; colors[1] = Color.red; colors[2] = Color.gray; colors[3] = Color.orange; colors[4] = Color.green;

        int i = 1;
        for (Color color : colors) {
            g.setColor(color);
            g.fillRect(x + 20, y + 20 * i, 25, 15);
            i++;
        }
        g.setColor(Color.black);
        Font oldfont = g.getFont();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.drawString("Pass car", x + 60, y + 35);
        g.drawString("Normal car", x + 60, y + 55);
        g.drawString("Empty parking pass spot", x + 60, y + 75);
        g.drawString("Online reserved spot (occupied)", x + 60, y + 95);
        g.drawString("Online reserved spot (unoccupied)", x + 60, y + 115);
        g.setFont(oldfont);
    }

}
