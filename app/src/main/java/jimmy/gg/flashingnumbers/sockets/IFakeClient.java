package jimmy.gg.flashingnumbers.sockets;

import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ggjimmy on 3/11/17.
 */

public interface IFakeClient{
    void startSocket();

    boolean socketClose();

    void sendMessage(String message);

    boolean attemptToConnectRoom(String roomName);

    boolean isConnected();

}
