package Serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {
    static final int PORT = 8001;

    static List<ClientProcess> clients = new ArrayList<>();

    public static void main(String[] args) {

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(PORT);

            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                ClientProcess threadedClient = new ClientProcess(socket);

                clients.add(threadedClient);

                Thread thread = new Thread(threadedClient);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
