package Parkeersimulator.controller;
import javax.swing.*;
import Parkeersimulator.model.*;
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

    private Boolean advance10 = false;

    public Controller(Simulator simulator)  {
        this.simulator = simulator;

        btn_advance10 = new JButton("Advance 10");
        btn_advance10.addActionListener(this);
        btn_advance100 = new JButton("Advance 100");
        btn_advance100.addActionListener(this);
        btn_changeView = new JButton("Change View");
        add(btn_advance10);
        add(btn_advance100);
        add(btn_changeView);


        /*btn_advance10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Stel in dat de Simulator 10 ticks vooruitgaat.
                advance10 = true;
                simulator.run(10);
                System.out.println("Advance 10");
            }
        });*/
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_advance10) {
            new RunWorker(simulator, 10).execute();
        }
        if (e.getSource() == btn_advance100) {
            new RunWorker(simulator, 100).execute();
        }
    }
}


//Misschien zorgen dat de threadcount niet te hoog wordt...

//SwingWorker die de simulatie (het run() process) op een andere - niet EDT - thread uitvoert zodat de GUI niet hangt.
class RunWorker extends SwingWorker<Void, Void>
{
    private Simulator simulator;
    private int steps = 0;
    public RunWorker(Simulator simulator, int steps) {
     this.simulator = simulator;
     this.steps = steps;
    }
    @Override
    protected Void doInBackground() throws Exception
    {
        simulator.run(steps);
        return null;
    }

    protected void done()
    {
    }
}