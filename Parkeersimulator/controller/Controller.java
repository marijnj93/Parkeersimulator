package Parkeersimulator.controller;
import javax.swing.*;
import Parkeersimulator.model.*;
import Parkeersimulator.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Jeronimo on 1/27/2017.
 */
public class Controller extends JPanel implements ActionListener{
    private Simulator simulator;

    private JButton btn_advance10;
    private JButton btn_advance100;
    private JButton btn_changeView;
    private Parkeergarage parkeergarage;

    private Boolean runWorkerRunning = false;

    public Controller(Simulator simulator, Parkeergarage parkeergarage)  {
        this.simulator = simulator;
        this.parkeergarage = parkeergarage;

        btn_advance10 = new JButton("Advance 10");
        btn_advance10.addActionListener(this);
        btn_advance100 = new JButton("Advance 100");
        btn_advance100.addActionListener(this);
        btn_changeView = new JButton("Change View");
        btn_changeView.addActionListener(this);
        add(btn_advance10);
        add(btn_advance100);
        add(btn_changeView);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_advance10 && runWorkerRunning == false) {
            new RunWorker(simulator, 10, this).execute();
        }
        if (e.getSource() == btn_advance100 && runWorkerRunning == false) {
            new RunWorker(simulator, 100, this).execute();
        }
        if (e.getSource() == btn_changeView) {
            parkeergarage.changeView();
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
    private Controller controller;

    public RunWorker(Simulator simulator, int steps, Controller controller) {
     this.simulator = simulator;
     this.steps = steps;
     this.controller = controller;
    }
    @Override
    protected Void doInBackground() throws Exception
    {
        controller.setWorkerRunning(true);
        simulator.run(steps);
        return null;
    }

    protected void done()
    {
        controller.setWorkerRunning(false);
    }
}