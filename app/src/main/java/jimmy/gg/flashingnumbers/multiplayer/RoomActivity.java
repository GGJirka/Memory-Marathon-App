package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class RoomActivity extends AppCompatActivity {
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
        setTitle("In room "+getIntent().getStringExtra(ROOMNAME));
        connectedUsers = (ListView) findViewById(R.id.connected_players);
        users = new ArrayList<>();
        client = MultiplayerNumbers.fakeClient;
        initUsers();
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
    }

    public void initUsers(){
        users.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        adapter = new HighScoreAdapter(getApplicationContext(),users);
        connectedUsers.setAdapter(adapter);
        String message = getIntent().getStringExtra(ROOMNAME)+"";
        client.setData(users, adapter, message);
    }

    public void click(View view){
        adapter.notifyDataSetChanged();
    }

    public String getRoomName(){
        return this.getIntent().getStringExtra(ROOMNAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
    public void onDestroy(){
        //client.cancel(true);
        super.onDestroy();
    }
}
