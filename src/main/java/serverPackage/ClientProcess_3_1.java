package main.java.serverPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess_3_1 extends Thread {
    private final Socket socket;
    private final int numero;

    public ClientProcess_3_1(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // c) Envoyer à chaque client son numéro d'ordre
            pw.println("Bienvenue, vous êtes le client n°" + numero);

            String message;
            // Lire les messages tant que le client ne dit pas "quit"
            while ((message = br.readLine()) != null) {
                System.out.println("[Client n°" + numero + "] dit : " + message);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                // Renvoyer une confirmation au client
                pw.println("Message bien reçu par le serveur (Client n°" + numero + ")");
            }

            System.out.println("Client n°" + numero + " s'est déconnecté.");
            socket.close();

        } catch (IOException e) {
            System.out.println("Déconnexion du client n°" + numero);
        }
    }
}