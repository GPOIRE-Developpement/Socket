import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 12345;
    private static ArrayList<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Serveur en écoute sur le port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) { // Accepter les connexions en continu
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion : " + clientSocket.getInetAddress());

                // Création d'un flux de sortie pour envoyer des messages au client
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                output.println("Bienvenue sur le serveur ! Veuillez entrer votre pseudo :");

                // Lire le pseudo envoyé par le client
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String pseudo = input.readLine();

                // Ajouter le joueur à la liste
                Player player = new Player(pseudo, output);
                players.add(player);
                System.out.println("Joueur ajouté : " + pseudo);

                // Vérification si 2 joueurs ou plus sont connectés
                if(players.size() >= 2){
                    System.out.println("Prêt à jouer");
                }

                // Lancer une conversation avec le joueur dans un thread séparé
                new Thread(() -> handlePlayer(player, input)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour gérer la communication avec un joueur
    private static void handlePlayer(Player player, BufferedReader input) {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println(player.getPseudo() + " : " + message);
                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println(player.getPseudo() + " s'est déconnecté.");
                    players.remove(player); // Retirer le joueur de la liste
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le joueur " + player.getPseudo());
            players.remove(player);
        }
    }
}
