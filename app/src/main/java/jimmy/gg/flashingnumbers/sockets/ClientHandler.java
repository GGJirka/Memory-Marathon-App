package jimmy.gg.flashingnumbers.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by ggjimmy on 3/7/17.
 */

public class ClientHandler extends Thread{
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

    @Override
    public void run(){
        while(true){
            try {
                server.sendToAllClients("message: "+br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public BufferedReader getBufferedReader(){
        return br;
    }
    public BufferedWriter getBufferedWriter(){
        return bw;
    }
}
