package jimmy.gg.flashingnumbers.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.TabbedHighScore;
import jimmy.gg.flashingnumbers.settings.SettingsAdapter;
import jimmy.gg.flashingnumbers.words.words.WordsScore;

public class MainHighScore extends AppCompatActivity {
    public ArrayList<String> scores;
    public ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_high_score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("High Score");
        scores = new ArrayList<>();
        list = (ListView) findViewById(R.id.main_high_score);
        scores.add(new String("Numbers high score"));
        scores.add(new String("Words high score"));
        list.setAdapter(new SettingsAdapter(getApplicationContext(), scores));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent numbersScore = new Intent(MainHighScore.this, TabbedHighScore.class);
                        startActivity(numbersScore);
                        break;
                    case 1:
                        Intent wordsScore = new Intent(MainHighScore.this, WordsScore.class);
                        startActivity(wordsScore);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
