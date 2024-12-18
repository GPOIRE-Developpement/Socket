import java.util.Random;

/**
 * Classe Paquet
 * 
 * Représente un paquet de cartes
 * 
 * @author Guillaume & Rayane
 * @version 1.0
 */
public class Paquet {
    private Carte[] cartes;

    /**
     * Construteur par défaut
     */
    public Paquet(){
        cartes = new Carte[0];
    }

    /**
     * Constructeur avec un tableau de cartes
     */
    public Paquet(Carte[] c){
        this.cartes = c;
    }

    /**
     * Constructeur avec un nom de fichier
     * 
     * @param nomFichier le nom du fichier à lire
     */
    public Paquet(String nomFichier){
        String[] lignes = new LectureFichier(nomFichier).lireFichier();

        this.cartes = new Carte[lignes.length];

        for (int i = 0; i < lignes.length; i++) {
            this.cartes[i] = new Carte(lignes[i]);
        }
    }

    /**
     * Retourne le nombre de cartes dans le paquet
     * 
     * @return le nombre de cartes dans le paquet
     */
    public int getNbCartes(){
        return this.cartes.length;
    }

    /**
     * Retourne vrai si la place est en dehors du tableau
     * 
     * @param place la place à vérifier
     * @return vrai si la place est en dehors du tableau
     */
    private boolean isPlaceOutOfBound(int place){
        return place < 0 || place >= this.getNbCartes();
    }

    /**
     * Retourne la carte à la place donnée
     * 
     * @param place la place de la carte à retourner
     * @return la carte à la place donnée
     */
    public Carte getCarte(int place){
        if(this.isPlaceOutOfBound(place)) return null;

        return this.cartes[place];
    }

    /**
     * Ajoute une carte à la fin du paquet
     * 
     * @param c la carte à ajouter
     */
    public void ajouterCarteFin(Carte c){
        var temp = new Carte[this.getNbCartes() + 1];

        for (int i = 0; i < this.cartes.length; i++) {
            temp[i] = this.cartes[i];
        }

        temp[this.cartes.length] = c;

        this.cartes = temp;
    }

    /**
     * Retire une carte à la place donnée
     * 
     * @param place la place de la carte à retirer
     * @return la carte retirée
     */
    public Carte retirerCarte(int place){
        if(this.isPlaceOutOfBound(place)) return null;

        // Créer un tableau avec une carte de moins
        var temp = new Carte[this.cartes.length - 1];

        // Stock la carte supprimée
        Carte carteSupprimee = null;

        for (int i = 0; i < this.cartes.length ; i++) {
            // Si ce n'est pas la carte à supprimer
            if(place != i){
                // La met dans le nouveau paquet.
                // Retire 1 de l'indice si la carte à retirer l'a été (car il y a un décallage)
                temp[i - (carteSupprimee == null ? 0 : 1)] = this.cartes[i]; 
            }else{
                // Sauvegarde la carte supprimée du paquet
                carteSupprimee = this.cartes[i];
            }
        }

        this.cartes = temp;

        return carteSupprimee;
    }

    /**
     * Ajoute une carte au début du paquet
     * 
     * @param carte la carte à ajouter
     */
    public void ajouterCarteDebut(Carte carte){
        var temp = new Carte[this.cartes.length + 1];

        temp[0] = carte;

        for (int i = 0; i < this.cartes.length; i++) {
            temp[i + 1] = this.cartes[i];
        }

        this.cartes = temp;
    }

    /**
     * Ajoute une carte à la place donnée
     * 
     * @param c la carte à ajouter
     * @param place la place où ajouter la carte
     */
    public void ajouterCarte(Carte c, int place){
        if (place < 0 || place > this.cartes.length) return;

        var temp = new Carte[this.cartes.length + 1];

        for (int i = 0; i < place; i++) {
            temp[i] = this.cartes[i];
        }

        temp[place] = c;

        for (int i = place; i < this.cartes.length; i++) {
            temp[i + 1] = this.cartes[i];
        }

        this.cartes = temp;
    }

    /**
     * Retourne une représentation du paquet
     * 
     * @return une représentation du paquet
     */
    public String toString(){
        StringBuffer str = new StringBuffer();

        str.append("--------------------------\n");

        if(this.getNbCartes() == 0) str.append("Aucune carte dans le paquet\n");
        else{
            for (int i = 0; i < cartes.length; i++) {
                str.append(i + ". " + this.cartes[i].toString() + "\n");
            }
    
        }

        str.append("--------------------------");

        return str.toString();
    }
    
    /**
     * Pioche une carte au hasard dans le paquet
     * 
     * @return la carte piochée
     */
    public Carte piocherHasard(){
        if(this.getNbCartes() == 0) return null;

        Random rand = new Random();
        int place = rand.nextInt(this.getNbCartes());

        Carte c = this.getCarte(place);

        this.retirerCarte(place);

        return c;
    }


}
