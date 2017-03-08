package jimmy.gg.flashingnumbers.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by ggjimmy on 3/7/17.
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ClientHandler> clients;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            System.out.println("connected client");
            serverSocket = new ServerSocket(4758);
            System.out.println("connected client");
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients = new ArrayList<>();
        this.initSocket();
    }

    public void initSocket() {
        System.out.println("connected client");
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("connected client" + clientSocket.getInetAddress().getHostName());
                ClientHandler client = new ClientHandler(this, "username", new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

                clients.add(client);
                client.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToAllClients(final String message) {
        for (ClientHandler client : clients) {
            try {
                BufferedWriter bw = client.getBufferedWriter();
                bw.write(message + "\r\n");
                bw.flush();
                System.out.println("send > " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
