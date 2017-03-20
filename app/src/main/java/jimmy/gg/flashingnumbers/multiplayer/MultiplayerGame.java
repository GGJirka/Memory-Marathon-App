package jimmy.gg.flashingnumbers.multiplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

import jimmy.gg.flashingnumbers.R;

public class MultiplayerGame extends AppCompatActivity {

    public final String ROOMROUNDS  =  "ROOMROUNDS";
    public final String ROOMNUMBERS = "ROOMNUMBERS";
    private Integer number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_game);
        setTitle("Multiplayer game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initNumber();
    }

    public void initNumber(){
        number = Integer.valueOf(getIntent().getStringExtra(ROOMNUMBERS));
        TextView numbersText = (TextView)findViewById(R.id.amount_numbers_text);
        StringBuilder builder = new StringBuilder();

        for(int i=0;i<number;i++){
            builder.append(new Random().nextInt(10));
        }
        numbersText.setText(builder.toString());
    }
    @Override
    public void onResume(){
        super.onResume();
        MultiplayerNumbers.setState(MultiplayerState.INGAME);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
