public class MainCarte {
    public static void main(String[] args) {
        String[] lignes = new LectureFichier("../cartes/timeline.txt").lireFichier();

        for (String ligne : lignes) {
            Carte c = new Carte(ligne);
            System.out.println(c);
            c.retourner();
            System.out.println(c);
        }
    }
}
