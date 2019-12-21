import javax.swing.*;
import java.awt.*;


public class Fenetre extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Controler controler;

    public Simulation simulation;
    public JPanel panCommandes;
    public Model model;
    public int sizeX;
    public int sizeY;


    public Fenetre(Controler c, Model m){
        this.controler = c;
        this.model = m;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ON créer le panneau simulation
        this.simulation = new Simulation(c,m);
        // on assigne ce panneau au controler
        this.controler.simulation = this.simulation;



        // ON créer le panneau commandes
        this.panCommandes = new JPanel();
        // On créer le bouton next
        JButton bNext = new JButton("NEXT");
        bNext.addActionListener(this.controler);
        bNext.setActionCommand("next");
        this.panCommandes.add(bNext);

        JButton bPlay = new JButton("PLAY");
        bPlay.addActionListener(this.controler);
        bPlay.setActionCommand("play");
        this.panCommandes.add(bPlay);

        JButton bRestart = new JButton("RESTART");
        bRestart.addActionListener(this.controler);
        bRestart.setActionCommand("restart");
        this.panCommandes.add(bRestart);

        JButton bScores = new JButton("AFFICHER SCORES");
        bScores.addActionListener(this.controler);
        bScores.setActionCommand("scores");
        this.panCommandes.add(bScores);
        // LAYOUT
        this.setLayout(new FlowLayout());

        this.add(simulation);
        this.add(panCommandes);
        this.pack();
        
    }
}