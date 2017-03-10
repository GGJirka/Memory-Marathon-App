package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;

import jimmy.gg.flashingnumbers.R;

public class RoomActivity extends AppCompatActivity {
    public final String ROOMNAME = "ROOMNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Multiplayer Room");
        TextView title = (TextView) findViewById(R.id.room_title);
        title.setText("Room "+getIntent().getStringExtra(ROOMNAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

}
