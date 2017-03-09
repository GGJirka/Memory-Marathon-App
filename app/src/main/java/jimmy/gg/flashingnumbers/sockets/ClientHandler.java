package jimmy.gg.flashingnumbers.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientHandler extends Thread {

    private BufferedReader br;
    private BufferedWriter bw;
    private Server server;
    private String receivedMessage = "";

    public ClientHandler(Server server, BufferedReader br, BufferedWriter bw) {
        this.server = server;
        this.br = br;
        this.bw = bw;
    }

    @Override
    public void run() {
        while (true) {
            try {
                receivedMessage = br.readLine();
                System.out.println("received:  " + receivedMessage);
                server.sendToAllClients(receivedMessage.trim());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void send() throws IOException{
        bw.write(receivedMessage);
        bw.flush();
    }

    public String getMessage() {
        return receivedMessage;
    }
    public void disconnect(){
        server.getClients().remove(this);
    }
}
