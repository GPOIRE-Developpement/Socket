public class Frise {
    private Paquet cartes;

    public Frise(String message) {
        this.cartes = new Paquet();

        String[] cartesList = message.substring(6).split(";");
        for(int i = 0; i < cartesList.length; i++){
            String[] parts = cartesList[i].split(",");
            int date = Integer.parseInt(parts[0]);
            String nom = parts[1];

            this.cartes.ajouterCarteFin(new Carte(date, nom, true));
        }
    }

    public Paquet getPaquet() {
        return this.cartes;
    }

    public void ajouterCarteTrie(Carte c) {
        int i = 0;
    
        while (i < cartes.getNbCartes() && c.getDate() > cartes.getCarte(i).getDate()) {
            i++;
        }
    
        if (i == 0) {
            cartes.ajouterCarteDebut(c);
        } else {
            cartes.ajouterCarte(c, i);
        }
    }
    

    public boolean verifierCarteApres(Carte c, int p) {
        if (p < 0 || p >= cartes.getNbCartes()) {
            return false;
        }

        if(p == 0 && this.cartes.getNbCartes() == 0) return true;

        Carte cartePrecedente = cartes.getCarte(p - 1);
        Carte carteSuivante = null;
        
        if (p+1 < cartes.getNbCartes()) {
            carteSuivante = cartes.getCarte(p);
        }

        if (carteSuivante == null) {
            return c.getDate() > cartePrecedente.getDate();
        } else {
            return c.getDate() > cartePrecedente.getDate() && c.getDate() < carteSuivante.getDate();
        }
    }


    public boolean insererCarteApres(Carte c, int p) {
        if(cartes.getNbCartes() == 0 || verifierCarteApres(c, p)) {
            ajouterCarteTrie(c);
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return cartes.toString();
    }
}