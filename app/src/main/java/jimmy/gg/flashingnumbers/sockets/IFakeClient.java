package jimmy.gg.flashingnumbers.sockets;

/**
 * Created by ggjimmy on 3/11/17.
 */

public interface IFakeClient{

    boolean socketClose();

    void sendMessage(String message);

    boolean attemptToConnectRoom(String roomName);

    boolean isConnected();
}
