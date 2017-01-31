package Parkeersimulator.controller;

import Parkeersimulator.Parkeergarage;
import Parkeersimulator.model.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jeronimo on 1/31/2017.
 */
public class Settings extends Controller {
    private JTextField txt_Test1;
    private JButton btn_Test1;


    public Settings(Simulator simulator, Parkeergarage parkeergarage )  {
        super(simulator, parkeergarage);

        setLayout(new GridLayout(5, 2));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 100));

        txt_Test1 = new JTextField();
        txt_Test1.setSize(new Dimension(20, 10));

        btn_Test1 = new JButton("Button");

        add(txt_Test1);
        add(btn_Test1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return The dimension of the GUI
     */
    public Dimension getPreferredSize() {
        repaint();
        return new Dimension(1200, 500);
    }
}
