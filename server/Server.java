import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 12345;
    private static ArrayList<Player> players = new ArrayList<>();

    private static Jeu partie;

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
                if(players.size() == 1){
                    System.out.println("1 joueur connecté");

                    output.println("1 joueur connecté, en attente d'un autre joueur...");
                }else if(players.size() == 2){
                    System.out.println("2 joueurs connectés");

                    for(Player p : players) {
                        p.getOutput().println("2 joueurs connectés, la partie peut commencer !");
                    }

                    String[] arguments = {"../cartes/timeline.txt", "../cartes/timeline2.txt"};
                    Server.partie = new Jeu(players, arguments);

                    Server.partie.initialisationPartie(5);
                }else if(players.size() > 2){
                    System.out.println("Trop de joueurs connectés");

                    // Retirer le joueur de la liste
                    players.remove(player);
                    System.out.println("Joueur retiré : " + pseudo);

                    output.println("Trop de joueurs connectés, veuillez réessayer plus tard.");

                    // Fermer la connexion
                    clientSocket.close();
                    System.out.println("Connexion fermée avec : " + clientSocket.getInetAddress());
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
                // Message : "play:[id],[nbCarte],[pos]"
                if(message.startsWith("play:")) {
                    message = message.substring(5);
                    int id = Integer.parseInt(message.split(",")[0]);
                    int nbCarte = Integer.parseInt(message.split(",")[1]);
                    int pos = Integer.parseInt(message.split(",")[2]);

                    if(id == Server.partie.getTour()) {
                        System.out.println(Server.partie.getPlayer(id).getMain().getCarte(nbCarte));
                        System.out.println(Server.partie.getFrise());
                        System.out.println(pos);
                        
                        Server.partie.getFrise().insererCarteApres(Server.partie.getPlayer(id).getMain().getCarte(nbCarte), pos);

                        System.out.println(Server.partie.getFrise());
                        System.out.println(Server.partie.getPlayer(id).getMain());
                    }else{
                        System.out.println("Ce n'est pas à vous de jouer !");
                    }
                }



                if("exit".equalsIgnoreCase(message)) {
                    System.out.println(player.getPseudo() + " s'est déconnecté.");
                    players.remove(player);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le joueur " + player.getPseudo());
            players.remove(player);
        }
    }
}
