package jimmy.gg.flashingnumbers.multiplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.Client;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class MultiplayerNumbers extends AppCompatActivity implements IMultiplayerNumbers{
    public final String ROOMNAME = "ROOMNAME";
    public Client         client;
    public FakeClient fakeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Multiplayer");
        fakeClient = new FakeClient();
    }
    @Override
    public void connectToRoom(View v) {
        final View view = LayoutInflater.from(this).inflate(R.layout.edittext_alert,null);
        final EditText text = (EditText) view.findViewById(R.id.edittext_alert2);

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(MultiplayerNumbers.this)
                .setTitle("Connect")
                .setMessage("Enter name of room: ")
                .setView(LayoutInflater.from(this).inflate(R.layout.edittext_alert,null))
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
        connectDialog.show();
        connectDialog.create();
    }

    @Override
    public void createRoom(View v) {
        View view = LayoutInflater.from(MultiplayerNumbers.this).inflate(R.layout.edittext_alert,null);
        final EditText text = (EditText) view.findViewById(R.id.edittext_alert2);

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(MultiplayerNumbers.this)
                .setView(view)
                .setTitle("Create room")
                .setMessage("Enter name of room: ")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent roomIntent = new Intent(MultiplayerNumbers.this, RoomActivity.class);
                        roomIntent.putExtra(ROOMNAME,text.getText()+"test");
                        startActivity(roomIntent);
                    }
                });
        connectDialog.show();
        connectDialog.create();
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
        fakeClient.socketClose();
        super.onDestroy();
    }
}
