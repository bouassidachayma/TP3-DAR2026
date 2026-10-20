package main.java.serverPackage;

import main.java.commonPackage.Operation;
import java.io.*;
import java.net.Socket;

public class ClientHandler_3_2 implements Runnable {
    private final Socket socket;
    private final int clientId;
    // Compteur global partagé par tous les threads
    private static int compteurGlobal = 0;
    // Objet verrou pour la synchronisation
    private static final Object lock = new Object();

    public ClientHandler_3_2(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            // Ordre important : ObjectOutputStream d'abord !
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Envoyer l'ID au client
            oos.writeInt(clientId);
            oos.flush();

            Object recu;
            while ((recu = ois.readObject()) != null) {
                if (recu instanceof Operation) {
                    Operation op = (Operation) recu;

                    // Effectuer le calcul
                    String reponse = calculer(op.getOperande1(), op.getOperateur(), op.getOperande2());

                    // Envoyer la réponse au client
                    oos.writeObject(reponse);
                    oos.flush();

                    // --- SECTION CRITIQUE (SYNCHRONISÉE) ---
                    synchronized (lock) {
                        compteurGlobal++;
                        System.out.println("[Client n°" + clientId + "] Opération : "
                                + op.getOperande1() + " " + op.getOperateur() + " " + op.getOperande2()
                                + " => " + reponse);
                        System.out.println("    >>> Nombre total d'opérations traitées : " + compteurGlobal);
                    }

                } else if (recu instanceof String && recu.equals("FIN")) {
                    break;
                }
            }

            System.out.println("Client n°" + clientId + " s'est déconnecté.");
            socket.close();

        } catch (Exception e) {
            System.out.println("Déconnexion du client n°" + clientId);
        }
    }
    private String calculer(double a, char operateur, double b) {
        double r;
        switch (operateur) {
            case '+': r = a + b; break;
            case '-': r = a - b; break;
            case '*': r = a * b; break;
            case '/':
                if (b == 0) return "Erreur : division par zéro impossible";
                r = a / b;
                break;
            default: return "Erreur : opérateur inconnu";
        }
        return a + " " + operateur + " " + b + " = " + r;
    }
}