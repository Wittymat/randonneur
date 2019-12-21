import java.awt.event.*;
import javax.swing.event.*;

public class Controler implements ActionListener,ChangeListener {

    Simulation simulation;
    Model model;
    Fenetre view;
    int nbNext = 5;


    public Controler(){

    }

    public void actionPerformed(ActionEvent e){

        switch(e.getActionCommand()){
            case "next":
            for (int i = 0; i < this.nbNext; i++){
            this.model.next();}
            break;
            case "play":
            this.simulation.play = !this.simulation.play;
            this.simulation.repaint();
            break;
            case "restart":
            this.model.restart();
            this.simulation.play = true;
            this.simulation.repaint();
            break;
            case "scores":
            this.model.afficherScores();
            break;
        }

    }

    public void stateChanged(ChangeEvent e){

    }

    public void setModel(Model m){
        this.model = m;
    }

    public void setView(Fenetre f){
        this.view = f;
    }


}