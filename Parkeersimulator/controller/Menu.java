package Parkeersimulator.controller;

import javax.swing.*;
import Parkeersimulator.main.Parkeergarage;
import Parkeersimulator.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.JOptionPane.showInputDialog;

/**
 * A class to with a menu containing buttons.
 * @author Marijn, Mark, Vincent, Bart.
 * @version 03-02-2017.
 */
public class Menu extends Controller implements ActionListener {
    private JButton btn_advance10;
    private JButton btn_advance100;
    private JButton btn_changeView;
    private JButton btn_advanceX;
    private Boolean runWorkerRunning = false;

    /**
     * Create a menu.
     * @param simulator, A simulator to use in the menu.
     * @param parkeergarage, A parkeergarage to use in the menu.
     */
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
        add(btn_advance10);
        add(btn_advance100);
        add(btn_advanceX);
        add(btn_changeView);
    }

    /**
     * Create an ActionEvent to use to check for actions.
     * @param e, An ActionEvent.
     */
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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Input only numbers");
            }
            new RunWorker(simulator, steps, this).execute();
        }
    }

    /**
     * Sets a boolean to check if a RunWorker is running.
     * @param bool, true if a RunWorker is running, else false.
     */
    void setWorkerRunning(Boolean bool) {
        runWorkerRunning = bool;
    }
}

// Misschien zorgen dat de threadcount niet te hoog wordt...
//SwingWorker die de simulatie (het run() process) op een andere - niet EDT - thread uitvoert zodat de GUI niet hangt.

/**
 * A class used as a RunWorker.
 */
class RunWorker extends SwingWorker<Void, Void>
{
    private Simulator simulator;
    private int steps = 0;
    private Menu menu;

    /**
     * Create a RunWorker.
     * @param simulator, A simulator to run.
     * @param steps, how many ticks/steps a simulator must run.
     * @param menu, The menu containing buttons.
     */
    RunWorker(Simulator simulator, int steps, Menu menu) {
     this.simulator = simulator;
     this.steps = steps;
     this.menu = menu;
    }

    /**
     * Runs a simulator.
     * @return null
     * @throws Exception, An exception to trow.
     */
    @Override
    protected Void doInBackground() throws Exception
    {
        menu.setWorkerRunning(true);
        simulator.run(steps);
        return null;
    }

    /**
     * Set setWorkerRunning to false, stopping a worker.
     */
    protected void done()
    {
        menu.setWorkerRunning(false);
    }
}