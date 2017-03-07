package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.Client;

public class MultiplayerNumbers extends AppCompatActivity {
    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_numbers);
        client = new Client();
    }
}
