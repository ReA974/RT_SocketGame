package tea.Serveur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * @author Olivier Francou
 * @author Erwan Thibaut
 */

public class TPServeur {
    static byte[] etat = new byte[2 * 10 * 10 + 1];

    Thread toSend;
    Thread toReceive;

    static ServerSocket serveurSocket;
    Socket clientSocket;
    DataInputStream in;
    DataOutputStream out;

    static final int port = 8000;

    public TPServeur() {
        try {
            clientSocket = serveurSocket.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            toSend();
            toReceive();
        }
        catch (IOException e) {
            e.printStackTrace(); //affiche l'erreur
        }
    }

    public static void main(String[] test) {
        try {
            serveurSocket = new ServerSocket(port);
            while (true) {
                TPServeur serveurJeu = new TPServeur();
            }
        }
        catch (IOException e) {
            e.printStackTrace(); //affiche l'erreur
        }
    }

    public synchronized void toSend() {
        toSend = new Thread(new Runnable() {
            public void run() {
                synchronized (toSend) {
                    try {
                        out = new DataOutputStream(clientSocket.getOutputStream());
                        while (true) {
                            byte[] etatActuel = TPServeur.etat;
                            etatActuel[200] = 0;
                            out.write(etatActuel);
                            try {
                                toSend.wait(300);
                            }
                            catch (InterruptedException ex) {
                                Logger.getLogger(TPServeur.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace(); //affiche l'erreur
                    }
                }
            }
        });
        toSend.start();
    }

    public synchronized void toReceive() {

        toReceive = new Thread(new Runnable() {
            String message;
            public void run() {
                try {
                    message = in.readUTF();
                    while (message != null) {
                        System.out.println(message);
                        String[] point = new String[6];
                        point = message.split(";");
                        //permet la libération de la place si le mouvement est valide
                        int ancienx = parseInt(point[0]);
                        int ancieny = parseInt(point[1]);
                        //permet la vérification que le mouvement est possible:
                        int xvoulu = parseInt(point[2]);
                        int yvoulu = parseInt(point[3]);
                        //récupère l'équipe pour associer la bonne couleur
                        int team = parseInt(point[4]);
                        int numero = parseInt(point[5]);

                        if (setEtat(ancienx, ancieny, xvoulu, yvoulu, team)) {
                            int victoire = 0; // On met 0 pour le moment car on a pas eu le temps de coder la méthode de vctoire
                            if (victoire != 0) { // Pas encore fini mais si victoire = 1 la team 1 gagne et si victoire = 2 la team 2 gagne
                                System.out.println("Bravo team " + victoire + " vous avez gagné");
                            }
                        }
                        out.write(TPServeur.etat);
                        message = in.readUTF();
                    }
                    in.close();
                    out.close();
                    clientSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace(); //affiche l'erreur
                }
            }
        });
        toReceive.start();
    }

    public boolean setEtat(int ancienx, int ancieny, int x, int y, int team){
        //impossible de base donc byte est initialisé à 0
        TPServeur.etat[200] = 0;

        int pos = (y * 10 + x) * 2;

        if (pos > etat.length) {  //impossible, hors jeu
            return false;
        }
        if ((x < 0 || y < 0) || (x > 9 || y > 9)) { //impossible, hors jeu
            return false;
        }
        if (TPServeur.etat[pos] == 1) { //impossible, déjà utilisé
            return false;
        }

        TPServeur.etat[pos] = 1;
        if (team == 1) {
            TPServeur.etat[pos + 1] = 1; //team 1 = 0
            //team 2 = 1
            //changement de couleur
        }
        //possible -> dernierByte = 1
        TPServeur.etat[200] = 1;
        int anciennePos = (ancieny * 10 + ancienx) * 2;
        TPServeur.etat[anciennePos] = 0; //ancienne pos devient alors libre
        TPServeur.etat[anciennePos + 1] = 0;

        return true; //possible
    }

}
