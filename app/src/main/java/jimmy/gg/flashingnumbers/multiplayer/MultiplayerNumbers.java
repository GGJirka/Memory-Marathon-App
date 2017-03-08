package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.Client;

public class MultiplayerNumbers extends AppCompatActivity {
    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_numbers);
        TextView view = (TextView) findViewById(R.id.message);
        client = new Client(view);
        client.execute();
    }

    public void setText(String message){
        TextView view = (TextView) findViewById(R.id.message);
        view.setText(message);
    }
}
