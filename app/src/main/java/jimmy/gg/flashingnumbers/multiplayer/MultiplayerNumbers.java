package jimmy.gg.flashingnumbers.multiplayer;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.Client;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class MultiplayerNumbers extends AppCompatActivity {
    public Client client;
    public FakeClient fakeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_numbers);
        TextView view = (TextView) findViewById(R.id.message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*client = new Client(view);
        client.execute();*/
        fakeClient = new FakeClient(view);
        //new Thread(new FakeClient(view).run());
    }

    public void setText(String message){
        TextView view = (TextView) findViewById(R.id.message);
        view.setText(message);
    }
    @RequiresPermission(android.Manifest.permission.INTERNET)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
