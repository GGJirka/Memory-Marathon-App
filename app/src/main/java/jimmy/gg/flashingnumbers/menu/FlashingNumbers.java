package jimmy.gg.flashingnumbers.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.helpers.IternalMemory;
import jimmy.gg.flashingnumbers.main.MainActivity;
import jimmy.gg.flashingnumbers.quicklevel.QuickLevel;
import jimmy.gg.flashingnumbers.settings.NumbersSettings;

public class FlashingNumbers extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;
    public static ArrayList<Level> levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashing_numbers);
        setTitle("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        IternalMemory memory = new IternalMemory();
        levelList = memory.getLevelList();
        initSettings();
    }
    public void initSettings(){

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
