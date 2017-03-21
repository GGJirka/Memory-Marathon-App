package jimmy.gg.flashingnumbers.multiplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import jimmy.gg.flashingnumbers.LevelManager.NumbersRemember;
import jimmy.gg.flashingnumbers.R;

public class MultiplayerGame extends AppCompatActivity {

    public final String ROOMROUNDS  =  "ROOMROUNDS";
    public final String ROOMNUMBERS = "ROOMNUMBERS";
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_game);
        setTitle("Multiplayer game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initNumber();
    }

    public void initNumber(){
        number = getIntent().getStringExtra(ROOMNUMBERS);
        TextView numbersText = (TextView)findViewById(R.id.multiplayer_numbers_text);
        numbersText.setText(number);
    }

    @Override
    public void onResume(){
        super.onResume();
        MultiplayerNumbers.setState(MultiplayerState.INGAME);
    }

    public void onRemember(View v){
        Intent intent = new Intent(this, NumbersRemember.class);
        intent.putExtra(EXTRA_NUMBERS, number);
        intent.putExtra(EXTRA_NUMBERS_COUNT, 20);
        intent.putExtra(EXTRA_LEVEL, "12");
        intent.putExtra(EXTRA_TIME, 1000);
        intent.putExtra(EXTRA_TIME_REMAIN, 50);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
    public String EXTRA_NUMBERS = "jimmy.gg.flashingnumbers.NUMBERS";
    public String EXTRA_NUMBERS_COUNT = "jimmy.gg.flashingnumbers.NUMBERS_COUNT";
    public String EXTRA_TIME = "jimmy.gg.flashingnumbers.TIME";
    public String EXTRA_TIME_REMAIN = "jimmy.gg.flashingnumbers.TIME_REMAIN";
    public final String EXTRA_LEVEL = "jimmy.gg.flashingnumbers.LEVEL";

}
