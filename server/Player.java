import java.io.*;

public class Player {
    private String pseudo;
    private Paquet main;
    private PrintWriter output;

    public Player(String p, PrintWriter o){
        this.pseudo = p;
        this.output = o;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public PrintWriter getOutput(){
        return this.output;
    }

    public void setPseudo(String p){
        this.pseudo = p;
    }

    public void setOutput(PrintWriter o){
        this.output = o;
    }

    public void setMain(int tMain, Paquet pioche){
        this.main = new Paquet();
        for (int i = 0; i < tMain; i++) {
            Carte c = pioche.piocherHasard();
            if (c == null) break;

            this.main.ajouterCarteFin(c);
        }
    }

    public Paquet getMain(){
        return this.main;
    }
}
