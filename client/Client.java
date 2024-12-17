import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(input.readLine()); // Message de bienvenue
            System.out.print("Entrez votre pseudo : ");
            String pseudo = userInput.readLine();
            output.println(pseudo); // Envoie du pseudo

            // Dialogue avec le serveur
            String serverMessage;
            while (true) {
                System.out.print("Votre message : ");
                String message = userInput.readLine();
                output.println(message);

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                serverMessage = input.readLine();
                System.out.println("Serveur : " + serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
