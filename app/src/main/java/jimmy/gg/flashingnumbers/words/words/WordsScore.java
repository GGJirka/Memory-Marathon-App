package jimmy.gg.flashingnumbers.words.words;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;

public class WordsScore extends AppCompatActivity {

    public ArrayList<Score> score;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_score);
        ListView list = (ListView) findViewById(R.id.words_high_score);
        sharedPreferences = WordsMain.sharedPreferences;
        score = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
            score.add(new Score(sharedPreferences.getString("WORDS_SCORE", "")));
        }
        list.setAdapter(new HighScoreAdapter(getApplicationContext(), score));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
