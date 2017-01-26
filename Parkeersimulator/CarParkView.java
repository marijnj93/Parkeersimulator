package Parkeersimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Marijn on 26-1-2017.
 */
class CarParkView extends JPanel {

    private SimulatorView simulatorView;
    private Dimension size;
    private Image carParkImage;
    private Boolean clicked_advance10 = true;
    private Boolean clicked_advance100 = true;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorView simulatorView) {
        this.simulatorView = simulatorView;
        size = new Dimension(0, 0);
        JButton btn_advance10 = new JButton("Advance 10");
        JButton btn_advance100 = new JButton("Advance 100");
        add(btn_advance10);
        add(btn_advance100);
        btn_advance10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicked_advance10 = true;
            }
        });
        btn_advance100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicked_advance100 = true;
            }
        });
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        } else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
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
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
    public Boolean checkAdvance_10() {
        Boolean r = clicked_advance10;
        clicked_advance10 = false;
        return r;
    }
    public Boolean checkAdvance_100() {
        Boolean r = clicked_advance100;
        clicked_advance100 = false;
        return r;
    }
}
