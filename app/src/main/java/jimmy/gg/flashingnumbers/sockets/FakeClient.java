package jimmy.gg.flashingnumbers.sockets;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/*praise jimmy*/

public class FakeClient {
    private Socket clientSocket;
    private BufferedWriter bw;
    private BufferedReader br;
    private String message = "";
    private TextView view;

    public FakeClient(final TextView view){
        this.view = view;
        view.setText("test");
        new Thread(new SocketThread()).start();
    }

    /*
    * This creating creating a client socket to connect to local server
    * and proccess mssages
    */

    class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run() {
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bw.write("testing socket "+"\r\n");
                bw.flush();

                while(true){
                    String message = br.readLine();
                    view.setText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
