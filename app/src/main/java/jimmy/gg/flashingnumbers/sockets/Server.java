package jimmy.gg.flashingnumbers.sockets;

import android.support.v7.app.AppCompatActivity;

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

public class Server extends AppCompatActivity{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ClientHandler> clients;

    public Server(){
        try {
            serverSocket = new ServerSocket(80);
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients = new ArrayList<>();
        this.initSocket();
    }

    public void initSocket(){
        try {
            clientSocket = serverSocket.accept();
            new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())).write("TEST"+"\r\n");
            new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())).flush();
            clients.add(new ClientHandler(this,"username",new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendToAllClients(final String message){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for(ClientHandler client : clients){
                    try {
                        BufferedWriter bw = client.getBufferedWriter();
                        bw.write(message+"\r\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        runOnUiThread(run);
    }

}
