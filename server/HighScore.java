import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighScore {
    // Dictionnaire avec en clé la difficulté et en valeur la liste des scores
    private Map<Integer, List<Score>> scores;

    public HighScore() {
        this.scores = new HashMap<>();

        this.recupererScore();
    }

    public Map<Integer, List<Score>> getScores() {
        return scores;
    }

    public void ajouterScore(int difficulte, Score score) {
        if(score == null) return;

        if(!scores.containsKey(difficulte)) {
            scores.put(difficulte, new ArrayList<>(List.of(score)));
        } else {
            var tabScores = this.scores.get(difficulte);

            tabScores.add(score);
            tabScores.sort((s1, s2) -> s2.getScore() - s1.getScore());

            // Sauvegarde uniquement les 5 meilleurs scores
            if(tabScores.size() > 5){
                tabScores.remove(5);
            } 
        }

        this.saveScore();
    }

    private void saveScore() {
        var fichier = new EcritureFichier("highscore.txt");
        fichier.ouvrirFichier();

        for(Map.Entry<Integer, List<Score>> entry : scores.entrySet()) {
            for(Score score : entry.getValue()) {
                fichier.ecrireFichier(entry.getKey() + " " + score.getNomJoueur() + " " + score.getScore());
            }
        }

        fichier.fermerFichier();
    }

    private void recupererScore(){
        // Si le fichier highscore n'existe pas, return
        if(!new File("highscore.txt").exists()) return;

        var fichier = new LectureFichier("highscore.txt");

        String[] lignes = fichier.lireFichier();
        for(String ligne : lignes){
            String[] elements = ligne.split(" ");
            int difficulte = Integer.parseInt(elements[0]);
            String nomJoueur = elements[1];
            int score = Integer.parseInt(elements[2]);

            this.ajouterScore(difficulte, new Score(nomJoueur, score));
        }
    }
    
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Map.Entry<Integer, List<Score>> entry : scores.entrySet()) {
            str.append("Pour la difficulté ").append(entry.getKey()).append(" : \n");
            List<Score> scoreList = entry.getValue();
            for(int i = scoreList.size() - 1; i >= 0; i--) {
                str.append(scoreList.get(i)).append("\n");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
