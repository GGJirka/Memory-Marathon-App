package jimmy.gg.flashingnumbers.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;

public class HighScore extends AppCompatActivity {
    public final static String KEY_HIGH_SCORE ="KEY_HIGH_SCORE";
    public ArrayList<Score> scores;
    public ArrayList<Level> levelList;
    public ListView highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("High Score");
        scores = new ArrayList<>();
        levelList = new ArrayList<>();
        highScores = (ListView) findViewById(R.id.high_scores);

        for(int i=0;i<Levels.levelList.size();i++){
            Level lv = Levels.levelList.get(i);
            if(Levels.highScore.getString(KEY_HIGH_SCORE+String.valueOf(i),"0")!="0") {
                scores.add(new Score(lv.getLevel() + " (" + lv.getNumbers()+"): ",
                        Levels.highScore.getString(KEY_HIGH_SCORE + String.valueOf(i), "0")));
            }
        }
        highScores.setAdapter(new HighScoreAdapter(getApplicationContext(),scores));
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
