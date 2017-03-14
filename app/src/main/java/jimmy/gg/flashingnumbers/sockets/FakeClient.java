package jimmy.gg.flashingnumbers.sockets;

import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.multiplayer.MultiplayerNumbers;
import jimmy.gg.flashingnumbers.multiplayer.MultiplayerState;
import jimmy.gg.flashingnumbers.multiplayer.RoomActivity;

/*praise jimmy*/

public class FakeClient extends AsyncTask<Object, Object, BaseAdapter> implements IFakeClient{

    private Socket clientSocket;
    private BufferedWriter bw;
    private String message = "";
    private String result;
    private RoomActivity activity;
    public TextView view;
    public ArrayList<Score> users;
    public BaseAdapter adapter;
    public ListView list;
    public static BufferedReader br;
    public Score score;


    public void setData(ArrayList<Score> users, BaseAdapter adapter, ListView list, TextView view){
        this.users = users;
        this.list = list;
        this.view = view;
    }

    public void setList(ArrayList<Score> users){
        this.users = users;
    }

    public void setAdapter(BaseAdapter adater){
        adapter = adater;
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
    public void sendMessage(String message){
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

    @Override
    protected BaseAdapter doInBackground(Object... params) {
        try {
            message = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (message != null){
                if(MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INROOM)){
                   // if(users.contains(new Score(message.split(" ")[1],true))) {
                        score = new Score("a", true);
                   // }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return adapter;
    }

    @Override
    public void onPostExecute(BaseAdapter res){
        users.add(score);
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
        super.onPostExecute(res);
    }

    public class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
