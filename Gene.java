import java.util.Random;

public class Gene {

    Random random = new Random();
    int score;
    String sequence = "";
    int[] alphabet = {0,1,2,3}; // 0 = rien faire, HAUT, DROITE, BAS, GAUCHE
    int lMin = 0;
    int lMax;
    int longueur;

    public Gene(int l){
        this.score = 0;
        this.longueur = l;
        this.lMax = 10;
        this.init();
    }

    public void init(){
        for (int i = 0; i < longueur; i ++){
            sequence += random.nextInt(lMax) + lMin;
            sequence += alphabet[random.nextInt(alphabet.length)]; // on ajoute une lettre de l'alphabet au hasard
        }
    }

    public Gene croiser(Gene autre){
        String s = "";
        int a;
        int ratio = 20; // On veut 80% du meilleur, 20% d'un nouveau
        // on croise par 2 car distance et direction sont liées
        // Au début on va faire du 50/50
        for (int i = 0; i < sequence.length(); i+=2){
            a = random.nextInt(101);
            if (a<ratio){
                s+=autre.sequence.charAt(i);
                s+=autre.sequence.charAt(i+1);
            }
            else{
                s+=this.sequence.charAt(i);
                s+=this.sequence.charAt(i+1);
            }
        }
        autre.sequence = s;
        return autre;
    }

    public void muter(float mutationRate){
        String s = "";
        float r = mutationRate * 100;
        int a;
        for (int i = 0; i < sequence.length(); i++){
            a = random.nextInt(100*100+1);
            if (a<r){
                if (i%2 == 1){
                    s += alphabet[random.nextInt(alphabet.length)];
                }
                else{
                    s += random.nextInt(lMax);
                }
            }
            else{
                s += this.sequence.charAt(i);
            }
        }
        this.sequence = s;
    }
}