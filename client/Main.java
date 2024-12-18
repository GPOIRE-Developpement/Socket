public class Main {
    private Carte[] cartes;

    /**
     * Construteur par défaut
     */
    public Main(){
        cartes = new Carte[0];
    }

    public Main(String message){
        String[] cartesList = message.substring(5).split(";");
        this.cartes = new Carte[cartesList.length];

        for(int i = 0; i < cartesList.length; i++){
            String[] parts = cartesList[i].split(",");
            int date = Integer.parseInt(parts[0]);
            String nom = parts[1];

            this.cartes[i] = new Carte(date, nom, false);
        }
    }

    public int getNbCartes(){
        return this.cartes.length;
    }

    private boolean isPlaceOutOfBound(int place){
        return place < 0 || place >= this.getNbCartes();
    }


    public Carte getCarte(int place){
        if(this.isPlaceOutOfBound(place)) return null;

        return this.cartes[place];
    }


    public void ajouterCarteFin(Carte c){
        var temp = new Carte[this.getNbCartes() + 1];

        for (int i = 0; i < this.cartes.length; i++) {
            temp[i] = this.cartes[i];
        }

        temp[this.cartes.length] = c;

        this.cartes = temp;
    }
 
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

    public void ajouterCarteDebut(Carte carte){
        var temp = new Carte[this.cartes.length + 1];

        temp[0] = carte;

        for (int i = 0; i < this.cartes.length; i++) {
            temp[i + 1] = this.cartes[i];
        }

        this.cartes = temp;
    }

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
}
