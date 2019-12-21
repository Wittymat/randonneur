import java.awt.*;
import javax.swing.JPanel;

public class Simulation extends JPanel {

    private static final long serialVersionUID = 1L;
    Controler controler;
    Model model;
    int size = 10;
    int taille;
    Color[] couleurs;
    boolean play = false;

    public Simulation(Controler c, Model m) {
        this.setBackground(Color.WHITE);
        this.model = m;
        this.setPreferredSize(new Dimension(size * model.taille, size * model.taille));
        this.controler = c;
        this.taille = model.taille;
        this.couleurs = new Color[] { Color.BLACK, Color.GREEN, Color.RED, Color.WHITE };

    }

    public void paintComponent(Graphics g) {
        this.paintGrid(g);
        if (play){
            try {
                this.paintAgent(g);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void paintGrid(Graphics g){

        for (int x = 0; x<this.taille; x++){
            for (int y = 0; y < this.taille; y ++){
                int n = this.model.map[x][y];
                g.setColor(couleurs[n]);
                g.fillRect(x*size, y*size, size, size);
            }
        }
    }

    public void paintAgent(Graphics g) throws InterruptedException {
        this.play = false;
        Gene best = this.model.getBest();
        g.setColor(new Color(100,100,200,100));
        int xa = 0;
        int ya = 0;

        // On va deplacer l'agent en animé
        for (int i = 0; i < best.sequence.length()-1; i +=2){
            // System.out.println(" boucle " + i);
            int u = i+1;
            char direction = best.sequence.charAt(u);

            for (int j = 0; j < best.sequence.charAt(i); j++){
                
                if (direction == '1'){
                    ya -= 1;
                }
                else if (direction == '2'){
                    xa += 1;
                }else if (direction == '3'){
                    ya ++;
                }else if (direction == '4'){
                    xa --;
                }
                g.fillRect(xa*size, ya*size, size, size);
            }

        }

        play = false; // A LA FIN ON ARRETE L'ANIMATION
    }
}