import java.util.Random;


public class Model{
    Random random = new Random();
    /**
     * TAILLE DE LA MAP, CARRE DE TAILLE
     */
    int taille = 100;
    int tailleGene = taille * 5;
    /**
     * Le terrain est représenté par des entiers\n
     * 0 = CASE VIDE
     * 1 = FOOD
     * 2 = TRAP
     * 3 = MUR
     */
    int[][] map = new int[taille][taille];
    float foodRatio = 0.1f;
    float trapRatio = 0.05f;

    int foodGain = 30;
    int trapGain = -30;
    int voidGain = 0;

    Gene[] population;
    int popSize = 100;

    float mutationRate = 0.05f;
    

    public Model(){
        this.population = new Gene[popSize];
        this.initMap();
        this.initPop();
        this.trierPop();
        //this.afficherScores();
    }
    public void afficherScores(){
        System.out.println("\n\nSCORES\n\n");
        for (int i = 0; i < popSize/5; i ++){
            if (population[i].score != population[i+1].score){
                System.out.println("score : " + population[i].score);
            }
        }
    }

    // METHODE QUI GENERE UN TERRAIN
    public void initMap(){

        for (int i = 0; i < (taille*taille)*foodRatio; i++){
            this.dropRandom(1);

        }
        for (int i = 0; i < (taille*taille)*trapRatio; i++){
            this.dropRandom(2);
        }
        // on met la premier case (0,0) avec rien dessus
        map[0][0] = 0;
    }

    public void dropRandom(int n){

        int x = random.nextInt(taille);
        int y = random.nextInt(taille);
        this.map[x][y] = n; 
    }

    public void initPop(){
        for (int i = 0; i < this.popSize; i++){
            population[i] = new Gene(tailleGene);
        }
    }

    public int evaluerGene(Gene g){

        // ON fait une copie de la map car on va suprimer des trucs et les remettre après
        int[][] mapCopie = copie(map);
        int score = 0;
        int x = 0;
        int y = 0;
        // On parcours toute la séquence 2 par 2 pour avoir le nombre de case seulement, la case suivante sera la direction
        for (int i = 0; i < g.sequence.length()-1; i+=2){
            int nbCase = g.sequence.charAt(i);
            int direction = Character.getNumericValue(g.sequence.charAt(i+1));
            // on fait parcourir chaque case d'une direction donnée
            for (int j = 0; j < nbCase; j++){
                if (direction == 1){
                    y -= 1;
                }
                else if (direction == 2){
                    x += 1;
                }else if (direction == 3){
                    y ++;
                }else if (direction == 4){
                    x --;
                }
                // evaluer
                if (inGrid(x,y)){
                    score += evaluer(x,y);
                }
            }
        }
        this.map = mapCopie;
        // avant de retourner le score on l'assigne au gene
        g.score = score;
        return score;
    }

    public boolean inGrid(int x, int y){
        return (x>=0 && y>=0 && x<taille && y<taille);
    }
    public int evaluer(int x, int y ){
        int n = map[x][y];
        if (n == 1){
            map[x][y] = 0; // ON REMPLACE PAR DU VIDE UNE FOIS QU'ON A PRIS LA NOURRITURE
            return foodGain;
        }
        else if (n == 2){
            return trapGain;
        }
        else{
            return voidGain;
        }
    }

    public void trierPop(){
        int mini;
        int minii = 0;
        int current;
        for (int i = 0; i < popSize-1; i ++){
            mini = evaluerGene(population[i]);
            minii = i;
            for (int j = i+1; j < popSize; j++){
                current = evaluerGene(population[j]);
                if (current > mini){
                    mini = current;
                    minii = j;
                }
            }
            swap(i,minii);
        }
    }
    public Gene getBest(){
        return population[0];
    }
    private void swap(int a, int b){
        Gene g = population[b];
        population[b] = population[a];
        population[a] = g;
    }

    // METHODE QUI PASSE A L'ETAPE SUIVANTE DE L'ALGORITHME
    public void next(){
        Gene[] newPop = new Gene[popSize];

        for (int i = 0; i < popSize; i++){
            // Pour le premier quart, on garde les 1/4 meilleurs
            if (i < popSize/4){
                newPop[i] = population[i];
            } else if (i>= popSize/4 && i < popSize/2){ // Pour le deuxième quart, on va faire un crossover des meilleurs et de randoms
                newPop[i] = crossover(population[i-popSize/4], new Gene(tailleGene));
            }
            else{
                newPop[i] = new Gene(tailleGene);
            }

            // SAUF POUR LES 3 PREMIERS, ON APPLIQUE DES MUTATIONS
            if (i > 2){
                newPop[i].muter(mutationRate);
            }
        }
        // ON REMPLACE L'ANCIENNE POP PAR LA NOUVELLE
        this.population = newPop;
        // ON RETRIE
        this.trierPop();
    }

    public void restart(){
        this.initPop();
        this.trierPop();
    }

    public Gene crossover(Gene a, Gene b){
        return a.croiser(b);
    }

    public int[][] copie(int[][] m){
        int[][] c = new int[m.length][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m.length; j++){
                c[i][j] = m[i][j];
            }
        }
        return c;
    }
}
