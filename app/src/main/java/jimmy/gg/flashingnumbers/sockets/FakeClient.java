package jimmy.gg.flashingnumbers.sockets;

import android.support.annotation.RequiresPermission;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import jimmy.gg.flashingnumbers.multiplayer.RoomActivity;

/*praise jimmy*/

public class FakeClient implements IFakeClient{
    private Socket clientSocket;
    private BufferedWriter bw;
    private BufferedReader br;
    private String message = "";
    private RoomActivity activity;

    public FakeClient(){
        activity = new RoomActivity();
        new Thread(new SocketThread()).start();
    }

    /*
    * This creating creating a client socket to connect to local server
    * and proccess mssages
    */
    @Override
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

    @Override
    public void sendMessage(String message) {
        try {
            bw.write(message + "\r\n");
            bw.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean attemptToConnectRoom(String roomName) {

        return false;
    }

    @Override
    public boolean isConnected() {
        return clientSocket.isConnected();
    }

    class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                //if(clientSocket == null) {
                    clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                    bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    sendMessage("ahojky");

                    while (true) {
                        String message = br.readLine();
                        String manageMessage = message.split(" ")[0];
                        if(manageMessage.equals("ROOMCONNECT")){
                            if(activity.getRoomName()!=null || activity.getRoomName()!=""){
                                if(activity.getRoomName().equals(message.split(" ")[3])){
                                    activity.addUser(message.split(" ")[2]);
                                }
                            }
                        }
                    }
               // }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
