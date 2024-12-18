import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[36m";

    private static String serverAddress = "127.0.0.1";
    private static int port = 12345;

    private static PrintWriter output;
    private static int id;
    private static Main main;
    private static Frise frise;

    private static Scanner sc = new Scanner(System.in);

    private static void printColorer(String str, String color) {
        System.out.println(color + str + ANSI_RESET);
    }

    public static void jouer(){
        System.out.println("Choisir quelle carte souhaitez-vous jouer !");

        int nbCarte = sc.nextInt();;
        while (nbCarte < 0 || nbCarte >= Client.main.getNbCartes()) {
            nbCarte = sc.nextInt();;
        }

        System.out.println("Vous avez choisit la carte " + Client.main.getCarte(nbCarte).toString());

        System.out.println("Choisir la position où vous souhaitez jouer la carte ");

        int pos = sc.nextInt();
        while(pos < -1 || pos > Client.frise.getPaquet().getNbCartes()) {
            System.out.println("Position invalide, veuillez choisir une autre !");
            pos = sc.nextInt();
        }

        if(pos == -1) {
            System.out.println("Vous allez jouer la carte avant :" + Client.frise.getPaquet().getCarte(0).toString());
        }else if(pos == Client.frise.getPaquet().getNbCartes()-1){
            System.out.println("Vous allez jouer la carte après :" + Client.frise.getPaquet().getCarte(Client.frise.getPaquet().getNbCartes()-1).toString());
        }else{
            System.out.println("Vous allez jouer la carte entre :" + Client.frise.getPaquet().getCarte(pos-1).toString() + "et" + Client.frise.getPaquet().getCarte(pos).toString());
        }

        Client.output.println("play:"+id+","+nbCarte+","+pos);
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(serverAddress, port)) {
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Client.output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(input.readLine());
            System.out.print("Entrez votre pseudo : ");
            String pseudo = userInput.readLine();
            output.println(pseudo);

            String message = input.readLine();  
            while(message == null || !message.equals("2 joueurs connectés, la partie peut commencer !")) {
                System.out.println(message);
                message = input.readLine();
            }

            message = input.readLine();
            while(message != null && !message.startsWith("fin:")){
                String messages = message;
                message = null;

                if(messages == null){
                    message = input.readLine();

                    break;
                };

                if(messages.startsWith("afficher:")){
                    messages = messages.substring(9);

                    printColorer(messages, ANSI_GREEN);
                }

                if(messages.startsWith("main:")){
                    Client.main = new Main(messages);

                    System.out.println("Voici votre main :");
                    printColorer(Client.main.toString(), ANSI_BLUE);
                }

                if(messages.startsWith("frise:")){
                    Client.frise = new Frise(messages);

                    System.out.println("Voici la frise :");
                    printColorer(frise.toString(), ANSI_YELLOW);
                }

                if(messages.startsWith("start:")){
                    if(messages.equals("start:0")){
                        printColorer("C'est à toi de jouer !", ANSI_RED);

                        Client.jouer();
                    }else{
                        printColorer("Merci de patienter le temps que l'autre joueur joue !", ANSI_RED);
                    }
                }

                message = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
