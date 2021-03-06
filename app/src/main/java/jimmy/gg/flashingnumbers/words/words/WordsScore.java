package jimmy.gg.flashingnumbers.words.words;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.HighScoreAdapter;
import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.main.MemoryMarathon;

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
        sharedPreferences = MemoryMarathon.sharedPreferences;
        setTitle(getString(R.string.high_score_title));
        score = new ArrayList<>();
        if (sharedPreferences.getString("WORDS_SORT", "0").equals("0")) {
            sortByDate();
        } else {
            sortByScore();
        }
        if (score.size() == 0) {
            addView();
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

    public void addView() {
        TextView view = new TextView(WordsScore.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        TypedValue typedValue = new TypedValue();
        int actionBarHeight=0;
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize,typedValue,true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,getResources().getDisplayMetrics());
        }
        params.setMargins(0,0,0, actionBarHeight*2);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        view.setText(getString(R.string.nothing_to_display));
        view.setTextSize(20);
        view.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_words_score);
        layout.addView(view);
    }
    public void sortByDate() {
        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
            String s = sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), "none lul");
            score.add(new Score(s.split(" ")[0]+" "+s.split(" ")[1]+" "+getString(R.string.score)+" "+s.split(" ")[s.split(" ").length-1]));
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
                ArrayList<Score> addScores = new ArrayList<>();
                for (int j = 0; j < addScore.size(); j++) {
                    Score row = new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(j), ""));
                    addScores.add(row);
                }
                for (int i = 0; i < addScore.size(); i++) {
                    String score0 = String.valueOf(addScore.get(i));
                    for (Score score1 : addScores) {
                        if (score1.getText().split(" ")[3].equals(score0)) {
                            String s = score1.getText();
                            score.add(new Score(s.split(" ")[0]+" "+s.split(" ")[1]+" "+getString(R.string.score)+" "+s.split(" ")[s.split(" ").length-1]));
                            addScores.remove(score1);
                            break;
                        }
                    }
                }
            }
        };
        runOnUiThread(run);
    }

    @Override
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
       AlertDialog.Builder build = new AlertDialog.Builder(WordsScore.this);
                build.setTitle(getString(R.string.delete_all_score));
                build.setMessage(R.string.words_delete_all);
                build.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
                            sharedPreferences.edit().remove("WORDS_SCORE" + String.valueOf(i)).apply();
                            sharedPreferences.edit().remove("WORDS_COUNT_SCORE").apply();
                        }
                        score.removeAll(score);
                        baseAdapter.notifyDataSetChanged();
                        addView();
                    }
                });
                build.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = build.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.numbers_button_color));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.numbers_button_color));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.words_delete:
                deleteScoreDialog();
                break;
            case R.id.sort_date:
                score.removeAll(score);
                sharedPreferences.edit().putString("WORDS_SORT", "0").apply();
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                sortByDate();
                sharedPreferences.edit().putString("WORDS_SCORE_CHECK", "0").apply();
                baseAdapter.notifyDataSetChanged();
                break;

            case R.id.sort_score:
                sharedPreferences.edit().putString("WORDS_SORT", "1").apply();
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                score.removeAll(score);
                sortByScore();
                sharedPreferences.edit().putString("WORDS_SCORE_CHECK", "1").apply();
                baseAdapter.notifyDataSetChanged();
                break;

            case R.id.graph:
                if(Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0"))!=0) {
                    Intent graphIntent = new Intent(this, WordsGraph.class);
                    startActivity(graphIntent);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.graph_no_score, Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                sharedPreferences.edit().putString("WORDS_STATE", "0").apply();
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        sharedPreferences.edit().putString("WORDS_STATE", "0").apply();
        this.finish();
    }
}
