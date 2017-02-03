package Parkeersimulator.controller;

import javax.swing.*;

import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showInputDialog;

/**
 *
 * @author Marijn, Mark, Vincent, Bart,
 * @since 26-01-2017
 */
public class Menu extends Controller implements ActionListener {


    private JButton btn_advance10;
    private JButton btn_advance100;
    private JButton btn_changeView;
    private JButton btn_advanceX;
    private JButton btn_Settings;


    private Boolean runWorkerRunning = false;

    public Menu(Simulator simulator, Parkeergarage parkeergarage)  {
        super(simulator, parkeergarage);

        btn_advance10 = new JButton("Advance 10");
        btn_advance10.addActionListener(this);
        btn_advance100 = new JButton("Advance 100");
        btn_advance100.addActionListener(this);
        btn_advanceX = new JButton("Advance X");
        btn_advanceX.addActionListener(this);
        btn_changeView = new JButton("Change View");
        btn_changeView.addActionListener(this);
        btn_Settings = new JButton("Settings");
        btn_Settings.addActionListener(this);

        add(btn_advance10);
        add(btn_advance100);
        add(btn_advanceX);
        add(btn_changeView);
        add(btn_Settings);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_advance10 && !runWorkerRunning) {
            new RunWorker(simulator, 10, this).execute();
        }
        if (e.getSource() == btn_advance100 && !runWorkerRunning) {
            new RunWorker(simulator, 100, this).execute();
        }
        if (e.getSource() == btn_changeView) {
            parkeergarage.changeView();
        }
        if (e.getSource() == btn_advanceX) {
            int steps = 0;
            try {
                steps = Integer.parseInt(showInputDialog("Amount of minutes to run: "));
                new RunWorker(simulator, steps, this).execute();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Input only numbers");
            }
        }
        if (e.getSource() == btn_Settings) {
            parkeergarage.changeSettings();
        }
    }

    public void setWorkerRunning(Boolean bool) {
        runWorkerRunning = bool;
    }
}


//Misschien zorgen dat de threadcount niet te hoog wordt...

//SwingWorker die de simulatie (het run() process) op een andere - niet EDT - thread uitvoert zodat de GUI niet hangt.
class RunWorker extends SwingWorker<Void, Void>
{
    private Simulator simulator;
    private int steps = 0;
    private Menu menu;

    public RunWorker(Simulator simulator, int steps, Menu menu) {
     this.simulator = simulator;
     this.steps = steps;
     this.menu = menu;
    }
    @Override
    protected Void doInBackground() throws Exception
    {
        menu.setWorkerRunning(true);
        simulator.run(steps);
        return null;
    }

    protected void done()
    {
        menu.setWorkerRunning(false);
    }
}