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
        options.add(new Option("Exit speed: : ", "exitSpeed"));
        options.add(new Option("Payment speed: : ", "paymentSpeed"));
        options.add(new Option("Simulator speed : ", "tickPause"));

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
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Input only numbers");
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
                case "exitSpeed":
                    simulator.setExitSpeed(value);
                    break;
                case "paymentSpeed":
                    simulator.setPaymentSpeed(value);
                    break;
                case "tickPause":
                    simulator.setTickPause(value);
                    break;
            }
        }
    }
}

