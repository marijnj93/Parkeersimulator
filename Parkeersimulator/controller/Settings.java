package Parkeersimulator.controller;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @version 03-02-2017
 */
public class Settings extends Controller {
    /**
     * Create settings with options.
     * @param simulator, A simulator.
     * @param parkeergarage , A parkeergarage
     */
    public Settings(Simulator simulator, Parkeergarage parkeergarage )  {
        super(simulator, parkeergarage);

        setLayout(new GridLayout(0, 3));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option("Enter speed: ", "enterSpeed"));
        options.add(new Option("Simulator speed : ", "tickPause"));
        options.add(new Option("Parking passes : ", "parkingPasses"));
        options.add(new Option("Reservations/hour : ", "reservationAmount"));
        options.add(new Option("Regular cars/hour : ", "regularCarsAmount"));
        options.add(new Option("Pass holders/hour : ", "passCarsAmount"));
        options.add(new Option("Floors:  ", "setFloors"));
        options.add(new Option("Rows:  ", "setRows"));
        options.add(new Option("Places:  ", "setPlaces"));
        for (Option option : options) {
            option.display();
        }
        //Teken een x aantal labels, afhankelijk van aantal opties om de size van de rows altijd hetzelfde te houden
        for (int i = 0; i < 20 - options.size(); i++) {
            add(new JLabel(" "));
        }
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI.
     */
    public Dimension getPreferredSize() {
        repaint();
        return new Dimension(1200, 500);
    }

    /**
     * Class option, used for settings to configure the simulator.
     */
    private class Option {
        private JLabel lbl_Description;
        private JTextField txt_Input;
        private JButton btn_Submit;
        private String variable;

        /**
         *
         * @param Description, The description of a setting (label).
         * @param variable, The name of a specific setting
         */
        Option(String Description, String variable) {
            lbl_Description = new JLabel(Description);
            txt_Input = new JTextField();
            btn_Submit = new JButton("Set");
            this.variable = variable;

            btn_Submit.addActionListener(e -> {
                int value = 0;
                try {
                    value = Integer.parseInt(txt_Input.getText());
                    if (value < 0) {
                        JOptionPane.showMessageDialog(new JFrame(), "You can't enter negative numbers!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "You can only enter numbers!");
                }
                setValue(value);
            });
        }

        /**
         * Display input fields, descriptions and a submit button.
         */
        void display() {
            add(lbl_Description);
            add(txt_Input);
            add(btn_Submit);
        }

        /**
         * Set the inputted value of the setting for the simulator.
         * @param value, The value entered by a user to adjust the simulator.
         */
        //Roep de juiste setter aan aan de hand van de in de constructor meegegeven variabele "variabele"
        void setValue(int value) {
            switch(variable) {
                case "enterSpeed":
                    simulator.setEnterSpeed(value);
                    break;
                case "tickPause":
                    simulator.setTickPause(value);
                    break;
                case "parkingPasses":
                    simulator.setParkingPasses(value);
                    break;
                case "reservationAmount":
                    simulator.setReservations(value);
                    break;
                case "regularCarsAmount":
                    simulator.setWeekDayArrivals(value / 2);
                    simulator.setWeekendArrivals(value);
                    break;
                case "passCarsAmount":
                    simulator.setWeekDayPassArrivals(value );
                    simulator.setWeekendPassArrivals(value / 2);
                    break;
                case "setFloors":
                    simulator.changeMap("Floors", value);
                    break;
                case "setRows":
                    simulator.changeMap("Rows", value);
                    break;
                case "setPlaces":
                    simulator.changeMap("Places", value);
                    break;
            }
        }
    }
}

