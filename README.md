# TP3 - Serveur Multi-threads (Sockets TCP)

**Étudiant :** Chayma Bouassida  
**Matière :** Développement d'applications réparties  
**Année universitaire :** 2026 – 2027

Ce dépôt contient le travail complet du TP3 : un serveur multi-thread capable de gérer plusieurs clients simultanément, avec une communication texte (Activité 3-1) puis par objets avec un compteur synchronisé (Activité 3-2).

---

# Activité 3-1 : Acceptation de plusieurs clients

## Objectif
Créer un serveur multi-thread acceptant plusieurs clients simultanément, affichant l'adresse IP et le numéro d'ordre de chaque client.

## Code Source
### ServerMT_3_1.java
![Code Server](screenshots/code_server_3_1.png)

### ClientProcess_3_1.java (Le Thread)
![Code Process](screenshots/code_process_3_1.png)

### ClientMT_3_1.java
![Code Client](screenshots/code_client_3_1.png)

## Déroulement des tests

### 1. Démarrage du serveur
Le serveur démarre et bloque sur `accept()` en attente de clients.
![Serveur attente](screenshots/server_waiting.png)

### 2. Connexion du Client 1
Le premier client se connecte et reçoit son numéro.

![Client 1](screenshots/client1_connected.png)

Le serveur détecte la connexion (IP + n°1).

![Serveur Client 1](screenshots/server_client1.png)

### 3. Connexion du Client 2
Le second client se connecte simultanément et reçoit son numéro.
![Client 2](screenshots/client2_connected.png)

Le serveur montre les deux clients connectés en parallèle.
![Serveur Client 1 et 2](screenshots/server_client1_2.png)

### 4. Échange de messages
Le Client 1 envoie un message au serveur.
![Client 1 message](screenshots/client1_message.png)

Le Client 2 envoie un message au serveur.
![Client 2 message](screenshots/client2_message.png)

Le serveur reçoit et affiche les messages des deux clients sans se bloquer.
![Serveur messages](screenshots/server_messages.png)

### 5. Test avec Telnet (Client 3)
Pour prouver que le serveur accepte n'importe quel client, on utilise Telnet.
![Commande Telnet](screenshots/telnet_cmd.png)
Le serveur envoie le message de bienvenue à Telnet.
![Telnet Bienvenue](screenshots/telnet_bienvenue.png)
Le serveur affiche la 3ème connexion (Client n°3).
![Serveur Telnet](screenshots/server_telnet.png)

## Synthèse (3-1)
Le serveur utilise une boucle `while(true)` couplée à `ServerSocket.accept()`. À chaque nouvelle connexion, un nouveau Thread (`ClientProcess_3_1` héritant de `Thread`) est instancié. Le thread principal reste donc disponible pour accepter d'autres clients. L'échange de texte se fait via `PrintWriter` et `BufferedReader`.
