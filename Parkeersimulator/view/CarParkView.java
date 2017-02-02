package Parkeersimulator.view;

import Parkeersimulator.model.Car;
import Parkeersimulator.model.Location;
import Parkeersimulator.model.SimulatorView;
import java.awt.*;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class CarParkView extends View {

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
                    Color color = car == null ? Color.white : car.getColor();
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


}
