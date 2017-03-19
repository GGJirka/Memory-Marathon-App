package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

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
        client.sendMessage("ROOMCONNECT "+getNickname()+" " +getRoomName());
    }

    public void onResume(){
        super.onResume();
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
    }

    public void click(View view){
        //client.setData(getRoomName(),this);
        //client.sendMessage("ROOMSTART "+getRoomName());
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
