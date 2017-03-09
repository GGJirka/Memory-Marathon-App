package jimmy.gg.flashingnumbers.sockets;

/**
 * Created by ggjimmy on 3/9/17.
 */

public interface IServer {

    void initSocket();

    void sendToAllClients(String message);
}
