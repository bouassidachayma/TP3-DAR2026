package main.java.clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientMT_3_1 {
    // "localhost" for local tests; use IP (e.g., "192.168.56.1") for network tests
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(IP_SERVEUR), PORT), 5000);
            System.out.println("Je suis un client connecté");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            Scanner keyboard = new Scanner(System.in);

            // Message de bienvenue contenant le numéro d'ordre
            System.out.println("Serveur : " + br.readLine());

            while (true) {
                System.out.print("Message (tapez 'quit' pour arrêter) : ");
                String message = keyboard.nextLine();
                pw.println(message);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }
                System.out.println("Serveur : " + br.readLine());
            }

            keyboard.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}