package jimmy.gg.flashingnumbers.sockets;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.multiplayer.MultiplayerNumbers;
import jimmy.gg.flashingnumbers.multiplayer.MultiplayerState;
import jimmy.gg.flashingnumbers.multiplayer.RoomActivity;

/*praise jimmy*/

public class FakeClient implements IFakeClient{

    private Socket clientSocket;
    private BufferedWriter bw;
    private String message = "";
    private RoomActivity activity;
    public TextView view;
    public ArrayList<Score> users;
    public BaseAdapter adapter;
    public ListView list;
    public static BufferedReader br;

    public FakeClient(TextView view){
        this.view = view;
    }

    public void setData(ArrayList<Score> users, BaseAdapter adapter, ListView list, TextView view){
        this.users = users;
        this.adapter = adapter;
        this.list = list;
        this.view = view;
    }
    public void setList(ArrayList<Score> users){
        this.users = users;
    }
    @Override
    public void startSocket(){
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
    public void setText(TextView view){
        this.view = view;
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

    public class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while(true){
                    message = br.readLine();
                    try {
                        if (message != null){
                            view.setText(message);
                            if(MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INROOM)){
                                users.add(new Score("cs", true));
                                //list.setAdapter(adapter);
                            }
                        }
                    }catch(Exception e){

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
