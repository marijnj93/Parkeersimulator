package Parkeersimulator.controller;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class Settings extends Controller {

    private ArrayList<Option> options = new ArrayList<Option>();


    public Settings(Simulator simulator, Parkeergarage parkeergarage )  {
        super(simulator, parkeergarage);

        setLayout(new GridLayout(0, 3));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));

        options.add(new Option("Enter speed: ", "enterSpeed"));
        options.add(new Option("Exit speed: : ", "exitSpeed"));

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
     * @return The dimension of the GUI
     */
    public Dimension getPreferredSize() {
        repaint();
        return new Dimension(1200, 500);
    }

    private class Option {
        private JLabel lbl_Description;
        private JTextField txt_Input;
        private JButton btn_Submit;
        private String variable;

        public Option(String Description, String variable) {
            lbl_Description = new JLabel(Description);
            txt_Input = new JTextField();
            btn_Submit = new JButton("Set");
            this.variable = variable;

            btn_Submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int value = Integer.parseInt(txt_Input.getText());
                    JOptionPane.showMessageDialog(new JFrame(), txt_Input.getText());
                    setValue(value);
                }
            });
        }
        public void display() {
            add(lbl_Description);
            add(txt_Input);
            add(btn_Submit);
        }

        //Roep de juiste setter aan aan de hand van de in de constructor meegegeven variabele "variabele"
        public void setValue(int value) {
            switch(variable) {
                case "enterSpeed":
                    simulator.setEnterSpeed(value);
                    break;
                case "exitSpeed":
                    simulator.setExitSpeed(value);
                    break;
            }
        }
    }
}

