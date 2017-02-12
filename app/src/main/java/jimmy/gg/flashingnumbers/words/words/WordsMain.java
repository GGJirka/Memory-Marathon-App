package jimmy.gg.flashingnumbers.words.words;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import jimmy.gg.flashingnumbers.R;

public class WordsMain extends AppCompatActivity {
    /**
     * TODO:   BETTER ALGORITHM IN WORDS, BETTER DESIGN, ADD HIGH SCORE, WHEN 0 LIVES MENU.
     */
    private SharedPreferences sharedPreferences;
    private WordsStats words;
    private BufferedReader bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_main2);
        setTitle(getText(R.string.words_activity_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        words = new WordsStats(3, 0);
        initWordList();
        if (sharedPreferences.getString(readText(R.string.words_activity_dialog), "0").equals("0")) {
            init();
        }
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.checkbox, null);
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.check);
        new AlertDialog.Builder(WordsMain.this)
                .setView(view)
                .setTitle("How to play")
                .setMessage("There will be always one word displayed.\n" +
                        "press seen if you already seen that word " +
                        "or press new if it is new.")
                .setPositiveButton(getText(R.string.quick_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkbox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(readText(R.string.words_activity_dialog), "check");
                            editor.commit();
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (checkbox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(readText(R.string.words_activity_dialog), "check");
                            editor.commit();
                        }
                    }
                })
                .show();
    }

    public void initWordList() {
        try {
            bf = new BufferedReader(new InputStreamReader(getResources().getAssets().open("wordlist.txt")));
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    String line = "";
                    while (line != null) {
                        try {
                            line = bf.readLine();
                            words.addWord(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    TextView word = (TextView) findViewById(R.id.words_word);
                    int r = new Random().nextInt(words.getWordList().size());
                    word.setText(words.getWords(r));
                }
            };
            runOnUiThread(run);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newWord(View view) {
        TextView word = (TextView) findViewById(R.id.words_word);
        int r = new Random().nextInt(words.getWordList().size());
        if (words.isInUsed(word.getText().toString())) {
            TextView lives = (TextView) findViewById(R.id.words_lives);
            words.setLives(words.getLives() - 1);
            lives.setText("lives: " + words.getLives());
        } else {
            TextView score = (TextView) findViewById(R.id.words_score);
            words.setScore(words.getScore() + 1);
            score.setText("score: " + words.getScore());
        }
        if (words.count != words.getRandom()) {
            words.addNewWord(word.getText().toString());
            word.setText(words.getWords(r));
        } else {
            word.setText(words.getUsedWords().get(new Random().nextInt(words.getUsedWords().size())));
            words.count = 0;
            words.setRandom(5);
        }
        words.count++;

    }

    public void seenWord(View v) {
        TextView word = (TextView) findViewById(R.id.words_word);
        int r = new Random().nextInt(words.getWordList().size());
        if (!words.isInUsed(word.getText().toString())) {
            TextView lives = (TextView) findViewById(R.id.words_lives);
            words.setLives(words.getLives() - 1);
            lives.setText("lives: " + words.getLives());
        } else {
            TextView score = (TextView) findViewById(R.id.words_score);
            words.setScore(words.getScore() + 1);
            score.setText("score: " + words.getScore());
        }
        if (words.count != words.getRandom()) {
            words.addNewWord(word.getText().toString());
            word.setText(words.getWords(r));
        } else {
            word.setText(words.getUsedWords().get(new Random().nextInt(words.getUsedWords().size())));
            words.count = 0;
            words.setRandom(5);
        }
        words.count++;
    }

    public String readText(int string) {
        return String.valueOf(getText(string));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
