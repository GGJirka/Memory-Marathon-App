package jimmy.gg.flashingnumbers.sockets;

import android.support.annotation.RequiresPermission;
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

    public FakeClient(){
        new Thread(new SocketThread()).start();
    }

    /*
    * This creating creating a client socket to connect to local server
    * and proccess mssages
    */

    public boolean socketClose(){
        try{
            clientSocket.close();
            bw.close();
            br.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
