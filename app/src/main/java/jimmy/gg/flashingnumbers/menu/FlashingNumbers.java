package jimmy.gg.flashingnumbers.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.multiplayer.MultiplayerNumbers;
import jimmy.gg.flashingnumbers.quicklevel.QuickLevel;
import jimmy.gg.flashingnumbers.settings.NumbersSettings;

public class FlashingNumbers extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;
    public static ArrayList<Level> levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashing_numbers);
        setTitle(getString(R.string.menu_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        //IternalMemory memory = new IternalMemory();
        levelList = new ArrayList<>();
        levelList.add(new Level(getString(R.string.level)+" 1", getString(R.string.numbers)+" 5   ", getString(R.string.timer)+" 15s   ", "easy"));
        levelList.add(new Level(getString(R.string.level)+" 2", getString(R.string.numbers)+" 7   ", getString(R.string.timer)+" 20s   ", "easy"));
        levelList.add(new Level(getString(R.string.level)+" 3", getString(R.string.numbers)+" 10 ", getString(R.string.timer)+" 30s   ", "easy"));
        levelList.add(new Level(getString(R.string.level)+" 4", getString(R.string.numbers)+" 13 ", getString(R.string.timer)+" 40s   ", "easy"));
        levelList.add(new Level(getString(R.string.level)+" 5", getString(R.string.numbers)+" 17 ", getString(R.string.timer)+" 50s   ", "easy"));
        levelList.add(new Level(getString(R.string.level)+" 6", getString(R.string.numbers)+" 20 ", getString(R.string.timer)+" 60s   ", "medium"));
        levelList.add(new Level(getString(R.string.level)+" 7", getString(R.string.numbers)+" 25 ", getString(R.string.timer)+" 60s   ", "medium"));
        levelList.add(new Level(getString(R.string.level)+" 8", getString(R.string.numbers)+" 33 ", getString(R.string.timer)+" 80s   ", "medium"));
        levelList.add(new Level(getString(R.string.level)+" 9", getString(R.string.numbers)+" 40 ",getString(R.string.timer)+" 120s", "medium"));
        levelList.add(new Level(getString(R.string.level)+" 10", getString(R.string.numbers)+" 45 ", getString(R.string.timer)+" 120s", "medium"));
        levelList.add(new Level(getString(R.string.level)+" 11", getString(R.string.numbers)+" 55 ", getString(R.string.timer)+" 150s", "hard"));
        levelList.add(new Level(getString(R.string.level)+" 12", getString(R.string.numbers)+" 65 ", getString(R.string.timer)+" 180s", "hard"));
        levelList.add(new Level(getString(R.string.level)+" 13", getString(R.string.numbers)+" 80 ", getString(R.string.timer)+" 180s", "hard"));
        levelList.add(new Level(getString(R.string.level)+" 14", getString(R.string.numbers)+" 95 ", getString(R.string.timer)+" 240s", "hard"));
    }
    public void levelsClicked(View view){
        Intent intent = new Intent(this,Levels.class);
        startActivity(intent);
    }
    public void settingsClicked(View view){
        Intent intent = new Intent(this, NumbersSettings.class);
        startActivity(intent);
    }
    public void quickClicked(View view){
        Intent intent = new Intent(this,QuickLevel.class);
        startActivity(intent);
    }

    public void multiplayerStart(View view){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()== NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED){
            Intent intent = new Intent(this, MultiplayerNumbers.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Cannot connect to internet. Try again.", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
