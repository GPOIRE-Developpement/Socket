import java.util.Scanner;
import java.util.ArrayList;

public class Jeu {
Scanner sc = new Scanner(System.in);
    // Liste de joueurs connectés
    private ArrayList<Player> players;

    // Cartes restantes dans la pioche
    private Paquet pioche;
    // Frise actuelle
    private Frise frise;
    // Score du joueur
    private int score;
    private String nomJoueur;

    private HighScore highScores;

    // Sauvgearde des args pour rejouer
    private String[] cheminFichierTimeline;
    private int tMain;

    private int tour;

    // Couleurs pour l'affichage
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[36m";


    public Jeu(ArrayList<Player> players, String[] cheminFichierTimeline) {
        this.players = players;
        this.cheminFichierTimeline = cheminFichierTimeline;
        //this.highScores = new HighScore();
    }

    private void printColorer(String str, String color) {
        System.out.println(color + str + ANSI_RESET);
    }

    public void initialisationPartie(int nbreCarte){
        // Initialisation des attributs
        if(cheminFichierTimeline[0] == null) return;

        // Initialisation de la pioche
        this.pioche = new Paquet(cheminFichierTimeline[0]);
        for(int i = 1; i < this.cheminFichierTimeline.length; i++){
            Paquet p = new Paquet(this.cheminFichierTimeline[i]);

            for(int c = 0; c < p.getNbCartes(); c++){
                this.pioche.ajouterCarteFin(p.getCarte(c));
            }
        }

        // Définit la frise qu'il met vide 
        this.frise = new Frise();
        this.score = 0;

        // Initialisation de la taille de la main du joueur
        this.tMain = nbreCarte;
        
        printColorer("Le nombre de carte a été définie à " + tMain + "\n", ANSI_BLUE);

        for(Player p : players){
            p.setMain(tMain, this.pioche);
        }
        

        Carte cF = this.pioche.piocherHasard();
        this.frise.ajouterCarteTrie(cF);
        cF.retourner();

        envoyerToutesInformations(0);

        printColorer("Lancement de la partie", ANSI_GREEN);
    }

    public void envoyerToutesInformations(int playerNext){
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);

            // Envoyer sa main au joueur 
            Paquet main = p.getMain();
            String message = "main:";
            for(int j = 0; j < main.getNbCartes(); j++){
                message = message+main.getCarte(j).getDate()+","+main.getCarte(j).getNom()+";";
            }

            p.getOutput().println(message);

            // Envoyer la frise
            String frise = "frise:";
            for(int j = 0; j < this.frise.getPaquet().getNbCartes(); j++){
                frise = frise+this.frise.getPaquet().getCarte(j).getDate()+","+this.frise.getPaquet().getCarte(j).getNom()+";";
            }

            p.getOutput().println(frise);

            this.tour = playerNext;
            
            if(i == playerNext){
                p.getOutput().println("start:1");
            }else{
                p.getOutput().println("start:0");
            }
        }


    }

    public void executerTour(){
        
    }

    public int partieTerminee(){
        while(pioche.getNbCartes() > 0 && players.get(0).getMain().getNbCartes() > 0 && players.get(1).getMain().getNbCartes() > 0) {
            return 0
        }

        if(pioche.getNbCartes() == 0) {
            return 1

            //this.players.get(0).getOutput().println("Vous avez perdu la partie car la pioche est vide !");
            //this.players.get(1).getOutput().println("Vous avez perdu la partie car la pioche est vide !");
        }else{
            if(players.get(0).getMain().getNbCartes() == 0){
                return 2

                //this.players.get(0).getOutput().println("Vous avez gagné la partie !");
                //this.players.get(1).getOutput().println("Vous avez perdu la partie !");
            }else{
                return 3

                //this.players.get(0).getOutput().println("Vous avez perdu la partie !");
                //this.players.get(1).getOutput().println("Vous avez gagné la partie !");
            }
        }
    }

    public int getTour(){
        return this.tour;
    }

    public void setTour(int t){
        this.tour = t;
    }


    /*
    private int choisirCarte() {
        printColorer("\nquelle carte de votre main ?", ANSI_YELLOW);
        Carte carteJouee = null;
        int nbCarte = -1;
        while (carteJouee == null) {
            nbCarte = sc.nextInt();
            carteJouee = this.main.getCarte(nbCarte);
        }
        return nbCarte;
    }

    private void attendre(int milliseconde) {
        try {
            Thread.sleep(milliseconde);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





    private int choisirPlace() {
        printColorer("Derriere quelle carte de la frise ? (-1 pour avant la première carte)", ANSI_YELLOW);
        int placeCarte = -2;

        while (placeCarte < -1 || placeCarte >= this.frise.getPaquet().getNbCartes()) {
            placeCarte = sc.nextInt();
        }

        return placeCarte;
    }


    private void afficherBorneCarte(int placeCarte) {
        printColorer("Vous avec choisi de placer la carte", ANSI_GREEN);
        // Si on place la carte au tout début de la frise
        if (placeCarte == -1) {
            printColorer("Avant ....\n     - " + this.frise.getPaquet().getCarte(0), ANSI_GREEN);
        }
        // Si on place la carte à la fin de la frise
        else if (placeCarte == this.frise.getPaquet().getNbCartes() - 1) {
            printColorer("Après ....\n     - " + this.frise.getPaquet().getCarte(placeCarte), ANSI_GREEN);
        }
        //Si elle est placée entre deux cartes
        else if (placeCarte >= 0 && placeCarte < this.frise.getPaquet().getNbCartes()) {
            printColorer("Entre ....\n     - " + this.frise.getPaquet().getCarte(placeCarte) + "\n     - " + this.frise.getPaquet().getCarte(placeCarte + 1), ANSI_GREEN);
        } else {
            printColorer("Erreur dans le choix de la place de la carte", ANSI_RED);
        }
    }


    private void verifierEmplacement(Carte carteJouee, int placeCarte, int nbCarteChoisi){
        boolean resultat = this.frise.insererCarteApres(carteJouee, placeCarte);

        if (resultat) {
            printColorer("\n !!! Une carte de placee !!!", ANSI_GREEN);

            // Retirer la carte de la main du joueur
            this.main.retirerCarte(nbCarteChoisi);
        } else {
            printColorer("\n !!! La carte n'est pas au bon endroit !!!", ANSI_RED);

            // Retirer la carte de la main du joueur
            this.main.retirerCarte(nbCarteChoisi);
            // Ajoute une nouvelle carte à la fin de la main du joueur
            Carte c = this.pioche.piocherHasard();
            this.main.ajouterCarteFin(c);
        }
    }


    private void executerTour() {
        afficherEtatPartie();

        int nbCarteChoisi = choisirCarte();
        Carte carteJouee = this.main.getCarte(nbCarteChoisi);

        printColorer("Vous avez choisi de jouer la carte :", ANSI_GREEN);
        printColorer(carteJouee.toString() + "\n", ANSI_GREEN);

        int placeCarte = choisirPlace();

        attendre(400);

        afficherBorneCarte(placeCarte);

        attendre(2000);

        printColorer("\nVérifions si la carte peut être placée à cet endroit", ANSI_YELLOW);

        attendre(750);

        // Révèle la date de la carte
        carteJouee.retourner();

        printColorer(carteJouee.toString(), ANSI_YELLOW);

        attendre(1250);

        verifierEmplacement(carteJouee, placeCarte, nbCarteChoisi);

        score++; // Un coup est passé, le score augmente

        attendre(2000);
    }


    private void afficherEtatPartie() {
        StringBuffer str = new StringBuffer();
        str.append("--------------------------\n");
        str.append("frise\n");
        str.append(this.frise.toString() + "\n\n");

        str.append("--------------------------\n");
        str.append("main du joueur\n");
        str.append(this.main.toString());

        printColorer(str.toString(), ANSI_BLUE);
    }


    public String[] recupererCartes(){
        String[] cartes = new String[2];
        cartes[0] = this.frise.getPaquet().toString();
        cartes[1] = this.main.toString();
        return cartes;
    }


    public void commencerJeu() {
        while (pioche.getNbCartes() > 0 && main.getNbCartes() > 0) {
            executerTour();
        }

        if (pioche.getNbCartes() == 0) {
            printColorer("Vous avez perdu la partie car la pioche est vide !", ANSI_RED);
        } else {
            printColorer("Vous avez gagné la partie !", ANSI_GREEN);
        }

        // Affichage du score
        printColorer("\nVotre score est de : " + score, ANSI_BLUE);

        // Sauvegarde du score dans les highscores
        highScores.ajouterScore(tMain, new Score(nomJoueur, score));

        // Affichage des highscores
        printColorer("\nMeilleurs scores : \n" + highScores.toString(), ANSI_BLUE);

        printColorer("\nVoulez-vous rejouer ? (o/n)", ANSI_YELLOW);
        String rejouer = sc.next();

        if (rejouer.equals("o")) {
            commencerJeu();
        } else {
            printColorer("Merci d'avoir joué !", ANSI_YELLOW);
        }
    }*/
}
