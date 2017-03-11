package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;

public class RoomActivity extends AppCompatActivity {
    public final String ROOMNAME = "ROOMNAME";
    public final String NICKNAME = "NICKNAME";
    public BaseAdapter userAdapter;
    public ListView connectedUsers;
    public ArrayList<Score> users;
    public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("In room "+getIntent().getStringExtra(ROOMNAME));
        TextView title = (TextView) findViewById(R.id.room_title);
        connectedUsers = (ListView) findViewById(R.id.connected_players);
        users = new ArrayList<>();
        initUsers();
    }

    public void initUsers(){
        users.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        userAdapter = new HighScoreAdapter(getApplicationContext(),users);
        connectedUsers.setAdapter(userAdapter);
    }

    public void listener(){
        Runnable run = new Runnable(){
            @Override
            public void run(){
                while(true){
                    if(message.split(" ").equals("ROOMCONNECT") && message.split(" ")[3]
                            .equals(getIntent().getStringExtra(ROOMNAME)+"")){

                        users.add(new Score(message.split(" ")[2],true));

                    }
                }
            }
        };
        runOnUiThread(run);
    }
    public String getRoomName(){
        return this.getIntent().getStringExtra(ROOMNAME);
    }
    public void addUser(String username){
        this.users.add(new Score(username, true));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

}
