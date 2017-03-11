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
    public ArrayList<Score> score;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("In room "+getIntent().getStringExtra(ROOMNAME));
        TextView title = (TextView) findViewById(R.id.room_title);
        //title.setText(getString(R.string.room_title)+getIntent().getStringExtra(ROOMNAME));
        connectedUsers = (ListView) findViewById(R.id.connected_players);
        score = new ArrayList<>();
        initUsers();
    }

    public void initUsers(){
        score.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        userAdapter = new HighScoreAdapter(getApplicationContext(),score);
        connectedUsers.setAdapter(userAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

}
