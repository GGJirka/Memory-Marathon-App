package jimmy.gg.flashingnumbers.multiplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.Client;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class MultiplayerNumbers extends AppCompatActivity implements IMultiplayerNumbers{

    public final String ROOMNAME = "ROOMNAME";
    public final String NICKNAME = "NICKNAME";
    public Client                       client;
    public static FakeClient        fakeClient;
    public static MultiplayerState  GAMESTATE = MultiplayerState.INMENU;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Multiplayer");
        startSocket();
    }

    public void startSocket(){
        fakeClient = new FakeClient();
        fakeClient.startSocket();
    }

    @Override
    public void connectToRoom(View v) {
        final EditText nickname = (EditText) findViewById(R.id.nick);
        if(!nickname.getText().toString().equals("")) {
            final View view = LayoutInflater.from(this).inflate(R.layout.edittext_alert, null);
            final EditText text = (EditText) view.findViewById(R.id.edittext_alert2);

            final AlertDialog connectDialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .setTitle("Connect to room")
                    .setMessage("Enter room name: ")
                    .setPositiveButton("connect", null)
                    .create();

            connectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = connectDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            if(!text.getText().toString().equals("")) {
                                Thread thread = new Thread(new Runnable(){
                                    @Override
                                    public void run(){
                                        if(fakeClient.isConnected()){
                                            fakeClient.sendMessage("ROOMCONNECT " + nickname.getText() + " " + text.getText());
                                        }
                                    }
                                });
                                thread.start();
                            }else{
                                Toast.makeText(getApplicationContext(),"Enter room name",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            connectDialog.show();
        }else{
            Toast.makeText(getApplicationContext(),"Enter nickname",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void createRoom(View v){
        final EditText nickname = (EditText) findViewById(R.id.nick);
        if(!nickname.getText().toString().equals("")){
            View view = LayoutInflater.from(MultiplayerNumbers.this).inflate(R.layout.edittext_alert, null);
            final EditText text = (EditText) view.findViewById(R.id.edittext_alert2);
            final AlertDialog connectDialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .setTitle("Create room")
                    .setMessage("Enter room name: ")
                    .setPositiveButton("create", null)
                    .create();

            connectDialog.setOnShowListener(new DialogInterface.OnShowListener(){
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = connectDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            if(!text.getText().toString().equals("")) {
                                Thread thread = new Thread(new Runnable(){
                                    @Override
                                    public void run() {
                                        try {
                                            if(fakeClient.isConnected()){
                                               fakeClient.sendMessage("ROOMNAME "+nickname.getText()+" "+text.getText());
                                                Intent roomIntent = new Intent(MultiplayerNumbers.this, RoomActivity.class);
                                                roomIntent.putExtra(ROOMNAME, text.getText() + "");
                                                roomIntent.putExtra(NICKNAME, nickname.getText() + "");
                                                startActivity(roomIntent);
                                                connectDialog.dismiss();
                                            }else{
                                                Toast.makeText(getApplicationContext(),"Problem with internet.",Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                            }else{
                                Toast.makeText(getApplicationContext(),"Enter room name",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            connectDialog.show();
        }else{
            Toast.makeText(getApplicationContext(),"Enter nickname",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changeFragment(int fragment) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        /*fakeClient.sendMessage("DISCONNECT");
        fakeClient.socketClose();*/
        super.onDestroy();
    }

    public static void setState(MultiplayerState state){
        GAMESTATE = state;
    }
}
