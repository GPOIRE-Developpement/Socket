public class Score {
    private String nomJoueur;
    private int score;

    public Score(String nomJoueur, int score) {
        this.nomJoueur = nomJoueur;
        this.score = score;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return nomJoueur + " : " + score;
    }
}
