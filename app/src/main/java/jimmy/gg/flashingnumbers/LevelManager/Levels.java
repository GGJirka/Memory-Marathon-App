package jimmy.gg.flashingnumbers.LevelManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class Levels extends AppCompatActivity {
    public final static String EXTRA_LEVEL = "jimmy.gg.flashingnumbers.LEVEL";
    public final static String EXTRA_NUMBERS = "jimmy.gg.flashingnumbers.NUMBERS";
    public final static String EXTRA_TIME = "jimmy.gg.flashingnumbers.TIME";
    private ListView levels;
    public static ArrayList<Level> levelList;

    public Levels(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setTitle("Levels");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        levelList = new ArrayList<>();
        levels = (ListView) findViewById(R.id.levels);
        levels.setItemsCanFocus(false);
        levelList.add(new Level("Level 1", "numbers: 5   ", "time: 10s   ", "easy"));
        levelList.add(new Level("Level 2", "numbers: 8   ", "time: 20s   ", "easy"));
        levelList.add(new Level("Level 3", "numbers: 13 ", "time: 45s   ", "easy"));
        levelList.add(new Level("Level 4", "numbers: 15 ", "time: 45s   ", "easy"));
        levelList.add(new Level("Level 5", "numbers: 19 ", "time: 60s   ", "easy"));
        levelList.add(new Level("Level 6", "numbers: 25 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 7", "numbers: 30 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 8", "numbers: 33 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 9", "numbers: 40 ", "time: 120s", "medium"));
        levelList.add(new Level("Level 10", "numbers: 45 ", "time: 120s", "medium"));
        levelList.add(new Level("Level 11", "numbers: 55 ", "time: 150s", "hard"));
        levelList.add(new Level("Level 12", "numbers: 65 ", "time: 180s", "hard"));
        levelList.add(new Level("Level 13", "numbers: 80 ", "time: 180s", "hard"));
        levelList.add(new Level("Level 14", "numbers: 100", "time: 180s", "hard"));

        levels.setAdapter(new LevelAdapter(getApplicationContext(), levelList));

        levels.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String level = levelList.get(position).getLevel();
                String numbers = levelList.get(position).getNumbers();
                String time = levelList.get(position).getTime();
                gameStarted(level, numbers, time);
            }
        });
    }

    public void gameStarted(String level, String numbers, String time){
        Intent intent = new Intent(this, Numbers.class);
        intent.putExtra(EXTRA_LEVEL,level);
        intent.putExtra(EXTRA_NUMBERS,numbers);
        intent.putExtra(EXTRA_TIME,time);
        startActivity(intent);
    }
    /*@Override
    public void onBackPressed(){
        Intent intent = new Intent(this,FlashingNumbers.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }*/
    public void onPause(){
        super.onPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public List<Level> getLevelList(){
        return levelList;
    }
}