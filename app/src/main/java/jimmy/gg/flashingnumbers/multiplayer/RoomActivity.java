package jimmy.gg.flashingnumbers.multiplayer;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class RoomActivity extends AppCompatActivity {
    /*TODO: bug - connecting doesnt show up users in list,
    * better design
    * when someone start the room send to other
    * display numbers to other*/
    public  final String ROOMNAME = "ROOMNAME";
    public  final String NICKNAME = "NICKNAME";
    public  FakeClient client;
    private ArrayList<Score> users;
    private BaseAdapter adapter;
    private ListView connectedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
        setTitle("In room "+getRoomName());
        connectedUsers = (ListView) findViewById(R.id.connected_players);
        users = new ArrayList<>();
        client = MultiplayerNumbers.fakeClient;
        initUsers();
    }

    public void initUsers(){
        adapter = new HighScoreAdapter(getApplicationContext(),users);
        connectedUsers.setAdapter(adapter);
        String message = getRoomName()+"";
        client.setData(users, adapter,getNickname()+"", message);
        client.setData(getRoomName(),this);
        client.sendMessage("ROOMCONNECT "+getNickname()+" " +getRoomName());
    }

    public void onResume(){
        super.onResume();
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
    }

    public void click(View view){
        View selects = LayoutInflater.from(this).inflate(R.layout.room_start_select,null);
        final EditText round = (EditText) selects.findViewById(R.id.amount_rounds_edit);
        final EditText numbers = (EditText) selects.findViewById(R.id.amount_numbers_edit);

        new AlertDialog.Builder(this)
                .setView(selects)
                .setPositiveButton("START", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        if(round.getText().length()!=0){
                            Toast.makeText(getApplicationContext(),"Enter amount of rounds",Toast.LENGTH_SHORT).show();
                            if(numbers.getText().length()!=0){
                                client.sendMessage("ROOMSTART "+getRoomName()+" "+round.getText()+" "+numbers.getText());
                            }else{
                                Toast.makeText(getApplicationContext(),"Enter amount of numbers",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Enter amount of rounds",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setCancelable(true)
                .show();
    }

    public String getRoomName(){
        return this.getIntent().getStringExtra(ROOMNAME);
    }
    public String getNickname(){
        return this.getIntent().getStringExtra(NICKNAME);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        client.sendMessage("ROOMDISCONNECT "+getNickname()+" "+getRoomName());
        super.onDestroy();
    }
}
