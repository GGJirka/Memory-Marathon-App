package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.sockets.FakeClient;

public class MP extends AppCompatActivity {
    public FakeClient fakeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp);
        final TextView view = (TextView) findViewById(R.id.mp);
        fakeClient = new FakeClient(view);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                view.setText("sending");
                fakeClient.sendMessage("TEST LOL");
            }
        });
        thread.start();
    }
}
