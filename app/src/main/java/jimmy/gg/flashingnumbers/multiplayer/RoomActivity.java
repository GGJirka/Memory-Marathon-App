package jimmy.gg.flashingnumbers.multiplayer;

import android.content.Context;
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
import jimmy.gg.flashingnumbers.sockets.Room;

public class RoomActivity extends AppCompatActivity {
    public final String ROOMNAME = "ROOMNAME";
    public final String NICKNAME = "NICKNAME";
    public FakeClient client;
    public String message;
    public static Context context;
    private ArrayList<Score> users;
    private BaseAdapter adapter;
    private ListView connectedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("In room "+getIntent().getStringExtra(ROOMNAME));
        TextView title = (TextView) findViewById(R.id.room_title);
        connectedUsers = (ListView) findViewById(R.id.connected_players);
        users = new ArrayList<>();
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
        TextView room = (TextView) findViewById(R.id.room_title);
        client = MultiplayerNumbers.fakeClient;
        initUsers(room);
    }

    public void initUsers(TextView room){
        users.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        adapter = new HighScoreAdapter(getApplicationContext(),users);
        connectedUsers.setAdapter(adapter);
        //client.setData(users, adapter, connectedUsers,room);
        client.setList(users);
    }

    /*public void listener(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = FakeClient.br.readLine();
                        users.add(new Score("jimmy",true));
                        userAdapter = new HighScoreAdapter(getApplicationContext(),users);
                        userAdapter.notifyDataSetChanged();
                        if (message.split(" ")[0].equals("ROOMCONNECT") && message.split(" ")[2].equals(getRoomName())) {
                            users.add(new Score(message.split(" ")[1],true));
                            userAdapter = new HighScoreAdapter(getApplicationContext(),users);
                            userAdapter.notifyDataSetChanged();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
       thread.start();
    }*/

    public void click(View view){
        users.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        //connectedUsers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public String getRoomName(){
        return this.getIntent().getStringExtra(ROOMNAME);
    }

    public void addUser(String username){
        UserData.users.add(new Score(username, true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public static final class UserData {
        public static BaseAdapter userAdapter;
        public static ListView connectedUsers;
        public static ArrayList<Score> users;
        public static String username, roomName;
    }
}
