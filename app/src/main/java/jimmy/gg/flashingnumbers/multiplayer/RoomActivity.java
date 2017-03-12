package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("In room "+getIntent().getStringExtra(ROOMNAME));
        TextView title = (TextView) findViewById(R.id.room_title);
        UserData.connectedUsers = (ListView) findViewById(R.id.connected_players);
        UserData.users = new ArrayList<>();
        UserData.username = getIntent().getStringExtra(NICKNAME);
        UserData.roomName = getIntent().getStringExtra(ROOMNAME);
        MultiplayerNumbers.setState(MultiplayerState.INROOM);
        TextView room = (TextView) findViewById(R.id.room_title);
        client = MultiplayerNumbers.fakeClient;
        client.setText(room);
        initUsers();
    }

    public void initUsers(){
        UserData.users.add(new Score(getIntent().getStringExtra(NICKNAME), true));
        UserData.userAdapter = new HighScoreAdapter(getApplicationContext(),UserData.users);
        UserData.connectedUsers.setAdapter(UserData.userAdapter);
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
