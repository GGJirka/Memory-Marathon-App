package jimmy.gg.flashingnumbers.sockets;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

/*praise jimmy*/

public class FakeClient extends AppCompatActivity implements IFakeClient{

    private Socket clientSocket;
    private BufferedWriter bw;
    private String message = "";
    public TextView view;
    public String nickname, roomName;
    public ArrayList<Score> users;
    public BaseAdapter adapter;
    public BufferedReader br;
    public Score score;
    public EditText username, room;
    public Context context;

    public void setData(ArrayList<Score> users, BaseAdapter adapter, String username,String roomNam){
        this.users = users;
        this.adapter = adapter;
        this.roomName = roomNam;
        this.nickname = username;
    }

    public void setData(EditText username,EditText room,Context context,TextView view){
        this.username = username;
        this.room = room;
        this.context = context;
        this.view = view;
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

    public boolean findUserByName(Score score){
        for(Score sc:users){
           if(sc.getText().equals(score.getText())){
               return true;
           }
        }
        return false;
    }
    /*
    * Starting socket - must be run on new thread,
    * Starting reading from server and making operations
    * */
    public class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //sick code
                while (clientSocket.isConnected()){
                    message = br.readLine();
                        try {
                            if (message != null) {
                                if (MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INMENU)) {
                                    //WHEN CONNECTING
                                    Runnable run = new Runnable() {
                                        @Override
                                        public void run() {
                                            if (message.split(" ")[0].equals("NOEXISTROOM")) {
                                                view.setText("no");
                                            } else {
                                                view.setText("working");
                                            }
                                        }
                                    };
                                    runOnUiThread(run);
                                } else if (MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INROOM)) {
                                    //ADDING NEW USER TO LIST
                                    try {
                                        if (adapter != null && users != null) {
                                            if (message.split(" ")[0].equals("ROOMCONNECT")) {
                                                if (roomName.equals(message.split(" ")[2])) {
                                                    if (message.split(" ")[1] != null) {
                                                        Score score = new Score(message.split(" ")[1], true);
                                                        if (!findUserByName(score)) {
                                                            users.add(score);
                                                            Runnable run = new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            };
                                                            runOnUiThread(run);
                                                            try {
                                                                Thread.sleep(600);
                                                                if (nickname != null) {
                                                                    sendMessage("ROOMCONNECT " + nickname + " " + message.split(" ")[2]);
                                                                }
                                                            } catch (Exception e) {

                                                            }
                                                        }
                                                    }
                                                }
                                            }else if(message.split(" ")[0].equals("ROOMDISCONNECT")) {
                                                for (int i = 0; i < users.size(); i++) {
                                                    String user = users.get(i).getText();
                                                    if (user.equals(message.split(" ")[1])) {
                                                        users.remove(i);
                                                    }
                                                }
                                                Runnable run = new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                };
                                                runOnUiThread(run);
                                            }
                                        }
                                    } catch (Exception e) {
                                        //Log.d(TAG, "run: ");
                                    }
                                } else if (message.split(" ")[0].equals("USERSINROOM")) {
                                    if (adapter != null && users != null) {
                                        if (message.split(" ")[1].equals(message)) {
                                            Score score = new Score(message.split(" ")[2], true);
                                            if (!findUserByName(score)) {
                                                users.add(score);
                                                Runnable run = new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                };
                                                runOnUiThread(run);
                                            }
                                        }
                                    }
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
