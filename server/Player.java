import java.io.*;

public class Player {
    private String pseudo;
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
}
