package jimmy.gg.flashingnumbers.sockets;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
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
    public String roomName;
    public ArrayList<Score> users;
    public BaseAdapter adapter;
    public BufferedReader br;
    public Score score;
    public EditText username, room;
    public Context context;
    public boolean roomExist;

    public void setData(ArrayList<Score> users, BaseAdapter adapter, String roomNam){
        this.users = users;
        this.adapter = adapter;
        this.roomName = roomNam;
    }

    public void setData(EditText username,EditText room,Context context, boolean roomExist,TextView view){
        this.username = username;
        this.room = room;
        this.context = context;
        this.roomExist = roomExist;
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

    public class SocketThread implements Runnable{
        @RequiresPermission(android.Manifest.permission.INTERNET)
        @Override
        public void run(){
            try {
                clientSocket = new Socket(InetAddress.getByName("192.168.1.101"), 4758);
                bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true){
                    message = br.readLine();
                    try {
                        if (message != null){
                            if(MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INMENU)){
                                //WHEN CONNECTING
                                if(message.split(" ")[0].equals("NOEXISTROOM")){    z
                                    if(message.split(" ")[1].equals(username.getText())
                                            && message.split(" ")[2].equals(room.getText())){
                                        roomExist = false;
                                        Runnable run = new Runnable(){
                                            public void run(){
                                                room.setText("room doesnt exist lul");
                                            }
                                        };
                                        runOnUiThread(run);
                                    }
                                    view.setText("no exist hah");
                                }else{
                                    view.setText("working hah");
                                    roomExist = true;
                                    room.setText("room doesnt exist lul");
                                    username.setText("ajajjaj");
                                    Runnable run = new Runnable(){
                                        public void run(){
                                            room.setText("room doesnt exist lul");
                                            username.setText("ajajjaj");
                                        }
                                    };
                                    runOnUiThread(run);
                                }
                            }else if(MultiplayerNumbers.GAMESTATE.equals(MultiplayerState.INROOM)){
                                //ADDING NEW USER TO LIST
                                if(adapter != null && users!=null) {
                                    if (message.split(" ")[0].equals("ROOMCONNECT")) {
                                        if (roomName.equals(message.split(" ")[2])) {
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
                                            }
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
