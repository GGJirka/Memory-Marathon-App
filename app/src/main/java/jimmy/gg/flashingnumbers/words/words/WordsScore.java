package jimmy.gg.flashingnumbers.words.words;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;

public class WordsScore extends AppCompatActivity {
    /**
     * TODO: BETTER DESIGN, ON DELETE SCORE POPUP MENU
     */
    public ArrayList<Score> score;
    public SharedPreferences sharedPreferences;
    public BaseAdapter baseAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_score);
        ListView list = (ListView) findViewById(R.id.words_high_score);
        sharedPreferences = WordsMain.sharedPreferences;
        score = new ArrayList<>();
        if (sharedPreferences.getString("WORDS_SORT", "0").equals("0")) {

            sortByDate();
        } else {
            sortByScore();
        }
        baseAdapter = new HighScoreAdapter(getApplicationContext(), score);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                baseAdapter.notifyDataSetChanged();
            }
        };
        runOnUiThread(run);
        list.setAdapter(baseAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void sortByDate() {
        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
            score.add(new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), "none lul")));
        }
        Collections.reverse(score);
    }

    public void sortByScore() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> addScore = new ArrayList<>();
                for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
                    Score score0 = new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), ""));
                    addScore.add(Integer.parseInt(score0.getText().split(" ")[3]));
                }
                Collections.sort(addScore, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });
                for (int i = 0; i < addScore.size(); i++) {
                    String score0 = String.valueOf(addScore.get(i));
                    for (int j = 0; j < addScore.size(); j++) {
                        Score row = new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(j), ""));
                        if (row.getText().split(" ")[3].equals(score0)) {
                            score.add(row);
                            break;
                        }
                    }
                }
            }
        };
        runOnUiThread(run);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.words_score_menu, menu);
        if (sharedPreferences.getString("WORDS_SCORE_CHECK", "0").equals("0")) {
            menu.findItem(R.id.sort_date).setChecked(true);
        } else {
            menu.findItem(R.id.sort_score).setChecked(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void deleteScoreDialog() {
        new AlertDialog.Builder(WordsScore.this)
                .setTitle("Delete score")
                .setMessage("Do you really want to delete all score?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
                            sharedPreferences.edit().remove("WORDS_SCORE" + String.valueOf(i)).commit();
                            sharedPreferences.edit().remove("WORDS_COUNT_SCORE").commit();
                        }
                        score.removeAll(score);
                        baseAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.words_delete:
                deleteScoreDialog();
                break;
            case R.id.sort_date:
                score.removeAll(score);
                sharedPreferences.edit().putString("WORDS_SORT", "0").commit();
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                sortByDate();
                sharedPreferences.edit().putString("WORDS_SCORE_CHECK", "0").commit();
                baseAdapter.notifyDataSetChanged();
                break;

            case R.id.sort_score:
                sharedPreferences.edit().putString("WORDS_SORT", "1").commit();
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                score.removeAll(score);
                sortByScore();
                sharedPreferences.edit().putString("WORDS_SCORE_CHECK", "1").commit();
                baseAdapter.notifyDataSetChanged();
                break;
            default:
                sharedPreferences.edit().putString("WORDS_STATE", "0").commit();
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        sharedPreferences.edit().putString("WORDS_STATE", "0").commit();
        this.finish();
    }
}
