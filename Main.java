import java.awt.*;

public class Main{

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){

            public void run(){
                Controler controler = new Controler();
                Model model = new Model();
                Fenetre fenetre = new Fenetre(controler, model);
                controler.setModel(model);
                controler.setView(fenetre);
            }

        });
    
    }

}