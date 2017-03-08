package jimmy.gg.flashingnumbers.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ggjimmy on 3/7/17.
 */

public class ClientHandler{
    private String username;
    private Server server;
    private BufferedWriter bw;
    private BufferedReader br;


    public ClientHandler(Server server, String username, BufferedWriter bw, BufferedReader br){
        this.server = server;
        this.username = username;
        this.bw = bw;
        this.br = br;
    }

    public void run(){
        server.sendToAllClients("ahoj");
        Thread thread = new Thread(new Runnable(){
            public void run() {
                while (true) {
                    try {
                        String message = br.readLine();
                        System.out.println("mesage " + message);
                        server.sendToAllClients(message);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        thread.start();
    }
    public BufferedReader getBufferedReader(){
        return br;
    }
    public BufferedWriter getBufferedWriter(){
        return bw;
    }
}
