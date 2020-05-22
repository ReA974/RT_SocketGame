package tea.Client;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Olivier Francou
 * @author Erwan Thibaut
 */

public class TPClient extends Frame {

    static int number = 1;

    byte[] etat = new byte[2 * 10 * 10];

    int team;
    int x;
    int y;
    int numero;
    public int port = 8000;

    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    TPPanel tpPanel;
    TPCanvas tpCanvas;
    Timer timer;
    public Socket clientSocket;

    //position des x et y voulues
    int xvoulu;
    int yvoulu;

    //Chaine de caractère à envoyer au serveur pour bouger
    String send;

    //Envoi des infos au serveur
    Thread toSend;

    //Reçois des infos du serveur
    Thread toReceive;

    /**
     * Constructeur
     */
    public TPClient(int team, int x, int y) {
        try {
            this.clientSocket = new Socket("127.0.0.1", port);
        }
        catch (IOException e) {
            e.printStackTrace(); //affiche l'erreur
        }

        setLayout(new BorderLayout());
        tpPanel = new TPPanel(this);
        add("North", tpPanel);
        tpCanvas = new TPCanvas(this.etat);
        add("Center", tpCanvas);

        timer = new Timer();
        timer.schedule(new MyTimerTask(), 500, 500);

        this.numero = number;
        this.team = team;

        this.x = x;
        this.y = y;
        this.xvoulu = x;
        this.yvoulu = y;

        this.number += 1; //permet l'incrémentation du numéro du joueur
        this.send = this.x + ";" + this.y + ";" + xvoulu + ";" + yvoulu + ";" + this.team + ";" + this.numero;
    }

    /**
     * Action vers droit
     */
    public synchronized void droit() {
        System.out.println("Droit");

        this.xvoulu = this.x + 1; //permet de déplacer le jeton

        this.send = this.x + ";" + this.y + ";" + this.xvoulu + ";" + this.yvoulu + ";" + this.team + ";" + this.numero;
        synchronized (this.toSend) {
            toSend.notify(); //pour le toSend.wait() de toSend()
        }
        refresh();

    }

    /**
     * Action vers gauche
     */
    public synchronized void gauche() {
        System.out.println("Gauche");

        this.xvoulu = this.x - 1; //permet de déplacer le jeton

        this.send = this.x + ";" + this.y + ";" + this.xvoulu + ";" + this.yvoulu + ";" + this.team + ";" + this.numero;
        synchronized (this.toSend) {
            toSend.notify(); //pour le toSend.wait() de toSend()
        }
        refresh();
    }

    /**
     * Action vers Haut
     */
    public synchronized void haut() {
        System.out.println("Haut");

        this.yvoulu = this.y - 1; //permet de déplacer le jeton

        this.send = this.x + ";" + this.y + ";" + this.xvoulu + ";" + this.yvoulu + ";" + this.team + ";" + this.numero;
        synchronized (this.toSend) {
            toSend.notify(); //pour le toSend.wait() de toSend()
        }
        refresh();
    }

    /**
     * Action vers bas
     */
    public synchronized void bas() {
        System.out.println("Bas");

        this.yvoulu = this.y + 1; //permet de déplacer le jeton

        this.send = this.x + ";" + this.y + ";" + this.xvoulu + ";" + this.yvoulu + ";" + this.team + ";" + this.numero;
        synchronized (this.toSend) {
            toSend.notify(); //pour le toSend.wait() de toSend()
        }
        refresh();

    }

    /**
     * Pour rafraichir la situation
     */
    public synchronized void refresh() {
        tpCanvas.repaint();
    }

    /**
     * Initialisations
     */
    public synchronized void minit(int team, int x, int y) {
        int position = (y * 10 + x) * 2;
        this.etat[position] = 1;
        if (team == 1) {
            this.etat[position + 1] = 1;
        }
        //lancement des threads d'envoi et de reception qui tourneront en continu
        toSend();
        toReceive();
    }

    public synchronized void toSend() {
        toSend = new Thread(new Runnable() {
            public void run() {
                synchronized (toSend) {
                    try {
                        out = new DataOutputStream(clientSocket.getOutputStream());
                        while (true) {
                            out.writeUTF(send);
                            out.flush();
                            try {
                                toSend.wait(); //attente d'action
                            }
                            catch (InterruptedException e) {
                                e.printStackTrace(); //affiche l'erreur
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
            public void run() {
                try {
                    int count = 0;
                    in = new DataInputStream(clientSocket.getInputStream());
                    byte[] actuel = new byte[2 * 10 * 10 + 1];
                    in.read(actuel); //pour voir les changements réalisés
                    while (true) {
                        int dernierByte = actuel[2 * 10 * 10];
                        if (dernierByte == 1) { // mise à jour si position valide
                            // check le dernier byte
                            // byte = 1 -> possible
                            // byte = 0 -> impossible
                            x = xvoulu;
                            y = yvoulu;
                        } else {
                            xvoulu = x;
                            yvoulu = y;
                        }
                        for (int i = 0; i < etat.length; i++) {
                            etat[i] = actuel[i];
                        }
                        count++;

                        in.read(actuel);

                        refresh();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace(); //affiche l'erreur
                }
            }
        });
        toReceive.start();

    }

    /**
     * Pour rafraichir
     */
    class MyTimerTask extends TimerTask {
        public void run() {
            refresh();
        }
    }
}
